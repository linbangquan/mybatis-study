package com.lbq.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.lbq.mybatis.model.SysRole;
@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {

	SysRole selectRoleById(Long id);
	
	List<SysRole> selectAllRoleAndPrivileges();
	
	List<SysRole> selectRoleByUserId(@Param("userId") Long userId);
	
	List<SysRole> selectRoleByUserIdChoose(@Param("userId") Long userId);

	int updateById(SysRole role);
	
	List<SysRole> selectAll();
	
	List<SysRole> selectAll(RowBounds rowBounds);
}
