package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.ReportingEachTimeNCDao;
import com.hoolai.bi.report.model.entity.ReportingEachTimeNC;
import com.hoolai.bi.report.service.ReportingEachTimeNCService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class ReportingEachTimeNCServiceImpl extends GenericServiceImpl<ReportingEachTimeNC, Long> implements ReportingEachTimeNCService {

	@Autowired
	private ReportingEachTimeNCDao entityDao;
	
	@Override
    public GenericDao<ReportingEachTimeNC, Long> getGenricDao() {
            return this.entityDao;
    }


}
