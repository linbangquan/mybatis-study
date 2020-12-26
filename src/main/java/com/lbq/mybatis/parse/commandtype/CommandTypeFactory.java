package com.lbq.mybatis.parse.commandtype;

import java.util.HashMap;
import java.util.Map;

public class CommandTypeFactory {

	public final static String SELECT = "SELECT";
	public final static String INSERT = "INSERT";
	public final static String UPDATE = "UPDATE";
	public final static String DELETE = "DELETE";
	
	private static Map<String, CommandTypeHandler> map = new HashMap<>();
	
	public static CommandTypeHandler getHandler(String commamdType) {
		
		switch (commamdType) {
			case SELECT : 
				if(!map.containsKey(commamdType)) {
					map.put(commamdType, new SelectCommandTypeHandler());
				}
				return map.get(commamdType);
			case INSERT : 
				if(!map.containsKey(commamdType)) {
					map.put(commamdType, new InsertCommandTypeHandler());
				}
				return map.get(commamdType);
			case UPDATE : 
				if(!map.containsKey(commamdType)) {
					map.put(commamdType, new UpdateCommandTypeHandler());
				}
				return map.get(commamdType);
			case DELETE : 
				if(!map.containsKey(commamdType)) {
					map.put(commamdType, new DeleteCommandTypeHandler());
				}
				return map.get(commamdType);
			default : return null;
		}
		
	}
}
