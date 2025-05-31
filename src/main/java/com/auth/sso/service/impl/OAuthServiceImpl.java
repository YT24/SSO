package com.auth.sso.service.impl;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.sso.config.Oauth2Properties;
import com.auth.sso.entity.OauthClient;
import com.auth.sso.entity.SysUser;
import com.auth.sso.mapper.OauthClientMapper;
import com.auth.sso.mapper.SysUserMapper;
import com.auth.sso.service.OAuthService;
import com.auth.sso.util.RedisUtil;
import com.auth.sso.util.TokenGenerator;
import com.auth.sso.vo.TokenResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    private OauthClientMapper oauthClientMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Oauth2Properties oauth2Properties;

    @Override
    public String generateCode(String clientId, String redirectUri, String scope, Long userId) {
        String code = TokenGenerator.generateToken(32);
        String codeKey = "oauth:code:" + code;
        // 用逗号拼接简单存储，生产建议用JSON
        String codeValue = userId + "," + clientId + "," + redirectUri + "," + (scope == null ? "" : scope);
        redisUtil.set(codeKey, codeValue, 10, TimeUnit.MINUTES);
        return code;
    }

    @Override
    public TokenResponse exchangeToken(String code, String clientId, String clientSecret, String redirectUri) {
        String codeKey = "oauth:code:" + code;
        String codeValue = redisUtil.get(codeKey);
        if (codeValue == null) {
            throw new RuntimeException("授权码无效或已过期");
        }
        String[] arr = codeValue.split(",", 4);
        Long userId = Long.valueOf(arr[0]);
        String codeClientId = arr[1];
        String codeRedirectUri = arr[2];
        String codeScope = arr.length > 3 ? arr[3] : null;
        if (!clientId.equals(codeClientId) || !redirectUri.equals(codeRedirectUri)) {
            throw new RuntimeException("客户端信息错误");
        }
        // 校验client_secret
        OauthClient client = oauthClientMapper.selectOne(new QueryWrapper<OauthClient>()
                .eq("client_id", clientId)
                .eq("client_secret", clientSecret)
                .eq("redirect_uri", redirectUri));
        if (client == null) {
            throw new RuntimeException("客户端信息错误");
        }
        String accessToken = TokenGenerator.generateToken(32);
        String refreshToken = TokenGenerator.generateToken(32);
        String idToken = Base64.getEncoder().encodeToString(
                ("{\"sub\":\"" + userId + "\",\"aud\":\"" + clientId + "\",\"exp\":"
                        + (System.currentTimeMillis() / 1000 + oauth2Properties.getAccessTokenExpireSeconds()) + "}")
                        .getBytes());
        String tokenKey = "oauth:token:" + accessToken;
        String refreshKey = "oauth:refresh:" + refreshToken;
        String tokenValue = userId + "," + clientId + "," + (codeScope == null ? "" : codeScope);
        redisUtil.set(tokenKey, tokenValue, oauth2Properties.getAccessTokenExpireSeconds(), TimeUnit.SECONDS);
        redisUtil.set(refreshKey, tokenValue, oauth2Properties.getRefreshTokenExpireSeconds(), TimeUnit.SECONDS);
        redisUtil.delete(codeKey);
        TokenResponse resp = new TokenResponse();
        resp.setAccess_token(accessToken);
        resp.setRefresh_token(refreshToken);
        resp.setExpires_in(oauth2Properties.getAccessTokenExpireSeconds());
        resp.setId_token(idToken);
        return resp;
    }

    @Override
    public TokenResponse refreshToken(String refreshToken, String clientId, String clientSecret) {
        String refreshKey = "oauth:refresh:" + refreshToken;
        String tokenValue = redisUtil.get(refreshKey);
        if (tokenValue == null) {
            throw new RuntimeException("refresh_token无效或已过期");
        }
        String[] arr = tokenValue.split(",", 3);
        String codeClientId = arr[1];
        if (!clientId.equals(codeClientId)) {
            throw new RuntimeException("客户端信息错误");
        }
        // 校验client_secret
        OauthClient client = oauthClientMapper.selectOne(new QueryWrapper<OauthClient>()
                .eq("client_id", clientId)
                .eq("client_secret", clientSecret));
        if (client == null) {
            throw new RuntimeException("客户端信息错误");
        }
        String accessToken = TokenGenerator.generateToken(32);
        String tokenKey = "oauth:token:" + accessToken;
        redisUtil.set(tokenKey, tokenValue, oauth2Properties.getAccessTokenExpireSeconds(), TimeUnit.SECONDS);
        TokenResponse resp = new TokenResponse();
        resp.setAccess_token(accessToken);
        resp.setRefresh_token(refreshToken); // refresh_token不变
        resp.setExpires_in(oauth2Properties.getAccessTokenExpireSeconds());
        resp.setId_token(null); // 如需可生成新id_token
        return resp;
    }

    @Override
    public Object getUserInfo(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("token无效");
        }
        String accessToken = token.replace("Bearer ", "");
        String tokenKey = "oauth:token:" + accessToken;
        String tokenValue = redisUtil.get(tokenKey);
        if (tokenValue == null) {
            throw new RuntimeException("token无效或已过期");
        }
        String[] arr = tokenValue.split(",", 3);
        Long userId = Long.valueOf(arr[0]);
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return new UserInfoVO(user.getId(), user.getUsername(), user.getNickname());
    }

    @Override
    public Long checkUser(String username, String password) {
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .eq("username", username)
                .eq("password", password));
        return user != null ? user.getId() : null;
    }

    public static class UserInfoVO {
        public Long id;
        public String username;
        public String nickname;

        public UserInfoVO(Long id, String username, String nickname) {
            this.id = id;
            this.username = username;
            this.nickname = nickname;
        }
    }
}