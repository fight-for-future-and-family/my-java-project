package com.hoolai.manage.dao;

import com.hoolai.dao.GenericDao;
import com.hoolai.manage.model.OldReportUser;
import com.hoolai.manage.vo.OldReportUserVO;


public interface OldReportUserDao extends GenericDao<OldReportUser, Long> {

	OldReportUser getUserByEmail(OldReportUserVO oldReportUserVO);

	Long getMaxUserId();

	void removeByEmail(String email);

}
