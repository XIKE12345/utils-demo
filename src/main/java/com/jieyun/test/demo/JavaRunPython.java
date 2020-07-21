package com.jieyun.test.demo;


import java.io.IOException;

/**
 * @author huike
 */
public class JavaRunPython {


    public static void main(String[] args) {

        String[] params = new String[]{"python", "/Users/huike/PycharmProjects/spiders-monitor-backend/test.py"};
//        String[] params =new String[]{ "python3 /Users/huike/IdeaProjects/test/src/main/java/com/jieyun/test/demo/ExcelTranspose.py"};

        try {
            Runtime.getRuntime().exec(params);
            System.out.println("================");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
