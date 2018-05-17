package com.hoolai.bi.report.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.GamesDao;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class GamesDaoImpl extends GenericDaoImpl<Games, Long> implements GamesDao {

	@Override
	public String namespace() {
		return Games.class.getName();
	}

	@Override
	public List<Games> selectAllGames() {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAllGames");
	}

	@Override
	public List<Games> selectGamesByUserId(long userId) {
		try {
			return this.sqlSessionTemplate.selectList(this.namespace()+".selectGamesByUserId", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	@Override
	public List<Games> selectAllGames2() {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAllGames2");
	}
	
}
