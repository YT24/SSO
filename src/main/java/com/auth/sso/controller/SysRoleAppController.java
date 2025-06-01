package com.auth.sso.controller;

import com.auth.sso.entity.SysRoleApp;
import com.auth.sso.service.SysRoleAppService;
import com.auth.sso.entity.SysRole;
import com.auth.sso.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roleApp")
public class SysRoleAppController {
    @Autowired
    private SysRoleAppService sysRoleAppService;

    @GetMapping("/list")
    public Result<List<SysRole>> list(@RequestParam("clientId") String clientId) {
        return Result.success(sysRoleAppService.listRolesByClientId(clientId));
    }

    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SysRoleApp roleApp) {
        return Result.success(sysRoleAppService.save(roleApp));
    }

    @PostMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysRoleAppService.removeById(id));
    }
}