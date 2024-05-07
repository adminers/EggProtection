package com.rondeo.pixwarsspace.t1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tank extends Actor {
    private float size;
    private TextureRegion tankTextureRegion;

    public Tank(float size, Texture tankTexture) {
        this.size = size;
        setSize(size, size);
        tankTextureRegion = new TextureRegion(tankTexture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        // 保存原始颜色
        Color oldColor = batch.getColor().cpy();

        // 设置绘制颜色为黄色
        batch.setColor(Color.YELLOW);

        // 绘制一个简单的黄色圆形代替坦克形状
        batch.draw(tankTextureRegion, getX(), getY(), size, size);

        // 恢复原始颜色
        batch.setColor(oldColor);
    }
}