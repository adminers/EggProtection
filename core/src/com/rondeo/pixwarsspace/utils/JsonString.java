package com.rondeo.pixwarsspace.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.StreamUtils;
import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;
import com.rondeo.pixwarsspace.gamescreen.cells.po.ButtonImage;
import com.rondeo.pixwarsspace.monster.DistributionMap;
import com.rondeo.pixwarsspace.monster.MonsterAttr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: JsonString
 * @Description: JsonString
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-04-30
 * @version: V1.0
 */
public class JsonString {

    public static final Json JSON_GDX = new Json();


    public static List<List<ButtonImage>> createPlate() {

        /**
         * 当初想的是,每关都可能不同的我方玩家卡牌
         * 现在看来,都一样,先copy
         */
        /*String plate = "[{\"axis\":{\"x\":35,\"y\":60},\"image\":\"lib/t_map/plateImage/tundra.png\"},{\"axis\":{\"x\":70,\"y\":60},\"image\":\"lib/t_map/plateImage/water.png\"},{\"axis\":{\"x\":105,\"y\":60},\"image\":\"lib/t_map/plateImage/tundra.png\"},{\"axis\":{\"x\":140,\"y\":60},\"image\":\"lib/t_map/plateImage/water.png\"},{\"axis\":{\"x\":17.5,\"y\":70},\"image\":\"lib/t_map/plateImage/sandlands_tundra.png\"},{\"axis\":{\"x\":52.5,\"y\":70},\"image\":\"lib/t_map/plateImage/greenlands_tundra.png\"},{\"axis\":{\"x\":87.5,\"y\":70},\"image\":\"lib/t_map/plateImage/sandlands_tundra.png\"},{\"axis\":{\"x\":122.5,\"y\":70},\"image\":\"lib/t_map/plateImage/greenlands_tundra.png\"},{\"axis\":{\"x\":157.5,\"y\":70},\"image\":\"lib/t_map/plateImage/sandlands_tundra.png\"},{\"axis\":{\"x\":35,\"y\":80},\"image\":\"lib/t_map/plateImage/tundra.png\"},{\"axis\":{\"x\":70,\"y\":80},\"image\":\"lib/t_map/plateImage/water.png\"},{\"axis\":{\"x\":105,\"y\":80},\"image\":\"lib/t_map/plateImage/tundra.png\"},{\"axis\":{\"x\":140,\"y\":80},\"image\":\"lib/t_map/plateImage/water.png\"}]";
        List<List<ButtonImage>> plates = new ArrayList<>(1);
        List<ButtonImage> level = new ArrayList<>(100);
        JsonValue jsonValue = new JsonReader().parse(plate);
        for (JsonValue value : jsonValue) {
            ButtonImage obj = JSON_GDX.fromJson(ButtonImage.class, value.toString());
            level.add(obj);
        }
        plates.add(level);*/

        List<List<ButtonImage>> plates = new ArrayList<>(1);
        List<ButtonImage> dataList = new ArrayList<>(1);
        dataList.add(new ButtonImage(new Axis(35.0f, 60.0f), "lib/t_map/plateImage/tundra.png"));
        dataList.add(new ButtonImage(new Axis(70.0f, 60.0f), "lib/t_map/plateImage/water.png"));
        dataList.add(new ButtonImage(new Axis(105.0f, 60.0f), "lib/t_map/plateImage/tundra.png"));
        dataList.add(new ButtonImage(new Axis(140.0f, 60.0f), "lib/t_map/plateImage/water.png"));
        dataList.add(new ButtonImage(new Axis(17.5f, 70.0f), "lib/t_map/plateImage/sandlands_tundra.png"));
        dataList.add(new ButtonImage(new Axis(52.5f, 70.0f), "lib/t_map/plateImage/greenlands_tundra.png"));
        dataList.add(new ButtonImage(new Axis(87.5f, 70.0f), "lib/t_map/plateImage/sandlands_tundra.png"));
        dataList.add(new ButtonImage(new Axis(122.5f, 70.0f), "lib/t_map/plateImage/greenlands_tundra.png"));
        dataList.add(new ButtonImage(new Axis(157.5f, 70.0f), "lib/t_map/plateImage/sandlands_tundra.png"));
        dataList.add(new ButtonImage(new Axis(35.0f, 80.0f), "lib/t_map/plateImage/tundra.png"));
        dataList.add(new ButtonImage(new Axis(70.0f, 80.0f), "lib/t_map/plateImage/water.png"));
        dataList.add(new ButtonImage(new Axis(105.0f, 80.0f), "lib/t_map/plateImage/tundra.png"));
        dataList.add(new ButtonImage(new Axis(140.0f, 80.0f), "lib/t_map/plateImage/water.png"));
        plates.add(dataList);
        return plates;
    }

    public static List<List<ButtonImage>> createCards() {

        /*List<List<ButtonImage>> plates = new ArrayList<>();


         // 创建包含ButtonImage对象的JSON数据
        String jsonData = gdxFileString("lib/card/CardLeave");

        // 将JSON数据转换为Object数组
        Object[] objArray = JSON_GDX.fromJson(Object[].class, jsonData);

        // 将Object数组转换为List<List<ButtonImage>>数据结构
        for (Object obj : objArray) {
            List<ButtonImage> buttonImages = new ArrayList<>();
            Array innerArray = (Array) obj;
            for (Object innerObj : innerArray) {
                ButtonImage buttonImage = JSON_GDX.fromJson(ButtonImage.class, innerObj.toString());
                buttonImages.add(buttonImage);
            }
            plates.add(buttonImages);
        }*/

        List<List<ButtonImage>> plates = new ArrayList<>();

        ButtonImage buttonImage1 = new ButtonImage();
        buttonImage1.setImage("lib/card/card_fantasy_back.png");
        buttonImage1.setAxis(new Axis(35, 10));
        buttonImage1.setName("extend");

        ButtonImage buttonImage2 = new ButtonImage();
        buttonImage2.setImage("lib/card/card_scifi_back.png");
        buttonImage2.setAxis(new Axis(70, 10));
        buttonImage2.setName("attack");

        ButtonImage buttonImage3 = new ButtonImage();
        buttonImage3.setImage("lib/card/card_vampire_back.png");
        buttonImage3.setAxis(new Axis(105, 10));
        buttonImage3.setName("protect");

        ButtonImage buttonImage4 = new ButtonImage();
        buttonImage4.setImage("lib/card/carta_gioco_med_mostro.png");
        buttonImage4.setAxis(new Axis(140, 10));
        buttonImage4.setName("electricShock");
        List<ButtonImage> buttonImages = new ArrayList<>();
        buttonImages.add(buttonImage1);
        buttonImages.add(buttonImage2);
        buttonImages.add(buttonImage3);
        buttonImages.add(buttonImage4);

        plates.add(buttonImages);
        return plates;
    }

    public static List<List<ButtonImage>> mapLevel() {

        /*List<List<ButtonImage>> plates = new ArrayList<>();

         // 创建包含ButtonImage对象的JSON数据
        String jsonData = gdxFileString("lib/t_map/level1");

        // 将JSON数据转换为Object数组
        Object[] objArray = JSON_GDX.fromJson(Object[].class, jsonData);

        // 将Object数组转换为List<List<ButtonImage>>数据结构
        for (Object obj : objArray) {
            List<ButtonImage> buttonImages = new ArrayList<>();
            Array innerArray = (Array) obj;
            for (Object innerObj : innerArray) {
                ButtonImage buttonImage = JSON_GDX.fromJson(ButtonImage.class, innerObj.toString());
                buttonImages.add(buttonImage);
            }
            plates.add(buttonImages);
        }*/

        List<List<ButtonImage>> plates = new ArrayList<>();
        List<ButtonImage> row0 = new ArrayList<>();
        row0.add(new ButtonImage(new Axis(10, 70), "block_1", "0:0", "0", "0"));
        row0.add(new ButtonImage(new Axis(42, 70), "block_1", "0:1", "0", "1"));
        row0.add(new ButtonImage(new Axis(74, 70), "block_1", "0:2", "0", "2"));
        row0.add(new ButtonImage(new Axis(106, 70), "block_1", "0:3", "0", "3"));
        row0.add(new ButtonImage(new Axis(138, 70), "block_1", "0:4", "0", "4"));
        row0.add(new ButtonImage(new Axis(26, 46), "block_1", "1:0", "1", "0"));
        row0.add(new ButtonImage(new Axis(58, 46), "block_1", "1:1", "1", "1"));
        row0.add(new ButtonImage(new Axis(90, 46), "block_1", "1:2", "1", "2"));
        row0.add(new ButtonImage(new Axis(122, 46), "block_1", "1:3", "1", "3"));
        row0.add(new ButtonImage(new Axis(154, 46), "block_1", "1:4", "1", "4"));
        row0.add(new ButtonImage(new Axis(42, 22), "block_1", "2:0", "2", "0"));
        row0.add(new ButtonImage(new Axis(74, 22), "block_1", "2:1", "2", "1"));
        row0.add(new ButtonImage(new Axis(106, 22), "block_1", "2:2", "2", "2"));
        row0.add(new ButtonImage(new Axis(138, 22), "block_1", "2:3", "2", "3"));
        row0.add(new ButtonImage(new Axis(26, -2), "block_1", "3:0", "3", "-1"));
        row0.add(new ButtonImage(new Axis(58, -2), "block_1", "3:1", "3", "0"));
        row0.add(new ButtonImage(new Axis(90, -2), "block_1", "3:2", "3", "1"));
        row0.add(new ButtonImage(new Axis(122, -2), "block_1", "3:3", "3", "2"));
        row0.add(new ButtonImage(new Axis(154, -2), "block_1", "3:4", "3", "3"));
        row0.add(new ButtonImage(new Axis(42, -26), "block_1", "4:0", "4", "-1"));
        row0.add(new ButtonImage(new Axis(74, -26), "block_1", "4:1", "4", "0"));
        row0.add(new ButtonImage(new Axis(106, -26), "block_1", "4:2", "4", "1"));
        row0.add(new ButtonImage(new Axis(138, -26), "block_1", "4:3", "4", "2"));
        plates.add(row0);
        return plates;
    }

    public static List<List<DistributionMap>> createDistribution() {

        /*List<List<DistributionMap>> plates = new ArrayList<>();

         // 创建包含ButtonImage对象的JSON数据
        String jsonData = gdxFileString("lib/t_map/monster/DistributionMap.json");

        // 将JSON数据转换为Object数组
        Object[] objArray = JSON_GDX.fromJson(Object[].class, jsonData);

        // 将Object数组转换为List<List<ButtonImage>>数据结构
        for (Object obj : objArray) {
            List<DistributionMap> buttonImages = new ArrayList<>();
            Array innerArray = (Array) obj;
            for (Object innerObj : innerArray) {
                DistributionMap buttonImage = JSON_GDX.fromJson(DistributionMap.class, innerObj.toString());
                buttonImages.add(buttonImage);
            }
            plates.add(buttonImages);
        }*/

        List<List<DistributionMap>> plates = new ArrayList<>();

        List<DistributionMap> distribution1 = new ArrayList<>();
        distribution1.add(new DistributionMap("slug", "0:2"));
        distribution1.add(new DistributionMap("Mosquito", "0:4"));
        distribution1.add(new DistributionMap("Mosquito", "0:3"));
        plates.add(distribution1);

        List<DistributionMap> distribution2 = new ArrayList<>();
        distribution2.add(new DistributionMap("slug", "0:2"));
        distribution2.add(new DistributionMap("Mosquito", "0:4"));
        plates.add(distribution2);

        List<DistributionMap> distribution3 = new ArrayList<>();
        distribution3.add(new DistributionMap("slug", "0:2"));
        distribution3.add(new DistributionMap("Mosquito", "0:4"));
        plates.add(distribution3);

        List<DistributionMap> distribution4 = new ArrayList<>();
        distribution4.add(new DistributionMap("slug", "0:2"));
        distribution4.add(new DistributionMap("Mosquito", "0:4"));
        plates.add(distribution4);

        List<DistributionMap> distribution5 = new ArrayList<>();
        distribution5.add(new DistributionMap("slug", "0:2"));
        distribution5.add(new DistributionMap("Mosquito", "0:4"));
        plates.add(distribution5);

        List<DistributionMap> distribution6 = new ArrayList<>();
        distribution6.add(new DistributionMap("slug", "0:2"));
        distribution6.add(new DistributionMap("Mosquito", "0:4"));
        plates.add(distribution6);

        List<DistributionMap> distribution7 = new ArrayList<>();
        distribution7.add(new DistributionMap("slug", "0:2"));
        distribution7.add(new DistributionMap("Mosquito", "0:4"));
        plates.add(distribution7);
        return plates;
    }

    public static Map<String, MonsterAttr> createMonsterAttr() {

        /*String jsonData = gdxFileString("lib/t_map/monster/MonsterFactory.json");

        // 将JSON数据转换为Map<String, MonsterAttr>
        Map<String, MonsterAttr> monsterAttrMap = JSON_GDX.fromJson(HashMap.class, MonsterAttr.class, jsonData);*/

        Map<String, MonsterAttr> monsterAttrMap = new HashMap<>();
        monsterAttrMap.put("slug", new MonsterAttr(25, 20, "lib/t_map/monster/base/baseball.atlas", "idle", new Axis(50, 330), 5, "slug", 3000));
        monsterAttrMap.put("Mosquito", new MonsterAttr(25, 20, "lib/t_map/t1/SlugCannon.atlas", "SlimeOrange", new Axis(100, 330), 5, "SlugCannon", 3000));
        return monsterAttrMap;
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

}
