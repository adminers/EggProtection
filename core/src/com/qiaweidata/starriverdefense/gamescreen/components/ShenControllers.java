package com.qiaweidata.starriverdefense.gamescreen.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.dongbat.jbump.World;
import com.qiaweidata.starriverdefense.gamescreen.components.controllers.ShenCameraController;

public class ShenControllers implements Disposable {
    private ShenCameraController cameraController;
    public boolean gameOver = false;
    public boolean pause = false;
    public int tutorial = -1;
    public int credits = 0; // Change this later to 0

    private Preferences preferences;

    private static ShenControllers instance = null;
    private ShenControllers() {}

    public ShenControllers init(OrthographicCamera camera ) {

        cameraController = new ShenCameraController( camera );


        preferences = Gdx.app.getPreferences( "pixwars-space-impact" );
        return this;
    }

    public void act( Stage stage, float delta ) {
        cameraController.act( stage, delta );
    }

    @Override
    public void dispose() {

    }

    public static synchronized ShenControllers getInstance() {
        if( instance == null ) {
            instance = new ShenControllers();
        }
        return instance;
    }


    public void powerUp( int type ) {
        switch( type ) {
            case 0:
                if( !preferences.contains( "tut0" ) ) {
                    tutorial = 0;
                    preferences.putBoolean( "tut0", true );
                    preferences.flush();
                }
                break;
            case 1:
                if( !preferences.contains( "tut1" ) ) {
                    tutorial = 1;
                    preferences.putBoolean( "tut1", true );
                    preferences.flush();
                }
                break;
            case 2:
                if( !preferences.contains( "tut2" ) ) {
                    tutorial = 2;
                    preferences.putBoolean( "tut2", true );
                    preferences.flush();
                }
                break;
            case 3:
                if( !preferences.contains( "tut3" ) ) {
                    tutorial = 3;
                    preferences.putBoolean( "tut3", true );
                    preferences.flush();
                }
                break;
        }
    }

    public ShenCameraController cameraController() {
        return cameraController;
    }

}
