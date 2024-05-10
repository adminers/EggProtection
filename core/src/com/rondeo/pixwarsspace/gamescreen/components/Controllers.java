package com.rondeo.pixwarsspace.gamescreen.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.BossController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.BrickController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.BulletController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.CameraController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.EnemyController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.LightningController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.MonsterFactoryController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.PlayController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.PowerUpController;
import com.rondeo.pixwarsspace.gamescreen.components.controllers.SnakeEnemyController;
import com.rondeo.pixwarsspace.gamescreen.components.entity.Ship;

public class Controllers implements Disposable {
    private BulletController bulletController;
    private LightningController lightningController;
    private EnemyController enemyController;

    private SnakeEnemyController snakeEnemyController;
    private PlayController playController;
    private BrickController brickController;
    private MonsterFactoryController monsterFactoryController;
    private CameraController cameraController;
    private PowerUpController powerUpController;
    private BossController bossController;
    public boolean gameOver = false;
    public boolean pause = false;

    // 结算
    public boolean junction = false;
    public int tutorial = -1;
    public int credits = 0; // Change this later to 0

    private Preferences preferences;
    private Ship ship;

    private static Controllers instance = null;
    private Controllers() {}

    public Controllers init( World<Entity> world, OrthographicCamera camera, TextureAtlas assets, Ship ship ) {
        this.ship = ship;
        cameraController = new CameraController( camera );

        TextureAtlas assetsx = new TextureAtlas( Gdx.files.internal( "lib/t_map/play/bullet.atlas" ) );
        bulletController = new BulletController( world, /*assets.findRegion( "bullet_blue" ), */assetsx.findRegion( "idle" ) );
        lightningController = new LightningController( world, /*assets.findRegion( "bullet_blue" ), */assetsx.findRegion( "idle" ) );
        enemyController = new EnemyController( world, assets.findRegions( "explosion" ), 3, assets.findRegion( "enemy_orb" ), assets.findRegion( "enemy_spider" ), assets.findRegion( "enemy_moth" ) );


        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("lib/barn/snake.atlas"));
        snakeEnemyController = new SnakeEnemyController( world, assets.findRegions( "explosion" ), 3, textureAtlas.findRegion( "idle" ), textureAtlas.findRegion( "idle" ), textureAtlas.findRegion( "idle" ) );
        playController = new PlayController( world, null, 3, null, null, null );
        brickController = new BrickController( world, null, 3, textureAtlas.findRegion( "idle" ), textureAtlas.findRegion( "idle" ), textureAtlas.findRegion( "idle" ) );
        monsterFactoryController = new MonsterFactoryController( world );
        powerUpController = new PowerUpController( world, assets.findRegions( "powerups" ).toArray() );
        bossController = new BossController( world, 
            assets.findRegions( "tentacles" ), 
            assets.findRegions( "explosion" ), 
            assets.findRegion( "head" ), 
            assets.findRegion( "left_hand" ), 
            assets.findRegion( "right_hand" ), 
            assets.findRegion( "laser_left" ), 
            assets.findRegion( "laser_right" ), 
            assets.findRegion( "laser_head" ), 
            assets.findRegion( "warning" )
        );
        preferences = Gdx.app.getPreferences( "pixwars-space-impact" );
        return this;
    }

    public void act( Stage stage, float delta ) {
        cameraController.act( stage, delta );
    }

    @Override
    public void dispose() {
        enemyController.dispose();
        snakeEnemyController.dispose();
        playController.dispose();
        brickController.dispose();
        monsterFactoryController.dispose();
        bulletController.dispose();
        lightningController.dispose();
        bossController.dispose();
    }

    public static synchronized Controllers getInstance() {
        if( instance == null ) {
            instance = new Controllers();
        }
        return instance;
    }

    public Ship getShip() {
        return ship;
    }

    public void restart() {
        gameOver = false;
        pause = false;

        // 结算
        junction = false;
    }

    public void powerUp( int type ) {
        switch( type ) {
            case 0:
                if( !preferences.contains( "tut0" ) ) {
                    tutorial = 0;
                    preferences.putBoolean( "tut0", true );
                    preferences.flush();
                }
                ship.hasWings = System.currentTimeMillis() + 10000;
                break;
            case 1:
                if( !preferences.contains( "tut1" ) ) {
                    tutorial = 1;
                    preferences.putBoolean( "tut1", true );
                    preferences.flush();
                }
                ship.invulnerable = System.currentTimeMillis() + 5000;
                break;
            case 2:
                if( !preferences.contains( "tut2" ) ) {
                    tutorial = 2;
                    preferences.putBoolean( "tut2", true );
                    preferences.flush();
                }
                if( ship.life < Ship.maxLife )
                    ship.life += 1;
                break;
            case 3:
                if( !preferences.contains( "tut3" ) ) {
                    tutorial = 3;
                    preferences.putBoolean( "tut3", true );
                    preferences.flush();
                }
                ship.effect = System.currentTimeMillis() + 5000;
                break;
        }
    }

    public BulletController bulletController() {
        return bulletController;
    }

    public EnemyController enemyController(){
        return enemyController;
    }
    public SnakeEnemyController snakeEnemyController(){
        return snakeEnemyController;
    }

    public PlayController getPlayController() {
        return playController;
    }

    public BrickController getBrickController() {
        return this.brickController;
    }

    public CameraController cameraController() {
        return cameraController;
    }
    public PowerUpController powerUpController() {
        return powerUpController;
    }
    public BossController bossController() {
        return bossController;
    }

    public MonsterFactoryController getMonsterFactoryController() {
        return this.monsterFactoryController;
    }

    public LightningController getLightningController() {
        return lightningController;
    }

}
