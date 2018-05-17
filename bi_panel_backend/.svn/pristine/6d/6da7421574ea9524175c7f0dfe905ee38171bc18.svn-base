package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.ClientEconomyNewReport;
import com.hoolai.bi.report.model.entity.EconomyItemReport;
import com.hoolai.bi.report.vo.ClientEconomyNewReportVO;
import com.jian.service.GenericService;

public interface ClientEconomyNewReportService extends GenericService<ClientEconomyNewReport, Long>{

	List<Integer> selectGameClients(ClientEconomyNewReportVO clientEconomyNewReportVO);

	List<EconomyItemReport> selectItemList(String queryType,ClientEconomyNewReportVO clientEconomyNewReportVO);
	
	List<ClientEconomyNewReport> selectItemList4Down(ClientEconomyNewReportVO clientEconomyNewReportVO);

	Long selectItemCount(ClientEconomyNewReportVO clientEconomyNewReportVO);

	EconomyItemReport selectItemCountList(String queryType,ClientEconomyNewReportVO clientEconomyReportVO);
}
