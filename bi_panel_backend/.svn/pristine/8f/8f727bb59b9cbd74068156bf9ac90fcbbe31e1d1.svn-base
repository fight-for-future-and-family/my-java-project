package com.hoolai.bi.report.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.LevelInstallPayerReportDao;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.model.entity.LevelInstallPayerReport;
import com.hoolai.bi.report.service.LevelInstallPayerReportService;
import com.hoolai.bi.report.vo.LevelInstallPayerReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class LevelInstallPayerReportServiceImpl extends GenericServiceImpl<LevelInstallPayerReport, Long> implements LevelInstallPayerReportService {

	public static SimpleDateFormat sdf = LevelDauReportServiceImpl.sdf;
	
	@Autowired
	private LevelInstallPayerReportDao entityDao;

	@Override
	public GenericDao<LevelInstallPayerReport, Long> getGenricDao() {
		return this.entityDao;
	}

	@Override
	public List<LevelInstallDauReport> getLevelDauReportList(LevelInstallPayerReportVO installPayerReportVO,String queryType) {
		
		List<LevelInstallDauReport> reports = null;
		
		try {
			// 查询各等级注册日期横表
			StringBuffer sb = new StringBuffer();
			sb.append("select level,");
			int dateCount = verticalToHorizontal(installPayerReportVO, sb,transforQueryType(queryType));
			sb.append(" from level_install_payer where level <= 200 and retention_day >=0 and retention_day <=14 ");
			screeningConditions(installPayerReportVO, sb);
			sb.append(" group by snid,gameid,level order by level desc");
			installPayerReportVO.setDateString(sb.toString());
			reports = this.entityDao.getLevelDauReportList(installPayerReportVO);
			
			// 查询所有等级活跃日期横表
			StringBuffer sba = new StringBuffer();
			sba.append("select -1 as level,");
			sumString(sba,dateCount);
			sba.append(" from(");
			sba.append(sb.toString());
			sba.append(") a");
			installPayerReportVO.setDateString(sba.toString());
			List<LevelInstallDauReport> countReport = this.entityDao.getLevelDauReportList(installPayerReportVO);
			
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
		if(queryType == null || StringUtils.isEmpty(queryType)){
			return "dau_level";
		}else if("install_payment".equals(queryType)){
			return "new_payment";
		}else{
			return "new_payers";
		}
	}
	
	private void sumString(StringBuffer sb,int dateCount){
		for(int i=0;i<=dateCount;i++){
			if(i == dateCount){
				sb.append("sum(day"+i+") as day"+i+" ");
			}else{
				sb.append("sum(day"+i+") as day"+i+", ");
			}
		}
		
	}
	
	private int verticalToHorizontal(
			LevelInstallPayerReportVO installPayerReportVO, StringBuffer sb,String queryType)
			throws ParseException {
		int index = 0;
		Date beginDate = sdf.parse(installPayerReportVO.getBeginDate());
		Date endDate = sdf.parse(sdf.format(new Date()));//我只是想要去掉时分秒
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
			
		}while(!(calendar.getTime().getTime() > endDate.getTime() || index >= 15));
		sb.delete(sb.length()-1, sb.length());
		return index -1;
	}
	
	private void screeningConditions(LevelInstallPayerReportVO installPayerReportVO, StringBuffer sb) {
		if(installPayerReportVO.getEntity().getId() != null && installPayerReportVO.getEntity().getId() != 0){
			sb.append(" and ");
			sb.append("id="+installPayerReportVO.getEntity().getId());
		}
		if(!StringUtils.isEmpty(installPayerReportVO.getEntity().getSnid())){
			sb.append(" and ");
			sb.append("snid=\'"+installPayerReportVO.getEntity().getSnid()+"\'");
		}
		if(!StringUtils.isEmpty(installPayerReportVO.getEntity().getGameid())){
			sb.append(" and ");
			sb.append("gameid=\'"+installPayerReportVO.getEntity().getGameid()+"\'");
		}
		if(!StringUtils.isEmpty(installPayerReportVO.getBeginDate())){
			sb.append(" and ");
		    sb.append("install_date=\'"+installPayerReportVO.getBeginDate()+"\'");
		}
		
	}

}
