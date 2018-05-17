package com.hoolai.manage.vo;

/** 
 * <b>类说明：</b>
 * <blockquote>
 *   
 * </blockquote> 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年11月17日 下午6:47:34  
 */

public class EtlEngineCleanupVO {

	private String[] gameids;

	private String beginDate;
	
	private String endDate;
	
	private String type;

	public String[] getGameids() {
		return gameids;
	}

	public void setGameids(String[] gameids) {
		this.gameids = gameids;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public enum DataType{
		ETL("cleanupsql/cleanupsql_etl.ftl"),
		FACTS("cleanupsql/cleanupsql_facts.ftl"),
		DEFAULT("cleanupsql/cleanupsql_default.ftl"),
		REPORT("cleanupsql/cleanupsql_report.ftl");
		private String value;
		
		private DataType(String value){
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
		
	}
	
}
