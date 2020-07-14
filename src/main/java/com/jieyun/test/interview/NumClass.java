package com.jieyun.test.interview;

/**
 * 500 以内的完全数
 * 例： 6 = 1+2+3
 */
public class NumClass {

    public static void main(String[] args) {
        int num = 500;
        NumClass.print(num);
    }

    public static void print(int num) {
        int s;
        // 500以内的数
        for (int i = 6; i <= num; i++) {
            s = 0;
            // 因式分解
            for (int j = 1; j < i; j++) {
                if (i % j == 0)
                    s += j;
            }
            if (i == s)
                System.out.println(i);
        }
    }

}
