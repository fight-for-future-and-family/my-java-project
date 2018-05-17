package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.ConsumeDimensionDao;
import com.hoolai.bi.report.model.entity.ConsumeDimension;
import com.hoolai.bi.report.vo.ConsumeDimensionVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class ConsumeDimensionDaoImpl extends GenericDaoImpl<ConsumeDimension, Long> implements ConsumeDimensionDao {

	@Override
	public String namespace() {
		return ConsumeDimension.class.getName();
	}

	@Override
	public List<ConsumeDimension> selectList(ConsumeDimensionVO consumeDimensionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", consumeDimensionVO);
	}

	@Override
	public ConsumeDimension getByGameId(ConsumeDimension consumeDimensionVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".getByGameId", consumeDimensionVO);
	}

	
}
