package com.hoolai.manage.dao.impl;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.EtlEngineCleanupDao;
import com.hoolai.manage.model.EtlEngineCleanup;

/** 
 * <b>类说明：</b>
 * <blockquote>
 *   
 * </blockquote> 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年11月17日 下午7:20:40  
 */

@Repository
public class EtlEngineCleanupHiveDaoImpl extends GenericDaoImpl<EtlEngineCleanup, String>implements EtlEngineCleanupDao {
	
	@Autowired(required=false)
	@Qualifier("hiveJdbcTemplate")
	private JdbcTemplate hiveJdbcTemplate;
	
	@Autowired(required=false)
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getHiveJdbcTemplate() {
		return hiveJdbcTemplate;
	}

	public void setHiveJdbcTemplate(JdbcTemplate hiveJdbcTemplate) {
		this.hiveJdbcTemplate = hiveJdbcTemplate;
	}
	
	@Override
	public Map<String, List<EtlEngineCleanup>> executeMysql(final List<String> SQLs, final List<EtlEngineCleanup> etlEngineCleanups) {
		StatementCallback<Map<String, List<EtlEngineCleanup>>> sc = new StatementCallback<Map<String, List<EtlEngineCleanup>>>() {

			@Override
			public Map<String, List<EtlEngineCleanup>> doInStatement(Statement stmt)
					throws SQLException, DataAccessException {
				
				return executeHiveQL(stmt);
			}
			
			private Map<String, List<EtlEngineCleanup>> executeHiveQL(Statement stmt) {
				Map<String, List<EtlEngineCleanup>> resultMap = new HashMap<String, List<EtlEngineCleanup>>();
				resultMap.put("0", new ArrayList<EtlEngineCleanup>());
				resultMap.put("1", new ArrayList<EtlEngineCleanup>());
				
				for(EtlEngineCleanup engineCleanup : etlEngineCleanups){
					
					int i = 0;
					for(String sql : SQLs){
						sql = sql.replace("${snid}", engineCleanup.getSnid());
						sql = sql.replace("${gameid}", engineCleanup.getGameid());
						sql = sql.replace("${ds}", engineCleanup.getDs());
						sql = sql.trim();
						
						try {
							stmt.execute(sql);
							i++;
						} catch (Exception e) {
							try {
								stmt.execute(sql);
								i++;
							} catch (SQLException e1) {
								engineCleanup.setErrMessage("失败SQL：" + sql + "；异常信息：" + e.getMessage());
								break;
							}
							
						}
						
					}//hiveQLs end
					
					if(i == SQLs.size()){
						//所有sql读执行完了
						resultMap.get("0").add(new EtlEngineCleanup(engineCleanup));
					}else{
						resultMap.get("1").add(new EtlEngineCleanup(engineCleanup));
					}
					
				}
				
				return resultMap;
			}
		};
		Map<String, List<EtlEngineCleanup>> cleanupResultMap = this.jdbcTemplate.execute(sc);
		
		return cleanupResultMap;
	}
	
	
	@Override
	public Map<String, List<EtlEngineCleanup>> executeHiveQL(final List<String> hiveQLs, final List<EtlEngineCleanup> etlEngineCleanups) {
		
		StatementCallback<Map<String, List<EtlEngineCleanup>>> sc = new StatementCallback<Map<String, List<EtlEngineCleanup>>>() {

			@Override
			public Map<String, List<EtlEngineCleanup>> doInStatement(Statement stmt)
					throws SQLException, DataAccessException {
				
				return executeHiveQL(stmt);
			}
			
			private Map<String, List<EtlEngineCleanup>> executeHiveQL(Statement stmt) {
				Map<String, List<EtlEngineCleanup>> resultMap = new HashMap<String, List<EtlEngineCleanup>>();
				resultMap.put("0", new ArrayList<EtlEngineCleanup>());
				resultMap.put("1", new ArrayList<EtlEngineCleanup>());
				
				for(EtlEngineCleanup engineCleanup : etlEngineCleanups){
					
					int i = 0;
					for(String hiveQL : hiveQLs){
						hiveQL = hiveQL.replace("${snid}", engineCleanup.getSnid());
						hiveQL = hiveQL.replace("${gameid}", engineCleanup.getGameid());
						hiveQL = hiveQL.replace("${ds}", engineCleanup.getDs());
						hiveQL = hiveQL.trim();
						
						try {
							stmt.execute(hiveQL);
							i++;
						} catch (Exception e) {
							try {
								stmt.execute(hiveQL);
								i++;
							} catch (SQLException e1) {
								engineCleanup.setErrMessage("失败SQL：" + hiveQL + "；异常信息：" + e.getMessage());
								break;
							}
							
						}
						
					}//hiveQLs end
					
					if(i == hiveQLs.size()){
						//所有sql读执行完了
						resultMap.get("0").add(new EtlEngineCleanup(engineCleanup));
					}else{
						resultMap.get("1").add(new EtlEngineCleanup(engineCleanup));
					}
					
				}
				
				return resultMap;
			}
		};
		Map<String, List<EtlEngineCleanup>> cleanupResultMap = this.hiveJdbcTemplate.execute(sc);
		
		return cleanupResultMap;
		
		
		
		
	}
	
	
}
