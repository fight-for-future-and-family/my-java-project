package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.HourLtvSourceDailyReport;
import com.hoolai.bi.report.vo.HourLtvSourceDailyReportVO;
import com.jian.service.GenericService;

public interface HourLtvSourceDailyReportService extends GenericService<HourLtvSourceDailyReport, Long>{
	
	public List<String> selectGameSources(HourLtvSourceDailyReportVO hourSourceDailyReportVO);

	public long selectCount(HourLtvSourceDailyReportVO sourceDailyReportVO);

	public List<HourLtvSourceDailyReport> selectList(HourLtvSourceDailyReportVO sourceDailyReportVO);

	public void removeHourDaily(HourLtvSourceDailyReport hourSourceDailyReport);

	public void saveList(List<HourLtvSourceDailyReport> hourLtvDailyReportList);

}
