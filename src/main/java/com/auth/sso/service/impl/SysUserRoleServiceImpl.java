package com.auth.sso.service.impl;

import com.auth.sso.entity.SysUserRole;
import com.auth.sso.mapper.SysUserRoleMapper;
import com.auth.sso.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
}