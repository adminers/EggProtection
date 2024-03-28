package com.qiaweidata.starriverdefense.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dongbat.jbump.World;
import com.qiaweidata.starriverdefense.ShenMain;
import com.qiaweidata.starriverdefense.gamescreen.components.BackgroundActor;
import com.qiaweidata.starriverdefense.gamescreen.components.ShenControllers;
import com.qiaweidata.starriverdefense.gamescreen.components.ShenEntity;
import com.rondeo.pixwarsspace.utils.SoundController;

public class ShenGameScreen extends ScreenAdapter {

    private Stage stage;
    private OrthographicCamera camera;
    private InputMultiplexer inputMultiplexer;

    private TextureAtlas assets;
    private World<ShenEntity> world;

     private ShapeRenderer shapeRenderer;


    public ShenGameScreen(ShenMain main ) {
        //this.main = main;
        stage = new Stage( new FitViewport( main.width, main.height, camera = new OrthographicCamera() ) );
        assets = new TextureAtlas( Gdx.files.internal( "assets.atlas" ) );
        
        world = new World<>( 4f );
        //world.setTileMode( false );


//        backgroundActor = new BackgroundActor(Color.RED); // 设置背景颜色为红色
        stage.addActor(new BackgroundActor(Color.RED));

        shapeRenderer = new ShapeRenderer();
        ShenControllers.getInstance().init( camera );


        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor( stage );
        Gdx.input.setInputProcessor( inputMultiplexer );
    }

    @Override
    public void render( float delta ) {
        super.render( delta );

        stage.getViewport().apply();

        ScreenUtils.clear( Color.valueOf( "#4785e6" ) );
        
        if( ShenControllers.getInstance().tutorial >= 0 ) {
            ShenControllers.getInstance().pause = true;
        }
        if( ShenControllers.getInstance().credits == 1 ) {
            ShenControllers.getInstance().credits = 2;
        }
        if( !ShenControllers.getInstance().pause ) {
            if( !ShenControllers.getInstance().gameOver ) {
                stage.act();
            } else {
                stage.act( .0003f );
            }
        }
        stage.draw();

        ShenControllers.getInstance().act( stage, delta );

        if( Gdx.input.isKeyJustPressed( Keys.P ) ) {
            ShenControllers.getInstance().pause = !ShenControllers.getInstance().pause;
        }

        if( Gdx.input.isKeyPressed( Keys.L ) ) {
            System.out.println( world.countItems() + "<>" + world.countCells() + " = " + Gdx.graphics.getFramesPerSecond() );
        }

        // 绘制矩形轮廓
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(stage.getViewport().getScreenX(), stage.getViewport().getScreenY(), stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

         stage.getViewport().update(width, height, true); // 更新 Stage 的视口大小
//        stage.getViewport().update( 200, 200, true );
        //background.setBounds( 0, 0, width, height );
    }


    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        ShenControllers.getInstance().dispose();
        assets.dispose();
    }

}
