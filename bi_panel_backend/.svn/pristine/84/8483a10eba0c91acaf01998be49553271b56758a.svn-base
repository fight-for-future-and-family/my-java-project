package com.hoolai.bi.report.service;


import java.util.List;
import java.util.Map;

import com.hoolai.bi.report.model.entity.CustomReportCache;
import com.hoolai.bi.report.vo.CustomReportCacheVO;
import com.jian.service.GenericService;

public interface CustomReportCacheService extends GenericService<CustomReportCache, Long>{

	List<String> selectAllParamsName(long taskId);

	List<Map<String, String>> selectValuesList(long taskId, List<String> params);

	List<CustomReportCacheVO> selectAllParamsNames(long taskId);

	int countValuesList(long taskId, List<String> params);

	List<Map<String, String>> selectValuesListCustom(long taskId,
			List<String> paramCodes, int beginNum);

}
