package com.hoolai.bi.report.dao;

import com.hoolai.bi.report.model.entity.HourDailyReport;
import com.hoolai.dao.GenericDao;


public interface HourDailyReportDao extends GenericDao<HourDailyReport, Long> {

	void removeHourDaily(HourDailyReport hourDailyReport);
    
}
