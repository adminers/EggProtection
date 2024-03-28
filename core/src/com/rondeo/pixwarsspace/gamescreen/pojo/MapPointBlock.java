package com.rondeo.pixwarsspace.gamescreen.pojo;

import com.rondeo.pixwarsspace.gamescreen.components.entity.BrickShip;
import com.rondeo.pixwarsspace.gamescreen.components.entity.PointShip;

/**
 * @Title: MapPointBlock
 * @Description: MapPointBlock
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2024-03-28
 * @version: V1.0
 */
public class MapPointBlock {

    private PointShip pointShip;

    private BrickShip brickShip;

    public MapPointBlock(PointShip pointShip, BrickShip brickShip) {
        this.pointShip = pointShip;
        this.brickShip = brickShip;
    }

    public PointShip getPointShip() {
        return this.pointShip;
    }

    public MapPointBlock setPointShip(PointShip pointShip) {
        this.pointShip = pointShip;
        return this;
    }

    public BrickShip getBrickShip() {
        return this.brickShip;
    }

    public MapPointBlock setBrickShip(BrickShip brickShip) {
        this.brickShip = brickShip;
        return this;
    }
}
