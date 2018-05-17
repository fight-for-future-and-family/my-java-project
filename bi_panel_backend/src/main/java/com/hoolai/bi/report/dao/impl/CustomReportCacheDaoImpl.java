package com.hoolai.bi.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.CustomReportCacheDao;
import com.hoolai.bi.report.model.entity.CustomReportCache;
import com.hoolai.bi.report.vo.CustomReportCacheVO;
import com.hoolai.dao.impl.GenericDaoImpl;


@Repository
public class CustomReportCacheDaoImpl extends GenericDaoImpl<CustomReportCache, Long> implements CustomReportCacheDao {

	
	public static String getInsertSql(){
		return "insert into task_data_cache (task_id,row_id,col_name,col_value,create_time)  values(?,?,?,?,?)";
	}
	
	public static String getDeleteSql(){
		return "delete from task_data_cache where task_id=?";
	}
	
	@Override
	public String namespace() {
		return CustomReportCache.class.getName();
	}

	@Override
	public List<String> selectAllParamsName(long taskId) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAllParamsName", taskId);
	}

	@Override
	public List<Map<String, String>> selectValuesList(CustomReportCacheVO vo) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectValuesList", vo);
	}

	@Override
	public List<CustomReportCacheVO> selectAllParamsNames(long taskId) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAllParamsNameMap", taskId);
	}

	@Override
	public int countValuesList(CustomReportCacheVO vo) {
		return this.sqlSessionTemplate.selectOne("countValuesList", vo);
	}
	
}
