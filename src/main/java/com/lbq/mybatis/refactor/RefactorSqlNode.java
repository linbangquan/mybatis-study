package com.lbq.mybatis.refactor;

import org.apache.ibatis.scripting.xmltags.SqlNode;

public interface RefactorSqlNode {
	SqlNode refactor(SqlNode sqlNode) throws Exception;
}
