package com.auth.sso.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("sys_role")
public class SysRole {
    private Long id;
    private String roleName;
    private String roleDesc;
    private Date createTime;
}