package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.ChannelCpa;
import com.hoolai.bi.report.vo.ChannelCpaVO;
import com.hoolai.dao.GenericDao;


public interface ChannelCpaDao extends GenericDao<ChannelCpa, Long> {

	List<ChannelCpa> selectListBySource(ChannelCpaVO channelCpaVO);

	List<ChannelCpa> selectListByMonth(ChannelCpaVO channelCpaVO);

	Long selectBySourceCount(ChannelCpaVO channelCpaVO);

	Long selectByMonthCount(ChannelCpaVO channelCpaVO);

}
