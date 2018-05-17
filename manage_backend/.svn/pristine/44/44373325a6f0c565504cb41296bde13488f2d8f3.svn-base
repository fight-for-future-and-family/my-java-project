package com.hoolai.manage.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.dao.EtlEngineCleanupDao;
import com.hoolai.manage.model.EtlEngineCleanup;
import com.hoolai.manage.service.EtlEngineCleanupService;
import com.hoolai.manage.util.CleanupSqlUtil;
import com.hoolai.manage.util.DateUtil;
import com.jian.service.impl.GenericServiceImpl;

/** 
 * <b>类说明：</b>
 * <blockquote>
 *   
 * </blockquote> 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年11月17日 下午8:32:29  
 */

@Service
public class EtlEngineCleanupServiceImpl extends GenericServiceImpl<EtlEngineCleanup, String>
		implements EtlEngineCleanupService {

	
	@Autowired
	private EtlEngineCleanupDao etlEngineCleanupDao;

	@Override
	public GenericDao<EtlEngineCleanup, String> getGenricDao() {
		return this.etlEngineCleanupDao;
	}

	

	public EtlEngineCleanupDao getEtlEngineCleanupDao() {
		return etlEngineCleanupDao;
	}

	public void setEtlEngineCleanupDao(EtlEngineCleanupDao etlEngineCleanupDao) {
		this.etlEngineCleanupDao = etlEngineCleanupDao;
	}

	@Override
	public Map<String, List<EtlEngineCleanup>> executeHiveQL(
			List<EtlEngineCleanup> validGameList, String beginDate,
			String endDate, String fileName) {
		
		String[] dsArray = DateUtil.getDayList(beginDate, endDate);
		if(dsArray.length > 7){
			Map<String, List<EtlEngineCleanup>> resultMap = new HashMap<String, List<EtlEngineCleanup>>();
			resultMap.put("2", new ArrayList<EtlEngineCleanup>());
			return resultMap;
		}
		
		//读文件获取要执行的sql，因为执行频率不高，所有每次都从文件中读取，不放到缓存中，如果sql有变化，可以直接修改配置文件即可，不用重启服务。
		
		
		
		List<String> hiveQLs = CleanupSqlUtil.getHiveQLList(fileName);
		
		List<EtlEngineCleanup> etlEngineCleanups = new ArrayList<EtlEngineCleanup>();
		
		
		
		for(EtlEngineCleanup etlEngineCleanup : validGameList){
			for(String ds : dsArray){
				etlEngineCleanup.setDs(ds);
				etlEngineCleanups.add(new EtlEngineCleanup(etlEngineCleanup));
			}
		}
		
		return this.etlEngineCleanupDao.executeHiveQL(hiveQLs, etlEngineCleanups);
	}


	@Override
	public Map<String, List<EtlEngineCleanup>> executeMySql(List<EtlEngineCleanup> validGameList, String beginDate, String endDate, String sqlFileName) {
		String[] dsArray = DateUtil.getDayList(beginDate, endDate);
		if(dsArray.length > 7){
			Map<String, List<EtlEngineCleanup>> resultMap = new HashMap<String, List<EtlEngineCleanup>>();
			resultMap.put("2", new ArrayList<EtlEngineCleanup>());
			return resultMap;
		}
		
		//读文件获取要执行的sql，因为执行频率不高，所有每次都从文件中读取，不放到缓存中，如果sql有变化，可以直接修改配置文件即可，不用重启服务。
		
		
		
		List<String> hiveQLs = CleanupSqlUtil.getHiveQLList(sqlFileName);
		
		List<EtlEngineCleanup> etlEngineCleanups = new ArrayList<EtlEngineCleanup>();
		
		
		
		for(EtlEngineCleanup etlEngineCleanup : validGameList){
			for(String ds : dsArray){
				etlEngineCleanup.setDs(ds);
				etlEngineCleanups.add(new EtlEngineCleanup(etlEngineCleanup));
			}
		}
		
		return this.etlEngineCleanupDao.executeMysql(hiveQLs, etlEngineCleanups);
	}

	
	
	
}
