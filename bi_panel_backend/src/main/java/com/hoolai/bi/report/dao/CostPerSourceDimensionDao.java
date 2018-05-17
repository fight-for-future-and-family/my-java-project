package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.CostPerSourceDimension;
import com.hoolai.bi.report.vo.CostPerSourceDimensionVO;
import com.hoolai.dao.GenericDao;


public interface CostPerSourceDimensionDao extends GenericDao<CostPerSourceDimension, Long> {

	CostPerSourceDimension getByCode(CostPerSourceDimension dimension);

	List<CostPerSourceDimension> selectList(CostPerSourceDimensionVO dimensionVO);

	List<CostPerSourceDimension> getUserSource(CostPerSourceDimensionVO dimensionVO);

}
