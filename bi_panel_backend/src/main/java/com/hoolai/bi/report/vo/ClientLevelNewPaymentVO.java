package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.ClientLevelNewPayment;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class ClientLevelNewPaymentVO extends AbstractObjectVO<ClientLevelNewPayment>{
	
	private static final long serialVersionUID = -4862956885373318170L;
	
	private String beginDate;
	private String endDate;
    private String searchValue;
	private String dateString;
	private String orderDate;
	private String orderType;
	private String groupType;
	private int dateTimes;
	private String concatStr;
	
	public ClientLevelNewPaymentVO() {
		super();
		this.entity=new ClientLevelNewPayment();
	}

	public ClientLevelNewPaymentVO(ClientLevelNewPayment entity) {
		super(entity);
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

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public int getDateTimes() {
		return dateTimes;
	}

	public void setDateTimes(int dateTimes) {
		this.dateTimes = dateTimes;
	}

	public String getConcatStr() {
		return concatStr;
	}

	public void setConcatStr(String concatStr) {
		this.concatStr = concatStr;
	}
	
	public void setDate(String beginDate, String endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
}
