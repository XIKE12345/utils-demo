package com.jieyun.test.interview;

import java.math.BigDecimal;

public class RandomClass {
    // 2颗骰子，6面，N次
    // 可能出现的点数和 2，3，4，5，6，7，8，9，10，11，12
    public static void main(String[] args) {
        RandomClass.getResultNum(100);
    }

    /**
     * 求各点数的和及概率
     *
     * @param n
     */
    private static void getResultNum(int n) {

        int intArray[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        // 获取1-6之间随机数
        for (int i = 0; i <= n; i++) {
            int ran1 = (int) (Math.random() * 6) + 1;
            int ran2 = (int) (Math.random() * 6) + 1;
            int res = ran1 + ran2;
            switch (res) {
                case 2:
                    intArray[0]++;
                    break;
                case 3:
                    intArray[1]++;
                    break;
                case 4:
                    intArray[2]++;
                    break;
                case 5:
                    intArray[3]++;
                    break;
                case 6:
                    intArray[4]++;
                    break;
                case 7:
                    intArray[5]++;
                    break;
                case 8:
                    intArray[6]++;
                    break;
                case 9:
                    intArray[7]++;
                    break;
                case 10:
                    intArray[8]++;
                    break;
                case 11:
                    intArray[9]++;
                    break;
                case 12:
                    intArray[10]++;
                    break;
            }
        }
        for (int arr = 0; arr <= intArray.length - 1; arr++) {
            int count = intArray[arr];
            double rate = new BigDecimal((float) count / n).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println(rate);
        }
    }

}


