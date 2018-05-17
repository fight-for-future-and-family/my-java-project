package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.adtracking.AdShortUrl;
import com.hoolai.bi.report.vo.AdShortUrlVO;
import com.hoolai.dao.GenericDao;


public interface AdShortUrlDao extends GenericDao<AdShortUrl, Long> {

	AdShortUrl getByGameId(AdShortUrlVO adShortUrlVO);

	void delByPlatforms(AdShortUrl adShortUrl);

	void modifyByPlatforms(AdShortUrl adShortUrl);

	List<AdShortUrl> selectList(AdShortUrlVO adShortUrlVO);

	void updateById(Long id);

	AdShortUrl getShortUrlByPlatform(AdShortUrl adShortUrl);

}
