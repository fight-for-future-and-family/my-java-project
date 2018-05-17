package com.hoolai.bi.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.GamesCreativeDao;
import com.hoolai.bi.report.model.entity.GamesCreative;
import com.hoolai.bi.report.model.entity.GamesCreativeRate;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class GamesCreativeDaoImpl extends GenericDaoImpl<GamesCreative, Long> implements GamesCreativeDao {

	@Override
	public List<GamesCreative> selectGames(GamesCreative gamesCreative) {
		return this.sqlSessionTemplate.selectList("selectGames", gamesCreative);
	}

	@Override
	public GamesCreative selectGameCreative(GamesCreative gamesCreative) {
		return this.sqlSessionTemplate.selectOne("selectGameCreative", gamesCreative);
	}

	@Override
	public List<GamesCreative> selectGameNameList() {
		return this.sqlSessionTemplate.selectList("selectGameNameList");
	}

	@Override
	public GamesCreativeRate getRate(WandaDailyReport wandaDailyReport) {
		return this.sqlSessionTemplate.selectOne("selectCreativeRate", wandaDailyReport);
	}

	@Override
	public List<GamesCreativeRate> selectGameCreativeRates(GamesCreativeRate gamesCreativeRate) {
		return this.sqlSessionTemplate.selectList("selectGameCreativeRates", gamesCreativeRate);
	}

	@Override
	public int updateGamesCreativeRate(GamesCreativeRate gamesCreativeRate) {
		return this.sqlSessionTemplate.update("updateGamesCreativeRate", gamesCreativeRate);
	}

	@Override
	public int saveGamesCreativeRate(GamesCreativeRate gamesCreativeRate) {
		return this.sqlSessionTemplate.insert("saveGamesCreativeRate", gamesCreativeRate);
	}

}
