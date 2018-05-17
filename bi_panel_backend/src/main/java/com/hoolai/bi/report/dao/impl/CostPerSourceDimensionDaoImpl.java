package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.CostPerSourceDimensionDao;
import com.hoolai.bi.report.model.entity.CostPerSourceDimension;
import com.hoolai.bi.report.vo.CostPerSourceDimensionVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class CostPerSourceDimensionDaoImpl extends GenericDaoImpl<CostPerSourceDimension, Long> implements CostPerSourceDimensionDao {

	@Override
	public String namespace() {
		return CostPerSourceDimension.class.getName();
	}

	@Override
	public CostPerSourceDimension getByCode(CostPerSourceDimension dimension) {
		CostPerSourceDimensionVO vo = new CostPerSourceDimensionVO(dimension);
		return this.sqlSessionTemplate.selectOne(this.namespace()+".getByCode", vo);
	}

	@Override
	public List<CostPerSourceDimension> selectList(CostPerSourceDimensionVO dimensionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList",dimensionVO);
	}

	@Override
	public List<CostPerSourceDimension> getUserSource(CostPerSourceDimensionVO dimensionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".getUserSource",dimensionVO);
	}

	
}
