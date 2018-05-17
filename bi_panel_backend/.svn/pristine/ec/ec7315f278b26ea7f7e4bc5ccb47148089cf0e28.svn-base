package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.ClientEconomyNewReportDao;
import com.hoolai.bi.report.model.entity.ClientEconomyNewReport;
import com.hoolai.bi.report.model.entity.EconomyItemReport;
import com.hoolai.bi.report.vo.ClientEconomyNewReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class ClientEconomyNewReportDaoImpl extends GenericDaoImpl<ClientEconomyNewReport, Long> implements ClientEconomyNewReportDao {

	@Override
	public String namespace() {
		return ClientEconomyNewReport.class.getName();
	}

	@Override
	public List<Integer> selectGameClients(ClientEconomyNewReportVO clientEconomyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGameClients", clientEconomyReportVO);
	}

	@Override
	public List<ClientEconomyNewReport> selectItemList4Down(ClientEconomyNewReportVO clientEconomyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectItemList4Down", clientEconomyReportVO);
	}

	@Override
	public Long selectItemCount(ClientEconomyNewReportVO clientEconomyReportVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectItemCount", clientEconomyReportVO);
	}

	@Override
	public List<EconomyItemReport> selectItemList(ClientEconomyNewReportVO clientEconomyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectItemList", clientEconomyReportVO);
	}
	
}
