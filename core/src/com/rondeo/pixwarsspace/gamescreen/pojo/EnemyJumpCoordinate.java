package com.rondeo.pixwarsspace.gamescreen.pojo;


import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;

/**
 * @author shenshilong
 */
public class EnemyJumpCoordinate {

    private String name;

    private Axis left;
    private Axis leftTop;
    private Axis leftBottom;
    private Axis right;
    private Axis rightTop;
    private Axis rightBottom;

    public Axis getLeft() {
        return this.left;
    }

    public EnemyJumpCoordinate setLeft(Axis left) {
        this.left = left;
        return this;
    }

    public Axis getLeftTop() {
        return this.leftTop;
    }

    public EnemyJumpCoordinate setLeftTop(Axis leftTop) {
        this.leftTop = leftTop;
        return this;
    }

    public Axis getLeftBottom() {
        return this.leftBottom;
    }

    public EnemyJumpCoordinate setLeftBottom(Axis leftBottom) {
        this.leftBottom = leftBottom;
        return this;
    }

    public Axis getRight() {
        return this.right;
    }

    public EnemyJumpCoordinate setRight(Axis right) {
        this.right = right;
        return this;
    }

    public Axis getRightTop() {
        return this.rightTop;
    }

    public EnemyJumpCoordinate setRightTop(Axis rightTop) {
        this.rightTop = rightTop;
        return this;
    }

    public Axis getRightBottom() {
        return this.rightBottom;
    }

    public EnemyJumpCoordinate setRightBottom(Axis rightBottom) {
        this.rightBottom = rightBottom;
        return this;
    }
}
