package com.qiaweidata.starriverdefense.test.po;

public class FloatAttributes {
    
    // 定义六个浮点属性
    private Axis left;
    private Axis leftTop;
    private Axis leftBottom;
    private Axis right;
    private Axis rightTop;
    private Axis rightBottom;

    public Axis getLeft() {
        return this.left;
    }

    public FloatAttributes setLeft(Axis left) {
        this.left = left;
        return this;
    }

    public Axis getLeftTop() {
        return this.leftTop;
    }

    public FloatAttributes setLeftTop(Axis leftTop) {
        this.leftTop = leftTop;
        return this;
    }

    public Axis getLeftBottom() {
        return this.leftBottom;
    }

    public FloatAttributes setLeftBottom(Axis leftBottom) {
        this.leftBottom = leftBottom;
        return this;
    }

    public Axis getRight() {
        return this.right;
    }

    public FloatAttributes setRight(Axis right) {
        this.right = right;
        return this;
    }

    public Axis getRightTop() {
        return this.rightTop;
    }

    public FloatAttributes setRightTop(Axis rightTop) {
        this.rightTop = rightTop;
        return this;
    }

    public Axis getRightBottom() {
        return this.rightBottom;
    }

    public FloatAttributes setRightBottom(Axis rightBottom) {
        this.rightBottom = rightBottom;
        return this;
    }
}