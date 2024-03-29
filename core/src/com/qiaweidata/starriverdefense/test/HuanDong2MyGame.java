package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.qiaweidata.starriverdefense.test.func.CustomBounceInterpolator;
import com.qiaweidata.starriverdefense.test.func.EasingFunctions;
import com.qiaweidata.starriverdefense.test.func.LinearInterpolator;


/**
 * 缓动,可以用的,正在使用中
 *
 * @Title:
 * @Description:
 * @Company: www.fineplug.top
 * @author: shenshilong[shilong_shen@163.com]
 * @date:
 * @version: v1.0
 */
public class HuanDong2MyGame extends Game {

    long time = System.currentTimeMillis() + 9000;
    private SpriteBatch batch;
    private float elapsedTime = 0;
    private float initialY = 500;
    float newY = initialY;
    private float targetY = 470;
    private float fallDuration = 1.0f;
    private float shakeDuration = 0.5f;
    private float returnDuration = 1.0f;
    private boolean isFalling = true;
    private boolean isShaking = false;
    private boolean isReturning = false;
    private Texture texture;
    private boolean isToTarget = false;
    private float initialUpY = targetY;
    private float targetUpY = initialY;

    private TextureAtlas textureAtlas;
    private AtlasRegion shipRegion;
    private FitViewport viewport;

    @Override
    public void create() {
//        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
//        texture = new Texture("lib/ui/Jump.png");
        textureAtlas = new TextureAtlas(Gdx.files.internal("lib/t_map/map.atlas"));
        shipRegion = textureAtlas.findRegion("block_2");


    }

    @Override
    public void render() {

        /*if( System.currentTimeMillis() <= time ) {
            batch.begin();

            // 绘制物体
            batch.draw(texture, 100, initialY);

            batch.end();
            return;
        }*/
        elapsedTime += Gdx.graphics.getDeltaTime();

        /*if (isFalling) {
             newY = CustomBounceInterpolator.easeOutBounce(elapsedTime, initialY, targetY - initialY, fallDuration);
            // 绘制物体下落的位置
            System.out.println("绘制物体下落的位置");
            if (elapsedTime >= fallDuration) {
                isFalling = false;
                isShaking = true;
                initialY = targetY;
                elapsedTime = 0f;
            }
        } else if (isShaking) {
             newY = CustomElasticInterpolator.easeOutElastic(elapsedTime, initialY, targetY - initialY, shakeDuration);
            // 绘制物体震动的位置

            
            if (elapsedTime >= shakeDuration) {
                isShaking = false;
                isReturning = true;
                initialY = targetY;
                elapsedTime = 0f;
            }
        } else if (isReturning) {
             newY = CustomBackInterpolator.easeInOutBack(elapsedTime, initialY, targetY - initialY, returnDuration);
            // 绘制物体回到原位置的位置
            
            if (elapsedTime >= returnDuration) {
                isReturning = false;
            }
        }*/
        if (isToTarget) {
            if (newY != targetUpY) {
//                newY = LinearInterpolator.linearInterpolation(elapsedTime, initialUpY, targetUpY - initialUpY, fallDuration);
                newY = EasingFunctions.easeOutCubic(elapsedTime, initialUpY, targetUpY - initialUpY, fallDuration);
//                newY = CustomBounceInterpolator.easeOutBounce(elapsedTime, initialUpY, targetUpY - initialUpY, returnDuration);
            }
            if (newY > targetUpY) {
                newY = targetUpY;
            }
        } else {
            if (newY > targetY + .51) {
                newY = EasingFunctions.easeOutExpo(elapsedTime, initialY, targetY - initialY, returnDuration);
//                newY = LinearInterpolator.linearInterpolation(elapsedTime, initialY, targetY - initialY, fallDuration);
            } else {
                System.out.println("该上去了");
                isToTarget = true;
                elapsedTime = 0f;
            }
        }
        System.out.println(newY);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // 绘制物体
        batch.draw(shipRegion, 100, newY, 0, 0, 34, 34, 1, 1, 0);
//        batch.draw( baseRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );


        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
