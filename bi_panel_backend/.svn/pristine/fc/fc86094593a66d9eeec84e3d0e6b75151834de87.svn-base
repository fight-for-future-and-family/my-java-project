package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.RealtimeSourceReport;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class RealtimeSourceReportVO extends AbstractObjectVO<RealtimeSourceReport>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4862956885373318170L;
	
	private String beginDate;
	private String endDate;
	
	private String searchValue;
	private String orderCol;
	private String orderType;
	private String orderValue;

	public RealtimeSourceReportVO() {
		super();
		this.entity=new RealtimeSourceReport();
	}

	public RealtimeSourceReportVO(RealtimeSourceReport entity) {
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
	
	public void setDate(String beginDate,String endDate){
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
}
