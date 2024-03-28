package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MyGdxGame1 extends ApplicationAdapter {
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // 创建主布局
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // 设置底部区域背景颜色
//        Drawable redBackground = new TextureRegionDrawable();
//        rootTable.setBackground(redBackground);

        // 创建纵向四个大区域
        for (int i = 0; i < 4; i++) {
            Table verticalTable = new Table();
            verticalTable.setFillParent(true);
            rootTable.add(verticalTable).expand().fill();

            // 设置每个区域不同的背景颜色
//            Drawable background = getBackgroundForIndex(i); // 自定义方法，根据索引获取背景
//            verticalTable.setBackground(background);

            // 在底部区域创建横向并列四个区域
            if (i == 3) {
                for (int j = 0; j < 4; j++) {
                    final TextButton button = new TextButton("Button " + (j + 1), new Skin( Gdx.files.internal( "ui/default.json" ) ));
                    button.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            // 点击事件处理
                            System.out.println("Button clicked: " + button.getText());
                        }
                    });
                    verticalTable.add(button).expandX().fillX().pad(10);
                }
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Drawable getBackgroundForIndex(int index) {
        Drawable background;

        Texture texture1 = new Texture(Gdx.files.internal("input/cursor.png"));
        TextureRegion someTextureRegion1 = new TextureRegion(texture1);

        Texture texture2 = new Texture(Gdx.files.internal("bg/bg.png"));
        TextureRegion someTextureRegion2 = new TextureRegion(texture2);

        Texture texture3 = new Texture(Gdx.files.internal("bg/bg.png"));
        TextureRegion someTextureRegion3 = new TextureRegion(texture3);

        Texture texture4 = new Texture(Gdx.files.internal("input/cursor.png"));
        TextureRegion someTextureRegion4 = new TextureRegion(texture4);
        switch (index) {
            case 0:
                background = new TextureRegionDrawable();
                break;
            case 1:
                background = new TextureRegionDrawable();
                break;
            case 2:
                background = new TextureRegionDrawable();
                break;
            case 3:
                background = new TextureRegionDrawable();
                break;
            default:
                background = new TextureRegionDrawable();
                break;
        }
        return background;
    }
}

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyGdxGame extends ApplicationAdapter {
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // 创建主布局
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // 设置底部区域背景颜色
        rootTable.setBackground(Color.RED);

        // 创建纵向四个大区域
        for (int i = 0; i < 4; i++) {
            Table verticalTable = new Table();
            verticalTable.setFillParent(true);
            rootTable.add(verticalTable).expand().fill();

            // 设置每个区域不同的背景颜色
            switch (i) {
                case 0:
                    verticalTable.setBackground(Color.BLUE);
                    break;
                case 1:
                    verticalTable.setBackground(Color.GREEN);
                    break;
                case 2:
                    verticalTable.setBackground(Color.YELLOW);
                    break;
                case 3:
                    verticalTable.setBackground(Color.ORANGE);
                    break;
            }

            // 在底部区域创建横向并列四个区域
            if (i == 3) {
                for (int j = 0; j < 4; j++) {
                    TextButton button = new TextButton("Button " + (j + 1), skin);
                    button.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            // 点击事件处理
                            System.out.println("Button clicked: " + button.getText());
                        }
                    });
                    verticalTable.add(button).expandX().fillX().pad(10);
                }
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}*/

//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.badlogic.gdx.graphics.Color;
//
//public class MyGdxGame extends ApplicationAdapter {
//    private Stage stage;
//
//    @Override
//    public void create () {
//        stage = new Stage(new ScreenViewport());
//        Gdx.input.setInputProcessor(stage);
//
//        Table table = new Table();
//        table.setFillParent(true);
//        stage.addActor(table);
//
//        // 设置布局在屏幕纵向25%位置
////        table.top().padTop(Gdx.graphics.getHeight() * 0.25f);
//        table.bottom().padBottom(Gdx.graphics.getHeight() * 0.25f);
//
//        for (int i = 0; i < 4; i++) {
//            TextButton button = new TextButton("Button " + i, new Skin( Gdx.files.internal( "ui/default.json" ) )); // 这里的 skin 是你的皮肤资源
//            final int finalI = i;
//            button.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    Gdx.app.log("Button Clicked", "Button " + finalI + " clicked!");
//                }
//            });
//            table.add(button).pad(10);
//            table.row();
//        }
//        stage.getRoot().setColor(Color.RED);
//    }
//
//    @Override
//    public void render () {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        stage.act(Gdx.graphics.getDeltaTime());
//        stage.draw();
//    }
//
//    @Override
//    public void dispose () {
//        stage.dispose();
//    }
//}