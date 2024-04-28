package com.ray3k;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class TableGdxGame extends ApplicationAdapter {
    private Stage stage;
    private Table table;

    @Override
    public void create () {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin( Gdx.files.internal( "ui/default.json" ) );


          // 创建三列五行的表格
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                if (col == 0 && row < 3) {
                    // 添加合并单元格的内容
                    if (row == 0) {
                        Label label = new Label("A1", skin);
                        table.add(label).center();
                    } else if (row == 1) {
                        Label label = new Label("A2", skin);
                        table.add(label).center();
                    } else if (row == 2) {
                        Label label = new Label("A3", skin);
                        table.add(label).center();
                    }
                    table.row();
                    break;
                } else {
                    // 普通单元格
                    Label label = new Label("Cell " + col + "-" + row, skin);
                    table.add(label).center();
                }
            }
            table.row();
        }
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
