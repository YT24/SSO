package com.auth.sso.service.impl;

import com.auth.sso.entity.DbFieldInfo;
import com.auth.sso.mapper.DbFieldInfoMapper;
import com.auth.sso.service.DbFieldInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class DbFieldInfoServiceImpl extends ServiceImpl<DbFieldInfoMapper, DbFieldInfo> implements DbFieldInfoService {
} 