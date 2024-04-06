package com.qiaweidata.starriverdefense.test.light;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class LineRenderer {
    private Texture circleTexture;
    private List<Vector2> points;
    private float alpha;
    private List<Float[]> cachePoints;

    private boolean isComplete;

    private long time;

    private int i;

    public LineRenderer(Texture circleTexture) {
        this.circleTexture = circleTexture;
        this.points = new ArrayList<>();

        // 初始透明度为1
        this.alpha = 1.0f;
    }

    public void init(List<Float[]> points) {
        this.points.clear();
        this.alpha = 1.0f;
        this.cachePoints = points;
        this.isComplete = false;
        this.time = 0;
        this.i = 0;
    }

    public void addPoint(float x, float y) {
        points.add(new Vector2(x, y));
    }

    public void putPoint(List<Float[]> points) {
        cachePoints = points;
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        if (System.currentTimeMillis() > time + 10 && i != cachePoints.size()) {
            time = System.currentTimeMillis();
            Float[] floats = cachePoints.get(i);
            points.add(new Vector2(floats[0], floats[1]));
            i++;
        }
        for (int i = 0; i < points.size() - 1; i++) {
            Vector2 p1 = points.get(i);
            Vector2 p2 = points.get(i + 1);
            float distance = p1.dst(p2);
            float angle = MathUtils.atan2(p2.y - p1.y, p2.x - p1.x);
//            int width = circleTexture.getWidth();
            int width = 2;
            for (float d = 0; d < distance; d += width / 2) {
                float offsetX = MathUtils.cos(angle) * d;
                float offsetY = MathUtils.sin(angle) * d;
//                batch.draw(circleTexture, p1.x + offsetX, p1.y + offsetY);

                float scale = 0.1f; // 调整缩放比例
                float size = circleTexture.getWidth() * scale;
                batch.draw(circleTexture, p1.x + offsetX - size / 2, p1.y + offsetY - size / 2, size, size);
            }
        }
        if (points.size() > 5) {
            isComplete = true;
        }
        if (isComplete) {
            alpha -= 0.05f;
            if (alpha < 0) {
                alpha = 0;
                if (!points.isEmpty()) {

                    // 移除之前绘制的闪电路径的起点
                    points.remove(0);
                }
            }
        }
        batch.end();

    }
}