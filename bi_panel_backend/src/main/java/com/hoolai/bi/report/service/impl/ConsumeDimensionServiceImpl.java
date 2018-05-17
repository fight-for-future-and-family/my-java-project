package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.ConsumeDimensionDao;
import com.hoolai.bi.report.model.entity.ConsumeDimension;
import com.hoolai.bi.report.service.ConsumeDimensionService;
import com.hoolai.bi.report.vo.ConsumeDimensionVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class ConsumeDimensionServiceImpl extends GenericServiceImpl<ConsumeDimension, Long> implements ConsumeDimensionService {
	
	@Autowired
	private ConsumeDimensionDao entityDao;

	@Override
	public GenericDao<ConsumeDimension, Long> getGenricDao() {
		return this.entityDao;
	}

	@Override
	public List<ConsumeDimension> selectList(ConsumeDimensionVO consumeDimensionVO) {
		
		String col = consumeDimensionVO.getOrderCol();
		if(col == null){
			consumeDimensionVO.setOrderValue(" order by update_time desc");
		}else{
			if("0".equals(col)){
				consumeDimensionVO.setOrderValue(" order by consume_code "+consumeDimensionVO.getOrderType());
			}else{
				consumeDimensionVO.setOrderValue(" order by consume_name "+consumeDimensionVO.getOrderType());
			}
			
		}
		
		return this.entityDao.selectList(consumeDimensionVO);
	}

	@Override
	public ConsumeDimension getByGameId(ConsumeDimension consumeDimension) {
		return this.entityDao.getByGameId(consumeDimension);
	}

	@Override
	public Long selectCount(ConsumeDimensionVO consumeDimensionVO) {
		try {
			return this.entityDao.selecCount(consumeDimensionVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

}
