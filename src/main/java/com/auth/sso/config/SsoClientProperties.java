package com.auth.sso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "sso")
@Data
public class SsoClientProperties {
    private String serverUrl;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}