package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class MyGame extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;

    HexMap hexMap = new HexMap();

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        hexMap.render(shapeRenderer);
        /*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        // 计算六边形的顶点坐标
        float centerX = Gdx.graphics.getWidth() / 2;
        float centerY = Gdx.graphics.getHeight() / 2;
        float radius = 100;
        float angleDeg = 60; // 每个角的角度

        float[] vertices = new float[12];
        for (int i = 0; i < 6; i++) {
            float angleRad = MathUtils.degRad * (60 * i);
            vertices[i * 2] = centerX + radius * MathUtils.cos(angleRad);
            vertices[i * 2 + 1] = centerY + radius * MathUtils.sin(angleRad);
        }

        shapeRenderer.polygon(vertices);*/
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
