package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.GamePrediction;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.GamePredictionVO;
import com.jian.service.GenericService;

public interface DailyReportService extends GenericService<DailyReport, Long>{

	List<DailyReport> selectDailyReport4Auth(List<Games> games);
	
	GamePrediction avg4Predict(GamePredictionVO gamePredictionVO);
	
	GamePrediction sumPayAmount4Predict(GamePredictionVO gamePredictionVO);
	
	GamePrediction avgOldUserLossRate4Predict(GamePredictionVO gamePredictionVO);

	List<DailyReport> getMatchJoin(DailyReportVO dailyReportVO);

	DailyReport getSumMatchJoin(DailyReportVO dailyReportVO);

	List<DailyReport> selectDailyDownloadAllList(DailyReportVO dailyReportVO);

}
