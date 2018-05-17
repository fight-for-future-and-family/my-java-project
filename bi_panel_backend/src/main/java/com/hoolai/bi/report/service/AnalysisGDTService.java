package com.hoolai.bi.report.service;

import com.hoolai.bi.report.model.entity.AnalysisGDT;
import com.jian.service.GenericService;

public interface AnalysisGDTService extends GenericService<AnalysisGDT, Long>{

	AnalysisGDT getByGameId(AnalysisGDT analysisGDT);

}
