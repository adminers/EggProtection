package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

public class BulletTest {
    private Vector2 position;
    private Bezier<Vector2> bezierCurve;
    private float time;
    private TextureRegion textureRegion;
    private Vector2 controlPoint;

    public BulletTest(Vector2 startPoint, Vector2 endPoint, Vector2 controlPoint, TextureRegion textureRegion) {
        this.position = new Vector2(startPoint);
        this.bezierCurve = new Bezier<>(startPoint, controlPoint, endPoint);
        this.time = 0;
        this.textureRegion = textureRegion;
        this.controlPoint = new Vector2(controlPoint);
    }

    public void update(float deltaTime) {
        time += deltaTime;
        position = bezierCurve.valueAt(new Vector2(), time);
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setControlPoint(Vector2 controlPoint) {
        this.controlPoint.set(controlPoint);
//        bezierCurve.set(1, controlPoint);
    }
}
