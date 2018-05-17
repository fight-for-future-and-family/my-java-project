package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.AdminOpMonitorDao;
import com.hoolai.bi.report.model.entity.AdminOpMonitor;
import com.hoolai.bi.report.service.AdminOpMonitorService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class AdminOpMonitorServiceImpl extends GenericServiceImpl<AdminOpMonitor, Long> implements AdminOpMonitorService {

	@Autowired
	private AdminOpMonitorDao entityDao;
	
	@Override
    public GenericDao<AdminOpMonitor, Long> getGenricDao() {
            return this.entityDao;
    }

}
