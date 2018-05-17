package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.dao.GenericDao;


public interface SourceDailyReportDao extends GenericDao<SourceDailyReport, Long> {

	List<SourceDailyReportVO> selectForPieList(SourceDailyReportVO sourceDailyReportVO);

	Long selectForPieListCount(SourceDailyReportVO sourceDailyReportVO);

	List<SourceDailyReportVO> selectDauForPieList(SourceDailyReportVO sourceDailyReportVO);

	Long selectDauForPieListCount(SourceDailyReportVO sourceDailyReportVO);

	List<String> selectGameSources(SourceDailyReportVO sourceDailyReportVO);

	List<SourceDailyReportVO> selectPaymentForPieList(SourceDailyReportVO sourceDailyReportVO);

	Long selectPaymentForPieListCount(SourceDailyReportVO sourceDailyReportVO);

	List<SourceDailyReport> selectList(SourceDailyReportVO sourceDailyReportVO);

	List<SourceDailyReport> selectListBySource(SourceDailyReportVO sourceDailyReportVO);

	List<SourceDailyReport> selectListByMonth(SourceDailyReportVO sourceDailyReportVO);

	Long selectByMonthCount(SourceDailyReportVO sourceDailyReportVO);

	Long selectBySourceCount(SourceDailyReportVO sourceDailyReportVO);

	List<SourceDailyReport> getMatchJoin(SourceDailyReportVO sourceDailyReportVO);

	List<SourceDailyReport> selectListJoin(
			SourceDailyReportVO sourceDailyReportVO);

	SourceDailyReport getSumMatchSourceJoin(SourceDailyReportVO sourceDailyReportVO);

}
