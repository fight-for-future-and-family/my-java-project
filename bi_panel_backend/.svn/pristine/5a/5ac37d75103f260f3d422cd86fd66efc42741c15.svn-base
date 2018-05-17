package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.EquipModelRetentionLtvDao;
import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipModelRetentionLtv;
import com.hoolai.bi.report.vo.EquipModelRetentionLtvVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class EquipModelRetentionLtvImpl extends GenericDaoImpl<EquipModelRetentionLtv, Long> implements EquipModelRetentionLtvDao {

	@Override
	public String namespace() {
		return EquipModelRetentionLtv.class.getName();
	}

	@Override
	public List<EquipLtv> selectList(EquipModelRetentionLtvVO modelRetentionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", modelRetentionVO);
	}
	
}
