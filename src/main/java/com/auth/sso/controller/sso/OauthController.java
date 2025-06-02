package com.auth.sso.controller.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.auth.sso.common.Result;
import com.auth.sso.service.OAuthService;
import com.auth.sso.util.RedisUtil;
import com.auth.sso.vo.TokenResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/oauth2")
public class OauthController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private RedisUtil redisUtil;

    // 授权码获取（需登录后访问）
    @GetMapping("/authorize")
    public void authorize(
            @RequestParam String client_id,
            @RequestParam String redirect_uri,
            @RequestParam String response_type,
            @RequestParam(required = false) String scope,
            @RequestParam(required = false) String state,
            @RequestParam Long user_id, // 实际应从session获取
            HttpServletResponse response) throws Exception {
        String code = oAuthService.generateCode(client_id, redirect_uri, scope, user_id);
        response.sendRedirect(redirect_uri + "?code=" + code + (state != null ? "&state=" + state : ""));
    }

    // 换取token
    @PostMapping("/token")
    public Result<TokenResponse> token(
            @RequestParam String code,
            @RequestParam String client_id,
            @RequestParam String client_secret,
            @RequestParam String redirect_uri) {
        return Result.success(oAuthService.exchangeToken(code, client_id, client_secret, redirect_uri));
    }

    // OIDC用户信息
    @GetMapping("/userinfo")
    public Result<?> userinfo(@RequestHeader("Authorization") String token) {
        return Result.success(oAuthService.getUserInfo(token));
    }

    @GetMapping("/check_token")
    public Result<Boolean> checkToken(@RequestParam String access_token) {
        String tokenKey = "oauth:token:" + access_token;
        boolean valid = redisUtil.hasKey(tokenKey);
        return Result.success(valid);
    }

    @PostMapping("/refresh_token")
    public Result<TokenResponse> refreshToken(
            @RequestParam String refresh_token,
            @RequestParam String client_id,
            @RequestParam String client_secret) {
        return Result.success(oAuthService.refreshToken(refresh_token, client_id, client_secret));
    }

    @PostMapping("/login")
    public void login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String client_id,
            @RequestParam String redirect_uri,
            @RequestParam(required = false) String state,
            HttpServletResponse response) throws IOException {
        // 校验用户名密码，假设oAuthService.checkUser返回userId，校验失败返回null
        Long userId = oAuthService.checkUser(username, password);
        if (userId == null) {
            response.setStatus(401);
            response.getWriter().write("用户名或密码错误");
            return;
        }
        // 生成授权码
        String code = oAuthService.generateCode(client_id, redirect_uri, null, userId);
        // 跳转到 redirect_uri?code=xxx&state=xxx
        String redirect = redirect_uri + "?code=" + code + (state != null ? "&state=" + state : "");
        response.sendRedirect(redirect);
    }
}