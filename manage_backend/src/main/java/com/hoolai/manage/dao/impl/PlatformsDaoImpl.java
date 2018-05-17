package com.hoolai.manage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.PlatformsDao;
import com.hoolai.manage.model.Platforms;
import com.hoolai.manage.vo.PlatformsVO;

@Repository
public class PlatformsDaoImpl extends GenericDaoImpl<Platforms, Long> implements PlatformsDao {

	@Override
	public String namespace() {
		return Platforms.class.getName();
	}

	@Override
	public Platforms getByCodeAndName(PlatformsVO platforms) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".getByCodeAndName", platforms);
	}

	@Override
	public List<Platforms> selectPlatforms(PlatformsVO platforms) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectPlatforms", platforms);
	}
}
