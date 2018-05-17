package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.GamesDao;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class GamesServiceImpl extends GenericServiceImpl<Games, Long> implements GamesService {

	@Autowired
	private GamesDao entityDao;
	
	@Override
    public GenericDao<Games, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<Games> selectAllGames() {
		return entityDao.selectAllGames();
	}

	@Override
	public List<Games> selectGamesByUserId(long userId) {
		return entityDao.selectGamesByUserId(userId);
	}

	@Override
	public Games getGames(String snid, String gameid) {
		try {
			GamesVO gamesVO=new GamesVO();
			gamesVO.getEntity().setSnid(snid);
			gamesVO.getEntity().setGameid(gameid);
			return this.entityDao.selectOne(gamesVO);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Games getGames(Long snid, Long gameid) {
		return this.getGames(snid+"", gameid+"");
	}

	@Override
	public List<Games> selectAllGames2() {
		return entityDao.selectAllGames2();
	}
}
