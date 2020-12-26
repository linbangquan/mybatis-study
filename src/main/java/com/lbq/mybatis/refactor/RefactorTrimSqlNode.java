package com.lbq.mybatis.refactor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.apache.ibatis.session.Configuration;

public class RefactorTrimSqlNode implements RefactorSqlNode {

	private Field contentsField;	
	private Field prefixField;
	private Field suffixField;
	private Field prefixesToOverrideField;
	private Field suffixesToOverrideField;
	private Field configurationField;
	public RefactorTrimSqlNode() {
		try {
			contentsField = TrimSqlNode.class.getDeclaredField("contents");
			contentsField.setAccessible(true);
			prefixField = TrimSqlNode.class.getDeclaredField("prefix");
			prefixField.setAccessible(true);
			suffixField = TrimSqlNode.class.getDeclaredField("suffix");
			suffixField.setAccessible(true);
			prefixesToOverrideField = TrimSqlNode.class.getDeclaredField("prefixesToOverride");
			prefixesToOverrideField.setAccessible(true);
			suffixesToOverrideField = TrimSqlNode.class.getDeclaredField("suffixesToOverride");
			suffixesToOverrideField.setAccessible(true);
			configurationField = TrimSqlNode.class.getDeclaredField("configurationField");
			configurationField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	@Override
	public SqlNode refactor(SqlNode sqlNode) throws Exception {
		String prefix = (String) prefixField.get(sqlNode);
		String suffix = (String) suffixField.get(sqlNode);
		List<String> prefixesToOverride = (List<String>) prefixesToOverrideField.get(sqlNode);
		String prefixes = toOverride(prefixesToOverride);
		List<String> suffixesToOverride = (List<String>) suffixesToOverrideField.get(sqlNode);
		String suffixes = toOverride(suffixesToOverride);
		Configuration configuration = (Configuration) configurationField.get(sqlNode);
		SqlNode contents = (SqlNode) contentsField.get(sqlNode);
		RefactorSqlNode refactorSqlNode = RefactorSqlNodeFactory.getHandler(contents);
		SqlNode contentsrefactor = refactorSqlNode.refactor(contents);
		return new TrimSqlNode(configuration, contentsrefactor, prefix, prefixes, suffix, suffixes);
	}
	
	private String toOverride(List<String> OverrideList) {
		if(OverrideList == null) {
			return null;
		}
		String toOverride = null;
		for(int i = 0; i < OverrideList.size(); i++) {
			if(i == 0) {
				toOverride = OverrideList.get(i);
			}else {
				toOverride += "|" + OverrideList.get(i);
			}
		}
		return toOverride;
	}
	
//	public static void main(String[] args) {
//		String overrides = "1|2|3|4|5";
//		StringTokenizer parser = new StringTokenizer(overrides, "|", false);
//		List<String> list = new ArrayList<String>(parser.countTokens());
//		while(parser.hasMoreTokens()) {
//			list.add(parser.nextToken().toUpperCase(Locale.ENGLISH));
//		}
//		System.out.println(list.toString());
//	}

}
