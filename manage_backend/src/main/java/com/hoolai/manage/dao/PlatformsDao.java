package com.hoolai.manage.dao;

import java.util.List;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.model.Platforms;
import com.hoolai.manage.vo.PlatformsVO;


public interface PlatformsDao extends GenericDao<Platforms, Long> {

	Platforms getByCodeAndName(PlatformsVO platforms);

	List<Platforms> selectPlatforms(PlatformsVO platforms);

}
