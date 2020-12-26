package com.lbq.mybatis.refactor;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.scripting.xmltags.SqlNode;

public class RefactorSqlNodeFactory {

	private static String classPathPrefix;
	private static Map<String, RefactorSqlNode> map = new HashMap<>();
	static {
		String refactorSqlNodeClassName = RefactorSqlNode.class.getName();
		int index = refactorSqlNodeClassName.indexOf("SqlNode");
		classPathPrefix = refactorSqlNodeClassName.substring(0, index);
	}
	public static RefactorSqlNode getHandler(SqlNode sqlNode) {
		String simpleName = sqlNode.getClass().getSimpleName();
		String className = classPathPrefix + simpleName;
		if(map.containsKey(className)) {
			return map.get(className);
		}
		try {
			Class clazz = Class.forName(className);
			RefactorSqlNode refactorSqlNode = (RefactorSqlNode) clazz.newInstance();
			map.put(className, refactorSqlNode);
			return refactorSqlNode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
