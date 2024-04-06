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
import com.rondeo.pixwarsspace.gamescreen.cells.po.ButtonImage;
import com.rondeo.pixwarsspace.gamescreen.components.*;
import com.rondeo.pixwarsspace.gamescreen.components.play.BubblesShip;
import com.rondeo.pixwarsspace.gamescreen.components.play.PlayShip;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.Random;

public class PlayController extends Actor implements Entity, Disposable {

    World<Entity> world;
    Array<AtlasRegion> explosionRegions;
    private final Pool<PlayShip> playShipPool = new Pool<PlayShip>() {
        @Override
        protected PlayShip newObject() {
            return new PlayShip( world, explosionRegions );

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

    public PlayController(World<Entity> world, Array<AtlasRegion> explosionRegions, int regionLength, AtlasRegion... enemyRegion ) {
//        textureAtlas = new TextureAtlas(Gdx.files.internal("lib/t_map/play/play.atlas"));
        textureAtlas = new TextureAtlas(Gdx.files.internal("lib/t_map/play/play.atlas"));
        this.world = world;
        this.explosionRegions = textureAtlas.findRegions( "idle" );
        this.enemyRegion = enemyRegion;
        this.regionLength = regionLength;
    }

    PlayShip playShip;

    public PlayShip deploy(int c) {

        Stage stage = getStage();
        ButtonImage buttonImage = Constants.PLATES.get(LevelManager.getCurrentIndexLevel()).get(c);
        Axis level = buttonImage.getAxis();
        activeEnemiesLength++;

        // Deploy first
        playShip = playShipPool.obtain();
        stage.addActor(playShip);
        AtlasRegion shipRegion = enemyRegion[choosenRegionIndex];
        playShip.init(shipRegion, patterns_1[ choosenPatternIndex ] , level.getX(), level.getY());

        BubblesShip bubblesShip = new BubblesShip(world);
        bubblesShip.init(level.getX(), level.getY());
        stage.addActor(bubblesShip);

        playShip.setName("玩家(" + c + ")");
        System.out.println("创建玩家: " + c + "|" + level.getX() + "|" + level.getY());
        Constants.ACTIVE_PLAYERS.add(playShip);
        return playShip;
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
        deploySequence.addAction( Actions.delay( 0.3f + 0.1f * 1 ) );
        deploySequence.addAction(new Action() {

            @Override
            public boolean act(float delta) {
                deploy(0);
                return true;
            }
        });

        // 添加一个 RunnableAction 作为最后一个动作，用于执行其他代码
        RunnableAction finishAction = Actions.run(() -> {
            // 在这里执行其他代码，表示前面的所有延迟动作都已完成
            System.out.println("所有延迟玩家都已加载");
//            LevelManager.goToNextLevel();
        });
        deploySequence.addAction(finishAction);
        return deploySequence;
    }

    long time;

    int count = 0;

    @Override
    public void act( float delta ) {
        super.act(delta);
        if (count >= 1) {
            return;
        }
        count++;

//        Constants.LEVEL1_ENEMYS.get(LevelManager.getCurrentLevel())
        boolean endLevel = LevelManager.isEndLevel();
        if (endLevel) {
        } else {
            if ( /*activeEnemies.size*/Constants.ACTIVE_PLAYERS.isEmpty() && !Controllers.getInstance().bossController().dead ) {
                if( System.currentTimeMillis() > time + 5000 ) {
    //                if( count < 10 ) {
    //                    count +=2;
    //                }
//                    addAction( deployShips() );

                    time = System.currentTimeMillis();
                }
            }
        }
    }

    public void forceFree( PlayShip item ) {
        playShipPool.free( item );
        item.dispose();
        Constants.ACTIVE_PLAYERS.removeValue( item, false );
        activeEnemiesLength --;
    }

    public void freeAll() {
        
    }

    @Override
    public void dispose() {
        for ( Player player : Constants.ACTIVE_PLAYERS) {
            player.dispose();
        }
    }
    
}
