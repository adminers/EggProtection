package com.rondeo.pixwarsspace.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.cells.po.ButtonImage;
import com.rondeo.pixwarsspace.gamescreen.components.Enemy;
import com.rondeo.pixwarsspace.gamescreen.components.Player;
import com.rondeo.pixwarsspace.gamescreen.components.entity.BrickShip;
import com.rondeo.pixwarsspace.gamescreen.components.entity.PointShip;
import com.rondeo.pixwarsspace.gamescreen.components.entity.SlugShip;
import com.rondeo.pixwarsspace.gamescreen.pojo.CenterPoint;
import com.rondeo.pixwarsspace.gamescreen.pojo.EnemyJumpCoordinate;
import com.rondeo.pixwarsspace.gamescreen.pojo.MapPointBlock;
import com.rondeo.pixwarsspace.gamescreen.pojo.SulgPoint;
import com.rondeo.pixwarsspace.monster.MonsterAttr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 落脚点(中心点)
     */
    public static Map<String, CenterPoint> CENTER_POINTS = new HashMap<>();

    public static Map<String, MapPointBlock> POINT_BRICK_SHIPS = new HashMap<>();

    public static Map<String, SulgPoint> SLUGSHIP = new HashMap<>();

    /**
     * 怪物预建对象
     */
    public static Map<String, MonsterAttr> MONSTER_ATTR = new HashMap<>();

    /**
     * 文件中的地图,普遍要加上这个值,才在屏幕显示居中
     */
    public static float COMMON_SHIP_HEIGHT = 200;

    static {

        Type listType = new TypeToken<List<List<Axis>>>() {}.getType();

        LEVEL1_ENEMYS = new Gson().fromJson(gdxFileString("lib/barn/level1"), listType);

//        LEVEL1_ENEMYS = new ArrayList<>();
//        LEVEL1_ENEMYS.add(new Axis(52.06f, 50.0f));
//        LEVEL1_ENEMYS.add(new Axis(22.06f, 50));
//        LEVEL1_ENEMYS.add(new Axis(32.06f, 110));
//        LEVEL1_ENEMYS.add(new Axis(42.06f, 50));
//        LEVEL1_ENEMYS.add(new Axis(152.06f, 0));
        System.out.println(LEVEL1_ENEMYS);

        MAP_1 = new Gson().fromJson(gdxFileString("lib/t_map/level1"), new TypeToken<List<List<ButtonImage>>>() {}.getType());

        CARDS = new Gson().fromJson(gdxFileString("lib/card/CardLeave"), new TypeToken<List<List<ButtonImage>>>() {}.getType());
        PLATES = new Gson().fromJson(gdxFileString("lib/t_map/Plate"), new TypeToken<List<List<ButtonImage>>>() {}.getType());

        MONSTER_ATTR = new Gson().fromJson(gdxFileString("lib/t_map/monster/MonsterFactory.json"), new TypeToken<Map<String, MonsterAttr>>() {}.getType());
    }

    private Constants() {
    }

    public static String gdxFileString(String fileName) {
        FileHandle fileHandle = Gdx.files.internal(fileName);
        return fileString(fileHandle);
    }

    public static String fileString(FileHandle fileHandle) {

        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileHandle.read()), 1024);
        try {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
        } catch (Exception ex) {
            throw new GdxRuntimeException("Error reading texture atlas file: ", ex);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
        return sb.toString();
    }

    public static void centerPoint() {

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
    }

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
        attr.setLeft(new Axis(x - E * 2, y));
        attr.setLeftTop(new Axis(x - E, y + D));
        attr.setLeftBottom(new Axis(x - E, y - D));
        attr.setRight(new Axis(x + E * 2, y));
        attr.setRightTop(new Axis(x + E, y + D));
        attr.setRightBottom(new Axis(x + E, y - D));
        return new CenterPoint(axis, new Axis(x, y), attr);
    }
}
