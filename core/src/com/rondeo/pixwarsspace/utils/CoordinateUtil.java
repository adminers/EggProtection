package com.rondeo.pixwarsspace.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * @Title:
 * @Description:
 * @Company: www.fineplug.top
 * @author: shenshilong[shilong_shen@163.com]
 * @date:
 * @version: v1.0
 */
public class CoordinateUtil {

    public static float rotate(float enemyX, float enemyY, float playerX, float playerY) {

        // 玩家的位置
        Vector2 playerPosition = new Vector2(playerX, playerY);

        // 敌人的位置
        Vector2 enemyPosition = new Vector2(enemyX, enemyY);

        // 计算玩家朝向敌人的向量
        Vector2 direction = new Vector2(enemyPosition.x - playerPosition.x, enemyPosition.y - playerPosition.y);

        // 计算玩家朝向敌人的角度
        float angle = direction.angleDeg(); // 默认返回角度值在 -180 到 180 之间
        return angle;
    }

    public static float rotateVertical(float enemyX, float enemyY, float playerX, float playerY) {

        float rotate = rotate(enemyX, enemyY, playerX, playerY);
        return rotate - 90f;
    }

}
