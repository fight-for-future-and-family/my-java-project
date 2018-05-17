package com.hoolai.manage.dao;

import java.util.List;

import com.hoolai.manage.model.Users;
import com.hoolai.manage.vo.UsersVO;
import com.hoolai.dao.GenericDao;


public interface UsersDao extends GenericDao<Users, Long> {

	Users getUsersByLoginName(UsersVO usersVO);
	
	List<Users> getUsersByAuthorityId(long authId);
	
        List<Users> getUserListToPage(int pageNum,int showNum);
        
        int getUsersCount();

		List<Users> selectList4Source(UsersVO usersVO);
}
