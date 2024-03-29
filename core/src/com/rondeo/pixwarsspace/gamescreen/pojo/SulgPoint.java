package com.rondeo.pixwarsspace.gamescreen.pojo;

import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.entity.SlugShip;

/**
 * @Title: SulgPoint
 * @Description: SulgPoint
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2024-03-29
 * @version: V1.0
 */
public class SulgPoint {

    private Axis axis;

    private SlugShip slugShip;

    public SulgPoint(Axis axis, SlugShip slugShip) {
        this.axis = axis;
        this.slugShip = slugShip;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public SulgPoint setAxis(Axis axis) {
        this.axis = axis;
        return this;
    }

    public SlugShip getSlugShip() {
        return this.slugShip;
    }

    public SulgPoint setSlugShip(SlugShip slugShip) {
        this.slugShip = slugShip;
        return this;
    }
}
