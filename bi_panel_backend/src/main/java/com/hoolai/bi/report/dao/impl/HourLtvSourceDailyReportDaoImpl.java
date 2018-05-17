package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.HourLtvSourceDailyReportDao;
import com.hoolai.bi.report.model.entity.HourLtvSourceDailyReport;
import com.hoolai.bi.report.vo.HourLtvSourceDailyReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class HourLtvSourceDailyReportDaoImpl extends GenericDaoImpl<HourLtvSourceDailyReport, Long> implements HourLtvSourceDailyReportDao {

	@Override
	public String namespace() {
		return HourLtvSourceDailyReport.class.getName();
	}

	@Override
	public List<String> selectGameSources(HourLtvSourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGameSources", sourceDailyReportVO);
	}

	@Override
	public List<HourLtvSourceDailyReport> selectList(HourLtvSourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", sourceDailyReportVO);
	}

	@Override
	public void removeHourDaily(HourLtvSourceDailyReport hourSourceDailyReport) {
		this.sqlSessionTemplate.delete(this.namespace()+".removeLtvSourceHourDaily", hourSourceDailyReport);
	}

	@Override
	public void saveList(List<HourLtvSourceDailyReport> hourLtvDailyReportList) {
		this.sqlSessionTemplate.insert(this.namespace()+".saveList", hourLtvDailyReportList);
	}
}
