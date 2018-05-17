package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.UserGamesDao;
import com.hoolai.bi.report.model.entity.UserGame;
import com.hoolai.bi.report.service.UserGamesService;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.UserGamesVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class UserGamesServiceImpl extends GenericServiceImpl<UserGame, Long> implements UserGamesService {

	@Autowired
    private UserGamesDao dao;

    @Override
    public GenericDao<UserGame, Long> getGenricDao() {
            return this.dao;
    }

	@Override
	public int removeByUserId(Long userId) {
		try {
			UserGame userGame = new UserGame();
			userGame.setUserId(userId);
			return this.dao.removeByUserId(userGame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int removeByGameId(Integer gameId) {
		try {
			UserGame userGame = new UserGame();
			userGame.setGameId(gameId);
			return this.dao.removeByGameId(userGame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<UserGamesVO> getGameUserList(Long gameId) {
		UserGamesVO userGamesVO=new UserGamesVO();
		userGamesVO.getEntity().setGameId(gameId.intValue());
		return this.dao.getGameUserList(userGamesVO);
	}

	@Override
	public int removeByGroupId(GamesVO vo) {
		return dao.removeByGroupId(vo);
	}
}
