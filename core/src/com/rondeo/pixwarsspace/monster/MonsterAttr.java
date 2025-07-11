package com.rondeo.pixwarsspace.monster;

import com.rondeo.pixwarsspace.gamescreen.cells.po.Axis;

/**
 * @Title: MonsterAttr
 * @Description: MonsterAttr
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-30
 * @version: V1.0
 */
public class MonsterAttr {

    private int width;

    private int height;

    private String textureRegion;
    private String fall;
    private String targetName;

    private Axis generatePoint;
    private int life;
    private String name;

    /**
     * 静止时间,然后寻路跳跃
     */
    private int restTime;

    public MonsterAttr() {
    }

    public MonsterAttr(int width, int height, String textureRegion, String fall, Axis generatePoint, int life, String name, int restTime) {
        this.width = width;
        this.height = height;
        this.textureRegion = textureRegion;
        this.fall = fall;
        this.generatePoint = generatePoint;
        this.life = life;
        this.name = name;
        this.restTime = restTime;
    }

    public String getTextureRegion() {
        return this.textureRegion;
    }

    public MonsterAttr setTextureRegion(String textureRegion) {
        this.textureRegion = textureRegion;
        return this;
    }

    public String getFall() {
        return this.fall;
    }

    public MonsterAttr setFall(String fall) {
        this.fall = fall;
        return this;
    }

    public Axis getGeneratePoint() {
        return this.generatePoint;
    }

    public MonsterAttr setGeneratePoint(Axis generatePoint) {
        this.generatePoint = generatePoint;
        return this;
    }

    public int getLife() {
        return this.life;
    }

    public MonsterAttr setLife(int life) {
        this.life = life;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public MonsterAttr setName(String name) {
        this.name = name;
        return this;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public MonsterAttr setTargetName(String targetName) {
        this.targetName = targetName;
        return this;
    }

    public int getWidth() {
        return this.width;
    }

    public MonsterAttr setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return this.height;
    }

    public MonsterAttr setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getRestTime() {
        return restTime;
    }

    public MonsterAttr setRestTime(int restTime) {
        this.restTime = restTime;
        return this;
    }

    @Override
    public String toString() {
        return "MonsterAttr{" +
                "width=" + width +
                ", height=" + height +
                ", textureRegion='" + textureRegion + '\'' +
                ", fall='" + fall + '\'' +
                ", targetName='" + targetName + '\'' +
                ", generatePoint=" + generatePoint +
                ", life=" + life +
                ", name='" + name + '\'' +
                ", restTime=" + restTime +
                '}';
    }
}
