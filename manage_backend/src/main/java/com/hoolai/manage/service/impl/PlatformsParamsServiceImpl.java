package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.PlatformsParamsDao;
import com.hoolai.manage.model.PlatformsParams;
import com.hoolai.manage.service.PlatformsParamsService;
import com.hoolai.manage.vo.PlatformsParamsVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class PlatformsParamsServiceImpl extends GenericServiceImpl<PlatformsParams, Long> implements
		PlatformsParamsService {

	@Autowired
	private PlatformsParamsDao dao;

	@Override
	public GenericDao<PlatformsParams, Long> getGenricDao() {
		return this.dao;
	}


	@Override
	public List<PlatformsParams> selectList(PlatformsParamsVO paramsVO) {
		return dao.selectList(paramsVO);
	}

	@Override
	public void removeByPlatformId(Long platformId) {
		this.dao.removeByPlatformId(platformId);
	}

	@Override
	public void modifyByPlatforms(PlatformsParamsVO paramsVO) {
		this.dao.modifyByPlatforms(paramsVO);
	}


}
