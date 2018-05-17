package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.ChannelCps;
import com.hoolai.bi.report.vo.ChannelCpsVO;
import com.jian.service.GenericService;

public interface ChannelCpsService extends GenericService<ChannelCps, Long>{

	List<ChannelCps> selectListBySource(ChannelCpsVO channelCpsVO);

	List<ChannelCps> selectListByMonth(ChannelCpsVO channelCpsVO);
	
	Long selectMatchCount(ChannelCpsVO channelCpsVO);
	
	Long selectBySourceCount(ChannelCpsVO channelCpsVO);
	
	Long selectByMonthCount(ChannelCpsVO channelCpsVO);


}
