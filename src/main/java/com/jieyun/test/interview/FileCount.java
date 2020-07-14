package com.jieyun.test.interview;

import java.util.*;

public class FileCount {

    public static void main(String[] args) {

        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            // 产生1-50的随机数
            int num = (int) (Math.random() * 50) + 1;
            nums.add(num);
        }
        // 挑出偶数
        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer evenNum : nums) {
            if (evenNum % 2 == 0) {
                evenNumbers.add(evenNum);
            }
        }

        Map<Integer, Integer> map = new HashMap();
        for (Integer res : evenNumbers) {
            boolean flag = map.containsKey(res);
            if (!flag) {
                map.put(res, 1);
            } else {
                map.put(res, map.get(res) + 1);
            }
        }

        // 遍历map
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + "出现了 " + entry.getValue() + " 次");
//        }

        // 排序 使用Compare
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for (Map.Entry s : list) {
            System.out.println(s.getKey() + "出现了" + s.getValue() + "次");
        }
    }
}
