package com.hoolai.bi.report.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.EquipVersionDauDao;
import com.hoolai.bi.report.model.entity.equip.EquipVersionDau;
import com.hoolai.bi.report.model.entity.equip.EquipVersionDauResult;
import com.hoolai.bi.report.service.EquipVersionDauService;
import com.hoolai.bi.report.vo.EquipVersionDauVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class EquipVersionDauServiceImpl extends GenericServiceImpl<EquipVersionDau, Long> implements EquipVersionDauService {

	@Autowired
	private EquipVersionDauDao entityDao;
	
	@Override
    public GenericDao<EquipVersionDau, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Long selectCount(EquipVersionDauVO EquipVersionDauVO) {
		try {
			return this.entityDao.selecCount(EquipVersionDauVO);
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EquipVersionDauResult> selectList(EquipVersionDauVO equipVersionDauVO) {
		List<String> versionList = this.entityDao.selectTop5Version(equipVersionDauVO);
		if(versionList.size() < 1){
			return Collections.EMPTY_LIST;
		}
		equipVersionDauVO.setVersionNames(versionList);
		
		String versionStr = "select ds,";
		String sumStr = "(sumdau";
		for(int i=1;i<=versionList.size();i++){
			versionStr += "v"+i+",";
			sumStr += "-v"+i;
		}
		versionStr += sumStr+") other  from(select ds,";
		int index = 1;
		for(String version:versionList){
			versionStr += "max(case version when '" + version +"' then dau else 0 end) v"+index+",";
			index ++;
		}
		versionStr += "sum(dau) sumdau";
		equipVersionDauVO.setVersionStr(versionStr);
		
		String col = equipVersionDauVO.getOrderCol();
		String orderType = StringUtils.isEmpty(equipVersionDauVO.getOrderType()) ? "desc" : equipVersionDauVO.getOrderType();
		if(StringUtils.isEmpty(col)){
			equipVersionDauVO.setOrderValue(" order by ds "+orderType);
		}else{
			String orderCol = "ds";
			int colInt = Integer.valueOf(col);
			switch(colInt){
			case 0:
				orderCol = "ds";
				break;
			case 1:
				orderCol = "v1";
				break;
			case 2:
				orderCol = "v2";
				break;
			case 3:
				orderCol = "v3";
				break;
			case 4:
				orderCol = "v4";
				break;
			case 5:
				orderCol = "v5";
				break;
			case 6:
				orderCol = "other";
				break;
			}
			equipVersionDauVO.setOrderValue(" order by "+orderCol+" "+orderType);
		}
		
		return this.entityDao.selectList(equipVersionDauVO);
	}

	@Override
	public List<String> selectTop5Version(EquipVersionDauVO versionDauVO) {
		return this.entityDao.selectTop5Version(versionDauVO);
	}

}
