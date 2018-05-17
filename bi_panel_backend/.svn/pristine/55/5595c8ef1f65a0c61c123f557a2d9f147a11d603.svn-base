package com.hoolai.bi.report.service;

import java.util.List;
import java.util.Map;

import com.hoolai.bi.report.model.entity.GamesCreative;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.jian.service.GenericService;

public interface WandaDailyReportService extends GenericService<WandaDailyReport, Long>{

	int saveDailyReport(WandaDailyReport wandaDailyReport);

	List<WandaDailyReport> queryDailyData(WandaDailyReport wandaDailyReport);
	
	int updateWandaDailyReport(WandaDailyReport wandaDailyReport);
	
	int selectExists(WandaDailyReport wandaDailyReport);

	int saveWandaDailysReport(List<WandaDailyReport> resultList);

	List<WandaDailyReport> selectSourceDaily(Map<String, String> dateMap);

	List<WandaDailyReport> selectGameCreativeDailyBysource(GamesCreative gamesCreative);

	List<WandaDailyReport> selectFirstTen(Map<String, String> dateMap);

	List<WandaDailyReport> selectSeriesList(Map<String, String> paramsMap);

	List<WandaDailyReport> selectOther(Map<String, String> dateMap);

	List<WandaDailyReport> selectGwCreativeDailyBysource(GamesCreative gamesCreative);

}
