package com.rondeo.pixwarsspace.gamescreen.cells.po;

import java.io.Serializable;

/**
 * @Title: Axis
 * @Description: Axis
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-19
 * @version: V1.0
 */
public class Axis implements Serializable {

    private static final long serialVersionUID = 8671587586062793903L;

    private float x;

    private float y;

    public Axis() {
    }

    public Axis(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public Axis setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return this.y;
    }

    public Axis setY(float y) {
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return "Axis{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
