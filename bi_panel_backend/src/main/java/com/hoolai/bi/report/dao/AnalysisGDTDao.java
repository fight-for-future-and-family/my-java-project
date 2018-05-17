package com.hoolai.bi.report.dao;

import com.hoolai.bi.report.model.entity.AnalysisGDT;
import com.hoolai.dao.GenericDao;


public interface AnalysisGDTDao extends GenericDao<AnalysisGDT, Long> {

	AnalysisGDT getByGameId(AnalysisGDT analysisGDT);

}
