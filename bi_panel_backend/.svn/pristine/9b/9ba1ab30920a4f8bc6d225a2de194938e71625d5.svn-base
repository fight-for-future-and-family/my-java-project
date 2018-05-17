package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.SourceCpaDailyReportDao;
import com.hoolai.bi.report.model.entity.SourceCpaDailyReport;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.vo.SourceCpaDailyReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class SourceCpaDailyReportDaoImpl extends GenericDaoImpl<SourceCpaDailyReport, Long> implements SourceCpaDailyReportDao {
	
	@Override
	public String namespace() {
		return SourceCpaDailyReport.class.getName();
	}

	@Override
	public List<String> selectGameSources(SourceCpaDailyReportVO cpaSourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGameSources", cpaSourceDailyReportVO);
	}

	@Override
	public List<SourceCpaDailyReport> selectListBySource(SourceCpaDailyReportVO cpaSourceDailyReportVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectListBySource",cpaSourceDailyReportVO);
	}

	@Override
	public Long selectBySourceCount(SourceCpaDailyReportVO cpaSourceDailyReportVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectBySourceCount", cpaSourceDailyReportVO);
	}
	
	@Override
	public int saveCpaSourceDailysReport(List<SourceCpaDailyReport> resultList) {
		return this.sqlSessionTemplate.insert("saveCpaSourceDailysReport", resultList);
	}

	@Override
	public void delSourceDailyReportByDay(SourceCpaDailyReportVO sourceDailyReportVO) {
		this.sqlSessionTemplate.delete("delSourceDailyReportByDay", sourceDailyReportVO);
	}

	
}
