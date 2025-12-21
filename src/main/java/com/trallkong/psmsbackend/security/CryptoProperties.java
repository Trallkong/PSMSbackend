package com.trallkong.psmsbackend.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "crypto")
public class CryptoProperties {
    private final Aes aes = new Aes();
    private final Hmac hmac = new Hmac();

    @Setter
    @Getter
    public static class Aes {
        private String key;
    }

    @Setter
    @Getter
    public static class Hmac {
        private String key;
    }
}
