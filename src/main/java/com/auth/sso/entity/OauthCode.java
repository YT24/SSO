package com.auth.sso.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oauth_code")
public class OauthCode {
    @TableId
    private String code;
    private Long userId;
    private String clientId;
    private String redirectUri;
    private String scope;
    private LocalDateTime expiresAt;
    private LocalDateTime createTime;
} 