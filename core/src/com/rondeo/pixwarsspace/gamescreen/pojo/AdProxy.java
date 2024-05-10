package com.rondeo.pixwarsspace.gamescreen.pojo;

/**
 * @Title: AdProxy
 * @Description: AdProxy
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-05-10
 * @version: V1.0
 */
public class AdProxy {

    private String name;

    public AdProxy() {

        // 模拟广告

    }

    public String getName() {
        return this.name;
    }

    public AdProxy setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "AdProxy{" +
                "name='" + name + '\'' +
                '}';
    }
}
