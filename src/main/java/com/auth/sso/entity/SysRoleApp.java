package com.auth.sso.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("sys_role_app")
public class SysRoleApp {
    private Long id;
    private Long roleId;
    private String clientId;
    private Date createTime;
}