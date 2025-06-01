package com.auth.sso.mapper;

import com.auth.sso.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("SELECT r.* FROM sys_role r JOIN sys_role_app ra ON r.id = ra.role_id WHERE ra.client_id = #{clientId}")
    List<SysRole> selectRolesByClientId(@Param("clientId") String clientId);
}