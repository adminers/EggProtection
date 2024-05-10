package com.rondeo.pixwarsspace.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.Main;
import com.rondeo.pixwarsspace.gamescreen.card.CardRectangleActor;
import com.rondeo.pixwarsspace.gamescreen.cells.CardImageButton;
import com.rondeo.pixwarsspace.gamescreen.cells.CellTable;
import com.rondeo.pixwarsspace.gamescreen.cells.po.ButtonImage;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.HudManager;
import com.rondeo.pixwarsspace.gamescreen.components.LevelManager;
import com.rondeo.pixwarsspace.gamescreen.components.Outbound;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.MonsterFactoryController;
import com.rondeo.pixwarsspace.gamescreen.components.entity.Ship;
import com.rondeo.pixwarsspace.gamescreen.components.play.CloudShip;
import com.rondeo.pixwarsspace.gamescreen.plate.PlateBlockButton;
import com.rondeo.pixwarsspace.gamescreen.ui.PowerShow;
import com.rondeo.pixwarsspace.gamescreen.ui.UIManager;
import com.rondeo.pixwarsspace.t1.GridBoard;
import com.rondeo.pixwarsspace.t1.Tank;
import com.rondeo.pixwarsspace.utils.Background;
import com.rondeo.pixwarsspace.utils.SoundController;

import java.util.List;

public class GameScreen extends ScreenAdapter /*implements InputProcessor */{
    //private Main main;
    private Stage stage;
    private OrthographicCamera camera;
    private InputMultiplexer inputMultiplexer;

    private TextureAtlas assets;
    private World<Entity> world;
    private Outbound[] outbound = new Outbound[4];
    private Background background;
    private Ship ship;

    private HudManager hudManager;

    private UIManager uiManager;

    private boolean levelComplete = true;

    private MonsterFactoryController monsterFactoryController;

    public GameScreen( Main main ) {
        //this.main = main;
        stage = new Stage( new FitViewport( main.width, main.height, camera = new OrthographicCamera() ) );
        assets = new TextureAtlas( Gdx.files.internal( "assets.atlas" ) );
        
        world = new World<Entity>( 4f );
        //world.setTileMode( false );

        ship = new Ship( world );
//        ship.setRegions( assets.findRegion( "ship" ), assets.findRegion( "wing" ), assets.findRegion( "ship_sketch" ), assets.findRegion( "wing_sketch" ), assets.findRegions( "thrusters" ), assets.findRegion( "effect" ) );
        background = new Background( main.width, main.height, ship );
//        background.setShip(ship);



        Controllers.getInstance().init( world, camera, assets, ship );
        outbound[0] = new Outbound( world, 0, main.height + 1, main.width, 10 );
        outbound[1] = new Outbound( world, 0, -11, main.width, 10 );
        outbound[2] = new Outbound( world, -11, 0, 10, main.height );
        outbound[3] = new Outbound( world, main.width + 1, 0, 10, main.height );

        //--创建地图--------------------------------------------------------------------------------
//        BrickShip brickShip = new BrickShip(world, 0, 0);
//        TextureAtlas brickShipAssets = new TextureAtlas( Gdx.files.internal( "lib/t_map/map.atlas" ) );
//        brickShip.setRegions( brickShipAssets.findRegion( "block_1" ), assets.findRegion( "wing" ), assets.findRegion( "ship_sketch" ), assets.findRegion( "wing_sketch" ), assets.findRegions( "thrusters" ), assets.findRegion( "effect" ) );

        // ----------------------------------------------------------------------------------------------
        GridBoard gridBoard = new GridBoard(10, 10, 20f);

        Texture tankTexture = new Texture(Gdx.files.internal("bg/topImage.png"));
        Tank tank = new Tank(16, tankTexture);
        gridBoard.addTank(0, 0, tank); // 将坦克放置在第一行第一列
        gridBoard.setShip(ship);
        // ----------------------------------------------------------------------------------------------

        Texture cellTexture = new Texture( Gdx.files.internal( "bg/cell.jpg" ) );
        TextureRegion cellTextureRegion = new TextureRegion( cellTexture, 0, 0, 20, 20 );


        Texture cellTextureY = new Texture( Gdx.files.internal( "bg/cell_y.jpg" ) );
        TextureRegion cellTextureRegionY = new TextureRegion( cellTextureY, 0, 0, 20, 20 );
        int i = 0;

        // --创建卡牌背景-----------------------------------------------------------------------------
        CardRectangleActor cardRectangleActor = new CardRectangleActor();
        cardRectangleActor.setPosition(0, 0); // 设置方形的位置
        cardRectangleActor.setSize(Gdx.graphics.getWidth(), 110); // 设置方形的宽度和高度

        // -----------------------------------------------------------------------------------------
        // --创建卡牌按钮-----------------------------------------------------------------------------
        List<CardImageButton> cards = LevelManager.createCard();
        // -----------------------------------------------------------------------------------------
        // --创建卡牌按钮-----------------------------------------------------------------------------
        List<PlateBlockButton> plates = LevelManager.createPlate();
        // -----------------------------------------------------------------------------------------




        CellTable cellTable = null;
//        cellTable = new CellTable(world, assets);

        stage.addActor( background);
        stage.addActor( ship );
//        stage.addActor( brickShip );
//        stage.addActor( gridBoard );
//        stage.addActor( tank );


//        stage.addActor( cellTable.getTable() );
        stage.addActor(cardRectangleActor);
        cards.forEach(button -> stage.addActor( button ));

        CloudShip cloudShip = createCloudShip();
        stage.addActor( cloudShip);

        // 草坪
        plates.forEach(button -> stage.addActor( button ));
        stage.addActor( Controllers.getInstance().enemyController() );
//        stage.addActor( Controllers.getInstance().snakeEnemyController() );
        stage.addActor( Controllers.getInstance().getPlayController() );
        stage.addActor( Controllers.getInstance().getBrickController() );

        hudManager = new HudManager( main, assets, world, cellTable);

        monsterFactoryController = Controllers.getInstance().getMonsterFactoryController();
        monsterFactoryController.setHudManager(hudManager);
        stage.addActor(monsterFactoryController);
        stage.addActor( Controllers.getInstance().bossController());


        // 创建卡牌下面的数字
        for (CardImageButton card : cards) {
            ButtonImage buttonImage = card.getButtonImage();
            new PowerShow(stage, buttonImage);
        }

        Controllers.getInstance().bossController().setup();


        // --创建弹框-----------------------------------------------------------------------------
        uiManager = new UIManager(hudManager, stage, new Skin( Gdx.files.internal( "ui/default.json" ) ));
        // -----------------------------------------------------------------------------------------

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor( ship );
        inputMultiplexer.addProcessor( stage );
        inputMultiplexer.addProcessor( hudManager.hud );
//        inputMultiplexer.addProcessor( e1 );
//        inputMultiplexer.addProcessor( e10 );
//        inputMultiplexer.addProcessor(myInputProcessor );
//        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor( inputMultiplexer );



//        stage.getViewport().setScreenPosition(0, Gdx.graphics.getHeight() - stage.getViewport().getScreenHeight());
//        stage.getViewport().setScreenY(Gdx.graphics.getHeight() - stage.getViewport().getScreenHeight());
    }

    private CloudShip createCloudShip() {

        TextureAtlas cloudTextureAtlas = new TextureAtlas(Gdx.files.internal("lib/Cloud/Cloud.atlas"));
        CloudShip cloudShip = new CloudShip(world, cloudTextureAtlas.findRegions("C2010"), null);
        cloudShip.init(null, -50, 40);
        cloudShip.setName("踩云");
        return cloudShip;
    }

    @Override
    public void render( float delta ) {
        super.render( delta );

        stage.getViewport().apply();
        ScreenUtils.clear( Color.valueOf( "#2f033b" ) );
        
        if( Controllers.getInstance().tutorial >= 0 ) {
            hudManager.showTutorial();
            Controllers.getInstance().pause = true;
        }
        if( Controllers.getInstance().credits == 1 ) {
//            hudManager.showCredits();
            Controllers.getInstance().credits = 2;
        }
        if( !Controllers.getInstance().pause ) {
            if( !Controllers.getInstance().gameOver ) {
                stage.act();
            } else {
                stage.act( .0003f );
                hudManager.gameOver();
            }
        }
        stage.draw();

        hudManager.update( ship.life );

        Controllers.getInstance().act( stage, delta );

        if( Gdx.input.isKeyJustPressed( Keys.P ) ) {
            Controllers.getInstance().pause = !Controllers.getInstance().pause;
        }

        if( Gdx.input.isKeyPressed( Keys.L ) ) {
            System.out.println( world.countItems() + "<>" + world.countCells() + " = " + Gdx.graphics.getFramesPerSecond() );
        }

        levelComplete();
    }

    private void levelComplete() {

        if (Controllers.getInstance().gameOver) {
            return;
        }
        // 在这里更新游戏逻辑，检查关卡完成条件是否满足
        if (monsterFactoryController.isLevelComplete()) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    uiManager.showComplexDialog();
                }
            }, 1.0f);

            /*Action delayedAction = Actions.run(() -> {
                System.out.println("time:" + (System.currentTimeMillis() / 1000) + ",执行something");
            });

            // 延迟1s后执行delayedAction
            Action action = Actions.delay(1f, delayedAction);
            stage.addAction(action);*/

//            Controllers.getInstance().pause = true;

            // 确保只显示一次
            levelComplete = false;
            monsterFactoryController.setLevelComplete(false);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        
        stage.getViewport().update( width, height, true );
        hudManager.hud.getViewport().update( width, height, true );
        hudManager.updateSize( width, height );
        outbound[0].update( 0, height + 1, width, 10 );
        outbound[1].update( 0, -11, width, 10 );
        outbound[2].update( -11, 0, 10, height );
        outbound[3].update( width + 1, 0, 10,height );

        // 重新设置 Stage 在屏幕上的位置，保持顶部贴紧屏幕顶部
//        stage.getViewport().setScreenPosition(0, Gdx.graphics.getHeight() - stage.getViewport().getScreenHeight());
        //background.setBounds( 0, 0, width, height );
//        stage.getViewport().setScreenY(Gdx.graphics.getHeight() - stage.getViewport().getScreenHeight());
    }


    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        background.dispose();
        ship.dispose();
        Controllers.getInstance().dispose();
        assets.dispose();
        hudManager.dispose();
        SoundController.getInstance().dispose();
    }

//    /**
//     * Called when a key was pressed
//     *
//     * @param keycode one of the constants in {@link Input.Keys}
//     * @return whether the input was processed
//     */
//    @Override
//    public boolean keyDown(int keycode) {
//        return false;
//    }
//
//    /**
//     * Called when a key was released
//     *
//     * @param keycode one of the constants in {@link Input.Keys}
//     * @return whether the input was processed
//     */
//    @Override
//    public boolean keyUp(int keycode) {
//        return false;
//    }
//
//    /**
//     * Called when a key was typed
//     *
//     * @param character The character
//     * @return whether the input was processed
//     */
//    @Override
//    public boolean keyTyped(char character) {
//        return false;
//    }
//
//    /**
//     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on iOS.
//     *
//     * @param screenX The x coordinate, origin is in the upper left corner
//     * @param screenY The y coordinate, origin is in the upper left corner
//     * @param pointer the pointer for the event.
//     * @param button  the button
//     * @return whether the input was processed
//     */
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        System.out.println("我擦(" +screenX + ")," + screenY + ") ");
//
//        return false;
//    }
//
//    /**
//     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on iOS.
//     *
//     * @param screenX
//     * @param screenY
//     * @param pointer the pointer for the event.
//     * @param button  the button
//     * @return whether the input was processed
//     */
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        return false;
//    }
//
//    /**
//     * Called when a finger or the mouse was dragged.
//     *
//     * @param screenX
//     * @param screenY
//     * @param pointer the pointer for the event.
//     * @return whether the input was processed
//     */
//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        return false;
//    }
//
//    /**
//     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
//     *
//     * @param screenX
//     * @param screenY
//     * @return whether the input was processed
//     */
//    @Override
//    public boolean mouseMoved(int screenX, int screenY) {
//        return false;
//    }
//
//    /**
//     * Called when the mouse wheel was scrolled. Will not be called on iOS.
//     *
//     * @param amountX the horizontal scroll amount, negative or positive depending on the direction the wheel was scrolled.
//     * @param amountY the vertical scroll amount, negative or positive depending on the direction the wheel was scrolled.
//     * @return whether the input was processed.
//     */
//    @Override
//    public boolean scrolled(float amountX, float amountY) {
//        return false;
//    }
}
