package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.LevelDauReport;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.vo.LevelDauReportVO;
import com.jian.service.GenericService;

public interface LevelDauReportService extends GenericService<LevelDauReport, Long>{

	List<LevelInstallDauReport> getLevelDauReportList(LevelDauReportVO levelDauReportVO);


}
