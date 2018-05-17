package com.hoolai.panel.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.core.Constant;
import com.hoolai.bi.report.etl.ETLEngineGameInfo.Type;
import com.hoolai.bi.report.job.etl.DailyTriggerAllGamesETLEngineJob;
import com.hoolai.bi.report.model.Types.GameViewTestReport;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.util.ReportDateUtils;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.manage.model.Users;


@Controller
@RequestMapping("/panel_bi/gameTestEtl")
public class GameTestEtlController extends AbstractPanelController {
	
	@Autowired
	private GamesService gamesService;
	
	private boolean isExecuting=false;
	
	
	@RequestMapping(value = {"/toGameTestEtl.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		String view = request.getParameter("view");
		GameViewTestReport page = GameViewTestReport.convertToPage(view);
		String jsp = "";
		switch(page){
		
			default:
				return  jsp = "games/testEtl/gameTestEtl.jsp";
		}
		
	}
	
	@RequestMapping(value = {"/getGamePerform.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameInfo(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		List<Games> gameList = (List<Games>) super.getSession(request, "games");
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		
		String snid =  request.getParameter("snid");
		String gameid = request.getParameter("gameid");
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		
		
//		type.getDisplayName(),game.getName(),
//		game.getSnid(), game.getGameid(),
//		beginDate, step,game.getTerminalType().intValue(),
//		game.getSystemType().intValue(), game.getEtlCurrency()
		
		try{
		
			
			List<Games> filterGameList=new ArrayList<Games>();
			filterGameList.add(games);
			DailyTriggerAllGamesETLEngineJob.triggerGameEtlEngine(Type.ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/runEtls",filterGameList, beginDate,endDate,0, null);

		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<Games> filterTestGames(List<Games> gameList){
		
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
	
	public boolean isReadyTriggerEtl(Games game){
		try {
			
			TimeZone timeZone=TimeZone.getTimeZone("GMT+8");
			if(StringUtils.isNotEmpty(game.getTimeZone())){
				timeZone=TimeZone.getTimeZone(game.getTimeZone());
			}
			Calendar cal=Calendar.getInstance(timeZone);
			String hourOfDay=ReportDateUtils.getHourOfDay(cal.getTime(), timeZone);
			if(!isTriggerHour(hourOfDay)){
				//super.debug("snid:"+game.getSnid()+" gameid:"+game.getGameid()+" hourOfDay:"+hourOfDay+" isTriggerHour:false");
				return false;
			}
			int hourOfDayNum=Integer.parseInt(hourOfDay);
			int etlTriggerIdNum=0;
			if(StringUtils.isNotEmpty(game.getEtlTriggerId())||StringUtils.isNumeric(game.getEtlTriggerId())){
				etlTriggerIdNum=Integer.parseInt(game.getEtlTriggerId());
			}
			if(etlTriggerIdNum>=hourOfDayNum){
				//super.debug("snid:"+game.getSnid()+" gameid:"+game.getGameid()+" hourOfDay:"+hourOfDay+" etlTriggerIdNum:"+etlTriggerIdNum+" etlTriggerIdNum>=hourOfDayNum:false");
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
	
	public boolean isTriggerHour(String hourOfDay){
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
	
}
