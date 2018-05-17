package com.hoolai.bi.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.PayUserRetentionLtvDao;
import com.hoolai.bi.report.model.entity.whaleUser.PayUserRetentionLtv;
import com.hoolai.bi.report.service.PayUserRetentionLtvService;
import com.hoolai.bi.report.vo.PayUserRetentionLtvVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;


@Service
public class PayUserRetentionLtvServiceImpl extends GenericServiceImpl<PayUserRetentionLtv, Long> implements PayUserRetentionLtvService {

	@Autowired
	private PayUserRetentionLtvDao entityDao;
	
	@Override
    public GenericDao<PayUserRetentionLtv, Long> getGenricDao() {
            return this.entityDao;
    }

	@Override
	public Long selectCount(PayUserRetentionLtvVO retentionVO) {
		try {
			return this.entityDao.selecCount(retentionVO);
		} catch (Exception e) {
			return 0l;
		}
	}
	
	@Override
	public List<PayUserRetentionLtvVO> selectList(PayUserRetentionLtvVO retentionVO) {
		StringBuffer sb = new StringBuffer("");
		sb.append("select firstpay_date as firstpayDate,max(firstpay_users) firstpayUsers, ");
		
		for(int i=1;i<=90;i++){
			sb.append("max(case retention_day when "+i+" then retention_uu/firstpay_users else 0 end) d"+i);
			if(i<90){
				sb.append(",");
			}
		}
		sb.append(" from payuser_retention_ltv where ");
		screeningConditions(retentionVO, sb);
		sb.append("group by firstpay_date");
		
		retentionVO.setSearchValue(sb.toString());
		
		List<Map<String,Object>> list = this.entityDao.selectMapList(retentionVO);
		List<PayUserRetentionLtvVO> payUserRetentionLtvVOs = new ArrayList<PayUserRetentionLtvVO>();
		
		for(Map<String,Object> map:list){
			PayUserRetentionLtvVO ltvVO = new PayUserRetentionLtvVO();
			Double[] modelArr = new Double[90];
			
			String  firstpayDate = (String)map.get("firstpayDate");
			Object firstpayUsersObj = map.get("firstpayUsers");
			String  firstpayUsers = firstpayUsersObj ==null ?"":String.valueOf(firstpayUsersObj);
			
			for(int i=0;i<90;i++){
				Object o = map.get("d"+(i+1));
				String dayData = o ==null ?"":String.valueOf(o);
				if(StringUtils.isEmpty(dayData)){
					modelArr[i] = 0d;
				}else{
					modelArr[i] = Double.valueOf(dayData);
				}
			}
			ltvVO.getEntity().setFirstpayDate(firstpayDate);
			ltvVO.getEntity().setFirstpayUsers(StringUtils.isEmpty(firstpayUsers) ? 0 : Integer.valueOf(firstpayUsers));
			
			ltvVO.setDoubleDayDatas(modelArr);
			payUserRetentionLtvVOs.add(ltvVO);
		}
		
		return payUserRetentionLtvVOs;
	}

	private void screeningConditions(PayUserRetentionLtvVO retentionLtvVO, StringBuffer sb) {
		int valueIndex = 0;
		if(retentionLtvVO.getEntity().getId() != null && retentionLtvVO.getEntity().getId() != 0){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("id="+retentionLtvVO.getEntity().getId());
			valueIndex ++;
		}
		if(!StringUtils.isEmpty(retentionLtvVO.getEntity().getSnid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("snid=\'"+retentionLtvVO.getEntity().getSnid()+"\'");
		}
		if(!StringUtils.isEmpty(retentionLtvVO.getEntity().getGameid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("gameid=\'"+retentionLtvVO.getEntity().getGameid()+"\'");
		}
		if(!StringUtils.isEmpty(retentionLtvVO.getBeginDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("firstpay_date>=\'"+retentionLtvVO.getBeginDate()+"\'");
		}
		if(!StringUtils.isEmpty(retentionLtvVO.getEndDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("firstpay_date<=\'"+retentionLtvVO.getEndDate()+"\'");
		}
		
		if(valueIndex > 0){
			sb.append(" and ");
			sb.append("(retention_day >= 0 and retention_day <= 90) ");
		}
	}
	
	@Override
	public List<PayUserRetentionLtvVO> selectAvgRetentionDataList(PayUserRetentionLtvVO retentionVO) {
		return this.entityDao.selectAvgRetentionDataList(retentionVO);
	}
	
	
}
