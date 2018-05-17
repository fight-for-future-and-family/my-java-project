package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.WeekReport;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class WeekReportVO extends AbstractObjectVO<WeekReport> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private Long gameId;
	
	private String indicators;
	
	private String beginDate;
	
	private String endDate;
	
	public WeekReportVO() {
		super();
		this.entity=new WeekReport();
	}

	public WeekReportVO(WeekReport entity) {
		super(entity);
	}
	
	public void setDate(String beginDate,String endDate){
		this.beginDate = beginDate;
		this.endDate = endDate;
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
}
