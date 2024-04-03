package com.rondeo.pixwarsspace.gamescreen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;

public class ComplexDialog extends Dialog {
    public ComplexDialog(String title, Skin skin) {
        super(title, skin);

        // 创建一个Table来管理布局
        Table table = new Table();
        table.defaults().pad(10);

        // 添加图片
        Image image = new Image(new Texture(Gdx.files.internal("lib/t_map/play/CelestialObjects.png")));
        image.setHeight(20);
        table.add(image).row();

        // 添加说明文字
        Label label = new Label("ZHE", skin);
        table.add(label).row();

        // 添加按钮
        TextButton button = new TextButton("YES", skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Controllers.getInstance().junction = false;
                hide();
            }
        });
        table.add(button);

        // 将Table添加到对话框中
        getContentTable().add(table);

        // 添加图片弹性效果的动作
        float duration = 0.5f; // 动作持续时间
        float scale = 1.1f; // 缩放比例
        image.addAction(Actions.sequence(
                Actions.sizeBy(image.getWidth() * scale, image.getHeight() * scale, duration, Interpolation.swingOut),
                Actions.sizeBy(-image.getWidth() * scale, -image.getHeight() * scale, duration, Interpolation.swingOut)
        ));
    }

    /*@Override
    public void act(float delta) {
        super.act(delta);
        // 添加图片弹性效果的动作
        Image image = (Image) ((Table) getContentTable().getCells().get(0).getActor()).getCells().get(0).getActor();
        float duration = 0.5f; // 动作持续时间
        float scale = 1.1f; // 缩放比例
        image.setScale(0.8f); // 初始缩放比例
        image.addAction(Actions.sequence(
                Actions.scaleTo(scale, scale, duration, Interpolation.swingOut),
                Actions.scaleTo(1f, 1f, duration, Interpolation.swingOut)
        ));
    }*/
}