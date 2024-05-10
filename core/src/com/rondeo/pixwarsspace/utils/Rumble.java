package com.rondeo.pixwarsspace.utils;

import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;
import java.util.Random;

public class Rumble {
    
    private static float time = 0;
    private static float currentTime = 0;
    private static float power = 0;
    private static float currentPower = 0;
    private static Random random;
    private static Vector3 pos = new Vector3();

    public static void rumble( float rumblePower, float rumbleLength ) {
        random = new Random();
        power = rumblePower;
        time = rumbleLength;
        currentTime = 0;
    }

    public static int[] randomIndex() {

        Random random = new Random();

        int minI = 0;
        int maxI = 4 - 1;
        int minJ = 0;
        int maxJ = 9 - 1;

        int randomI = random.nextInt(maxI - minI + 1) + minI;
        int randomJ = random.nextInt(maxJ - minJ + 1) + minJ;
        System.out.println("随机生成的 i 值: " + randomI + "随机生成的 j 值: " + randomJ);
        return new int[]{randomI, randomJ};
    }

    public static int[][] randomIndexs(int num) {

        int[][] re = new int[num][2];
        for (int i = 0; i < num; i++) {
            re[i] = randomIndex();
        }
        return re;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(randomIndexs(3)));
    }

    public static Vector3 tick( float delta ) {
        if (currentTime <= time) {
            currentPower = power * ((time - currentTime) / time);

            pos.x = (random.nextFloat() - 0.5f) * 2 * currentPower;
            pos.y = (random.nextFloat() - 0.5f) * 2 * currentPower;

            currentTime += delta;
        } else {
            time = 0;
        }
        return pos;
    }

    public static float getRumbleTimeLeft() {
        return time;
    }

    public static Vector3 getPos() {
        return pos;
    }
}