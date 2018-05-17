package com.hoolai.bi.report.vo;

import org.apache.commons.lang3.StringUtils;

import com.hoolai.bi.report.model.entity.SourceCpaDailyReport;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class SourceCpaDailyReportVO extends AbstractObjectVO<SourceCpaDailyReport> {

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
	
	// 表格中搜索框的值
	private String searchValue;
	private String orderValue;
	private String orderCol;
	private String orderType;
	
	private String queryType = "limit";
	private String multiSource = "single";
	
	//cps
	private String sourceName;
	private Integer pu;
	private Integer paymentCnt;
	private Double paymentAmount;

	public String getOrderCol() {
		return orderCol;
	}

	public void setOrderCol(String orderCol) {
		this.orderCol = orderCol;
	}
	
	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}

	public String getOrderType() {
		if(StringUtils.isEmpty(orderType)){
			return "desc";
		}
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public SourceCpaDailyReportVO() {
		super();
		this.entity=new SourceCpaDailyReport();
	}

	public SourceCpaDailyReportVO(SourceCpaDailyReport entity) {
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

	public Integer getDauSum() {
		return dauSum;
	}

	public void setDauSum(Integer dauSum) {
		this.dauSum = dauSum;
	}

	public Integer getPaymentSum() {
		return paymentSum;
	}

	public void setPaymentSum(Integer paymentSum) {
		this.paymentSum = paymentSum;
	}

	public Integer getDnuSum() {
		return dnuSum;
	}

	public void setDnuSum(Integer dnuSum) {
		this.dnuSum = dnuSum;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getMultiSource() {
		return multiSource;
	}

	public void setMultiSource(String multiSource) {
		this.multiSource = multiSource;
	}

	public Integer getPu() {
		return pu;
	}

	public void setPu(Integer pu) {
		this.pu = pu;
	}

	public Integer getPaymentCnt() {
		return paymentCnt;
	}

	public void setPaymentCnt(Integer paymentCnt) {
		this.paymentCnt = paymentCnt;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

}
