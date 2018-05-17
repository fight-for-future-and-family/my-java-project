package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.LevelInstallPayerReportDao;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.model.entity.LevelInstallPayerReport;
import com.hoolai.bi.report.vo.LevelInstallPayerReportVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class LevelInstallPayerReportDaoImpl extends GenericDaoImpl<LevelInstallPayerReport, Long> implements LevelInstallPayerReportDao {

	@Override
	public String namespace() {
		return LevelInstallPayerReport.class.getName();
	}
	
	@Override
	public List<LevelInstallDauReport> getLevelDauReportList(LevelInstallPayerReportVO installPayerReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".getLevelDauReportList", installPayerReportVO);
	}

}
