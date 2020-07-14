package com.jieyun.test.demo;

/**
 * @author huike
 */
public class TestZip {
    public static void main(String[] args) {
        String dkOrgName = "DK.";
        String s = "/Users/huike/IdeaProjects/test/src/main/resources/unzip/可一书店/DK.csv";
        boolean contains1 = s.contains(dkOrgName);
        boolean contains = dkOrgName.contains(s);

        System.out.println(contains);
        System.out.println(contains1);
    }
}
