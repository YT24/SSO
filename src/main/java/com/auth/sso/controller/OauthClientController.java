package com.auth.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.auth.sso.common.Result;
import com.auth.sso.entity.OauthClient;
import com.auth.sso.service.OauthClientService;

@RestController
@RequestMapping("/api/client")
public class OauthClientController {
    @Autowired
    private OauthClientService oauthClientService;

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody OauthClient client) {
        return Result.success(oauthClientService.save(client));
    }

    @GetMapping("/{id}")
    public Result<OauthClient> getById(@PathVariable Long id) {
        return Result.success(oauthClientService.getById(id));
    }
} 