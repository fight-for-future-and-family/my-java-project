package com.hoolai.manage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.CustomReportTemParamsDao;
import com.hoolai.manage.model.CustomReportTemParams;

@Repository
public class CustomReportTemParamsDaoImpl extends GenericDaoImpl<CustomReportTemParams, Long> implements CustomReportTemParamsDao {

	@Override
	public String namespace() {
		return  CustomReportTemParams.class.getName();
	}

	@Override
	public List<CustomReportTemParams> selectListByTemplateId(Long templateId) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectListByTemplateId", templateId);
	}

	@Override
	public void deleteByTemplateId(Long id) {
		 this.sqlSessionTemplate.delete(this.namespace()+".deleteByTemplateId", id);
	}

	


	
}
