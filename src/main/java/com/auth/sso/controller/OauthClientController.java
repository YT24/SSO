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

    @GetMapping("/{id}")
    public Result<OauthClient> getById(@PathVariable Long id) {
        return Result.success(oauthClientService.getById(id));
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(oauthClientService.list());
    }

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody OauthClient client) {
        return Result.success(oauthClientService.save(client));
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody OauthClient client) {
        return Result.success(oauthClientService.updateById(client));
    }

    @PostMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(oauthClientService.removeById(id));
    }
}