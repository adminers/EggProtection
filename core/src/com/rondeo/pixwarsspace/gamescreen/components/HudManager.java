package com.rondeo.pixwarsspace.gamescreen.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.Main;
import com.rondeo.pixwarsspace.gamescreen.GameScreen;
import com.rondeo.pixwarsspace.gamescreen.cells.BreathingEffect;
import com.rondeo.pixwarsspace.gamescreen.cells.CellTable;
import com.rondeo.pixwarsspace.gamescreen.ui.HealthBar;
import com.rondeo.pixwarsspace.gamescreen.ui.HealthBarActor;
import com.rondeo.pixwarsspace.menuscreen.MenuScreen;
import com.rondeo.pixwarsspace.utils.Constants;
import com.rondeo.pixwarsspace.utils.Rumble;
import com.rondeo.pixwarsspace.utils.SoundController;

import java.util.Random;

public class HudManager implements Disposable {
    public Stage hud;
    Skin skin;
    Table table, credits;
    TextureAtlas assets;
    TutorialManager tutorialManager;
    WarningManager warningManager;
    int tutType = -1;
    TextButton restartButton;
    Label lifeLabel, gameOverLabel, dialogLabel;

    Label toolLabel;

    Window lifeWindow;

    Window toolWindow;
    private Main main;

    private World world;
    private CellTable cellTable;

    private HealthBarActor healthBarActor;


    public HudManager(final Main main, TextureAtlas assets , World world, CellTable cellTable ) {
        this.assets = assets;
        this.main = main;
        this.cellTable = cellTable;

        hud = new Stage( new ExtendViewport( main.width*1.5f , main.height*1.5f ) );
        skin = new Skin( Gdx.files.internal( "ui/default.json" ) );

        gameOverLabel = new Label("Game\nOver", skin, "big");
        gameOverLabel.setVisible( false );

        restartButton = new TextButton( "RESTART", skin );
        restartButton.setVisible( false );
        restartButton.padLeft( 50 );
        restartButton.padRight( 50 );
        restartButton.addListener( new InputListener() {
            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
                SoundController.getInstance().playClick();
                return true;
            };
            public void touchUp( InputEvent event, float x, float y, int pointer, int button ) {
                Controllers.getInstance().gameOver = false;
                main.setScreen( new GameScreen( main ) );
            };
        } );

        dialogLabel = new Label( "", skin );
        //Window dialogWindow = new Window( "", skin.get( "window_green", WindowStyle.class ) );
        //dialogWindow.add( dialogLabel ).growX();

        lifeLabel = new Label( "SHIP INT", skin.get( "big_32", LabelStyle.class ) );
        lifeWindow = new Window( "", skin/*.get( "window_green", WindowStyle.class )*/ );
        lifeWindow.add( lifeLabel );

        LabelStyle big32 = skin.get("big_32", LabelStyle.class);
        big32.font.getData().setScale(0.5f);
        toolLabel = new Label( "LEVEL 1", big32);
        Skin skin22 = new Skin();

        // 添加窗口样式到 Skin 对象
//        Window.WindowStyle windowStyle = new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new Texture("bg/cellBlue.jpg")));
//        Window.WindowStyle windowStyle = new Window.WindowStyle();
//        skin22.add("default", windowStyle);

        // 创建一个透明的 Pixmap
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0); // 设置颜色为透明
        pixmap.fill(); // 填充整个 Pixmap 区域为透明

        // 创建一个透明的窗口
        TextureRegionDrawable background = new TextureRegionDrawable(new Texture(pixmap));
        Window.WindowStyle windowStyle = new Window.WindowStyle(big32.font, Color.WHITE, null);
//        windowStyle.background = null; // 设置窗口背景为空，即透明
        skin22.add("default", windowStyle);
        toolWindow = new Window( "", skin22/*skin.get( "window_red", WindowStyle.class ) */);
        toolWindow.add( toolLabel );

        //----------------------------------------------------------------

        Texture cellTexture = new Texture( Gdx.files.internal( "bg/cell.jpg" ) );
        TextureRegion cellTextureRegion = new TextureRegion( cellTexture, 0, 0, 20, 20 );
        int i = 0;

        final ImageTextButton shenButton = new ImageTextButton( "SHEN TOOL", skin );
//        shenButton.add("Click Me");
//        shenButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // 按钮点击事件处理逻辑
//                System.out.println("Button clicked!");
//            }
//        });
//        shenButton.addListener( new InputListener() {
//            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
//                // 按钮点击事件处理逻辑
//                System.out.println("Button clicked!11");
//                return true;
//            };
//            public void touchUp( InputEvent event, float x, float y, int pointer, int button ) {
//                // 按钮点击事件处理逻辑
//                System.out.println("Button clicked!22");
//            };
//        } );
        /*shenButton.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                shenButton.moveBy(x - shenButton.getWidth() / 2, y - shenButton.getHeight() / 2);
            }
        });*/


        // 创建一个 Texture 对象，用于加载按钮的纹理
        Texture buttonTexture = new Texture("bg/cell_y.jpg");

        // 创建一个 TextureRegionDrawable 对象，用于显示按钮的纹理
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(buttonTexture);

        // 创建 ImageButton 对象，设置按钮的纹理
        ImageButton button = new ImageButton(buttonDrawable);
        button.addListener( new InputListener() {
            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
//                // 按钮点击事件处理逻辑
                System.out.println("Button clicked!11");

                Constants.CREATE_CELL_TYPE = "ship";

                int[][] indexs = Rumble.randomIndexs(3);
                for (int[] index : indexs) {
                    ImageButton cell = cellTable.getImageButton(index[0], index[1]);
                    BreathingEffect.applyBreathingEffect(cell.getImage(), 1.2f);

                    // 延迟一定时间后停止呼吸式抖动效果
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            BreathingEffect.stopBreathingEffect(cell.getImage());
                        }
                    }, 5.0f); // 2秒后停止呼吸式抖动效果
                }

                /*// 更新表格中第一行第二列格子的对象
                Texture cellTexture = new Texture( Gdx.files.internal( "bg/cell_y.jpg" ) );
                SquareShip ship = new SquareShip( world );
                ship.setRegions( assets.findRegion( "ship" ), assets.findRegion( "wing" ), assets.findRegion( "ship_sketch" ), assets.findRegion( "wing_sketch" ), assets.findRegions( "thrusters" ), assets.findRegion( "effect" ) );
                BreathingEffect.applyBreathingEffect(ship, 1.2f);
                cellTable.updateCell(0, 4, ship);

                // 延迟一定时间后停止呼吸式抖动效果
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        BreathingEffect.stopBreathingEffect(ship);
                    }
                }, 2.0f); // 2秒后停止呼吸式抖动效果*/
                return true;
            };
        });
//        toolWindow.add(button);

        //----------------------------------------------------------------
        // -----血条---------------------------------------------------------------------------------
        healthBarActor = new HealthBarActor(new HealthBar(100));
        // -----------------------------------------------------------------------------------------


        // 创建一个按钮
        createButton();

        table = new Table();
        table.setFillParent(true);
        //table.setVisible( false );





        table.setDebug(true);
        
        //table.add( dialogWindow ).growX();
       /*
       table.setPosition(x, Gdx.graphics.getHeight()); // 设置 Table 的起始位置

       // 计算 Table 的起始坐标位置（屏幕中心）
        float x = (Gdx.graphics.getWidth() - table.getWidth()) / 2;
        float y = (Gdx.graphics.getHeight() - table.getHeight()) / 2;
        System.out.println("屏幕(" + x + "," + y + ")");
         table.setWidth(200); // 设置 Table 的宽度
        table.setHeight(100); // 设置 Table 的高度
        Label label1 = new Label("Label 1", skin);
        Label label2 = new Label("Label 2", skin);
        Label label3 = new Label("Label 3", skin);
        Label label4 = new Label("Label 4", skin);
        Label label5 = new Label("Label 5", skin);
        Label label6 = new Label("Label 6", skin);
        table.add(label1).expandX().fillX(); // 第一行第一列
        table.add(label2).expandX().fillX(); // 第一行第二列
        table.add(label3).expandX().fillX(); // 第一行第三列
        table.row(); // 换行
        table.add(label4).expandX().fillX(); // 第二行第一列
        table.add(label5).expandX().fillX(); // 第二行第二列
        table.add(label6).expandX().fillX(); // 第二行第三列
        table.row(); // 换行
        table.add().expandY();
        table.add( lifeWindow );
        table.row();*/


//        table.add( lifeWindow );
        // 创建一个占位符
        Actor placeholder = new Image(); // 您也可以使用其他空的 Actor

        // 设置占位符的高度
        float placeholderHeight = 20f;
//        placeholder.setHeight(placeholderHeight);

        // 调整占位符的上下边距
        table.add(placeholder).height(placeholderHeight)/*.padTop(20f).padBottom(20f)*/;
        table.row();
        table.add( toolWindow );
        table.row();

        // 将血条添加到 Table 中
        table.add(healthBarActor).width(100).height(10);

        table.row();
        table.add().expandY();

        table.row();
        table.add( gameOverLabel );

        table.row().padTop( 100 );
        table.add( restartButton );

        table.row();
        table.add().expandY();


        hud.addActor( table );

        tutorialManager = new TutorialManager();
        warningManager = new WarningManager();


        credits = new Table();
        credits.setFillParent(true);
        credits.setVisible( false );

        Label label = new Label("Thanks For Playing!", skin);
        credits.add(label);

        credits.row();
        label = new Label("Libgdx\n+\nJBUMP", skin, "big");
        label.setAlignment( Align.center );
        credits.add(label).spaceTop(40.0f).spaceBottom(40.0f);

        credits.row();
        label = new Label(
            "I would like to give thanks\n"+
            "to these people who helped\n"+
            "me during the creation\n"+
            "of this game:\n\n\n"+
            "Libgdx Community\n\n"+
            "OpenGameArt\n\n"+
            "Other Free Software\n\n"+
            "Erwin Magno (Playtest)\n\n"+
            "JGRAN\n\n"+
            "and also You!",
                skin );
        label.setAlignment( Align.center );
        credits.add(label);
        hud.addActor(credits);
    }

    private void createButton() {

        // 创建一个 Texture 对象，用于加载按钮的纹理
        Texture buttonTexture = new Texture("bg/button_easy.png");

        // 创建一个 TextureRegionDrawable 对象，用于显示按钮的纹理
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(buttonTexture);

        // 创建 ImageButton 对象，设置按钮的纹理
        ImageButton button = new ImageButton(buttonDrawable);
        button.addListener( new InputListener() {
            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
//                // 按钮点击事件处理逻辑
                System.out.println("Button clicked!11");
                Constants.CREATE_CELL_TYPE = "alien";

                int[][] indexs = Rumble.randomIndexs(3);
                for (int[] index : indexs) {
                    ImageButton cell = cellTable.getImageButton(index[0], index[1]);
                    BreathingEffect.applyBreathingEffect(cell.getImage(), 1.2f);

                    // 延迟一定时间后停止呼吸式抖动效果
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            BreathingEffect.stopBreathingEffect(cell.getImage());
                        }
                    }, 5.0f); // 2秒后停止呼吸式抖动效果
                }

                /*// 更新表格中第一行第二列格子的对象
                Texture cellTexture = new Texture( Gdx.files.internal( "bg/cell_y.jpg" ) );
                SquareShip ship = new SquareShip( world );
                ship.setRegions( assets.findRegion( "ship" ), assets.findRegion( "wing" ), assets.findRegion( "ship_sketch" ), assets.findRegion( "wing_sketch" ), assets.findRegions( "thrusters" ), assets.findRegion( "effect" ) );
                BreathingEffect.applyBreathingEffect(ship, 1.2f);
                cellTable.updateCell(0, 4, ship);

                // 延迟一定时间后停止呼吸式抖动效果
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        BreathingEffect.stopBreathingEffect(ship);
                    }
                }, 2.0f); // 2秒后停止呼吸式抖动效果*/
                return true;
            };
        });
//        toolWindow.add(button);

        //----------------------------------------------------------------
    }

    private String lifeToBars( int life ) {
        String val = "X";
        switch( life ) {
            case 1:
                val = "|";
                break;
            case 2:
                val = "||";
                break;
            case 3:
                val = "|||";
                break;
            case 4:
                val = "||||";
                break;
            case 5:
                val = "|||||";
                break;
            case 6:
                val = "||||||";
                break;
            case 7:
                val = "|||||||";
                break;
            case 8:
                val = "||||||||";
                break;
            case 9:
                val = "|||||||||";
                break;
            case 10:
                val = "||||||||||";
                break;
        }
        return val;
    }
    Random random = new Random();
    int ccc = 0;

    public void update( int life ) {
        hud.getViewport().apply();
        hud.act();
        hud.draw();

        lifeLabel.setText( "SHIP INT: " + lifeToBars( life ) );
        toolLabel.setText("LEVEL " + ccc);
        if (ccc >= 100) {
            ccc = 0;
        }

          // 更新血条
        healthBarActor.setCurrentHealth( ccc++); // 假设playerHealth是玩家当前的血量

//        updateTablePosition();



        if( !Controllers.getInstance().bossController().boss && System.currentTimeMillis() > Controllers.getInstance().bossController().timeStart ) {
            warningManager.show();
        }
    }

    private void updateTablePosition() {
        float x = (Gdx.graphics.getWidth() - table.getWidth()) / 2;
        float y = (Gdx.graphics.getHeight() - table.getHeight()) / 2;

        // 确保 Table 在游戏屏幕内
        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - table.getWidth()));
        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - table.getHeight()));

        table.setPosition(100, 100);
    }

    public void showCredits() {
        lifeWindow.setVisible( false );
        toolWindow.setVisible( false );
        credits.addAction( Actions.sequence(
            Actions.moveTo( credits.getX(), - table.getHeight() ),
            Actions.visible( true ),
            Actions.moveTo( credits.getX(), table.getHeight(), 30f ),
            new Action() {
                @Override
                public boolean act( float delta ) {
                    main.setScreen( new MenuScreen( main ) );
                    return true;
                }
            }
        ) );
    }

    public void gameOver() {
        //table.setVisible( true );
        gameOverLabel.setVisible( true );
        restartButton.setVisible( true );
    }

    public void showTutorial() {
        tutorialManager.show( Controllers.getInstance().tutorial );
    }

    public void showWarning() {
        warningManager.show();
    }

    public void updateSize( float width, float height ) {
        tutorialManager.updateSize( hud.getWidth(), hud.getHeight() );
        warningManager.updateSize( hud.getWidth(), hud.getHeight() );
    }

    @Override
    public void dispose() {
        hud.dispose();
        skin.dispose();
    }

    private class WarningManager {
        Window window;
        boolean showing = false;

        public WarningManager() {
            window = new Window( "", skin.get( "window_red", WindowStyle.class ) );
            window.pad( 15f );
            window.setVisible( false );
            updateSize( hud.getWidth(), hud.getHeight() );

            Label warninLabel = new Label( "BOSS", skin.get( "big", LabelStyle.class ) );
            window.add( warninLabel );

            hud.addActor( window );
        }

        public void show() {
            if( showing )
                return;
            window.setVisible( true );
            window.addAction(
                Actions.sequence( 
                    Actions.repeat( 4, Actions.sequence( Actions.visible( true ), Actions.delay( .8f ), Actions.visible( false ), Actions.delay( .2f ) ) ),
                    Actions.visible( false )
                )
            );
            SoundController.play( SoundController.getInstance().summon );
            showing = true;
        }

        public void updateSize( float width, float height ) {
            window.setModal( true );
            window.setSize( width, 200 );
            window.setPosition( 0, height/2f - window.getHeight()/2f );
        }
    }

    private class TutorialManager {

        public String[] tutTitle = {
            "Power Up - Wing",
            "Power Up - Shield",
            "Power Up - Health",
            "Power Up - Halo"
        };
        public String[] tutString = {
            "Shoots two bullets in each\n" + 
                "fire instead of one",
            "Invulnerable to bullets",
            "Increases health by one",
            "Can tank, invulnerable to\n" +
                "other enemy ship when crashed.\n" +
                "But vulnerable to bullets"
        };
        public String[] tutPreview = {
            "wing",
            "ship_sketch",
            "ship_sketch",
            "effect"
        };

        Image ship, preview, img;
        Label title, message;
        Window window;
        public TutorialManager() {
            window = new Window( "", skin );
            window.pad( 15f );
            window.setVisible( false );
            updateSize( hud.getWidth(), hud.getHeight() );

            img = new Image();
            ship = new Image( assets.findRegion( "ship" ) );
            preview = new Image();

            title = new Label( "", skin );
            title.setAlignment( Align.center );
            message = new Label( "", skin );
            message.setAlignment( Align.center );

            TextButton closeButton = new TextButton( "GOT IT", skin );
            closeButton.addListener( new InputListener() {
                public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
                    SoundController.getInstance().playClick();
                    return true;
                };
                public void touchUp( InputEvent event, float x, float y, int pointer, int button ) {
                    Controllers.getInstance().pause = false;
                    window.setVisible( false );
                    Controllers.getInstance().powerUp( tutType );
                };
            } );

            Stack stack = new Stack();
            stack.add( ship );
            stack.add( preview );

            window.add( title );
            window.row().padTop( 20 );
            window.add( img ).size( 30 );
            window.row().padBottom( 20 );
            window.add( stack ).size( 60 );
            window.row();
            window.add( message );
            window.row();
            window.add().expandY();
            window.row();
            window.add( closeButton );

            hud.addActor( window );
        }

        public void show( int tut ) {
            tutType = tut;
            title.setText( tutTitle[tut] );
            message.setText( tutString[tut] );
            img.setDrawable( new TextureRegionDrawable( assets.findRegions( "powerups" ).get(tut) ) );
            preview.setDrawable( new TextureRegionDrawable( assets.findRegion( tutPreview[tut] ) ) );
            window.setVisible( true );
            window.setPosition( 0, hud.getHeight()/2f - window.getHeight()/2f );
            Controllers.getInstance().tutorial = -1;
        }

        public void updateSize( float width, float height ) {
            window.setModal( true );
            window.setSize( width, 320 );
            window.setPosition( 0, height/2f - window.getHeight()/2f );
        }
    }
}
