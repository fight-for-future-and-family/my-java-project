package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.vo.HourSourceDailyReportVO;
import com.hoolai.dao.GenericDao;


public interface HourSourceDailyReportDao extends GenericDao<HourSourceDailyReport, Long> {

	List<String> selectGameSources(HourSourceDailyReportVO hourSourceDailyReportVO);

	List<HourSourceDailyReport> selectList(HourSourceDailyReportVO sourceDailyReportVO);

	void removeHourDaily(HourSourceDailyReport hourSourceDailyReport);
}
