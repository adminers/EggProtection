package com.qiaweidata.starriverdefense.test.po;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: MapBlock
 * @Description: MapBlock
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-28
 * @version: V1.0
 */
public class MapBlock {

    public static List<List<ButtonImage>> MAP_1;

    static {
//        MAP_1 = new Gson().fromJson(gdxFileString("lib/t_map/level1"), new TypeToken<List<List<ButtonImage>>>() {}.getType());
        try {
            MAP_1 = new Gson().fromJson(  new String(Files.readAllBytes(Paths.get("E:\\giteeWork\\GitHub\\pixwars-space-impact\\assets\\lib\\t_map\\level1"))), new TypeToken<List<List<ButtonImage>>>() {}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        float E = 16;
        float D = 24;
        List<List<CenterPoint>> centerPoints = new ArrayList<>();
        for (List<ButtonImage> buttonImages : MAP_1) {
            List<CenterPoint> centerPoint = new ArrayList<>();
            centerPoints.add(centerPoint);
            for (ButtonImage buttonImage : buttonImages) {
                FloatAttributes attr = new FloatAttributes();
                Axis axis = buttonImage.getAxis();
                float x = axis.getX();
                float y = axis.getY();
                attr.setLeft(new com.qiaweidata.starriverdefense.test.po.Axis(x - E * 2, y));
                attr.setLeftTop(new com.qiaweidata.starriverdefense.test.po.Axis(x - E, y + D));
                attr.setLeftBottom(new com.qiaweidata.starriverdefense.test.po.Axis(x - E, y - D));
                attr.setRight(new com.qiaweidata.starriverdefense.test.po.Axis(x + E * 2, y));
                attr.setRightTop(new com.qiaweidata.starriverdefense.test.po.Axis(x + E, y + D));
                attr.setRightBottom(new com.qiaweidata.starriverdefense.test.po.Axis(x + E, y - D));

                CenterPoint centerPointPojo = new CenterPoint(axis, attr);
                centerPoint.add(centerPointPojo);
            }
        }
        System.out.println(centerPoints);
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
