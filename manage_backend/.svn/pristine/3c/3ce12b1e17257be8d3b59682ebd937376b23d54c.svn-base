package com.hoolai.manage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.GroupUsersDao;
import com.hoolai.manage.model.GroupUsers;
import com.hoolai.manage.vo.GroupUsersVO;

@Repository
public class GroupUsersDaoImpl extends GenericDaoImpl<GroupUsers, Long> implements GroupUsersDao {

	@Override
	public String namespace() {
		return GroupUsers.class.getName();
	}

	@Override
	public int removeByUserId(GroupUsersVO groupUsers) {
		return this.sqlSessionTemplate.delete(this.namespace()+".removeByUserId", groupUsers);
	}

	@Override
	public List<Long> getUsersByGroupId(long groupId) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".getUsersByGroupId", groupId);
	}

	
}
