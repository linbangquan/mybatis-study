package com.lbq.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lbq.mybatis.model.SysPrivilege;

public interface RolePrivilegeMapper {

	List<SysPrivilege> selectPrivilegeByRoleId(@Param("roleId") Long roleId);
}
