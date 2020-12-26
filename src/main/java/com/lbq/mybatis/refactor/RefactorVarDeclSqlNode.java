package com.lbq.mybatis.refactor;

import java.lang.reflect.Field;

import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;

public class RefactorVarDeclSqlNode implements RefactorSqlNode {

//	private Field expressionField;
	
	private Field nameField;
	
	public RefactorVarDeclSqlNode() {
		try {
//			expressionField = VarDeclSqlNode.class.getDeclaredField("expression");
			nameField = VarDeclSqlNode.class.getDeclaredField("name");
			nameField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	@Override
	public SqlNode refactor(SqlNode sqlNode) throws Exception {
//		String expression = (String) expressionField.get(sqlNode);
		String name = (String) nameField.get(sqlNode);
		return new VarDeclSqlNode(name, "'?'");
	}

}
