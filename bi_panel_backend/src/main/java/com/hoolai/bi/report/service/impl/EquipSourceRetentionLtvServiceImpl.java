package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.EquipSourceRetentionLtvDao;
import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipSourceRetentionLtv;
import com.hoolai.bi.report.service.EquipSourceRetentionLtvService;
import com.hoolai.bi.report.vo.EquipSourceRetentionLtvVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class EquipSourceRetentionLtvServiceImpl extends GenericServiceImpl<EquipSourceRetentionLtv, Long> implements EquipSourceRetentionLtvService {

	@Autowired
	private EquipSourceRetentionLtvDao entityDao;
	
	@Override
    public GenericDao<EquipSourceRetentionLtv, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Long selectCount(EquipSourceRetentionLtvVO sourceRetentionVO) {
		try {
			return this.entityDao.selecCount(sourceRetentionVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public List<EquipLtv> selectList(EquipSourceRetentionLtvVO sourceRetentionVO) {
		String sql = "select install_date,max(new_equip) new_equip,source as sourceOrModel,";
		for(int i=0;i<=7;i++){
			sql += "max(case retention_day when "+i+" then retention_equip/new_equip else 0 end) d"+i;
			if(i<7){
				sql += ",";
			}
		}
		sourceRetentionVO.setOrderValue(sql);
		return this.entityDao.selectList(sourceRetentionVO);
	}

}
