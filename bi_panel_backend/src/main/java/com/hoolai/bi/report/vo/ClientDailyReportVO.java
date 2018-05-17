package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class ClientDailyReportVO extends AbstractObjectVO<ClientDailyReport> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Long gameId;
	private String indicators;
	private String beginDate;
	private String endDate;
	
	/** 新安装相关数值  */
	private Integer dnuSum;
	private Integer dauSum;
	private Integer paymentSum;
	private Integer installSum;
	
	// 表格中搜索框的值
   private String searchValue;
   private String orderValue;
   private String orderCol;
   private String orderType;

	public ClientDailyReportVO() {
		super();
		this.entity=new ClientDailyReport();
	}

	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
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

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public ClientDailyReportVO(ClientDailyReport entity) {
		super(entity);
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public String getIndicators() {
		return indicators;
	}

	public void setIndicators(String indicators) {
		this.indicators = indicators;
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

	public Integer getDnuSum() {
		return dnuSum;
	}

	public void setDnuSum(Integer dnuSum) {
		this.dnuSum = dnuSum;
	}

	public Integer getPaymentSum() {
		return paymentSum;
	}

	public void setPaymentSum(Integer paymentSum) {
		this.paymentSum = paymentSum;
	}

	public Integer getDauSum() {
		return dauSum;
	}

	public void setDauSum(Integer dauSum) {
		this.dauSum = dauSum;
	}
	
	public void setDate(String beginDate, String endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public Integer getInstallSum() {
		return installSum;
	}

	public void setInstallSum(Integer installSum) {
		this.installSum = installSum;
	}

//	public Long getAllInstall() {
//		return allInstall;
//	}
//
//	public void setAllInstall(Long allInstall) {
//		this.allInstall = allInstall;
//	}

}
