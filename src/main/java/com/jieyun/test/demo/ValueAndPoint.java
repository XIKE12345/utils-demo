package com.jieyun.test.demo;

/**
 * java 值传递和引用传递
 * @author huike
 */
public class ValueAndPoint {
    public static void main(String[] args) {
        int x = 0 ;
        change(x);
        System.out.println(x);
    }

     static void change(int x) {
        x = 7;
    }
}
