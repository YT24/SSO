package com.auth.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.auth.sso.common.Result;
import com.auth.sso.entity.SysUser;
import com.auth.sso.service.SysUserService;

@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody SysUser user) {
        return Result.success(sysUserService.save(user));
    }

    @GetMapping("/{id}")
    public Result<SysUser> getById(@PathVariable Long id) {
        return Result.success(sysUserService.getById(id));
    }
} 