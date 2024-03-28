package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.dongbat.jbump.*;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Enemy;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.BossController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.BossController.Laser;
import com.rondeo.pixwarsspace.utils.Constants;
import com.rondeo.pixwarsspace.utils.CoordinateUtil;

/**
 * @Title: ArtilleryShip
 * @Description:  ArtilleryShip
 * @Company: www.fineplug.top
 * @author: shenshilong[shilong_shen@163.com]
 * @date: 2024-3-15
 * @version: v1.0
 */
public class ArtilleryShip extends Entity.Wrapper implements Entity, Disposable {


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

    public ArtilleryShip(World<Entity> world, float stageX, float stageY) {
        this.world = world;

        setBounds( stageX, stageY, width, height );
        item = new Item<Entity>( this );
        world.add( item, getX(), getY(), getWidth(), getHeight() );
        System.out.println("==新建大脑袋==" + getX() + "=Y=" + getY());

        // 设置原点(原点是Actor的旋转、缩放和其他变换操作的参考点。)
        setOrigin( getWidth()/2f, getHeight()/2f );
    }

    int faShe = 0;
    boolean tmp = false;

    @Override
    public void act( float delta ) {

        super.act( delta );
        deltaTime += delta;
        if (faShe >= 1) {
            return;
        }
        world.move( item, getX(), getY(), collisionFilter );
        resolve();
//        ++faShe;
        if( System.currentTimeMillis() > time + 1000 && ( !Controllers.getInstance().gameOver && !Controllers.getInstance().pause && !Controllers.getInstance().bossController().dead ) ) {
            time = System.currentTimeMillis();

            Enemy enemyShip  = findClosestEnemy();

            if (null != enemyShip) {

                // 获取敌人的位置
                float enemyX = enemyShip.getX();
                float enemyY = enemyShip.getY();

                // 计算飞船需要旋转的角度
                float angle = MathUtils.atan2(enemyY - getY() - 100, enemyX - getX()) * MathUtils.radiansToDegrees;
                float rotate = CoordinateUtil.rotateVertical(enemyX, enemyY, getX(), getY() - Constants.CELL_HIGHT);
                if (tmp) {
                    // 旋转飞船
                    setRotation(rotate);
                    tmp = false;
                } else {
                    setRotation(0f);
                    tmp = true;
                }
                float startX = getX() - 15f;
                float startY = getY() - Constants.CELL_HIGHT;
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
                Controllers.getInstance().bulletController().fire(getStage(), getX(), getY() - Constants.CELL_HIGHT + 8f, xMoveCM, yMoveCM, true, rotate);
//                Controllers.getInstance().bulletController().fire( getStage(), (getX() + getWidth()/2f) - Bullet.width/2, getTop() - 5f, 0, 0, true ,1);
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
//            System.out.println("最近的:" + closestEnemy.getName());
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
