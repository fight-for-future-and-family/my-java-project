package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hoolai.bi.report.dao.CostPerSourceDimensionDao;
import com.hoolai.bi.report.model.entity.CostPerSourceDimension;
import com.hoolai.bi.report.service.CostPerSourceDimensionService;
import com.hoolai.bi.report.vo.CostPerSourceDimensionVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class CostPerSourceDimensionServiceImpl extends GenericServiceImpl<CostPerSourceDimension, Long> implements CostPerSourceDimensionService {

	@Autowired
	private CostPerSourceDimensionDao entityDao;
	
	@Override
    public GenericDao<CostPerSourceDimension, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public CostPerSourceDimension getByCode(CostPerSourceDimension dimension) {
		return entityDao.getByCode(dimension);
	}
	
	private String getOrderCol(String col){
		if("0".equals(col)){
			return "source_code";
		}else {
			return "source_name";
		}
	}

	@Override
	public List<CostPerSourceDimension> selectList(CostPerSourceDimensionVO dimensionVO) {
		if(StringUtils.isEmpty(dimensionVO.getOrderCol())){
			dimensionVO.setOrderCol("order by source_code asc");
		}else{
			dimensionVO.setOrderCol("order by " + getOrderCol(dimensionVO.getOrderCol()) + " " + dimensionVO.getOrderType());
		}
		
		return entityDao.selectList(dimensionVO);
	}

	@Override
	public long selectCount(CostPerSourceDimensionVO dimensionVO) {
		try {
			return entityDao.selecCount(dimensionVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public List<CostPerSourceDimension> getUserSource(CostPerSourceDimensionVO dimensionVO) {
		return entityDao.getUserSource(dimensionVO);
	}


}
