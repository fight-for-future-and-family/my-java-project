package com.hoolai.bi.report.dao.impl;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.HourDailyReportDao;
import com.hoolai.bi.report.model.entity.HourDailyReport;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class HourDailyReportDaoImpl extends GenericDaoImpl<HourDailyReport, Long> implements HourDailyReportDao {

	@Override
	public String namespace() {
		return HourDailyReport.class.getName();
	}

	@Override
	public void removeHourDaily(HourDailyReport hourDailyReport) {
		this.sqlSessionTemplate.delete("removeHourDaily", hourDailyReport);
	}
	
}
