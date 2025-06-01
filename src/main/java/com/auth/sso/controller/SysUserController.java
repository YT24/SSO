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

    @GetMapping("/{id}")
    public Result<SysUser> getById(@PathVariable Long id) {
        return Result.success(sysUserService.getById(id));
    }

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SysUser user) {
        return Result.success(sysUserService.save(user));
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SysUser user) {
        return Result.success(sysUserService.updateById(user));
    }

    @PostMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysUserService.removeById(id));
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(sysUserService.list());
    }
}