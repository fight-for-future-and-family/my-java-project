package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class HourSourceDailyReportVO extends AbstractObjectVO<HourSourceDailyReport> {

	private static final long serialVersionUID = 1L;
	
	private String beginDate;
	private String endDate;
	private String beginHour;
	private String endHour;
	
	private String searchValue;
	private String orderCol;
	private String orderType;
	private String orderValue;

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

	public HourSourceDailyReportVO() {
		super();
		this.entity=new HourSourceDailyReport();
	}

	public HourSourceDailyReportVO(HourSourceDailyReport entity) {
		super(entity);
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
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

	public String getBeginHour() {
		return beginHour;
	}

	public void setBeginHour(String beginHour) {
		this.beginHour = beginHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public void setdate(String beginDate, String endDate, String beginHour,
			String endHour) {
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.beginHour = beginHour;
		this.endHour = endHour;
	}

}
