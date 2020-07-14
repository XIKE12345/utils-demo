package com.jieyun.test.interview;

import java.io.File;

public class FileName {

    public static void main(String[] args) {
        int sumCount = 0;
        FileName listfile = new FileName();
        int count = listfile.listJavaFile("/Users/huike/apache-maven-3.6.0/conf");
        sumCount += count;
        System.out.println("总数为：" + sumCount);

    }

    public int listJavaFile(String fileName) {
        int count = 0;
        File file = new File(fileName);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length - 1; i++) {
                String name = files[i].getName();

                System.out.println(name);

                if (files[i].isDirectory()) {
                    String path = files[i].getPath();
                    // 递归
                    listJavaFile(path);
                }
            }
            count = files.length;
        }
        return count;
    }
}
