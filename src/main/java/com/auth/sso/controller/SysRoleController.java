package com.auth.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.auth.sso.common.Result;
import com.auth.sso.entity.SysRole;
import com.auth.sso.service.SysRoleService;

@RestController
@RequestMapping("/api/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SysRole role) {
        return Result.success(sysRoleService.save(role));
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SysRole role) {
        return Result.success(sysRoleService.updateById(role));
    }

    @PostMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysRoleService.removeById(id));
    }

    @GetMapping("/{id}")
    public Result<SysRole> getById(@PathVariable Long id) {
        return Result.success(sysRoleService.getById(id));
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(sysRoleService.list());
    }
}