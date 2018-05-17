package com.hoolai.bi.report.dao;

import java.util.List;
import java.util.Map;

import com.hoolai.bi.report.model.entity.CustomReportCache;
import com.hoolai.bi.report.vo.CustomReportCacheVO;
import com.hoolai.dao.GenericDao;

public interface CustomReportCacheDao extends GenericDao<CustomReportCache, Long> {

	List<String> selectAllParamsName(long taskId);

	List<Map<String, String>> selectValuesList(CustomReportCacheVO vo);

	List<CustomReportCacheVO> selectAllParamsNames(long taskId);

	int countValuesList(CustomReportCacheVO vo);

}
