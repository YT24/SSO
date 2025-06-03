package com.auth.sso.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sso")
public class SsoClientProperties {
    private String serverUrl;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}