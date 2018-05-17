package com.hoolai.bi.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.EquipVersionDauDao;
import com.hoolai.bi.report.model.entity.equip.EquipVersionDau;
import com.hoolai.bi.report.model.entity.equip.EquipVersionDauResult;
import com.hoolai.bi.report.vo.EquipVersionDauVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class EquipVersionDauDaoImpl extends GenericDaoImpl<EquipVersionDau, Long> implements EquipVersionDauDao {

	@Override
	public String namespace() {
		return EquipVersionDau.class.getName();
	}

	@Override
	public List<EquipVersionDauResult> selectList(EquipVersionDauVO equipVersionDauVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList",equipVersionDauVO);
	}

	@Override
	public List<String> selectTop5Version(EquipVersionDauVO equipVersionDauVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectTop5Version",equipVersionDauVO);
	}

}
