package com.auth.sso.service.impl;

import com.auth.sso.entity.SysUser;
import com.auth.sso.mapper.SysUserMapper;
import com.auth.sso.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
} 