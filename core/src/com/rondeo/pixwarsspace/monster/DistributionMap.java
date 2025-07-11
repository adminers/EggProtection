package com.rondeo.pixwarsspace.monster;

/**
 * @Title: DistributionMap
 * @Description: DistributionMap
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-03-30
 * @version: V1.0
 */
public class DistributionMap {

    private String name;

    /**
     * 初始的话,就是下面的目标点
     */
    private String targetName;

    public DistributionMap() {
    }

    public DistributionMap(String name, String targetName) {
        this.name = name;
        this.targetName = targetName;
    }

    public String getName() {
        return this.name;
    }

    public DistributionMap setName(String name) {
        this.name = name;
        return this;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public DistributionMap setTargetName(String targetName) {
        this.targetName = targetName;
        return this;
    }

    @Override
    public String toString() {
        return "DistributionMap{" +
                "name='" + name + '\'' +
                ", targetName='" + targetName + '\'' +
                '}';
    }
}
