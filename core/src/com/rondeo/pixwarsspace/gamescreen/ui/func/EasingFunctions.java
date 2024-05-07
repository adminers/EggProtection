package com.rondeo.pixwarsspace.gamescreen.ui.func;

import com.badlogic.gdx.math.MathUtils;


public class EasingFunctions {
    public static float easeOutCubic(float t, float b, float c, float d) {
        t = MathUtils.clamp(t, 0, d);
        return c * ((t = t / d - 1) * t * t + 1) + b;
    }


    /**
     * https://easings.net/zh-cn#easeOutExpo
     *
     * @param t
     * @param b
     * @param c
     * @param d
     * @return
     */
    public static float easeOutExpo(float t, float b, float c, float d) {
        t = MathUtils.clamp(t, 0, d);
        return (t == d) ? b + c : c * (-1 * (float) Math.pow(2, -10 * t / d) + 1) + b;
    }
}
