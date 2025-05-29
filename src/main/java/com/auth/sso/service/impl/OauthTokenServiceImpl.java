package com.auth.sso.service.impl;

import com.auth.sso.entity.OauthToken;
import com.auth.sso.mapper.OauthTokenMapper;
import com.auth.sso.service.OauthTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class OauthTokenServiceImpl extends ServiceImpl<OauthTokenMapper, OauthToken> implements OauthTokenService {
} 