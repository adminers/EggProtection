package com.rondeo.pixwarsspace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Title: WorkTest
 * @Description: WorkTest
 * @Company: www.qiaweidata.com
 * @author: shenshilong
 * @date: 2024-04-03
 * @version: V1.0
 */
public class WorkTest {

     public static void main(String[] args) {

         generate();
     }

    private static void generate() {
        List<Integer> generatedNumbers = new ArrayList<>();
        Random random = new Random();
        List<Integer> result = Stream.generate(() -> random.nextInt(21))
                .filter(n -> !generatedNumbers.contains(n))
                .limit(20)
                .collect(Collectors.toList());
        Collections.shuffle(result);

        System.out.println(result.get(0));
    }

    private static void batchSql() {
        List<Integer> dataList = new ArrayList<>();
        for (int i = 0; i < 10001; i++) {
            dataList.add(i);
        }
        // 假设 dataList 包含了10000条数据

        int batchSize = 100; // 每次提交的数据量

        IntStream.range(0, (dataList.size() + batchSize - 1) / batchSize)
                 .mapToObj(i -> dataList.subList(i * batchSize, Math.min((i + 1) * batchSize, dataList.size())))
                 .forEach(batch -> {
                     // 执行提交操作
                     System.out.println("提交了" + batch.size() + "条数据");
                 });
    }
}
