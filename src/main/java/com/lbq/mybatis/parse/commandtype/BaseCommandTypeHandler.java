package com.lbq.mybatis.parse.commandtype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseCommandTypeHandler implements CommandTypeHandler {

	@Override
	public Map<String, String> parse(String sql) {
		sql = sql.replaceAll("\\.`", ".");
		sql = sql.replaceAll("`", " ");
		sql = sql.replaceAll("\\s+|\n|\r|\f|\t", " ");
		
		sql = sql.replaceAll("\\s*\\(\\s*", " (");
		sql = sql.replaceAll("\\s*\\)\\s*", ") ");
		sql = sql.replaceAll("\\s*=\\s*", "=");
		sql = sql.replaceAll("\\s*,\\s*", ",");
		
		List<String> keywords = new ArrayList<String>() {{
			add("INSERT INTO ");
			add(" VALUES ");
			add(" delete ");
			add("update ");
			add(" set ");
			add("select ");
			add(" distinct ");
			add(" as ");
			add(" from ");
			add(" left ");
			add(" right ");
			add(" join ");
			add(" on ");
			add(" where ");
			add(" and ");
			add(" or ");			
			add(" group ");
			add(" by ");
			add(" having ");
			add(" order ");
			add(" limit ");
		}};
		for(String keyword : keywords) {
			sql = sql.replaceAll(keyword, keyword.toUpperCase());
		}
			
		System.out.println("BaseCommandTypeHandler:"+sql);
		return myParse(sql);
	}

	public abstract Map<String, String> myParse(String sql);
	
	public Map<String, String> keywordsParse(String sql, List<String> keywords) {
		Map<String, String> map = new HashMap<>();		
		int startIndex = 0;
		for(String keyword : keywords) {
			int keywordIndex = findKeywordIndexOf(sql, keyword, startIndex);		
			if(keywordIndex == -1) {
				continue;			
			}
			startIndex = keywordIndex;
			int nextIndex = findNextKeywordIndex(sql, keyword, startIndex, keywords);
			String a = sql.substring(startIndex, nextIndex);
			map.put(keyword.trim(),a);
		}
		return map;
	}
	
	public int findNextKeywordIndex(String sql, String keyword, int startIndex, List<String> keywords) {
		int index = keywords.indexOf(keyword);
		int nextKeywordIndex = -1;
		for(int i = index + 1; i < keywords.size(); i++) {
			nextKeywordIndex = findKeywordIndexOf(sql, keywords.get(i), startIndex);
			if(nextKeywordIndex != -1) {
				return nextKeywordIndex;
			}
		}
		return sql.length();
	}

	public int findKeywordIndexOf(String sql, String keyword, int startIndex) {
		int index = sql.indexOf(keyword, startIndex);
		while(index != -1 && !isMatch(sql, startIndex, index)) {
			index = sql.indexOf(keyword, index + 1);
		};
		return index;
	}
	public int pairedBrackets(String sql, int fromIndex) {		
		for(int i = fromIndex; i < sql.length(); i++) {
			int count = 0;
			int asciiValue = sql.charAt(i);
			if(asciiValue==40) {
				count++;
			}else if(asciiValue==41) {
				count--;
			}
			if(count==0) {
				return i;
			}
		}
		return -1;
	}

	public boolean isMatch(String sql, int fromIndex, int toIndex) {
		int count = 0;
		for(int i = fromIndex; i < toIndex; i++) {		
			int asciiValue = sql.charAt(i);
			if(asciiValue==40) {
				count++;
			}else if(asciiValue==41) {
				count--;
			}
		}
		return count==0;
	}
}
