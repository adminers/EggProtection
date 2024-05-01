package com.rondeo.qiaweidata;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.qiaweidata.starriverdefense.test.AtlasAnimationExample;

public class SpineAnimationExampleDesktopLauncher {

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        //config.setForegroundFPS(60);
        config.setTitle("PixWars: Space Impact");
        config.setWindowSizeLimits( 800 , 800 , 9999, 9999  );
        new Lwjgl3Application(new AtlasAnimationExample(), config);
    }
}
