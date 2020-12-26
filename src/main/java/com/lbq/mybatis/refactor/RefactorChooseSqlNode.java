package com.lbq.mybatis.refactor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.scripting.xmltags.ChooseSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

public class RefactorChooseSqlNode implements RefactorSqlNode {

	private Field defaultSqlNodeField;
	
	private Field ifSqlNodesField;
	
	public RefactorChooseSqlNode() {
		try {
			defaultSqlNodeField = ChooseSqlNode.class.getDeclaredField("defaultSqlNode");
			defaultSqlNodeField.setAccessible(true);
			ifSqlNodesField = ChooseSqlNode.class.getDeclaredField("ifSqlNodes");
			ifSqlNodesField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	@Override
	public SqlNode refactor(SqlNode sqlNode) throws Exception{
		SqlNode defaultSqlNode = (SqlNode) defaultSqlNodeField.get(sqlNode);
		List<SqlNode> ifSqlNodes = (List<SqlNode>) ifSqlNodesField.get(sqlNode);
		List<SqlNode> contents = new ArrayList<>();
		for(SqlNode ifSqlNode : ifSqlNodes) {
			RefactorSqlNode refactorSqlNode = RefactorSqlNodeFactory.getHandler(ifSqlNode);
			SqlNode ifSqlNodeRefactor = refactorSqlNode.refactor(ifSqlNode);
			contents.add(ifSqlNodeRefactor);
		}
		
		if(defaultSqlNode != null) {
			RefactorSqlNode refactorSqlNode = RefactorSqlNodeFactory.getHandler(defaultSqlNode);
			SqlNode defaultSqlNodeRefactor = refactorSqlNode.refactor(defaultSqlNode);
			contents.add(defaultSqlNodeRefactor);
		}
		return new MixedSqlNode(contents);
	}

}
