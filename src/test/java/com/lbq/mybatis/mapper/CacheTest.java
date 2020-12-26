package com.lbq.mybatis.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.lbq.mybatis.model.SysRole;
import com.lbq.mybatis.model.SysUser;

import junit.framework.Assert;

public class CacheTest extends BaseMapperTest{

	@Test
	public void testL1Cache() {
		SqlSession sqlSession = getSqlSession();
		SysUser user1 = null;
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user1 = userMapper.selectById(1L);
			user1.setUserName("New Name");
			SysUser user2 = userMapper.selectById(1L);
			Assert.assertEquals("New Name", user2.getUserName());
			Assert.assertEquals(user1,user2);
		}finally {
			sqlSession.close();
		}
		System.out.println("开启新的sqlSession");
		sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user2 = userMapper.selectById(1L);
			Assert.assertNotSame("New Name",user2.getUserName());
			Assert.assertNotSame(user1, user2);
			userMapper.deleteById(2L);
			SysUser user3 = userMapper.selectById(1L);
			Assert.assertNotSame(user2, user3);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testL2Cache() {
		SqlSession sqlSession = getSqlSession();
		SysRole role1 = null;
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			role1 = roleMapper.selectRoleById(1L);
			role1.setRoleName("New Name");
			SysRole role2 = roleMapper.selectRoleById(1L);
			Assert.assertEquals("New Name", role2.getRoleName());
			Assert.assertEquals(role1, role2);
		}finally {
			sqlSession.close();
		}
		System.out.println("开启新的sqlSession");
		sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role2 = roleMapper.selectRoleById(1L);
			Assert.assertEquals("New Name", role2.getRoleName());
			Assert.assertNotSame(role1, role2);
			SysRole role3 = roleMapper.selectRoleById(1L);
			Assert.assertNotSame(role2, role3);
		}finally {
			sqlSession.close();
		}
	}
}
