package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Hexagon {
    private float x;
    private float y;
    private float radius;
    private Color color;

    public Hexagon(float x, float y, float radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public void render(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(color);

        // 计算六边形的顶点坐标
        float[] vertices = calculateVertices(x, y, radius);

        shapeRenderer.polygon(vertices);
    }

    private float[] calculateVertices(float centerX, float centerY, float radius) {

        float[] vertices = new float[12]; // 6个顶点，每个顶点2个坐标（x，y）

        for (int i = 0; i < 6; i++) {
            float angleRad = MathUtils.PI / 3 * i; // 计算每个顶点的角度，每个顶点角度差为 PI/3
            float x = centerX + radius * MathUtils.cos(angleRad); // 计算顶点 x 坐标
//            float y = centerY + radius * MathUtils.sin(angleRad); // 计算顶点 y 坐标
            float y = centerY + radius * MathUtils.sin(angleRad); // 计算顶点 y 坐标

            vertices[i * 2] = x; // 存储顶点 x 坐标
            vertices[i * 2 + 1] = y; // 存储顶点 y 坐标
        }
//        System.out.println(vertices[0]+ "," + vertices[1] + "," + vertices[2] + "," + vertices[3] + "," + vertices[4] + "," + vertices[5] + "," + vertices[6] + "," + vertices[7] + "," + vertices[8] + "," + vertices[9] + "," + vertices[10] + "," + vertices[11]);

        return vertices;
    }

}
