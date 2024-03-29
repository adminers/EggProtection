package com.rondeo.pixwarsspace.gamescreen.components.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.*;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.*;
import com.rondeo.pixwarsspace.gamescreen.components.entity.ArtilleryShip;
import com.rondeo.pixwarsspace.gamescreen.pojo.SulgPoint;
import com.rondeo.pixwarsspace.utils.Constants;
import com.rondeo.pixwarsspace.utils.CoordinateUtil;

import java.util.Random;

public class YunShip extends Player {
    World<Entity> world;
    Item<Entity> item;
    Rect rect;
    CollisionFilter collisionFilter = new CollisionFilter() {
        //Bullet bullet;
        @SuppressWarnings( "rawtypes" )
        @Override
        public Response filter( Item item, Item other ) {
            /*if( other.userData instanceof Bullet && !((Bullet) other.userData).isDead && ((Bullet) other.userData).top ) {
                if( invulnerable )
                    return null;
                isHit = System.currentTimeMillis() + 100;
                bullet = (Bullet) other.userData;
                bullet.forceFree();
                life --;
            } else */ if( other.userData instanceof ArtilleryShip) {
                ArtilleryShip ship = (ArtilleryShip) other.userData;
                if( System.currentTimeMillis() > ship.effect ) {
                    ship.life --;
                }
                life = 0;
            }
            return null;
        };
    };

    int life;

    AtlasRegion shipRegion;
    //Array<Vector2> pattern;
    Animation<TextureRegion> explosionAnimation;
    Animation<TextureRegion> explosionAnimation1;
    Animation<TextureRegion> explosionAnimation2;

    int width = 100, height = 35;
    float screenWidth, screenHeight;
    long time;
    boolean isDead = true;
    public boolean invulnerable = true;
    long isHit;

    public YunShip(World<Entity> world, Array<TextureRegion> explosionRegions ) {
        this.world = world;
        item = new Item<Entity>( this );
        world.add( item, 0, 0, 0, 0 );
        explosionAnimation1 = new Animation<>( 5f, explosionRegions );
        explosionAnimation1.setPlayMode( PlayMode.LOOP );

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0021.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0022.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0023.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0024.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0025.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0026.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0027.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0028.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0029.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0030.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0031.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0032.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0033.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0034.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0035.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0036.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0037.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0038.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0039.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0040.png"))));

        explosionAnimation2 = new Animation<>( 5f, frames );
        explosionAnimation2.setPlayMode( PlayMode.LOOP );

        explosionAnimation = explosionAnimation1;
        shapeRenderer = new ShapeRenderer();
    }

    float positionX, positionY;

    float runEndXLeft, runEndXRight;

    private ShapeRenderer shapeRenderer;

    public void init( AtlasRegion shipRegion, float positionX, float positionY ) {
        this.shipRegion = shipRegion;
        life = 4;
        invulnerable = true;
        this.positionX = positionX;
        this.positionY = positionY;
        runEndXLeft = 0 + positionX - 40;
        runEndXRight = 0 + positionX + 40;
        world.update( item, positionX + 4, positionY , width, height );
        setOrigin(getWidth() / 2f, getHeight() / 2f);
        resolve();
        isDead = false;



    }

    float deltaTime;
    Random random = new Random();

    private float duration = 130f; // 下落动画持续时间
    private float elapsedTime = 0f;

     private Interpolation interpolation = Interpolation.swingOut; // 选择一个插值函数

    float tempX = 0.01f;

    private boolean isHd = false;
    float tempEnd = runEndXLeft;

    @Override
    public void act(float delta) {

        deltaTime += delta;
//        if (true) {
//            return;
//        }
        world.move( item, getX(), getY(), collisionFilter );
        resolve();
        if( System.currentTimeMillis() > time + 4000 && ( !Controllers.getInstance().gameOver && !Controllers.getInstance().pause && !Controllers.getInstance().bossController().dead ) ) {
            time = System.currentTimeMillis();
            explosionAnimation = explosionAnimation2;
        }

    }
    Color color = new Color();
    int isMove = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.act( parentAlpha );
        deltaTime += parentAlpha;
        super.draw(batch, parentAlpha);
        batch.draw( explosionAnimation.getKeyFrame( deltaTime ), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
        if( isHit > System.currentTimeMillis() || isDead ) {
            color.set( batch.getColor() );
            batch.setColor( 1, 0, 0, .5f );
            batch.draw( shipRegion, getX(), getY(), getWidth(), getHeight() );
            batch.setColor( color );
        }

        if( isDead ) {
            batch.draw( explosionAnimation.getKeyFrame( deltaTime ), getX() - getWidth()/2f, getY() - getHeight()/2f, getWidth()*2f, getHeight()*2f );
            if( explosionAnimation.isAnimationFinished( deltaTime ) ) {
                forceFree();
            }
        }


    }

    @Override
    public void dispose() {
        
    }

    public void forceFree() {
//        Controllers.getInstance().getPlayController().forceFree( this );
    }

    @Override
    public void reset() {
        clearActions();
        isDead = true;
//        world.update( item, -100, -100 );
        resolve();
        remove();
    }

    public void resolve() {
        rect = world.getRect( item );
        setBounds( rect.x, rect.y, rect.w, rect.h );
    }

}
