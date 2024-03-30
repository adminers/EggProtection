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

    private String targetName;

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
