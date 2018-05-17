package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.AdminOpMonitor;
import com.hoolai.dao.pagination.AbstractObjectVO;
public class AdminOpMonitorVO extends AbstractObjectVO<AdminOpMonitor> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String searchValue;
	
	public AdminOpMonitorVO() {
		super();
		this.entity=new AdminOpMonitor();
	}

	public AdminOpMonitorVO(AdminOpMonitor entity) {
		super(entity);
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	
}
