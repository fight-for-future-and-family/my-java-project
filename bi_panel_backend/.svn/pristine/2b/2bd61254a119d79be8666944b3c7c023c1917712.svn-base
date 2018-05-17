package com.hoolai.bi.report.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoolai.bi.report.dao.ClientEconomyNewReportDao;
import com.hoolai.bi.report.model.entity.ClientEconomyNewReport;
import com.hoolai.bi.report.model.entity.EconomyItemReport;
import com.hoolai.bi.report.service.ClientEconomyNewReportService;
import com.hoolai.bi.report.vo.ClientEconomyNewReportVO;
import com.hoolai.dao.GenericDao;
import com.jian.service.impl.GenericServiceImpl;

@Service
public class ClientEconomyNewReportServiceImpl extends GenericServiceImpl<ClientEconomyNewReport, Long> implements
		ClientEconomyNewReportService {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private ClientEconomyNewReportDao entityDao;

	@Override
	public GenericDao<ClientEconomyNewReport, Long> getGenricDao() {
		return this.entityDao;
	}


	@Override
	public List<Integer> selectGameClients(ClientEconomyNewReportVO clientEconomyNewReportVO) {
		return this.entityDao.selectGameClients(clientEconomyNewReportVO);
	}

	@Override
	public List<EconomyItemReport> selectItemList(String queryType,ClientEconomyNewReportVO clientEconomyNewReportVO) {
		try {
			this.processDimension(clientEconomyNewReportVO);
			
			StringBuffer sb = new StringBuffer("select consume_item,");
			verticalToHorizontal(queryType, clientEconomyNewReportVO, sb);
			
			sb.append(" from (select a.snid,a.gameid,a.ds,a.amount,a.users,a.times,a.item_num," +
					"case when b.consume_name is null then a.consume_code else b.consume_name end as consume_item " +
					"from (select snid,gameid,ds,");
			sb.append(clientEconomyNewReportVO.getConcatStr() + " as consume_code,");
			sb.append("sum(amount) amount,sum(users) users,sum(times) times,sum(item_num) item_num from client_economy_new_report where ");
			
			screeningConditions(clientEconomyNewReportVO, sb);
			
			sb.append(" group by snid,gameid,ds,");
			sb.append(clientEconomyNewReportVO.getGroupType());
			sb.append(") a left outer join ( select snid,gameid,consume_code,consume_name from consume_dimension" +
					" where snid=\'"+clientEconomyNewReportVO.getEntity().getSnid()+"\'"+
					" and gameid=\'" + clientEconomyNewReportVO.getEntity().getGameid()+"\')b " +
					"on a.snid=b.snid and a.gameid=b.gameid and a.consume_code = b.consume_code ) c");
			
			if(!StringUtils.isEmpty(clientEconomyNewReportVO.getSearchValue())){
				sb.append(" where consume_item like concat('%', '"+clientEconomyNewReportVO.getSearchValue()+"', '%')");
			}
			sb.append(" group by consume_item");
			sb.append(" order by ");
			sb.append(" "+(StringUtils.isEmpty(" "+clientEconomyNewReportVO.getOrderDate()) ? "day1" : clientEconomyNewReportVO.getOrderDate()));
			sb.append(" "+(StringUtils.isEmpty(clientEconomyNewReportVO.getOrderType()) ? "desc" : clientEconomyNewReportVO.getOrderType()));
			
			if(clientEconomyNewReportVO.getRows() == 0){
				clientEconomyNewReportVO.setRows(10);
			}
			sb.append(" limit "+clientEconomyNewReportVO.getOffset()+","+clientEconomyNewReportVO.getRows()+"");
			
			clientEconomyNewReportVO.setDateString(sb.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return this.entityDao.selectItemList(clientEconomyNewReportVO);
	}
	
	private void verticalToHorizontal(String queryType,
			ClientEconomyNewReportVO EconomyNewReportVO, StringBuffer sb)
			throws ParseException {
		int index = 1;
		Date beginDate = sdf.parse(EconomyNewReportVO.getBeginDate());
		Date endDate = sdf.parse(EconomyNewReportVO.getEndDate());
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

	private void screeningConditions(ClientEconomyNewReportVO EconomyNewReportVO, StringBuffer sb) {
		int valueIndex = 0;
		if(EconomyNewReportVO.getEntity().getId() != null && EconomyNewReportVO.getEntity().getId() != 0){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("id="+EconomyNewReportVO.getEntity().getId());
			valueIndex ++;
		}
		if(!StringUtils.isEmpty(EconomyNewReportVO.getEntity().getSnid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("snid=\'"+EconomyNewReportVO.getEntity().getSnid()+"\'");
		}
		if(!StringUtils.isEmpty(EconomyNewReportVO.getEntity().getGameid())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			sb.append("gameid=\'"+EconomyNewReportVO.getEntity().getGameid()+"\'");
		}
		if(!StringUtils.isEmpty(EconomyNewReportVO.getBeginDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds>=\'"+EconomyNewReportVO.getBeginDate()+"\'");
		}
		if(!StringUtils.isEmpty(EconomyNewReportVO.getEndDate())){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("ds<=\'"+EconomyNewReportVO.getEndDate()+"\'");
		}
		if(EconomyNewReportVO.getBeginClientid() != null && EconomyNewReportVO.getBeginClientid() != 0){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("clientid+0>=\'"+EconomyNewReportVO.getBeginClientid()+"\'");
		}
		if(EconomyNewReportVO.getEndClientid() != null && EconomyNewReportVO.getEndClientid() != 0){
			if(valueIndex > 0){
				sb.append(" and ");
			}
			valueIndex ++;
			sb.append("clientid+0<=\'"+EconomyNewReportVO.getEndClientid()+"\'");
		}
	}

	@Override
	public List<ClientEconomyNewReport> selectItemList4Down(ClientEconomyNewReportVO clientEconomyNewReportVO) {
		processDimension(clientEconomyNewReportVO);
		return this.entityDao.selectItemList4Down(clientEconomyNewReportVO);
	}

	@Override
	public Long selectItemCount(ClientEconomyNewReportVO clientEconomyNewReportVO) {
		processDimension(clientEconomyNewReportVO);
		return this.entityDao.selectItemCount(clientEconomyNewReportVO);
	}

	private void processDimension(ClientEconomyNewReportVO clientEconomyNewReportVO) {
		if("first".equals(clientEconomyNewReportVO.getGroupType())){
			clientEconomyNewReportVO.setGroupType("phylum");
			clientEconomyNewReportVO.setConcatStr("phylum");
		}else if("second".equals(clientEconomyNewReportVO.getGroupType())){
			clientEconomyNewReportVO.setGroupType("classfield");
			clientEconomyNewReportVO.setConcatStr("classfield");
		}else if("third".equals(clientEconomyNewReportVO.getGroupType())){
			clientEconomyNewReportVO.setGroupType("family");
			clientEconomyNewReportVO.setConcatStr("family");
		}else if("fourth".equals(clientEconomyNewReportVO.getGroupType())){
			clientEconomyNewReportVO.setGroupType("genus");
			clientEconomyNewReportVO.setConcatStr("genus");
		}
	}

	@Override
	public EconomyItemReport selectItemCountList(String queryType,ClientEconomyNewReportVO clientEconomyReportVO) {
		try {
			
			this.processDimension(clientEconomyReportVO);
			StringBuffer sb = new StringBuffer("select ");
			sumString(queryType, clientEconomyReportVO, sb);
			
			sb.append(" from(select consume_code,");
			
			verticalToHorizontal(queryType, clientEconomyReportVO, sb);
			
			sb.append(" from (select snid,gameid,ds,");
			sb.append(clientEconomyReportVO.getConcatStr() + " as consume_code,");
			sb.append("sum(amount) amount,sum(users) users,sum(times) times,sum(item_num) item_num from client_economy_new_report where ");
			
			screeningConditions(clientEconomyReportVO, sb);
			
			sb.append(" group by snid,gameid,ds,");
			sb.append(clientEconomyReportVO.getGroupType());
					
			sb.append(") a group by consume_code) b");
			clientEconomyReportVO.setDateString(sb.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return this.entityDao.selectItemList(clientEconomyReportVO).get(0);
	}
	
	private void sumString(String queryType,
			ClientEconomyNewReportVO economyNewReportVO, StringBuffer sb)
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
}
