package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.HourSourceDailyReportDao;
import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.vo.HourSourceDailyReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class HourSourceDailyReportDaoImpl extends GenericDaoImpl<HourSourceDailyReport, Long> implements HourSourceDailyReportDao {

	@Override
	public String namespace() {
		return HourSourceDailyReport.class.getName();
	}

	@Override
	public List<String> selectGameSources(HourSourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGameSources", sourceDailyReportVO);
	}

	@Override
	public List<HourSourceDailyReport> selectList(HourSourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", sourceDailyReportVO);
	}

	@Override
	public void removeHourDaily(HourSourceDailyReport hourSourceDailyReport) {
		this.sqlSessionTemplate.delete("removeSourceHourDaily", hourSourceDailyReport);
	}
}
