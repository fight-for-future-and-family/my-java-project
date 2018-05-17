package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.whaleUser.WhaleUser;
import com.hoolai.bi.report.vo.WhaleUserVO;
import com.jian.service.GenericService;

public interface WhaleUserService extends GenericService<WhaleUser, Long>{


	Long selectCount(WhaleUserVO WhaleUserVO);

	List<WhaleUser> selectList(WhaleUserVO WhaleUserVO);
	
	WhaleUser selectMaxDs(WhaleUserVO whaleUserVO);

}
