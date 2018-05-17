package com.hoolai.bi.report.dao.impl;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.AnalysisGDTDao;
import com.hoolai.bi.report.model.entity.AnalysisGDT;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class AnalysisGDTDaoImpl extends GenericDaoImpl<AnalysisGDT, Long> implements AnalysisGDTDao {

	@Override
	public String namespace() {
		return AnalysisGDT.class.getName();
	}

	@Override
	public AnalysisGDT getByGameId(AnalysisGDT analysisGDT) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".getByGameId", analysisGDT);
	}

	
}
