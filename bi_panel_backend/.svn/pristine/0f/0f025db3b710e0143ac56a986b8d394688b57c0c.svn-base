package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hoolai.bi.report.dao.CpaSourceDimensionDao;
import com.hoolai.bi.report.model.entity.CpaSourceDimension;
import com.hoolai.bi.report.service.CpaSourceDimensionService;
import com.hoolai.bi.report.vo.CpaSourceDimensionVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class CpaSourceDimensionServiceImpl extends GenericServiceImpl<CpaSourceDimension, Long> implements CpaSourceDimensionService {

	@Autowired
	private CpaSourceDimensionDao entityDao;
	
	@Override
    public GenericDao<CpaSourceDimension, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public CpaSourceDimension getByCode(CpaSourceDimension dimension) {
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
	public List<CpaSourceDimension> selectList(CpaSourceDimensionVO dimensionVO) {
		if(StringUtils.isEmpty(dimensionVO.getOrderCol())){
			dimensionVO.setOrderCol("order by source_code asc");
		}else{
			dimensionVO.setOrderCol("order by " + getOrderCol(dimensionVO.getOrderCol()) + " " + dimensionVO.getOrderType());
		}
		
		return entityDao.selectList(dimensionVO);
	}

	@Override
	public long selectCount(CpaSourceDimensionVO dimensionVO) {
		try {
			return entityDao.selecCount(dimensionVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public List<CpaSourceDimension> getUserSource(CpaSourceDimensionVO dimensionVO) {
		return entityDao.getUserSource(dimensionVO);
	}


}
