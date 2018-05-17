package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.UserGame;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.UserGamesVO;
import com.jian.service.GenericService;


public interface UserGamesService extends GenericService<UserGame, Long>{

	int removeByUserId(Long id);

	int removeByGameId(Integer gameId);
	
	List<UserGamesVO> getGameUserList(Long gameId);

	int removeByGroupId(GamesVO vo);
	
}
