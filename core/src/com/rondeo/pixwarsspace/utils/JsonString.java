package com.rondeo.pixwarsspace.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rondeo.pixwarsspace.gamescreen.cells.po.ButtonImage;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: JsonString
 * @Description: JsonString
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-04-30
 * @version: V1.0
 */
public class JsonString {

    /**
     * 当初想的是,每关都可能不同的我方玩家卡牌
     * 现在看来,都一样,先copy
     */
    public static String plate = "[{\"axis\":{\"x\":35,\"y\":60},\"image\":\"lib/t_map/plateImage/tundra.png\"},{\"axis\":{\"x\":70,\"y\":60},\"image\":\"lib/t_map/plateImage/water.png\"},{\"axis\":{\"x\":105,\"y\":60},\"image\":\"lib/t_map/plateImage/tundra.png\"},{\"axis\":{\"x\":140,\"y\":60},\"image\":\"lib/t_map/plateImage/water.png\"},{\"axis\":{\"x\":17.5,\"y\":70},\"image\":\"lib/t_map/plateImage/sandlands_tundra.png\"},{\"axis\":{\"x\":52.5,\"y\":70},\"image\":\"lib/t_map/plateImage/greenlands_tundra.png\"},{\"axis\":{\"x\":87.5,\"y\":70},\"image\":\"lib/t_map/plateImage/sandlands_tundra.png\"},{\"axis\":{\"x\":122.5,\"y\":70},\"image\":\"lib/t_map/plateImage/greenlands_tundra.png\"},{\"axis\":{\"x\":157.5,\"y\":70},\"image\":\"lib/t_map/plateImage/sandlands_tundra.png\"},{\"axis\":{\"x\":35,\"y\":80},\"image\":\"lib/t_map/plateImage/tundra.png\"},{\"axis\":{\"x\":70,\"y\":80},\"image\":\"lib/t_map/plateImage/water.png\"},{\"axis\":{\"x\":105,\"y\":80},\"image\":\"lib/t_map/plateImage/tundra.png\"},{\"axis\":{\"x\":140,\"y\":80},\"image\":\"lib/t_map/plateImage/water.png\"}]";

    public static List<List<ButtonImage>> createPlate() {

        List<List<ButtonImage>> plates = new ArrayList<>(100);
        List<ButtonImage> level = new Gson().fromJson(plate, new TypeToken<List<ButtonImage>>() {}.getType());
        plates.add(level);
        return plates;
    }
}
