package com.rondeo.pixwarsspace.gamescreen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * @Title: HomeCoin
 * @Description: HomeCoin
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-05-10
 * @version: V1.0
 */
public class HomeCoin extends Actor {

    TextureAtlas atlasCoin = new TextureAtlas(Gdx.files.internal("lib/ui/Coin/Coin.atlas"));

    Array<AtlasRegion> atlasRegions;

    float deltaTime;

    Animation<AtlasRegion> coinAnimation;

    public HomeCoin() {

        this.setWidth(40);
        this.setHeight(40);
        atlasRegions = atlasCoin.findRegions("homeCoin");
        coinAnimation = new Animation<>( 0.2f, atlasRegions );
        coinAnimation.setPlayMode( PlayMode.LOOP_PINGPONG );
    }

    @Override
    public void act(float delta) {

        deltaTime += delta;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(coinAnimation.getKeyFrame( deltaTime ), getX(), getY(), getWidth(), getHeight());
    }
}
