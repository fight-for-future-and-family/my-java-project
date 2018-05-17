package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.ChannelCpa;
import com.hoolai.bi.report.vo.ChannelCpaVO;
import com.jian.service.GenericService;

public interface ChannelCpaService extends GenericService<ChannelCpa, Long>{

	List<ChannelCpa> selectListBySource(ChannelCpaVO channelCpaVO);

	List<ChannelCpa> selectListByMonth(ChannelCpaVO channelCpaVO);
	
	Long selectMatchCount(ChannelCpaVO channelCpaVO);
	
	Long selectBySourceCount(ChannelCpaVO channelCpaVO);
	
	Long selectByMonthCount(ChannelCpaVO channelCpaVO);


}
