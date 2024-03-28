package com.rondeo.pixwarsspace.gamescreen.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rondeo.pixwarsspace.gamescreen.cells.CardImageButton;
import com.rondeo.pixwarsspace.gamescreen.plate.PlateBlockButton;
import com.rondeo.pixwarsspace.utils.Constants;

import java.util.ArrayList;
import java.util.List;

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

    private boolean isLevelComplete() {

        // 检查第一关是否完成的逻辑，比如玩家达到一定分数
        return Constants.LEVEL1_ENEMYS.get(currentIndexLevel).isEmpty();
    }

    public static void goToNextLevel() {
        currentLevel++;
        currentIndexLevel++;
    }

    public static boolean isEndLevel() {

        return currentLevel > maxLevel;
    }

    public static int getCurrentLevel() {
        return currentIndexLevel;
    }

    public static List<CardImageButton> createCard() {

        List<CardImageButton> cardImageButtons = new ArrayList<>(4);
        Constants.CARDS.forEach(array -> array.forEach(card -> cardImageButtons.add(
            new CardImageButton(
                new TextureRegionDrawable(new Texture(Gdx.files.internal(card.getImage()))),
                card.getAxis().getX(),
                card.getAxis().getY()
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

        List<PlateBlockButton> imageButtons = new ArrayList<>(4);
        Constants.PLATES.forEach(array -> array.forEach(card -> imageButtons.add(
                new PlateBlockButton(
                        new TextureRegionDrawable(new Texture(Gdx.files.internal(card.getImage()))),
                        card.getAxis().getX(),
                        card.getAxis().getY()
                )
        )));
        return imageButtons;
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
}
