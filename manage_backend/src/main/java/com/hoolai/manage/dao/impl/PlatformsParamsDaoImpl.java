package com.hoolai.manage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.PlatformsParamsDao;
import com.hoolai.manage.model.PlatformsParams;
import com.hoolai.manage.vo.PlatformsParamsVO;

@Repository
public class PlatformsParamsDaoImpl extends GenericDaoImpl<PlatformsParams, Long> implements PlatformsParamsDao {

	@Override
	public String namespace() {
		return PlatformsParams.class.getName();
	}

	@Override
	public List<PlatformsParams> selectList(PlatformsParamsVO paramsVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", paramsVO);
	}

	@Override
	public void removeByPlatformId(Long platformId) {
		this.sqlSessionTemplate.delete(this.namespace()+".removeByPlatformId", platformId);
	}

	@Override
	public void modifyByPlatforms(PlatformsParamsVO paramsVO) {
		this.sqlSessionTemplate.update(this.namespace()+".modifyByPlatforms", paramsVO);
	}
	
}
