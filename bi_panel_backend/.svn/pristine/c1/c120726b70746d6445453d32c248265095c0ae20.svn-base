package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.LevelDauReportDao;
import com.hoolai.bi.report.model.entity.LevelDauReport;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.vo.LevelDauReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class LevelDauReportDaoImpl extends GenericDaoImpl<LevelDauReport, Long> implements LevelDauReportDao {

	@Override
	public String namespace() {
		return LevelDauReport.class.getName();
	}
	
	@Override
	public List<LevelInstallDauReport> getLevelDauReportList(LevelDauReportVO levelDauReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".getLevelDauReportList", levelDauReportVO);
	}

}
