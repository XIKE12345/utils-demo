package com.jieyun.test.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author huike
 */
public class StrDemo {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>(16);
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");

        for (int i = 0;i<=list.size()-1;i++){
            System.out.println("i= " + i);
            System.out.println("v= "+ list.get(i));
        }
    }

}
