package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.EquipRetentionLtvDao;
import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipRetentionLtv;
import com.hoolai.bi.report.vo.EquipRetentionLtvVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class EquipRetentionLtvDaoImpl extends GenericDaoImpl<EquipRetentionLtv, Long> implements EquipRetentionLtvDao {

	@Override
	public String namespace() {
		return EquipRetentionLtv.class.getName();
	}

	@Override
	public List<EquipLtv> selectList(EquipRetentionLtvVO retentionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList", retentionVO);
	}

	@Override
	public List<EquipRetentionLtvVO> selectAvgRetentionDataList(
			EquipRetentionLtvVO retentionVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectAvgRetentionDataList", retentionVO);
	}

	
}
