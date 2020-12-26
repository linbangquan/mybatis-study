package com.lbq.mybatis.mapper;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.ChooseSqlNode;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;

import com.lbq.mybatis.refactor.RefactorMixedSqlNode;
import com.lbq.mybatis.refactor.RefactorSqlNode;
import com.lbq.mybatis.refactor.RefactorSqlNodeFactory;

public class BaseMapperTest {
	private static SqlSessionFactory sqlSessionFactory;
	private static Field rootSqlNodeField;
	@BeforeClass
	public static void init() {
		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//			Configuration config = sqlSessionFactory.getConfiguration();
//			Collection<String> names = config.getMappedStatementNames();
//			for(String name : names) {
//				System.out.println(name);
//			}
//			Collection<MappedStatement> MappedStatements = config.getMappedStatements();
//			System.out.println(MappedStatements.size());
//			List<Integer> sqlHashCodeList = new ArrayList<>();
//			try {
//				rootSqlNodeField = DynamicSqlSource.class.getDeclaredField("rootSqlNode");
//				rootSqlNodeField.setAccessible(true);
//			} catch (NoSuchFieldException e1) {
//				e1.printStackTrace();
//			} catch (SecurityException e1) {
//				e1.printStackTrace();
//			}
//			for(Object obj:MappedStatements) {
//				Class<? extends Object> clazz = obj.getClass();
//				System.out.println(clazz.getName());
//				if(obj instanceof MappedStatement) {
//					MappedStatement mappedStatement = (MappedStatement) obj;
//					String[] arr = mappedStatement.getKeyColumns();
//					ParameterMap paramenterMap = mappedStatement.getParameterMap();
//					SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
//					
//					String TypeName = sqlCommandType.name();
//					System.out.println(TypeName);
//					SqlSource sqlSource = mappedStatement.getSqlSource();
//					System.out.println(sqlSource.getClass().getName());
////					Object parameterObject = null;
//					BoundSql boundSql = null;
//					if(sqlSource instanceof DynamicSqlSource) {
//						DynamicSqlSource dynamicSqlSource = (DynamicSqlSource) sqlSource;
//						try {
//							SqlNode rootSqlNode = (SqlNode) rootSqlNodeField.get(dynamicSqlSource);
//							String simpleName = rootSqlNode.getClass().getSimpleName();
//							RefactorSqlNode refactorSqlNode = RefactorSqlNodeFactory.getInstance(simpleName);
//							SqlNode refactorRootSqlNode = refactorSqlNode.refactor(rootSqlNode);
//							DynamicSqlSource newDynamicSqlSource = new DynamicSqlSource(config,refactorRootSqlNode);
//							boundSql = newDynamicSqlSource.getBoundSql(null);
//						} catch (IllegalArgumentException e) {
//							e.printStackTrace();
//						} catch (IllegalAccessException e) {
//							e.printStackTrace();
//						}
// 
//					}else if (sqlSource instanceof RawSqlSource) {
//						boundSql = sqlSource.getBoundSql(null);
//					}
//					
//					String sql = boundSql.getSql();
//					int sqlHashCode = sql.hashCode();
//					if(sqlHashCodeList.contains(sqlHashCode)) {
//						continue;
//					}
//					sqlHashCodeList.add(sqlHashCode);
//					System.out.println(sql);
//				}
//			}
			reader.close();
		}catch(IOException ignore) {
			ignore.printStackTrace();
		}
	}
	
	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
}
