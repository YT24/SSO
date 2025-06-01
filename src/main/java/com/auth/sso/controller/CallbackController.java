package com.auth.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.sso.common.Result;
import com.auth.sso.service.OAuthService;
import com.auth.sso.vo.TokenResponse;

@RestController
public class CallbackController {

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("/callback")
    public Result<TokenResponse> callback(@RequestParam String code) {
        TokenResponse exchangeToken = oAuthService.exchangeToken(code, "test", "test",
                "http://localhost:8080/callback");
        ;
        // 返回登录页面
        return Result.success(exchangeToken);
    }
}