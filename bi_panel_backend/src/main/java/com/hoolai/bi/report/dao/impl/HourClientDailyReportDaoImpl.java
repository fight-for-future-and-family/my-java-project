package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.HourClientDailyReportDao;
import com.hoolai.bi.report.model.entity.HourClientDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.vo.HourClientDailyReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class HourClientDailyReportDaoImpl extends GenericDaoImpl<HourClientDailyReport, Long> implements HourClientDailyReportDao {

	@Override
	public String namespace() {
		return HourClientDailyReport.class.getName();
	}
	
	@Override
	public List<String> selectGameClients(HourClientDailyReportVO clientDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGameClients", clientDailyReportVO);
	}

	@Override
	public List<HourClientDailyReport> selectList(HourClientDailyReportVO clientDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", clientDailyReportVO);
	}
	
	@Override
	public void removeHourDaily(HourClientDailyReport hourClientDailyReport) {
		this.sqlSessionTemplate.delete("removeClientHourDaily", hourClientDailyReport);
	}

}
