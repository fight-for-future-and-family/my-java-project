package com.hoolai.bi.report.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.ClientLevelNewPaymentDao;
import com.hoolai.bi.report.model.entity.ClientLevelNewPayment;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.model.entity.LevelNewPayment;
import com.hoolai.bi.report.service.ClientLevelNewPaymentService;
import com.hoolai.bi.report.vo.ClientLevelNewPaymentVO;
import com.hoolai.dao.GenericDao;
import com.hoolai.dao.pagination.AbstractObjectVO;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class ClientLevelNewPaymentServiceImpl extends GenericServiceImpl<ClientLevelNewPayment, Long> implements ClientLevelNewPaymentService {

	public static SimpleDateFormat sdf = LevelDauReportServiceImpl.sdf;
	
	@Autowired
	private ClientLevelNewPaymentDao entityDao;

	@Override
	public GenericDao<ClientLevelNewPayment, Long> getGenricDao() {
		return this.entityDao;
	}

	@Override
	public List<LevelInstallDauReport> getLevelNewPayReportList(ClientLevelNewPaymentVO levelNewPaymentVO,String queryType) {
		
		List<LevelInstallDauReport> reports = null;
		
		try {
			// 查询各等级注册日期横表
			StringBuffer sb = new StringBuffer();
			sb.append("select user_level as level,");
			int dateCount = verticalToHorizontal(levelNewPaymentVO, sb,transforQueryType(queryType));
			sb.append(" from client_level_new_payment where ");
			screeningConditions(levelNewPaymentVO, sb);
			sb.append(" group by snid,gameid,user_level order by user_level desc");
			levelNewPaymentVO.setDateString(sb.toString());
			reports = this.entityDao.getLevelNewPayReportList(levelNewPaymentVO);
			
			// 查询所有等级活跃日期横表
			StringBuffer sba = new StringBuffer();
			sba.append("select -1 as level,");
			sumString(sba,dateCount);
			sba.append(" from(");
			sba.append(sb.toString());
			sba.append(") a");
			levelNewPaymentVO.setDateString(sba.toString());
			List<LevelInstallDauReport> countReport = this.entityDao.getLevelNewPayReportList(levelNewPaymentVO);
			
			if(countReport.size()>0){
				LevelInstallDauReport report = countReport.get(0);
				reports.add(report);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return reports;
	}
	
	private String  transforQueryType(String queryType){
		if("intall_payers".equals(queryType)){
			return "new_payers";
		}else{
			return "amount";
		}
	}
	
	private void sumString(StringBuffer sb,int dateCount){
		for(int i=1;i<=dateCount;i++){
			if(i == dateCount){
				sb.append("sum(day"+i+") as day"+i+" ");
			}else{
				sb.append("sum(day"+i+") as day"+i+", ");
			}
		}
		
	}
	
	private int verticalToHorizontal(
			ClientLevelNewPaymentVO levelNewPaymentVO, StringBuffer sb,String queryType)
			throws ParseException {
		int index = 1;
		Date beginDate = sdf.parse(levelNewPaymentVO.getBeginDate());
		Date endDate = sdf.parse(levelNewPaymentVO.getEndDate());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		
		do{
			sb.append("max(case when ds=\'");
			sb.append(sdf.format(calendar.getTime()));
			sb.append("\' then ");
			sb.append(queryType);
			sb.append(" end) as day");
			sb.append(index++);
			sb.append(",");
			
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			
		}while(!(calendar.getTime().getTime() > endDate.getTime() || index > 15));
		sb.delete(sb.length()-1, sb.length());
		return index -1;
	}
	
	private void screeningConditions(ClientLevelNewPaymentVO levelNewPaymentVO, StringBuffer sb) {
		int valueIndex = 0;
		if(levelNewPaymentVO.getEntity().getId() != null && levelNewPaymentVO.getEntity().getId() != 0){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("id="+levelNewPaymentVO.getEntity().getId());
			valueIndex ++;
		}
		if(!StringUtils.isEmpty(levelNewPaymentVO.getEntity().getSnid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("snid=\'"+levelNewPaymentVO.getEntity().getSnid()+"\'");
		}
		if(!StringUtils.isEmpty(levelNewPaymentVO.getEntity().getGameid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("gameid=\'"+levelNewPaymentVO.getEntity().getGameid()+"\'");
		}
		if(levelNewPaymentVO.getEntity().getClientid() != 0){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("clientid="+levelNewPaymentVO.getEntity().getClientid());
		}
		if(!StringUtils.isEmpty(levelNewPaymentVO.getBeginDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds>=\'"+levelNewPaymentVO.getBeginDate()+"\'");
		}
		
		if(!StringUtils.isEmpty(levelNewPaymentVO.getEndDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds<=\'"+levelNewPaymentVO.getEndDate()+"\'");
		}
	}

	@Override
	public List<Integer> selectClientids(ClientLevelNewPaymentVO clientLevelNewPaymentVO) {
		return this.entityDao.selectClientids(clientLevelNewPaymentVO);
	}

}
