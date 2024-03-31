package com.rondeo.pixwarsspace.gamescreen.components.play;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
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

public class PlayShip extends Player {
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
    Animation<AtlasRegion> explosionAnimation;

    int width = 15, height = 25;
    float screenWidth, screenHeight;
    long time;
    boolean isDead = true;
    public boolean invulnerable = true;
    long isHit;

    public PlayShip(World<Entity> world, Array<AtlasRegion> explosionRegions ) {
        this.world = world;
        item = new Item<Entity>( this );
        world.add( item, 0, 0, 0, 0 );
//        explosionAnimation = new Animation<>( .15f, explosionRegions );
//        explosionAnimation.setPlayMode( PlayMode.NORMAL );

        explosionAnimation = new Animation<>( .31f, explosionRegions );
        explosionAnimation.setPlayMode( PlayMode.LOOP );

        shapeRenderer = new ShapeRenderer();
    }

    float positionX, positionY;

    float runEndXLeft, runEndXRight;

    private ShapeRenderer shapeRenderer;

    public void init( AtlasRegion shipRegion, final float[] pattern, float positionX, float positionY ) {
        this.shipRegion = shipRegion;
        life = 4;
        invulnerable = true;
        screenWidth = getStage().getWidth();
        screenHeight = getStage().getHeight();
        this.positionX = positionX;
        this.positionY = positionY;

        //setBounds( pattern[0] * screenWidth, pattern[1] * screenHeight, width, height );
//        world.update( item, pattern[0] * screenWidth, pattern[1] * screenHeight, width, height );
//        world.update( item, screenWidth/2, screenHeight - 32, width, height );
        runEndXLeft = 0 + positionX - 40;
        runEndXRight = 0 + positionX + 40;

        world.update( item, positionX + 4, positionY , width, height );
        setOrigin(getWidth() / 2f, getHeight() / 2f);
//        world.update( item, 0, 100, width, height );
        resolve();
        isDead = false;


        /*申注释clearActions();
        SequenceAction sequenceAction = new SequenceAction();
        for( int x = 0; x < pattern.length - 1; x += 2 ) {
            final int y = x + 1;
            sequenceAction.addAction( Actions.moveTo( (pattern[x] * screenWidth) - getWidth()/2f, (pattern[y] * screenHeight) - getHeight()/2f, .5f ) );
            sequenceAction.addAction( new Action() {
                @Override
                public boolean act( float delta ) {
                    if( y > 4 )
                        invulnerable = false;
                    if( getStage() != null && pattern[y] > .5f && pattern[y] < 1f ) {
                        Controllers.getInstance().bulletController().fire( getStage(), ( getX() + getWidth()/2f ) - Bullet.width/2, getY() + 5f, -.5f, 0, false );
                        Controllers.getInstance().bulletController().fire( getStage(), ( getX() + getWidth()/2f ) - Bullet.width/2, getY() + 5f, .5f, 0, false );
                    }
                    return true;
                }
            } );
        }*/


        /*setBounds( pattern.first().x * screenWidth, pattern.first().y * screenHeight, width, height );
        world.update( item, pattern.first().x * screenWidth, pattern.first().y * screenHeight, width, height );
        isDead = false;
        clearActions();
        SequenceAction sequenceAction = new SequenceAction();
        for ( Vector2 patternPos : pattern ) {
            sequenceAction.addAction( Actions.moveTo( (patternPos.x * screenWidth) - getWidth()/2f, (patternPos.y * screenHeight) - getHeight()/2f, .5f ) );
            sequenceAction.addAction( new Action() {
                @Override
                public boolean act(float delta) {
                    if( getStage() != null )
                    Controllers.getInstance().bulletController.fire( getStage(), ( getX() + getWidth()/2f ) - Bullet.width/2, getY() + 5f, false );
                    return true;
                }
            } );
        }*/
        /*申注释addAction( Actions.forever( sequenceAction ) );*/
    }

    float deltaTime;
    Random random = new Random();

    private float duration = 130f; // 下落动画持续时间
    private float elapsedTime = 0f;

     private Interpolation interpolation = Interpolation.swingOut; // 选择一个插值函数

    float tempX = 0.01f;

    private boolean isHd = false;
    float tempEnd = runEndXLeft;
//    float rotate = 0f;

    @Override
    public void act(float delta) {

        deltaTime += delta;
//        if (true) {
//            return;
//        }
        world.move( item, getX(), getY(), collisionFilter );
        resolve();
//        ++faShe;
        if( System.currentTimeMillis() > time + 800 && ( !Controllers.getInstance().gameOver && !Controllers.getInstance().pause && !Controllers.getInstance().bossController().dead ) ) {
            time = System.currentTimeMillis();
            Enemy enemyShip  = LevelManager.findClosestEnemy(getX(), getY());
            if (null != enemyShip) {

//                Constants.POINT_BRICK_SHIPS.get("2").getBrickShip().runJump();
//                Constants.POINT_BRICK_SHIPS.get("2").getPointShip().runJump();

                // TODO:shen 测试先往左跳
                /*SulgPoint sulgPoint = Constants.SLUGSHIP.get("2:0");
                Axis axis = sulgPoint.getAxis();
                sulgPoint.getSlugShip().init(axis.getX(), axis.getY());*/

                System.out.println("////////////////////" + enemyShip.getX() + "-" + enemyShip.getY());

                // 获取敌人的位置
                float enemyX = enemyShip.getX();
                float enemyY = enemyShip.getY();
//                rotate += 10f;

                // 计算飞船需要旋转的角度
                float rotate = CoordinateUtil.rotateVertical(enemyX, enemyY, getX(), getY());
                setOrigin(7.5f, 9.5f);


                // 旋转飞船
                setRotation(rotate);
                float startX = getX() - 15f;
                float startY = getY();
                float endX = enemyX;
                float endY = enemyY;
                double speed = 5.0; // 移动速度
                double distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)); // 计算起点到终点的距离
                double moveDistance = Math.min(speed, distance); // 每帧移动的距离
                double alpha = moveDistance / distance; // 计算插值比例

                float xMoveCM = (endX - startX) * (float) alpha;
                float yMoveCM = (endY - startY) * (float) alpha;



//                Controllers.getInstance().bulletController().fire( getStage(), (getX() + getWidth()/2f) - Bullet.width/2, getTop() - Constants.CELL_HIGHT - 5f, 0, 0, true );
                // 发射子弹，设置子弹的速度和方向
//                Controllers.getInstance().bulletController().fire(getStage(), getX(), getY() - Constants.CELL_HIGHT + 8f, MathUtils.cosDeg(angle), MathUtils.sinDeg(angle), true, rotate);

                // 可以的第一版
//                Controllers.getInstance().bulletController().fire(getStage(), getX(), getY(), xMoveCM, yMoveCM, true, rotate);

                // 二次贝尔曲线版本
                Controllers.getInstance().bulletController().fire(getStage(), getX(), getY(), new Axis(enemyX, enemyY), true, rotate);
//                Controllers.getInstance().bulletController().fire( getStage(), (getX() + getWidth()/2f) - Bullet.width/2, getTop() - 5f, 0, 0, true ,1);
            } else {
                System.out.println("敌人为空");
            }
        }

    }
    Color color = new Color();
    int isMove = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.act( parentAlpha );
        deltaTime += parentAlpha;
        // shen
//        if (isMove >= 100) {
//            return;
//        }
        super.draw(batch, parentAlpha);

//        batch.end();
//
//        // 在对象的中心绘制一个十字交叉线
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.line(10, 10, 100, 10);
//        System.out.println("字交叉线:" + (getX() + getWidth() / 2f));
////        shapeRenderer.line(getX(), getY() + getHeight() / 2f, getX() + getWidth(), getY() + getHeight() / 2f);
//        shapeRenderer.end();
//        batch.begin();

        //if( !isDead )
//        batch.draw( shipRegion, getX(), getY(), getWidth(), getHeight() );
//        batch.draw( shipRegion, 100, 100, getWidth(), getHeight() );
        batch.draw( explosionAnimation.getKeyFrame( deltaTime ), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
//        batch.draw( explosionAnimation.getKeyFrame( deltaTime ), getX(), getY(), getWidth(), getHeight() );


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
        Controllers.getInstance().getPlayController().forceFree( this );
    }

    @Override
    public void reset() {
        clearActions();
        isDead = true;
        world.update( item, -100, -100 );
        resolve();
        remove();
    }

    public void resolve() {
        rect = world.getRect( item );
        setBounds( rect.x, rect.y, rect.w, rect.h );
    }

}
