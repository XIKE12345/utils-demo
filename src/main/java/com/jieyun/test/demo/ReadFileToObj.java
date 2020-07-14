package com.jieyun.test.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author huike
 * @desc 读取文件对象
 */
@Slf4j
public class ReadFileToObj {

    public static void main(String[] args) {
        // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        String pathname = "/Users/huike/IdeaProjects/test/src/main/resources/unzip/可一书店/BGT1-1.16";
        // 要读取以上路径的input。txt文件
        File filename = new File(pathname);
        // 建立一个输入流对象reader
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            // 建立一个对象，它把文件内容转成计算机能读懂的语言
            BufferedReader br = new BufferedReader(reader);
            Object line = "";
            line = br.readLine();
            while (line != null) {
                // 一次读入一行数据
                line = br.readLine();
                System.out.println(line.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
