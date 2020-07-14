package com.jieyun.test.demo;

public class SwitchDemo {
    public static void main(String[] args) {
        SwitchDemo.colourConvert("黄白色");

    }

    private static int colourConvert(String colour) {
        int result = 0;
        switch (colour) {
            case "黄白色":
                System.out.println("colour mapper value is : 1");
                result = 1;
                break;
            case "A":
                System.out.println("case two");
                result = 2;
                break;
            case "C":
                System.out.println("case three");
                result = 3;
                break;
        }
        return result;
    }
}
