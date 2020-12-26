package com.lbq.mybatis.mapper;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.lbq.mybatis.model.Enabled;
import com.lbq.mybatis.model.SysPrivilege;
import com.lbq.mybatis.model.SysRole;
import com.lbq.mybatis.model.SysUser;
import com.lbq.mybatis.plugin.PageRowBounds;
import com.lbq.mybatis.proxy.MyMapperProxy;

import junit.framework.Assert;

public class UserMapperTest extends BaseMapperTest {

	@Test
	public void testSelectById() {
		
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1L);
			Assert.assertNotNull(user);
			Assert.assertEquals("admin", user.getUserName());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAll() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAll();
			Assert.assertNotNull(userList);
			Assert.assertTrue(userList.size() > 0);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectRolesByUserId() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> roleList = userMapper.selectRolesByUserId(1L);
			Assert.assertNotNull(roleList);
			Assert.assertTrue(roleList.size() > 0);
			System.out.println(roleList);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsert() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassword("123456");
			user.setUserEmail("test1@mybatis.tk");
			user.setUserInfo("test1 info");
			user.setHeadImg(new byte[] {1,2,3});
			user.setCreateTime(new Date());
			//将新建的对象插入数据库中，特别注意这里的返回值result是执行的SQL影响的行数
			int result = userMapper.insert(user);
			Assert.assertEquals(1,result);
			//id为null,没有给id赋值,并且没有配置回写id的值
			//Assert.assertNull(user.getId());
			Assert.assertNotNull(user.getId());
		}finally {
			//为了不影响其他测试，这里选择回滚
			//由于默认的sqlSessionFactory.openSession()是不自动提交的
			//因此不手动执行commit也不会提交到数据库
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsert3() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassword("123456");
			user.setUserEmail("test1@mybatis.tk");
			user.setUserInfo("test1 info");
			user.setHeadImg(new byte[] {1,2,3});
			user.setCreateTime(new Date());
			//将新建的对象插入数据库中，特别注意这里的返回值result是执行的SQL影响的行数
			int result = userMapper.insert3(user);
			Assert.assertEquals(1,result);
			//id为null,没有给id赋值,并且没有配置回写id的值
			//Assert.assertNull(user.getId());
			Assert.assertNotNull(user.getId());
		}finally {
			//为了不影响其他测试，这里选择回滚
			//由于默认的sqlSessionFactory.openSession()是不自动提交的
			//因此不手动执行commit也不会提交到数据库
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdateById() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1L);
			Assert.assertEquals("admin",user.getUserName());
			user.setUserName("admin_test");
			user.setUserEmail("test@mybatis.tk");
			int result = userMapper.updateById(user);
			Assert.assertEquals(1, result);
			user = userMapper.selectById(1L);
			Assert.assertEquals("admin_test", user.getUserName());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testDeleteById() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			
			SysUser user1 = userMapper.selectById(1L);
			Assert.assertNotNull(user1);
			Assert.assertEquals(1,userMapper.deleteById(1L));
			Assert.assertNull(userMapper.selectById(1L));
			
			SysUser user2 = userMapper.selectById(1001L);
			Assert.assertNotNull(user2);
			Assert.assertEquals(1,userMapper.deleteById(user2));
			Assert.assertNull(userMapper.selectById(1001L));
			
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectRolesByUserIdAndRoleEnabled() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> roleList = userMapper.selectRolesByUserIdAndRoleEnabled(1L,1);
			Assert.assertNotNull(roleList);
			Assert.assertTrue(roleList.size()>0);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testMyMapperProxy() {
		SqlSession sqlSession = getSqlSession();
		try {
			MyMapperProxy userMapperProxy = new MyMapperProxy(UserMapper.class, sqlSession);
			UserMapper userMapper = (UserMapper)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {UserMapper.class}, userMapperProxy);
			List<SysUser> user = userMapper.selectAll();
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectByUser() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser query = new SysUser();
			query.setUserName("ad");
			List<SysUser> userList = userMapper.selectByUser(query);
			Assert.assertTrue(userList.size() > 0);
			
			query = new SysUser();
			query.setUserEmail("test@mybatis.tk");
			userList = userMapper.selectByUser(query);
			Assert.assertTrue(userList.size() > 0);
			query = new SysUser();
			query.setUserName("ad");
			query.setUserEmail("test@mybatis.tk");
			userList = userMapper.selectByUser(query);
			Assert.assertTrue(userList.size() == 0);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdateByIdSelective() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1L);
			user.setUserEmail("test@mybatis.tk");
			int result = userMapper.updateByIdSelective(user);
			Assert.assertEquals(1, result);
			user = userMapper.selectById(1L);
			Assert.assertEquals("admin", user.getUserName());
			Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsert2Selective() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test-selective");
			user.setUserPassword("123456");
			user.setUserInfo("test info");
			user.setCreateTime(new Date());
			userMapper.insert2(user);
			user = userMapper.selectById(user.getId());
			Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectByIdOrUserName() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser query = new SysUser();
			query.setId(1L);
			query.setUserName("admin");
			SysUser user = userMapper.selectByIdOrUserName(query);
			Assert.assertNotNull(user);
			query.setId(null);
			user = userMapper.selectByIdOrUserName(query);
			Assert.assertNotNull(user);
			query.setUserName(null);
			user = userMapper.selectByIdOrUserName(query);
			Assert.assertNull(user);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectByIdList() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<Long> idList = new ArrayList<Long>();
			idList.add(1L);
			idList.add(1001L);
			List<SysUser> userList = userMapper.selectByIdList(idList);
			Assert.assertEquals(2, userList.size());
			Long[] idArray = {1L,1001L};
			userList = userMapper.selectByIdArray(idArray);
			Assert.assertEquals(2, userList.size());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsertList() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = new ArrayList<>();
			for(int i=0; i<2; i++) {
				SysUser user = new SysUser();
				user.setUserName("test" + i);
				user.setUserPassword("123456");
				user.setUserEmail("test@mybatis.tk");
				userList.add(user);
			}
			int result = userMapper.insertList(userList);
			for(SysUser user : userList) {
				System.out.println(user.getId());
			}
			Assert.assertEquals(2, result);
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test 
	public void testUpdateByMap() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String, Object> map = new HashMap<>();
			map.put("id",1L);
			map.put("user_email","test@mybatis.tk");
			map.put("user_password", "12345678");
			
			userMapper.updateByMap(map);
			SysUser user = userMapper.selectById(1L);
			Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserAndRoleById() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getRole());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserAndRoleById2() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById2(1001L);
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getRole());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserAndRoleById3() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById3(1001L);
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getRole());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserAndRoleByIdSelect() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleByIdSelect(1001L);
			Assert.assertNotNull(user);
			user.equals(null);
			System.out.println("------------------------------------------------------");
			Assert.assertNotNull(user.getRole());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllUserAndRoles() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAllUserAndRoles();
			System.out.println("用户数：" + userList.size());
			for(SysUser user : userList) {
				System.out.println("用户名：" + user.getUserName());
				for(SysRole role : user.getRoleList()) {
					System.out.println("角色名：" + role.getRoleName());
				}
			}
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllUserAndRoles2() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAllUserAndRoles2();
			System.out.println("用户数：" + userList.size());
			for(SysUser user : userList) {
				System.out.println("用户名：" + user.getUserName());
				for(SysRole role : user.getRoleList()) {
					System.out.println("角色名：" + role.getRoleName());
					for(SysPrivilege privilege : role.getPrivilegeList()) {
						System.out.println("权限名：" + privilege.getPrivilegeName());
					}
				}
			}
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllRoleAndPrivileges() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<SysRole> roleList = roleMapper.selectAllRoleAndPrivileges();
			for(SysRole role : roleList) {
				System.out.println(role);
			}
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllUserAndRoleSelect() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectAllUserAndRoleSelect(1L);
			System.out.println("用户名：" + user.getUserName());
			for(SysRole role : user.getRoleList()) {
				System.out.println("角色名：" + role.getRoleName());
				for(SysPrivilege privilege : role.getPrivilegeList()) {
					System.out.println("权限名：" + privilege.getPrivilegeName());
				}
			}
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectRoleByUserIdChoose() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role1 = roleMapper.selectRoleById(2L);
//			role1.setEnabled(0);
			roleMapper.updateById(role1);
			List<SysRole> roleList = roleMapper.selectRoleByUserIdChoose(1L);
			for(SysRole role : roleList) {
				System.out.println("角色名：" + role.getRoleName());
				if(role.getId().equals(1L)) {
					Assert.assertNotNull(role.getPrivilegeList());
				}else if(role.getId().equals(2L)) {
					Assert.assertNull(role.getPrivilegeList());
					continue;
				}
				for(SysPrivilege privilege : role.getPrivilegeList()){
					System.out.println("权限名：" + privilege.getPrivilegeName());
				}
			}
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserById() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1L);
			userMapper.selectUserById(user);
			Assert.assertNotNull(user.getUserName());
			System.out.println("用户名：" + user.getUserName());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserPage() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userName","ad");
			params.put("offset",0);
			params.put("limit", 10);
			List<SysUser> userList = userMapper.selectUserPage(params);
			Long total = (Long) params.get("total");
			System.out.println("总数：" + total);
			for(SysUser user : userList) {
				System.out.println("用户名：" + user.getUserName());
			}
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsertAndDelete() {
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassword("123456");
			user.setUserEmail("test@mybatis.tk");
			user.setUserInfo("test info");
			user.setHeadImg(new byte[] {1,2,3});
			
			userMapper.insertUserAndRoles(user,"1,2");
			Assert.assertNotNull(user.getId());
			Assert.assertNotNull(user.getCreateTime());
			userMapper.deleteUserById(user.getId());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdateById2() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectRoleById(2L);
			Assert.assertEquals(Enabled.enabled, role.getEnabled());
			role.setEnabled(Enabled.disabled);
			roleMapper.updateById(role);
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllByRowBounds() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			//查询第一个，使用RowBounds类型时不会查询总数
			RowBounds rowBounds = new RowBounds(0,1);
			List<SysRole> list = roleMapper.selectAll(rowBounds);
			for(SysRole role : list) {
				System.out.println("角色名：" + role.getRoleName());		
			}
			//使用PageRowBounds时会查询总数
			PageRowBounds pageRowBounds = new PageRowBounds(0,1);
			list = roleMapper.selectAll(pageRowBounds);
			//获取总数
			System.out.println("查询总数：" + pageRowBounds.getTotal());
			for(SysRole role : list) {
				System.out.println("角色名：" + role.getRoleName());
			}
			//再次查询获取第二个角色
			pageRowBounds = new PageRowBounds(1,1);
			list = roleMapper.selectAll(pageRowBounds);
			//获取总数
			System.out.println("查询总数：" + pageRowBounds.getTotal());
			for(SysRole role : list) {
				System.out.println("角色名：" + role.getRoleName());
			}
		}finally {
			sqlSession.close();
		}
	}
}
