package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.GamePrediction;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.GamePredictionVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.dao.GenericDao;


public interface DailyReportDao extends GenericDao<DailyReport, Long> {

	List<DailyReport> selectDailyReport4Auth(GamesVO gamesVO);

	GamePrediction avg4Predict(GamePredictionVO gamePredictionVO);

	GamePrediction sumPayAmount4Predict(GamePredictionVO gamePredictionVO);

	GamePrediction avgOldUserLossRate4Predict(GamePredictionVO gamePredictionVO);

	List<DailyReport> getMatchJoin(DailyReportVO dailyReportVO);

	DailyReport getSumMatchJoin(DailyReportVO dailyReportVO);
	
	List<DailyReport> selectDailyReport(GamesVO gamesVO);

	List<DailyReport> selectDailyDownloadAllList(DailyReportVO dailyReportVO);

}
