package com.hoolai.manage.vo;

import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.model.Authorities;

public class AuthoritiesVO extends AbstractObjectVO<Authorities>{
	
	private static final long serialVersionUID = -4862956885373318170L;
	
	private Long userId;
	
	private Long resourceId;
	
	public AuthoritiesVO() {
		super();
		this.entity=new Authorities();
	}

	public AuthoritiesVO(Authorities entity) {
		super(entity);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	

	
}
