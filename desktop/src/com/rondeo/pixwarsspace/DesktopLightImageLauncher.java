package com.rondeo.pixwarsspace;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.qiaweidata.starriverdefense.test.light.LightningSpriteGame;
import com.qiaweidata.starriverdefense.test.light.MyGameLight;

public class DesktopLightImageLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        //config.setForegroundFPS(60);
        config.setTitle("PixWars: Space Impact");
        config.setWindowSizeLimits( 400 , 800 , 9999, 9999  );
        new Lwjgl3Application(new LightningSpriteGame(), config);
    }
}
