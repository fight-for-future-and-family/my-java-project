package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.HourDailyReportDao;
import com.hoolai.bi.report.model.entity.HourDailyReport;
import com.hoolai.bi.report.service.HourDailyReportService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class HourDailyReportServiceImpl extends GenericServiceImpl<HourDailyReport, Long> implements HourDailyReportService {

	@Autowired
	private HourDailyReportDao entityDao;
	
	@Override
    public GenericDao<HourDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public void removeHourDaily(HourDailyReport hourDailyReport) {
		entityDao.removeHourDaily(hourDailyReport);
	}

}
