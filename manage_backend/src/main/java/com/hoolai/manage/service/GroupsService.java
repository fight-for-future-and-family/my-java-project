package com.hoolai.manage.service;

import java.util.List;

import com.hoolai.manage.model.Groups;
import com.jian.service.GenericService;

public interface GroupsService extends GenericService<Groups, Long> {
    
    public List<Groups> selectAllGroups();
    
    public List<Groups> getGroupsByUserId(Long userId);

    public List<Groups> getGroupsByGameId(Long gameId);

	public List<Groups> selectGameGroups();
}
