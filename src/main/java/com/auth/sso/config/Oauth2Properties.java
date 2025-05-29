package com.auth.sso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Properties {
    private int accessTokenExpireSeconds;
    private int refreshTokenExpireSeconds;

    public int getAccessTokenExpireSeconds() { return accessTokenExpireSeconds; }
    public void setAccessTokenExpireSeconds(int accessTokenExpireSeconds) { this.accessTokenExpireSeconds = accessTokenExpireSeconds; }
    public int getRefreshTokenExpireSeconds() { return refreshTokenExpireSeconds; }
    public void setRefreshTokenExpireSeconds(int refreshTokenExpireSeconds) { this.refreshTokenExpireSeconds = refreshTokenExpireSeconds; }
} 