package com.hoolai.bi.report.dao;

import java.util.List;
import java.util.Map;

import com.hoolai.bi.report.model.entity.equip.EquipSourceDay;
import com.hoolai.bi.report.vo.EquipSourceDayVO;
import com.hoolai.dao.GenericDao;


public interface EquipSourceDayDao extends GenericDao<EquipSourceDay, Long> {

	List<EquipSourceDay> selectList(EquipSourceDayVO equipSourceDayVO);
	List<String> selectEquipSources(EquipSourceDayVO equipSourceDayVO);
	List<EquipSourceDayVO> selectInsatll4Pie(EquipSourceDayVO equipSourceDayVO);
	List<EquipSourceDayVO> selectDau4Pie(EquipSourceDayVO equipSourceDayVO);
	List<EquipSourceDayVO> selectNewEquip4Pie(EquipSourceDayVO equipSourceDayVO);
	List<Map<String,String>> selectDau4Bar(EquipSourceDayVO equipSourceDayVO);
	List<Map<String,String>> selectNewEquip4Line(EquipSourceDayVO equipSourceDayVO);
	List<Map<String,Object>> selectNewEquipRate4Line(EquipSourceDayVO equipSourceDayVO);

}
