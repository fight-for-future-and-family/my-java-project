package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.EquipSourceRetentionLtvDao;
import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipSourceRetentionLtv;
import com.hoolai.bi.report.vo.EquipSourceRetentionLtvVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class EquipSourceRetentionLtvDaoImpl extends GenericDaoImpl<EquipSourceRetentionLtv, Long> implements EquipSourceRetentionLtvDao {

	@Override
	public String namespace() {
		return EquipSourceRetentionLtv.class.getName();
	}

	@Override
	public List<EquipLtv> selectList(EquipSourceRetentionLtvVO sourceRetentionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", sourceRetentionVO);
	}
}
