package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.equip.EquipRetentionLtv;
import com.hoolai.dao.pagination.AbstractObjectVO;
public class EquipRetentionLtvVO extends AbstractObjectVO<EquipRetentionLtv> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String searchValue;
	private String orderCol;
	private String orderType;
	private String orderValue;
	
	private String beginDate;
	private String endDate;
	
	private String queryType = "limit";
	
	private Double avgRetentionRate;
	
	public EquipRetentionLtvVO() {
		super();
		this.entity=new EquipRetentionLtv();
	}

	public EquipRetentionLtvVO(EquipRetentionLtv entity) {
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
	
	public Double getAvgRetentionRate() {
		return avgRetentionRate;
	}

	public void setAvgRetentionRate(Double avgRetentionRate) {
		this.avgRetentionRate = avgRetentionRate;
	}

	public void setDate(String beginDate,String endDate){
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
}
