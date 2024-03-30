package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.World;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.monster.MonsterAttr;

import static com.rondeo.pixwarsspace.utils.Constants.COMMON_SHIP_HEIGHT;

/**
 * @Title: MonsterShip
 * @Description: MonsterShip
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-30
 * @version: V1.0
 */
public class MonsterShip extends Entity.Wrapper implements Entity, Disposable {

    public int life;
    World<Entity> world;
    Item<Entity> item;
    Rect rect;
    Animation<AtlasRegion> thrusterAnimation;
    int width = 25, height = 19;

    long time;

    float deltaTime;

    float positionX, positionY;

    private Bezier<Vector2> bezierCurve;

    private float t = 0;

    private Vector2 position;

    /**
     * 不渲染,默认为TRUE不加载(延迟加载,可能没有位置)
     */
    private boolean notRender = true;

    private MonsterAttr monsterAttr;

    /**
     * 状态标志,如果是下落完成后,则执行地图块下落
     */
    private String state;

    public MonsterShip(World<Entity> world) {

        this.world = world;
        item = new Item<Entity>(this);
        world.add(item, getX(), getY(), getWidth(), getHeight());

        // 设置原点(原点是Actor的旋转、缩放和其他变换操作的参考点。)
        setOrigin(getWidth() / 2f, getHeight() / 2f);
    }

    public void setRegions(Array<AtlasRegion> commonRegion) {

        thrusterAnimation = new Animation<>(.09f, commonRegion);
        thrusterAnimation.setPlayMode(PlayMode.LOOP);
    }

    public void init(MonsterAttr monsterAttr) {

        Axis targetPoint = monsterAttr.getGeneratePoint();
        this.monsterAttr = monsterAttr;
        this.life = monsterAttr.getLife();
        this.positionX = targetPoint.getX();
        this.positionY = targetPoint.getY() + COMMON_SHIP_HEIGHT;
        this.world.update(this.item, 0 + positionX, positionY, width, height);
        resolve();
        /*CenterPoint centerPoint = Constants.CENTER_POINTS.get(getName());
        Axis leftAxis = centerPoint.getAttr().getLeftBottom();
        float targetX = leftAxis.getX();
        float targetY = leftAxis.getY() + COMMON_SHIP_HEIGHT;
        Vector2 startPos = new Vector2(positionX, positionY);
        float controlTargetX = positionX - 10;
        float controlTargetY = positionY + 10;
        Vector2 controlPoint = new Vector2(controlTargetX, controlTargetY);
        Vector2 endPoint = new Vector2(targetX, targetY);
        position = new Vector2(startPos);
        Vector2 direction = new Vector2(endPoint).sub(startPos).nor();
        bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        t = 0;*/

        this.position = new Vector2(this.positionX, this.positionY);
        fall();
        notRender = false;
    }

    /**
     * 下落,天降神兵
     */
    private void fall() {

        this.state = "fall";
        Axis targetPoint = monsterAttr.getTargetPoint();
        float targetX = targetPoint.getX();
        float targetY = targetPoint.getY() + COMMON_SHIP_HEIGHT;
        Vector2 startPos = new Vector2(positionX, positionY);
        float controlTargetX = positionX - 10;
        float controlTargetY = positionY + 10;
        Vector2 controlPoint = new Vector2(controlTargetX, controlTargetY);
        Vector2 endPoint = new Vector2(targetX, targetY);
        position = new Vector2(startPos);
        bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        t = 0;
    }

    @Override
    public void act(float delta) {

        if (notRender) {
            return;
        }
        deltaTime += delta;
    }

    public void update() {

        if (null == bezierCurve) {
            return;
        }
        Vector2 point = bezierCurve.valueAt(new Vector2(), t);
        position.set(point);

        t += 0.05f;
        if (t > 1) {
            t = 1;

            if ("fall".equals(this.state)) {
                System.out.println("执行完下落");

                // 执行块下落

            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (notRender) {
            return;
        }
        update();
        batch.draw(thrusterAnimation.getKeyFrame(deltaTime), position.x, position.y, getWidth(), getHeight());
    }

    public void resolve() {
        rect = world.getRect(item);
        setBounds(rect.x, rect.y, rect.w, rect.h);
    }


    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {

    }
}
