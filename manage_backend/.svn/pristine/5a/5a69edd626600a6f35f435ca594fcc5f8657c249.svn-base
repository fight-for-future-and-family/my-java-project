package com.hoolai.manage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.CustomReportTemColumnsDao;
import com.hoolai.manage.model.CustomReportTemColumns;

@Repository
public class CustomReportTemColumnsDaoImpl extends GenericDaoImpl<CustomReportTemColumns, Long> implements CustomReportTemColumnsDao {

	@Override
	public String namespace() {
		return  CustomReportTemColumns.class.getName();
	}

	@Override
	public void deleteByTemplateId(Long id) {
		this.sqlSessionTemplate.delete(this.namespace()+".deleteByTemplateId",id);
	}

	@Override
	public List<CustomReportTemColumns> selectColumnsByTemplateId(Long id) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectListByTemplateId",id);
	}
}
