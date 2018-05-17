package com.hoolai.bi.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.SeriesAllDao;
import com.hoolai.bi.report.model.entity.SeriesAll;
import com.hoolai.bi.report.model.entity.SeriesGame;
import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.dao.pagination.Pagination;

@Repository
public class SeriesAllDaoImpl extends GenericDaoImpl<SeriesAll, Long> implements SeriesAllDao {

	@Override
	public List<SeriesAll> selectSeriesAll(SeriesAll seriesAll) {
		return this.sqlSessionTemplate.selectList("selectSeriesAll", seriesAll);
	}

	@Override
	public List<SeriesGame> selectSeriesGame(SeriesGame seriesGame) {
		return this.sqlSessionTemplate.selectList("selectSeriesGame", seriesGame);
	}

	@Override
	public int saveSeriesAllList(List<SeriesAll> seriesAllList) {
		return this.sqlSessionTemplate.insert("saveSeriesAllList", seriesAllList);
	}

	@Override
	public int saveSeriesGameList(List<SeriesGame> seriesGameList) {
		return this.sqlSessionTemplate.insert("saveSeriesGameList", seriesGameList);
	}

	@Override
	public int removeAllBySeriesid(String seriesid) {
		return this.sqlSessionTemplate.delete("removeAllBySeriesid", seriesid);
	}

	@Override
	public int removeGamesBySeriesid(String seriesid) {
		return this.sqlSessionTemplate.delete("removeGamesBySeriesid", seriesid);
	}

}
