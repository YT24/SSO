package com.auth.sso.service.impl;

import com.auth.sso.entity.OauthClient;
import com.auth.sso.mapper.OauthClientMapper;
import com.auth.sso.service.OauthClientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class OauthClientServiceImpl extends ServiceImpl<OauthClientMapper, OauthClient> implements OauthClientService {
} 