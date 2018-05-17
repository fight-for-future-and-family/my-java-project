package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.HourClientDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.vo.HourClientDailyReportVO;
import com.hoolai.dao.GenericDao;


public interface HourClientDailyReportDao extends GenericDao<HourClientDailyReport, Long> {

	List<String> selectGameClients(HourClientDailyReportVO hourClientDailyReportVO);

	List<HourClientDailyReport> selectList(HourClientDailyReportVO clientDailyReportVO);

	void removeHourDaily(HourClientDailyReport hourClientDailyReport);

}
