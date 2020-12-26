package com.lbq.mybatis.refactor;

import java.lang.reflect.Field;

import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.apache.ibatis.session.Configuration;

public class RefactorWhereSqlNode implements RefactorSqlNode {

	private Field contentsField;
	private Field configurationField;
	public RefactorWhereSqlNode() {
		try {
			contentsField = TrimSqlNode.class.getDeclaredField("contents");
			contentsField.setAccessible(true);
			configurationField = TrimSqlNode.class.getDeclaredField("configuration");
			configurationField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public SqlNode refactor(SqlNode sqlNode) throws Exception{
		Configuration configuration = (Configuration) configurationField.get(sqlNode);
		SqlNode contents = (SqlNode) contentsField.get(sqlNode);
		RefactorSqlNode refactorSqlNode = RefactorSqlNodeFactory.getHandler(contents);
		SqlNode contentsRefactor = refactorSqlNode.refactor(contents);
		return new WhereSqlNode(configuration, contentsRefactor);
	}

}
