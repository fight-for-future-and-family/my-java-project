package com.hoolai.bi.report.service.mongo;

import java.util.List;

import com.hoolai.bi.report.model.entity.mongo.SourceDaily;

public interface SourceDailyService {
	public String save(SourceDaily sourceDaily);

	public int batchSave(String snid, String gameid, List<SourceDaily> sourceDailies);
	
	public List<SourceDaily> find(String snid, String gameid);

	public void delete(SourceDaily sourceDaily);

	public void update(SourceDaily sourceDaily);
	
}
