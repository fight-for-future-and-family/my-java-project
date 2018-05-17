package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.adtracking.AdShortUrl;
import com.hoolai.bi.report.vo.AdShortUrlVO;
import com.jian.service.GenericService;

public interface AdShortUrlService extends GenericService<AdShortUrl, Long>{

	AdShortUrl getByGameId(AdShortUrlVO adShortUrlVO);

	Long selectCount(AdShortUrlVO adShortUrlVO);

	void modifyByPlatforms(AdShortUrl adShortUrl);

	void delByPlatforms(AdShortUrl adShortUrl);

	List<AdShortUrl> selectList(AdShortUrlVO adShortUrlVO);

	void updateById(Long id);

	AdShortUrl getShortUrlByPlatform(AdShortUrl adShortUrl);

}
