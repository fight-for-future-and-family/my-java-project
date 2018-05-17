package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.MonthReport;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class MonthReportVO extends AbstractObjectVO<MonthReport> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private Long gameId;
	
	private String indicators;
	
	private String beginDate;
	
	private String endDate;
	
	public MonthReportVO() {
		super();
		this.entity=new MonthReport();
	}

	public MonthReportVO(MonthReport entity) {
		super(entity);
	}

	public String getIndicators() {
		return indicators;
	}

	public void setIndicators(String indicators) {
		this.indicators = indicators;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
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

	public void setDate(String beginDate, String endDate) {
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
}
