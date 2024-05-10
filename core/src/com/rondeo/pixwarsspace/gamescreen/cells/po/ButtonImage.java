package com.rondeo.pixwarsspace.gamescreen.cells.po;

import java.io.Serializable;

public class ButtonImage implements Serializable {

    private static final long serialVersionUID = 7856330031657036276L;

    private Axis axis;

    private String image;

    private String name;

    private String row;

    private String col;

    /**
     * 消耗的能量值
     */
    private Integer power;

    public ButtonImage() {
    }

    public ButtonImage(Axis axis, String image) {
        this.axis = axis;
        this.image = image;
    }

    public ButtonImage(Axis axis, String image, String name, String row, String col) {
        this.axis = axis;
        this.image = image;
        this.name = name;
        this.row = row;
        this.col = col;
    }

    public String getImage() {
        return image;
    }

    public ButtonImage setImage(String image) {
        this.image = image;
        return this;
    }

    public Axis getAxis() {
        return axis;
    }

    public ButtonImage setAxis(Axis axis) {
        this.axis = axis;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ButtonImage setName(String name) {
        this.name = name;
        return this;
    }

    public String getRow() {
        return row;
    }

    public ButtonImage setRow(String row) {
        this.row = row;
        return this;
    }

    public String getCol() {
        return col;
    }

    public ButtonImage setCol(String col) {
        this.col = col;
        return this;
    }

    public Integer getPower() {
        return this.power;
    }

    public ButtonImage setPower(Integer power) {
        this.power = power;
        return this;
    }

    @Override
    public String toString() {
        return "ButtonImage{" +
                "axis=" + axis +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", row='" + row + '\'' +
                ", col='" + col + '\'' +
                ", power=" + power +
                '}';
    }
}
