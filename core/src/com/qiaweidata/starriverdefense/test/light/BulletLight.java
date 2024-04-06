package com.qiaweidata.starriverdefense.test.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class BulletLight {
    private Vector2 position;
    private Vector2 velocity;
    private float speed;

    public BulletLight(float x, float y, float angle) {
        position = new Vector2(x, y);
        speed = 5f;
        velocity = new Vector2(MathUtils.cos(angle) * speed, MathUtils.sin(angle) * speed);
    }

    public void update() {
        position.add(velocity);
    }

    public void draw(ShapeRenderer shapeRenderer) {

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(position.x, position.y, 5);
//        shapeRenderer.end();
    }
}