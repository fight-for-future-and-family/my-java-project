package com.hoolai.bi.report.dao.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public abstract class AbstractHiveParameterizedRowMapper implements ParameterizedRowMapper<Map<String,Object>>{
	
	protected final List<String> columnsList=new ArrayList<String>();
	
	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum)throws SQLException {
		
		Map<String,Object> ret= new HashMap<String,Object>();
		
		if(columnsList.isEmpty()){
			ResultSetMetaData metaData=rs.getMetaData();
			int columnsCount=metaData.getColumnCount();
			for( int i = 0; i < columnsCount; i ++){
				String columnName = metaData.getColumnName(i+1);
				columnsList.add(columnName);
			}
		}
		
		if(!columnsList.isEmpty()){
			for(String column:columnsList){
				try {
					Object val=rs.getObject(column);
					if(val==null||"".equals(val.toString())){
						val="0";
					}else if("NaN".equals(val.toString())){
						val="0";
					}
					ret.put(column, val);
				} catch (Exception e) {
					throw new SQLException(e.getMessage()+" column:"+column,e);
				}
			}
		}
		
		return ret;
	}

	public List<String> getColumnsList() {
		return columnsList;
	}
	
	public String toStringBatchValues(List<Map<String,Object>> hiveDatas){
		StringBuilder sb=new StringBuilder("[");
		for(Map<String,?> data:hiveDatas){
			StringBuilder row=new StringBuilder();
			for(Entry<String, ?> entry:data.entrySet()){
				row.append(entry.getKey());
				row.append(":");
				row.append(entry.getValue());
				row.append(",");
			}
			sb.append(row.toString());
			sb.append("\r\n");
		}
		sb.append("]");
		return sb.toString();
	}
	
}
