package com.hoolai.panel.web.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.manage.model.EtlEngineCleanup;
import com.hoolai.manage.service.EtlEngineCleanupService;
import com.hoolai.manage.vo.EtlEngineCleanupVO;
import com.hoolai.manage.vo.EtlEngineCleanupVO.DataType;
import com.hoolai.panel.web.controller.AbstractPanelController;

/** 
 * <b>类说明：</b>
 * <blockquote>
 *   
 * </blockquote> 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年11月17日 下午1:24:19  
 */


@Controller
public class ETLengineCleanupController extends AbstractPanelController{
	
	private Logger log = Logger.getLogger(ETLengineCleanupController.class);
	
	@Autowired
	private EtlEngineCleanupService etlEngineCleanupService;
	
	
	@RequestMapping(value = {"/panel_manage/EtlengineCleanup/toEtlengineCleanupQuery.ac"}, method = { RequestMethod.GET })
	public String triggerEtlEngineQueryGet(HttpServletRequest request,HttpSession session,
			@ModelAttribute("etlEngineCleanupVO") EtlEngineCleanupVO etlEngineCleanupVO,BindingResult result,Model model)throws Exception {
		
		String type = request.getParameter("type");
		if(StringUtils.isEmpty(type)){
			type = "etl";
		}
		return "manage/cleanup/" + type + "_cleanup_query.jsp";
	}
	
	@RequestMapping(value = {"/panel_manage/EtlengineCleanup/toEtlengineCleanupQuery.ac"}, method = {RequestMethod.POST })
	public String triggerEtlEngineQueryPost(HttpServletRequest request,HttpSession session,
			@ModelAttribute("etlEngineCleanupVO") EtlEngineCleanupVO etlEngineCleanupVO,BindingResult result,Model model)throws Exception {
		
		Map<Long,Games> gamesMap=(Map<Long,Games>)session.getAttribute("gamesMap");
		
		String type = etlEngineCleanupVO.getType();
		if(StringUtils.isEmpty(type)){
			type = "etl";
		}
		
		List<EtlEngineCleanup> validGameList=new ArrayList<EtlEngineCleanup>();
		String[] gameids=etlEngineCleanupVO.getGameids();
		for (String gameid : gameids) {
			if(!StringUtils.isNumeric(gameid)){
				continue;
			}
			Games games=gamesMap.get(Long.parseLong(gameid));
			if(games==null){
				continue;
			}
			EtlEngineCleanup etlEngineCleanup = new EtlEngineCleanup();
			etlEngineCleanup.setGameid(games.getGameid());
			etlEngineCleanup.setSnid(games.getSnid());
			
			etlEngineCleanup.setGameName(games.getName());
			
			validGameList.add(etlEngineCleanup);
		}
		
		Map<String, List<EtlEngineCleanup>> gameProcessMessMap = new HashMap<String, List<EtlEngineCleanup>>();
		String sqlFileName = "";
		if("etl".equals(type)){
			sqlFileName = DataType.ETL.getValue();
			gameProcessMessMap = etlEngineCleanupService.executeHiveQL(validGameList, etlEngineCleanupVO.getBeginDate(),etlEngineCleanupVO.getEndDate(), sqlFileName);
		}else if("facts".equals(type)){
			sqlFileName = DataType.FACTS.getValue();
			gameProcessMessMap = etlEngineCleanupService.executeHiveQL(validGameList, etlEngineCleanupVO.getBeginDate(),etlEngineCleanupVO.getEndDate(), sqlFileName);
		}else if("default".equals(type)){
			sqlFileName = DataType.DEFAULT.getValue();
			gameProcessMessMap = etlEngineCleanupService.executeHiveQL(validGameList, etlEngineCleanupVO.getBeginDate(),etlEngineCleanupVO.getEndDate(), sqlFileName);
		}else{
			sqlFileName = DataType.REPORT.getValue();
			gameProcessMessMap = etlEngineCleanupService.executeMySql(validGameList, etlEngineCleanupVO.getBeginDate(),etlEngineCleanupVO.getEndDate(), sqlFileName);
		}
		
		
		
		model.addAttribute("gameProcessMessMap", gameProcessMessMap);
		model.addAttribute("status", "succ");
		return "manage/cleanup/" + type +"_cleanup_query.jsp";
	}

	public EtlEngineCleanupService getEtlEngineCleanupService() {
		return etlEngineCleanupService;
	}

	public void setEtlEngineCleanupService(
			EtlEngineCleanupService etlEngineCleanupService) {
		this.etlEngineCleanupService = etlEngineCleanupService;
	}

	
	
	/*@RequestMapping(value = {"/panel_manage/etlengine_trigger.ac"}, method = { RequestMethod.GET })
	public String triggerEtlEngineGet(HttpServletRequest request,HttpSession session,
			@ModelAttribute("etlEngineTriggerVO") ETLEngineTriggerVO etlEngineTriggerVO,BindingResult result,Model model)throws Exception {
		
		return "manage/etl/etlengine_trigger.jsp";
	}
	
	@RequestMapping(value = {"/panel_manage/etlengine_trigger.ac"}, method = {RequestMethod.POST })
	public String triggerEtlEnginePost(HttpServletRequest request,HttpSession session,
			@ModelAttribute("etlEngineTriggerVO") ETLEngineTriggerVO etlEngineTriggerVO,BindingResult result,Model model)throws Exception {
		
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
		List<Map<String,String>> gameProcessMess=DailyTriggerAllGamesETLEngineJob.triggerGameEtlEngine(Type.ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/runEtls",validGameList, etlEngineTriggerVO.getBeginDate(),etlEngineTriggerVO.getEndDate(),0);
		gameProcessMessList.addAll(gameProcessMess);
		
		model.addAttribute("gameProcessMessList", gameProcessMessList);
		model.addAttribute("status", "succ");
		return "manage/etl/etlengine_trigger_query.jsp";
	}*/
	
	
	
	

	
}

