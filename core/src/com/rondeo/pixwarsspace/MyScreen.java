package com.rondeo.pixwarsspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyScreen implements Screen {
    
    private Stage stage;
    private Label label;
    private int seconds = 0;
    private String unit = "s";
    
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        
        label = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setPosition(100, 100);
        stage.addActor(label);
        
//        Timer.schedule(new Task() {
//            @Override
//            public void run() {
//                unit = "s";
//                label.setText(seconds + unit);
//
//                // 使用动画实现数字快速变化效果
//                label.addAction(Actions.sequence(
//                    Actions.repeat(10, Actions.sequence(
//                        Actions.run(() -> {
//                            seconds++;
//                            label.setText(seconds + unit);
//                        }),
//                        Actions.delay(0.1f)
//                    )),
//                    Actions.delay(1f),
//                    Actions.run(() -> {
//                        unit = "s";
//                        seconds = 0;
//                    })
//                ));
//            }
//        }, 1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
        d();
    }

    private void d() {

        unit = "s";
        label.setText(seconds + unit);

        // 使用动画实现数字快速变化效果
        label.addAction(Actions.sequence(
            Actions.repeat(10, Actions.sequence(
                Actions.run(() -> {
                    seconds++;
                    label.setText(seconds + unit);
                }),
                Actions.delay(0.1f)
            )),
            Actions.delay(1f),
            Actions.run(() -> {
                unit = "s";
                seconds = 0;
            })
        ));
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }

    // 其他方法省略
}
