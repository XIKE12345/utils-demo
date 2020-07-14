package com.jieyun.test.interview;

import java.util.*;

/**
 * 统计客人人数
 */
public class StatisticsGust {
    /*
    举办一场8小时的聚会，时间段从12：00到20：00点，让来访的客人事先填好到达的时间和离开的时间，为了掌握聚会期间的座位数目，需要先估计不同时间的最大客人数量。
    1.到达和离开的时间，以整点计算，输入为整数，比如“12，18”表示客人到达的时间为12点后13点前，离开的时间是17点后18点前。
    2.按小时区间统计客人的数量，需要统计[12，13),[13,14)….[19，20)共有8个时间段的最大客人数量。
    3.假设邀请的客人最多100个。
    * */

    public static List<String> list = new ArrayList<String>();

    public static void main(String[] args) {

        String[] str = {"12,15", "16,17", "12,20"};
        for (int i = 0; i < str.length; i++) {
            reset(str[i]);
        }
        Hashtable<String, Integer> wordCount = wordcount();
        List<String> newlist = new ArrayList<String>();
        for (String key : wordcount().keySet()) {
            newlist.add("[" + key + ")" + ":" + wordCount.get(key));
        }
        //重排
        Collections.sort(newlist, new Comparator() {
            public int compare(Object o1, Object o2) {
                String str1 = (String) o1;
                String str2 = (String) o2;
                if (str1.compareToIgnoreCase(str2) < 0) {
                    return -1;
                }
                return 1;
            }
        });
        //输出结果
        for (int i = 0; i < newlist.size(); i++) {
            System.out.println(newlist.get(i));
        }


    }

    //字符串组合成制定格式，类似于16,17
    static void reset(String str) {
        String[] aa = str.split(",");
        int inter = Integer.parseInt(aa[1]) - Integer.parseInt(aa[0]);
        for (int i = 0; i < inter; i++) {
            int newString = Integer.parseInt(aa[0]) + i;
            list.add(newString + "," + (newString + 1));
        }
    }

    //统计次数
    static Hashtable<String, Integer> wordcount() {
        Hashtable<String, Integer> wordCount = new Hashtable<String, Integer>();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (!wordCount.containsKey(str)) {
                wordCount.put(str, Integer.valueOf(1));
            } else {
                wordCount.put(str, Integer.valueOf(wordCount.get(str).intValue() + 1));
            }
        }
        return wordCount;
    }
}
