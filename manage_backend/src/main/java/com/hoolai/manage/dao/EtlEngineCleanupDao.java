package com.hoolai.manage.dao;

import java.util.List;
import java.util.Map;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.model.EtlEngineCleanup;

/** 
 * <b>类说明：</b>
 * <blockquote>
 *   
 * </blockquote> 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年11月17日 下午6:24:19  
 */

public interface EtlEngineCleanupDao extends GenericDao<EtlEngineCleanup, String>{
	
	public Map<String, List<EtlEngineCleanup>> executeHiveQL(List<String> hiveQL, List<EtlEngineCleanup> etlEngineCleanups);

	public Map<String, List<EtlEngineCleanup>> executeMysql(List<String> SQLs, List<EtlEngineCleanup> etlEngineCleanups);

}
