package com.hoolai.panel.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.monitor.service.GameAlarmUserService;
import com.hoolai.bi.monitor.vo.MonitorMetricVO;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.MonitorMetrics;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.MonitorMetricsService;
import com.hoolai.bi.report.service.UserGamesService;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;

@Controller
@RequestMapping("/panel_bi/monitor_metric")
public class MonitorMetricController extends AbstractPanelController{
	
	@Autowired
	private GamesService gamesService;
	
	@Autowired
	private GroupUsersService groupUserService;
	
	@Autowired
	private GameAlarmUserService gameAlarmUserService;
	
	@Autowired
	private UserGamesService userGamesService;
	
	@Autowired
	private MonitorMetricsService monitorMetricService;
	

	@RequestMapping(value = {"/toList.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String toList(HttpServletRequest request,HttpSession session,@ModelAttribute("monitorMetrics") MonitorMetrics monitorMetrics,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		Games game=super.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		return "games/monitormetric/monitorList.jsp";
	}
	
	@RequestMapping(value = {"/getMonitorListData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getWanDaGameDatasView(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		Users users = getSessionUsers(request);
		Games game=super.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		MonitorMetrics monitorMetric = new MonitorMetrics(game.getSnid(), game.getGameid());
		if(request.getParameter("id")!=null&request.getParameter("id")!="") {
			monitorMetric.setId(Long.valueOf(request.getParameter("id")));
		}
		
		List<MonitorMetrics> monitorMetricList = new ArrayList<MonitorMetrics>();
		monitorMetricList = convertType(monitorMetricService.selectList(monitorMetric));
		
		ret.put("data", monitorMetricList);
		return ret;
	}
	
	private List<MonitorMetrics> convertType(List<MonitorMetrics> monitorMetricList) {
		for (MonitorMetrics monitorMetrics : monitorMetricList) {
			if("dnu".equals(monitorMetrics.getColumnName())) {
				monitorMetrics.setColumnName("新增用户数");
			}else if("dau".equals(monitorMetrics.getColumnName())) {
				monitorMetrics.setColumnName("活跃用户数");
			}else if("payer".equals(monitorMetrics.getColumnName())) {
				monitorMetrics.setColumnName("付费用户数");
			}else if("totalAmount".equals(monitorMetrics.getColumnName())) {
				monitorMetrics.setColumnName("付费金额");
			}
		}
		
		return monitorMetricList;
	}
	
	@RequestMapping(value = {"/toAdd.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String toAdd(HttpServletRequest request,HttpSession session,@ModelAttribute("monitorMetrics") MonitorMetrics monitorMetrics,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		Games game=super.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		return "games/monitormetric/monitorAdd.jsp";
	}
	
	
	
	@RequestMapping(value = {"/addMonitorListData.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String addMonitorListData(HttpServletRequest request,HttpSession session,@ModelAttribute("monitorMetrics") MonitorMetrics monitorMetrics,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games game=super.getSessionGames(request);
		monitorMetrics.setSnid(game.getSnid());
		monitorMetrics.setGameid(game.getGameid());
		if(monitorMetrics.getLowerLimit()==null) {
			monitorMetrics.setLowerLimit(0d);
		}
		if(monitorMetrics.getTopLimit()==null) {
			monitorMetrics.setTopLimit(0d);
		}
		
		int num = monitorMetricService.saveMonitorMetrics(monitorMetrics);
		
		return super.redirect("/panel_bi/monitor_metric/toList.ac?gameId="+game.getGameid()+"&snid="+game.getSnid());
	}
	
	@RequestMapping(value = {"/selectMonitorMetricExists.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> selectMonitorMetricExists(HttpServletRequest request,HttpSession session,@ModelAttribute("monitorMetricVO") MonitorMetricVO monitorMetricVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games game=super.getSessionGames(request);
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String snid = request.getParameter("snid");
		String gameid= request.getParameter("gameId");
		String columnName = request.getParameter("columnName");
		MonitorMetrics monitorMetrics = new MonitorMetrics(snid, gameid);
		monitorMetrics.setColumnName(columnName);
		int count = monitorMetricService.selectMonitorMetricExists(monitorMetrics);
		
		ret.put("count", String.valueOf(count));
		return ret;
	}
	
	@RequestMapping(value = {"/editMonitorListData.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> editMonitorListData(HttpServletRequest request,HttpSession session,@ModelAttribute("monitorMetricVO") MonitorMetricVO monitorMetricVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games game=super.getSessionGames(request);
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String lowerLimit = request.getParameter("lowerLimit");
		String topLimit = request.getParameter("topLimit");
		boolean isEmail = Boolean.valueOf(request.getParameter("isEmail"));
		boolean isPhone = Boolean.valueOf(request.getParameter("isPhone"));
		
		MonitorMetrics monitorMetric = new MonitorMetrics(game.getSnid(), game.getGameid());
		if(request.getParameter("id")!=null&request.getParameter("id")!="") {
			monitorMetric.setId(Long.valueOf(request.getParameter("id")));
		}
		if(StringUtils.isEmpty(lowerLimit)) {
			monitorMetric.setLowerLimit(0d);
		}else{
			monitorMetric.setLowerLimit(Double.valueOf(lowerLimit));
		}
		if(StringUtils.isEmpty(topLimit)) {
			monitorMetric.setTopLimit(0d);
		}else{
			monitorMetric.setTopLimit(Double.valueOf(topLimit));
		}
		monitorMetric.setIsEmail(isEmail);
		monitorMetric.setIsPhone(isPhone);
		
		int num = monitorMetricService.updateById(monitorMetric);
		
		ret.put("msg", "success");
		return ret;
	}
	
	@RequestMapping(value = {"/delMonitorListData.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delMonitorListData(HttpServletRequest request,HttpSession session,@ModelAttribute("monitorMetricVO") MonitorMetricVO monitorMetricVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games game=super.getSessionGames(request);
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String lowerLimit = request.getParameter("lowerLimit");
		String topLimit = request.getParameter("topLimit");
		
		MonitorMetrics monitorMetric = new MonitorMetrics(game.getSnid(), game.getGameid());
		if(request.getParameter("id")!=null&request.getParameter("id")!="") {
			monitorMetric.setId(Long.valueOf(request.getParameter("id")));
		}
		int num = monitorMetricService.deleteById(monitorMetric);
		
		ret.put("msg", "success");
		return ret;
		
	}

	
	
}

