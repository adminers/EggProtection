package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.BossController.Laser;
import com.rondeo.pixwarsspace.utils.SoundController;

public class Ship extends Entity.Wrapper implements Entity, Disposable, InputProcessor {
    World<Entity> world;
    Item<Entity> item;
    Rect rect;

    AtlasRegion baseRegion, wingRegion, base_sketchRegion, wing_sketchRegion, effectRegion;
    Animation<AtlasRegion> thrusterAnimation;
    public static final int maxLife = 7;
    public int life = maxLife;
    int width = 32, height = 32;
    long time;
    float deltaTime;
    int invTime = 2700;
    public long invulnerable = 0;
    public long effect = 0;
    public long hasWings = 0;

    private boolean win = false;

    CollisionFilter collisionFilter = new CollisionFilter(  ) {
        @SuppressWarnings( "rawtypes" )
        @Override
        public Response filter( Item item, Item other ) {
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

    public void setRegions( AtlasRegion baseRegion, AtlasRegion wingRegion, AtlasRegion base_sketchRegion, AtlasRegion wing_sketchRegion, Array<AtlasRegion> thrustersRegion, AtlasRegion effectRegion ) {
        this.baseRegion = baseRegion;
        this.wingRegion = wingRegion;
        this.base_sketchRegion = base_sketchRegion;
        this.wing_sketchRegion = wing_sketchRegion;
        this.effectRegion = effectRegion;

        thrusterAnimation = new Animation<>( .01f, thrustersRegion );
        thrusterAnimation.setPlayMode( PlayMode.LOOP_PINGPONG );
    }

    public Ship( World<Entity> world ) {
        this.world = world;

        setBounds( 50, 200, width, height );
        item = new Item<Entity>( this );
        world.add( item, getX(), getY(), getWidth(), getHeight() );
        setOrigin( getWidth()/2f, getHeight()/2f );
    }

    Affine2 effectAffine = new Affine2();

    @Override
    public void act( float delta ) {
        
        if (true) return;

        super.act( delta );
        deltaTime += delta;
        world.move( item, getX(), getY(), collisionFilter );
        resolve();
        if( System.currentTimeMillis() > time + 80 && ( !Controllers.getInstance().gameOver && !Controllers.getInstance().pause && !Controllers.getInstance().bossController().dead ) ) {
            time = System.currentTimeMillis();
            if( effect > System.currentTimeMillis() ) {
                Controllers.getInstance().bulletController().fire( getStage(), getX() - Bullet.width/2, getTop() - 5f, 0, 0, true );
                Controllers.getInstance().bulletController().fire( getStage(), getRight() - Bullet.width/2, getTop() - 5f, 0, 0, true );
            }
            if( hasWings > System.currentTimeMillis() ) {
                Controllers.getInstance().bulletController().fire( getStage(), getX() - Bullet.width/2, getTop() - 5f, -.5f, 0, true );
                Controllers.getInstance().bulletController().fire( getStage(), getRight() - Bullet.width/2, getTop() - 5f, .5f, 0, true );
            }
            Controllers.getInstance().bulletController().fire( getStage(), (getX() + getWidth()/2f) - Bullet.width/2, getTop() - 5f, 0, 0, true );
        }

        if( life <= 3 && !SoundController.getInstance().alarm.isPlaying() && !Controllers.getInstance().gameOver ) {
            SoundController.getInstance().alarm.play();
        }

        if( life <= 0 ) {
            if( !Controllers.getInstance().gameOver )
                SoundController.play( SoundController.getInstance().death );
            Controllers.getInstance().gameOver = true;
            rotateBy( 1 );
        }
    }

    @Override
    public void draw( Batch batch, float parentAlpha ) {
        if (true) return;
        // Base ship
        batch.draw( baseRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
        
        // Ship's Wings
        if( hasWings > System.currentTimeMillis() )
            batch.draw( wingRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );

        // Shield sprites
        if( System.currentTimeMillis() < isHit + invTime || invulnerable > System.currentTimeMillis() ) {
            batch.draw( base_sketchRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
            if( hasWings > System.currentTimeMillis() )
                batch.draw( wing_sketchRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
        }

        // Ship's tank effect
        if( System.currentTimeMillis() < effect ) {
            effectAffine.rotate( deltaTime * 10f );
            batch.draw( effectRegion, getX() - getWidth()/2f, getY() - getHeight()/2f, getWidth(), getHeight(), getWidth()*2f, getHeight()*2f, getScaleX(), getScaleY(), deltaTime * 200f );
        }

        // Thrusters
        batch.draw( thrusterAnimation.getKeyFrame( deltaTime ), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
    }

    public void resolve() {
        rect = world.getRect( item );
        setBounds( rect.x, rect.y, rect.w, rect.h );
    }

    public void tryEnd() {
        if( Controllers.getInstance().bossController().dead && !win ) {
            Controllers.getInstance().enemyController().freeAll();
            effect = System.currentTimeMillis() + 60 * 1000;
            clearActions();
            addAction( Actions.sequence(
                Actions.moveTo( getStage().getWidth()/2f - getWidth()/2f, getStage().getHeight()/2f - getHeight()/2f, 1f ),
                new Action() {
                    int bulletsReleased = 0;
                    Vector2 bulletDir = new Vector2( -1, -1 );
                    int rotation = 0;
                    @Override
                    public boolean act( float delta ) {
                        rotation ++;
                        if( bulletsReleased < 300 ) {
                            bulletDir.rotateDeg( rotation );
                            Controllers.getInstance().bulletController().fire( getStage(), (getX() + getWidth()/2f) - Bullet.width/2, getTop() - 5f, bulletDir.x, bulletDir.y - (300*delta), true );
                            bulletsReleased ++;
                            return false;
                        }
                        return true;
                    }
                },
                Actions.delay( 1f ),
                Actions.moveTo( getStage().getWidth()/2f - getWidth()/2f, getStage().getHeight() + 200f, 2f ),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        Controllers.getInstance().credits = 1;
                        return true;
                    }
                }
            ) );
            win = true;
        }
    }

    @Override
    public void dispose() { }

    @Override
    public boolean keyDown( int keycode ) { return false; }

    @Override
    public boolean keyUp( int keycode ) { return false; }

    @Override
    public boolean keyTyped( char character ) { return false; }

    @Override
    public boolean touchDown( int screenX, int screenY, int pointer, int button ) {
//        if( Controllers.getInstance().pause || Controllers.getInstance().gameOver || Controllers.getInstance().bossController().dead ) {
//            tryEnd();
//            return false;
//        }
        return false;
    }

    @Override
    public boolean touchUp( int screenX, int screenY, int pointer, int button ) { return false; }

    Ray ray;
    Vector3 endPoint = new Vector3();
    float x = 0, y = 0;

    @Override
    public boolean touchDragged( int screenX, int screenY, int pointer ) {

//        if (true) return false;
        if( Controllers.getInstance().pause || Controllers.getInstance().gameOver || Controllers.getInstance().bossController().dead ) {
            tryEnd();
            return false;
        }
        ray = getStage().getViewport().getPickRay( screenX, screenY );
        ray.getEndPoint( endPoint, 0f );
        x = MathUtils.clamp( endPoint.x - getWidth()/2f, 0, getStage().getViewport().getWorldWidth() - getWidth() );
        y = MathUtils.clamp( endPoint.y, 20, getStage().getViewport().getWorldHeight() - 200f );
        addAction( Actions.moveTo( x, y, .2f ) );
        return true;
    }

    @Override
    public boolean mouseMoved( int screenX, int screenY ) { return false; }

    @Override
    public boolean scrolled( float amountX, float amountY ) { return false; }

}
