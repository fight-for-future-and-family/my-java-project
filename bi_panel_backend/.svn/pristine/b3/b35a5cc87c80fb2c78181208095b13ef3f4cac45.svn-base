package com.hoolai.bi.report.vo;

import java.math.BigDecimal;

import com.hoolai.bi.report.model.entity.TotalPaymentLevel;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class TotalPaymentLevelVO  extends AbstractObjectVO<TotalPaymentLevel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Long gameId;
	private String indicators;
	private String beginDate;
	private String endDate;
	
	private Integer payUserSum;
	private BigDecimal payMentSum;

	public TotalPaymentLevelVO() {
		super();
		this.entity=new TotalPaymentLevel();
	}

	public TotalPaymentLevelVO(TotalPaymentLevel entity) {
		super(entity);
	}
	
	public void setDate(String beginDate, String endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
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

	public Integer getPayUserSum() {
		return payUserSum;
	}

	public void setPayUserSum(Integer payUserSum) {
		this.payUserSum = payUserSum;
	}

	public BigDecimal getPayMentSum() {
		return payMentSum;
	}

	public void setPayMentSum(BigDecimal payMentSum) {
		this.payMentSum = payMentSum;
	}
}
