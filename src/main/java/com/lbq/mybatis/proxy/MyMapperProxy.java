package com.lbq.mybatis.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class MyMapperProxy<T> implements InvocationHandler {
    
	private Class<T> mapperInterface;
	private SqlSession sqlSession;
	
	public MyMapperProxy(Class<T> mapperInterface, SqlSession sqlSession) {
		this.mapperInterface = mapperInterface;
		this.sqlSession = sqlSession;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		List<T> list = sqlSession.selectList(mapperInterface.getCanonicalName()+"."+method.getName());
		return list;
	}

}
