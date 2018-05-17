package com.hoolai.bi.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.CustomReportCacheDao;
import com.hoolai.bi.report.model.entity.CustomReportCache;
import com.hoolai.bi.report.service.CustomReportCacheService;
import com.hoolai.bi.report.vo.CustomReportCacheVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class CustomReportCacheServiceImpl extends GenericServiceImpl<CustomReportCache, Long> implements CustomReportCacheService {

	@Autowired
	private CustomReportCacheDao entityDao;
	
	@Override
    public GenericDao<CustomReportCache, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public List<String> selectAllParamsName(long taskId) {
		return this.entityDao.selectAllParamsName(taskId);
	}

	@Override
	public List<Map<String, String>> selectValuesList(long taskId,List<String> params) {
		
		StringBuffer sqlBuff = new StringBuffer("select row_id,");
		for(String param:params){
			sqlBuff.append("max(case col_name when '"+param+"' then col_value else null end) "+param+",");
		}
		sqlBuff.delete(sqlBuff.lastIndexOf(","), sqlBuff.length());
		sqlBuff.append(" from task_data_cache where task_id = "+taskId+" group by row_id");
		
		CustomReportCacheVO vo = new CustomReportCacheVO();
		System.out.println(sqlBuff.toString());
		vo.setValueSqlStr(sqlBuff.toString());
		return this.entityDao.selectValuesList(vo);
	}

	private String paramRule(String str){
		return str;
	}

	@Override
	public List<CustomReportCacheVO> selectAllParamsNames(long taskId) {
		return this.entityDao.selectAllParamsNames(taskId);
	}
	
	@Override
	public int countValuesList(long taskId, List<String> params) {
		StringBuffer sqlBuff = new StringBuffer("select count(1) from (select row_id,");
		for(String param:params){
			sqlBuff.append("max(case col_name when '"+param+"' then col_value else null end) "+param+",");
		}
		sqlBuff.delete(sqlBuff.lastIndexOf(","), sqlBuff.length());
		sqlBuff.append(" from task_data_cache where task_id = "+taskId+" group by row_id) datas");
		System.out.println(sqlBuff.toString());
		
		CustomReportCacheVO vo = new CustomReportCacheVO();
		System.out.println(sqlBuff.toString());
		vo.setValueSqlStr(sqlBuff.toString());
		return this.entityDao.countValuesList(vo);
	}

	@Override
	public List<Map<String, String>> selectValuesListCustom(long taskId,List<String> params, int beginNum) {
		StringBuffer sqlBuff = new StringBuffer("select row_id,");
		for(String param:params){
			sqlBuff.append("max(case col_name when '"+param+"' then col_value else null end) "+param+",");
		}
		sqlBuff.delete(sqlBuff.lastIndexOf(","), sqlBuff.length());
		sqlBuff.append(" from task_data_cache where task_id = "+taskId+" group by row_id limit "+beginNum+",10000");
		
		CustomReportCacheVO vo = new CustomReportCacheVO();
		System.out.println(sqlBuff.toString());
		vo.setValueSqlStr(sqlBuff.toString());
		return this.entityDao.selectValuesList(vo);
	}

}
