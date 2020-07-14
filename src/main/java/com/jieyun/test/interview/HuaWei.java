package com.jieyun.test.interview;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HuaWei {
    public static int minimumLengthEncoding(String[] words) {
        Set<String> good = new HashSet(Arrays.asList(words));
        for (String word : words) {
            for (int k = 1; k < word.length(); ++k) {
                good.remove(word.substring(k));

            }
        }

        int ans = 0;
        for (String word : good) {
            ans += word.length() + 1;

        }
        return ans;
    }

    public static void main(String[] args) {
        String[] s = new String[]{"time", "me", "bell"};
        int i = minimumLengthEncoding(s);
        System.out.println(i);
    }


}
