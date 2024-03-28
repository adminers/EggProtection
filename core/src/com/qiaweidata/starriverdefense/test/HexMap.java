package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.MathUtils;

public class HexMap {
    private List<Hexagon> hexagons;

    public HexMap() {
        hexagons = new ArrayList<Hexagon>();
        // 添加需要绘制的六边形
//        hexagons.add(new Hexagon(x1, y1, radius, color));
//        hexagons.add(new Hexagon(x2, y2, radius, color));
        // 添加更多六边形
        float startX = 100; // 第一个六边形的起始X坐标
        float startY = 100; // 第一个六边形的起始Y坐标
        float radius = 50; // 六边形的半径
        Color color = Color.WHITE; // 六边形的颜色

        int numRows = 5;
        int numCols = 5;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                float x = startX + col * radius * 2f/*(float) Math.sqrt(3)*/;
                float y = startY + row * radius * 4f;
                hexagons.add(new Hexagon(x, y, radius, color));
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {

        for (Hexagon hexagon : hexagons) {
            hexagon.render(shapeRenderer);
        }

    }
}
