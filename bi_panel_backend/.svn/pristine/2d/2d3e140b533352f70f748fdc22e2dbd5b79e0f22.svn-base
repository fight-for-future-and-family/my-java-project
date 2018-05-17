package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.HourSourceRetentionDailyReport;
import com.hoolai.bi.report.vo.HourSourceRetentionDailyReportVo;
import com.hoolai.dao.GenericDao;
import com.jian.service.GenericService;

public interface HourSourceRetentionDailyReportService extends GenericService<HourSourceRetentionDailyReport, Long>  {

	public List<String> selectGameSources(HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo);

	public long selectCount(HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo);

	public List<HourSourceRetentionDailyReport> selectList(HourSourceRetentionDailyReportVo hourSourceRetentionDailyReportVo);

	public void removeHourDaily(HourSourceRetentionDailyReport hourSourceRetentionDailyReport);

	public void saveList(List<HourSourceRetentionDailyReport> hourSourceRetentionDailyReport);
}
