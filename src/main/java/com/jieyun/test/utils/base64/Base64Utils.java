package com.jieyun.test.utils.base64;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Base64加密解密工具类
 *
 * @author huike
 * @date 2019-12-21
 */
@Slf4j
public class Base64Utils {

    /**
     * 编码使用基本型 base64 编码方案
     *
     * @param content
     * @return base64encodedString
     */
    public static String getEncoder(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        String base64encodedString = "";
        try {
            // 使用基本编码
            base64encodedString = Base64.getEncoder().encodeToString(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Base64 getEncoder error", e);
        }
        return base64encodedString;
    }

    /**
     * 解码使用基本型 base64 编码方案
     *
     * @param keyContent 密文
     * @return base64decoderStr
     */
    public static String getDecoder(String keyContent) {
        if (StringUtils.isEmpty(keyContent)) {
            return "";
        }
        String base64decoderStr = null;
        try {
            base64decoderStr = new String(Base64.getDecoder().decode(keyContent), "utf-8");

        } catch (UnsupportedEncodingException e) {
            log.error("Base64 getEncoder error", e);
        }
        return base64decoderStr;
    }

    /**
     * 编码使用 URL 和文件名安全型 base64 编码方案
     *
     * @param content
     * @return base64encodedUrlString
     */
    public static String getUrlEncoder(String content) {

        if (StringUtils.isEmpty(content)) {
            return "";
        }
        String base64encodedUrlString = null;
        try {
            base64encodedUrlString = Base64.getUrlEncoder().encodeToString(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Base64 getEncoder error", e);
        }
        return base64encodedUrlString;
    }

    /**
     * 解码使用 URL 和文件名安全型 base64 编码方案
     *
     * @param keyContent 密文
     * @return base64decoderUrlStr
     */
    public static String getDecoderUrl(String keyContent) {

        if (StringUtils.isEmpty(keyContent)) {
            return "";
        }
        String base64decoderUrlStr = null;
        try {
            base64decoderUrlStr = new String(Base64.getUrlDecoder().decode(keyContent), "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Base64 getEncoder error", e);
        }
        return base64decoderUrlStr;
    }

    /**
     * 编码使用 MIME 型 base64 编码方案。
     *
     * @param content
     * @return
     */
    public static String getMimeEncoder(String content) {

        if (StringUtils.isEmpty(content)) {
            return "";
        }
        String base64encodedMimeString = null;
        try {
            byte[] bytes = content.toString().getBytes("utf-8");
            base64encodedMimeString = Base64.getMimeEncoder().encodeToString(bytes);
        } catch (UnsupportedEncodingException e) {
            log.error("Base64 getEncoder error", e);
        }
        return base64encodedMimeString;
    }

    /**
     * 解码使用 MIME 型 base64 编码方案
     *
     * @param keyContent 密文
     * @return
     */
    public static String getDecoderMime(String keyContent) {

        if (StringUtils.isEmpty(keyContent)) {
            return "";
        }
        String base64decoderUrlStr = null;
        try {
            base64decoderUrlStr = new String(Base64.getMimeDecoder().decode(keyContent), "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Base64 getEncoder error", e);
        }
        return base64decoderUrlStr;
    }
}
