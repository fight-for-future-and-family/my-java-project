package com.hoolai.bi.report.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.LevelDauReportDao;
import com.hoolai.bi.report.model.entity.LevelDauReport;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.service.LevelDauReportService;
import com.hoolai.bi.report.vo.LevelDauReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class LevelDauReportServiceImpl extends GenericServiceImpl<LevelDauReport, Long> implements LevelDauReportService {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private LevelDauReportDao entityDao;

	@Override
	public GenericDao<LevelDauReport, Long> getGenricDao() {
		return this.entityDao;
	}
	
	@Override
	public List<LevelInstallDauReport> getLevelDauReportList(LevelDauReportVO levelDauReportVO){
		
		List<LevelInstallDauReport> reports = null;
		
		try {
			// 查询各等级活跃日期横表
			StringBuffer sb = new StringBuffer();
			sb.append("select dau_level,");
			verticalToHorizontal(levelDauReportVO, sb,"dau");
			sb.append(" from level_dau where  dau_level <= 200 ");
			screeningConditions(levelDauReportVO, sb);
			sb.append("group by snid,gameid,dau_level order by dau_level desc");
			levelDauReportVO.setDateString(sb.toString());
			reports = this.entityDao.getLevelDauReportList(levelDauReportVO);
			
			// 查询所有等级活跃日期横表
			sb.setLength(0);
			sb.append("select ");
			verticalToHorizontal(levelDauReportVO, sb,"total_dau");
			sb.append(" from level_dau where 1=1 ");
			screeningConditions(levelDauReportVO, sb);
			sb.append("group by snid,gameid");
			levelDauReportVO.setDateString(sb.toString());
			List<LevelInstallDauReport> countReport = this.entityDao.getLevelDauReportList(levelDauReportVO);
			
			if(countReport.size()>0){
				LevelInstallDauReport report = countReport.get(0);
				report.setLevel(-1);
				reports.add(report);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return reports;
	}
	
	private void verticalToHorizontal(
			LevelDauReportVO levelDauReportVO, StringBuffer sb,String queryType)
			throws ParseException {
		int index = 1;
		Date beginDate = sdf.parse(levelDauReportVO.getBeginDate());
		Date endDate = sdf.parse(levelDauReportVO.getEndDate());
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
			
		}while(!(calendar.getTime().getTime() > endDate.getTime() || index >15));
		sb.delete(sb.length()-1, sb.length());
	}
	
	private void screeningConditions(LevelDauReportVO levelDauReportVO, StringBuffer sb) {
		if(levelDauReportVO.getEntity().getId() != null && levelDauReportVO.getEntity().getId() != 0){
			sb.append(" and ");
			sb.append("id="+levelDauReportVO.getEntity().getId());
		}
		if(!StringUtils.isEmpty(levelDauReportVO.getEntity().getSnid())){
			sb.append(" and ");
			sb.append("snid=\'"+levelDauReportVO.getEntity().getSnid()+"\'");
		}
		if(!StringUtils.isEmpty(levelDauReportVO.getEntity().getGameid())){
			sb.append(" and ");
			sb.append("gameid=\'"+levelDauReportVO.getEntity().getGameid()+"\'");
		}
		if(!StringUtils.isEmpty(levelDauReportVO.getBeginDate())){
			sb.append(" and ");
			sb.append("ds>=\'"+levelDauReportVO.getBeginDate()+"\'");
		}
		if(!StringUtils.isEmpty(levelDauReportVO.getEndDate())){
			sb.append(" and ");
			sb.append("ds<=\'"+levelDauReportVO.getEndDate()+"\'");
		}
	}
}
