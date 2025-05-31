package com.auth.sso.service.impl;

import com.auth.sso.entity.SysRoleApp;
import com.auth.sso.mapper.SysRoleAppMapper;
import com.auth.sso.service.SysRoleAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysRoleAppServiceImpl extends ServiceImpl<SysRoleAppMapper, SysRoleApp> implements SysRoleAppService {
}