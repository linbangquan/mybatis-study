package com.lbq.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.lbq.mybatis.model.SysUser;

import junit.framework.Assert;

public class UserRoleMapperTest extends BaseMapperTest{

	@Test
	public void testSelectByUser() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserRoleMapper userRoleMapper = sqlSession.getMapper(UserRoleMapper.class);
			SysUser query = new SysUser();
			query.setUserName("ad");
			List<SysUser> userList = userRoleMapper.selectByUser(query);
			Assert.assertTrue(userList.size() > 0);
			
			query = new SysUser();
			query.setUserEmail("test@mybatis.tk");
			userList = userRoleMapper.selectByUser(query);
			Assert.assertTrue(userList.size() > 0);
			query = new SysUser();
			query.setUserName("ad");
			query.setUserEmail("test@mybatis.tk");
			userList = userRoleMapper.selectByUser(query);
			Assert.assertTrue(userList.size() == 0);
		}finally {
			sqlSession.close();
		}
	}
}
