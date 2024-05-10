package com.rondeo.pixwarsspace.gamescreen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rondeo.pixwarsspace.gamescreen.cells.po.ButtonImage;

/**
 * @Title: PowerShow
 * @Description: PowerShow
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-05-07
 * @version: V1.0
 */
public class PowerShow extends Actor {

    private Label label;

    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("lib/ui/Coin/Coin.atlas"));

    AtlasRegion atlasRegion;

    public PowerShow(Stage stage, ButtonImage buttonImage) {

        this.label = new Label(buttonImage.getPower().toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.label.setFontScale(0.5f);
        atlasRegion = atlas.findRegion("coin");
        this.setWidth(10);
        this.setHeight(10);
        float x = buttonImage.getAxis().getX() + 5;
        float y = buttonImage.getAxis().getY();
        label.setPosition(x, y - 13);
        label.setFontScale(0.5f);
        setPosition(x + 7, y - 8.5f);

        // 将Label添加到舞台中
        stage.addActor(label);
        stage.addActor(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(atlasRegion, getX(), getY(), getWidth(), getHeight());
    }
}
