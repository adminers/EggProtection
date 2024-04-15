package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dongbat.jbump.*;
import com.dongbat.jbump.Response.Result;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.Outbound;
import com.rondeo.pixwarsspace.utils.SoundController;

public class LightningBullet extends Actor implements Entity, Disposable, Poolable {
    World<Entity> world;
    Item<Entity> item;
    Rect rect;
    Result result;
    Collision collision;

    private float speed = 10; // 添加一个速度变量，默认值为300

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
    float time = 0;
    public float dirX = 0, dirY = 0;
    public static int width = 8, height = 8;

    boolean isAngled = false;
    float angle;

    private String name = "默认";

    private Vector2 position;
    private Vector2 velocity;
    private float t = 0;
    private Bezier<Vector2> bezierCurve;

    public LightningBullet(World<Entity> world, float x, float y, TextureRegion... bulletRegion) {
        this.world = world;
        item = new Item<Entity>(this);
        world.add(item, x, y, width, height);

        this.bulletRegion = bulletRegion;
        setBounds(x, y, width, height);
    }

    public void init(float x, float y, Axis axis, boolean top, float angle) {
        isDead = false;
        world.update(item, x, y, width, height);
        resolve();
        this.top = top;
        this.angle = angle;
        this.isAngled = true;
        this.name = "角度";

        // 设置原点(原点是Actor的旋转、缩放和其他变换操作的参考点。)
        setOrigin(getWidth() / 2f, getHeight() / 2f);
        setRotation(angle);


        Vector2 startPos = new Vector2(x, y);

        float targetX = axis.getX();
        float targetY = axis.getY() + 110;

        float controlTarget;

        // 目标点在左侧
        if (x > targetX) {
            controlTarget = x - 50;
        } else {
            controlTarget = targetX - x;
        }
        System.out.println("y=" + y + ";x=" + x + ";targetX=" + targetX + ";targetY=" + targetY + ";");

        Vector2 controlPoint = new Vector2(controlTarget, targetY);
        Vector2 endPoint = new Vector2(targetX, axis.getY());

        position = new Vector2(startPos);
        Vector2 direction = new Vector2(endPoint).sub(startPos).nor();
        velocity = direction.scl(5);
        bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        t = 0;
    }

    /**
     * 超出屏幕
     *
     * @return
     */
    private boolean isDriftAway() {

        return getX() >= 2000 || getX() <= -10 || getY() >= 2000 || getY() <= -10;
    }

    public void update() {

        if (null == bezierCurve) {
            return;
        }
        Vector2 point = bezierCurve.valueAt(new Vector2(), t);
        position.set(point);

        t += 0.05f;
        if (t > 1) {
            t = 1;
        }
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
        update();
        super.act(delta);
        resolve();
        result = world.move(item, position.x, position.y, collisionFilter);

        for (int i = 0; i < result.projectedCollisions.size(); i++) {
            collision = result.projectedCollisions.get(i);
            if (collision.other.userData instanceof Outbound || collision.other.userData instanceof LightningBullet) {
                forceFree();
                return;
            }
            if (collision.other.userData instanceof BossParts) {
                if (((BossParts) collision.other.userData).life > 0) {
                    if (((BossParts) collision.other.userData).isHit + 100 < System.currentTimeMillis()) {
                        ((BossParts) collision.other.userData).isHit = System.currentTimeMillis() + 100;
                        SoundController.play(SoundController.getInstance().hurt);
                        ((BossParts) collision.other.userData).reduceLife();
                    }
                    forceFree();
                    return;
                }
            }
            if (collision.other.userData instanceof Ship) {
                if (System.currentTimeMillis() > ((Ship) collision.other.userData).isHit + ((Ship) collision.other.userData).invTime && System.currentTimeMillis() > ((Ship) collision.other.userData).invulnerable) {
                    SoundController.play(SoundController.getInstance().hurt);
                    ((Ship) collision.other.userData).isHit = System.currentTimeMillis() + 300;
                    ((Ship) collision.other.userData).life--;
                }
                forceFree();
                return;
            }
            if (collision.other.userData instanceof EnemyShip) {
                ((EnemyShip) collision.other.userData).isHit = System.currentTimeMillis() + 100;
                ((EnemyShip) collision.other.userData).life--;
                forceFree();
                return;
            }
            if (collision.other.userData instanceof SnakeEnemyShip) {
                ((SnakeEnemyShip) collision.other.userData).isHit = System.currentTimeMillis() + 100;
                ((SnakeEnemyShip) collision.other.userData).life--;
                forceFree();
                return;
            }

            if (collision.other.userData instanceof MonsterShip) {
                ((MonsterShip) collision.other.userData).isHit = System.currentTimeMillis() + 100;
                ((MonsterShip) collision.other.userData).life--;
                forceFree();
                System.out.println("敌人");
                return;
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isDead)
            return;
        super.draw(batch, parentAlpha);

        TextureRegion region = bulletRegion[0];
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }

    @Override
    public void dispose() {

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

}
