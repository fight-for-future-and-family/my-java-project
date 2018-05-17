package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.WhaleUserDao;
import com.hoolai.bi.report.model.entity.whaleUser.WhaleUser;
import com.hoolai.bi.report.vo.WhaleUserVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class WhaleUserDaoImpl extends GenericDaoImpl<WhaleUser, Long> implements WhaleUserDao {

	@Override
	public String namespace() {
		return WhaleUser.class.getName();
	}
	
	@Override
	public List<WhaleUser> selectList(WhaleUserVO WhaleUserVO) {
		return sqlSessionTemplate.selectList(this.namespace()+".selectList", WhaleUserVO);
	}

	@Override
	public WhaleUser selectMaxDs(WhaleUserVO whaleUserVO) {
		return sqlSessionTemplate.selectOne(this.namespace()+".selectMaxDs", whaleUserVO);
	}

	
}
