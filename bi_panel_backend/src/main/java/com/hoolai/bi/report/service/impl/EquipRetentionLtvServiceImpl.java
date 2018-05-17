package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.EquipRetentionLtvDao;
import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipRetentionLtv;
import com.hoolai.bi.report.service.EquipRetentionLtvService;
import com.hoolai.bi.report.vo.EquipRetentionLtvVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class EquipRetentionLtvServiceImpl extends GenericServiceImpl<EquipRetentionLtv, Long> implements EquipRetentionLtvService {

	@Autowired
	private EquipRetentionLtvDao entityDao;
	
	@Override
    public GenericDao<EquipRetentionLtv, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Long selectCount(EquipRetentionLtvVO retentionVO) {
		try {
			return this.entityDao.selecCount(retentionVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public List<EquipLtv> selectList(EquipRetentionLtvVO retentionVO) {
		String sql = "select install_date,max(new_equip) new_equip,";
		for(int i=0;i<=7;i++){
			sql += "max(case retention_day when "+i+" then retention_equip/new_equip else 0 end) d"+i;
			if(i<7){
				sql += ",";
			}
		}
		retentionVO.setOrderValue(sql);
		return this.entityDao.selectList(retentionVO);
	}

	@Override
	public List<EquipRetentionLtvVO> selectAvgRetentionDataList(
			EquipRetentionLtvVO retentionVO) {
		return this.entityDao.selectAvgRetentionDataList(retentionVO);
	}

}
