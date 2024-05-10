package com.rondeo.pixwarsspace.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.cells.po.ButtonImage;
import com.rondeo.pixwarsspace.gamescreen.components.Enemy;
import com.rondeo.pixwarsspace.gamescreen.components.LevelManager;
import com.rondeo.pixwarsspace.gamescreen.components.Player;
import com.rondeo.pixwarsspace.gamescreen.components.play.BubblesShip;
import com.rondeo.pixwarsspace.gamescreen.pojo.CenterPoint;
import com.rondeo.pixwarsspace.gamescreen.pojo.EnemyJumpCoordinate;
import com.rondeo.pixwarsspace.gamescreen.pojo.MapPointBlock;
import com.rondeo.pixwarsspace.gamescreen.pojo.SulgPoint;
import com.rondeo.pixwarsspace.monster.DistributionMap;
import com.rondeo.pixwarsspace.monster.MonsterAttr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Title: Constants
 * @Description: Constants
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-14
 * @version: V1.0
 */
public class Constants {


    public static final int CELL_HIGHT = 100;

    public static String CREATE_CELL_TYPE = "ship";

    public static TextureAtlas BARN = new TextureAtlas(Gdx.files.internal("lib/barn/alien-pro.atlas"));

    public static TextureAtlas DB = new TextureAtlas(Gdx.files.internal("lib/db/db.atlas"));

    public static Array<Enemy> ACTIVE_ENEMIES = new Array<>();

    public static Array<Player> ACTIVE_PLAYERS = new Array<>();

    public static List<List<Axis>> LEVEL1_ENEMYS;

    public static List<List<ButtonImage>> MAP_1;

    /**
     * 4中卡牌
     */
    public static List<List<ButtonImage>> CARDS;

    /**
     * 我方落脚点地图
     */
    public static List<List<ButtonImage>> PLATES;

    /**
     * 怪物生成点
     */
    public static List<List<DistributionMap>> DISTRIBUTION_MAP;

    /**
     * 落脚点(中心点)
     */
    public static Map<String, CenterPoint> CENTER_POINTS = new HashMap<>();

    public static Map<String, MapPointBlock> POINT_BRICK_SHIPS = new HashMap<>();

    public static Map<String, SulgPoint> SLUGSHIP = new HashMap<>();

    /**
     * 怪物预建对象
     */
    public static Map<String, MonsterAttr> MONSTER_ATTR;

    /**
     * 每层关卡已经出现过怪物的数量
     */
    public final static List<Integer> LEVEL_MONSTER_COUNT = new ArrayList<>(100);

    /**
     * 每层最大敌人数量
     */
    public final static List<Integer> LEVEL_MONSTER_MAX = new ArrayList<>(100);

    /**
     * 文件中的地图,普遍要加上这个值,才在屏幕显示居中
     */
    public static float COMMON_SHIP_HEIGHT = 200;

    /**
     * 所有中心点
     */
    public static final Set<CenterPoint> ALL_CENTER_POINT = new HashSet<>();

    public static Map<String, List<CenterPoint>> ROW_CENTERPOINT = new HashMap<>();

    /**
     * 记录所有防护罩,检测和敌人碰撞
     */
    public static final List<BubblesShip> bubblesShips = new ArrayList<>();

    /**
     * 最大关卡
     */
    public static int MAX_LEVEL = 100;

    /**
     * 叠加的总金币(全局可见)
     */
    public static int TOTAL_COIN = 50;

    public static Label HOME_LABEL = new Label(String.valueOf(TOTAL_COIN), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    static {

//        LEVEL1_ENEMYS = new Gson().fromJson(gdxFileString("lib/barn/level1"), listType);

        LEVEL1_ENEMYS = new ArrayList<>();
//        LEVEL1_ENEMYS.add(new Axis(52.06f, 50.0f));
//        LEVEL1_ENEMYS.add(new Axis(22.06f, 50));
//        LEVEL1_ENEMYS.add(new Axis(32.06f, 110));
//        LEVEL1_ENEMYS.add(new Axis(42.06f, 50));
//        LEVEL1_ENEMYS.add(new Axis(152.06f, 0));

        MAP_1 = JsonString.mapLevel();

        CARDS = JsonString.createCards();

        PLATES = JsonString.createPlate();

        DISTRIBUTION_MAP = JsonString.createDistribution();

        MONSTER_ATTR = JsonString.createMonsterAttr();

        // 随机增加怪物测速
//        List<DistributionMap> distributionMaps = DISTRIBUTION_MAP.get(0);
//        for (int i = 0; i < 50; i++) {
//            distributionMaps.add(distributionMaps.get(0));
//        }

        initLevelMonsterCount();
        LEVEL_MONSTER_MAX.add(3);
        LEVEL_MONSTER_MAX.add(4);
        LEVEL_MONSTER_MAX.add(10);
        LEVEL_MONSTER_MAX.add(1);
        LEVEL_MONSTER_MAX.add(20);
        LEVEL_MONSTER_MAX.add(20);
        LEVEL_MONSTER_MAX.add(20);
        LEVEL_MONSTER_MAX.add(20);
    }

    private static void initLevelMonsterCount() {

        LEVEL_MONSTER_COUNT.clear();

        // 先初始个100
        for (int i = 0; i < 100; i++) {
            LEVEL_MONSTER_COUNT.add(0);
        }
    }

    private Constants() {
    }


    /*public static void centerPoint() {

        float E = 16;
        float D = 24;
        float trimX = -2;
//        CENTER_POINTS = new ArrayList<>();
        for (List<ButtonImage> buttonImages : MAP_1) {
            List<CenterPoint> centerPoint = new ArrayList<>();
//            CENTER_POINTS.add(centerPoint);
            for (ButtonImage buttonImage : buttonImages) {
                EnemyJumpCoordinate attr = new EnemyJumpCoordinate();
                Axis axis = buttonImage.getAxis();
                float x = axis.getX() + E + trimX;
                float y = axis.getY() + D;
                attr.setLeft(new Axis(x - E * 2, y));
                attr.setLeftTop(new Axis(x - E, y + D));
                attr.setLeftBottom(new Axis(x - E, y - D));
                attr.setRight(new Axis(x + E * 2, y));
                attr.setRightTop(new Axis(x + E, y + D));
                attr.setRightBottom(new Axis(x + E, y - D));
                CenterPoint centerPointPojo = new CenterPoint(axis, new Axis(x, y), attr);
                centerPoint.add(centerPointPojo);
            }
        }
    }*/

    /**
     * 根据当前点,计算出周边坐标
     *
     * @param axis 中心点
     * @return
     */
    public static CenterPoint createAttrPoint(Axis axis) {

        float E = 16;
        float D = 24;
        float trimX = -2;
        EnemyJumpCoordinate attr = new EnemyJumpCoordinate();
        float x = axis.getX() + E + trimX;
        float y = axis.getY() + D;
//        float x = axis.getX() + trimX;
//        float y = axis.getY();
        attr.setLeft(new Axis(x - E * 2, y));
        attr.setLeftTop(new Axis(x - E, y + D));
        attr.setLeftBottom(new Axis(x - E, y - D));
        attr.setRight(new Axis(x + E * 2, y));
        attr.setRightTop(new Axis(x + E, y + D));
        attr.setRightBottom(new Axis(x + E, y - D));
        return new CenterPoint(axis, new Axis(x, y), attr);
    }

    public static void restart() {

        Constants.ACTIVE_ENEMIES.clear();
        Constants.ACTIVE_PLAYERS.clear();
        Constants.CENTER_POINTS.clear();
        Constants.bubblesShips.clear();
        Constants.POINT_BRICK_SHIPS.clear();
        Constants.ROW_CENTERPOINT.clear();
        initLevelMonsterCount();
        LevelManager.restart();
    }
}
