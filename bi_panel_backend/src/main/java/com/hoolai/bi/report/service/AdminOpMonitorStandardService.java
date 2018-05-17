package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.AdminOpMonitorStandard;
import com.hoolai.bi.report.vo.AdminOpMonitorStandardVO;
import com.jian.service.GenericService;

public interface AdminOpMonitorStandardService extends GenericService<AdminOpMonitorStandard, Long>{

	AdminOpMonitorStandard getByCode(AdminOpMonitorStandard monitor);

	Long selectCount(AdminOpMonitorStandardVO monitorStandardVO);

	List<AdminOpMonitorStandard> selectList(AdminOpMonitorStandardVO monitorStandardVO);

}
