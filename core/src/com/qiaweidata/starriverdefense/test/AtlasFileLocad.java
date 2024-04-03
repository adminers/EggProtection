package com.qiaweidata.starriverdefense.test;

/**
 * @Title: AtlasFileLocad
 * @Description: AtlasFileLocad
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-04-03
 * @version: V1.0
 */
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

public class AtlasFileLocad extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Animation<TextureRegion> animation;

    @Override
    public void create () {
        batch = new SpriteBatch();
        atlas = new TextureAtlas(Gdx.files.internal("lib/t_map/testYan/run.atlas"));
        animation = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float stateTime = TimeUtils.nanoTime() / 1000000000.0f;
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(currentFrame, Gdx.graphics.getWidth()/2 - currentFrame.getRegionWidth()/2, Gdx.graphics.getHeight()/2 - currentFrame.getRegionHeight()/2);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        atlas.dispose();
    }
}
