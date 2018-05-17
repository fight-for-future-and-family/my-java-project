package com.hoolai.bi.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.WandaDailyReportDao;
import com.hoolai.bi.report.model.entity.GamesCreative;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.service.WandaDailyReportService;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class WandaDailyReportServiceImpl extends GenericServiceImpl<WandaDailyReport, Long> implements WandaDailyReportService {

	@Autowired
	private WandaDailyReportDao entityDao;
	
	@Override
    public GenericDao<WandaDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public int saveDailyReport(WandaDailyReport wandaDailyReport) {
		int num = this.entityDao.saveDailyReport(wandaDailyReport);
		return num;
	}

	@Override
	public List<WandaDailyReport> queryDailyData(WandaDailyReport wandaDailyReport) {
		List<WandaDailyReport> listReport = this.entityDao.queryDailyData(wandaDailyReport);
		return listReport;
	}

	@Override
	public int updateWandaDailyReport(WandaDailyReport wandaDailyReport) {
		int num = this.entityDao.updateWandaDailyReport(wandaDailyReport);
		return num;
	}

	@Override
	public int selectExists(WandaDailyReport wandaDailyReport) {
		int num = this.entityDao.selectExists(wandaDailyReport);
		return num;
	}

	@Override
	public int saveWandaDailysReport(List<WandaDailyReport> resultList) {
		return this.entityDao.saveWandaDailysReport(resultList);
	}

	@Override
	public List<WandaDailyReport> selectSourceDaily(Map<String, String> dateMap) {
		return this.entityDao.selectSourceDaily(dateMap);
	}

	@Override
	public List<WandaDailyReport> selectGameCreativeDailyBysource(GamesCreative gamesCreative) {
		return this.entityDao.selectGameCreativeDailyBysource(gamesCreative);
	}

	@Override
	public List<WandaDailyReport> selectFirstTen(Map<String, String> dateMap) {
		return this.entityDao.selectFirstTen(dateMap);
	}

	@Override
	public List<WandaDailyReport> selectSeriesList(Map<String, String> paramsMap) {
		return this.entityDao.selectSeriesList(paramsMap);
	}

	@Override
	public List<WandaDailyReport> selectOther(Map<String, String> dateMap) {
		return this.entityDao.selectOther(dateMap);
	}

	@Override
	public List<WandaDailyReport> selectGwCreativeDailyBysource(GamesCreative gamesCreative) {
		return this.entityDao.selectGwCreativeDailyBysource(gamesCreative);
	}


}
