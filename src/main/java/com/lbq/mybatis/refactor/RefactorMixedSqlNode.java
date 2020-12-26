package com.lbq.mybatis.refactor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

public class RefactorMixedSqlNode implements RefactorSqlNode {
	
	private Field contentsField;
	
	public RefactorMixedSqlNode() {
		try {
			contentsField = MixedSqlNode.class.getDeclaredField("contents");
			contentsField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	@Override
	public SqlNode refactor(SqlNode sqlNode) throws Exception{
		List<SqlNode> newContents = new ArrayList<>();
		List<SqlNode> contents = (List<SqlNode>) contentsField.get(sqlNode);
		for(SqlNode content : contents) {			
			RefactorSqlNode refactorSqlNode = RefactorSqlNodeFactory.getHandler(content);
			SqlNode contentRefactor = refactorSqlNode.refactor(content);
			newContents.add(contentRefactor);
		}		
		return new MixedSqlNode(newContents);
	}

}
