package com.jieyun.test.utils.aes;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author wanghaocun
 * @since 2019-11-18
 **/
@SuppressWarnings("all")
public class AesUtils {

    /**
     * AES secretKey length (must be 16 bytes)
     */
//    private static final String SECRET_KEY = "QAZWSXEDCRFVTGBH";
    private static final String SECRET_KEY = "7fd2e257a128435b8b6574e5753d825d";

    /**
     *  AES IV 向量值
     */
    private static final String IV = "zGrVju3LPhyhiJR8";

    /**
     * AES密码器
     */
    private static Cipher cipher;

    /**
     * 字符串编码
     */
    private static final String KEY_CHARSET = "UTF-8";

    /**
     * 算法方式
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 算法/模式/填充
     */
    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

    /**
     * 私钥大小128/192/256(bits)位 即：16/24/32(bytes)，
     * 暂时使用128，如果扩大需要更换java/jre里面的jar包
     */
//    private static final Integer PRIVATE_KEY_SIZE_BIT = 128;
    private static final Integer PRIVATE_KEY_SIZE_BIT = 256;

    //    private static final Integer PRIVATE_KEY_SIZE_BYTE = 16;
    private static final Integer PRIVATE_KEY_SIZE_BYTE = 32;


    /**
     * 加密
     *
     * @param plainText 明文：要加密的内容
     * @return 密文：加密后的内容，如有异常返回空串：""
     */
    public static String encrypt(String plainText) {
        return encrypt(SECRET_KEY, plainText);
    }

    /**
     * 加密
     *
     * @param secretKey 密钥：加密的规则 16位
     * @param plainText 明文：要加密的内容
     * @return cipherText 密文：加密后的内容，如有异常返回空串：""
     */
    public static String encrypt(String secretKey, String plainText) {
//        if (secretKey.length() != PRIVATE_KEY_SIZE_BYTE) {
//            throw new RuntimeException("Invalid AES secretKey length (must be 16 bytes)");
//        }

        // 密文字符串
        String cipherText;
        try {
            // 加密模式初始化参数
            initParam(secretKey, Cipher.ENCRYPT_MODE);
            // 获取加密内容的字节数组
            byte[] bytePlainText = plainText.getBytes(KEY_CHARSET);
            // 执行加密
            byte[] byteCipherText = cipher.doFinal(bytePlainText);
            cipherText = Base64.encodeBase64String(byteCipherText);
        } catch (Exception e) {
            throw new RuntimeException("Encrypt failed", e);
        }
        return cipherText;
    }

    /**
     * 解密
     *
     * @param cipherText 密文：加密后的内容，即需要解密的内容
     * @return 明文：解密后的内容即加密前的内容，如有异常返回空串：""
     */
    public static String decrypt(String cipherText) {
        return decrypt(SECRET_KEY, cipherText);
    }


    /**
     * 解密
     *
     * @param secretKey  密钥：加密的规则 16位
     * @param cipherText 密文：加密后的内容，即需要解密的内容
     */
    public static String decrypt(String secretKey, String cipherText) {

        if (secretKey.length() != PRIVATE_KEY_SIZE_BYTE) {
            throw new RuntimeException("AesUtils:Invalid AES secretKey length (must be 16 bytes)");
        }

        // 明文字符串
        String plainText;
        try {
            initParam(secretKey, Cipher.DECRYPT_MODE);
            // 将加密并编码后的内容解码成字节数组
            byte[] byteCipherText = Base64.decodeBase64(cipherText);
            // 解密
            byte[] bytePlainText = cipher.doFinal(byteCipherText);
            plainText = new String(bytePlainText, KEY_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("AesUtils:decrypt fail!", e);
        }
        return plainText;
    }

    /**
     * 初始化参数
     *
     * @param secretKey 密钥：加密的规则 16位
     * @param mode      加密模式：加密or解密
     */
    private static void initParam(String secretKey, int mode) {
        try {
            // 防止Linux下生成随机key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(secretKey.getBytes());
            // 获取key生成器
            KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
            keygen.init(PRIVATE_KEY_SIZE_BIT, secureRandom);

            // 获得原始对称密钥的字节数组
            byte[] raw = secretKey.getBytes();

            // 根据字节数组生成AES内部密钥
            SecretKeySpec key = new SecretKeySpec(raw, KEY_ALGORITHM);
            // 根据指定算法"AES/CBC/PKCS5Padding"实例化密码器
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            // AES IV 向量值
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());

            cipher.init(mode, key, iv);
        } catch (Exception e) {
            throw new RuntimeException("AesUtils:initParam fail!", e);
        }
    }

    public static final String SAMPLE_STRING =
            "{\"enterpriseId\":\"540170007\",\"report\":{\"collectTime\":\"20191217155004\"," +
                    "\"dataId\":\"b2801ec4-d59e-46aa-914a-88d25ad5ebe4\",\"dataType\":\"energy_data\"," +
                    "\"datas\":[{\"quotaId\":\"540170007002G0001YW001\",\"value\":\"9173\"}," +
                    "{\"quotaId\":\"540170007002G0002YW001\",\"value\":\"12869\"}," +
                    "{\"quotaId\":\"540170007002G0003YW001\",\"value\":\"8734\"}," +
                    "{\"quotaId\":\"540170007002G0004YW001\",\"value\":\"12769\"}," +
                    "{\"quotaId\":\"540170007002G0005YW001\",\"value\":\"12781\"}," +
                    "{\"quotaId\":\"540170007002G0006YW001\",\"value\":\"13079\"}," +
                    "{\"quotaId\":\"540170007002G0007YW001\",\"value\":\"5603\"}," +
                    "{\"quotaId\":\"540170007002G0008YW001\",\"value\":\"3664\"}," +
                    "{\"quotaId\":\"540170007002G0009YW001\",\"value\":\"9687\"}," +
                    "{\"quotaId\":\"540170007002G0010YW001\",\"value\":\"2391\"}],\"enterpriseId\":\"540170007\"," +
                    "\"gatewayId\":\"54017000701\",\"isConnectDataSource\":\"true\",\"reportType\":\"report\"}}";

}
