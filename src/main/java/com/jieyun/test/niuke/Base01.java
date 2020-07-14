package com.jieyun.test.niuke;

// java基础-必会必知上
public class Base01 {
    public static void main(String[] args) {

        Object a = new String();
        Object b = new String();
        System.out.println(a==b); //false
        System.out.println(a.equals(b));//true
    }
}
