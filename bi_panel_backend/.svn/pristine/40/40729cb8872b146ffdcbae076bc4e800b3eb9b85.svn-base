package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.ClientDailyReportDao;
import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class ClientDailyReportDaoImpl extends GenericDaoImpl<ClientDailyReport, Long> implements ClientDailyReportDao {

	@Override
	public String namespace() {
		return ClientDailyReport.class.getName();
	}
	
	@Override
	public List<String> selectGameClients(ClientDailyReportVO clientDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGameClients", clientDailyReportVO);
	}

	@Override
	public List<ClientDailyReportVO> selectInstallForPieList(ClientDailyReportVO clientDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectInstallForPieList", clientDailyReportVO);
	}

	@Override
	public Long selectInstallForPieListCount(ClientDailyReportVO clientDailyReportVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".selectInstallForPieListCount", clientDailyReportVO);
	}

	@Override
	public List<ClientDailyReportVO> selectInstallForDownLoad(ClientDailyReportVO clientDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectInstallForDownLoad", clientDailyReportVO);
	}

	@Override
	public List<ClientDailyReport> selectList(ClientDailyReportVO clientDailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", clientDailyReportVO);
	}

	@Override
	public ClientDailyReport getClientMatch(ClientDailyReportVO clientDailyReportVO) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("selectClientMatchList", clientDailyReportVO);
	}
}
