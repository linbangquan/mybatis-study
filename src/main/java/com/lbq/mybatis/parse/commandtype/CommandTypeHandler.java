package com.lbq.mybatis.parse.commandtype;

import java.util.Map;

public interface CommandTypeHandler {

	Map<String, String> parse(String sql);
}
