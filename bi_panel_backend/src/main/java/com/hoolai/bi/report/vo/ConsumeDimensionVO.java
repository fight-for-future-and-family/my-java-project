package com.hoolai.bi.report.vo;

import org.apache.commons.lang3.StringUtils;

import com.hoolai.bi.report.model.entity.ConsumeDimension;
import com.hoolai.dao.pagination.AbstractObjectVO;
public class ConsumeDimensionVO extends AbstractObjectVO<ConsumeDimension> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String beginDate;
	
	private String endDate;
	
	private String searchValue;
	
	private String orderCol;
	private String orderType;
	private String orderValue;
	
	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	public ConsumeDimensionVO() {
		super();
		this.entity=new ConsumeDimension();
	}

	public ConsumeDimensionVO(ConsumeDimension entity) {
		super(entity);
	}


	public String getOrderCol() {
		return orderCol;
	}

	public void setOrderCol(String orderCol) {
		this.orderCol = orderCol;
	}

	public String getOrderType() {
		if(StringUtils.isEmpty(orderType)){
			return "asc";
		}
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setDate(String beginDate, String endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
}
