package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.EconomyItemReport;
import com.hoolai.bi.report.model.entity.EconomyNewReport;
import com.hoolai.bi.report.vo.EconomyNewReportVO;
import com.jian.service.GenericService;

public interface EconomyNewReportService extends GenericService<EconomyNewReport, Long>{

    List<EconomyItemReport> selectItemList(String queryType,EconomyNewReportVO economyReportVO);
    
    EconomyItemReport selectItemCountList(String queryType,EconomyNewReportVO economyReportVO);
	
	List<EconomyNewReport> selectItemList4Down(EconomyNewReportVO economyReportVO);

	Long selectItemCount(EconomyNewReportVO economyReportVO);

}
