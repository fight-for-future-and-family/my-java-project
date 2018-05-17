package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.dao.PlatformsDao;
import com.hoolai.manage.model.Platforms;
import com.hoolai.manage.service.PlatformsService;
import com.hoolai.manage.vo.PlatformsVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class PlatformsServiceImpl extends GenericServiceImpl<Platforms, Long> implements PlatformsService {

	@Autowired
	private PlatformsDao entityDao;
	
	@Override
    public GenericDao<Platforms, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Platforms getByCodeAndName(PlatformsVO platforms) {
		return this.entityDao.getByCodeAndName(platforms);
	}

	@Override
	public Long selectCount(PlatformsVO platformsVO) {
		try {
			return this.entityDao.selecCount(platformsVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}


	@Override
	public List<Platforms> selectPlatforms(PlatformsVO platforms) {
		return this.entityDao.selectPlatforms(platforms);
	}

}
