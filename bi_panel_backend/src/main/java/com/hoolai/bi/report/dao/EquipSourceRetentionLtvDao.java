package com.hoolai.bi.report.dao;

import java.util.List;

import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipSourceRetentionLtv;
import com.hoolai.bi.report.vo.EquipSourceRetentionLtvVO;
import com.hoolai.dao.GenericDao;


public interface EquipSourceRetentionLtvDao extends GenericDao<EquipSourceRetentionLtv, Long> {

	List<EquipLtv> selectList(EquipSourceRetentionLtvVO sourceRetentionVO);

}
