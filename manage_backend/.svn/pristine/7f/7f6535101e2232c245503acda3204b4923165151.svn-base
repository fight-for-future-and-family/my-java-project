package com.hoolai.manage.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.GroupsDao;
import com.hoolai.manage.model.Groups;
import com.hoolai.manage.vo.GroupsVO;

@Repository
public class GroupsDaoImpl extends GenericDaoImpl<Groups, Long> implements GroupsDao {

	@Override
	public String namespace() {
		return Groups.class.getName();
	}

    @Override
    public List<Groups> selectAllGroups() {
        return this.sqlSessionTemplate.selectList(this.namespace()+".selectAllList");
    }

    @Override
	public List<Groups> getGroupsByUserId(Long userId) {
		try {
			return this.sqlSessionTemplate.selectList(this.namespace()+".getGroupsByUserId", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@Override
	public List<Groups> getGroupsByGameId(Long gameId) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".getGroupsByGameId", gameId);
	}
	
}
