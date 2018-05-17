package com.hoolai.manage.service;

import java.util.List;
import java.util.Map;

import com.hoolai.manage.model.EtlEngineCleanup;
import com.jian.service.GenericService;

/** 
 * <b>类说明：</b>
 * <blockquote>
 *   
 * </blockquote> 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年11月17日 下午8:31:16  
 */

public interface EtlEngineCleanupService extends GenericService<EtlEngineCleanup, String> {

	public Map<String, List<EtlEngineCleanup>> executeHiveQL(List<EtlEngineCleanup> validGameList, String beginDate,
			String endDate, String fileName);

	public Map<String, List<EtlEngineCleanup>> executeMySql(List<EtlEngineCleanup> validGameList, String beginDate, String endDate, String sqlFileName);
}
