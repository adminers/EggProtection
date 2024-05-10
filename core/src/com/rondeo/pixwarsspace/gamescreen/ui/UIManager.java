package com.rondeo.pixwarsspace.gamescreen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rondeo.pixwarsspace.gamescreen.components.HudManager;

public class UIManager {
    private Stage stage;
    private Skin skin;
    private HudManager hudManager;

    private ComplexDialog complexDialog;

    public UIManager(HudManager hudManager, Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
        this.hudManager = hudManager;
    }

    public void showComplexDialog() {

        complexDialog = new ComplexDialog(hudManager, "", new Skin(Gdx.files.internal("uiskin.json")));
        complexDialog.setModal(true);
//        complexDialog.setSize(200 , 300 );
        complexDialog.setSize(170 / 2, 60);
//        complexDialog.show(stage);

        // 设置弹出窗口的位置
        complexDialog.setPosition((stage.getWidth() - complexDialog.getWidth()) / 2, (stage.getHeight() - complexDialog.getHeight()) / 2);

        // 添加弹性效果的动作
        float duration = 0.5f; // 动作持续时间
        float scale = 1.1f; // 缩放比例
//        complexDialog.setScale(0.8f); // 初始缩放比例

        // 使用Interpolation可以实现不同的效果，比如Interpolation.swingOut、Interpolation.bounceOut等
//        complexDialog.addAction(Actions.sequence(
//                Actions.scaleTo(scale, scale, duration, Interpolation.swingOut),
//                Actions.scaleTo(1f, 1f, duration, Interpolation.swingOut)
//        ));

        // 将弹出窗口添加到舞台中
        stage.addActor(complexDialog);
//        complexDialog.show(stage);
    }

    public Image getNextImage() {
        return complexDialog.getNextImage();
    }

}