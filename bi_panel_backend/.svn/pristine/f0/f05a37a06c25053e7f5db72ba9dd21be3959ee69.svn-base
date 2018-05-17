package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.EquipDayDao;
import com.hoolai.bi.report.model.entity.equip.EquipDay;
import com.hoolai.bi.report.vo.EquipDayVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class EquipDayDaoImpl extends GenericDaoImpl<EquipDay, Long> implements EquipDayDao {

	@Override
	public String namespace() {
		return EquipDay.class.getName();
	}

	@Override
	public List<EquipDay> selectList(EquipDayVO equipDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList",equipDayVO);
	}

	
}
