package com.qiaweidata.starriverdefense.test.func;

public class CustomBackInterpolator {
    
    public static float easeInOutBack(float t, float b, float c, float d) {
        float s = 1.70158f;
        if ((t /= d / 2) < 1) {
            return c / 2 * (t * t * (((s *= 1.525f) + 1) * t - s)) + b;
        } else {
            return c / 2 * ((t -= 2) * t * (((s *= 1.525f) + 1) * t + s) + 2) + b;
        }
    }
}