package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.AdminOpMonitorStandard;
import com.hoolai.bi.report.vo.AdminOpMonitorStandardVO;
import com.hoolai.dao.GenericDao;


public interface AdminOpMonitorStandardDao extends GenericDao<AdminOpMonitorStandard, Long> {

	AdminOpMonitorStandard getByCode(AdminOpMonitorStandardVO standardVO);

	List<AdminOpMonitorStandard> selectList(AdminOpMonitorStandardVO monitorStandardVO);

}
