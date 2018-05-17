package com.hoolai.bi.report.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.EconomyNewReportDao;
import com.hoolai.bi.report.model.entity.EconomyItemReport;
import com.hoolai.bi.report.model.entity.EconomyNewReport;
import com.hoolai.bi.report.service.EconomyNewReportService;
import com.hoolai.bi.report.vo.EconomyNewReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class EconomyNewReportServiceImpl extends GenericServiceImpl<EconomyNewReport, Long> implements EconomyNewReportService {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private EconomyNewReportDao entityDao;

	@Override
	public GenericDao<EconomyNewReport, Long> getGenricDao() {
		return this.entityDao;
	}

	@Override
	public List<EconomyItemReport> selectItemList(String queryType,EconomyNewReportVO economyNewReportVO) {
		try {
			
			this.processDimension(economyNewReportVO);
			
			StringBuffer sb = new StringBuffer("select consume_item,");
			verticalToHorizontal(queryType, economyNewReportVO, sb);
			
			sb.append(" from ( select a.snid,a.gameid,a.ds,a.amount,a.users,a.times,a.item_num," +
					"case when b.consume_name is null then a.consume_code else b.consume_name end as consume_item " +
					"from (select snid,gameid,ds,");
			sb.append(economyNewReportVO.getConcatStr() + " as consume_code,");
			sb.append("sum(amount) amount,sum(users) users," +
					"sum(times) times,sum(item_num) item_num from economy_new_report where ");
			
			screeningConditions(economyNewReportVO, sb);
			
			sb.append(" group by snid,gameid,ds,");
			sb.append(economyNewReportVO.getGroupType());
			sb.append(") a left outer join ( select snid,gameid,consume_code,consume_name from consume_dimension" +
					" where snid=\'"+economyNewReportVO.getEntity().getSnid()+"\'"+
					" and gameid=\'" + economyNewReportVO.getEntity().getGameid()+"\')b " +
					"on a.snid=b.snid and a.gameid=b.gameid and a.consume_code = b.consume_code ) c");
			
			if(!StringUtils.isEmpty(economyNewReportVO.getSearchValue())){
				sb.append(" where consume_item like concat('%', '"+economyNewReportVO.getSearchValue()+"', '%')");
			}
			sb.append(" group by consume_item order by ");
			sb.append(" "+(StringUtils.isEmpty(" "+economyNewReportVO.getOrderDate()) ? "day1" : economyNewReportVO.getOrderDate()));
			sb.append(" "+(StringUtils.isEmpty(economyNewReportVO.getOrderType()) ? "desc" : economyNewReportVO.getOrderType()));
			
			if(economyNewReportVO.getRows() == 0){
				economyNewReportVO.setRows(10);
			}
			sb.append(" limit "+economyNewReportVO.getOffset()+","+economyNewReportVO.getRows()+"");
			
			economyNewReportVO.setDateString(sb.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return this.entityDao.selectItemList(economyNewReportVO);
	}
	
	@Override
	public EconomyItemReport selectItemCountList(String queryType,EconomyNewReportVO economyNewReportVO) {
		try {
			
			this.processDimension(economyNewReportVO);
			StringBuffer sb = new StringBuffer("select ");
			sumString(queryType, economyNewReportVO, sb);
			
			sb.append(" from(select consume_code,");
			
			verticalToHorizontal(queryType, economyNewReportVO, sb);
			
			sb.append(" from (select snid,gameid,ds,");
			sb.append(economyNewReportVO.getConcatStr() + " as consume_code,");
			sb.append("sum(amount) amount,sum(users) users,sum(times) times,sum(item_num) item_num from economy_new_report where ");
			
			screeningConditions(economyNewReportVO, sb);
			
			sb.append(" group by snid,gameid,ds,");
			sb.append(economyNewReportVO.getGroupType());
					
			sb.append(") a group by consume_code) b");
			economyNewReportVO.setDateString(sb.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return this.entityDao.selectItemList(economyNewReportVO).get(0);
	}

	private void sumString(String queryType,
			EconomyNewReportVO economyNewReportVO, StringBuffer sb)
					throws ParseException {
		int index = 1;
		Date beginDate = sdf.parse(economyNewReportVO.getBeginDate());
		Date endDate = sdf.parse(economyNewReportVO.getEndDate());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		
		do{
			sb.append("sum(day"+ index +") day"+ index);
			sb.append(",");
			index++;
			
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
			
		}while(!(calendar.getTime().getTime() > endDate.getTime() || index >15));
		sb.delete(sb.length()-1, sb.length());
	}
	
	private void verticalToHorizontal(String queryType,
			EconomyNewReportVO economyNewReportVO, StringBuffer sb)
			throws ParseException {
		int index = 1;
		Date beginDate = sdf.parse(economyNewReportVO.getBeginDate());
		Date endDate = sdf.parse(economyNewReportVO.getEndDate());
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
		sb.append("sum(amount) amount");
	}

	private void screeningConditions(EconomyNewReportVO economyNewReportVO, StringBuffer sb) {
		int valueIndex = 0;
		if(economyNewReportVO.getEntity().getId() != null && economyNewReportVO.getEntity().getId() != 0){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("id="+economyNewReportVO.getEntity().getId());
			valueIndex ++;
		}
		if(!StringUtils.isEmpty(economyNewReportVO.getEntity().getSnid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("snid=\'"+economyNewReportVO.getEntity().getSnid()+"\'");
		}
		if(!StringUtils.isEmpty(economyNewReportVO.getEntity().getGameid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("gameid=\'"+economyNewReportVO.getEntity().getGameid()+"\'");
		}
		if(!StringUtils.isEmpty(economyNewReportVO.getBeginDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds>=\'"+economyNewReportVO.getBeginDate()+"\'");
		}
		if(!StringUtils.isEmpty(economyNewReportVO.getEndDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds<=\'"+economyNewReportVO.getEndDate()+"\'");
		}
	}

	@Override
	public List<EconomyNewReport> selectItemList4Down(EconomyNewReportVO economyNewReportVO) {
		processDimension(economyNewReportVO);
		return this.entityDao.selectItemList4Down(economyNewReportVO);
	}

	private void processDimension(EconomyNewReportVO economyNewReportVO) {
		if("first".equals(economyNewReportVO.getGroupType())){
			economyNewReportVO.setGroupType("phylum");
			economyNewReportVO.setConcatStr(" phylum ");
		}else if("second".equals(economyNewReportVO.getGroupType())){
			economyNewReportVO.setGroupType("classfield");
			economyNewReportVO.setConcatStr(" classfield ");
		}else if("third".equals(economyNewReportVO.getGroupType())){
			economyNewReportVO.setGroupType("family");
			economyNewReportVO.setConcatStr(" family ");
		}else if("fourth".equals(economyNewReportVO.getGroupType())){
			economyNewReportVO.setGroupType("genus");
			economyNewReportVO.setConcatStr(" genus ");
		}
	}
	
	@Override
	public Long selectItemCount(EconomyNewReportVO economyNewReportVO) {
		processDimension(economyNewReportVO);
		return this.entityDao.selectItemCount(economyNewReportVO);
	}

}
