package com.hoolai.bi.report.job.etl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;

import scala.annotation.meta.companionClass;

import com.hoolai.bi.notifyer.mail.MailSenderProxy;
import com.hoolai.bi.report.core.Constant;
import com.hoolai.bi.report.etl.ETLEngineGameInfo;
import com.hoolai.bi.report.etl.ETLEngineGameInfo.Type;
import com.hoolai.bi.report.job.AbstractExecuteJob;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.util.ReportDateUtils;
import com.hoolai.bi.report.vo.GamesVO;
import com.jian.tools.util.HttpClientUtils;
import com.jian.tools.util.JSONUtils;

/**
 * 每天触发所有有效游戏，计算etl
 */
public class DailyTriggerAllGamesETLEngineJob extends AbstractExecuteJob {
	
	private static final MailSenderProxy MAIL_SENDER_PROXY=new MailSenderProxy();
	
	@Autowired
	private GamesService gamesService;
	
	private boolean isExecuting=false;
	
	@Override
	public Map<String, Object> executeJob() throws Exception {
		if(isExecuting==true){
			return Collections.emptyMap();
		}
		isExecuting=true;
		try{
			// 只计算etlstats = 1 的项目
			GamesVO runGames = new GamesVO();
			runGames.getEntity().setEtlStatus("1");
			List<Games> validGameList=this.gamesService.getMatch(runGames);
			
			List<Games> filterGameList=this.filterTestGames(validGameList);
			
			if(filterGameList==null||filterGameList.isEmpty()){
				isExecuting=false;
				return Collections.emptyMap();
			}
			Games game=filterGameList.get(0);
			TimeZone timeZone=TimeZone.getTimeZone("GMT+8");
			if(StringUtils.isNotEmpty(game.getTimeZone())){
				timeZone=TimeZone.getTimeZone(game.getTimeZone());
			}
			Date now=new Date();
			Date yesterday = DateUtils.addDays(now, -1);
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			simpleDateFormat.setTimeZone(timeZone);
			String statDay=simpleDateFormat.format(yesterday);
			
			DailyTriggerAllGamesETLEngineJob.triggerGameEtlEngine(Type.ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/runEtls",filterGameList, statDay,statDay,0, null);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		isExecuting=false;
		return Collections.emptyMap();
	}
	
	
	private List<Games> filterTestGames(List<Games> gameList){
		
		if(Constant.IS_PUBLISHED){
			List<Games> finalGameList=new ArrayList<Games>();
			for(Games games:gameList){
				if(!isReadyTriggerEtl(games)){
					continue;
				}
				finalGameList.add(games);
			}
			return finalGameList;
		}
		
		String[] gameids=Constant.constantProperties.getProperty("test_gameids").split(",");
		
		List<Games> finalGameList=new ArrayList<Games>();
		for(Games games:gameList){
			for(String gameid:gameids){
				if(!Constant.IS_PUBLISHED&&!gameid.equals(games.getId()+"")){
					continue;
				}
				if(!isReadyTriggerEtl(games)){
					continue;
				}
				finalGameList.add(games);
			}
		}
		return finalGameList;
	}
	
	private boolean isReadyTriggerEtl(Games game){
		try {
			
			TimeZone timeZone=TimeZone.getTimeZone("GMT+8");
			if(StringUtils.isNotEmpty(game.getTimeZone())){
				timeZone=TimeZone.getTimeZone(game.getTimeZone());
			}
			Calendar cal=Calendar.getInstance(timeZone);
			String hourOfDay=ReportDateUtils.getHourOfDay(cal.getTime(), timeZone);
			if(!isTriggerHour(hourOfDay)){
				super.debug("snid:"+game.getSnid()+" gameid:"+game.getGameid()+" hourOfDay:"+hourOfDay+" isTriggerHour:false");
				return false;
			}
			int hourOfDayNum=Integer.parseInt(hourOfDay);
			int etlTriggerIdNum=0;
			if(StringUtils.isNotEmpty(game.getEtlTriggerId())||StringUtils.isNumeric(game.getEtlTriggerId())){
				etlTriggerIdNum=Integer.parseInt(game.getEtlTriggerId());
			}
			if(etlTriggerIdNum>=hourOfDayNum){
				super.debug("snid:"+game.getSnid()+" gameid:"+game.getGameid()+" hourOfDay:"+hourOfDay+" etlTriggerIdNum:"+etlTriggerIdNum+" etlTriggerIdNum>=hourOfDayNum:false");
				return false;
			}
			Games updateGame=new Games();
			updateGame.setId(game.getId());
			updateGame.setEtlTriggerId(hourOfDay);
			updateGame.setEtlTriggerTime(new Date());
			this.gamesService.modifyEntitySelective(updateGame);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean isTriggerHour(String hourOfDay){
//		if(true){
//			return true;
//		}
		if(hourOfDay==null||hourOfDay.length()!=10){
			return false;
		}
		String hour=hourOfDay.substring(8);
		if(!"01".equals(hour)){
			return false;
		}
		return true;
	}
	
	public static List<Map<String,String>> triggerGameEtlEngine(Type type,String url,List<Games> validGameList,String beginDate,String endDate,int step, String minute){
		try {
			if(validGameList==null|validGameList.isEmpty()){
				return Collections.emptyList();
			}
			
			Collections.sort(validGameList, new GamesComparator());
			
			List<Map<String,String>> ret=new ArrayList<Map<String,String>>();
			
			List<ETLEngineGameInfo> engineGameInfoList=new ArrayList<ETLEngineGameInfo>();
			for (Games game : validGameList) {
				ETLEngineGameInfo etlEngineGameInfo=new ETLEngineGameInfo(type.getDisplayName(),game.getName(),game.getSnid(), game.getGameid(), beginDate, step,game.getTerminalType().intValue(),game.getSystemType().intValue(), game.getEtlCurrency());
				if(minute!=null) {
					etlEngineGameInfo.setMinute(minute);
				}
				engineGameInfoList.add(etlEngineGameInfo);
			}
			
			Map<String,String> params=new HashMap<String,String>();
			params.put("gameInfosJson", JSONUtils.toJSON(engineGameInfoList));
			params.put("beginDate", beginDate);
			params.put("endDate", endDate);
			logger.info("triggerGameEtlEngine-info:"+JSONUtils.toJSON(params));
			String retJson=HttpClientUtils.executePostRequest(url, params, "UTF-8");
			
			TypeReference<List<HashMap<String,String>>> typeReference=new TypeReference<List<HashMap<String,String>>>() {};
			List<HashMap<String,String>> retMess= JSONUtils.fromJSON(retJson, typeReference, true);
			ret.addAll(retMess);
			return ret;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Map<String,Object> errorDatas=new HashMap<String,Object>();
			errorDatas.put("url", url);
			errorDatas.put("validGameList", validGameList);
			errorDatas.put("beginDate", beginDate);
			errorDatas.put("endDate", endDate);
			MAIL_SENDER_PROXY.sendMail(Constant.MONITOR_SYS_EMAILS, "ETL计算提交失败！",e.getMessage()+"["+JSONUtils.toJSON(errorDatas)+"]");
		}
		return Collections.emptyList();
	}
	
	private static class GamesComparator implements Comparator<com.hoolai.bi.report.model.entity.Games>{

		@Override
		public int compare(Games arg0, Games arg1) {
			if(arg0.getEtlOrder().longValue() > arg1.getEtlOrder().longValue()){
				return 1;
			}else if(arg0.getEtlOrder().longValue() < arg1.getEtlOrder().longValue()){
				return -1;
			}
			return 0;
		}
		
	}
	public static void main(String[] args) {
		String zones[]=new String[]{"GMT+2","GMT-5","GMT-4","GMT-11"};
		for (String zone : zones) {
			TimeZone.getTimeZone(zone);
		}
	}
	
}
