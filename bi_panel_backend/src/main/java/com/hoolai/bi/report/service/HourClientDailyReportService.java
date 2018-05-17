package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.HourClientDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.vo.HourClientDailyReportVO;
import com.jian.service.GenericService;

public interface HourClientDailyReportService extends GenericService<HourClientDailyReport, Long>{

	List<String> selectGameClients(HourClientDailyReportVO hourClientDailyReportVO);

	public long selectCount(HourClientDailyReportVO clientDailyReportVO);
	
	public List<HourClientDailyReport> selectList(HourClientDailyReportVO clientDailyReportVO);

	 public void removeHourDaily(HourClientDailyReport hourClientDailyReport);
	
}
