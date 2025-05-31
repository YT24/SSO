package com.auth.sso.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("sys_user_role")
public class SysUserRole {
    private Long id;
    private Long userId;
    private Long roleId;
    private Date createTime;
}