package com.lbq.mybatis.refactor;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;

public class RefactorTextSqlNode implements RefactorSqlNode {

	private Field textField;
	private Field injectionFilterField;
	
	public RefactorTextSqlNode() {
		try {
			textField = TextSqlNode.class.getDeclaredField("text");
			textField.setAccessible(true);
			injectionFilterField = TextSqlNode.class.getDeclaredField("injectionFilter");
			injectionFilterField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	@Override
	public SqlNode refactor(SqlNode sqlNode) throws Exception {	
		Pattern injectionFilter = (Pattern) injectionFilterField.get(sqlNode);
		String text = (String) textField.get(sqlNode);
		text = replaceVariable("${", "}", text);		
		return new TextSqlNode(text, injectionFilter);	
	}
	private String replaceVariable(String open, String close, String oldText) {
		int openIndex = oldText.indexOf(open);
		int closeIndex = oldText.indexOf(close);
		if(openIndex >= 0 && closeIndex >= 0) {
			do {
			oldText = oldText.substring(0, openIndex) + "??" + oldText.substring(closeIndex + 1);
			openIndex = oldText.indexOf(open);
			closeIndex = oldText.indexOf(close);
			}while(openIndex >= 0 && closeIndex >= 0);			
		}
		return oldText;		
	}
}
