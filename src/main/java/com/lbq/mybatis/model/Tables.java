package com.lbq.mybatis.model;

import java.util.List;
import java.util.Map;

public class Tables {

	private List<String> tableNames; 
	
	private List<String> tableAliases;
	
	private Map<String,String> tableAliasAndOriginalNameMap;
	
	private List<String> columnNames;
	
	private List<String> columnAliases;
	
	private Map<String,String> columnAliasAndOriginalNameMap;

	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

	public List<String> getTableAliases() {
		return tableAliases;
	}

	public void setTableAliases(List<String> tableAliases) {
		this.tableAliases = tableAliases;
	}

	public Map<String, String> getTableAliasAndOriginalNameMap() {
		return tableAliasAndOriginalNameMap;
	}

	public void setTableAliasAndOriginalNameMap(Map<String, String> tableAliasAndOriginalNameMap) {
		this.tableAliasAndOriginalNameMap = tableAliasAndOriginalNameMap;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public List<String> getColumnAliases() {
		return columnAliases;
	}

	public void setColumnAliases(List<String> columnAliases) {
		this.columnAliases = columnAliases;
	}

	public Map<String, String> getColumnAliasAndOriginalNameMap() {
		return columnAliasAndOriginalNameMap;
	}

	public void setColumnAliasAndOriginalNameMap(Map<String, String> columnAliasAndOriginalNameMap) {
		this.columnAliasAndOriginalNameMap = columnAliasAndOriginalNameMap;
	}
}
