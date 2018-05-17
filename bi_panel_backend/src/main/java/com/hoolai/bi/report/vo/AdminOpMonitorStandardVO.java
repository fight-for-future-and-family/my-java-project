package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.AdminOpMonitorStandard;
import com.hoolai.dao.pagination.AbstractObjectVO;
public class AdminOpMonitorStandardVO extends AbstractObjectVO<AdminOpMonitorStandard> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String searchValue;
	private String orderCol;
	private String orderType;
	private String orderValue;
	
	private String queryType = "limit";
	
	public AdminOpMonitorStandardVO() {
		super();
		this.entity=new AdminOpMonitorStandard();
	}

	public AdminOpMonitorStandardVO(AdminOpMonitorStandard entity) {
		super(entity);
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getOrderCol() {
		return orderCol;
	}

	public void setOrderCol(String orderCol) {
		this.orderCol = orderCol;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
}
