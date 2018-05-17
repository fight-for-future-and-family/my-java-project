package com.hoolai.manage.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.hoolai.dao.impl.GenericDaoImpl;
import com.hoolai.manage.dao.TblGroupDao;
import com.hoolai.manage.model.TblGroup;
import com.hoolai.manage.vo.TblGroupVO;

@Repository
public class TblGroupDaoImpl extends GenericDaoImpl<TblGroup, Long> implements TblGroupDao {

	@Override
	public String namespace() {
		return TblGroup.class.getName();
	}

	@Autowired
	@Qualifier("oldReportSqlSessionTemplate")
	protected SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public SqlSessionTemplate getSqlSessionTemplate() {
		if(this.sqlSessionTemplate==null){
			throw new RuntimeException("sqlSessionTemplate not to init!");
		}
		return sqlSessionTemplate;
	}

	@Override
	public Long getGroupMaxId() {
		return sqlSessionTemplate.selectOne(this.namespace()+".getGroupMaxId");
	}

	@Override
	public TblGroupVO getOldReportGameInfo(TblGroupVO games) {
		return sqlSessionTemplate.selectOne(this.namespace()+".getOldReportGameInfo", games);
	}
	
}
