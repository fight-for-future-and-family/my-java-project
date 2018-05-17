package com.hoolai.panel.web.processor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hoolai.bi.report.dao.impl.CustomReportCacheDaoImpl;

@Component
public class CustomTaskJob {

	@Autowired
	@Qualifier("hiveJdbcTemplate")
	private JdbcTemplate hiveJdbcTemplate;
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String,Object>> queryDatasFromHiveDB(String sql, Integer isPresto){
		
		List<Map<String,Object>> hiveDatas = new ArrayList<Map<String,Object>>();
		if(0 == isPresto){
			JdbcTemplate etlEngineJdbcTemplate = new ETLEngineJdbcTemplate(this.hiveJdbcTemplate.getDataSource());
			
			List<String> sqls = SQLScript.parse(sql);
			for (String tmp : sqls) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String sqltmp = new String(tmp);
				if(!sqltmp.toLowerCase().startsWith("select") ){
					etlEngineJdbcTemplate.execute(tmp.replace(";", ""));		
				}else{
					hiveDatas = etlEngineJdbcTemplate.queryForList(tmp.replace(";", ""));
				}
			}
		}else{
			PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
			try {
				ResultSet rs = prestoJdbcTemplate.exec(sql);
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				
				while (rs.next()) {
					Map rowData = new HashMap();
					for (int i = 1; i <= columnCount; i++) {
						rowData.put(md.getColumnName(i), rs.getObject(i));
					}
					hiveDatas.add(rowData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return hiveDatas;
	}
	
	public int deleteDatasFromMySql(Long taskId){
        return jdbcTemplate.update(CustomReportCacheDaoImpl.getDeleteSql(), new Object[]{taskId});
	}
	
	public void insertDatasToMySql(final Long taskId,final int rowId,final Map<String,Object> data){
		String sql = CustomReportCacheDaoImpl.getInsertSql();
		
		Set<String> keySet = data.keySet();
		final List<String> keyList = new ArrayList<String>(keySet);  
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, taskId);   
				ps.setInt(2, rowId);   
                ps.setString(3, keyList.get(i));   
                ps.setString(4, String.valueOf(data.get(keyList.get(i))));   
                ps.setDate(5, new Date(Calendar.getInstance().getTimeInMillis()));   
			}
			
			@Override
			public int getBatchSize() {
				return keyList.size();
			}
		});
	}
}
