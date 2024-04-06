package com.qiaweidata.starriverdefense.test.light;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LightningThire {
    private List<Vector2> points;

    public LightningThire(float startX, float startY, float endX, float endY) {
        points = new ArrayList<>();
        float x = startX;
        float y = startY;

        for (int i = 0; i < 5; i++) {
            float yOffset = MathUtils.random(1, 30);
            float xOffset = MathUtils.random(-20, 20);
            y += yOffset;
            x += xOffset;
            points.add(new Vector2(x, y));

            for (int j = 0; j < 5; j++) {
                yOffset = MathUtils.random(1, 8);
                xOffset = MathUtils.random(-3, 3);
                y += yOffset;
                x += xOffset;
                points.add(new Vector2(x, y));

                float bend = 1.7f;
                int m = y < endY ? 1 : -1;
                x += bend * m + MathUtils.random(-3, 3);
                points.add(new Vector2(x, y));
            }
        }

        points.add(new Vector2(endX, endY));
    }

    /*public LightningThire(int width, int height) {
        points = new ArrayList<>();
        float x = 0;
        float y = 0;

        for (int i = 0; i < 10; i++) {
            float yOffset = MathUtils.random(1, 30);
            float xOffset = MathUtils.random(-20, 20);
            y += yOffset;
            x += xOffset;
            points.add(new Vector2(x, y));

            for (int j = 0; j < 10; j++) {
                yOffset = MathUtils.random(1, 8);
                xOffset = MathUtils.random(-3, 3);
                y += yOffset;
                x += xOffset;
                points.add(new Vector2(x, y));

                float bend = 1.7f;
                int m = y < height / 2 ? 1 : -1;
                x += bend * m + MathUtils.random(-3, 3);
                points.add(new Vector2(x, y));
            }
        }
    }*/

    public void draw(ShapeRenderer shapeRenderer) {
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        for (int i = 0; i < points.size() - 1; i++) {
            Vector2 p1 = points.get(i);
            Vector2 p2 = points.get(i + 1);
            shapeRenderer.line(p1.x, p1.y, p2.x, p2.y);
        }



//        shapeRenderer.end();
    }
}