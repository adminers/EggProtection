package com.rondeo.pixwarsspace.utils;

import com.badlogic.gdx.math.Vector2;

public class DistanceCalculator {

    public static float calculateDistance(Vector2 point1, Vector2 point2) {
        return point1.dst(point2);
    }

    public static float abDist(float x1, float y1, float x2, float y2) {
        return calculateDistance(new Vector2(x1, y1), new Vector2(x2, y2));
    }

    // 示例用法
    public static void main(String[] args) {
        Vector2 point1 = new Vector2(109.53418f, 270.08997f);
        Vector2 point2 = new Vector2(117.169235f, 246.0f);

        DistanceCalculator calculator = new DistanceCalculator();
        float distance = calculator.calculateDistance(point1, point2);
        System.out.println("The distance between the two points is: " + distance);
    }
}
