package com.qiaweidata.starriverdefense.gamescreen.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor {
    private Color color;

    TextureRegion textureRegion;

    public BackgroundActor(Color color) {
        this.color = color;
        setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Texture bg = new Texture( Gdx.files.internal( "bg/bg.png" ) );
        bg.setWrap( TextureWrap.ClampToEdge, TextureWrap.Repeat );
        textureRegion = new TextureRegion( bg, 0, 0, 200, 400 );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        batch.setColor(color);
        batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
    }
}
