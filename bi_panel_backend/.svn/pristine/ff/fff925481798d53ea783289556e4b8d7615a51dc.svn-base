package com.hoolai.bi.report.service;

import java.util.List;
import java.util.Map;

import com.hoolai.bi.report.model.entity.GamesCreative;
import com.hoolai.bi.report.model.entity.GamesCreativeRate;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.jian.service.GenericService;

public interface GamesCreativeService extends GenericService<GamesCreative, Long> {

	List<GamesCreative> selectGames(GamesCreative gamesCreative);

	GamesCreative selectGameCreative(GamesCreative gamesCreative);
	
	List<GamesCreative> selectGameNameList();

	GamesCreativeRate getRate(WandaDailyReport wandaDailyReport);

	List<GamesCreativeRate> selectGameCreativeRates(GamesCreativeRate gamesCreativeRate);

	int updateGamesCreativeRate(GamesCreativeRate gamesCreativeRate);

	int saveGamesCreativeRate(GamesCreativeRate gamesCreativeRate);

}
