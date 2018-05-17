package com.hoolai.bi.report.job.etl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hoolai.bi.notifyer.mail.MailSenderProxy;
import com.hoolai.bi.report.core.Constant;
import com.hoolai.bi.report.etl.ETLEngineGameInfo.Type;
import com.hoolai.bi.report.job.AbstractExecuteJob;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.bi.report.util.Types;
import com.hoolai.bi.report.vo.GamesVO;

/**
 * Adtracking触发所有有效游戏，计算实时etl
 */
public class AdtrackingRealTimeTriggerAllGamesETLEngineJob extends AbstractExecuteJob {
	
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
			
			List<Games> validGameList=this.gamesService.getMatch(new GamesVO());
			
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
			Date statDate=DateUtils.addHours(now, -1);
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			simpleDateFormat.setTimeZone(timeZone);
			String statDay=simpleDateFormat.format(statDate);
			
			Calendar calendar=Calendar.getInstance(timeZone);
			calendar.setTime(statDate);
			int hour=calendar.get(Calendar.HOUR_OF_DAY);
			
			DailyTriggerAllGamesETLEngineJob.triggerGameEtlEngine(Type.ADTRACKING_QUASI_ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/runEtls",filterGameList, statDay,statDay,hour,null);
			
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
				if(Types.TerminalType.MOBILE_PHONE.getId()==games.getTerminalType().intValue()
						&&Types.GameMobRunSystemType.IOS.getId()==games.getSystemType().intValue()){
					finalGameList.add(games);
				}
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
				finalGameList.add(games);
			}
		}
		return finalGameList;
	}
	
}
