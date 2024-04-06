package com.qiaweidata.starriverdefense.test.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class LightningTwo {
    private List<Vector2> points;

    public LightningTwo(Vector2 start, Vector2 end, int numSegments, float curvature) {
        points = new ArrayList<>();
        points.add(start);

        Vector2 controlPoint1 = new Vector2(start.x + (end.x - start.x) * 0.33f, start.y + (end.y - start.y) * 0.33f);
        Vector2 controlPoint2 = new Vector2(start.x + (end.x - start.x) * 0.66f, start.y + (end.y - start.y) * 0.66f);

        for (int i = 1; i <= numSegments; i++) {
            float t = i / (float) numSegments;
            float x = (1 - t) * (1 - t) * start.x + 2 * (1 - t) * t * controlPoint1.x + t * t * controlPoint2.x;
            float y = (1 - t) * (1 - t) * start.y + 2 * (1 - t) * t * controlPoint1.y + t * t * controlPoint2.y;
            points.add(new Vector2(x, y));
        }

        points.add(end);
    }

    public void draw(ShapeRenderer shapeRenderer) {
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);

        for (int i = 0; i < points.size() - 1; i++) {
            Vector2 p1 = points.get(i);
            Vector2 p2 = points.get(i + 1);
            shapeRenderer.line(p1.x, p1.y, p2.x, p2.y);
        }

//        shapeRenderer.end();
    }
}