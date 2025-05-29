package com.auth.sso.service;

import com.auth.sso.vo.TokenResponse;

public interface OAuthService {
    String generateCode(String clientId, String redirectUri, String scope, Long userId);
    TokenResponse exchangeToken(String code, String clientId, String clientSecret, String redirectUri);
    Object getUserInfo(String token);
    TokenResponse refreshToken(String refreshToken, String clientId, String clientSecret);
} 