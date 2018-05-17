package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.AdShortUrlDao;
import com.hoolai.bi.report.model.entity.adtracking.AdShortUrl;
import com.hoolai.bi.report.vo.AdShortUrlVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class AdShortUrlDaoImpl extends GenericDaoImpl<AdShortUrl, Long> implements AdShortUrlDao {

	@Override
	public String namespace() {
		return AdShortUrl.class.getName();
	}

	@Override
	public AdShortUrl getByGameId(AdShortUrlVO adShortUrlVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".getByGameId",adShortUrlVO);
	}

	@Override
	public void delByPlatforms(AdShortUrl adShortUrl) {
		this.sqlSessionTemplate.update(this.namespace()+".delByPlatforms",adShortUrl);
	}

	@Override
	public void modifyByPlatforms(AdShortUrl adShortUrl) {
		this.sqlSessionTemplate.update(this.namespace()+".modifyByPlatforms",adShortUrl);
	}

	@Override
	public List<AdShortUrl> selectList(AdShortUrlVO adShortUrlVO) {
		return sqlSessionTemplate.selectList(this.namespace()+".selectList", adShortUrlVO);
	}

	//更新状态
	@Override
	public void updateById(Long id) {
		this.sqlSessionTemplate.update(this.namespace()+".updateById", id);
	}

	@Override
	public AdShortUrl getShortUrlByPlatform(AdShortUrl adShortUrl) {
		return this.sqlSessionTemplate.selectOne("selectShortUrlByPlatform", adShortUrl);
	}

	
}
