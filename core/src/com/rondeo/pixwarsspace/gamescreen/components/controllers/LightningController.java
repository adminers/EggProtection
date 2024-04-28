package com.rondeo.pixwarsspace.gamescreen.components.controllers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.Enemy;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.entity.LightningBullet;

public class LightningController implements Disposable {

    World<Entity> world;
    
    AtlasRegion[] bulletRegion;

    public LightningController(World<Entity> world, AtlasRegion... bulletRegion) {
        this.world = world;
        this.bulletRegion = bulletRegion;
    }

    Array<LightningBullet> activeBullets = new Array<>();

    private final Pool<LightningBullet> bulletPool = new Pool<LightningBullet>() {
        @Override
        protected LightningBullet newObject() {
            return new LightningBullet(world, -10, -10, bulletRegion);
        }
    };

    LightningBullet bullet;

    public void fire(Stage stage, float x, float y, Axis axis, boolean top, float angle, Enemy enemyShip) {
        bullet = bulletPool.obtain();
        bullet.init(x, y, axis, top, angle, enemyShip);
        stage.addActor(bullet);
    }

    public void forceFree(LightningBullet item) {
        bulletPool.free(item);
        item.dispose();
    }

    @Override
    public void dispose() {
        for (LightningBullet shipBullet : activeBullets) {
            shipBullet.dispose();
        }
    }

}
