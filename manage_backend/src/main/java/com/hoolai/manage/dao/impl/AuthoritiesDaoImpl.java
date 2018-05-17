package com.hoolai.manage.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.manage.dao.AuthoritiesDao;
import com.hoolai.manage.model.Authorities;
import com.hoolai.manage.vo.AuthoritiesVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class AuthoritiesDaoImpl extends GenericDaoImpl<Authorities, Long> implements AuthoritiesDao {

	@Override
	public String namespace() {
		return Authorities.class.getName();
	}

	@Override
	public List<Authorities> getAuthoritiesByUserId(Long uId) {
		try {
			AuthoritiesVO queryVO=new AuthoritiesVO();
			queryVO.setUserId(uId);
			return this.sqlSessionTemplate.selectList(this.namespace()+".getAuthoritiesByUserId", queryVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@Override
	public List<Authorities> getAuthoritiesByResourcesId(Long resourceId) {
		try {
			AuthoritiesVO queryVO=new AuthoritiesVO();
			queryVO.setResourceId(resourceId);;
			return this.sqlSessionTemplate.selectList(this.namespace()+".getAuthoritiesByResourcesId", queryVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@Override
	public List<Authorities> getAllAuthorities() {
		try {
			return this.sqlSessionTemplate.selectList(this.namespace()+".getAllAuthorities", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	
}
