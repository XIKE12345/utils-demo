package com.jieyun.test.demo;

public class BaseStartThreadDemo {

    public static void main(String[] args) {
        // 启动一个新的线程
        new Thread() {
            @Override
            public void run() {
                //这里是线程需要做的事情
                System.out.println("启动一个线程");
            }
        }.start();
    }


}

