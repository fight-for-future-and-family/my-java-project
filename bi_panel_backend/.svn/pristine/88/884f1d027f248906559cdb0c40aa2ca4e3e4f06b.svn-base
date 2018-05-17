package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.RealTimeNoClientResultDao;
import com.hoolai.bi.report.model.entity.RealTimeNoClientResult;
import com.hoolai.bi.report.service.RealTimeNoClientResultService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class RealTimeNoClientResultServiceImpl extends GenericServiceImpl<RealTimeNoClientResult, Long> implements RealTimeNoClientResultService {

	@Autowired
	private RealTimeNoClientResultDao entityDao;
	
	@Override
    public GenericDao<RealTimeNoClientResult, Long> getGenricDao() {
            return this.entityDao;
    }

}
