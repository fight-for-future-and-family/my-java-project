package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.Games;
import com.jian.service.GenericService;

public interface GamesService extends GenericService<Games, Long>{

    public List<Games> selectAllGames();
    
    public List<Games> selectGamesByUserId(long userId);
    
    Games getGames(String snid,String gameid);
    
    Games getGames(Long snid,Long gameid);
    
    public List<Games> selectAllGames2();
}
