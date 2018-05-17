package com.hoolai.bi.report.service;

import java.util.List;

import com.hoolai.bi.report.model.entity.equip.EquipVersionDau;
import com.hoolai.bi.report.model.entity.equip.EquipVersionDauResult;
import com.hoolai.bi.report.vo.EquipVersionDauVO;
import com.jian.service.GenericService;

public interface EquipVersionDauService extends GenericService<EquipVersionDau, Long>{

	Long selectCount(EquipVersionDauVO equipVersionDauVO);

	List<EquipVersionDauResult> selectList(EquipVersionDauVO equipVersionDauVO);

	List<String> selectTop5Version(EquipVersionDauVO versionDauVO);

}
