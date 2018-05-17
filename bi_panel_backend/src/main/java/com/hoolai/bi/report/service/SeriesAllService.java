package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.SeriesAll;
import com.hoolai.bi.report.model.entity.SeriesGame;
import com.jian.service.GenericService;

public interface SeriesAllService extends GenericService<SeriesAll, Long> {
	
	List<SeriesAll> selectSeriesAll(SeriesAll seriesAll);

	List<SeriesGame> selectSeriesGame(SeriesGame seriesGame);

	int saveSeriesAllList(List<SeriesAll> seriesAllList);
	
	int saveSeriesGameList(List<SeriesGame> seriesGameList);

	int removeAllBySeriesid(String valueOf);
	
	int removeGamesBySeriesid(String valueOf);

}
