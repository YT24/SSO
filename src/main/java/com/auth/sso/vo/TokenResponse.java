package com.auth.sso.vo;

import lombok.Data;

@Data
public class TokenResponse {
    private String access_token;
    private String token_type = "Bearer";
    private String refresh_token;
    private long expires_in;
    private String id_token; // OIDC
} 