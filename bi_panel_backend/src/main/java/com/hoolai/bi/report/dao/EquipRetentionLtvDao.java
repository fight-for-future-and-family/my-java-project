package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipRetentionLtv;
import com.hoolai.bi.report.vo.EquipRetentionLtvVO;
import com.hoolai.dao.GenericDao;


public interface EquipRetentionLtvDao extends GenericDao<EquipRetentionLtv, Long> {

	List<EquipLtv> selectList(EquipRetentionLtvVO retentionVO);

	List<EquipRetentionLtvVO> selectAvgRetentionDataList(
			EquipRetentionLtvVO retentionVO);

}
