package com.hoolai.bi.report.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.DailyReportDao;
import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.GamePrediction;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.service.DailyReportService;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.GamePredictionVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class DailyReportServiceImpl extends GenericServiceImpl<DailyReport, Long> implements DailyReportService {

	@Autowired
	private DailyReportDao entityDao;
	
	@Override
    public GenericDao<DailyReport, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<DailyReport> selectDailyReport4Auth(List<Games> games) {
		GamesVO gamesVO = new GamesVO();
		gamesVO.setEntities(games);
		gamesVO.setYesterday(DateUtils.getYesterday());
		return this.entityDao.selectDailyReport4Auth(gamesVO);
	}

	@Override
	public GamePrediction avg4Predict(GamePredictionVO gamePredictionVO) {
		return entityDao.avg4Predict(gamePredictionVO);
	}

	@Override
	public GamePrediction sumPayAmount4Predict(GamePredictionVO gamePredictionVO) {
		return entityDao.sumPayAmount4Predict(gamePredictionVO);
	}

	@Override
	public GamePrediction avgOldUserLossRate4Predict(GamePredictionVO gamePredictionVO) {
		return entityDao.avgOldUserLossRate4Predict(gamePredictionVO);
	}

	@Override
	public List<DailyReport> getMatchJoin(DailyReportVO dailyReportVO) {
		return this.entityDao.getMatchJoin(dailyReportVO);
	}

	/**
	 * 总览汇总数据查询
	 */
	@Override
	public DailyReport getSumMatchJoin(DailyReportVO dailyReportVO) {
		return this.entityDao.getSumMatchJoin(dailyReportVO);
	}

	@Override
	public List<DailyReport> selectDailyDownloadAllList(DailyReportVO dailyReportVO) {
		return this.entityDao.selectDailyDownloadAllList(dailyReportVO);
	}

}
