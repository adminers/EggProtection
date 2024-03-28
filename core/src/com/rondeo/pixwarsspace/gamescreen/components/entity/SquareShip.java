package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Enemy;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.utils.Constants;

/**
 * @Title:
 * @Description:
 * @Company: www.fineplug.top
 * @author: shenshilong[shilong_shen@163.com]
 * @date:
 * @version: v1.0
 */
public class SquareShip extends Entity.Wrapper implements Entity, Disposable {


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

    public void setRegions(AtlasRegion baseRegion, AtlasRegion wingRegion, AtlasRegion base_sketchRegion, AtlasRegion wing_sketchRegion, Array<AtlasRegion> thrustersRegion, AtlasRegion effectRegion ) {
        this.baseRegion = baseRegion;
        this.wingRegion = wingRegion;
        this.base_sketchRegion = base_sketchRegion;
        this.wing_sketchRegion = wing_sketchRegion;
        this.effectRegion = effectRegion;

        thrusterAnimation = new Animation<>( .01f, thrustersRegion );
        thrusterAnimation.setPlayMode( PlayMode.LOOP_PINGPONG );
    }

    public SquareShip( World<Entity> world ) {
        this.world = world;

        setBounds( 20, 20, width, height );
        item = new Item<Entity>( this );
        world.add( item, getX(), getY(), getWidth(), getHeight() );
        setOrigin( getWidth()/2f, getHeight()/2f );
    }

    @Override
    public void act( float delta ) {

        super.act( delta );
        deltaTime += delta;

        if( System.currentTimeMillis() > time + 80 && ( !Controllers.getInstance().gameOver && !Controllers.getInstance().pause && !Controllers.getInstance().bossController().dead ) ) {
            time = System.currentTimeMillis();
//            Controllers.getInstance().bulletController().fire( getStage(), (getX() + getWidth()/2f) - Bullet.width/2, getTop() - Constants.CELL_HIGHT - 5f, 0, 0, true );
            Enemy enemyShip  = findClosestEnemy();

            if (null != enemyShip) {

                // 获取敌人的位置
                float enemyX = enemyShip.getX();
                float enemyY = enemyShip.getY();

                // 计算飞船需要旋转的角度
                float angle = MathUtils.atan2(enemyY - getY(), enemyX - getX()) * MathUtils.radiansToDegrees;

                // 旋转飞船
                setRotation(angle);

//                Controllers.getInstance().bulletController().fire( getStage(), (getX() + getWidth()/2f) - Bullet.width/2, getTop() - Constants.CELL_HIGHT - 5f, 0, 0, true );
                // 发射子弹，设置子弹的速度和方向
                Controllers.getInstance().bulletController().fire(getStage(), (getX() + getWidth()/2f) - Bullet.width/2, getTop() - Constants.CELL_HIGHT - 5f, MathUtils.cosDeg(angle), MathUtils.sinDeg(angle), true);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha ) {

        // Base ship
        batch.draw( baseRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );

    }

    public void resolve() {
        rect = world.getRect( item );
        setBounds( rect.x, rect.y, rect.w, rect.h );
    }

    private Enemy findClosestEnemy() {

        Enemy closestEnemy = null;
        float closestDistance = Float.MAX_VALUE;

        // 遍历所有敌人
        for (Enemy enemy : Constants.ACTIVE_ENEMIES) {

            // 计算飞船与当前敌人的距离
            float distance = Vector2.dst2(getX(), getY(), enemy.getX(), enemy.getY());

            // 如果当前敌人比之前记录的最近敌人更近，则更新最近敌人和距离
            if (distance < closestDistance) {
                closestDistance = distance;
                closestEnemy = enemy;
            }
        }
        if (null != closestEnemy) {
            System.out.println("最近的敌人:" + closestEnemy.getName());
        }

        return closestEnemy;
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {

    }
}
