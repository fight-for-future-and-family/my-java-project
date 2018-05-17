package com.hoolai.manage.vo;

import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.model.AuthorityGroup;

public class AuthorityGroupVO extends AbstractObjectVO<AuthorityGroup>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4862956885373318170L;

	public AuthorityGroupVO() {
		super();
		this.entity=new AuthorityGroup();
	}

	public AuthorityGroupVO(AuthorityGroup entity) {
		super(entity);
	}

	
}
