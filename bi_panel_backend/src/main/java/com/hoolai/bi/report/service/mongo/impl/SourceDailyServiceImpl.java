package com.hoolai.bi.report.service.mongo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.mongo.SourceDailyDao;
import com.hoolai.bi.report.model.entity.mongo.SourceDaily;
import com.hoolai.bi.report.service.mongo.SourceDailyService;
import com.mongodb.WriteResult;

@Service
public class SourceDailyServiceImpl implements SourceDailyService {

	@Autowired
	private SourceDailyDao sourceDailyDao;

	private void initConnection(String snid, String gameid) {
		sourceDailyDao.initConnectionName(snid, gameid);
	}

	private void initConnection(SourceDaily sourceDaily) {
		initConnection(sourceDaily.getSnid(), sourceDaily.getGameid());
	}

	@Override
	public String save(SourceDaily sourceDaily) {
		initConnection(sourceDaily);
		sourceDailyDao.save(sourceDaily);
		return sourceDaily.getId();
	}
	
	@Override
	public int batchSave(String snid, String gameid, List<SourceDaily> sourceDailies) {
		initConnection(snid, gameid);
		WriteResult result = sourceDailyDao.insert(sourceDailies);
		return result.getN();
	} 

	@Override
	public List<SourceDaily> find(String snid, String gameid) {
		initConnection(snid, gameid);
		return sourceDailyDao.findAll();
	}

	@Override
	public void delete(SourceDaily sourceDaily) {
		initConnection(sourceDaily);
		sourceDailyDao.remove(sourceDaily);
	}

	@Override
	public void update(SourceDaily sourceDaily) {
		// sourceDailyDao.update(sourceDaily);
	}

}
