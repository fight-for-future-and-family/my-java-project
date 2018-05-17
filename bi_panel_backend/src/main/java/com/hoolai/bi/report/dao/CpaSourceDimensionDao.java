package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.CpaSourceDimension;
import com.hoolai.bi.report.vo.CpaSourceDimensionVO;
import com.hoolai.dao.GenericDao;


public interface CpaSourceDimensionDao extends GenericDao<CpaSourceDimension, Long> {

	CpaSourceDimension getByCode(CpaSourceDimension dimension);

	List<CpaSourceDimension> selectList(CpaSourceDimensionVO dimensionVO);

	List<CpaSourceDimension> getUserSource(CpaSourceDimensionVO dimensionVO);

}
