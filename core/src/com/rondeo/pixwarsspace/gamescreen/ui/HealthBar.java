package com.rondeo.pixwarsspace.gamescreen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HealthBar extends Actor {
    private Texture healthBackground;
    private Texture healthForeground;
    private float currentHealth;
    private float maxHealth;

    public HealthBar(float maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        
        healthBackground = new Texture(Gdx.files.internal("lib/ui/x2.png"));
        healthForeground = new Texture(Gdx.files.internal("lib/ui/x1.png"));
    }

    public void setCurrentHealth(float health) {
        this.currentHealth = MathUtils.clamp(health, 0, maxHealth); // 限制血量在0和最大血量之间
    }

    public void draw(Batch batch, float x, float y, float width, float height) {
        batch.draw(healthForeground, x, y, width * (currentHealth / maxHealth), height);
        batch.draw(healthBackground, x, y, width, height);
    }

    public void dispose() {
        healthBackground.dispose();
        healthForeground.dispose();
    }
}
