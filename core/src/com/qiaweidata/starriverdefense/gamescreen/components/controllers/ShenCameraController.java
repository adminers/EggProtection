package com.qiaweidata.starriverdefense.gamescreen.components.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ShenCameraController {
    OrthographicCamera camera;
    Vector2 camPos = new Vector2();

    public ShenCameraController(OrthographicCamera camera ) {
        this.camera = camera;
    }

    public void act( Stage stage, float delta ) {
        camPos.set( MathUtils.lerp( camera.position.x, camera.viewportWidth/2, delta * 2f ), MathUtils.lerp( camera.position.y, camera.viewportHeight/2, delta * 2 ) );
        camera.position.set( camPos, 0 );

    }

}
