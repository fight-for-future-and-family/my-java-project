package com.hoolai.bi.report.dao;


import java.util.List;

import com.hoolai.bi.report.model.entity.HourLtvSourceDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceRetentionDailyReport;
import com.hoolai.bi.report.vo.HourLtvSourceDailyReportVO;
import com.hoolai.bi.report.vo.HourSourceRetentionDailyReportVo;
import com.hoolai.dao.GenericDao;

public interface HourSourceRetentionDailyReportDao extends GenericDao<HourSourceRetentionDailyReport, Long> {

	
	List<String> selectGameSources(HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo);

	List<HourSourceRetentionDailyReport> selectList(HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo);

	void removeHourDaily(HourSourceRetentionDailyReport hourSourceRetentionDailyReport);

	void saveList(List<HourSourceRetentionDailyReport> hourSourceRetentionDailyReport);
}
