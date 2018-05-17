package com.hoolai.bi.report.dao;

import java.util.List;
import java.util.Map;

import com.hoolai.bi.report.model.entity.GamesCreative;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.dao.GenericDao;


public interface WandaDailyReportDao extends GenericDao<WandaDailyReport, Long> {

	int saveWandaDailysReport(List<WandaDailyReport> resultList);

	List<WandaDailyReport> selectSourceDaily(Map<String, String> dateMap);

	List<WandaDailyReport> selectGameCreativeDailyBysource(GamesCreative gamesCreative);
	
	List<WandaDailyReport> queryDailyData(WandaDailyReport wandaDailyReport);

	int selectExists(WandaDailyReport wandaDailyReport);

	int saveDailyReport(WandaDailyReport wandaDailyReport);
	
	int updateWandaDailyReport(WandaDailyReport wandaDailyReport);

	List<WandaDailyReport> selectFirstTen(Map<String, String> dateMap);

	List<WandaDailyReport> selectSeriesList(Map<String, String> paramsMap);

	List<WandaDailyReport> selectOther(Map<String, String> dateMap);

	List<WandaDailyReport> selectGwCreativeDailyBysource(GamesCreative gamesCreative);
	
}
