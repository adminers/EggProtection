package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyGdxGame extends ApplicationAdapter {
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        float screenHeight = Gdx.graphics.getHeight();
        float regionHeight = screenHeight / 4;

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("ui/default.json"));
        table.add(new Label("111111", skin)).height(regionHeight).expandX().fillX().row();
        table.add(new Label("222222", skin)).height(regionHeight).expandX().fillX().row();
        table.add(new Label("333333", skin)).height(regionHeight).expandX().fillX().row();

        Table table4 = new Table();
        table.add(table4).height(regionHeight).expandX().fillX().row();

        for (int i = 0; i < 4; i++) {
            final TextButton button = new TextButton("aaaaaaaaaaaaaa" + (i + 1), skin);
            table4.add(button).width(Gdx.graphics.getWidth() / 4).expandX().fillX().pad(10);

            // 绑定点击事件
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("Button Clicked", button.getText().toString());
                }
            });
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}


