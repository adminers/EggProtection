package com.qiaweidata.starriverdefense.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qiaweidata.starriverdefense.ShenMain;

public class TwoBlockLayoutScreen extends ScreenAdapter {
    private Stage stage;

    private ScreenAdapter topScreenAdapter;

    private Viewport viewportTop;

    private Group topGroup;

    private Group upGroup;

    public TwoBlockLayoutScreen(ShenMain main) {
        stage = new Stage(new FitViewport(main.width, main.height));

        float topHeight = main.height * 0.7f; // 上方区域高度
        float bottomHeight = main.height * 0.3f; // 底部区域高度
        topScreenAdapter = new ShenGameScreen(main);

     topGroup = new Group();
        topGroup.setSize(main.width, topHeight);

        upGroup = new Group();
        upGroup.setSize(main.width, bottomHeight);

        topGroup.addActor(new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                topScreenAdapter.render(Gdx.graphics.getDeltaTime());
            }
        });
        topGroup.setSize(main.width, main.height * 0.7f);


         // 创建包含4个按钮的按钮组
        Table buttonTable = new Table();
        buttonTable.defaults().pad(10);

        Skin skin = new Skin( Gdx.files.internal( "ui/default.json" ) );
        for (int i = 0; i < 4; i++) {
            Button button = new TextButton("Button " + i, skin); // 使用自定义皮肤
            buttonTable.add(button).expandX().fillX();
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // 按钮点击事件处理
                }
            });
        }

        // 将按钮组添加到上部 Group
        upGroup.addActor(buttonTable);

        // 设置按钮组在上部 Group 中的位置和布局
        buttonTable.setFillParent(true);
        buttonTable.align(Align.top);

        stage.addActor(topGroup);
        stage.addActor(upGroup);

       /* // 创建上方区域
        Actor topActor = new Image(new Texture("bg/topImage.png"));
        ScreenAdapter topScreenAdapter = new ShenGameScreen(main);
        topScreenAdapter.resize((int)viewportTop.getWorldWidth(), (int)viewportTop.getWorldHeight());
        rootTable.add().expandX().fillX().height(topHeight).row();*/

        // 创建底部区域
        /*Actor bottomActor = new Image(new Texture("bg/topImage.png"));
        rootTable.add(bottomActor).expandX().fillX().height(bottomHeight);

        stage.addActor(rootTable);*/
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
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
