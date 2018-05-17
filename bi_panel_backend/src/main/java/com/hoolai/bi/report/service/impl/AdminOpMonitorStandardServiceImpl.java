package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.AdminOpMonitorStandardDao;
import com.hoolai.bi.report.model.entity.AdminOpMonitorStandard;
import com.hoolai.bi.report.service.AdminOpMonitorStandardService;
import com.hoolai.bi.report.vo.AdminOpMonitorStandardVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class AdminOpMonitorStandardServiceImpl extends GenericServiceImpl<AdminOpMonitorStandard, Long> implements AdminOpMonitorStandardService {

	@Autowired
	private AdminOpMonitorStandardDao entityDao;
	
	@Override
    public GenericDao<AdminOpMonitorStandard, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public AdminOpMonitorStandard getByCode(AdminOpMonitorStandard monitor) {
		AdminOpMonitorStandardVO standardVO = new AdminOpMonitorStandardVO(monitor);
		return this.entityDao.getByCode(standardVO);
	}

	@Override
	public Long selectCount(AdminOpMonitorStandardVO monitorStandardVO) {
		try {
			return this.entityDao.selecCount(monitorStandardVO);
		} catch (Exception e) {
			return 0l;
		}
	}

	@Override
	public List<AdminOpMonitorStandard> selectList(AdminOpMonitorStandardVO monitorStandardVO) {
		String col = getOrderColumn(monitorStandardVO.getOrderCol());
		if(col == null){
			monitorStandardVO.setOrderValue(" order by op_date desc");
		}else{
			monitorStandardVO.setOrderValue(" order by "+col+" "+monitorStandardVO.getOrderType());
		}
		
		return this.entityDao.selectList(monitorStandardVO);
	}

	private String getOrderColumn(String col) {
		if(StringUtils.isEmpty(col)){
			return null;
		}
		int colInt = Integer.valueOf(col);
		switch(colInt){
		case 0:
			return null;
		case 1:
			return "clientid";
		case 2:
		case 3:
		case 4:
		case 5:
			return null;
		case 6:
			return "item_num";
		case 7:
		case 8:
			return null;
		case 9:
			return "op_date";
		case 10:
			return null;
			default:
				return "op_date";
			
		}
	}

}
