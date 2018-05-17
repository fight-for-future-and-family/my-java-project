package com.hoolai.bi.report.dao.impl;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.SummaryGDTDao;
import com.hoolai.bi.report.model.entity.SummaryGDT;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class SummaryGDTDaoImpl extends GenericDaoImpl<SummaryGDT, Long> implements SummaryGDTDao {

	@Override
	public String namespace() {
		return SummaryGDT.class.getName();
	}

	@Override
	public SummaryGDT getByGameId(SummaryGDT summaryGDT) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".getByGameId", summaryGDT);
	}

	
}
