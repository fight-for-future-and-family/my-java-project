package com.hoolai.bi.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hoolai.bi.report.dao.EquipModelDayDao;
import com.hoolai.bi.report.model.entity.equip.EquipModelDay;
import com.hoolai.bi.report.vo.EquipModelDayVO;
import com.hoolai.dao.impl.GenericDaoImpl;

@Repository
public class EquipModelDayDaoImpl extends GenericDaoImpl<EquipModelDay, Long> implements EquipModelDayDao {

	@Override
	public String namespace() {
		return EquipModelDay.class.getName();
	}

	@Override
	public List<EquipModelDay> selectList(EquipModelDayVO equipModelDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectList",equipModelDayVO);
	}

	@Override
	public List<EquipModelDayVO> selectInsatll4Pie(
			EquipModelDayVO equipModelDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectInsatll4Pie",equipModelDayVO);
	}

	@Override
	public List<EquipModelDayVO> selectDau4Pie(EquipModelDayVO equipModelDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectDau4Pie",equipModelDayVO);
	}

	@Override
	public List<EquipModelDayVO> selectNewEquip4Pie(
			EquipModelDayVO equipModelDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectNewEquip4Pie",equipModelDayVO);
	}

	@Override
	public List<Map<String,String>> selectDau4Bar(EquipModelDayVO equipModelDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectDau4Bar",equipModelDayVO);
	}

	@Override
	public List<Map<String,String>> selectNewEquip4Line(
			EquipModelDayVO equipModelDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectNewEquip4Line",equipModelDayVO);
	}

	@Override
	public List<Map<String,Object>> selectNewEquipRate4Line(
			EquipModelDayVO equipModelDayVO) {
		return this.sqlSessionTemplate.selectList(this.namespace()+".selectNewEquipRate4Line",equipModelDayVO);
	}

}
