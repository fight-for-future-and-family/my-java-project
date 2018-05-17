package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.dao.SysConfigDao;
import com.hoolai.manage.model.SysConfig;
import com.hoolai.manage.service.SysConfigService;
import com.hoolai.manage.vo.SysConfigVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class SysConfigServiceImpl extends GenericServiceImpl<SysConfig, Long> implements SysConfigService {
    @Autowired
    private SysConfigDao dao;

    @Override
    public GenericDao<SysConfig, Long> getGenricDao() {
            return this.dao;
    }

	@Override
	public List<SysConfig> selectGamesType(SysConfigVO sysConfigVO) {
		return this.dao.selectGamesType(sysConfigVO);
	}

	@Override
	public List<SysConfig> selectConfigByParentId(SysConfigVO sysConfigVO) {
		return this.dao.selectConfigByParentId(sysConfigVO);
	}

	@Override
	public Long selectCount(SysConfigVO sysConfigVO) {
		try {
			return this.dao.selecCount(sysConfigVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public List<SysConfig> selectGamesTypeByUserId(SysConfigVO sysConfigVO) {
		return this.dao.selectGamesTypeByUserId(sysConfigVO);
	}

	@Override
	public List<SysConfig> selectList(SysConfigVO sysConfigVO) {
		return this.dao.selectList(sysConfigVO);
	}
    
	@Override
	public int selectExists(SysConfig sysConfig) {
		int num = this.dao.selectExists(sysConfig);
		return num;
	}

	@Override
	public List<SysConfig> selectAllSysConfig() {
		return this.dao.selectAllSysConfig();
	}

}
