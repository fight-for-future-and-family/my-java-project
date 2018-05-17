package com.hoolai.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.dao.GroupsDao;
import com.hoolai.manage.model.Groups;
import com.hoolai.manage.service.GroupsService;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class GroupsServiceImpl extends GenericServiceImpl<Groups, Long> implements
		GroupsService {

	@Autowired
	private GroupsDao dao;

	@Override
	public GenericDao<Groups, Long> getGenricDao() {
		return this.dao;
	}

    @Override
    public List<Groups> selectAllGroups() {
        return dao.selectAllGroups();
    }

    @Override
	public List<Groups> getGroupsByUserId(Long userId) {
		return this.dao.getGroupsByUserId(userId);
	}

	@Override
	public List<Groups> getGroupsByGameId(Long gameId) {
		return this.dao.getGroupsByGameId(gameId);
	}

	@Override
	public List<Groups> selectGameGroups() {
		List<Groups> groups = this.selectAllGroups();
		List<Groups> gameGroups = new ArrayList<Groups>();
		for(Groups group:groups){
			if(group.getId() == Groups.ADMIN_GROUP || group.getId() == Groups.BOSS_GROUP || 
					group.getId()== Groups.PM_GROUP || group.getId()== Groups.WANDA_GROUP){
				gameGroups.add(group);
			}
		}
		return gameGroups;
	}

}
