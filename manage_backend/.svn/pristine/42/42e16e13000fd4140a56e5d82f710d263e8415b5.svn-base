package com.hoolai.manage.dao;

import java.util.List;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.model.SysConfig;
import com.hoolai.manage.vo.SysConfigVO;


public interface SysConfigDao extends GenericDao<SysConfig, Long> {

	List<SysConfig> selectGamesType(SysConfigVO sysConfigVO);

	List<SysConfig> selectConfigByParentId(SysConfigVO sysConfigVO);

	List<SysConfig> selectGamesTypeByUserId(SysConfigVO sysConfigVO);

	List<SysConfig> selectList(SysConfigVO sysConfigVO);
	
	int selectExists(SysConfig sysConfig);
	
	List<SysConfig> selectAllSysConfig();

}
