package com.trallkong.psmsbackend.security;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密工具类：AES-GCM + HMAC-SHA256
 */
public class CryptoUtils {
    // ------------------- AES 配置 -------------------
    private static final String AES_ALGORITHM = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE = 256; // 密钥长度256位
    private static final int GCM_IV_LENGTH = 12; // GCM模式推荐IV长度12字节
    private static final int GCM_TAG_LENGTH = 128; // 认证标签长度128位

    // ------------------- HMAC-SHA256 配置 -------------------
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    // ===================== AES 方法 =====================
    /**
     * 生成AES随机密钥（256位）
     * @return Base64编码的密钥
     */
    public static String generateAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE, new SecureRandom());
        SecretKey secretKey = keyGen.generateKey();
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    /**
     * AES加密（GCM模式）
     * @param plainText 明文
     * @param aesKeyBase64 Base64编码的AES密钥
     * @return 加密结果（格式：Base64(IV + 密文 + 认证标签)）
     */
    public static String aesEncrypt(String plainText, String aesKeyBase64) throws Exception {
        // 1. 解码密钥
        byte[] aesKey = Base64.decodeBase64(aesKeyBase64);
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey, "AES");

        // 2. 生成随机IV
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        // 3. 初始化加密器
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);

        // 4. 加密
        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // 5. 拼接IV + 密文 + 标签（GCM模式下cipherText已包含标签），Base64编码返回
        byte[] result = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(cipherText, 0, result, iv.length, cipherText.length);
        return Base64.encodeBase64String(result);
    }

    /**
     * AES解密（GCM模式）
     * @param cipherTextBase64 加密后的Base64字符串
     * @param aesKeyBase64 Base64编码的AES密钥
     * @return 明文
     */
    public static String aesDecrypt(String cipherTextBase64, String aesKeyBase64) throws Exception {
        // 1. 解码密文（包含IV + 密文 + 标签）
        byte[] cipherData = Base64.decodeBase64(cipherTextBase64);

        // 2. 拆分IV和密文
        byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(cipherData, 0, iv, 0, iv.length);
        byte[] cipherText = new byte[cipherData.length - iv.length];
        System.arraycopy(cipherData, iv.length, cipherText, 0, cipherText.length);

        // 3. 解码密钥
        byte[] aesKey = Base64.decodeBase64(aesKeyBase64);
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey, "AES");

        // 4. 初始化解密器
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);

        // 5. 解密
        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText, StandardCharsets.UTF_8);
    }

    // ===================== HMAC-SHA256 方法 =====================
    /**
     * 生成HMAC-SHA256签名
     * @param data 待签名数据
     * @param hmacKeyBase64 Base64编码的HMAC密钥
     * @return Base64编码的签名
     */
    public static String hmacSha256Sign(String data, String hmacKeyBase64) throws Exception {
        byte[] hmacKey = Base64.decodeBase64(hmacKeyBase64);
        SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKey, HMAC_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        mac.init(secretKeySpec);
        byte[] signature = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(signature);
    }

    /**
     * 验证HMAC-SHA256签名
     * @param data 待验证数据
     * @param signatureBase64 Base64编码的签名
     * @param hmacKeyBase64 Base64编码的HMAC密钥
     * @return 签名是否有效
     */
    public static boolean hmacSha256Verify(String data, String signatureBase64, String hmacKeyBase64) throws Exception {
        String calculatedSign = hmacSha256Sign(data, hmacKeyBase64);
        // 常量时间比较，防止时序攻击
        return calculatedSign.equals(signatureBase64);
    }

    /**
     * 生成HMAC-SHA256随机密钥（256位）
     * @return Base64编码的密钥
     */
    public static String generateHmacKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(HMAC_ALGORITHM);
        keyGen.init(256, new SecureRandom()); // 256位密钥
        SecretKey secretKey = keyGen.generateKey();
        return Base64.encodeBase64String(secretKey.getEncoded());
    }
}