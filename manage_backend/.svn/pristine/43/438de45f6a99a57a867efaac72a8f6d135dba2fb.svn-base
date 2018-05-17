package com.hoolai.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.CustomReportDao;
import com.hoolai.manage.model.CustomReport;
import com.hoolai.manage.service.CustomReportService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class CustomReportServiceImpl extends GenericServiceImpl<CustomReport, Long> implements
		CustomReportService {

	@Autowired
	private CustomReportDao dao;

	@Override
	public GenericDao<CustomReport, Long> getGenricDao() {
		return this.dao;
	}

}
