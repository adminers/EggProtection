package com.qiaweidata.starriverdefense.test;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        for (int i = 0; i < 1000; i++) {
            int index = i % list.size();
            System.out.println("When i=" + i + ", get value: " + list.get(index));
        }
    }
}
