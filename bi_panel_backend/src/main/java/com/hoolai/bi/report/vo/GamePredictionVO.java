package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.GamePrediction;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class GamePredictionVO extends AbstractObjectVO<GamePrediction> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String isCurrMonth;
	
	private String currDate;
	
	private String monthBeginDate;
	private String monthendDate;
	
	private String avgEndDate;
	private String avgBeginDate;
	
	public GamePredictionVO() {
		super();
		this.entity=new GamePrediction();
	}

	public GamePredictionVO(GamePrediction entity) {
		super(entity);
	}

	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

	public void setAvgDate(String avgBeginDate, String avgEndDate) {
		this.avgBeginDate = avgBeginDate;
		this.avgEndDate = avgEndDate;
	}

	public void setSumDate(String monthBeginDate, String monthendDate) {
		this.monthendDate = monthendDate;
		this.monthBeginDate = monthBeginDate;
	}

	public String getIsCurrMonth() {
		return isCurrMonth;
	}

	public void setIsCurrMonth(String isCurrMonth) {
		this.isCurrMonth = isCurrMonth;
	}

	public String getMonthBeginDate() {
		return monthBeginDate;
	}

	public void setMonthBeginDate(String monthBeginDate) {
		this.monthBeginDate = monthBeginDate;
	}

	public String getMonthendDate() {
		return monthendDate;
	}

	public void setMonthendDate(String monthendDate) {
		this.monthendDate = monthendDate;
	}

	public String getAvgEndDate() {
		return avgEndDate;
	}

	public void setAvgEndDate(String avgEndDate) {
		this.avgEndDate = avgEndDate;
	}

	public String getAvgBeginDate() {
		return avgBeginDate;
	}

	public void setAvgBeginDate(String avgBeginDate) {
		this.avgBeginDate = avgBeginDate;
	}
	
}
