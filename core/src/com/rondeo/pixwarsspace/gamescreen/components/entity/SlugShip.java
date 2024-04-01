package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.dongbat.jbump.*;
import com.qiaweidata.starriverdefense.test.func.EasingFunctions;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.LevelManager;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.BossController.Laser;
import com.rondeo.pixwarsspace.gamescreen.pojo.CenterPoint;
import com.rondeo.pixwarsspace.utils.Constants;

import static com.rondeo.pixwarsspace.utils.Constants.COMMON_SHIP_HEIGHT;

public class SlugShip extends Entity.Wrapper implements Entity, Disposable {


    World<Entity> world;
    Item<Entity> item;

     Rect rect;

    AtlasRegion baseRegion, wingRegion, base_sketchRegion, wing_sketchRegion, effectRegion;
    Animation<AtlasRegion> thrusterAnimation;
    public static final int maxLife = 7;

    public int life = maxLife;

    // 446  291
    int width = 25, height = 19;

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
    private float fallDuration = 1.0f;
    private float shakeDuration = 0.5f;
    private float returnDuration = 1.0f;
    private boolean isFalling = true;
    private boolean isShaking = false;
    private boolean isReturning = false;
    private boolean isToTarget = false;
    private boolean isDraw = false;

    private Bezier<Vector2> bezierCurve;

    private float t = 0;

    private Vector2 position;

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

        thrusterAnimation = new Animation<>( .09f, thrustersRegion );
        thrusterAnimation.setPlayMode( PlayMode.LOOP );
    }

    public SlugShip(World<Entity> world, AtlasRegion shipRegion) {

        this.isDraw = false;
        this.baseRegion = shipRegion;
        this.world = world;

//        setBounds( stageX, stageY, width, height );
        item = new Item<Entity>( this );
        world.add( item, getX(), getY(), getWidth(), getHeight() );

        // 设置原点(原点是Actor的旋转、缩放和其他变换操作的参考点。)
        setOrigin( getWidth()/2f, getHeight()/2f );
    }

    public void init( float positionX, float positionY ) {


        life = 4;
        this.positionX = positionX;
        this.positionY = positionY = positionY + COMMON_SHIP_HEIGHT;
        world.update( item, 0 + positionX, positionY, width, height );
        resolve();
        isDead = false;

//        Axis axis = Constants.SLUGSHIP.get(getName()).getAxis();
        CenterPoint centerPoint = Constants.CENTER_POINTS.get(getName());
        Axis leftAxis = centerPoint.getAttr().getLeftBottom();
        System.out.println("中心：" + centerPoint.getAxis() + ";左侧位：" + leftAxis);

        float targetX = leftAxis.getX();
        float targetY = leftAxis.getY() + COMMON_SHIP_HEIGHT;



        Vector2 startPos = new Vector2(positionX, positionY);
        float controlTargetX = positionX - 10;
        float controlTargetY = positionY + 10;

        Vector2 controlPoint = new Vector2(controlTargetX, controlTargetY);
        Vector2 endPoint = new Vector2(targetX, targetY);

        position = new Vector2(startPos);
        Vector2 direction = new Vector2(endPoint).sub(startPos).nor();
        Vector2 velocity = direction.scl(5);
        bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        t = 0;

        isToTarget = false;
        elapsedTime = 0;
        isDraw = true;
    }

    private boolean runJump = false;

    public void runJump() {
        isToTarget = false;
        elapsedTime = 0;
    }

    int faShe = 0;
    boolean tmp = false;

    @Override
    public void act( float delta ) {

        if (!isDraw) {
            return;
        }
        deltaTime += delta;
        if (true) {
            return;
        }

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

    @Override
    public void draw(Batch batch, float parentAlpha ) {

        if (!isDraw) {
            return;
        }
        if (System.currentTimeMillis() > time + 3000) {
            time = System.currentTimeMillis();
        }
        elapsedTime += Gdx.graphics.getDeltaTime();

        update();
        batch.draw( thrusterAnimation.getKeyFrame( deltaTime ), position.x, position.y, getWidth(), getHeight() );

        // Base ship
//        batch.draw( baseRegion, getX(), newY, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );

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
}
