//package com.jieyun.test.demo;
//
//
//import org.springframework.util.DigestUtils;
//
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//
///**
// * @CalssName Md5EncryptDemo
// * @Desc Md5加密解密
// * @Author huike
// * @email <link>754873891@qq.com </link>
// * @Date 2019-12-22
// * @Version 1.0
// **/
//public class Md5EncryptDemo {
//
//    public static void main(String[] args) {
//
//        try{
//            String str = "jieyun@2019";
//            byte[] bytes = str.getBytes("utf-8");
//
//            String md5DigestAsHex = DigestUtils.md5DigestAsHex(bytes);
//
//            byte[] bytes1 = DigestUtils.md5Digest(bytes);
//
//            String s = new String(bytes1);
//            System.out.println(s +"---------");
//
//
//            System.out.println(md5DigestAsHex);
//
//        }catch (UnsupportedEncodingException e){
//
//        }
//    }
//}
