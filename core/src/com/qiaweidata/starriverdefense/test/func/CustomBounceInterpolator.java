package com.qiaweidata.starriverdefense.test.func;

public class CustomBounceInterpolator {
    
    public static float easeOutBounce(float t, float b, float c, float d) {
        if ((t /= d) < (1 / 2.75)) {
            return c * (7.5625f * t * t) + b;
        } else if (t < (2 / 2.75)) {
            return c * (7.5625f * (t -= (1.5f / 2.75f)) * t + 0.75f) + b;
        } else if (t < (2.5 / 2.75)) {
            return c * (7.5625f * (t -= (2.25f / 2.75f)) * t + 0.9375f) + b;
        } else {
            return c * (7.5625f * (t -= (2.625f / 2.75f)) * t + 0.984375f) + b;
        }
    }
}



