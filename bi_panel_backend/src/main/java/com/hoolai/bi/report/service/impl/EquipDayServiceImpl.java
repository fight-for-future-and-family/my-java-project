package com.hoolai.bi.report.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.EquipDayDao;
import com.hoolai.bi.report.model.entity.equip.EquipDay;
import com.hoolai.bi.report.service.EquipDayService;
import com.hoolai.bi.report.vo.EquipDayVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class EquipDayServiceImpl extends GenericServiceImpl<EquipDay, Long> implements EquipDayService {

	@Autowired
	private EquipDayDao entityDao;
	
	@Override
    public GenericDao<EquipDay, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Long selectCount(EquipDayVO equipDayVO) {
		try {
			return this.entityDao.selecCount(equipDayVO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0l;
		}
	}

	@Override
	public List<EquipDay> selectList(EquipDayVO equipDayVO) {
		String col = equipDayVO.getOrderCol();
		String orderType = StringUtils.isEmpty(equipDayVO.getOrderType()) ? "desc" : equipDayVO.getOrderType();
		if(StringUtils.isEmpty(col)){
			equipDayVO.setOrderValue(" order by ds "+orderType);
		}else{
			String orderCol = "ds";
			int colInt = Integer.valueOf(col);
			switch(colInt){
			case 0:
				orderCol = "ds";
				break;
			case 1:
				orderCol = "dau";
				break;
			case 2:
				orderCol = "new_equip";
				break;
			case 3:
				orderCol = "install";
				break;
			case 4:
				orderCol = "dau-new_equip";
				break;
			case 5:
				orderCol = "install/new_equip";
				break;
			case 6:
				orderCol = "uninstall";
				break;
			}
			equipDayVO.setOrderValue(" order by "+orderCol+" "+orderType);
		}
		
		return this.entityDao.selectList(equipDayVO);
	}

}
