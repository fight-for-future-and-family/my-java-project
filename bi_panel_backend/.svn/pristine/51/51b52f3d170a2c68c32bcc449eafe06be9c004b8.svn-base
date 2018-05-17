package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.ChannelCpsDao;
import com.hoolai.bi.report.model.entity.ChannelCps;
import com.hoolai.bi.report.vo.ChannelCpsVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class ChannelCpsDaoImpl extends GenericDaoImpl<ChannelCps, Long> implements ChannelCpsDao {

	@Override
	public String namespace() {
		return ChannelCps.class.getName();
	}

	@Override
	public List<ChannelCps> selectListBySource(ChannelCpsVO channelCpsVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectListBySource", channelCpsVO);
	}

	@Override
	public List<ChannelCps> selectListByMonth(ChannelCpsVO channelCpsVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectListByMonth", channelCpsVO);
	}

	@Override
	public Long selectBySourceCount(ChannelCpsVO channelCpsVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectBySourceCount", channelCpsVO);
	}

	@Override
	public Long selectByMonthCount(ChannelCpsVO channelCpsVO) {
		return (Long)this.sqlSessionTemplate.selectOne(this.namespace()+".selectByMonthCount", channelCpsVO);
	}

	
}
