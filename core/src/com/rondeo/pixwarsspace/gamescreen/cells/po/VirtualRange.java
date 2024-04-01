package com.rondeo.pixwarsspace.gamescreen.cells.po;

/**
 * 中心的左右范围,判断是否为虚拟节点用
 *
 * @author abc
 */
public class VirtualRange {

    private float leftX;

    private float rightX;

    public VirtualRange(float leftX, float rightX) {
        this.leftX = leftX;
        this.rightX = rightX;
    }

    public float getLeftX() {
        return leftX;
    }

    public VirtualRange setLeftX(float leftX) {
        this.leftX = leftX;
        return this;
    }

    public float getRightX() {
        return rightX;
    }

    public VirtualRange setRightX(float rightX) {
        this.rightX = rightX;
        return this;
    }

    @Override
    public String toString() {
        return "VirtualRange{" +
                "leftX=" + leftX +
                ", rightX=" + rightX +
                '}';
    }
}
