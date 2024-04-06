package com.qiaweidata.starriverdefense.test.light;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class LightningSpriteGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture lightningTexture;
    private LightningSprite lightningSprite;

    private float iii;

    @Override
    public void create() {
        batch = new SpriteBatch();
        lightningTexture = new Texture("lib/lightning/ling.png"); // 加载闪电的图片
        Vector2 startPos = new Vector2(10, 10);
        Vector2 endPos = new Vector2(300, 300);
        lightningSprite = new LightningSprite(lightningTexture, startPos, endPos);
        iii = calculateAngle(startPos, endPos);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//        lightningSprite.draw(batch); // 绘制闪电效果
        batch.draw(lightningTexture, 10, 10, 0, 0, 30, 20, 1, 1, -40, 0, 0, lightningTexture.getWidth(), lightningTexture.getHeight(), false, false);
        batch.end();
    }

    private float calculateAngle(Vector2 startPos, Vector2 endPos) {
        return (float) Math.toDegrees(Math.atan2(endPos.y - startPos.y, endPos.x - startPos.x));
    }

    @Override
    public void dispose() {
        batch.dispose();
        lightningTexture.dispose();
    }
}