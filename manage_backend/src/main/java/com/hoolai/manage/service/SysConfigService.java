package com.hoolai.manage.service;

import java.util.List;

import com.hoolai.manage.model.SysConfig;
import com.hoolai.manage.vo.SysConfigVO;
import com.jian.service.GenericService;

public interface SysConfigService extends GenericService<SysConfig, Long> {

	List<SysConfig> selectGamesType(SysConfigVO sysConfigVO);
	
	List<SysConfig> selectGamesTypeByUserId(SysConfigVO sysConfigVO);
	
	List<SysConfig> selectConfigByParentId(SysConfigVO sysConfigVO);
	
	Long selectCount(SysConfigVO sysConfigVO);

	List<SysConfig> selectList(SysConfigVO sysConfigVO);
	
	int selectExists(SysConfig sysConfig);
	
	List<SysConfig> selectAllSysConfig();
	
}
