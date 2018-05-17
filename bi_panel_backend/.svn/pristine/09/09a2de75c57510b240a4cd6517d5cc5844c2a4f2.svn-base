package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.DauStructDao;
import com.hoolai.bi.report.model.entity.DauStruct;
import com.hoolai.bi.report.service.DauStructService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class DauStructServiceImpl extends GenericServiceImpl<DauStruct, Long> implements DauStructService {

	@Autowired
	private DauStructDao entityDao;
	
	@Override
    public GenericDao<DauStruct, Long> getGenricDao() {
            return this.entityDao;
    }


}
