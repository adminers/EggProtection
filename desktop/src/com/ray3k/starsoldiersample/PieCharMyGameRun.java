package com.ray3k.starsoldiersample;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ray3k.PieCharMyGame;

/**
 * @Title: TableGdxGameRun
 * @Description: TableGdxGameRun
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2024-04-27
 * @version: V1.0
 */
public class PieCharMyGameRun {

    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        //config.setForegroundFPS(60);
        config.setTitle("PixWars: Space Impact");
        config.setWindowSizeLimits( 400 , 800 , 9999, 9999  );
        new Lwjgl3Application(new PieCharMyGame(), config);
    }

}
