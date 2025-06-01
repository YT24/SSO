package com.auth.sso.service;

import com.auth.sso.entity.SysRoleApp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.auth.sso.entity.SysRole;
import java.util.List;

public interface SysRoleAppService extends IService<SysRoleApp> {
    List<SysRole> listRolesByClientId(String clientId);
}