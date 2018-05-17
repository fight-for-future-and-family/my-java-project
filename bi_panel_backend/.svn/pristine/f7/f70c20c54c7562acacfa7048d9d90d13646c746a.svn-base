package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.AnalysisGDTDao;
import com.hoolai.bi.report.model.entity.AnalysisGDT;
import com.hoolai.bi.report.service.AnalysisGDTService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class AnalysisGDTServiceImpl extends GenericServiceImpl<AnalysisGDT, Long> implements AnalysisGDTService {

	@Autowired
	private AnalysisGDTDao entityDao;
	
	@Override
    public GenericDao<AnalysisGDT, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public AnalysisGDT getByGameId(AnalysisGDT analysisGDT) {
		return entityDao.getByGameId(analysisGDT);
	}

}
