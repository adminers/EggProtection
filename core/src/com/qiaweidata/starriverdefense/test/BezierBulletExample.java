package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BezierBulletExample extends ApplicationAdapter {
     private SpriteBatch batch;
    private Vector2 startPoint;
    private Vector2 endPoint;
    private Vector2 controlPoint;
    private TextureRegion bulletTexture;
    private BulletTest bullet;

    @Override
    public void create() {
        batch = new SpriteBatch();

        startPoint = new Vector2(100, 100);
        endPoint = new Vector2(500, 100);
        controlPoint = new Vector2(300, 300);

        // 加载子弹纹理
        bulletTexture = new TextureRegion(new Texture(Gdx.files.internal("lib/card/card.png"))); // 传入子弹纹理的路径或 Texture 对象

        bullet = new BulletTest(startPoint, endPoint, controlPoint, bulletTexture);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                controlPoint.set(screenX, Gdx.graphics.getHeight() - screenY);
                return true;
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        bullet.update(Gdx.graphics.getDeltaTime());
        
        batch.begin();
        bullet.render(batch); // 绘制子弹
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
