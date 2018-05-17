package com.hoolai.manage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.SysConfigDao;
import com.hoolai.manage.model.SysConfig;
import com.hoolai.manage.vo.SysConfigVO;

@Repository
public class SysConfigDaoImpl extends GenericDaoImpl<SysConfig, Long> implements SysConfigDao {

	@Override
	public String namespace() {
		return SysConfig.class.getName();
	}

	@Override
	public List<SysConfig> selectGamesType(SysConfigVO sysConfigVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGamesType", sysConfigVO);
	}

	@Override
	public List<SysConfig> selectConfigByParentId(SysConfigVO sysConfigVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectConfigByParentId", sysConfigVO);
	}

	@Override
	public List<SysConfig> selectGamesTypeByUserId(SysConfigVO sysConfigVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectGamesTypeByUserId", sysConfigVO);
	}

	@Override
	public List<SysConfig> selectList(SysConfigVO sysConfigVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", sysConfigVO);
	}

	@Override
	public int selectExists(SysConfig sysConfig) {
		int num = this.sqlSessionTemplate.selectOne("selectGameExists", sysConfig);
		return num;
	}

	@Override
	public List<SysConfig> selectAllSysConfig() {
		return this.sqlSessionTemplate.selectList("selectAllSysConfig");
	}
	
}
