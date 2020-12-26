package com.lbq.mybatis.mapper;

import java.util.List;

import com.lbq.mybatis.model.SysUser;

public interface UserRoleMapper {

	List<SysUser> selectByUser(SysUser user);
}
