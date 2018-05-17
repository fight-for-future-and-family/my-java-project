package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.DailyReportDao;
import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.GamePrediction;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.GamePredictionVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class DailyReportDaoImpl extends GenericDaoImpl<DailyReport, Long> implements DailyReportDao {

	@Override
	public String namespace() {
		return DailyReport.class.getName();
	}

	@Override
	public List<DailyReport> selectDailyReport4Auth(GamesVO gamesVO) {
		return this.sqlSessionTemplate.selectList("selectDailyReport4Auth", gamesVO);
	}

	@Override
	public GamePrediction avg4Predict(GamePredictionVO gamePredictionVO) {
		return this.sqlSessionTemplate.selectOne("avg4Predict", gamePredictionVO);
	}

	@Override
	public GamePrediction sumPayAmount4Predict(GamePredictionVO gamePredictionVO) {
		return this.sqlSessionTemplate.selectOne("sumPayAmount4Predict", gamePredictionVO);
	}

	@Override
	public GamePrediction avgOldUserLossRate4Predict(GamePredictionVO gamePredictionVO) {
		return this.sqlSessionTemplate.selectOne("avgOldUserLossRate4Predict", gamePredictionVO);
	}

	@Override
	public List<DailyReport> getMatchJoin(DailyReportVO dailyReportVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectMatchJoin", dailyReportVO);
	}

	@Override
	public DailyReport getSumMatchJoin(DailyReportVO dailyReportVO) {
		return this.sqlSessionTemplate.selectOne("selectSumMatchJoin", dailyReportVO);
	}

	//查询一段时间内的收益
	@Override
	public List<DailyReport> selectDailyReport(GamesVO gamesVO) {
		return this.sqlSessionTemplate.selectList("selectDailyReport", gamesVO);
	}

	@Override
	public List<DailyReport> selectDailyDownloadAllList(DailyReportVO dailyReportVO) {
		return this.sqlSessionTemplate.selectList("selectDailyDownloadAllList", dailyReportVO);
	}
	
}
