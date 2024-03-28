package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Enemy;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.utils.Rumble;
import com.rondeo.pixwarsspace.utils.SoundController;

import java.util.Random;

public class SnakeEnemyShip extends Enemy {
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
            } else */ if( other.userData instanceof ArtilleryShip ) {
                ArtilleryShip ship = (ArtilleryShip) other.userData;
                if( System.currentTimeMillis() > ship.effect )
                    ship.life --;
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

    public SnakeEnemyShip(World<Entity> world, Array<AtlasRegion> explosionRegions ) {
        this.world = world;
        item = new Item<Entity>( this );
        world.add( item, 0, 0, 0, 0 );
//        explosionAnimation = new Animation<>( .15f, explosionRegions );
//        explosionAnimation.setPlayMode( PlayMode.NORMAL );

        explosionAnimation = new Animation<>( .31f, explosionRegions );
        explosionAnimation.setPlayMode( PlayMode.LOOP );
    }

    float positionX, positionY;

    float runEndXLeft, runEndXRight;

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
        world.update( item, 0 + positionX, 200 + positionY, width, height );
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

    @Override
    public void act(float delta) {

        deltaTime += delta;
        if (true) {
            return;
        }

        if (System.currentTimeMillis() > time + 2000) {
            time = System.currentTimeMillis();
            if (isHd) {
                tempEnd = runEndXLeft;
                tempX = -0.01f;
                isHd = false;
            } else {
                tempX = 0.01f;
                tempEnd = runEndXRight;
                isHd = true;
            }
        }

//        if( true )
//            return;
        if( isDead )
            return;
        super.act(delta);
//        float progress = Math.min(1f, deltaTime / duration); // 进度在[0, 1]之间
        float progress = Math.min(1f, 0.33333335f / duration); // 进度在[0, 1]之间
        float curveValue = Interpolation.circleOut.apply(progress); // 使用圆弧曲线
//        float y = getY() - curveValue * 1.10f; // 下落的高度和曲线形状可以调整


//        float progress = Math.min(1f, elapsedTime / duration); // 计算进度
        float x = getX();
        float y = getY();
        float endX = 0;
        float endY = 0;

        // 使用插值函数计算实际的移动位置
        float newX = interpolation.apply(x, tempEnd, progress);
//        float newY = interpolation.apply(y, y - 1, progress);

        world.move( item, newX, y, collisionFilter );
        resolve();
        if( life <= 0 ) {
            if( !SoundController.getInstance().explosion.isPlaying() )
                SoundController.getInstance().explosion.play();
            Rumble.rumble( 4f, .4f );
            clearActions();
            isDead = true;
            deltaTime = 0;
            Controllers.getInstance().powerUpController().pop( getStage(), ((random.nextInt(8) + 1)*0.1f) * getStage().getWidth()/*(getX() + getWidth()/2f) + PowerUp.width/2f*/, /*(getY() + getHeight()/2f)*/ getStage().getHeight() + PowerUp.height/2f );
            return;
        }

        /*if( getStage() != null )
            if( time < System.currentTimeMillis() && getY() > getStage().getHeight()/2f ) {
                time = System.currentTimeMillis() + ( random.nextInt( 10 ) + 5 ) * 100;
                Controllers.getInstance().bulletController.fire( getStage(), ( getX() + getWidth()/2f ) - Bullet.width/2, getY() + 5f, false );
            }*/
    }
    Color color = new Color();
    int isMove = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {

        // shen
//        if (isMove >= 100) {
//            return;
//        }
        super.draw(batch, parentAlpha);

        //if( !isDead )
//        batch.draw( shipRegion, getX(), getY(), getWidth(), getHeight() );
//        batch.draw( shipRegion, 100, 100, getWidth(), getHeight() );
//        batch.draw( explosionAnimation.getKeyFrame( deltaTime ), 100, 200, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
        batch.draw( explosionAnimation.getKeyFrame( deltaTime ), getX(), getY(), getWidth(), getHeight() );


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
        Controllers.getInstance().snakeEnemyController().forceFree( this );
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
