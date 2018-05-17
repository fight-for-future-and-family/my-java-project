package com.hoolai.bi.report.vo;

import java.util.Calendar;

import com.hoolai.bi.report.model.entity.ReportingEachTimeNC;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.dao.pagination.AbstractObjectVO;

public class ReportingEachTimeNCVO extends AbstractObjectVO<ReportingEachTimeNC>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String today;
	private String yesterday;
	private String lastMonthSToday;
	private String last7Day;
	
	public ReportingEachTimeNCVO() {
		super();
		this.entity=new ReportingEachTimeNC();
	}

	public ReportingEachTimeNCVO(ReportingEachTimeNC entity) {
		super(entity);
		initDate();
	}
	
	private void initDate(){
		Calendar calendar = Calendar.getInstance();
//		int month = calendar.get(Calendar.MONTH);
		
		this.today = DateUtils.getDateStr(calendar);
		this.yesterday = DateUtils.getYesterday();
		
//		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)- 1);
//		if(calendar.get(Calendar.MONTH) >= month){
//			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)- 1);
//			this.LastMonthSToday = DateUtils.getCurrMonthEndDate(calendar);
//		}else{
//			this.LastMonthSToday = DateUtils.getDateStr(calendar);
//		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-7);
		this.last7Day = DateUtils.getDateStr(calendar);
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-23);
		this.lastMonthSToday = DateUtils.getDateStr(calendar);
	}
	
	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getYesterday() {
		return yesterday;
	}

	public void setYesterday(String yesterday) {
		this.yesterday = yesterday;
	}

	public String getLastMonthSToday() {
		return lastMonthSToday;
	}

	public void setLastMonthSToday(String lastMonthSToday) {
		this.lastMonthSToday = lastMonthSToday;
	}

	public String getLast7Day() {
		return last7Day;
	}

	public void setLast7Day(String last7Day) {
		this.last7Day = last7Day;
	}
	
}
