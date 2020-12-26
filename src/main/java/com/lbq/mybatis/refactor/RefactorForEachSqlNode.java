package com.lbq.mybatis.refactor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;

public class RefactorForEachSqlNode implements RefactorSqlNode {

	private Field contentsField;
	private Field openField;
	private Field closeField;
	
	public RefactorForEachSqlNode() {
		try {
			contentsField = ForEachSqlNode.class.getDeclaredField("contents");
			contentsField.setAccessible(true);
			openField = ForEachSqlNode.class.getDeclaredField("open");
			openField.setAccessible(true);
			closeField = ForEachSqlNode.class.getDeclaredField("close");
			closeField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	@Override
	public SqlNode refactor(SqlNode sqlNode) throws Exception {
		List<SqlNode> sqlNodes = new ArrayList<>();
		String open = (String) openField.get(sqlNode);
		sqlNodes.add(new StaticTextSqlNode(open));
		
		SqlNode contents = (SqlNode) contentsField.get(sqlNode);
		RefactorSqlNode refactorSqlNode = RefactorSqlNodeFactory.getHandler(contents);
		SqlNode contentsRefactor = refactorSqlNode.refactor(contents);
		sqlNodes.add(contentsRefactor);
		
		String close = (String) closeField.get(sqlNode);
		sqlNodes.add(new StaticTextSqlNode(close));
		
		return new MixedSqlNode(sqlNodes);
	}

}
