package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.CpaSourceDimensionDao;
import com.hoolai.bi.report.model.entity.CpaSourceDimension;
import com.hoolai.bi.report.vo.CpaSourceDimensionVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class CpaSourceDimensionDaoImpl extends GenericDaoImpl<CpaSourceDimension, Long> implements CpaSourceDimensionDao {

	@Override
	public String namespace() {
		return CpaSourceDimension.class.getName();
	}

	@Override
	public CpaSourceDimension getByCode(CpaSourceDimension dimension) {
		CpaSourceDimensionVO vo = new CpaSourceDimensionVO(dimension);
		return this.sqlSessionTemplate.selectOne(this.namespace()+".getByCode", vo);
	}

	@Override
	public List<CpaSourceDimension> selectList(CpaSourceDimensionVO dimensionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList",dimensionVO);
	}

	@Override
	public List<CpaSourceDimension> getUserSource(CpaSourceDimensionVO dimensionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".getUserSource",dimensionVO);
	}

	
}
