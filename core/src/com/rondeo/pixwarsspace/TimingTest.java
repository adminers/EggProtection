package com.rondeo.pixwarsspace;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class TimingTest extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private Timer timer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        timer = new Timer();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        long elapsedTime = timer.getTimeElapsed();

        batch.begin();
        font.draw(batch, "Time: " + elapsedTime / 1000 + "s", Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    private class Timer {
        private long startTime;

        public Timer() {
            startTime = TimeUtils.millis();
        }

        public long getTimeElapsed() {
            return TimeUtils.timeSinceMillis(startTime);
        }
    }
}
