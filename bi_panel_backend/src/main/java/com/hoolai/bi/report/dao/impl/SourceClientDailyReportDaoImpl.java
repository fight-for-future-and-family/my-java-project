package com.hoolai.bi.report.dao.impl;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.SourceClientDailyReportDao;
import com.hoolai.bi.report.model.entity.SourceClientDailyReport;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class SourceClientDailyReportDaoImpl extends GenericDaoImpl<SourceClientDailyReport, Long> implements SourceClientDailyReportDao {

	@Override
	public String namespace() {
		return SourceClientDailyReport.class.getName();
	}
	
}
