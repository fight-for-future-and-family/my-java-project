package com.hoolai.manage.dao;

import java.util.List;

import com.hoolai.manage.model.Groups;
import com.hoolai.manage.vo.GroupsVO;
import com.hoolai.dao.GenericDao;


public interface GroupsDao extends GenericDao<Groups, Long> {

    public List<Groups> selectAllGroups();
    
    public List<Groups> getGroupsByUserId(Long userId);
    
    public List<Groups> getGroupsByGameId(Long gameId);
}
