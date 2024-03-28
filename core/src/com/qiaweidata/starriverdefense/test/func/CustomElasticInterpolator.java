package com.qiaweidata.starriverdefense.test.func;

public class CustomElasticInterpolator {
    
    public static float easeOutElastic(float t, float b, float c, float d) {
        if (t == 0) return b;
        if ((t /= d) == 1) return b + c;

        float p = d * 0.3f;
        float a = c;
        float s = p / 4;

        return (a * (float) Math.pow(2, -10 * t) * (float) Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b);
    }
}