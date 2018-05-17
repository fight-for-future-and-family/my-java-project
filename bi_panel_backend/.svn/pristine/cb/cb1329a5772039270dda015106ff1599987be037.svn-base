package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.CpaSourceDimension;
import com.hoolai.bi.report.model.entity.UserCpaCpsSource;
import com.hoolai.dao.pagination.AbstractObjectVO;
public class CpaSourceDimensionVO extends AbstractObjectVO<CpaSourceDimension> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String searchValue;
	private String orderCol;
	private String orderType;
	
	private UserCpaCpsSource userSource;
	private int isHad=0;
	
	public UserCpaCpsSource getUserSource() {
		return userSource;
	}

	public void setUserSource(UserCpaCpsSource userSource) {
		this.userSource = userSource;
	}

	public CpaSourceDimensionVO() {
		super();
		this.entity=new CpaSourceDimension();
	}

	public CpaSourceDimensionVO(CpaSourceDimension entity) {
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

	public int getIsHad() {
		return isHad;
	}

	public void setIsHad(int isHad) {
		this.isHad = isHad;
	}
}
