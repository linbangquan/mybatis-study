package com.lbq.mybatis.test;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.lbq.mybatis.parse.commandtype.CommandTypeFactory;
import com.lbq.mybatis.parse.commandtype.CommandTypeHandler;
import com.lbq.mybatis.refactor.RefactorSqlNode;
import com.lbq.mybatis.refactor.RefactorSqlNodeFactory;
import com.lbq.mybatis.util.MySQLFormatter;
import com.lbq.mybatis.util.MySQLFormatter2;
import com.lbq.mybatis.util.ParseSql;

public class Test {
	private static SqlSessionFactory sqlSessionFactory;
	private static Field rootSqlNodeField;
	public static void main(String[] args) throws Exception{
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			Configuration config = sqlSessionFactory.getConfiguration();
			Collection<String> names = config.getMappedStatementNames();
			for(String name : names) {
				System.out.println(name);
			}
			Collection<MappedStatement> MappedStatements = config.getMappedStatements();
			System.out.println(MappedStatements.size());
			List<Integer> sqlHashCodeList = new ArrayList<>();
			try {
				rootSqlNodeField = DynamicSqlSource.class.getDeclaredField("rootSqlNode");
				rootSqlNodeField.setAccessible(true);
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}
			for(Object obj:MappedStatements) {
				Class<? extends Object> clazz = obj.getClass();
				System.out.println(clazz.getName());
				if(obj instanceof MappedStatement) {
					MappedStatement mappedStatement = (MappedStatement) obj;
					
					SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();					
					String typeName = sqlCommandType.name();
					System.out.println(typeName);
					
					SqlSource sqlSource = mappedStatement.getSqlSource();
					System.out.println(sqlSource.getClass().getName());
					BoundSql boundSql = null;
					if(sqlSource instanceof DynamicSqlSource) {
						DynamicSqlSource dynamicSqlSource = (DynamicSqlSource) sqlSource;
						SqlNode rootSqlNode = (SqlNode) rootSqlNodeField.get(dynamicSqlSource);
						RefactorSqlNode refactorSqlNode = RefactorSqlNodeFactory.getHandler(rootSqlNode);
						SqlNode refactorRootSqlNode = refactorSqlNode.refactor(rootSqlNode);
						DynamicSqlSource newDynamicSqlSource = new DynamicSqlSource(config,refactorRootSqlNode);
						boundSql = newDynamicSqlSource.getBoundSql(null);
//						boundSql = sqlSource.getBoundSql(null);
					}else if (sqlSource instanceof RawSqlSource) {
						boundSql = sqlSource.getBoundSql(null);
					}					
					String sql = boundSql.getSql();
					int sqlHashCode = sql.hashCode();
					if(sqlHashCodeList.contains(sqlHashCode)) {
						continue;
					}
					sqlHashCodeList.add(sqlHashCode);
					System.out.println(sql);					
					System.out.println("----------------------------------------------------------------------------");
					CommandTypeHandler commandTypeHandler = CommandTypeFactory.getHandler(typeName);
					Map<String, String> map = commandTypeHandler.parse(sql);
					for(Entry<String,String> entry : map.entrySet()) {
						System.out.println(entry.getKey() + ":" + entry.getValue());
					}
//					ParseSql.parse(TypeName,sql);
//					System.out.println(MySQLFormatter2.format(sql));
				}
			}
			reader.close();
		}catch(IOException ignore) {
			ignore.printStackTrace();
		}
	}
}
