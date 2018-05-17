package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.WhaleUserDao;
import com.hoolai.bi.report.model.entity.whaleUser.WhaleUser;
import com.hoolai.bi.report.service.WhaleUserService;
import com.hoolai.bi.report.vo.WhaleUserVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class WhaleUserServiceImpl extends GenericServiceImpl<WhaleUser, Long> implements WhaleUserService {

	@Autowired
	private WhaleUserDao entityDao;
	
	@Override
    public GenericDao<WhaleUser, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Long selectCount(WhaleUserVO WhaleUserVO) {
		try {
			return this.entityDao.selecCount(WhaleUserVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}

	@Override
	public List<WhaleUser> selectList(WhaleUserVO WhaleUserVO) {
		String col = WhaleUserVO.getOrderCol();
		if(col == null){
			WhaleUserVO.setOrderValue(" order by total_pay_amount desc");
		}else{
			WhaleUserVO.setOrderValue(" order by "+col+" "+WhaleUserVO.getOrderType());
		}
		return this.entityDao.selectList(WhaleUserVO);
	}

	@Override
	public WhaleUser selectMaxDs(WhaleUserVO WhaleUserVO) {
		try{
			WhaleUser whaleUser = this.entityDao.selectMaxDs(WhaleUserVO);
			if(whaleUser == null){
				return new WhaleUser();
			}
			return whaleUser;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return new WhaleUser();
		}
	}

}
