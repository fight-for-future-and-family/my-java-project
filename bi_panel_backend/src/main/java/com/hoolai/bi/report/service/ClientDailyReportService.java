package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.jian.service.GenericService;

public interface ClientDailyReportService extends GenericService<ClientDailyReport, Long>{

	List<String> selectGameClients(ClientDailyReportVO clientDailyReportVO);

	List<ClientDailyReportVO> selectInstallForPieList(ClientDailyReportVO clientDailyReportVO);
	
	List<ClientDailyReportVO> selectInstallForDownLoad(ClientDailyReportVO clientDailyReportVO);

	Long selectInstallForPieListCount(ClientDailyReportVO clientDailyReportVO);

	long selectCount(ClientDailyReportVO clientDailyReportVO);

	List<ClientDailyReport> selectList(ClientDailyReportVO clientDailyReportVO);

	ClientDailyReport getClientMatch(ClientDailyReportVO clientDailyReportVO);

}
