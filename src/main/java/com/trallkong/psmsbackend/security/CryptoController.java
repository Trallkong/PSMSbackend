package com.trallkong.psmsbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    @Autowired
    private CryptoProperties cryptoProperties;

    // 示例1：AES加密解密
    @GetMapping("/aes/encrypt")
    public String aesEncrypt(@RequestParam String plainText) throws Exception {
        String aesKey = cryptoProperties.getAes().getKey();
        return CryptoUtils.aesEncrypt(plainText, aesKey);
    }

    @GetMapping("/aes/decrypt")
    public String aesDecrypt(@RequestParam String cipherText) throws Exception {
        String aesKey = cryptoProperties.getAes().getKey();
        return CryptoUtils.aesDecrypt(cipherText, aesKey);
    }

    // 示例2：HMAC-SHA256签名与验签
    @GetMapping("/hmac/sign")
    public String hmacSign(@RequestParam String data) throws Exception {
        String hmacKey = cryptoProperties.getHmac().getKey();
        return CryptoUtils.hmacSha256Sign(data, hmacKey);
    }

    @GetMapping("/hmac/verify")
    public boolean hmacVerify(@RequestParam String data, @RequestParam String signature) throws Exception {
        String hmacKey = cryptoProperties.getHmac().getKey();
        return CryptoUtils.hmacSha256Verify(data, signature, hmacKey);
    }

    // 示例3：生成密钥（仅测试用，生产环境密钥需离线生成并妥善保管）
    @GetMapping("/generate/keys")
    public String generateKeys() throws Exception {
        String aesKey = CryptoUtils.generateAesKey();
        String hmacKey = CryptoUtils.generateHmacKey();
        return "AES密钥（Base64）：" + aesKey + "\nHMAC密钥（Base64）：" + hmacKey;
    }
}
