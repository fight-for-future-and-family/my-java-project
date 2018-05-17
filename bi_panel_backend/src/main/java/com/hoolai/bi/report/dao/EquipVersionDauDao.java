package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.equip.EquipVersionDau;
import com.hoolai.bi.report.model.entity.equip.EquipVersionDauResult;
import com.hoolai.bi.report.vo.EquipVersionDauVO;
import com.hoolai.dao.GenericDao;


public interface EquipVersionDauDao extends GenericDao<EquipVersionDau, Long> {

	List<EquipVersionDauResult> selectList(EquipVersionDauVO equipVersionDauVO);

	List<String> selectTop5Version(EquipVersionDauVO equipVersionDauVO);
}
