package com.rondeo.pixwarsspace.gamescreen.components.play;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.Player;
import com.rondeo.pixwarsspace.gamescreen.components.entity.ArtilleryShip;

/**
 * 踩到脚下的云彩
 *
 * @Title:
 * @Description:
 * @Company:www.qiaweidata.com
 * @author:shenshilong
 * @date: 2024-4-28
 * @version:V1.0
 */
public class CloudShip extends Player {
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
    int width = 300, height = 95;
    long time;
    boolean isDead = true;
    public boolean invulnerable = true;
    long isHit;

    public CloudShip(World<Entity> world, Array<AtlasRegion> baseRegion, Array<TextureRegion> explosionRegions ) {
        this.world = world;
        item = new Item<>( this );
        world.add( item, 0, 0, 0, 0 );
        explosionAnimation = new Animation<>( 5f, baseRegion );
        explosionAnimation.setPlayMode( PlayMode.LOOP );
    }

    float positionX, positionY;

    float runEndXLeft, runEndXRight;

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

    @Override
    public void act(float delta) {

        deltaTime += delta;
//        if (true) {
//            return;
//        }
        world.move( item, getX(), getY(), collisionFilter );
        resolve();
        if( System.currentTimeMillis() > time + 8000 && ( !Controllers.getInstance().gameOver && !Controllers.getInstance().pause && !Controllers.getInstance().bossController().dead ) ) {
            time = System.currentTimeMillis();
//            explosionAnimation = explosionAnimation1;
        } else if (System.currentTimeMillis() > time + 4000) {
//            explosionAnimation = explosionAnimation2;
        }

    }
    Color color = new Color();

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
