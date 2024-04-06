package com.qiaweidata.starriverdefense.test.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lightning {
    private List<Vector2> points;

    public Lightning(Vector2 start, Vector2 end, int numSegments) {
        points = new ArrayList<>();
        points.add(start);

        float segmentLength = start.dst(end) / numSegments;
        Vector2 prevPoint = start;

        for (int i = 0; i < numSegments; i++) {
            float angle = MathUtils.random(-MathUtils.PI / 4, MathUtils.PI / 4);
            Vector2 nextPoint = new Vector2(prevPoint.x + segmentLength * MathUtils.cos(angle),
                    prevPoint.y + segmentLength * MathUtils.sin(angle));
            points.add(nextPoint);
            prevPoint = nextPoint;
        }

        points.add(end);
    }

    public void update(Vector2 start, Vector2 end, int numSegments) {
        points.clear();
        points.add(start);

        float segmentLength = start.dst(end) / numSegments;
        Vector2 prevPoint = start;

        for (int i = 0; i < numSegments; i++) {
            float angle = MathUtils.random(-MathUtils.PI / 4, MathUtils.PI / 4);
            Vector2 nextPoint = new Vector2(prevPoint.x + segmentLength * MathUtils.cos(angle),
                    prevPoint.y + segmentLength * MathUtils.sin(angle));
            points.add(nextPoint);
            prevPoint = nextPoint;
        }

        points.add(end);
    }

    private void generateLightning(Vector2 start, Vector2 end, float chaos, int numSegments) {
        if (numSegments < 2) {
            points.add(end);
            return;
        }

        Vector2 midPoint = new Vector2((start.x + end.x) / 2, (start.y + end.y) / 2);
        midPoint.add(MathUtils.random(-chaos, chaos), MathUtils.random(-chaos, chaos));

        generateLightning(start, midPoint, chaos / 2, numSegments / 2);
        points.add(midPoint);
        generateLightning(midPoint, end, chaos / 2, numSegments / 2);
    }

    private void generateLightning(Vector2 start, Vector2 end, float chaos, int numSegments, int forkChance, float forkLength) {
        if (numSegments < 2) {
            points.add(end);
            return;
        }

        Vector2 midPoint = new Vector2((start.x + end.x) / 2, (start.y + end.y) / 2);
        midPoint.add(MathUtils.random(-chaos, chaos), MathUtils.random(-chaos, chaos));

        generateLightning(start, midPoint, chaos / 2, numSegments / 2, forkChance, forkLength);

        if (MathUtils.random(0, 100) < forkChance) {
            Vector2 forkPoint = new Vector2(midPoint.x + MathUtils.random(-forkLength, forkLength),
                    midPoint.y + MathUtils.random(-forkLength, forkLength));
            points.add(forkPoint);
            generateLightning(forkPoint, end, chaos / 2, numSegments / 2, forkChance, forkLength);
        }

        points.add(midPoint);
        generateLightning(midPoint, end, chaos / 2, numSegments / 2, forkChance, forkLength);
    }

    Random random = new Random();

    public void draw(ShapeRenderer shapeRenderer) {
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        update(new Vector2(50, 50), new Vector2(300, 300),  10);
//        generateLightning(new Vector2(50, 50), new Vector2(300, 300), 10, 100);
        generateLightning(new Vector2(50, 50), new Vector2(300, 300), 10, 100, 2, 20);
        shapeRenderer.setColor(Color.YELLOW);

        for (int i = 0; i < points.size() - 1; i++) {
            Vector2 p1 = points.get(i);
            Vector2 p2 = points.get(i + 1);
            shapeRenderer.line(p1.x, p1.y, p2.x, p2.y);
        }

//        shapeRenderer.end();
    }
}