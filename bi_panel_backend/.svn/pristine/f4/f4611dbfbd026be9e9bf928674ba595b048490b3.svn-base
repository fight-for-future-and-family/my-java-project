package com.hoolai.bi.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.EquipSourceDayDao;
import com.hoolai.bi.report.model.entity.equip.EquipSourceDay;
import com.hoolai.bi.report.vo.EquipSourceDayVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class EquipSourceDayDaoImpl extends GenericDaoImpl<EquipSourceDay, Long> implements EquipSourceDayDao {

	@Override
	public String namespace() {
		return EquipSourceDay.class.getName();
	}

	@Override
	public List<EquipSourceDay> selectList(EquipSourceDayVO equipSourceDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList",equipSourceDayVO);
	}

	@Override
	public List<String> selectEquipSources(EquipSourceDayVO equipSourceDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectSourceList", equipSourceDayVO);
	}

	@Override
	public List<EquipSourceDayVO> selectInsatll4Pie(
			EquipSourceDayVO equipSourceDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectInsatll4Pie",equipSourceDayVO);
	}

	@Override
	public List<EquipSourceDayVO> selectDau4Pie(
			EquipSourceDayVO equipSourceDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectDau4Pie",equipSourceDayVO);
	}

	@Override
	public List<EquipSourceDayVO> selectNewEquip4Pie(
			EquipSourceDayVO equipSourceDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectNewEquip4Pie",equipSourceDayVO);
	}

	@Override
	public List<Map<String,String>> selectDau4Bar(EquipSourceDayVO equipSourceDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectDau4Bar",equipSourceDayVO);
	}

	@Override
	public List<Map<String,String>> selectNewEquip4Line(
			EquipSourceDayVO equipSourceDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectNewEquip4Line",equipSourceDayVO);
	}

	@Override
	public List<Map<String,Object>> selectNewEquipRate4Line(
			EquipSourceDayVO equipSourceDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectNewEquipRate4Line",equipSourceDayVO);
	}

	
}
