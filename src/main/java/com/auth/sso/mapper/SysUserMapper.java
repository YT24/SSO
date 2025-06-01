package com.auth.sso.mapper;

import com.auth.sso.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT u.* FROM sys_user u JOIN sys_user_role ur ON u.id = ur.user_id WHERE ur.role_id = #{roleId}")
    List<SysUser> selectUsersByRoleId(@Param("roleId") Long roleId);
}