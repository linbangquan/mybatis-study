package com.lbq.mybatis.parse.commandtype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lbq.mybatis.model.Tables;
import com.lbq.mybatis.parse.keyword.KeywordPartFactory;
import com.lbq.mybatis.parse.keyword.KeywordPartHandler;

public class UpdateCommandTypeHandler extends BaseCommandTypeHandler {

	private List<String> keywords = new ArrayList<String>() {{
		add("UPDATE ");
		add(" SET ");
		add(" WHERE ");			
		add(" GROUP BY ");
		add(" HAVING ");
		add(" ORDER BY ");
		add(" LIMIT ");
	}};
	@Override
	public Map<String, String> myParse(String sql) {
		List<Tables> tablesList = new ArrayList<>();
		Map<String, String> map = keywordsParse(sql, keywords);
		for(Entry<String, String> entry : map.entrySet()) {
			KeywordPartHandler keywordPartHandler = KeywordPartFactory.getHandler(entry.getKey());
			Tables tables = keywordPartHandler.parse(entry.getValue());
			tablesList.add(tables);
		}
		return map;
	}

}
