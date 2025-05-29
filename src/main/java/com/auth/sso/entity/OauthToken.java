package com.auth.sso.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("oauth_token")
public class OauthToken {
    @TableId
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String clientId;
    private String scope;
    private LocalDateTime expiresAt;
    private String idToken;
    private LocalDateTime createTime;
} 