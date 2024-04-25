package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dongbat.jbump.*;
import com.dongbat.jbump.Response.Result;
import com.qiaweidata.starriverdefense.test.light.LineRenderer;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.Outbound;
import com.rondeo.pixwarsspace.gamescreen.components.entity.apper.LightningLineRenderer;

import java.util.ArrayList;
import java.util.List;

public class LightningBullet extends Actor implements Entity, Disposable, Poolable {
    World<Entity> world;
    Item<Entity> item;
    Rect rect;
    Result result;
    Collision collision;

    private float speed = 10; // 添加一个速度变量，默认值为300

    private LightningLineRenderer lightningLineRenderer;

    CollisionFilter collisionFilter = new CollisionFilter() {
        @SuppressWarnings("rawtypes")
        @Override
        public Response filter(Item item, Item other) {
            /*if( other.userData instanceof Outbound ) {
                forceFree();
                return null;
            }
            if( other.userData instanceof BossController.BossParts && top ) {
                BossController.BossParts part = (BossController.BossParts) other.userData;
                if( part.life > 0 ) {
                    if( part.isHit + 100 < System.currentTimeMillis() ) {
                        part.isHit = System.currentTimeMillis() + 100;
                        SoundController.getInstance().hurt.play();
                        part.reduceLife();
                        forceFree();
                    }
                    //forceFree();
                }
            }*/
            if (other.userData instanceof Outbound)
                return Response.cross;
            if (other.userData instanceof BossParts && top)
                return Response.cross;
            if (other.userData instanceof Ship && !top)
                return Response.cross;
            if (other.userData instanceof EnemyShip && top && !((EnemyShip) other.userData).invulnerable && ((EnemyShip) other.userData).life > 0)
                return Response.cross;
            if (other.userData instanceof LightningBullet && ((LightningBullet) other.userData).top != top)
                return Response.cross;
            if (other.userData instanceof SnakeEnemyShip)
                return Response.cross;
            if (other.userData instanceof MonsterShip)
                return Response.cross;
            return null;
        }

    };

    TextureRegion[] bulletRegion;

    boolean isDead = false;

    boolean top = false;

    public float dirX = 0, dirY = 0;

    public static int width = 200, height = 28;

    boolean isAngled = false;
    float angle;

    private String name = "默认";

    private Vector2 position;
    private Vector2 velocity;
    private float t = 0;
    private Bezier<Vector2> bezierCurve;
    private SpriteBatch spriteBatch;

    Animation<AtlasRegion> explosionAnimation;

    public LightningBullet(World<Entity> world, float x, float y, TextureRegion... bulletRegion) {
        this.world = world;
        item = new Item<Entity>(this);
        world.add(item, x, y, width, height);

        this.bulletRegion = bulletRegion;
        setBounds(x, y, width, height);

        lightningLineRenderer = new LightningLineRenderer(new Texture("lib/lightning/particle.png"));

        // 随机
        List<Float[]> points = createPoints(0, 0, 50, 100, 10);
        lightningLineRenderer.putPoint(points);
        spriteBatch = new SpriteBatch();
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("lib/t_map/monster/ligning/LightningTest.atlas"));
        Array<AtlasRegion> explosionRegions = textureAtlas.findRegions( "L1" );
        explosionAnimation = new Animation<>( .31f, explosionRegions );
        explosionAnimation.setPlayMode( PlayMode.LOOP );
    }

    public void init(float x, float y, Axis axis, boolean top, float angle) {
        float endX = MathUtils.random(100, 200);
        float endY = MathUtils.random(100, 200);
        lightningLineRenderer.init(createPoints(0, 0, endX, endY, 4));

        // 根据肉眼观察,细微调整
        x += 20;
        world.update( item, x, y, width, height );
        setRotation(angle + 90);
        System.out.println("闪电角度：" + angle);
        resolve();
        this.time = System.currentTimeMillis();
    }

    /**
     * 超出屏幕
     *
     * @return
     */
    private boolean isDriftAway() {

//        return getX() >= 2000 || getX() <= -10 || getY() >= 2000 || getY() <= -10;
        return false;
    }


    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void act(float delta) {
        if (isDead) {
            return;
        }

        if (isDriftAway()) {
            forceFree();
            return;
        }
    }

    float deltaTime;
    long time;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDead)
            return;
        deltaTime += parentAlpha;
        super.draw(batch, parentAlpha);
        if (System.currentTimeMillis() > this.time + 1000) {
            forceFree();
        }
        batch.draw( explosionAnimation.getKeyFrame( deltaTime ), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    public void forceFree() {
        Controllers.getInstance().getLightningController().forceFree(this);
    }

    @Override
    public void reset() {
        isDead = true;
        world.update(item, -10, -10);
        resolve();
        remove();
    }

    public void resolve() {
        rect = world.getRect(item);
        setBounds(rect.x, rect.y, rect.w, rect.h);
    }

    public List<Float[]> createPoints(float startX, float startY, float endX, float endY, int numPoints) {

        List<Float[]> rs = new ArrayList<>();
        rs.add(new Float[]{startX, startY});
        float deltaX = (endX - startX) / (numPoints - 1);
        float deltaY = (endY - startY) / (numPoints - 1);

        for (int i = 0; i < numPoints; i++) {
            float x = startX + deltaX * i;
            float y = startY + deltaY * i;

            if (i % 2 == 0) {
                x = x - MathUtils.random(-10, 10);
            } else {
                y = y - MathUtils.random(-10, 10);
            }
            rs.add(new Float[]{x, y});
        }
        rs.add(new Float[]{endX, endY});
        return rs;
    }
}
