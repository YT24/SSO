package com.auth.sso.service;

import com.auth.sso.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysUserRoleService extends IService<SysUserRole> {
    /**
     * 根据角色ID查询所有关联用户
     */
    java.util.List<com.auth.sso.entity.SysUser> listUsersByRoleId(Long roleId);
}