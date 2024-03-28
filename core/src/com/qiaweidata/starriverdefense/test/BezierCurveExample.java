package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

public class BezierCurveExample extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture texture;
    private Bezier<Vector2> bezierCurve;
    
    @Override
    public void create () {
        batch = new SpriteBatch();
        texture = new Texture("lib/ui/Jump.png");

        Vector2 startPoint = new Vector2(100, 10);
        Vector2 controlPoint = new Vector2(200, 1000);
        Vector2 endPoint = new Vector2(300, 500);

        bezierCurve = new Bezier<>(startPoint, controlPoint, endPoint);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        for (float t = 0; t <= 1; t += 0.01f) {
            Vector2 point = bezierCurve.valueAt(new Vector2(), t);
            batch.draw(texture, point.x, point.y);
        }
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        texture.dispose();
    }
}
