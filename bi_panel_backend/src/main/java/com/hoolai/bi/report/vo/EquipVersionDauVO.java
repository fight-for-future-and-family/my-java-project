package com.hoolai.bi.report.vo;

import java.util.List;

import com.hoolai.bi.report.model.entity.equip.EquipVersionDau;
import com.hoolai.dao.pagination.AbstractObjectVO;
public class EquipVersionDauVO extends AbstractObjectVO<EquipVersionDau> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String searchValue;
	private String orderCol;
	private String orderType;
	private String orderValue;
	
	private String versionStr;
	
	private String beginDate;
	private String endDate;
	
	private String queryType = "limit";
	
	private List<String> versionNames;
	
	public EquipVersionDauVO() {
		super();
		this.entity=new EquipVersionDau();
	}

	public EquipVersionDauVO(EquipVersionDau entity) {
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
	
	public String getVersionStr() {
		return versionStr;
	}

	public void setVersionStr(String versionStr) {
		this.versionStr = versionStr;
	}

	public List<String> getVersionNames() {
		return versionNames;
	}

	public void setVersionNames(List<String> versionNames) {
		this.versionNames = versionNames;
	}

	public void setDate(String beginDate,String endDate){
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
}
