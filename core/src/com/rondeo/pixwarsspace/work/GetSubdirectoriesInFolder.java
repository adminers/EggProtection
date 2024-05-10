package com.rondeo.pixwarsspace.work;

public class GetSubdirectoriesInFolder {

    public static String a = "t\t\t\t\t\t\t\t\t\t  \nsdf{0}t" +
            "\t\t\t\t\t\t\t\t\t  {1}";
//    public static void main(String[] args) {
//        try {
//            // 指定要读取的文件夹路径
//            Path folderPath = Paths.get("C:\\Users\\Administrator\\Downloads\\gamemaker.io");
//
//            // 使用Files.walk()方法获取文件夹下的所有子文件夹路径
//            Stream<Path> subdirectories = Files.walk(folderPath)
//                    .filter(Files::isDirectory)
//                    .filter(path -> !path.equals(folderPath)); // 排除根文件夹本身
//
//            // 遍历输出所有子文件夹路径
//            subdirectories.forEach(folder -> {
//
//                Path filePathToCheck = folder.resolve("1.png");
//                String format;
//                if (Files.exists(filePathToCheck)) {
//                    format = MessageFormat.format(ReadAllFilesInFolder.str, "./" + folder.getFileName() + "/1.png", folder , "asdf");
//                } else {
//                    format = MessageFormat.format(ReadAllFilesInFolder.str, "./" + folder.getFileName() + "/1.jpg", folder , "asdf");
//                }
////                String format = ReadAllFilesInFolder.str.replaceAll("\\{0}", folder.toString()).replaceAll("\\{1}", folder.toString());
//                System.out.println(format);
//            });
//
//        } catch (IOException e) {
//            System.out.println("Error getting subdirectories: " + e.getMessage());
//        }
//    }
}
