package com.qiaweidata.starriverdefense.test.light;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class MyGameLight extends ApplicationAdapter {
    SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BulletLight bullet;
//    private Lightning lightning;
//    private LightningTwo lightning;
    private LightningThire lightning;
    private LinePoints linePoints;
    private LineRenderer lineRenderer;
    private long time;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        bullet = new BulletLight(100, 100, MathUtils.PI / 4);
//        lightning = new Lightning(new Vector2(50, 50), new Vector2(150, 150), 100);
//        lightning = new LightningTwo(new Vector2(50, 50), new Vector2(150, 150), 100, 0);
        lightning = new LightningThire(100, 50, 0, 300);
        linePoints = new LinePoints(new Vector2(0, 0), new Vector2(50, 50), 10);
        lineRenderer = new LineRenderer(new Texture("lib/lightning/particle.png"));
//        lineRenderer.addPoint(0, 0);
//        lineRenderer.addPoint(50, 50);
//        lineRenderer.addPoint(50, 100);

        // 随机
        List<Float[]> points = createPoints(0, 0, 50, 100, 10);
        lineRenderer.putPoint(points);
//        for (Float[] point : points) {
//            lineRenderer.addPoint(point[0], point[1]);
//        }

    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bullet.update();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // 开始填充形状绘制
        bullet.draw(shapeRenderer);
        shapeRenderer.end(); // 结束填充形状绘制

        // 设置线条宽度为5
//        Gdx.gl.glLineWidth(2);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // 开始线条形状绘制
        lightning.draw(shapeRenderer);
        shapeRenderer.end(); // g结束线条形状绘制


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // 开始线条形状绘制
        linePoints.draw(shapeRenderer);
        shapeRenderer.end(); // 结束线条形状绘制


        if (System.currentTimeMillis() > time + 2100) {
            time = System.currentTimeMillis();
            float endX = MathUtils.random(100, 200);
            float endY = MathUtils.random(100, 200);
            lineRenderer.init(createPoints(0, 0, endX, endY, 50));
        }
        lineRenderer.render(batch);

        // 设置线条宽度为5
//        Gdx.gl.glLineWidth(10);


    }

    public List<Float[]> createPoints(float startX, float startY, float endX, float endY, int numPoints) {

        List<Float[]> rs = new ArrayList<>();
        rs.add(new Float[]{startX, startY});
        float deltaX = (endX - startX) / (numPoints - 1);
        float deltaY = (endY - startY) / (numPoints - 1);

        for (int i = 0; i < numPoints; i++) {
            float x = startX + deltaX * i;
            float y = startY + deltaY * i;

            if (i % 2 == 0) {
                x = x - MathUtils.random(-10, 10);
            } else {
                y = y - MathUtils.random(-10, 10);
            }
            rs.add(new Float[]{x, y});
        }
        rs.add(new Float[]{endX, endY});
        return rs;
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}