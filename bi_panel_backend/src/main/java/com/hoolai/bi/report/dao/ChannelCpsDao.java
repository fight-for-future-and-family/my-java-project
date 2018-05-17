package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.ChannelCps;
import com.hoolai.bi.report.vo.ChannelCpsVO;
import com.hoolai.dao.GenericDao;


public interface ChannelCpsDao extends GenericDao<ChannelCps, Long> {

	List<ChannelCps> selectListBySource(ChannelCpsVO channelCpsVO);

	List<ChannelCps> selectListByMonth(ChannelCpsVO channelCpsVO);

	Long selectBySourceCount(ChannelCpsVO channelCpsVO);

	Long selectByMonthCount(ChannelCpsVO channelCpsVO);

}
