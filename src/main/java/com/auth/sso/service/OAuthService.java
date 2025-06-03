package com.auth.sso.service;

import com.auth.sso.vo.TokenResponse;

public interface OAuthService {
    String generateCode(String clientId, String redirectUri, String scope, Long userId);

    TokenResponse exchangeToken(String code, String clientId, String clientSecret, String redirectUri);

    Object getUserInfo(String token);

    TokenResponse refreshToken(String refreshToken, String clientId, String clientSecret);

    /**
     * 校验用户名密码，成功返回userId，失败返回null
     */
    Long checkUser(String username, String password);

    /**
     * OIDC password模式，用户名密码直接换token
     */
    TokenResponse exchangeTokenByPassword(String username, String password, String clientId, String clientSecret, String scope);
}