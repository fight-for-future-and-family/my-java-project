package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.ChannelCpsDao;
import com.hoolai.bi.report.model.entity.ChannelCps;
import com.hoolai.bi.report.service.ChannelCpsService;
import com.hoolai.bi.report.vo.ChannelCpsVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class ChannelCpsServiceImpl extends GenericServiceImpl<ChannelCps, Long> implements ChannelCpsService {

	@Autowired
	private ChannelCpsDao entityDao;
	
	@Override
    public GenericDao<ChannelCps, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<ChannelCps> selectListBySource(ChannelCpsVO channelCpsVO) {
		return this.entityDao.selectListBySource(channelCpsVO);
	}

	@Override
	public List<ChannelCps> selectListByMonth(ChannelCpsVO channelCpsVO) {
		return this.entityDao.selectListByMonth(channelCpsVO);
	}

	@Override
	public Long selectMatchCount(ChannelCpsVO channelCpsVO) {
		try {
			return this.entityDao.selecCount(channelCpsVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Long selectBySourceCount(ChannelCpsVO channelCpsVO) {
		try {
			return this.entityDao.selectBySourceCount(channelCpsVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Long selectByMonthCount(ChannelCpsVO channelCpsVO) {
		try {
			return this.entityDao.selectByMonthCount(channelCpsVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}



}
