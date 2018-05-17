package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.SourceCpaDailyReport;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.vo.SourceCpaDailyReportVO;
import com.jian.service.GenericService;

public interface SourceCpaDailyReportService extends GenericService<SourceCpaDailyReport, Long>{
	
	public List<String> selectGameSources(SourceCpaDailyReportVO cpaSourceDailyReportVO);
	
	public List<SourceCpaDailyReport> selectListBySource(SourceCpaDailyReportVO cpaSourceDailyReportVO);
	
	public Long selectBySourceCount(SourceCpaDailyReportVO cpaSourceDailyReportVO);
	
	int saveCpaSourceDailysReport(List<SourceCpaDailyReport> resultList);

	void delSourceDailyReportByDay(SourceCpaDailyReportVO sourceDailyReportVO);
	
}
