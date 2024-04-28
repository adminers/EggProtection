package com.ray3k;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PieCharMyGame extends Game {
    private Stage stage;
    private SpriteBatch batch;
    private Texture pieTexture;
    private float[] data = {30, 20, 10, 40}; // 饼图数据（每个扇形的大小）
    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW}; // 饼图扇形颜色
    private TextureRegion[] pieRegions;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        pieTexture = new Texture("lib/card/card.png"); // 导入饼图的图片素材

        pieRegions = new TextureRegion[data.length];
        for (int i = 0; i < data.length; i++) {
            float height = data[i] / 100 * pieTexture.getHeight();
            pieRegions[i] = new TextureRegion(pieTexture, 0, 0, pieTexture.getWidth(), height);
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.begin();
        float y = Gdx.graphics.getHeight() / 2;
        for (int i = 0; i < data.length; i++) {
            batch.setColor(colors[i]);
            batch.draw(pieRegions[i], 0, y);
            y += pieRegions[i].getRegionHeight();
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        pieTexture.dispose();
    }
}
