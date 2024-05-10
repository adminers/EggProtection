package com.rondeo.pixwarsspace.gamescreen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rondeo.pixwarsspace.utils.Constants;

/**
 * @Title: CoinTiming
 * @Description: CoinTiming
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-05-06
 * @version: V1.0
 */
public class CoinTiming extends Actor {

    private Label labelCounter;

    private Label totalLabel;

    private int seconds = 0;

    private String unit = "s";

    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("lib/ui/Coin/Coin.atlas"));

    AtlasRegion atlasRegion;

    /**
     * 是否可以变化数字
     */
    private boolean chanage;

    /**
     * 是否变大
     */
    private boolean big;

    long time;

    public CoinTiming(Stage stage, Label totalLabel, Label labelCounter) {

        this.totalLabel = totalLabel;
        this.labelCounter = labelCounter;
//        labelCounter.setPosition(getX(), getY());
        atlasRegion = atlas.findRegion("coin");
        this.setWidth(20);
        this.setHeight(20);

        // 将Label添加到舞台中
        stage.addActor(labelCounter);
        incrementalSeconds();

        // 修复,看完广告后,没有变化
        chanage = true;
        big = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        totalLabel.setText(Constants.TOTAL_COIN);
        if (Constants.TOTAL_COIN >= 100) {

            labelCounter.setText("0" + unit);
            labelCounter.clearActions();
            batch.draw(atlasRegion, getX(), getY(), getWidth(), getHeight());
            return;
        }
        if (big) {
            if (System.currentTimeMillis() < time) {

                // 金币变大效果
                big(batch);
            } else {
                big = false;
            }
        }
        if (chanage) {
            incrementalSeconds();
        }
        batch.draw(atlasRegion, getX(), getY(), getWidth(), getHeight());
    }

    private void incrementalSeconds() {

        seconds = 0;
        chanage = false;

        unit = "s";
        labelCounter.setText(seconds + unit);
        labelCounter.clearActions();

        // 使用动画实现数字快速变化效果
        labelCounter.addAction(Actions.sequence(
            Actions.repeat(20, Actions.sequence(
                Actions.run(() -> {
                    seconds++;
                    labelCounter.setText(seconds + unit);
                    if (!chanage && seconds >= 10) {
                        chanage = true;
                        big = true;
                        totalLabel.setText(++Constants.TOTAL_COIN);
                        time = System.currentTimeMillis() + 200;
                    }
                }),
                Actions.delay(0.05f)
            ))
//            Actions.delay(1f),
//            Actions.run(() -> {
//
//            })
        ));
    }

    private void big(Batch batch) {

        // 缩放因子
        float scale = 1.2f;

        // 计算缩放后的宽度和高度
        float scaledWidth = getWidth() * scale;
        float scaledHeight = getHeight() * scale;

        // 计算缩放后的中心点
        float centerX = getX() + getWidth() / 2;
        float centerY = getY() + getHeight() / 2;
        float currentScale = 1.5f;

        // 绘制带有缩放效果的金币
        batch.draw(atlasRegion, centerX - scaledWidth / 2, centerY - scaledHeight / 2, scaledWidth / 2, scaledHeight / 2, scaledWidth, scaledHeight, currentScale, currentScale, 0);
    }
}
