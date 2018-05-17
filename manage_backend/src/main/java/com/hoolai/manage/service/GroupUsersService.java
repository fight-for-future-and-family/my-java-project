package com.hoolai.manage.service;

import java.util.List;

import com.hoolai.manage.model.GroupUsers;
import com.jian.service.GenericService;

public interface GroupUsersService extends GenericService< GroupUsers, Long> {

	public boolean isAdmin(Long userId);

	public int removeByUserId(Long id);
	
	public List<Long> getUsersByGroupId(long groupId);

	boolean isLeader(Long userId);
}
