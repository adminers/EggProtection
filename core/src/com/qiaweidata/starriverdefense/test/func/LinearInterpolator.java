package com.qiaweidata.starriverdefense.test.func;

public class LinearInterpolator {

    public static float linearInterpolation(float t, float b, float c, float d) {
        return c * t / d + b;
    }

}
