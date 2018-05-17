package com.hoolai.panel.web.controller.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoolai.bi.report.core.Constant;
import com.hoolai.bi.report.etl.ETLEngineGameInfo.Type;
import com.hoolai.bi.report.job.etl.DailyTriggerAllGamesETLEngineJob;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.vo.ETLEngineTriggerVO;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.panel.web.controller.AbstractPanelController;

@Controller
public class ETLEngineTriggerController extends AbstractPanelController{
	
	@Autowired
	private GroupUsersService groupUserService;
	
	@RequestMapping(value = {"/panel_manage/etlengine_trigger_query.ac"}, method = { RequestMethod.GET })
	public String triggerEtlEngineQueryGet(HttpServletRequest request,HttpSession session,
			@ModelAttribute("etlEngineTriggerVO") ETLEngineTriggerVO etlEngineTriggerVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		return "manage/etl/etlengine_trigger_query.jsp";
	}
	
	@RequestMapping(value = {"/panel_manage/etlengine_trigger_query.ac"}, method = {RequestMethod.POST })
	public String triggerEtlEngineQueryPost(HttpServletRequest request,HttpSession session,
			@ModelAttribute("etlEngineTriggerVO") ETLEngineTriggerVO etlEngineTriggerVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		Map<Long,Games> gamesMap=(Map<Long,Games>)session.getAttribute("gamesMap");
		
		List<Games> validGameList=new ArrayList<Games>();
		String[] gameids=etlEngineTriggerVO.getGameids();
		for (String gameid : gameids) {
			if(!StringUtils.isNumeric(gameid)){
				continue;
			}
			Games games=gamesMap.get(Long.parseLong(gameid));
			if(games==null){
				continue;
			}
			validGameList.add(games);
		}
		
		List<Map<String,String>> gameProcessMessList=new ArrayList<Map<String,String>>();
		List<Map<String,String>> gameProcessMess=DailyTriggerAllGamesETLEngineJob.triggerGameEtlEngine(Type.ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/queryRunEtls",validGameList, etlEngineTriggerVO.getBeginDate(),etlEngineTriggerVO.getEndDate(),0,null);
		gameProcessMessList.addAll(gameProcessMess);
		
		model.addAttribute("gameProcessMessList", gameProcessMessList);
		model.addAttribute("status", "succ");
		return "manage/etl/etlengine_trigger_query.jsp";
	}
	
	@RequestMapping(value = {"/panel_manage/etlengine_trigger.ac"}, method = { RequestMethod.GET })
	public String triggerEtlEngineGet(HttpServletRequest request,HttpSession session,
			@ModelAttribute("etlEngineTriggerVO") ETLEngineTriggerVO etlEngineTriggerVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		return "manage/etl/etlengine_trigger.jsp";
	}
	
	@RequestMapping(value = {"/panel_manage/etlengine_trigger.ac"}, method = {RequestMethod.POST })
	public String triggerEtlEnginePost(HttpServletRequest request,HttpSession session,
			@ModelAttribute("etlEngineTriggerVO") ETLEngineTriggerVO etlEngineTriggerVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		Map<Long,Games> gamesMap=(Map<Long,Games>)session.getAttribute("gamesMap");
		
		List<Games> validGameList=new ArrayList<Games>();
		String[] gameids=etlEngineTriggerVO.getGameids();
		for (String gameid : gameids) {
			if(!StringUtils.isNumeric(gameid)){
				continue;
			}
			Games games=gamesMap.get(Long.parseLong(gameid));
			if(games==null){
				continue;
			}
			validGameList.add(games);
		}
		
		List<Map<String,String>> gameProcessMessList=new ArrayList<Map<String,String>>();
		List<Map<String,String>> gameProcessMess=DailyTriggerAllGamesETLEngineJob.triggerGameEtlEngine(Type.ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/runEtls",validGameList, etlEngineTriggerVO.getBeginDate(),etlEngineTriggerVO.getEndDate(),0,null);
		gameProcessMessList.addAll(gameProcessMess);
		
		model.addAttribute("gameProcessMessList", gameProcessMessList);
		model.addAttribute("status", "succ");
		return "manage/etl/etlengine_trigger_query.jsp";
	}
}

