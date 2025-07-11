package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.BossController.Laser;
import com.rondeo.pixwarsspace.gamescreen.ui.func.EasingFunctions;

public class PointShip extends Entity.Wrapper implements Entity, Disposable {


    World<Entity> world;
    Item<Entity> item;

     Rect rect;

    AtlasRegion baseRegion, wingRegion, base_sketchRegion, wing_sketchRegion, effectRegion;
    Animation<AtlasRegion> thrusterAnimation;
    public static final int maxLife = 7;

    public int life = maxLife;
    int width = 5, height = 5;

    float moveX, moveY;
    long time;
    float deltaTime;
    int invTime = 2700;
    public long invulnerable = 0;
    public long effect = 0;
    public long hasWings = 0;

    float positionX, positionY;

    boolean isDead = true;

    private float elapsedTime = 0;
    private float initialY = 200;
    float newY = initialY;
    private float targetY = initialY - 30;
    private float fallDuration = 1.0f;
    private float shakeDuration = 0.5f;
    private float returnDuration = 1.0f;
    private boolean isFalling = true;
    private boolean isShaking = false;
    private boolean isReturning = false;
    private boolean isToTarget = false;
    private float initialUpY = targetY;
    private float targetUpY = initialY;
    private float initY;

    CollisionFilter collisionFilter = new CollisionFilter(  ) {
        @SuppressWarnings( "rawtypes" )
        @Override
        public Response filter(Item item, Item other ) {
            /*if( other.userData instanceof Bullet &&
                !((Bullet) other.userData).isDead && // Check if bullet is not dead
                !((Bullet) other.userData).top && // check if it's not your bullet
                System.currentTimeMillis() > isHit + invTime && // check if you've been hit recently
                System.currentTimeMillis() > invulnerable
            ) {
                SoundController.getInstance().hurt.play();
                isHit = System.currentTimeMillis() + 300;
                Bullet bullet = (Bullet) other.userData;
                bullet.forceFree();
                life --;
            }*/
            if( other.userData instanceof Laser && ((Laser)other.userData).active ) {
                life --;
            }
            return null;
        }

    };

    public void setRegions(AtlasRegion baseRegion, AtlasRegion wingRegion, AtlasRegion base_sketchRegion, AtlasRegion wing_sketchRegion, Array<AtlasRegion> thrustersRegion, AtlasRegion effectRegion ) {
        this.baseRegion = baseRegion;
        this.wingRegion = wingRegion;
        this.base_sketchRegion = base_sketchRegion;
        this.wing_sketchRegion = wing_sketchRegion;
        this.effectRegion = effectRegion;

        thrusterAnimation = new Animation<>( .01f, thrustersRegion );
        thrusterAnimation.setPlayMode( PlayMode.LOOP_PINGPONG );
    }

    public PointShip(World<Entity> world) {
        this.world = world;

//        setBounds( stageX, stageY, width, height );
        item = new Item<Entity>( this );
        world.add( item, getX(), getY(), getWidth(), getHeight() );

        // 设置原点(原点是Actor的旋转、缩放和其他变换操作的参考点。)
        setOrigin( getWidth()/2f, getHeight()/2f );
    }

    public void init( AtlasRegion shipRegion, float positionX, float positionY ) {

        this.baseRegion = shipRegion;
        life = 4;
        this.positionX = positionX;
        this.positionY = positionY;
        float y2 = 200 + positionY;
        world.update( item, 0 + positionX, y2, width, height );
        System.out.println("==计算点位==" + getX() + "=Y=" + getY());
        resolve();
        isDead = false;

        targetY = y2 - 20;
        initialY = y2;
        initialUpY = targetY;
        targetUpY = initialY;
        newY = initialY;
        isToTarget = false;
        elapsedTime = 0;
        initY = y2;
    }

    private boolean runJump = false;

    /**
     * 块,上下缓动
     */
    public void reJump() {

        if (!runJump) {
            return;
        }
        targetY = initY - 20;
        initialY = initY;
        initialUpY = targetY;
        targetUpY = initialY;
        newY = initialY;
        isToTarget = false;
        elapsedTime = 0;
    }


    public void runSlow() {
        targetY = initY - 20;
        initialY = initY;
        initialUpY = targetY;
        targetUpY = initialY;
        newY = initialY;
        isToTarget = false;
        elapsedTime = 0;
    }

    int faShe = 0;
    boolean tmp = false;

    @Override
    public void act( float delta ) {

        if (true) {
            return;
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha ) {

        if (System.currentTimeMillis() > time + 3000) {
            time = System.currentTimeMillis();
            reJump();
        }
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (isToTarget) {
            if (newY != targetUpY) {
                newY = EasingFunctions.easeOutCubic(elapsedTime, initialUpY, targetUpY - initialUpY, fallDuration);
            }
            if (newY > targetUpY) {
                newY = targetUpY;
            }
        } else {
            if (newY > targetY + .51) {
                newY = EasingFunctions.easeOutExpo(elapsedTime, initialY, targetY - initialY, returnDuration);
            } else {
                isToTarget = true;
                elapsedTime = 0f;
            }
        }

        // Base ship
        batch.draw( baseRegion, getX(), newY, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );

    }

    public void resolve() {
        rect = world.getRect( item );
        setBounds( rect.x, rect.y, rect.w, rect.h );
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {

    }

    public void forceFree() {
//        Controllers.getInstance().getBrickController().forceFree( this );
    }

    public PointShip setRunJump(boolean runJump) {
        this.runJump = runJump;
        return this;
    }
}
