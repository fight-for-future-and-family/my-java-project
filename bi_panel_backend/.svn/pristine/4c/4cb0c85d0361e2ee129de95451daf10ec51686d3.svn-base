package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.ChannelCpaDao;
import com.hoolai.bi.report.model.entity.ChannelCpa;
import com.hoolai.bi.report.service.ChannelCpaService;
import com.hoolai.bi.report.vo.ChannelCpaVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class ChannelCpaServiceImpl extends GenericServiceImpl<ChannelCpa, Long> implements ChannelCpaService {

	@Autowired
	private ChannelCpaDao entityDao;
	
	@Override
    public GenericDao<ChannelCpa, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<ChannelCpa> selectListBySource(ChannelCpaVO channelCpaVO) {
		return this.entityDao.selectListBySource(channelCpaVO);
	}

	@Override
	public List<ChannelCpa> selectListByMonth(ChannelCpaVO channelCpaVO) {
		return this.entityDao.selectListByMonth(channelCpaVO);
	}

	@Override
	public Long selectMatchCount(ChannelCpaVO channelCpaVO) {
		try {
			return this.entityDao.selecCount(channelCpaVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Long selectBySourceCount(ChannelCpaVO channelCpaVO) {
		try {
			return this.entityDao.selectBySourceCount(channelCpaVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Long selectByMonthCount(ChannelCpaVO channelCpaVO) {
		try {
			return this.entityDao.selectByMonthCount(channelCpaVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}



}
