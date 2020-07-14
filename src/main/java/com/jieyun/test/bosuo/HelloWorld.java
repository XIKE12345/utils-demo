package com.jieyun.test.bosuo;

/**
 * @author huike
 */
public class HelloWorld {
    public static void main(String[] args) {
        Runnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
