package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HuanDongMyGame extends Game {

    
    private SpriteBatch batch;
    private float elapsedTime = 0; // 记录已经过时间
    private float initialX = 0;
    private float initialY = 0;
    private float targetX = 0;
    private float targetY = 0;
    private float duration = 2f; // 动画持续时间

    private Texture texture;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture("lib/ui/Jump.png");
    }
    
    @Override
    public void render() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        
        float newX = CustomElasticInterpolator.easeOutElastic(elapsedTime, initialX, targetX - initialX, duration);
        float newY = CustomElasticInterpolator.easeOutElastic(elapsedTime, initialY, targetY - initialY, duration);
        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();

        // 绘制物体
        batch.draw(texture, newX, newY);
        batch.end();

        if (elapsedTime >= duration) {
            // 缓动完成，重置时间并重新播放缓动
            elapsedTime = 0;
        }
    }
    
    @Override
    public void dispose() {
        batch.dispose();
    }
}
