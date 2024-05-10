package com.rondeo.pixwarsspace.work;

public class ReadAllFilesInFolder {

    public static String str = "<div class=\"column is-one-quarter-desktop is-half-tablet\"> <div bundle=\"[object Object]\" class=\"bundle-card is-dark\"><div class=\"bundle-card-top\"><div class=\"bundle-card-image\"><img loading=\"lazy\" src=\"{0}\" alt=\"Animal SFX\" decoding=\"async\"></div> </div> <div class=\"bundle-card-bottom\"><div class=\"bundle-card-bottom-body\"><div class=\"bundle-card-title\">" +
            "{1}" +
            "</div> </div> </div></div></div>";
//    public static void main(String[] args) {
//
//
//        try {
//
//            // 指定要读取的文件夹路径
//            Path folderPath = Paths.get("C:\\Users\\Administrator\\Downloads\\gamemaker.io");
//
//            // 使用Files.walk()方法获取文件夹下的所有子文件路径
//            Stream<Path> files = Files.walk(folderPath)
//                    .filter(Files::isRegularFile);
//
//            // 遍历输出所有子文件路径
//            files.forEach(s -> {
//                String format = String.format(str, s);
//                System.out.println(format);
//            });
//
//
//
//        } catch (IOException e) {
//            System.out.println("Error reading files: " + e.getMessage());
//        }
//    }
}
