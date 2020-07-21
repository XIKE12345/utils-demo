package com.jieyun.test.demo;

/**
 * 找最大数
 * @author huike
 */
public class MaxNum {
    public static void main(String[] args) {
        int arr[] = new int[]{1, 6, 2, 2, 5};
        StringBuffer stringBuffer = BubbleSort(arr);
        System.out.println(stringBuffer);
    }
    public static StringBuffer BubbleSort(int[] arr) {
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] > arr[j]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return sb;
    }
}

