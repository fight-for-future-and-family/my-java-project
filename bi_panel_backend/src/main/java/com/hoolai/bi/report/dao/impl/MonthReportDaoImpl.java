package com.hoolai.bi.report.dao.impl;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.MonthReportDao;
import com.hoolai.bi.report.model.entity.MonthReport;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class MonthReportDaoImpl extends GenericDaoImpl<MonthReport, Long> implements MonthReportDao {

	@Override
	public String namespace() {
		return MonthReport.class.getName();
	}
	
}
