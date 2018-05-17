package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.HourLtvSourceDailyReport;
import com.hoolai.bi.report.vo.HourLtvSourceDailyReportVO;
import com.hoolai.dao.GenericDao;


public interface HourLtvSourceDailyReportDao extends GenericDao<HourLtvSourceDailyReport, Long> {

	List<String> selectGameSources(HourLtvSourceDailyReportVO hourSourceDailyReportVO);

	List<HourLtvSourceDailyReport> selectList(HourLtvSourceDailyReportVO sourceDailyReportVO);

	void removeHourDaily(HourLtvSourceDailyReport hourSourceDailyReport);

	void saveList(List<HourLtvSourceDailyReport> hourLtvDailyReportList);
}
