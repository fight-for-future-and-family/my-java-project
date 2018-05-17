package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.UserGame;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.UserGamesVO;
import com.hoolai.dao.GenericDao;


public interface UserGamesDao extends GenericDao<UserGame, Long> {

	int removeByUserId(UserGame userGame);

	int removeByGameId(UserGame userGame);
	
	List<UserGamesVO> getGameUserList(UserGamesVO userGamesVO);

	int removeByGroupId(GamesVO vo);

}
