package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.GroupUsersDao;
import com.hoolai.manage.model.GroupUsers;
import com.hoolai.manage.model.Groups;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.manage.vo.GroupUsersVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class GroupUsersServiceImpl extends GenericServiceImpl<GroupUsers, Long> implements
		GroupUsersService {

	@Autowired
	private GroupUsersDao dao;

	@Override
	public GenericDao<GroupUsers, Long> getGenricDao() {
		return this.dao;
	}
	
	@Override
	public boolean isAdmin(Long userId){
		try {
			GroupUsers groupUsers = new GroupUsers();
			groupUsers.setGroupId(Groups.ADMIN_GROUP);
			groupUsers.setUserId(userId);
			GroupUsersVO query = new GroupUsersVO(groupUsers);
			return this.dao.isExist(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean isLeader(Long userId){
		try {
			GroupUsers groupUsers = new GroupUsers();
			groupUsers.setGroupId(Groups.ADMIN_GROUP);
			groupUsers.setUserId(userId);
			GroupUsersVO query = new GroupUsersVO(groupUsers);
			boolean isPm = this.dao.isExist(query);
			
			query.getEntity().setGroupId(Groups.BOSS_GROUP);
			boolean isBoss = this.dao.isExist(query);
			
			return isPm || isBoss;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int removeByUserId(Long userId) {
		try {
			GroupUsers groupUsers = new GroupUsers();
			groupUsers.setUserId(userId);
			GroupUsersVO query = new GroupUsersVO(groupUsers);
			return this.dao.removeByUserId(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Long> getUsersByGroupId(long groupId) {
		return this.dao.getUsersByGroupId(groupId);
	}

}
