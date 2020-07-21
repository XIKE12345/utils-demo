package com.jieyun.test.demo;

import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Process proc;
        String[] params  = new String[]{"python3","/Users/huike/IdeaProjects/test/src/main/java/com/jieyun/test/demo/demo.py","/Users/huike/Downloads/工作簿2.xlsx","/Users/huike/Downloads/工作簿110.xlsx"};
        Runtime.getRuntime().exec(params);
    }
}
