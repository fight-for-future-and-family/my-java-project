package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.SeriesAll;
import com.hoolai.bi.report.model.entity.SeriesGame;
import com.hoolai.dao.GenericDao;

public interface SeriesAllDao extends GenericDao<SeriesAll, Long> {
	
	List<SeriesAll> selectSeriesAll(SeriesAll seriesAll);

	List<SeriesGame> selectSeriesGame(SeriesGame seriesGame);

	int saveSeriesAllList(List<SeriesAll> seriesAllList);

	int saveSeriesGameList(List<SeriesGame> seriesGameList);

	int removeAllBySeriesid(String seriesid);
	
	int removeGamesBySeriesid(String seriesid);

}
