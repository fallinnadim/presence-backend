package com.fallinnadim.presence_app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
public class JWTProperties {
    private String key;
    private Long accessTokenExpiration;

    public String getKey() {
        return key;
    }

    public Long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }
}

