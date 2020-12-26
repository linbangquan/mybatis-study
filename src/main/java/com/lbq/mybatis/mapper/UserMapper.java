package com.lbq.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lbq.mybatis.model.SysRole;
import com.lbq.mybatis.model.SysUser;

public interface UserMapper {
	/**
	 * 通过id查询用户
	 * @param id
	 * @return
	 */
	SysUser selectById(@Param("id") Long id);
	
	/**
	 * 查询全部用户
	 * @return
	 */
	List<SysUser> selectAll();
	
	/**
	 * 根据用户id获取角色信息
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRolesByUserId(Long userId);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	int insert(SysUser user);
	
	int insert2(SysUser user);
	/**
	 * 新增用户-使用selectKey方式
	 * @param user
	 * @return
	 */
	int insert3(SysUser user);
	
	/**
	 * 根据主键更新
	 * @param user
	 * @return
	 */
	int updateById(SysUser user);
	
	/**
	 * 通过主键删除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	
	int deleteById(SysUser user);
	
	/**
	 * 根据用户id和角色的enabled状态获取用户的角色
	 * @param userId
	 * @param enabled
	 * @return
	 */
	List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId")Long userId, @Param("enabled")Integer enabled);
	
	/**
	 * 根据动态条件查询用户信息
	 * @param sysUser
	 * @return
	 */
	List<SysUser> selectByUser(SysUser sysUser);
	
	/**
	 * 根据主键更新
	 * @param sysUser
	 * @return
	 */
	int updateByIdSelective(SysUser sysUser);
	
	/**
	 * 根据用户id或用户名查询
	 * @param sysUser
	 * @return
	 */
	SysUser selectByIdOrUserName(SysUser sysUser);
	
	/**
	 * 根据用户id集合查询
	 * @param idList
	 * @return
	 */
	List<SysUser> selectByIdList(List<Long> idList);
	
	/**
	 * 根据用户id集合查询
	 * @param idList
	 * @return
	 */
	List<SysUser> selectByIdArray(Long[] idArray);
	
	/**
	 * 批量插入用户信息
	 * @param userList
	 * @return
	 */
	int insertList(List<SysUser> userList);
	
	/**
	 * 通过Map更新列
	 * @param map
	 * @return
	 */
	int updateByMap(Map<String, Object> map);
	
	/**
	 * 根据用户id获取用户信息和用户的角色信息
	 * @param id
	 * @return
	 */
	SysUser selectUserAndRoleById(Long id);
	
	/**
	 * 根据用户id获取用户信息和用户的角色信息
	 * @param id
	 * @return
	 */
	SysUser selectUserAndRoleById2(Long id);
	
	/**
	 * 根据用户id获取用户信息和用户的角色信息
	 * @param id
	 * @return
	 */
	SysUser selectUserAndRoleById3(Long id);
	
	
	SysUser selectUserAndRoleByIdSelect(Long id);
	
	/**
	 * 获取所有的用户以及对应的所有角色
	 * @return
	 */
	List<SysUser> selectAllUserAndRoles();
	
	/**
	 * 获取所有的用户以及对应的所有角色
	 * @return
	 */
	List<SysUser> selectAllUserAndRoles2();
	
	/**
	 * 通过嵌套查询获取指定用户的信息以及用户的角色和权限信息
	 * @param id
	 * @return
	 */
	SysUser selectAllUserAndRoleSelect(@Param("id")Long id);
	
	/**
	 * 使用存储过程查询用户信息
	 * @param user
	 */
	void selectUserById(SysUser user);
	
	/**
	 * 使用存储过程分页查询
	 * @param params
	 */
	List<SysUser> selectUserPage(Map<String, Object> params);
	
	/**
	 * 保存用户信息和角色关联信息
	 * @return
	 */
	int insertUserAndRoles(@Param("user") SysUser user, @Param("roleIds") String roleIds);
	
	/**
	 * 根据用户id删除用户和用户的角色信息
	 * @param id
	 * @return
	 */
	int deleteUserById(Long id);
}
