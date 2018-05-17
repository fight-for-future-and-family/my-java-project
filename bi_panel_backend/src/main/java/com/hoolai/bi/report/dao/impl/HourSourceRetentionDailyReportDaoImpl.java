package com.hoolai.bi.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.HourSourceRetentionDailyReportDao;
import com.hoolai.bi.report.model.entity.HourLtvSourceDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceRetentionDailyReport;
import com.hoolai.bi.report.vo.HourLtvSourceDailyReportVO;
import com.hoolai.bi.report.vo.HourSourceRetentionDailyReportVo;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.dao.pagination.Pagination;
@Repository
public class HourSourceRetentionDailyReportDaoImpl extends GenericDaoImpl<HourSourceRetentionDailyReport, Long>  implements
		GenericDao<HourSourceRetentionDailyReport, Long>, HourSourceRetentionDailyReportDao {

	@Override
	public String namespace() {
		return HourSourceRetentionDailyReport.class.getName();
	}

	@Override
	public List<String> selectGameSources(
			HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGameSources", hourSourceRetentionDailyReportVo);
	}

	@Override
	public List<HourSourceRetentionDailyReport> selectList(
			HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", hourSourceRetentionDailyReportVo);
	}

	@Override
	public void removeHourDaily(
			HourSourceRetentionDailyReport hourSourceRetentionDailyReport) {
		this.sqlSessionTemplate.delete(this.namespace()+".removeLtvSourceHourDaily", hourSourceRetentionDailyReport);
		
	}

	@Override
	public void saveList(
			List<HourSourceRetentionDailyReport> hourSourceRetentionDailyReport) {
		this.sqlSessionTemplate.insert(this.namespace()+".saveList", hourSourceRetentionDailyReport);
	}




}
