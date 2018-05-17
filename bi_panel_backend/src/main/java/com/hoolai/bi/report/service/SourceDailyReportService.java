package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.jian.service.GenericService;

public interface SourceDailyReportService extends GenericService<SourceDailyReport, Long>{
	
	public List<SourceDailyReportVO> selectInstallForPieList(SourceDailyReportVO sourceDailyReportVO);
	
	public Long selectInstallForPieListCount(SourceDailyReportVO sourceDailyReportVO);
	
	public List<SourceDailyReportVO> selectDauForPieList(SourceDailyReportVO sourceDailyReportVO);
	
	public Long selectDauForPieListCount(SourceDailyReportVO sourceDailyReportVO);
	
	public List<SourceDailyReportVO> selectPaymentForPieList(SourceDailyReportVO sourceDailyReportVO);
	
	public Long selectPaymentForPieListCount(SourceDailyReportVO sourceDailyReportVO);

	public List<String> selectGameSources(SourceDailyReportVO sourceDailyReportVO);
	
	public long selectCount(SourceDailyReportVO sourceDailyReportVO);

	public List<SourceDailyReport> selectList(SourceDailyReportVO sourceDailyReportVO);
	
	// 以下方法供cpa&cps使用
	public List<SourceDailyReport> selectListBySource(SourceDailyReportVO sourceDailyReportVO);

	public List<SourceDailyReport> selectListByMonth(SourceDailyReportVO sourceDailyReportVO);

	public Long selectByMonthCount(SourceDailyReportVO sourceDailyReportVO);

	public Long selectBySourceCount(SourceDailyReportVO sourceDailyReportVO);

	public List<SourceDailyReport> getMatchJoin(
			SourceDailyReportVO sourceDailyReportVO);

	public List<SourceDailyReport> selectListJoin(
			SourceDailyReportVO sourceDailyReportVO);

	public SourceDailyReport getSumMatchSourceJoin(SourceDailyReportVO sourceDailyReportVO);
}
