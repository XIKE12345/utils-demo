package com.jieyun.test.interview;

public class Hump2Line {

    public static void main(String[] args) {
        String s = Hump2Line.lineToHump("sgsdf-sfd-et");
        System.out.println(s);
    }

    public static String lineToHump(String str) {
        // 转换成小写
        str = str.toLowerCase();
        // 以"-"分割
        String[] split = str.split("-");
        String resStr = "";
        for (int i = 0; i < split.length; i++) {
            String s = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
            resStr += s;
        }
        return resStr;
    }
}
