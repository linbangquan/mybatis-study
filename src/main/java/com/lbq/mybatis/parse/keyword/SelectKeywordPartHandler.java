package com.lbq.mybatis.parse.keyword;

import java.util.ArrayList;
import java.util.List;

import com.lbq.mybatis.model.Tables;

public class SelectKeywordPartHandler implements KeywordPartHandler {

	@Override
	public Tables parse(String sql) {
		System.out.println(SelectKeywordPartHandler.class.getName()+":"+sql);
		if(sql.startsWith("SELECT ")) {
			sql = sql.substring(7);
		}
		sql = sql.trim();
		System.out.println(SelectKeywordPartHandler.class.getName()+":"+sql);
		List<String> columnlist = new ArrayList<>();
		int startIndex = 0;
		while(sql.length() > 0) {
			int index = findKeywordIndexOf(sql, ",", startIndex);
			if(index == -1) {
				columnlist.add(sql);
				break;
			}else {
				String column = sql.substring(startIndex, index);
				columnlist.add(column);
				sql = sql.substring(index+1);
			}
		}
		for(String column : columnlist) {
			System.out.println(SelectKeywordPartHandler.class.getName()+":"+column);
			String columnName = null;
			String columnAlias = null;
			int index = findKeywordIndexOf(column, " AS ", 0);
			if(index == -1) {
				columnName = column;
			}else {
				columnName = column.substring(0, index);
				columnAlias = column.substring(index + 4);
			}
			System.out.println(SelectKeywordPartHandler.class.getName()+":"+columnName + "-" + columnAlias);
		}
		return null;
	}

	public int findKeywordIndexOf(String sql, String keyword, int startIndex) {
		int index = sql.indexOf(keyword, startIndex);
		while(index != -1 && !isMatch(sql, startIndex, index)) {
			index = sql.indexOf(keyword, index + 1);
		};
		return index;
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
