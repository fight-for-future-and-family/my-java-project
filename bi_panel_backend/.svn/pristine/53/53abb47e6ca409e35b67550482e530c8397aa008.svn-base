package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.RealTimeGameClientidDao;
import com.hoolai.bi.report.dao.RealTimeGamesDao;
import com.hoolai.bi.report.model.entity.RealTimeGame;
import com.hoolai.bi.report.model.entity.RealTimeGameClient;
import com.hoolai.bi.report.service.RealTimeGameClientidService;
import com.hoolai.bi.report.vo.RealTimeGameClientVo;
import com.hoolai.bi.report.vo.RealTimeGameVo;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.Pagination;
import com.jian.service.impl.GenericServiceImpl;
@Service
public class RealTimeGameClientidServiceImpl extends GenericServiceImpl<RealTimeGameClient, Long> implements
		RealTimeGameClientidService {

	
	@Autowired
	private RealTimeGameClientidDao entityDao;
	
	@Override
    public GenericDao<RealTimeGameClient, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public int saveRealTimeClientidDataList(
			List<RealTimeGameClient> realtTimeGameClientid) {
		return entityDao.saveRealTimeClientidDataList(realtTimeGameClientid);
	}

	@Override
	public List<RealTimeGameClientVo> getSelectGamesClientidList(
			RealTimeGameClientVo realTimeGameClientidVo) {
		
		Pagination pagination = new Pagination();
		pagination.setPageOffset(realTimeGameClientidVo.getOffset());
		pagination.setPageSize(Long.valueOf(realTimeGameClientidVo.getRows()).intValue());
		
		if(!StringUtils.isEmpty(realTimeGameClientidVo.getOrderCol())){
			realTimeGameClientidVo.setOrderValue("order by "+realTimeGameClientidVo.getOrderCol() + " " +realTimeGameClientidVo.getOrderType());
		}else{
			realTimeGameClientidVo.setOrderValue("order by finish_time desc");
		}
		
		return entityDao.getSelectGamesClientidList(realTimeGameClientidVo);
	}

	@Override
	public long getSelectGamesClientidCount(
			RealTimeGameClientVo realTimeGameClientidVo) {
		return entityDao.getSelectGamesClientidCount(realTimeGameClientidVo);
		
	}

	@Override
	public void deleteRealTimeClientData(RealTimeGameClientVo realTimeGameClientidVo) {
		 entityDao.deleteRealTimeClientData(realTimeGameClientidVo);
	}

	@Override
	public long getSelectCountdata(RealTimeGameClientVo realTimeGameClientidVo) {
		return entityDao.getSelectCountdata(realTimeGameClientidVo);
	}

	@Override
	public List<String> selectGameClients(
			RealTimeGameClientVo realTimeGameClientidVo) {
		
		return entityDao.selectGameClients(realTimeGameClientidVo);
	}
	
	

}
