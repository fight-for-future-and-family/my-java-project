package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.SourceClientDailyReportDao;
import com.hoolai.bi.report.model.entity.SourceClientDailyReport;
import com.hoolai.bi.report.service.SourceClientDailyReportService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class SourceClientDailyReportServiceImpl extends GenericServiceImpl<SourceClientDailyReport, Long> implements SourceClientDailyReportService {

	@Autowired
	private SourceClientDailyReportDao entityDao;
	
	@Override
    public GenericDao<SourceClientDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }


}
