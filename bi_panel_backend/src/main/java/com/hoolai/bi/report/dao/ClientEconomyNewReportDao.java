package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.ClientEconomyNewReport;
import com.hoolai.bi.report.model.entity.EconomyItemReport;
import com.hoolai.bi.report.vo.ClientEconomyNewReportVO;
import com.hoolai.dao.GenericDao;


public interface ClientEconomyNewReportDao extends GenericDao<ClientEconomyNewReport, Long> {

	List<Integer> selectGameClients(ClientEconomyNewReportVO clientEconomyNewReportVO);

	List<ClientEconomyNewReport> selectItemList4Down(ClientEconomyNewReportVO clientEconomyNewReportVO);

	Long selectItemCount(ClientEconomyNewReportVO clientEconomyNewReportVO);

	List<EconomyItemReport> selectItemList(ClientEconomyNewReportVO clientEconomyNewReportVO);
}
