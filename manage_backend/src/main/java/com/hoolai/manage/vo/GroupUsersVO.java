package com.hoolai.manage.vo;

import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.model.GroupUsers;

public class GroupUsersVO extends AbstractObjectVO<GroupUsers>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4862956885373318170L;

	public GroupUsersVO() {
		super();
		this.entity=new GroupUsers();
	}

	public GroupUsersVO(GroupUsers entity) {
		super(entity);
	}

	
}
