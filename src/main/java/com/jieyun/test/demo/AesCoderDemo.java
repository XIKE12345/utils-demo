//package com.jieyun.test.demo;
//
//import com.jieyun.test.utils.aes.AesUtils;
//
//import javax.crypto.*;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.UnsupportedEncodingException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//
///**
// * @CalssName AesCoderDemo
// * @Desc TODO
// * @Author huike
// * @email <link>754873891@qq.com </link>
// * @Date 2019-12-23
// * @Version 1.0
// **/
//public class AesCoderDemo {
//
//    public static void main(String[] args) {
//        String content = "test";
//        String password = "12345678";
//        //加密
//        System.out.println("加密前：" + content);
//        String enCoder = AesUtils.encrypt(content);
//        System.out.println("加密后："+enCoder);
//
//
//        //解密
//        String decrypt = AesUtils.decrypt(enCoder);
//        System.out.println("解密后：" + decrypt);
//    }
//}
