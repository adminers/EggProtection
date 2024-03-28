package com.qiaweidata.starriverdefense.test.po;

/**
 * @Title: CenterPoint
 * @Description: CenterPoint
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2024-03-28
 * @version: V1.0
 */
public class CenterPoint {

    private Axis axis;

    private FloatAttributes attr;

    public CenterPoint(Axis axis, FloatAttributes attr) {
        this.axis = axis;
        this.attr = attr;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public CenterPoint setAxis(Axis axis) {
        this.axis = axis;
        return this;
    }

    public FloatAttributes getAttr() {
        return this.attr;
    }

    public CenterPoint setAttr(FloatAttributes attr) {
        this.attr = attr;
        return this;
    }
}
