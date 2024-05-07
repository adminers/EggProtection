package com.rondeo.pixwarsspace.gamescreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HealthBarActor extends Actor {
    private HealthBar healthBar;

    public HealthBarActor(HealthBar healthBar) {
        this.healthBar = healthBar;
        this.setWidth(200); // 设置血条宽度
        this.setHeight(20); // 设置血条高度
    }

    /**
     * 层级变化更改血条健康度
     */
    public void rest(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    public void setCurrentHealth(float health) {
        this.healthBar.setCurrentHealth(health);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        // 绘制血条
//        batch.begin();
//        healthBar.draw(batch, 100, 100, 200, 20); // 在(100, 100)位置绘制血条，宽度200，高度20
        healthBar.draw(batch, getX(), getY(), getWidth(), getHeight());
//        batch.end();
    }
}