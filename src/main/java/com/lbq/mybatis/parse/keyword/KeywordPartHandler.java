package com.lbq.mybatis.parse.keyword;

import com.lbq.mybatis.model.Tables;

public interface KeywordPartHandler {

	public Tables parse(String sql);
}
