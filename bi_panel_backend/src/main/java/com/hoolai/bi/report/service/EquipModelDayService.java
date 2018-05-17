package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.equip.EquipModelDay;
import com.hoolai.bi.report.vo.EquipModelDayVO;
import com.jian.service.GenericService;

public interface EquipModelDayService extends GenericService<EquipModelDay, Long>{

	Long selectCount(EquipModelDayVO equipModelDayVO);

	List<EquipModelDay> selectList(EquipModelDayVO equipModelDayVO);

	List<EquipModelDayVO> selectInsatll4Pie(EquipModelDayVO equipModelDayVO);

	List<EquipModelDayVO> selectDau4Pie(EquipModelDayVO equipModelDayVO);

	List<EquipModelDayVO> selectNewEquip4Pie(EquipModelDayVO equipModelDayVO);

	List<EquipModelDayVO> selectDau4Bar(EquipModelDayVO equipModelDayVO);

	List<EquipModelDayVO> selectNewEquip4Line(EquipModelDayVO equipModelDayVO);

	List<EquipModelDayVO> selectNewEquipRate4Line(EquipModelDayVO equipModelDayVO);

}
