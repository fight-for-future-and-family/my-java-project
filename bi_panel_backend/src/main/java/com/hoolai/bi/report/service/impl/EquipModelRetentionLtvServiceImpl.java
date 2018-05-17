package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.EquipModelRetentionLtvDao;
import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipModelRetentionLtv;
import com.hoolai.bi.report.service.EquipModelRetentionLtvService;
import com.hoolai.bi.report.vo.EquipModelRetentionLtvVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class EquipModelRetentionLtvServiceImpl extends GenericServiceImpl<EquipModelRetentionLtv, Long> implements EquipModelRetentionLtvService {

	@Autowired
	private EquipModelRetentionLtvDao entityDao;
	
	@Override
    public GenericDao<EquipModelRetentionLtv, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Long selectCount(EquipModelRetentionLtvVO modelRetentionVO) {
		try {
			return this.entityDao.selecCount(modelRetentionVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public List<EquipLtv> selectList(EquipModelRetentionLtvVO modelRetentionVO) {
		String sql = "select install_date,max(new_equip) new_equip,model as sourceOrModel,";
		for(int i=0;i<=7;i++){
			sql += "max(case retention_day when "+i+" then retention_equip/new_equip else 0 end) d"+i;
			if(i<7){
				sql += ",";
			}
		}
		modelRetentionVO.setOrderValue(sql);
		return this.entityDao.selectList(modelRetentionVO);
	}

}
