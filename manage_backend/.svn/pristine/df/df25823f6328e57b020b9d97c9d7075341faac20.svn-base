package com.hoolai.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.hoolai.manage.dao.UsersDao;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.UsersService;
import com.hoolai.manage.vo.UsersVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class UsersServiceImpl extends GenericServiceImpl<Users, Long> implements
		UsersService {

	@Autowired
	private UsersDao dao;

	@Override
	public GenericDao<Users, Long> getGenricDao() {
		return this.dao;
	}

	@Override
	public List<Users> getUsersByAuthorityId(long authId) {
		return this.dao.getUsersByAuthorityId(authId);
	}

	@Override
	public Users getUserByLoginName(UsersVO usersVO) {
		return this.dao.getUsersByLoginName(usersVO);
	}

    @Override
    public List<Users> getUserListToPage(int pageNum, int showNum) {
        return this.dao.getUserListToPage(pageNum, showNum);
    }

    @Override
    public int getUsersCount() {
        return this.dao.getUsersCount();
    }

	@Override
	public List<Users> selectList4Source(UsersVO usersVO) {
		return this.dao.selectList4Source(usersVO);
	}

}
