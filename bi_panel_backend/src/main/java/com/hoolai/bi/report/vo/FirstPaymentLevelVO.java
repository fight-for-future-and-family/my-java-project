package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.FirstPaymentLevel;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class FirstPaymentLevelVO  extends AbstractObjectVO<FirstPaymentLevel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Long gameId;
	private String indicators;
	private String beginDate;
	private String endDate;
	
	private Integer payUserSum;
	private Integer payMentSum;

	public FirstPaymentLevelVO() {
		super();
		this.entity=new FirstPaymentLevel();
	}

	public FirstPaymentLevelVO(FirstPaymentLevel entity) {
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

	public Integer getPayMentSum() {
		return payMentSum;
	}

	public void setPayMentSum(Integer payMentSum) {
		this.payMentSum = payMentSum;
	}
}
