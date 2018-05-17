package com.hoolai.bi.report.dao;

import java.util.List;
import java.util.Map;

import com.hoolai.bi.report.model.entity.equip.EquipModelDay;
import com.hoolai.bi.report.vo.EquipModelDayVO;
import com.hoolai.dao.GenericDao;


public interface EquipModelDayDao extends GenericDao<EquipModelDay, Long> {

	List<EquipModelDay> selectList(EquipModelDayVO equipModelDayVO);

	List<EquipModelDayVO> selectInsatll4Pie(EquipModelDayVO equipModelDayVO);

	List<EquipModelDayVO> selectDau4Pie(EquipModelDayVO equipModelDayVO);

	List<EquipModelDayVO> selectNewEquip4Pie(EquipModelDayVO equipModelDayVO);

	List<Map<String,String>> selectDau4Bar(EquipModelDayVO equipModelDayVO);

	List<Map<String,String>> selectNewEquip4Line(EquipModelDayVO equipModelDayVO);

	List<Map<String,Object>> selectNewEquipRate4Line(EquipModelDayVO equipModelDayVO);

}
