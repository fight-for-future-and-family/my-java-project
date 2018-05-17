package com.hoolai.bi.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.WandaDailyReportDao;
import com.hoolai.bi.report.model.entity.GamesCreative;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class WandaDailyReportDaoImpl extends GenericDaoImpl<WandaDailyReport, Long> implements WandaDailyReportDao {

	@Override
	public int saveWandaDailysReport(List<WandaDailyReport> resultList) {
		return this.sqlSessionTemplate.insert("saveWandaDailysReport", resultList);
	}

	@Override
	public List<WandaDailyReport> selectSourceDaily(Map<String, String> dateMap) {
		return this.sqlSessionTemplate.selectList("selectSourceDaily", dateMap);
	}

	@Override
	public List<WandaDailyReport> selectGameCreativeDailyBysource(GamesCreative gamesCreative) {
		return this.sqlSessionTemplate.selectList("selectGameCreativeDailyBysource", gamesCreative);
	}
	
	@Override
	public List<WandaDailyReport> queryDailyData(WandaDailyReport wandaDailyReport) {
		List<WandaDailyReport> wandaDailyReports = this.sqlSessionTemplate.selectList("selectDailyData", wandaDailyReport);
		return wandaDailyReports;
	}

	@Override
	public int selectExists(WandaDailyReport wandaDailyReport) {
		int num = this.sqlSessionTemplate.selectOne("selectWandaExists", wandaDailyReport);
		return num;
	}

	@Override
	public int saveDailyReport(WandaDailyReport wandaDailyReport) {
		int num = this.sqlSessionTemplate.insert("saveWandaDailyReport", wandaDailyReport);
		return num;
	}

	@Override
	public int updateWandaDailyReport(WandaDailyReport wandaDailyReport) {
		int num = this.sqlSessionTemplate.update("updateWandaDailyReport", wandaDailyReport);
		return num;
	}

	@Override
	public List<WandaDailyReport> selectFirstTen(Map<String, String> dateMap) {
		return this.sqlSessionTemplate.selectList("selectFirstTen", dateMap);
	}

	@Override
	public List<WandaDailyReport> selectSeriesList(Map<String, String> paramsMap) {
		return this.sqlSessionTemplate.selectList("selectSeriesList", paramsMap);
	}

	@Override
	public List<WandaDailyReport> selectOther(Map<String, String> dateMap) {
		return this.sqlSessionTemplate.selectList("selectOther", dateMap);
	}

	@Override
	public List<WandaDailyReport> selectGwCreativeDailyBysource(GamesCreative gamesCreative) {
		return this.sqlSessionTemplate.selectList("selectGwCreativeDailyBysource", gamesCreative);
	}
	
}
