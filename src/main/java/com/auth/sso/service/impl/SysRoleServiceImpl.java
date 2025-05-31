package com.auth.sso.service.impl;

import com.auth.sso.entity.SysRole;
import com.auth.sso.mapper.SysRoleMapper;
import com.auth.sso.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}