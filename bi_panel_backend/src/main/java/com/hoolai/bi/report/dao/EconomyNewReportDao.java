package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.EconomyItemReport;
import com.hoolai.bi.report.model.entity.EconomyNewReport;
import com.hoolai.bi.report.vo.EconomyNewReportVO;
import com.hoolai.dao.GenericDao;

public interface EconomyNewReportDao extends GenericDao<EconomyNewReport, Long>{

	List<EconomyNewReport> selectItemList4Down(EconomyNewReportVO economyReportVO);

	Long selectItemCount(EconomyNewReportVO economyReportVO);

	List<EconomyItemReport> selectItemList(EconomyNewReportVO economyReportVO);

}
