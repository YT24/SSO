package com.auth.sso.service.impl;

import com.auth.sso.entity.SysRoleApp;
import com.auth.sso.entity.SysRole;
import com.auth.sso.mapper.SysRoleAppMapper;
import com.auth.sso.mapper.SysRoleMapper;
import com.auth.sso.service.SysRoleAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysRoleAppServiceImpl extends ServiceImpl<SysRoleAppMapper, SysRoleApp> implements SysRoleAppService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> listRolesByClientId(String clientId) {
        return sysRoleMapper.selectRolesByClientId(clientId);
    }
}