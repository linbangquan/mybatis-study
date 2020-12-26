package com.lbq.mybatis.parse.keyword;

import java.util.HashMap;
import java.util.Map;

public class KeywordPartFactory {

	public final static String FROM = "FROM";
	public final static String GROUP_BY = "GROUP BY";
	public final static String HAVING = "HAVING";
	public final static String INSERT_INTO = "INSERT INTO";
	public final static String LIMIT = "LIMIT";
	public final static String ON = "ON";
	public final static String ORDER_BY = "ORDER BY";
	public final static String SELECT = "SELECT";
	public final static String SET = "SET";
	public final static String UPDATE = "UPDATE";
	public final static String WHERE = "WHERE";
	
	private static Map<String, KeywordPartHandler> map = new HashMap<>();
	
	public static KeywordPartHandler getHandler(String keyword) {
		switch(keyword) {
			case FROM : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new FromKeywordPartHandler());
				}
				return map.get(keyword);
			case GROUP_BY : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new GroupByKeywordPartHandler());
				}
				return map.get(keyword);
			case HAVING : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new HavingKeywordPartHandler());
				}
				return map.get(keyword);
			case INSERT_INTO : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new InsertIntoKeywordPartHandler());
				}
				return map.get(keyword);
				
			case LIMIT : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new LimitKeywordPartHandler());
				}
				return map.get(keyword);
			case ON : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new OnKeywordPartHandler());
				}
				return map.get(keyword);
			case ORDER_BY : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new OrderByKeywordPartHandler());
				}
				return map.get(keyword);
			case SELECT : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new SelectKeywordPartHandler());
				}
				return map.get(keyword);
			case SET : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new SetKeywordPartHandler());
				}
				return map.get(keyword);
			case UPDATE : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new UpdateKeywordPartHandler());
				}
				return map.get(keyword);
			case WHERE : 
				if(!map.containsKey(keyword)) {
					map.put(keyword, new WhereKeywordPartHandler());
				}
				return map.get(keyword);
			default :
				return null;
		}
		
	}
}
