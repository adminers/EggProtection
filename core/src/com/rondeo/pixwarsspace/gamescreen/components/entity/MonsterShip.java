package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.jbump.*;
import com.rondeo.pixwarsspace.gamescreen.ui.func.EasingFunctions;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Enemy;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.HudManager;
import com.rondeo.pixwarsspace.gamescreen.components.play.BubblesShip;
import com.rondeo.pixwarsspace.gamescreen.pojo.CenterPoint;
import com.rondeo.pixwarsspace.gamescreen.pojo.MapPointBlock;
import com.rondeo.pixwarsspace.monster.MonsterAttr;
import com.rondeo.pixwarsspace.utils.Constants;
import com.rondeo.pixwarsspace.utils.Rumble;
import com.rondeo.pixwarsspace.utils.SoundController;

import java.util.List;
import java.util.Random;

import static com.rondeo.pixwarsspace.utils.Constants.COMMON_SHIP_HEIGHT;

/**
 * @Title: MonsterShip
 * @Description: MonsterShip
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-30
 * @version: V1.0
 */
public class MonsterShip extends Enemy {

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

    private String targetName;

    long isHit;

    Color color = new Color();

    private boolean isDead;

    Random random = new Random();

    private HudManager hudManager;

    CollisionFilter collisionFilter = new CollisionFilter() {
        //Bullet bullet;
        @SuppressWarnings( "rawtypes" )
        @Override
        public Response filter(Item item, Item other ) {
            return null;
        }
    };

    public MonsterShip(World<Entity> world, HudManager hudManager) {

        this.world = world;
        this.hudManager = hudManager;
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

        // 生成点
        Axis generatePoint = monsterAttr.getGeneratePoint();
        this.monsterAttr = monsterAttr;
        this.life = monsterAttr.getLife();
        this.positionX = generatePoint.getX();
        this.positionY = generatePoint.getY() ;
        this.world.update(this.item, 0 + positionX, positionY, monsterAttr.getWidth(), monsterAttr.getHeight());
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
        notRender = false;
        isDead = false;
        isSlow = false;
        jump = false;
        fall();
        t = 0;
        isAttackPlayer = false;
    }

    /**
     * 下落,天降神兵
     */
    private void fall() {

        this.state = "fall";
        CenterPoint centerPoint = Constants.CENTER_POINTS.get(targetName);
        Axis blockAxis = centerPoint.getAxis();

        // 目测下落至点位时有点偏移,微调
        float targetX = blockAxis.getX() - 8;
        float targetY = blockAxis.getY() + COMMON_SHIP_HEIGHT;
        Vector2 startPos = new Vector2(positionX, positionY);
        float controlTargetX = positionX - 10;
        float controlTargetY = positionY + 10;
        Vector2 controlPoint = new Vector2(controlTargetX, controlTargetY);
        Vector2 endPoint = new Vector2(targetX, targetY);
        position = new Vector2(startPos);
        bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        t = 0;

//        life = 100;
    }

    @Override
    public void act(float delta) {

        super.act(delta);
        if (notRender) {
            return;
        }
        deltaTime += delta;

        world.move( item, getX(), getY(), collisionFilter );
//        System.out.println("getX=" + getX() + ",getY()=" + getY() + ",position.x=" + position.x + ", position.y=" + position.y);
        resolve();
        if( life <= 0 ) {
            if( !SoundController.getInstance().explosion.isPlaying() ) {
                SoundController.getInstance().explosion.play();
            }
            Rumble.rumble( 4f, .4f );
            clearActions();
            isDead = true;
            deltaTime = 0;
//            Controllers.getInstance().powerUpController().pop( getStage(), ((random.nextInt(8) + 1)*0.1f) * getStage().getWidth(),  getStage().getHeight() + PowerUp.height/2f );
            return;
        }
    }

    public void update() {

        if (null == bezierCurve || t == 1.0f) {
            return;
        }
        Vector2 point = bezierCurve.valueAt(new Vector2(), t);
        position.set(point);
        t += 0.01f;
        if (t > 1) {
            t = 1;
            bezierCurve = null;
            execFall();
        }
    }

    private void execFall() {

        if (isAttackPlayer) {
            boolean successAttackPlayer = true;
            for (int i = Constants.bubblesShips.size() - 1; i >= 0; i--) {
                BubblesShip bubblesShip = Constants.bubblesShips.get(i);
                boolean enemyInside = bubblesShip.isEnemyInside(new Vector2(getX(), getY()));
                if (enemyInside) {
                    bubblesShip.attack();
                    bubblesShip.dispose();
                    bubblesShip.remove();
                    bubblesShip = null;
                    successAttackPlayer = false;
                    Constants.bubblesShips.remove(i);
                    break;
                }
            }
            if (successAttackPlayer) {
                hudManager.hideLife();
            }
            isDead = true;
            return;
        }
        if ("fall".equals(this.state)) {
            this.jump = false;
            CenterPoint centerPoint = Constants.CENTER_POINTS.get(targetName);

            // 初始化下落的值
            initY = centerPoint.getAxis().getY() + COMMON_SHIP_HEIGHT;

            // 执行块下落
            MapPointBlock mapPointBlock = Constants.POINT_BRICK_SHIPS.get(targetName);
            mapPointBlock.getPointShip().runSlow();
            mapPointBlock.getBrickShip().runSlow();
            this.runSlow();
            this.state = "endFall";
        }
    }

    float newY;
    private boolean isToTarget = false;
    private float initialUpY;
    private float targetUpY;
    private float elapsedTime = 0;
    private float initialY;

    private float initY;

    private float fallDuration = 0.1f;

    private float returnDuration = 0.1f;

    private float targetY;

    /**
     * 砖块上下缓动
     */
    private boolean isSlow;

    private boolean jump;

    private float slowAction() {

        // 已经要执行跳跃了,则不执行 天降神兵
        if (jump) {
            return newY;
        }
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (isToTarget) {
            if (newY != targetUpY) {
                newY = EasingFunctions.easeOutCubic(elapsedTime, initialUpY, targetUpY - initialUpY, fallDuration);
            }
            if (newY >= targetUpY) {
                newY = targetUpY;
                jump = true;

                // 缓动至为false
                isSlow = false;
                bezierCurve = null;
            }
        } else {
            if (newY > targetY + .51) {
                newY = EasingFunctions.easeOutExpo(elapsedTime, initialY, targetY - initialY, returnDuration);
            } else {
                isToTarget = true;
                elapsedTime = 0f;
            }
        }
        return newY;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (notRender) {
            return;
        }
        if (Controllers.getInstance().gameOver) {
//            forceFree();
            return;
        }
        if( isDead ) {
            batch.draw( thrusterAnimation.getKeyFrame(deltaTime), getX() - getWidth()/2f, getY() - getHeight()/2f, getWidth()*2f, getHeight()*2f );
            forceFree();
            return;
        }
        if (Controllers.getInstance().pause) {
            return;
        }
        /*if (isAttackPlayer) {
            Constants.bubblesShips.forEach(bubblesShip -> {
                boolean enemyInside = bubblesShip.isEnemyInside(new Vector2(getX(), getY()));
                if (enemyInside) {
                    bubblesShip.attack();
                    bubblesShip.remove();
                }
            });
            System.out.println("敌人：(" + getX() + "," + getY() + ")");
        }*/
        if (jump) {
            updateJump();
            batch.draw(thrusterAnimation.getKeyFrame(deltaTime), position.x, position.y, getWidth(), getHeight());
            setX(position.x);
            setY(position.y);

            // 延迟跳
            if (System.currentTimeMillis() > time + 500) {
                time = System.currentTimeMillis();
                if ("endFall".equals(state)) {

                    // 可以跳了
                    cJump();
                }
            }
            return;
        }

        if (isSlow) {
            slowAction();
            hit(batch);
            batch.draw(thrusterAnimation.getKeyFrame(deltaTime), position.x, newY, getWidth(), getHeight());
            setX(position.x);
            setY(newY);
            position.set(position.x, newY);
        } else {
            update();
            hit(batch);
            batch.draw(thrusterAnimation.getKeyFrame(deltaTime), position.x, position.y, getWidth(), getHeight());
            setX(position.x);
            setY(position.y);
        }
    }

    private void hit(Batch batch) {

        if( isHit > System.currentTimeMillis() ) {
            color.set( batch.getColor() );
            batch.setColor( 1, 0, 0, .5f );
            batch.draw( thrusterAnimation.getKeyFrame(deltaTime), getX(), getY(), getWidth() * 2, getHeight() * 2);
            batch.setColor( color );
        }
    }

    public void resolve() {
        rect = world.getRect(item);
        setBounds(rect.x, rect.y, rect.w, rect.h);
    }

    public void runSlow() {

        t = 0;
        targetY = initY - 20;
        initialY = initY;
        initialUpY = targetY;
        targetUpY = initialY;
        newY = initialY;
        isToTarget = false;
        elapsedTime = 0;
        isSlow = true;
    }


    public void updateJump() {

        if (null == bezierCurve) {
            return;
        }
        Vector2 point = bezierCurve.valueAt(new Vector2(), t);
        position.set(point);

        t += 0.05f;
        if (t > 1) {
            t = 1;

            // 跳跃之后,执行下落缓动
            this.state = "fall";

            // "跳跃完之后《position.x=" + position.x + ";getX()=" + getX()
            execFall();
        }
    }

    /**
     * 跳跃下一行
     */
    public void cJump() {

        String[] ranges = this.targetName.split(":");
        int keyX = 1 + Integer.parseInt(ranges[0]);
        int keyY = Integer.parseInt(ranges[1]);
        String row = String.valueOf(keyX);

        // 最后一行,不能再往下跳了
        if (!Constants.ROW_CENTERPOINT.containsKey(row)) {
//            this.jump = false;
//            t = 1.0f;
            createAttackPoint();
            return;
        }
        Axis leftAxis = searchJumpAxis(row, keyY);

        float targetX = leftAxis.getX();
        float targetY = leftAxis.getY() + COMMON_SHIP_HEIGHT;

        float startX = getX();
        float startY = getY();
        Vector2 startPos = new Vector2(startX, startY);
        float controlTargetX = startX - 10;
        float controlTargetY = startY + 10;

        Vector2 controlPoint = new Vector2(controlTargetX, controlTargetY);
        Vector2 endPoint = new Vector2(targetX, targetY);

        position = new Vector2(startPos);
        bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        t = 0;
        isToTarget = false;
        elapsedTime = 0;
    }

    /**
     * 攻击玩家
     */
    private boolean isAttackPlayer;

    /**
     * 到了最后一行,开始攻击玩家
     * 现在测试是,落到云彩的附近,就算攻击了玩家,玩家掉血
     */
    private void createAttackPoint() {

        Axis leftAxis = new Axis(100, -100);
        float targetX = leftAxis.getX();
        float targetY = leftAxis.getY() + COMMON_SHIP_HEIGHT;
        float startX = getX();
        float startY = getY();
        Vector2 startPos = new Vector2(startX, startY);
        float controlTargetX = startX - 10;
        float controlTargetY = startY + 10;
        Vector2 controlPoint = new Vector2(controlTargetX, controlTargetY);
        Vector2 endPoint = new Vector2(targetX, targetY);
        position = new Vector2(startPos);
        bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        t = 0;
        isToTarget = false;
        elapsedTime = 0;
        isAttackPlayer = true;
    }

    /**
     * (算了,再重新想个算法)
     * 寻找可以跳的节点
     * 首选是往左下
     *
     * @param keyX
     * @param row
     * @param keyY
     * @return
     */
    private Axis searchJumpAxis(String row, int keyY) {

        Axis nextAxis = null;
        List<CenterPoint> centerPoints = Constants.ROW_CENTERPOINT.get(row);
        if (centerPoints.size() > keyY) {
            CenterPoint centerPoint = centerPoints.get(keyY);
            nextAxis = centerPoint.getAxis();
        } else {
            keyY = centerPoints.size() - 1;
            CenterPoint centerPoint = centerPoints.get(keyY);
            nextAxis = centerPoint.getAxis();
        }
        this.targetName = row + ":" + keyY;
        return nextAxis;
    }


    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {

    }

    public MonsterShip setTargetName(String targetName) {
        this.targetName = targetName;
        return this;
    }

    public void forceFree() {
        Controllers.getInstance().getMonsterFactoryController().forceFree( this );

        this.position = null;
        notRender = true;
        bezierCurve = null;
    }

    @Override
    public void reset() {
        clearActions();
        isDead = true;
        world.update( item, -100, -100 );
        resolve();
        remove();
    }
}
