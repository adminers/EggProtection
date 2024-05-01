package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AtlasAnimationExample extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Animation<TextureRegion> animation;
    private float stateTime;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // 加载 .atlas 文件
        atlas = new TextureAtlas("lib/Yun/flame9.atlas");

        /*// 获取指定区域的纹理列表
        Array<TextureAtlas.AtlasRegion> regions = new Array<>();
//        for (int i = 25; i >= 0; i--) {
//            regions.add(atlas.findRegion("flame9-animation_" + String.format("%02d", i)));
//        }
        for (int i = 0; i < 26; i++) {
            regions.add(atlas.findRegion("flame9-animation_" + String.format("%02d", i)));
        }*/
        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions("trish");

        // 创建一个 Animation 对象
        animation = new Animation<>(0.1f, regions);

        // 设置循环播放
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // 在 render 方法中渲染动画
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 100, 100, 400, 100);

        batch.end();

        // 更新动画时间
        stateTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }
}
