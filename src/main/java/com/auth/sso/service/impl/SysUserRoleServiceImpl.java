package com.auth.sso.service.impl;

import com.auth.sso.entity.SysUserRole;
import com.auth.sso.mapper.SysUserRoleMapper;
import com.auth.sso.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth.sso.entity.SysUser;
import com.auth.sso.mapper.SysUserMapper;
import java.util.List;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> listUsersByRoleId(Long roleId) {
        // 通过自定义SQL查询
        return sysUserMapper.selectUsersByRoleId(roleId);
    }
}