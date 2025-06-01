package com.auth.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.auth.sso.common.Result;
import com.auth.sso.entity.SysUserRole;
import com.auth.sso.service.SysUserRoleService;

@RestController
@RequestMapping("/api/user-role")
public class SysUserRoleController {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @GetMapping("/{id}")
    public Result<SysUserRole> getById(@PathVariable Long id) {
        return Result.success(sysUserRoleService.getById(id));
    }

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SysUserRole userRole) {
        return Result.success(sysUserRoleService.save(userRole));
    }

    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SysUserRole userRole) {
        return Result.success(sysUserRoleService.updateById(userRole));
    }

    @PostMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysUserRoleService.removeById(id));
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam("roleId") Long roleId) {
        return Result.success(sysUserRoleService.listUsersByRoleId(roleId));
    }
}