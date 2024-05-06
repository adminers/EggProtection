package com.rondeo.pixwarsspace;

import com.badlogic.gdx.Game;

public class MyGame extends Game {

    @Override
    public void create() {
        setScreen(new MyScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}