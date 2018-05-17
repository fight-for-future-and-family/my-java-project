package com.hoolai.manage.service;

import java.util.List;

import com.hoolai.manage.model.Users;
import com.hoolai.manage.vo.UsersVO;
import com.jian.service.GenericService;

public interface UsersService extends GenericService<Users, Long> {

	List<Users> getUsersByAuthorityId(long authId);
	
	Users getUserByLoginName(UsersVO usersVO);
	
	List<Users> getUserListToPage(int pageNum,int showNum);
	
	int getUsersCount();

	List<Users> selectList4Source(UsersVO usersVO);
}
