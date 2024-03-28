package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

public class BezierExample extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private Bezier<Vector2> bezierCurve;
    private Vector2 startPoint;
    private Vector2 endPoint;
    private float time;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

        startPoint = new Vector2(100, 100);
        endPoint = new Vector2(100, 400);

        // 创建贝塞尔曲线，只包含起点和终点
        bezierCurve = new Bezier<>(Vector2.Zero, startPoint, endPoint);

        time = 0;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        time += Gdx.graphics.getDeltaTime(); // 增加时间

        // 更新贝塞尔曲线上的点的位置
        Vector2 curvePoint = bezierCurve.valueAt(new Vector2(), time); // 根据时间获取贝塞尔曲线上的点
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.circle(curvePoint.x, curvePoint.y, 5); // 绘制贝塞尔曲线上的点
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
