package com.hoolai.bi.report.service;

import com.hoolai.bi.report.model.entity.MonthReport;
import com.jian.service.GenericService;

public interface MonthReportService extends GenericService<MonthReport, Long>{

	MonthReport get(String snid,String gameid,String month);
	
}
