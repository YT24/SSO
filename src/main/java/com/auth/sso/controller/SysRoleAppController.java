package com.auth.sso.controller;

import com.auth.sso.entity.SysRoleApp;
import com.auth.sso.service.SysRoleAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roleApp")
public class SysRoleAppController {
    @Autowired
    private SysRoleAppService sysRoleAppService;

    @GetMapping("/list")
    public List<SysRoleApp> list() {
        return sysRoleAppService.list();
    }

    @PostMapping("/add")
    public boolean add(@RequestBody SysRoleApp roleApp) {
        return sysRoleAppService.save(roleApp);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return sysRoleAppService.removeById(id);
    }
}