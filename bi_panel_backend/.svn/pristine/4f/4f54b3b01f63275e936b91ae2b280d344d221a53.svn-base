package com.hoolai.bi.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.GamesCreativeDao;
import com.hoolai.bi.report.model.entity.GamesCreative;
import com.hoolai.bi.report.model.entity.GamesCreativeRate;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.service.GamesCreativeService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class GamesCreativeServiceImpl extends GenericServiceImpl<GamesCreative, Long> implements GamesCreativeService{

	@Autowired
	private GamesCreativeDao gamesCreativeDao;
	
	@Override
	public List<GamesCreative> selectGames(GamesCreative gamesCreative) {
		return gamesCreativeDao.selectGames(gamesCreative);
	}

	@Override
	public GamesCreative selectGameCreative(GamesCreative gamesCreative) {
		return gamesCreativeDao.selectGameCreative(gamesCreative);
	}

	@Override
	public List<GamesCreative> selectGameNameList() {
		return gamesCreativeDao.selectGameNameList();
	}

	@Override
	public GamesCreativeRate getRate(WandaDailyReport wandaDailyReport) {
		return gamesCreativeDao.getRate(wandaDailyReport);
	}

	@Override
	public List<GamesCreativeRate> selectGameCreativeRates(GamesCreativeRate gamesCreativeRate) {
		return gamesCreativeDao.selectGameCreativeRates(gamesCreativeRate);
	}

	@Override
	public int updateGamesCreativeRate(GamesCreativeRate gamesCreativeRate) {
		return gamesCreativeDao.updateGamesCreativeRate(gamesCreativeRate);
	}

	@Override
	public int saveGamesCreativeRate(GamesCreativeRate gamesCreativeRate) {
		return gamesCreativeDao.saveGamesCreativeRate(gamesCreativeRate);
	}

}
