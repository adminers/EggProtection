package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.Game;

public class SpineAnimationScreenMyGame extends Game {
    
    @Override
    public void create() {
        SpineAnimationScreen spineScreen = new SpineAnimationScreen();
        setScreen(spineScreen);
    }
}