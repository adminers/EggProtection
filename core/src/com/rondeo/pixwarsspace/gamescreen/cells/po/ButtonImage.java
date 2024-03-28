package com.rondeo.pixwarsspace.gamescreen.cells.po;

import java.io.Serializable;

public class ButtonImage implements Serializable {

    private Axis axis;

    private String image;

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
}
