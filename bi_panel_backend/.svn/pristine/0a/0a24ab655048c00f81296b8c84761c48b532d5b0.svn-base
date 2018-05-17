package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.AdShortUrlDao;
import com.hoolai.bi.report.model.entity.adtracking.AdShortUrl;
import com.hoolai.bi.report.service.AdShortUrlService;
import com.hoolai.bi.report.vo.AdShortUrlVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class AdShortUrlServiceImpl extends GenericServiceImpl<AdShortUrl, Long> implements AdShortUrlService {

	@Autowired
	private AdShortUrlDao entityDao;
	
	@Override
    public GenericDao<AdShortUrl, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public AdShortUrl getByGameId(AdShortUrlVO adShortUrlVO) {
		return this.entityDao.getByGameId(adShortUrlVO);
	}

	@Override
	public Long selectCount(AdShortUrlVO adShortUrlVO) {
		try {
			return this.entityDao.selecCount(adShortUrlVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}


	@Override
	public void modifyByPlatforms(AdShortUrl adShortUrl) {
		this.entityDao.modifyByPlatforms(adShortUrl);
	}

	@Override
	public void delByPlatforms(AdShortUrl adShortUrl) {
		this.entityDao.delByPlatforms(adShortUrl);
	}

	@Override
	public List<AdShortUrl> selectList(AdShortUrlVO adShortUrlVO) {
		return this.entityDao.selectList(adShortUrlVO);
	}

	//删除时将adshorturl状态进行更新
	@Override
	public void updateById(Long id) {
		this.entityDao.updateById(id);
	}

	@Override
	public AdShortUrl getShortUrlByPlatform(AdShortUrl adShortUrl) {
		return this.entityDao.getShortUrlByPlatform(adShortUrl);
	}

}
