package com.rondeo.pixwarsspace.gamescreen.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIManager {
    private Stage stage;
    private Skin skin;

    public UIManager(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
    }

    public void showComplexDialog() {
        ComplexDialog complexDialog = new ComplexDialog("FUCK", skin);
        complexDialog.setModal(true);
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
}