package com.auth.sso.service.impl;

import com.auth.sso.entity.DbTableInfo;
import com.auth.sso.mapper.DbTableInfoMapper;
import com.auth.sso.service.DbTableInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class DbTableInfoServiceImpl extends ServiceImpl<DbTableInfoMapper, DbTableInfo> implements DbTableInfoService {
} 