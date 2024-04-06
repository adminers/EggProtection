package com.qiaweidata.starriverdefense.test.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class LinePoints {
    private Vector2[] points;

    private Random random = new Random();

    public LinePoints(Vector2 start, Vector2 end, int numPoints) {
        points = new Vector2[numPoints];
        float deltaX = (end.x - start.x) / (numPoints - 1);
        float deltaY = (end.y - start.y) / (numPoints - 1);

        for (int i = 0; i < numPoints; i++) {
            float x = start.x + deltaX * i;
            float y = start.y + deltaY * i;

            if (i % 2 == 0) {
                x = x - MathUtils.random(-10, 10);
            } else {
                y = y - MathUtils.random(-10, 10);
            }
            points[i] = new Vector2(x, y);
        }
    }

    public Vector2[] getPoints() {
        return points;
    }

    public void draw(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(Color.WHITE);
        for (int i = 0; i < points.length - 1; i++) {
            Vector2 p1 = points[i];
            Vector2 p2 = points[i + 1];
            shapeRenderer.line(p1.x, p1.y, p2.x, p2.y);
        }
    }
}