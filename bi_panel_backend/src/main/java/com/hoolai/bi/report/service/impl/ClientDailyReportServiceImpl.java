package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.ClientDailyReportDao;
import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.service.ClientDailyReportService;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class ClientDailyReportServiceImpl extends GenericServiceImpl<ClientDailyReport, Long> implements ClientDailyReportService {

	@Autowired
	private ClientDailyReportDao entityDao;
	
	@Override
    public GenericDao<ClientDailyReport, Long> getGenricDao() {
            return this.entityDao;
    }


	@Override
	public List<String> selectGameClients(ClientDailyReportVO clientDailyReportVO) {
		return entityDao.selectGameClients(clientDailyReportVO);
	}


	@Override
	public List<ClientDailyReportVO> selectInstallForPieList(ClientDailyReportVO clientDailyReportVO) {
		return entityDao.selectInstallForPieList(clientDailyReportVO);
	}


	@Override
	public Long selectInstallForPieListCount(ClientDailyReportVO clientDailyReportVO) {
		return entityDao.selectInstallForPieListCount(clientDailyReportVO);
	}


	@Override
	public List<ClientDailyReportVO> selectInstallForDownLoad(ClientDailyReportVO clientDailyReportVO) {
		return entityDao.selectInstallForDownLoad(clientDailyReportVO);
	}


	@Override
	public List<ClientDailyReport> selectList(ClientDailyReportVO clientDailyReportVO) {
		String col = clientDailyReportVO.getOrderCol();
		String type = clientDailyReportVO.getOrderType();
		type = StringUtils.isEmpty(type) ? " desc" : type; 
		if(col == null){
			clientDailyReportVO.setOrderValue(" order by day desc");
		}else{
			clientDailyReportVO.setOrderValue(" order by "+col+" "+type);
		}
		return entityDao.selectList(clientDailyReportVO);
	}

	@Override
	public long selectCount(ClientDailyReportVO clientDailyReportVO) {
		try {
			return entityDao.selecCount(clientDailyReportVO);
		} catch (Exception e) {
			return 0;
		}
	}


	@Override
	public ClientDailyReport getClientMatch(ClientDailyReportVO clientDailyReportVO) {
		return entityDao.getClientMatch(clientDailyReportVO);
	}
}
