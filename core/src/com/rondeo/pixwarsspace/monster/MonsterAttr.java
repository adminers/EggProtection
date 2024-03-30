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

    private String textureRegion;
    private String fall;
    private Axis targetPoint;
    private Axis generatePoint;
    private int life;
    private String name;

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

    public Axis getTargetPoint() {
        return this.targetPoint;
    }

    public MonsterAttr setTargetPoint(Axis targetPoint) {
        this.targetPoint = targetPoint;
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

    @Override
    public String toString() {
        return "MonsterAttr{" +
                "textureRegion='" + textureRegion + '\'' +
                ", fall='" + fall + '\'' +
                ", targetPoint=" + targetPoint +
                ", generatePoint=" + generatePoint +
                ", life=" + life +
                ", name='" + name + '\'' +
                '}';
    }
}
