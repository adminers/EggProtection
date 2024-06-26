package com.rondeo.pixwarsspace.gamescreen.components.controllers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Enemy;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.entity.EnemyShip;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.Random;

public class EnemyController extends Actor implements Entity, Disposable {
    World<Entity> world;
    Array<AtlasRegion> explosionRegions;
    private final Pool<EnemyShip> enemyPool = new Pool<EnemyShip>() {
        @Override
        protected EnemyShip newObject() {
            return new EnemyShip( world, explosionRegions );

        }
    };

    Random random = new Random();
    float[][] patterns_1 = {
        {
            .8f, 1.3f,
            .2f, .4f,
            .8f, .4f,
            .8f, .6f,
            .5f, .6f,
            .2f, .6f,
            .8f, .8f,
            .8f, 1.3f
        },
        {
            .8f, 1.3f,
            .8f, .4f,
            .2f, .4f,
            .2f, .8f,
            .5f, .8f,
            .8f, .8f,
            .8f, .6f,
            .2f, .6f,
            .2f, 1.3f
        },
        {
            -1.2f, .3f,
            .9f, .3f,
            .7f, .3f,
            .7f, .5f,
            .9f, .5f,
            .9f, 0.7f,
            .7f, .7f
        },
        {
            .5f, 1.2f,
            .2f, .9f,
            .8f, .9f,
            .2f, .5f,
            .8f, .5f,
            .2f, .7f,
            .8f, .7f,
            .5f, .3f
        }
    };
    float[][] patterns_2 = {
        {
            .2f, 1.3f,
            .8f, .5f,
            .2f, .5f,
            .2f, .7f,
            .5f, .7f,
            .8f, .7f,
            .2f, .9f,
            .2f, 1.3f
        },
        {
            .2f, 1.3f,
            .2f, .5f,
            .8f, .5f,
            .8f, .9f,
            .5f, .9f,
            .2f, .9f,
            .2f, .7f,
            .8f, .7f,
            .8f, 1.3f
        },
        {
            1.2f, .3f,
            .1f, .3f,
            .3f, .3f,
            .3f, .5f,
            .1f, .5f,
            .1f, .7f,
            .3f, .7f
        }, {
            .5f, 1.2f,
            .8f, .8f,
            .2f, .8f,
            .8f, .4f,
            .2f, .4f,
            .8f, .6f,
            .2f, .6f,
            .5f, .3f
        }
    };
    

    AtlasRegion[] enemyRegion;
    int regionLength = 0;
    int choosenPatternIndex = 0;
    int choosenRegionIndex = 0;
    int activeEnemiesLength = 0;

    public EnemyController( World<Entity> world, Array<AtlasRegion> explosionRegions, int regionLength, AtlasRegion... enemyRegion ) {
        this.world = world;
        this.explosionRegions = explosionRegions;
        this.enemyRegion = enemyRegion;
        this.regionLength = regionLength;
    }

    EnemyShip enemyShip;
    public void deploy( Stage stage ) {

        // shen
        if (activeEnemiesLength >= 0) {
            return;
        }

        // Deploy first
        enemyShip = enemyPool.obtain();
        stage.addActor( enemyShip );
        //System.out.println( patterns.get( choosenPatternIndex ) );
        enemyShip.init( enemyRegion[choosenRegionIndex], patterns_1[ choosenPatternIndex ] );
        enemyShip.setName("正经敌人1");
        Constants.ACTIVE_ENEMIES.add( enemyShip );
        activeEnemiesLength ++;

        // shen
        if (activeEnemiesLength >= 1) {
            return;
        }

        // Deploy second
        enemyShip = enemyPool.obtain();
        stage.addActor( enemyShip );
        enemyShip.init( enemyRegion[choosenRegionIndex], patterns_2[ choosenPatternIndex ] );
        Constants.ACTIVE_ENEMIES.add( enemyShip );
        activeEnemiesLength ++;
    }
    
    public Action deployAction = new Action() {
        @Override
        public boolean act(float delta) {
            deploy( getStage() );
            return true;
        }
    };
    SequenceAction deploySequence = new SequenceAction();
    public SequenceAction deployShips() {
        deploySequence = new SequenceAction();
        choosenRegionIndex = random.nextInt( regionLength );
        choosenPatternIndex = random.nextInt( patterns_1.length );
        for( int i = 0; i < count; i ++ ) {
            deploySequence.addAction( Actions.delay( .3f ) );
            deploySequence.addAction( deployAction );
        }
        return deploySequence;
    }

    long time;
    int count = 0;
    @Override
    public void act( float delta ) {
        super.act(delta);

        if( /*activeEnemies.size*/activeEnemiesLength < 5 && !Controllers.getInstance().bossController().dead ) {
            if( System.currentTimeMillis() > time + 5000 ) {
                if( count < 10 ) {
                    count +=2;
                }
                addAction( deployShips() );
                time = System.currentTimeMillis();
            }
        }
    }

    public void forceFree( EnemyShip item ) {
        enemyPool.free( item );
        item.dispose();
        Constants.ACTIVE_ENEMIES.removeValue( item, false );
        activeEnemiesLength --;
    }

    public void freeAll() {
        
    }

    @Override
    public void dispose() {
        for ( Enemy enemyShip : Constants.ACTIVE_ENEMIES) {
            enemyShip.dispose();
        }
    }
    
}
