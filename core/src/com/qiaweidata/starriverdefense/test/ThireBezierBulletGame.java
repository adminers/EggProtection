package com.qiaweidata.starriverdefense.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;

public class ThireBezierBulletGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture bulletTexture;
    private Bezier<Vector2> bezierCurve;
    private ThireBullet bullet;

    private ParticleEffect flameEffect;

    @Override
    public void create() {
        batch = new SpriteBatch();
        bulletTexture = new Texture("lib/ui/Jump.png");

        Vector2 startPos = new Vector2(100, 100);
        Vector2 controlPoint = new Vector2(200, 1000);
        Vector2 endPoint = new Vector2(300, 500);

        bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        bullet = new ThireBullet(startPos, controlPoint, endPoint);

        flameEffect = new ParticleEffect();
        flameEffect.load(Gdx.files.internal("lib/effect/flame.p"), Gdx.files.internal("lib/effect"));
        flameEffect.start();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        bullet.update();

        batch.begin();

        // Render flame effect
        flameEffect.setPosition(bullet.getPosition().x, bullet.getPosition().y);
        flameEffect.update(Gdx.graphics.getDeltaTime());
        flameEffect.draw(batch);

        bullet.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bulletTexture.dispose();
        flameEffect.dispose();
    }

    public class ThireBullet {
        private Vector2 position;
        private Vector2 velocity;
        private float t = 0;

        public ThireBullet(Vector2 startPos, Vector2 controlPoint, Vector2 endPoint) {
            position = new Vector2(startPos);
            Vector2 direction = new Vector2(endPoint).sub(startPos).nor();
            velocity = direction.scl(5);
            bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        }

        public void update() {
            Vector2 point = bezierCurve.valueAt(new Vector2(), t);
            position.set(point);

            t += 0.01f;
            if (t > 1) {
                t = 1;
            }
        }

        public void render(SpriteBatch batch) {
            batch.draw(bulletTexture, position.x, position.y);
        }

        public Vector2 getPosition() {
            return position;
        }
    }
}
