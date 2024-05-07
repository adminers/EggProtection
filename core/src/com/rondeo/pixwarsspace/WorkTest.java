package com.rondeo.pixwarsspace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Title: WorkTest
 * @Description: WorkTest
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-04-03
 * @version: V1.0
 */
//public class WorkTest {
//
//     public static void main(String[] args) {
////         extracted();
//         extracted1();
//     }
//
//    private static void extracted1() {
//        int count = 2000;
//
//        for (int i = 1; i <= count; i++) {
//            String formattedNumber = String.format("%03d", i); // 保证数字部分是3位数，例如001, 002, ..., 200
//            String str1 = "ekb_api_md_" + formattedNumber;
//            String str2 = "ekb_api_md_" + formattedNumber + "2";
//
//            System.out.println(str1);
////            System.out.println(str2);
//        }
//    }
//
//    private static void extracted() {
//        List<Integer> dataList = new ArrayList<>();
//        for (int i = 0; i < 10001; i++) {
//            dataList.add(i);
//        }
//        // 假设 dataList 包含了10000条数据
//
//        int batchSize = 100; // 每次提交的数据量
//
//        IntStream.range(0, (dataList.size() + batchSize - 1) / batchSize)
//                 .mapToObj(i -> dataList.subList(i * batchSize, Math.min((i + 1) * batchSize, dataList.size())))
//                 .forEach(batch -> {
//                     // 执行提交操作
//                     System.out.println("提交了" + batch.size() + "条数据");
//                     System.out.println(batch);
//                 });
//    }
//}
