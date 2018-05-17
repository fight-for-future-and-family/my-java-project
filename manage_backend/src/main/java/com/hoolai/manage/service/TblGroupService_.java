package com.hoolai.manage.service;

import com.hoolai.manage.model.TblGroup;
import com.hoolai.manage.vo.TblGroupVO;
import com.jian.service.GenericService;

public interface TblGroupService_ extends GenericService<TblGroup, Long> {

	public Long getGroupMaxId();
	
	public TblGroupVO getOldReportGameInfo(TblGroupVO games);
	
}
