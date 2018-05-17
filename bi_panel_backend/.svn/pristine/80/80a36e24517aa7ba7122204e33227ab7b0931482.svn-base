package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.SeriesAllDao;
import com.hoolai.bi.report.model.entity.SeriesAll;
import com.hoolai.bi.report.model.entity.SeriesGame;
import com.hoolai.bi.report.service.SeriesAllService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class SeriesAllServiceImpl extends GenericServiceImpl<SeriesAll, Long> implements SeriesAllService{

	@Autowired
	private SeriesAllDao entityDao;
	
	@Override
	public List<SeriesAll> selectSeriesAll(SeriesAll seriesAll) {
		return entityDao.selectSeriesAll(seriesAll);
	}

	@Override
	public List<SeriesGame> selectSeriesGame(SeriesGame seriesGame) {
		return entityDao.selectSeriesGame(seriesGame);
	}

	@Override
	public int saveSeriesAllList(List<SeriesAll> seriesAllList) {
		return entityDao.saveSeriesAllList(seriesAllList);
	}

	@Override
	public int saveSeriesGameList(List<SeriesGame> seriesGameList) {
		return entityDao.saveSeriesGameList(seriesGameList);
	}

	@Override
	public int removeAllBySeriesid(String seriesid) {
		return entityDao.removeAllBySeriesid(seriesid);
	}

	@Override
	public int removeGamesBySeriesid(String seriesid) {
		return entityDao.removeGamesBySeriesid(seriesid);
	}

}
