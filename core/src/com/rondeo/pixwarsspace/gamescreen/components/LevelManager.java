package com.rondeo.pixwarsspace.gamescreen.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rondeo.pixwarsspace.gamescreen.cells.CardImageButton;
import com.rondeo.pixwarsspace.gamescreen.components.play.PlayShip;
import com.rondeo.pixwarsspace.gamescreen.plate.PlateBlockButton;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Title: LevelManager
 * @Description: LevelManager
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-20
 * @version: V1.0
 */
public class LevelManager {

    private static int currentLevel = 1;

    private static int currentIndexLevel = 0;

    private static final int maxLevel = 2;

    /**
     * 哪些草坪显示(index坐标),有无玩家
     */
    private static Map<Integer, PlayShip> showPlate = new HashMap<>(20);

    private static List<PlateBlockButton> plateBlockButtons = new ArrayList<>();

    /**
     * 已经生成玩家的坐标,showPlate 这个变量的key
     */
    private static Set<Integer> showPlateKey = new HashSet<>(20);

    public static void goToNextLevel() {
        currentIndexLevel++;
    }

    public static void goToNextShowLevel() {
        currentLevel++;
    }

    public static boolean isEndLevel() {

        return currentLevel > maxLevel;
    }

    public static int getCurrentIndexLevel() {
        return currentIndexLevel;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static List<CardImageButton> createCard() {

        List<CardImageButton> cardImageButtons = new ArrayList<>(4);
        Constants.CARDS.forEach(array -> array.forEach(card -> cardImageButtons.add(
            new CardImageButton(
                new TextureRegionDrawable(new Texture(Gdx.files.internal(card.getImage()))),
                card.getAxis().getX(),
                card.getAxis().getY(),
                card.getName()
            )
        )));
        return cardImageButtons;
    }

    /**
     * 创建我方落脚点
     *
     * @return
     */
    public static List<PlateBlockButton> createPlate() {

        Constants.PLATES.forEach(array -> array.forEach(card -> plateBlockButtons.add(
                new PlateBlockButton(
                        new TextureRegionDrawable(new Texture(Gdx.files.internal(card.getImage()))),
                        card.getAxis().getX(),
                        card.getAxis().getY(),
                        true
                )
        )));
        return plateBlockButtons;
    }

    public static Enemy findClosestEnemy(float x, float y) {

        Enemy closestEnemy = null;
        float closestDistance = Float.MAX_VALUE;

        // 遍历所有敌人
        for (Enemy enemy : Constants.ACTIVE_ENEMIES) {

            // 计算飞船与当前敌人的距离
            float distance = Vector2.dst2(x, y, enemy.getX(), enemy.getY());

            // 如果当前敌人比之前记录的最近敌人更近，则更新最近敌人和距离
            if (distance < closestDistance) {
                closestDistance = distance;
                closestEnemy = enemy;
            }
        }
        if (null != closestEnemy) {
//            System.out.println("最近的:" + closestEnemy.getName());
        }
        return closestEnemy;
    }

    public static Map<Integer, PlayShip> getShowPlate() {
        return showPlate;
    }

    public static List<PlateBlockButton> getPlateBlockButtons() {
        return plateBlockButtons;
    }

    public static Set<Integer> getShowPlateKey() {
        return showPlateKey;
    }
}
