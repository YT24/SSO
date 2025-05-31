package com.auth.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.sso.service.OAuthService;

@RestController
public class LoginController {

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String client_id,
            @RequestParam(required = false) String client_secret,
            @RequestParam(required = false) String redirect_uri) {
        // 返回登录页面
        return "login";
    }
}