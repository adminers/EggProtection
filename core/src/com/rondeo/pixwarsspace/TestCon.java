//package com.rondeo.pixwarsspace;
//
//
//import java.text.MessageFormat;
//
///**
// * @Title: TestCon
// * @Description: TestCon
// * @Company: www.qiaweidata.com
// * @author: shenshilong
// * @date: 2024-03-22
// * @version: V1.0
// */
//public class TestCon {
//
//    private static float w = 32;
//    static float h = 34;
//
//    /**
//     * 首个点位(10, 70)
//     */
//    static int firstX = 10;
//    static int firstY = 70;
//
//    /**
//     * 46 是试出来的,通过第一排=70,第二排y=46,这样的距离比较好(同理,10也是试出来的)
//     */
//    static int tryX = 10;
//    static int tryY = 46;
//
//    /**
//     * 横向错位,因为要展示阶梯形状,试出来的差26挺好
//     */
//    static int tryCrossX = 26;
//    static int heightDifference = 70 - tryY;
//
//    public static void main(String[] args) {
//
//        int firstXDifference = tryCrossX - tryX;
//
//        System.out.println("高度差距:" + heightDifference);
//
//        System.out.println("错位,第一个x差值:" + firstXDifference);
//
//        System.out.println("每个间距(宽度):" + w);
//
//        int colNum = 5;
//        int row = 5;
//
//        String str = "'{'\n" +
//                "            \"axis\" : '{'\"x\": {0},\"y\": {1}'}',\n" +
//                "            \"image\" : \"block_1\",\n" +
//                "            \"name\" : \"{2}\",\n" +
//                "            \"row\" : \"{3}\",\n" +
//                "            \"col\" : \"{4}\"\n" +
//                "        '}',";
//        StringBuffer sb = new StringBuffer();
//
//        for (int i = 0; i < row; i++) {
//            int tempX = firstX + firstXDifference * i;
//            int tempY = firstY - heightDifference * i;
//
//            // 修复BUG,寻找跳跃点的时候,下标是从0开始的,故把负的去掉
////            int tempCol = 0;
//
//            // 前面追加
//            for (int j = i; j > 0; j--) {
//                float v = tempX - w * (j);
//                String format = MessageFormat.format(str, v, tempY, i + ":-" + j, i, "-" + j);
//                sb.append(format);
////                tempCol++;
//            }
//            for (int j = 0; j < colNum; j++) {
//                String format = MessageFormat.format(str, tempX + (j * w), tempY, i + ":" + j, i, j);
//                sb.append(format);
////                tempCol++;
//            }
//            sb.append("\n");
//        }
//        System.out.println(sb);
//    }
//
//    /**
//     * 根据地铁里的分析计算
//     */
//    private void removePoints() {
//
//
//    }
//}
