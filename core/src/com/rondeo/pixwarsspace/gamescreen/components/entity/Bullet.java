package com.rondeo.pixwarsspace.gamescreen.components.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import com.dongbat.jbump.Response.Result;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.components.Controllers;
import com.rondeo.pixwarsspace.gamescreen.components.Entity;
import com.rondeo.pixwarsspace.gamescreen.components.Outbound;
import com.rondeo.pixwarsspace.utils.SoundController;

import static com.badlogic.gdx.math.MathUtils.PI;
import static com.badlogic.gdx.math.MathUtils.atan2;

public class Bullet extends Actor implements Entity, Disposable, Poolable {
    World<Entity> world;
    Item<Entity> item;
    Rect rect;
    Result result;
    Collision collision;

    private float speed = 10; // 添加一个速度变量，默认值为300

    CollisionFilter collisionFilter = new CollisionFilter() {
        @SuppressWarnings( "rawtypes" )
        @Override
        public Response filter(Item item, Item other) {
            /*if( other.userData instanceof Outbound ) {
                forceFree();
                return null;
            }
            if( other.userData instanceof BossController.BossParts && top ) {
                BossController.BossParts part = (BossController.BossParts) other.userData;
                if( part.life > 0 ) {
                    if( part.isHit + 100 < System.currentTimeMillis() ) {
                        part.isHit = System.currentTimeMillis() + 100;
                        SoundController.getInstance().hurt.play();
                        part.reduceLife();
                        forceFree();
                    }
                    //forceFree();
                }
            }*/
            if( other.userData instanceof Outbound )
                return Response.cross;
            if( other.userData instanceof BossParts && top )
                return Response.cross;
            if( other.userData instanceof Ship && !top  )
                return Response.cross;
            if( other.userData instanceof EnemyShip && top && !((EnemyShip)other.userData).invulnerable && ((EnemyShip)other.userData).life > 0 )
                return Response.cross;
            if( other.userData instanceof Bullet && ((Bullet)other.userData).top != top )
                return Response.cross;
            if( other.userData instanceof SnakeEnemyShip )
                return Response.cross ;
            return null;
        }

    };

    TextureRegion[] bulletRegion;
    boolean isDead = false;
    boolean top = false;
    float time = 0;
    public float dirX = 0, dirY = 0;
    public static int width = 8, height = 8;

    boolean isAngled = false;
    float angle;

    private String name = "默认";

    private Vector2 position;
    private Vector2 velocity;
    private float t = 0;
    private Bezier<Vector2> bezierCurve;

    public Bullet( World<Entity> world, float x, float y, TextureRegion... bulletRegion ) {
        this.world = world;
        item = new Item<Entity>( this );
        world.add( item, x, y, width, height );

        this.bulletRegion = bulletRegion;
        setBounds( x, y, width, height );
    }

    public void init( float x, float y, boolean top ) {
        isDead = false;
        world.update( item, x, y, width, height );
        resolve();
        this.top = top;
        this.isAngled = false;

    }

    public void init( float x, float y, boolean top , float angle) {
        isDead = false;
//        setBounds( x, y, width, height );
        world.update( item, x, y, width, height );
        resolve();
        this.top = top;
        this.angle = angle;
        this.isAngled = true;
        this.name = "角度";

        // 设置原点(原点是Actor的旋转、缩放和其他变换操作的参考点。)
        setOrigin( getWidth()/2f, getHeight()/2f );
        setRotation(angle);
    }

    public void init(float x, float y, Axis axis, boolean top , float angle) {
        isDead = false;
        world.update( item, x, y, width, height );
        resolve();
        this.top = top;
        this.angle = angle;
        this.isAngled = true;
        this.name = "角度";

        // 设置原点(原点是Actor的旋转、缩放和其他变换操作的参考点。)
        setOrigin( getWidth()/2f, getHeight()/2f );
        setRotation(angle);


        Vector2 startPos = new Vector2(x, y);

        float targetX = axis.getX();
        float targetY = axis.getY() + 110;

        float controlTarget;

        // 目标点在左侧
        if (x > targetX) {
            controlTarget = x - 50;
        } else {
            controlTarget = targetX - x;
        }
        System.out.println("y=" + y + ";x=" + x + ";targetX=" + targetX + ";targetY="  + targetY + ";");

        Vector2 controlPoint = new Vector2(controlTarget, targetY);
        Vector2 endPoint = new Vector2(targetX, axis.getY());

        position = new Vector2(startPos);
        Vector2 direction = new Vector2(endPoint).sub(startPos).nor();
        velocity = direction.scl(5);
        bezierCurve = new Bezier<>(startPos, controlPoint, endPoint);
        t = 0;
    }

    /**
     * 超出屏幕
     *
     * @return
     */
    private boolean isDriftAway() {

        if (getX() >= 2000 || getX() <= -10 || getY() >= 2000 || getY() <= -10) {
            return true;
        }
        return false;
    }

    public void update() {

        if (null == bezierCurve) {
            return;
        }
        Vector2 point = bezierCurve.valueAt(new Vector2(), t);
        position.set(point);

        t += 0.005f;
        if (t > 1) {
            t = 1;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void act( float delta ) {
        if( isDead ) {
            return;
        }

        if (isDriftAway()) {
            forceFree();
            return;
        }
        update();
        super.act(delta);
        resolve();
//        result = world.move( item, getX() + (dirX), getY() + (dirY), collisionFilter );
        result = world.move( item, position.x, position.y, collisionFilter );
        if (isAngled) {
//            result = world.move( item, getX() + dirX, getY() + 300*delta + dirY, collisionFilter );
//            System.out.println("getX()=" + getX() + "getY()=" +getY() + "dirX=" + dirX + "dirY=" + dirY +"---getRotation()=" + getRotation() +"--传过来的" + angle);
//            setRotation(angle);
        } else {

        }
        for( int i = 0; i < result.projectedCollisions.size(); i ++ ) {
            collision = result.projectedCollisions.get( i );
            if( collision.other.userData instanceof Outbound || collision.other.userData instanceof Bullet ) {
                forceFree();
                return;
            }
            if( collision.other.userData instanceof BossParts ) {
                if( ((BossParts)collision.other.userData).life > 0 ) {
                    if( ((BossParts)collision.other.userData).isHit + 100 < System.currentTimeMillis()  ) {
                        ((BossParts)collision.other.userData).isHit = System.currentTimeMillis() + 100;
                        SoundController.play( SoundController.getInstance().hurt );
                        ((BossParts)collision.other.userData).reduceLife();
                    }
                    forceFree();
                    return;
                }
            }
            if( collision.other.userData instanceof Ship ) {
                if( System.currentTimeMillis() > ((Ship)collision.other.userData).isHit + ((Ship)collision.other.userData).invTime && System.currentTimeMillis() >((Ship)collision.other.userData).invulnerable ) {
                    SoundController.play( SoundController.getInstance().hurt );
                    ((Ship)collision.other.userData).isHit = System.currentTimeMillis() + 300;
                    ((Ship)collision.other.userData).life --;
                }
                forceFree();
                return;
            }
            if( collision.other.userData instanceof EnemyShip ) {
                ((EnemyShip)collision.other.userData).isHit = System.currentTimeMillis() + 100;
                ((EnemyShip)collision.other.userData).life --;
                forceFree();
                return;
            }
            if( collision.other.userData instanceof SnakeEnemyShip ) {
                ((SnakeEnemyShip)collision.other.userData).isHit = System.currentTimeMillis() + 100;
                ((SnakeEnemyShip)collision.other.userData).life --;
                forceFree();
                return;
            }
        }
        //world.move( item, getX(), getY(), collisionFilter );


    }

    @Override
    public void draw( Batch batch, float parentAlpha ) {
        if( isDead )
            return;
        super.draw( batch, parentAlpha );

//        TextureRegion region = top ? bulletRegion[0] : bulletRegion[1];
        TextureRegion region = bulletRegion[0];
//        if (isAngled) {
//            batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
//                region.getRegionWidth(), region.getRegionHeight(),
//                1f, 1f, getRotation());
//        } else {
//            batch.draw(region, getX(), getY(), getWidth(), getHeight() );
//            batch.draw(region, getX(), getY(), getWidth(), getHeight() );
        batch.draw( region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );

//        }

    }

    @Override
    public void dispose() {
        
    }

    public void forceFree() {
        Controllers.getInstance().bulletController().forceFree( this );
    }

    @Override
    public void reset() {
        isDead = true;
        world.update( item, -10, -10 );
        resolve();
        remove();
    }

    public void resolve() {
        rect = world.getRect( item );
        setBounds( rect.x, rect.y, rect.w, rect.h );
    }

    public static void main(String[] args) {
        float x = 178;
        float y = 57;
        float angle = MathUtils.atan2(y, x);
        float angleDegrees = angle * (180 / PI);
        System.out.println(x);
        System.out.println(y);
        System.out.println(angleDegrees);

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
//        aVoid();
        bVoid();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

        float angleInDegrees = 90 - 76.41907f;
        float[] floats = moveTest(178.0f, 57.0f, -angleInDegrees);
        for (int i = 0; i < 10; i++) {
            floats = moveTest(floats[0], floats[1], -angleInDegrees);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
        moveing();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
        moveing2();

    }

    public static void moveing2() {

        float startX = 178.0f;
        float startY = 57.0f;
        float endX = 0;
        float endY = 100;
        double speed = 5.0; // 移动速度

        double distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)); // 计算起点到终点的距离
        double moveDistance = Math.min(speed, distance); // 每帧移动的距离
        double alpha = moveDistance / distance; // 计算插值比例
        float v = (endX - startX) * (float) alpha;
        System.out.println("(endX - startX) * alpha==" + v);
        System.out.println("(endY - startY) * alpha==" + (endY - startY) * alpha);


        float currentX = startX;
        float currentY = startY;

        // 计算途径坐标
        double x = currentX + (endX - startX) * alpha;
        double y = currentY + (endY - startY) * alpha;

        System.out.println("Current Position: (" + x + ", " + y + ")");
    }

    public static void moveing() {
        double startX = 178.0;
        double startY = 57.0;
        double endX = 0;
        double endY = 100;
        double speed = 5.0; // 移动速度

        double distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)); // 计算起点到终点的距离
        double remainingDistance = distance;
        double currentX = startX;
        double currentY = startY;

        while (remainingDistance > 0) {
            double moveDistance = Math.min(speed, remainingDistance); // 每帧移动的距离
            double alpha = moveDistance / distance; // 计算插值比例

            // 计算途径坐标
            double x = currentX + (endX - startX) * alpha;
            double y = currentY + (endY - startY) * alpha;

            System.out.println("Current Position: (" + x + ", " + y + ")");

            currentX = x;
            currentY = y;
            remainingDistance -= moveDistance;
        }
    }

    public static void bVoid() {

        float xA = 0f;
        float yA = 100f;

        float xB = 178.0f;
        float yB = 57.0f;
        //float angle = MathUtils.atan2(enemyY - getY(), enemyX - getX()) * MathUtils.radiansToDegrees;

        float relativeX = xB - xA;
        float relativeY = yB - yA;
        float angleDeg = com.badlogic.gdx.math.MathUtils.atan2(relativeY, relativeX) * MathUtils.radiansToDegrees;
        if (angleDeg < 0) {
            angleDeg += 360;
        }
        System.out.println("公司计算：" + angleDeg);

        // 玩家的位置
        Vector2 playerPosition = new Vector2(xB, yB);

        // 敌人的位置
        Vector2 enemyPosition = new Vector2(xA, yA);

        // 计算玩家朝向敌人的向量
        Vector2 direction = new Vector2(enemyPosition.x - playerPosition.x, enemyPosition.y - playerPosition.y);

        // 计算玩家朝向敌人的角度
        float angle2 = direction.angleDeg(); // 默认返回角度值在 -180 到 180 之间

        // 转换角度值到 0 到 360 范围
//        if (angle2 < 0) {
//            angle2 += 360;
//        }

        System.out.println("玩家朝向敌人的角度为: " + (angle2 - 90.0f) + " 度");
    }

    public static float[] moveTest(float x, float y, float angleInDegrees) {

        float distance = 5; // 移动距离为5

        float angleInRadians = MathUtils.degreesToRadians * angleInDegrees;
        float newX = x + distance * MathUtils.cos(angleInRadians);
        float newY = y + distance * MathUtils.sin(angleInRadians);

        System.out.println("当前位置: (" + x + ", " + y + ")");
        System.out.println("下一个位置: (" + newX + ", " + newY + ")");
        return new float[]{newX, newY};
    }


    public static void aVoid() {

        // 当前物体位置
        float currentX = 100;
        float currentY = 100;

        // 目标位置
        float targetX = 200;
        float targetY = 200;

        // 移动速度
        float speed = 1.0f;

        // 计算目标方向向量
        float dx = targetX - currentX;
        float dy = targetY - currentY;

        // 计算距离
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // 如果距离小于移动速度，则直接到达目标位置
        if (distance <= speed) {
            currentX = targetX;
            currentY = targetY;
        } else {
            // 计算下一步位置
            float ratio = speed / distance;
            float nextX = currentX + dx * ratio;
            float nextY = currentY + dy * ratio;

            // 更新当前位置
            currentX = nextX;
            currentY = nextY;
        }

        // 输出下一步位置
        System.out.println("Next position: " + currentX + ", " + currentY);
    }

}
