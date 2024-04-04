package com.rondeo.pixwarsspace.gamescreen.components.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Enemy;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.LevelManager;
import com.rondeo.pixwarsspace.gamescreen.components.entity.SnakeEnemyShip;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.Random;

public class SnakeEnemyController extends Actor implements Entity, Disposable {
    World<Entity> world;
    Array<AtlasRegion> explosionRegions;
    private final Pool<SnakeEnemyShip> enemyPool = new Pool<SnakeEnemyShip>() {
        @Override
        protected SnakeEnemyShip newObject() {
            return new SnakeEnemyShip( world, explosionRegions );

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

    private TextureAtlas textureAtlas;

    public SnakeEnemyController(World<Entity> world, Array<AtlasRegion> explosionRegions, int regionLength, AtlasRegion... enemyRegion ) {
        textureAtlas = new TextureAtlas(Gdx.files.internal("lib/barn/snake.atlas"));
        this.world = world;
        this.explosionRegions = textureAtlas.findRegions( "idle" );
        this.enemyRegion = enemyRegion;
        this.regionLength = regionLength;
    }

    SnakeEnemyShip enemyShip;

    public void deploy( Stage stage , int c) {

        Axis level = Constants.LEVEL1_ENEMYS.get(LevelManager.getCurrentIndexLevel()).get(c);
        activeEnemiesLength++;
        // Deploy first
        enemyShip = enemyPool.obtain();
        stage.addActor( enemyShip );
        AtlasRegion shipRegion = enemyRegion[choosenRegionIndex];
        enemyShip.init(shipRegion, patterns_1[ choosenPatternIndex ] , level.getX(), level.getY());
        enemyShip.setName("小虫(" + c + ")");
        System.out.println("创建敌人: " + c);
        Constants.ACTIVE_ENEMIES.add( enemyShip );
    }
    
//    public Action deployAction = new Action() {
//
//        @Override
//        public boolean act(float delta) {
//            deploy( getStage() );
//            return true;
//        }
//    };
    SequenceAction deploySequence = new SequenceAction();
    public SequenceAction deployShips() {
        deploySequence = new SequenceAction();
        choosenRegionIndex = random.nextInt( regionLength );
        choosenPatternIndex = random.nextInt( patterns_1.length );
        for (int i = 0; i < Constants.LEVEL1_ENEMYS.get(LevelManager.getCurrentIndexLevel()).size(); i ++  ) {
            int globalCount = i;
            deploySequence.addAction( Actions.delay( 0.3f + 0.1f * i ) );
            deploySequence.addAction(new Action() {

                @Override
                public boolean act(float delta) {
                    deploy(getStage(), globalCount);
                    return true;
                }
            });
        }

        // 添加一个 RunnableAction 作为最后一个动作，用于执行其他代码
        RunnableAction finishAction = Actions.run(() -> {
            // 在这里执行其他代码，表示前面的所有延迟动作都已完成
            System.out.println("所有延迟动作都已完成");
//            LevelManager.goToNextLevel();
        });
        deploySequence.addAction(finishAction);
        return deploySequence;
    }

    long time;
    int count = 1;

//    public void deployaaaa(  ) {
//
//        addAction( deployShips() );
//    }

    @Override
    public void act( float delta ) {
        super.act(delta);

//        Constants.LEVEL1_ENEMYS.get(LevelManager.getCurrentLevel())
        boolean endLevel = LevelManager.isEndLevel();
        if (endLevel) {
            System.out.println("不用创建");
        } else {
            if ( /*activeEnemies.size*/Constants.ACTIVE_ENEMIES.isEmpty() && !Controllers.getInstance().bossController().dead ) {
                if( System.currentTimeMillis() > time + 5000 ) {
    //                if( count < 10 ) {
    //                    count +=2;
    //                }
                    addAction( deployShips() );
                    time = System.currentTimeMillis();
                }
            }
        }
    }

    public void forceFree( SnakeEnemyShip item ) {
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
