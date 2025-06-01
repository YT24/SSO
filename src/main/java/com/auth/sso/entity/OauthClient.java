package com.auth.sso.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("oauth_client")
public class OauthClient {
    private Long id;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;
    private String protocol;
    private String appName;
    private String appUrl;
    private String appIcon;
}