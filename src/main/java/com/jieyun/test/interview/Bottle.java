package com.jieyun.test.interview;

/**
 * @CalssName Bottle
 * @Desc TODO
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2020/3/21
 * @Version 1.0
 **/
public class Bottle {
    public static void main(String[] args) {
        System.out.println(getFibo(10));
    }

    public static int getFibo(int n) {
        if (n <= 1) {
            return 1;
        } else if (n == 2) {
            return 1;
        } else {
            return (getFibo(n - 1) + getFibo(n - 2));
        }
    }
}
