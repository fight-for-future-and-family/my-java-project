package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.EconomyNewReportDao;
import com.hoolai.bi.report.model.entity.EconomyItemReport;
import com.hoolai.bi.report.model.entity.EconomyNewReport;
import com.hoolai.bi.report.vo.EconomyNewReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class EconomyNewReportDaoImpl extends GenericDaoImpl<EconomyNewReport, Long> implements EconomyNewReportDao {

	@Override
	public String namespace() {
		return EconomyNewReport.class.getName();
	}

	@Override
	public List<EconomyNewReport> selectItemList4Down(EconomyNewReportVO economyNewReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectItemList4Down", economyNewReportVO);
	}

	@Override
	public Long selectItemCount(EconomyNewReportVO economyNewReportVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectItemCount", economyNewReportVO);
	}

	@Override
	public List<EconomyItemReport> selectItemList(EconomyNewReportVO economyNewReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectItemList", economyNewReportVO);
	}
	
}
