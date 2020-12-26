package com.lbq.mybatis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lbq.mybatis.model.Tables;

public class ParseSql {

	private final static String SELECT = "SELECT";
	private final static String UPDATE = "UPDATE";
	private final static String DELETE = "DELETE";
	private final static String INSERT = "INSERT";
	Map<String, String> keywordHandler = new HashMap<>();
	public static Tables parse(String commandType, String sql) {
		List<String> keywords = new ArrayList<String>() {{
			add("delete ");
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
		System.out.println(sql);
		switch(commandType) {
		case SELECT: 
			return select(sql);
		case UPDATE: 
			return update(sql);
		case DELETE: 
			return delete(sql);
		case INSERT: 
			return insert(sql);
		default:
			return null;
		}
	}

	private static Tables insert(String sql) {
		sql = sql.toUpperCase();
		System.out.println(sql);
		int intoIndex = sql.indexOf("INTO");
		int valuesIndex = sql.indexOf("VALUES");
		String a = sql.substring(intoIndex, valuesIndex);
		System.out.println(a);
		int firstLeftBracketsIndex = a.indexOf("(");
		int lastRightBracketsIndex = a.lastIndexOf(")");
		String tableName = a.substring(4, firstLeftBracketsIndex);
		if(tableName.contains("`")) {
			tableName = tableName.replace("`", "");
		}
		tableName = tableName.trim();
		System.out.println(tableName);
		String c = a.substring(firstLeftBracketsIndex+1, lastRightBracketsIndex);
		System.out.println(c);
		String[] arr = c.split(",");
		System.out.println(arr);
		return null;
	}

	private static Tables delete(String sql) {
		sql = sql.toUpperCase();
		int fromIndex = sql.indexOf("FROM");
		int whereIndex = sql.indexOf("WHERE");
		String tableName = sql.substring(fromIndex+4, whereIndex);
		if(tableName.contains("`")) {
			tableName = tableName.replace("`", "");
		}
		tableName = tableName.trim();
		System.out.println(tableName);
		String b = sql.substring(whereIndex+5);
		String[] arr = b.split("AND|OR");
		System.out.println(arr);
		return null;
	}

	private static Tables update(String sql) {
		sql = sql.toUpperCase();
		return null;
	}

	private static Tables select(String sql) {
		
		List<String> keywords = new ArrayList<String>() {{
			add("SELECT ");
			add(" FROM ");
			add(" WHERE ");			
			add(" GROUP BY ");
			add(" HAVING ");
			add(" ORDER BY ");
			add(" LIMIT ");
		}};
		int startIndex = 0;
		for(String keyword : keywords) {
			int keywordIndex = findKeywordIndexOf(sql, keyword, startIndex);		
			if(keywordIndex == -1) {
				continue;			
			}
			startIndex = keywordIndex;
			int nextIndex = findNextKeywordIndex(sql, keyword, startIndex, keywords);
			String a = sql.substring(startIndex, nextIndex);
			System.out.println(keyword.trim() +":"+a);
		}
		return null;
	}
	
	private static int findNextKeywordIndex(String sql, String keyword, int startIndex, List<String> keywords) {
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

	private static int findKeywordIndexOf(String sql, String keyword, int startIndex) {
		int index = sql.indexOf(keyword, startIndex);
		while(index != -1 && !isMatch(sql, startIndex, index)) {
			index = sql.indexOf(keyword, index + 1);
		};
		return index;
	}
	private static int pairedBrackets(String sql, int fromIndex) {		
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

	private static boolean isMatch(String sql, int fromIndex, int toIndex) {
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
	public static void main(String[] args) {
		String sql = "insert into `sys_user`(id, user_name, user_password, user_email, user_info, head_img, create_time)\r\n" + 
				"		values (?, ?, ?, ?, ?, ?, ?)";
		insert(sql);
		sql = "delete from sys_user where id=?";
		delete(sql);
		sql = "";
		update(sql);
		sql = "";
		select(sql);
	}
}
