package com.rondeo.pixwarsspace.gamescreen.components.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.LevelManager;
import com.rondeo.pixwarsspace.gamescreen.components.entity.BrickShip;
import com.rondeo.pixwarsspace.gamescreen.components.entity.PointShip;
import com.rondeo.pixwarsspace.gamescreen.components.entity.SlugShip;
import com.rondeo.pixwarsspace.gamescreen.components.play.YunShip;
import com.rondeo.pixwarsspace.gamescreen.pojo.CenterPoint;
import com.rondeo.pixwarsspace.gamescreen.pojo.EnemyJumpCoordinate;
import com.rondeo.pixwarsspace.gamescreen.pojo.MapPointBlock;
import com.rondeo.pixwarsspace.gamescreen.pojo.SulgPoint;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.Random;

public class BrickController extends Actor implements Entity, Disposable {
    World<Entity> world;
    Array<AtlasRegion> explosionRegions;
    private final Pool<BrickShip> enemyPool = new Pool<BrickShip>() {
        @Override
        protected BrickShip newObject() {
            return new BrickShip( world );

        }
    };

    Random random = new Random();

    AtlasRegion[] enemyRegion;
    int regionLength = 0;
    int choosenPatternIndex = 0;
    int choosenRegionIndex = 0;
    int activeEnemiesLength = 0;

    private TextureAtlas textureAtlas;

    public BrickController(World<Entity> world, Array<AtlasRegion> explosionRegions, int regionLength, AtlasRegion... enemyRegion ) {

        textureAtlas = new TextureAtlas( Gdx.files.internal( "lib/t_map/map.atlas" ) );
        this.world = world;
        this.enemyRegion = enemyRegion;
        this.regionLength = regionLength;
    }

    BrickShip brickShip;

    public void deploy( Stage stage , int c) {

        ButtonImage buttonImage = Constants.MAP_1.get(LevelManager.getCurrentLevel()).get(c);
        Axis level = buttonImage.getAxis();
        activeEnemiesLength++;
        // Deploy first
        brickShip = enemyPool.obtain();
        stage.addActor(brickShip);
        AtlasRegion shipRegion = textureAtlas.findRegion( buttonImage.getImage() );
        brickShip.init(shipRegion, level.getX(), level.getY());
        String name = buttonImage.getName();
        brickShip.setName(buttonImage.getName());
        brickShip.setZIndex(1);
        System.out.println("创建地图: " + buttonImage.getName() + ";" + buttonImage.getImage());

        CenterPoint centerPoint = Constants.createAttrPoint(level);
        Constants.CENTER_POINTS.put(name, centerPoint);
        Axis centerPointAxis = centerPoint.getAxis();
        PointShip pointShip = createPointShip(centerPointAxis.getX(), centerPointAxis.getY());
        pointShip.setName(name);
        /*if (7 == c) {
            CenterPoint centerPoint = Constants.CENTER_POINTS.get(LevelManager.getCurrentLevel()).get(c);
            Axis centerPointAxis = centerPoint.getAxis();
            EnemyJumpCoordinate attr = centerPoint.getAttr();
            createPointShip(attr.getLeft().getX(), attr.getLeft().getY());
            createPointShip(attr.getLeftTop().getX(), attr.getLeftTop().getY());
            createPointShip(attr.getLeftBottom().getX(), attr.getLeftBottom().getY());
            createPointShip(attr.getRight().getX(), attr.getRight().getY());
            createPointShip(attr.getRightTop().getX(), attr.getRightTop().getY());
            createPointShip(attr.getRightBottom().getX(), attr.getRightBottom().getY());
        }*/

        Constants.POINT_BRICK_SHIPS.put(name, new MapPointBlock(pointShip, brickShip));


        if (c == 7) {
            SlugShip slugShip = createSlugShip(centerPointAxis.getX(), centerPointAxis.getY());
            slugShip.setName(name);
            Constants.SLUGSHIP.put(name, new SulgPoint(centerPointAxis, slugShip));

            TextureAtlas yunTextureAtlas = new TextureAtlas(Gdx.files.internal("lib/t_map/yun/yun.atlas"));


            /*Array<TextureRegion> frames = new Array<TextureRegion>();
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0001.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0002.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0003.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0004.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0005.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0006.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0007.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0008.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0009.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0010.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0011.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0012.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0013.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0014.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0015.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0016.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0017.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0018.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0019.png"))));
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("lib/t_map/yun/0020.png"))));*/
//            YunShip yunShip = new YunShip(world, frames);
            YunShip yunShip = new YunShip(world, yunTextureAtlas.findRegions("fudong"), null);
            yunShip.init(null, 50, 330);
            yunShip.setName("云(" + c + ")");
            getStage().addActor(yunShip);
        }

    }

    private PointShip createPointShip(float x, float y) {

        AtlasRegion pointShipRegion = pointTextureAtlas.findRegion( "idle" );
        PointShip pointShip = new PointShip(world);
        pointShip.init(pointShipRegion, x, y);
        pointShip.setZIndex(2);
        getStage().addActor(pointShip);
        return pointShip;
    }

    private SlugShip createSlugShip(float x, float y) {

        AtlasRegion pointShipRegion = slugTextureAtlas.findRegion( "SlimeOrange" );
        SlugShip slugShip = new SlugShip(world, pointShipRegion);
        slugShip.setRegions(null,null,null,null, slugTextureAtlas.findRegions("SlimeOrange"), null);
//        slugShip.init( x, y);
        slugShip.setZIndex(2);
        getStage().addActor(slugShip);
        return slugShip;
    }
    
    SequenceAction deploySequence = new SequenceAction();
    public SequenceAction deployShips() {
        deploySequence = new SequenceAction();
        choosenRegionIndex = random.nextInt( regionLength );
        for (int i = 0; i < Constants.MAP_1.get(LevelManager.getCurrentLevel()).size(); i ++  ) {
            int globalCount = i;
            deploySequence.addAction( Actions.delay( 0.0f) );
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

            // TODO: 进入下一关有问题
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

    TextureAtlas pointTextureAtlas = new TextureAtlas( Gdx.files.internal( "lib/t_map/play/point.atlas" ) );

    TextureAtlas slugTextureAtlas = new TextureAtlas( Gdx.files.internal( "lib/t_map/t1/SlugCannon.atlas" ) );

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

    public void forceFree( BrickShip item ) {
        enemyPool.free( item );
        item.dispose();
        activeEnemiesLength --;
    }


    @Override
    public void dispose() {

    }
    
}
