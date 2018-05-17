package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.SourceCpaDailyReport;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.vo.SourceCpaDailyReportVO;
import com.hoolai.dao.GenericDao;


public interface SourceCpaDailyReportDao extends GenericDao<SourceCpaDailyReport, Long> {
	
	List<String> selectGameSources(SourceCpaDailyReportVO cpaSourceDailyReportVO);
	
	List<SourceCpaDailyReport> selectListBySource(SourceCpaDailyReportVO cpaSourceDailyReportVO);

	Long selectBySourceCount(SourceCpaDailyReportVO cpaSourceDailyReportVO);
	
	int saveCpaSourceDailysReport(List<SourceCpaDailyReport> resultList);

	void delSourceDailyReportByDay(SourceCpaDailyReportVO sourceDailyReportVO);

}
