package com.qiaweidata.starriverdefense.menuscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.qiaweidata.starriverdefense.ShenMain;
import com.qiaweidata.starriverdefense.gamescreen.ShenGameScreen;
import com.qiaweidata.starriverdefense.gamescreen.TwoBlockLayoutScreen;

public class ShenMenuScreen extends ScreenAdapter {
    Stage stage;
    Skin skin;

    public ShenMenuScreen(final ShenMain main ) {
        stage = new Stage( new FitViewport( main.width*1.5f, main.height*1.5f ) );

        skin = new Skin( Gdx.files.internal( "ui/default.json" ) );
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        ImageTextButton textButton = new ImageTextButton( "Start", skin );
        textButton.addListener( new InputListener() {
            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
                return true;
            };
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                main.setScreen( new ShenGameScreen( main ) );
//                main.setScreen( new TwoBlockLayoutScreen( main ) );
            };
        } );
        table.add( textButton ).space( 20f ).fillX().width( 150 );

        //table.row();
        textButton = new ImageTextButton( "Settings", skin );
        textButton.addListener( new InputListener() {
            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
                return true;
            };
            public void touchUp( InputEvent event, float x, float y, int pointer, int button ) {
                
            };
        } );
        //table.add( textButton ).space( 20f ).fillX();

        table.row();
        textButton = new ImageTextButton( "Quit", skin );
        textButton.addListener( new InputListener() {
            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
                return true;
            };
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            };
        } );
        table.add( textButton ).space( 20f ).fillX();
        stage.addActor( table );

    }

    @Override
    public void render( float delta ) {
        super.render( delta );

        ScreenUtils.clear( Color.valueOf( "#2f033b" ) );

        stage.getViewport().apply();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize( int width, int height ) {
        super.resize( width, height );
        stage.getViewport().update( width, height, true );
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }
}
