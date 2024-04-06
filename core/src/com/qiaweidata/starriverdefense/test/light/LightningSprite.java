package com.qiaweidata.starriverdefense.test.light;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class LightningSprite {

    private Sprite lightningSprite;

    public LightningSprite(Texture texture, Vector2 startPos, Vector2 endPos) {
        lightningSprite = new Sprite(texture);
        lightningSprite.setPosition(startPos.x, startPos.y);
        lightningSprite.setSize(endPos.x - startPos.x, endPos.y - startPos.y);
    }

    public void draw(SpriteBatch batch) {
        lightningSprite.draw(batch);
    }
}