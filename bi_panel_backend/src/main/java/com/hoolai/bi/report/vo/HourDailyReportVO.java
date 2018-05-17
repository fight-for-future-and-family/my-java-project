package com.hoolai.bi.report.vo;

import com.hoolai.bi.report.model.entity.HourDailyReport;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class HourDailyReportVO extends AbstractObjectVO<HourDailyReport> {

	private static final long serialVersionUID = 8867277237698758218L;
	
	private String beginDate;
	private String endDate;
	private String beginHour;
	private String endHour;
	
	public HourDailyReportVO() {
		super();
		this.entity=new HourDailyReport();
	}

	public HourDailyReportVO(HourDailyReport entity) {
		super(entity);
	}

	public String getBeginHour() {
		return beginHour;
	}

	public void setBeginHour(String beginHour) {
		this.beginHour = beginHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
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
	
	public void setdate(String beginDate, String endDate, String beginHour,
			String endHour) {
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.beginHour = beginHour;
		this.endHour = endHour;
	}

}
