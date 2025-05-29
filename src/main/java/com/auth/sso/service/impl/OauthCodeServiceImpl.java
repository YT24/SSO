package com.auth.sso.service.impl;

import com.auth.sso.entity.OauthCode;
import com.auth.sso.mapper.OauthCodeMapper;
import com.auth.sso.service.OauthCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class OauthCodeServiceImpl extends ServiceImpl<OauthCodeMapper, OauthCode> implements OauthCodeService {
} 