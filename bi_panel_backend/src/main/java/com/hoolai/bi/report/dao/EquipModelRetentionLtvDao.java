package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipModelRetentionLtv;
import com.hoolai.bi.report.vo.EquipModelRetentionLtvVO;
import com.hoolai.dao.GenericDao;


public interface EquipModelRetentionLtvDao extends GenericDao<EquipModelRetentionLtv, Long> {

	List<EquipLtv> selectList(EquipModelRetentionLtvVO modelRetentionVO);

}
