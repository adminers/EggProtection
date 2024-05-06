package com.rondeo.pixwarsspace;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SpineAnimationExample extends ApplicationAdapter {
    private PolygonSpriteBatch batch;
    private SkeletonRenderer skeletonRenderer;
    private Skeleton skeleton;
    private AnimationState animationState;

    @Override
    public void create() {
        batch = new PolygonSpriteBatch();
        skeletonRenderer = new SkeletonRenderer();

        SkeletonJson json = new SkeletonJson(new TextureAtlas("lib/Test/pro/coronavirus.atlas"));
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("lib/Test/pro/coronavirus.json"));

//        SkeletonJson json = new SkeletonJson(new TextureAtlas("lib/Test/flame9.atlas"));
//        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("lib/Test/flame9.json"));

        skeleton = new Skeleton(skeletonData);
        animationState = new AnimationState(new AnimationStateData(skeletonData));

        skeleton.setSkin("blue-rim-light");
        skeleton.setToSetupPose();
        animationState.setAnimation(0, "hit", true);
//        animationState.setAnimation(0, "animation", true);

    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Gdx.gl.glClearColor(1, 1, 1, 1);

        animationState.update(Gdx.graphics.getDeltaTime());
        animationState.apply(skeleton);
        skeleton.updateWorldTransform();

        batch.begin();
        skeleton.setPosition(400, 200);
        skeletonRenderer.draw(batch, skeleton);
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
