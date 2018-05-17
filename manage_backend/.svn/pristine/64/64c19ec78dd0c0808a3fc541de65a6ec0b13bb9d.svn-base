package com.hoolai.manage.dao;

import java.util.List;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.model.PlatformsParams;
import com.hoolai.manage.vo.PlatformsParamsVO;


public interface PlatformsParamsDao extends GenericDao<PlatformsParams, Long> {

	List<PlatformsParams> selectList(PlatformsParamsVO paramsVO);

	void removeByPlatformId(Long platformId);

	void modifyByPlatforms(PlatformsParamsVO paramsVO);

}
