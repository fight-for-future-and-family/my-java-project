package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.ChannelCpaDao;
import com.hoolai.bi.report.model.entity.ChannelCpa;
import com.hoolai.bi.report.vo.ChannelCpaVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class ChannelCpaDaoImpl extends GenericDaoImpl<ChannelCpa, Long> implements ChannelCpaDao {

	@Override
	public String namespace() {
		return ChannelCpa.class.getName();
	}

	@Override
	public List<ChannelCpa> selectListBySource(ChannelCpaVO channelCpaVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectListBySource", channelCpaVO);
	}

	@Override
	public List<ChannelCpa> selectListByMonth(ChannelCpaVO channelCpaVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectListByMonth", channelCpaVO);
	}

	@Override
	public Long selectBySourceCount(ChannelCpaVO channelCpaVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectBySourceCount", channelCpaVO);
	}

	@Override
	public Long selectByMonthCount(ChannelCpaVO channelCpaVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectByMonthCount", channelCpaVO);
	}

	
}
