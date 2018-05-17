package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.WeekReportDao;
import com.hoolai.bi.report.model.entity.WeekReport;
import com.hoolai.bi.report.service.WeekReportService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class WeekReportServiceImpl extends GenericServiceImpl<WeekReport, Long> implements WeekReportService {

	@Autowired
	private WeekReportDao entityDao;
	
	@Override
    public GenericDao<WeekReport, Long> getGenricDao() {
            return this.entityDao;
    }


}
