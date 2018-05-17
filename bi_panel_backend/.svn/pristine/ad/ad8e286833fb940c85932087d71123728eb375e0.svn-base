package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.SourceDailyReportDao;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class SourceDailyReportDaoImpl extends GenericDaoImpl<SourceDailyReport, Long> implements SourceDailyReportDao {

	@Override
	public String namespace() {
		return SourceDailyReport.class.getName();
	}

	@Override
	public List<SourceDailyReportVO> selectForPieList(SourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectInstallForPieList", sourceDailyReportVO);
	}
	
	@Override
	public Long selectForPieListCount(SourceDailyReportVO sourceDailyReportVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectInstallForPieListCount", sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReportVO> selectDauForPieList(SourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectDauForPieList", sourceDailyReportVO);
	}

	@Override
	public Long selectDauForPieListCount(SourceDailyReportVO sourceDailyReportVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectDauForPieListCount", sourceDailyReportVO);
	}

	@Override
	public List<String> selectGameSources(SourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGameSources", sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReportVO> selectPaymentForPieList(SourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectPaymentForPieList", sourceDailyReportVO);
	}

	@Override
	public Long selectPaymentForPieListCount(SourceDailyReportVO sourceDailyReportVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectPaymentForPieListCount", sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReport> selectList(SourceDailyReportVO sourceDailyReportVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectList",sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReport> selectListBySource(SourceDailyReportVO sourceDailyReportVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectListBySource",sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReport> selectListByMonth(SourceDailyReportVO sourceDailyReportVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectListByMonth",sourceDailyReportVO);
	}

	@Override
	public Long selectByMonthCount(SourceDailyReportVO sourceDailyReportVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectByMonthCount", sourceDailyReportVO);
	}

	@Override
	public Long selectBySourceCount(SourceDailyReportVO sourceDailyReportVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectBySourceCount", sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReport> getMatchJoin(
			SourceDailyReportVO sourceDailyReportVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectMatchJoin",sourceDailyReportVO);
	}

	@Override
	public List<SourceDailyReport> selectListJoin(
			SourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectListJoin", sourceDailyReportVO);
	}

	@Override
	public SourceDailyReport getSumMatchSourceJoin(SourceDailyReportVO sourceDailyReportVO) {
		return this.sqlSessionTemplate.selectOne("selectSumSourceMatchJoin", sourceDailyReportVO);
	}
}
