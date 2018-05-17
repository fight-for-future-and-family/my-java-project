package com.hoolai.bi.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.SummaryGDTDao;
import com.hoolai.bi.report.model.entity.SummaryGDT;
import com.hoolai.bi.report.service.SummaryGDTService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class SummaryGDTServiceImpl extends GenericServiceImpl<SummaryGDT, Long> implements SummaryGDTService {

	@Autowired
	private SummaryGDTDao entityDao;
	
	@Override
    public GenericDao<SummaryGDT, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public SummaryGDT getByGameId(SummaryGDT summaryGDT) {
		return entityDao.getByGameId(summaryGDT);
	}

}
