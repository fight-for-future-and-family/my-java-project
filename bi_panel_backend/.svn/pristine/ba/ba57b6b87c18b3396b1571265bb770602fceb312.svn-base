package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.hoolai.dao.GenericDao;


public interface ClientDailyReportDao extends GenericDao<ClientDailyReport, Long> {

	List<String> selectGameClients(ClientDailyReportVO clientDailyReportVO);

	List<ClientDailyReportVO> selectInstallForPieList(ClientDailyReportVO clientDailyReportVO);
	
	List<ClientDailyReportVO> selectInstallForDownLoad(ClientDailyReportVO clientDailyReportVO);

	Long selectInstallForPieListCount(ClientDailyReportVO clientDailyReportVO);

	List<ClientDailyReport> selectList(ClientDailyReportVO clientDailyReportVO);

	ClientDailyReport getClientMatch(ClientDailyReportVO clientDailyReportVO);

}
