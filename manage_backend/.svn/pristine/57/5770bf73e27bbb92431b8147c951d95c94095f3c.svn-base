package com.hoolai.manage.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.UsersDao;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.vo.UsersVO;

@Repository
public class UsersDaoImpl extends GenericDaoImpl<Users, Long> implements UsersDao {

	@Override
	public String namespace() {
		return Users.class.getName();
	}

	@Override
	public List<Users> getUsersByAuthorityId(long authId) {
		try {
			UsersVO queryVO=new UsersVO();
			queryVO.setAuthoritiesId(authId);
			return this.sqlSessionTemplate.selectList(this.namespace()+".getUsersByAuthorityId", queryVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@Override
	public Users getUsersByLoginName(UsersVO usersVO) {
		return this.sqlSessionTemplate.selectOne(this.namespace()+".getUsersByloginName", usersVO);
	}

    @Override
    public List<Users> getUserListToPage(int pageNum, int showNum) {
        Map<String,Object> pageVO = new HashMap<String,Object>();
        pageVO.put("pageNum", pageNum);
        pageVO.put("showNum", showNum);
       // return this.sqlSessionTemplate.selectList(this.namespace()+".getUserListToPage", pageVO);
        return null;
    }

    @Override
    public int getUsersCount() {
        return 0;
    }

	@Override
	public List<Users> selectList4Source(UsersVO usersVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList4Source", usersVO);
	}

	
}
