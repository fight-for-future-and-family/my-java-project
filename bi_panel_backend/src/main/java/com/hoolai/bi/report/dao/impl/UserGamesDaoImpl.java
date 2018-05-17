package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.UserGamesDao;
import com.hoolai.bi.report.model.entity.UserGame;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.UserGamesVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class UserGamesDaoImpl  extends GenericDaoImpl<UserGame, Long> implements UserGamesDao{
	
	@Override
	public String namespace() {
		return UserGame.class.getName();
	}

	@Override
	public int removeByUserId(UserGame userGame) {
		return this.sqlSessionTemplate.delete(this.namespace()+".removeByUserId", userGame);
	}
	
	@Override
	public int removeByGameId(UserGame userGame) {
		return this.sqlSessionTemplate.delete(this.namespace()+".removeByGameId", userGame);
	}

	@Override
	public List<UserGamesVO> getGameUserList(UserGamesVO userGamesVO) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectGameUserList",userGamesVO);
	}

	@Override
	public int removeByGroupId(GamesVO vo) {
		return sqlSessionTemplate.delete(this.namespace()+".removeByGroupId", vo);
	}
}
