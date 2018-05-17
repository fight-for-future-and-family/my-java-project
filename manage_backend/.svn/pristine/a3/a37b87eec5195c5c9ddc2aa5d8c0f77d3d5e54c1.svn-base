package com.hoolai.manage.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.ResourcesDao;
import com.hoolai.manage.model.Resources;
import com.hoolai.manage.vo.ResourcesVO;

@Repository
public class ResourcesDaoImpl extends GenericDaoImpl< Resources, Long> implements  ResourcesDao {

	@Override
	public String namespace() {
		return  Resources.class.getName();
	}

	@Override
	public List<Resources> getResourcesByAuthorityId(Long authId) {
		try {
			ResourcesVO queryVO=new ResourcesVO();
			queryVO.setAuthoritiesId(authId);
			return this.sqlSessionTemplate.selectList(this.namespace()+".getResourcesByAuthorityId", queryVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@Override
	public List<Resources> getResourcesByUserId(Long userId) {
		try {
			return this.sqlSessionTemplate.selectList(this.namespace()+".selectUserAuth", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	
}
