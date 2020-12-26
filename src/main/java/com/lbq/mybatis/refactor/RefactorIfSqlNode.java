package com.lbq.mybatis.refactor;

import java.lang.reflect.Field;

import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

public class RefactorIfSqlNode implements RefactorSqlNode {

	private Field contentsField;
	
	public RefactorIfSqlNode() {
		try {
			contentsField = IfSqlNode.class.getDeclaredField("contents");
			contentsField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	@Override
	public SqlNode refactor(SqlNode sqlNode) throws Exception {
		SqlNode contents = (SqlNode) contentsField.get(sqlNode);
		RefactorSqlNode refactorSqlNode = RefactorSqlNodeFactory.getHandler(contents);		
		return refactorSqlNode.refactor(contents);
	}

}
