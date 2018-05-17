package com.hoolai.manage.dao;

import java.util.List;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.model.GroupUsers;
import com.hoolai.manage.vo.GroupUsersVO;


public interface GroupUsersDao extends GenericDao<GroupUsers, Long> {

	int removeByUserId(GroupUsersVO groupUsers);

	List<Long> getUsersByGroupId(long groupId);

}
