package com.rondeo.pixwarsspace.gamescreen.cells.po;

import java.io.Serializable;

public class ButtonImage implements Serializable {

    private static final long serialVersionUID = 7856330031657036276L;

    private Axis axis;

    private String image;

    private String name;

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

    @Override
    public String toString() {
        return "ButtonImage{" +
                "axis=" + axis +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
