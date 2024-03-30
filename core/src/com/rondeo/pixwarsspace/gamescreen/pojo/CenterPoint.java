package com.rondeo.pixwarsspace.gamescreen.pojo;


import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;

/**
 * 块上面的点
 *
 * @Title: CenterPoint
 * @Description: CenterPoint
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-28
 * @version: V1.0
 */
public class CenterPoint {

    /**
     * 块图原始点
     */
    private Axis blockAxis;

    /**
     * 块的站点
     */
    private Axis axis;

    private EnemyJumpCoordinate attr;

    public CenterPoint(Axis blockAxis, Axis axis, EnemyJumpCoordinate attr) {
        this.blockAxis = blockAxis;
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

    public EnemyJumpCoordinate getAttr() {
        return this.attr;
    }

    public CenterPoint setAttr(EnemyJumpCoordinate attr) {
        this.attr = attr;
        return this;
    }

    public Axis getBlockAxis() {
        return this.blockAxis;
    }

    public CenterPoint setBlockAxis(Axis blockAxis) {
        this.blockAxis = blockAxis;
        return this;
    }
}
