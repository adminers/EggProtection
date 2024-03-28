package com.rondeo.pixwarsspace.gamescreen.card;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author shen
 */
public class CardRectangleActor extends Actor {

    private ShapeRenderer shapeRenderer;

    public CardRectangleActor() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end(); // 结束批处理

        shapeRenderer.begin(ShapeType.Filled);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(getX(), getY() + 7, getWidth(), getHeight());

        shapeRenderer.setColor(new Color(73/255f, 88/255f, 104/255f, 1));
        shapeRenderer.rect(getX(), getY() + 5, getWidth(), getHeight());

        shapeRenderer.setColor(new Color(49/255f, 49/255f, 49/255f, 1.0f));
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();

        batch.begin(); // 开始新的批处理
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}