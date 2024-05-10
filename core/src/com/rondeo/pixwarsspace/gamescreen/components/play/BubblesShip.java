package com.rondeo.pixwarsspace.gamescreen.components.play;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.Scenery;

/**
 * 防护罩
 *
 * @Title:
 * @Description:
 * @Company:www.qiaweidata.com
 * @author:shenshilong
 * @date:
 * @version:V1.0
 */
public class BubblesShip extends Scenery {

    World<Entity> world;
    Item<Entity> item;
    Rect rect;

    private Texture circleTexture;
    int width = 100, height = 100;

    private float rotationAngle;

    /**
     * 监测敌人来袭的圆
     */
    private Vector2 center;
    private float radius;

    /**
     * 消失
     */
    private String notAttack = "notAttack";

    /**
     * 鲸鱼玩家,方便销毁
     */
    private PlayShip playShip;


    public BubblesShip(World<Entity> world, PlayShip playShip) {
        this.world = world;
        this.playShip = playShip;
        item = new Item<Entity>( this );
        world.add( item, 0, 0, 0, 0 );
        this.circleTexture = new Texture("lib/lightning/paopao2.png");
    }

    public void init(float positionX, float positionY) {
        world.update( item, positionX, positionY , width, height );
        setOrigin(getWidth() / 2f, getHeight() / 2f);
//        world.update( item, 0, 100, width, height );
        resolve();
        circleShield(new Vector2(positionX + 12 , positionY + 36), 50);
        notAttack = "notAttack";
    }

    @Override
    public void act(float delta) {
//        world.move( item, getX(), getY(), null );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

//        ShapeRenderer debugShapeRenderer = new ShapeRenderer();
//        debugShapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
//        debugShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        debugShapeRenderer.setColor(Color.BLUE);
//        debugShapeRenderer.circle(center.x, center.y, radius); // 绘制圆形防护罩
//        debugShapeRenderer.end();

        rotationAngle += 10f; // 每帧增加旋转角度

        if (rotationAngle >= 360) {
            rotationAngle = 0; // 重置角度为0，实现循环旋转
        }
//        batch.draw( circleTexture, getX() - 38, getY() - 15, width, height);
        batch.draw(circleTexture, getX() - 38, getY() - 15, getWidth() / 2, getHeight() / 2,
                width, height, 1, 1, rotationAngle, 0, 0,
                circleTexture.getWidth(), circleTexture.getHeight(), false, false);


    }

    public void resolve() {
        rect = world.getRect( item );
        setBounds( rect.x, rect.y, rect.w, rect.h );
    }

    private void circleShield(Vector2 center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public void attack() {
        notAttack = "Attack";
        playShip.isDead = true;
//        Controllers.getInstance().getPlayController().forceFree( playShip );
        System.out.println(notAttack);
    }

    public boolean isEnemyInside(Vector2 enemyPosition) {

        if ("Attack".equals(notAttack)) {
            return false;
        }
        float distance = center.dst(enemyPosition);
//        System.out.println("center(" + center.x + "," + center.y + ");enemyPosition(" + enemyPosition.x + "," + enemyPosition.y + ");distance:" + distance);
        return distance <= radius;
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {
        super.dispose();
        world.remove(item);
    }
}
