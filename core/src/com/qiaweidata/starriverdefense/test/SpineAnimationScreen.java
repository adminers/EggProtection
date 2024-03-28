package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SpineAnimationScreen implements Screen {
    private Batch batch;
    private Stage stage;
    private SkeletonRenderer skeletonRenderer;
    private Skeleton skeleton;
    private AnimationState animationState;

    private static final float SCALE = 0.25f; // 设置缩放因子

    public SpineAnimationScreen() {
        batch = new SpriteBatch(); // 创建一个 Batch 对象用于渲染
        stage = new Stage(new ScreenViewport()); // 创建一个舞台对象
        skeletonRenderer = new SkeletonRenderer();

        // 加载 Spine 动画文件
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("lib/t_map/map.atlas"));
        SkeletonJson json = new SkeletonJson(atlas);

        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("lib/t_map/map.json"));
        skeleton = new Skeleton(skeletonData);
        AnimationStateData stateData = new AnimationStateData(skeletonData);
        animationState = new AnimationState(stateData);

        // 设置初始动画
        animationState.setAnimation(0, "animation", true);

        skeleton.setScale(SCALE, SCALE);

        skeleton.setX(100);
        skeleton.setY(110);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        animationState.update(Gdx.graphics.getDeltaTime());
        animationState.apply(skeleton);
        skeleton.updateWorldTransform();

        // 渲染骨骼动画
        batch.begin();
        skeletonRenderer.draw(batch, skeleton);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
