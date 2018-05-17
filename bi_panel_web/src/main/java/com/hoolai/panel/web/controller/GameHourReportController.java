package com.hoolai.panel.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
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
import com.hoolai.bi.report.model.Types.GameAnalysisChannel;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.HourClientDailyReport;
import com.hoolai.bi.report.model.entity.HourDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.service.HourClientDailyReportService;
import com.hoolai.bi.report.service.HourDailyReportService;
import com.hoolai.bi.report.service.HourSourceDailyReportService;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.HourClientDailyReportVO;
import com.hoolai.bi.report.vo.HourDailyReportVO;
import com.hoolai.bi.report.vo.HourSourceDailyReportVO;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;

@Controller
@RequestMapping("/panel_bi/hourReport")
public class GameHourReportController extends AbstractPanelController{
	
	@Autowired
	private HourDailyReportService hourDailyReportService;
	@Autowired
	private GroupUsersService groupUsersService;
	@Autowired
	private HourSourceDailyReportService hourSourceDailyReportService;
	@Autowired
	private HourClientDailyReportService hourClientDailyReportService;
	@Autowired
	@Qualifier("etlEngineJdbcTemplate")
	private JdbcTemplate etlEngineJdbcTemplate;
	
	private Map<String,RunEtlStatus> runEtlSet = new HashMap<String, GameHourReportController.RunEtlStatus>();
	
	@RequestMapping(value = {"/toHourReportView.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		// 在session中存放game实例
		super.setSession(request, "game", games);
		
        String view = request.getParameter("view");
        Date calendar = new Date();
//		if(games.getOnlineDate() != null 
//			&& (calendar.getTime() - games.getOnlineDate().getTime()) <= GameRealTimeController.INTERVAL_TIME){
			model.addAttribute("isDisplayHourReport", true);
//		}else{
//			model.addAttribute("isDisplayHourReport", false);
//		}
		
        if("forecastHourReport".equals(view)){
        	//按小时
			return "games/realTime/reportEachTime.jsp";
		}else if("hourReport".equals(view)){
			return "games/hourReport/hourReport.jsp";
		}else if("hourReportSourceLtv".equals(view)){
			return "games/hourReport/hourReportSourceLtv.jsp";
		}else if("hourReportSourceRetention".equals(view)){
			return "games/hourReport/hourReportSourceRetention.jsp";
		}else{
			//实时
			return "games/realTime/gameRealTime.jsp";
		}
	}
	
	@RequestMapping(value = {"/downloadData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		String channelStr = request.getParameter("channel");
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String beginHour = request.getParameter("beginHour");
		String endHour = request.getParameter("endHour");
		
		// 目前只开放当天上一个小时的数据
		Map<String,Object> timeZoneMap = getTimeZone(games, 10);
		beginDate = (String) timeZoneMap.get("date");
		endDate = beginDate;
		beginHour = String.valueOf(timeZoneMap.get("hour"));
		endHour = beginHour;
		
		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(channelStr);
		
		switch(channel){
		case SOURCE:
			HourSourceDailyReport sourceDailyReport = new HourSourceDailyReport(games.getSnid(),games.getGameid(),null);
			HourSourceDailyReportVO sourceDailyReportVO = new HourSourceDailyReportVO(sourceDailyReport);
			sourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			DownLoadCsvProcessor processorSource = new DownLoadCsvProcessor(games, response);
			processorSource.initHourSourceDailyReport(hourSourceDailyReportService.getMatch(sourceDailyReportVO));
			processorSource.writeHourSourceDailyReport();
			break;
		case CLIENT:
			HourClientDailyReport clientDailyReport = new HourClientDailyReport(games.getSnid(),games.getGameid(),null);
			HourClientDailyReportVO clientDailyReportVO = new HourClientDailyReportVO(clientDailyReport);
			clientDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			DownLoadCsvProcessor processorClient = new DownLoadCsvProcessor(games, response);
			processorClient.initHourClientDailyReport(hourClientDailyReportService.getMatch(clientDailyReportVO));
			processorClient.writeHourClientDailyReport();
			break;
			
				default:
					throw new RuntimeException();
		}
		
		return null;
	}
	
	@RequestMapping(value = {"/redoneHourReport.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> redoneHourReport(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		boolean isRedone = false;
		boolean isAdmin = groupUsersService.isAdmin(users.getId());
		
		Map<String,Object> timeZoneMap = new HashMap<String, Object>();
		if(!isAdmin) {
			timeZoneMap = getTimeZone(games, 15);
		}else{
			timeZoneMap = getTimeZone(games, 10);
			isRedone = true;
		}
		
		String beginDate = (String) timeZoneMap.get("date");
		String endDate = beginDate;
		String beginHour = String.valueOf(timeZoneMap.get("hour"));
		String endHour = beginHour;
		String minute = String.valueOf(timeZoneMap.get("minute"));
		
		if(!isAdmin){
			HourDailyReport dailyReport = new HourDailyReport(games.getSnid(),games.getGameid());
			HourDailyReportVO dailyReportVO = new HourDailyReportVO(dailyReport);
			dailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			List<HourDailyReport> hourDailyReports = hourDailyReportService.getMatch(dailyReportVO);
			if(hourDailyReports.size()>0){
				String tmpDate = hourDailyReports.get(0).getDay();
				int tmpHour = hourDailyReports.get(0).getHour();
				int tmpMinute = hourDailyReports.get(0).getMinute()+10;
				
				if(tmpMinute >= 60){
					tmpMinute = tmpMinute - 60;
					tmpHour = tmpHour + 1;
				}
				
				if(tmpHour > Integer.valueOf(beginHour)){
					ret.put("hour", String.valueOf(tmpHour));
					ret.put("minute", String.valueOf(tmpMinute));
					ret.put("status", "3");
				}else if(tmpHour == Integer.valueOf(beginHour)){
					if(tmpMinute <= Integer.valueOf(minute)){
						isRedone = true;
					}else{
						ret.put("hour", String.valueOf(tmpHour));
						ret.put("minute", String.valueOf(tmpMinute));
						ret.put("status", "3");
					}
				}else if(tmpHour < Integer.valueOf(beginHour)){
					isRedone = true;
				}
			}
		}
		
		if(isRedone){
			runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
			JdbcTemplate etlEngineV4JdbcTemplate = new JdbcTemplate(this.etlEngineJdbcTemplate.getDataSource());
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			sdf = new SimpleDateFormat("HH");
			calendar.add(Calendar.HOUR_OF_DAY, -1);
			String hourStr = String.valueOf(Integer.parseInt(sdf.format(calendar.getTime())));
			String sql = "select status from task_status where name='quasi-etl-engine-run' and date(runing_time)='"+
					endDate+"' and code='quasi-etl-engine-run_"+games.getSnid()+"_"+games.getGameid()+"_"+endDate+"_"+endHour+"' and status!=0";
			List<Map<String,Object>> hiveDatas = new ArrayList<Map<String,Object>>();
			hiveDatas = etlEngineV4JdbcTemplate.queryForList(sql);
			if(hiveDatas.size()>0 && !"0".equals(hiveDatas.get(0).get("status"))){
				sql = "delete from task_status where name='quasi-etl-engine-run' and date(runing_time)='"+
						endDate+"' and code='quasi-etl-engine-run_"+games.getSnid()+"_"+games.getGameid()+"_"+endDate+"_"+endHour+"' and status!=0";
				etlEngineV4JdbcTemplate.execute(sql);
				HourDailyReport hourDailyReport = new HourDailyReport();
				hourDailyReport.setSnid(games.getSnid());
				hourDailyReport.setGameid(games.getGameid());
				hourDailyReport.setDay(endDate);
				hourDailyReport.setHour(new Integer(endHour));
				hourDailyReportService.removeHourDaily(hourDailyReport);
				HourSourceDailyReport hourSourceDailyReport = new HourSourceDailyReport();
				hourSourceDailyReport.setSnid(games.getSnid());
				hourSourceDailyReport.setGameid(games.getGameid());
				hourSourceDailyReport.setDay(endDate);
				hourSourceDailyReport.setHour(new Integer(endHour));
				hourSourceDailyReportService.removeHourDaily(hourSourceDailyReport);
				HourClientDailyReport hourClientDailyReport = new HourClientDailyReport();
				hourClientDailyReport.setSnid(games.getSnid());
				hourClientDailyReport.setGameid(games.getGameid());
				hourClientDailyReport.setDay(endDate);
				hourClientDailyReport.setHour(new Integer(endHour));
				hourClientDailyReportService.removeHourDaily(hourClientDailyReport);
				ret.put("status", hiveDatas.get(0).get("status"));
			}
		}
		return ret;
	}
	
	
	//实时日报
	@RequestMapping(value = {"/getHourReport.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getHourReport(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String channelStr = request.getParameter("channel");
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String beginHour = request.getParameter("beginHour");
		String endHour = request.getParameter("endHour");
		String minute = null;
		String source = request.getParameter("source");
		String clientid = request.getParameter("clientid");
		
		// 目前只开放当天上一个小时的数据
		Map<String,Object> timeZoneMap = getTimeZone(games, 10);
		beginDate = (String) timeZoneMap.get("date");
		endDate = beginDate;
		beginHour = String.valueOf(timeZoneMap.get("hour"));
		endHour = beginHour;
		minute = String.valueOf(timeZoneMap.get("minute"));
		
		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(channelStr);
		boolean isEtl = false;
		
		ret.put("game", games);
		ret.put("date", beginDate);
		ret.put("hour", beginHour);
		ret.put("minute", minute);
		session.setAttribute("game", games);
		
		Map<String,Object> pageInfo = findPageInfo(request);
		
//		channel = GameAnalysisChannel.convertToChannel("client");
		switch(channel){
		case SOURCE:
			HourSourceDailyReport sourceDailyReport = new HourSourceDailyReport(games.getSnid(),games.getGameid(),source);
			HourSourceDailyReportVO sourceDailyReportVO = new HourSourceDailyReportVO(sourceDailyReport);
			sourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			if("-99".equals(source)){
				sourceDailyReport.setSource(null);
				
				long recordsTotal = hourSourceDailyReportService.selectCount(sourceDailyReportVO);
				sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
				sourceDailyReportVO.setOrderCol(getSourceOrderCol((String)pageInfo.get("orderCol")));
				sourceDailyReportVO.setOrderType((String) pageInfo.get("orderType"));
				long recordsFiltered = hourSourceDailyReportService.selectCount(sourceDailyReportVO);
				
				sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
				sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
				List<HourSourceDailyReport> sourceDailyReports = hourSourceDailyReportService.selectList(sourceDailyReportVO);
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", sourceDailyReports);
				
				runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
			}else{
				List<HourSourceDailyReport> sourceDailyReports = hourSourceDailyReportService.getMatch(sourceDailyReportVO);
		        if(sourceDailyReports.isEmpty()){
		        	if(runEtlSet.get(games.getSnid()+"_"+games.getGameid()) != null){
		        		RunEtlStatus status = runEtlSet.get(games.getSnid()+"_"+games.getGameid());
		        		if(status.getHour() == Integer.valueOf(beginHour).intValue()
		        				&& status.spendMinuts() < 1){
		        			ret.put("status", "running");
							ret.put("spendMinuts", status.spendMinuts());
		        		}else{
		        			isEtl = true;
		        			runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
		        		}
		        	}else{
		        		isEtl = true;
		        	}
		        }else{
		        	ret.put("sourceReports", sourceDailyReports);
		        }
			}
			break;
		case CLIENT:
			HourClientDailyReport clientDailyReport = new HourClientDailyReport(games.getSnid(),games.getGameid(),clientid);
			HourClientDailyReportVO clientDailyReportVO = new HourClientDailyReportVO(clientDailyReport);
			clientDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			if("-99".equals(source)){
				clientDailyReport.setClientid(null);
				long recordsTotal = hourClientDailyReportService.selectCount(clientDailyReportVO);
				clientDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
				clientDailyReportVO.setOrderCol(getClientOrderCol((String)pageInfo.get("orderCol")));
				clientDailyReportVO.setOrderType((String) pageInfo.get("orderType"));
				long recordsFiltered = hourClientDailyReportService.selectCount(clientDailyReportVO);
				
				clientDailyReportVO.setOffset((Long) pageInfo.get("start"));
				clientDailyReportVO.setRows((Long) pageInfo.get("length"));
				List<HourClientDailyReport> clientDailyReports = hourClientDailyReportService.selectList(clientDailyReportVO);
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", clientDailyReports);
				
				runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
			}else{
				List<HourClientDailyReport> clientDailyReports = hourClientDailyReportService.getMatch(clientDailyReportVO);
				if(clientDailyReports.isEmpty()){
		        	if(runEtlSet.get(games.getSnid()+"_"+games.getGameid()) != null){
		        		RunEtlStatus status = runEtlSet.get(games.getSnid()+"_"+games.getGameid());
		        		if(status.getHour() == Integer.valueOf(beginHour).intValue()
		        				&& status.spendMinuts() < 1){
		        			ret.put("status", "running");
							ret.put("spendMinuts", status.spendMinuts());
		        		}else{
		        			isEtl = true;
		        			runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
		        		}
		        	}else{
		        		isEtl = true;
		        	}
		        }else{
		        	ret.put("clientReports", clientDailyReports);
		        }
			}
//			List<HourClientDailyReport> clientDailyReports = hourClientDailyReportService.getMatch(clientDailyReportVO);
//			
//			if(clientDailyReports.isEmpty()){
//	        	isEtl = true;
//	        }else{
//	        	ret.put("clientReports", clientDailyReports);
//	        }
				
			break;
		case ALL://实时日报
		default:
			HourDailyReport dailyReport = new HourDailyReport(games.getSnid(),games.getGameid());
			HourDailyReportVO dailyReportVO = new HourDailyReportVO(dailyReport);
			dailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			List<HourDailyReport> hourDailyReports = hourDailyReportService.getMatch(dailyReportVO);
			
			if(hourDailyReports.isEmpty()){
				if(runEtlSet.get(games.getSnid()+"_"+games.getGameid()) != null){
	        		RunEtlStatus status = runEtlSet.get(games.getSnid()+"_"+games.getGameid());
	        		if(status.getHour() == Integer.valueOf(beginHour).intValue()
	        				&& status.spendMinuts() < 1){
	        			ret.put("status", "running");
						ret.put("spendMinuts", status.spendMinuts());
	        		}else{
	        			isEtl = true;
	        			runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
	        		}
	        	}else{
	        		isEtl = true;
	        	}
			}else{
				ret.put("reports", hourDailyReportService.getMatch(dailyReportVO));
				runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
			} 
			break;
		}
		
		if(isEtl){
//			System.out.println(games.getSnid() + "," +games.getGameid() + "查询...");
			
			List<Games> filterGameList=new ArrayList<Games>();
			filterGameList.add(games);
			
			// 查询这个时间段的etl进程有没有，没有触发，有则查询完成进度
			List<Map<String,String>> list = DailyTriggerAllGamesETLEngineJob
					.triggerGameEtlEngine(Type.QUASI_ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/queryRunEtls",
							filterGameList, beginDate,endDate,Integer.valueOf(beginHour), minute);
			int status = -1;
			//没有进程触发
			if(list.isEmpty()){
//				System.out.println(games.getSnid() + "," +games.getGameid() + "触发etl...");
				DailyTriggerAllGamesETLEngineJob
				.triggerGameEtlEngine(Type.QUASI_ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/runEtls",
						filterGameList, beginDate,endDate,Integer.valueOf(beginHour), minute);
				ret.put("status", "running");
				ret.put("spendMinuts", 0);
				
				runEtlSet.put(games.getSnid()+"_"+games.getGameid(), new RunEtlStatus(games.getSnid(), games.getGameid(),Integer.valueOf(beginHour)));
			}else{
				
				if(!StringUtils.isEmpty(list.get(0).get("taskStatus"))){
					status = Integer.valueOf(list.get(0).get("taskStatus"));
					ret.put("status",  status== 0 ? "running" : status == 1 ? "finished" : "error");
					ret.put("spendMinuts", list.get(0).get("spendMinuts"));
				}else{
					ret.put("status", "error");
				}
//				System.out.println(games.getSnid() + "," +games.getGameid() + "有进程，查询状态...status："+status);
			}
			
//			if(status == 1){//这种极端的情况不知道有没有
////				System.out.println(games.getSnid() + "," +games.getGameid() + "status等于1，查询状态...status："+status);
//				return this.getHourReport(request, session);
//			}
		}
		
		return ret;
	}
	
	private String getClientOrderCol(String orderCol){
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "day";
			case 1:
				return "hour";
			case 2:
				return "clientid";
			case 3:
				return "dnu";
			case 4:
				return "dau";
			case 5:
				return "loyal_user";
			case 6:
				return "pcu";
			case 7:
				return "acu";
			case 8:
				return "avg_user_time";
			case 9:
				return "pu";
			case 10:
				return "payment";
			case 11:
				return "payment_times";
			case 12:
				return "pay_rate";
			case 13:
				return "arpu";
			case 14:
				return "arppu";
			case 15:
				return "arppu";
			}
		}
		return null;
	}
	
	private String getSourceOrderCol(String orderCol){
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "day";
			case 1:
				return "hour";
			case 2:
				return "source";
			case 3:
				return "dnu";
			case 4:
				return "new_equip";
			case 5:
				return "dau";
			case 6:
				return "loyal_user";
			case 7:
				return "pcu";
			case 8:
				return "acu";
			case 9:
				return "avg_user_time";
			case 10:
				return "d1";
			case 11:
				return "pu";
			case 12:
				return "payment";
			case 13:
				return "payment_times";
			case 14:
				return "pay_rate";
			case 16:
				return "arpu";
			case 17:
				return "arppu";
			case 18:
				return "new_pu";
			case 19:
				return "new_payment";
			case 20:
				return "install_pu";
			case 21:
				return "install_pay_amount";
			case 22:
				return "install_pay_rate";
			}
		}
		return null;
	}

	@RequestMapping(value = {"/getGameSource.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameSource(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		GamesVO gamesVO = new GamesVO(super.getSessionGames(request));
		
		HourSourceDailyReport sourceDailyReport = new HourSourceDailyReport();
		sourceDailyReport.setSnid(gamesVO.getEntity().getSnid());
		sourceDailyReport.setGameid(gamesVO.getEntity().getGameid());
		
		HourSourceDailyReportVO sourceDailyReportVO = new HourSourceDailyReportVO(sourceDailyReport);
//		Map<String,Object> timeZoneMap = getTimeZone(gamesVO.getEntity());
//		sourceDailyReportVO.setBeginDate((String) timeZoneMap.get("date"));
//		sourceDailyReportVO.setEndDate(sourceDailyReportVO.getBeginDate());
//		sourceDailyReportVO.setBeginHour(String.valueOf(timeZoneMap.get("hour")));
//		sourceDailyReportVO.setEndHour(sourceDailyReportVO.getEndHour());
		
		List<String> gameSources = hourSourceDailyReportService.selectGameSources(sourceDailyReportVO);
		
		ret.put("gameSources", gameSources);
		return ret;
	}
	
	@RequestMapping(value = {"/getGameClient.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameClient(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		GamesVO gamesVO = new GamesVO(super.getSessionGames(request));
		
		HourClientDailyReport clientDailyReport = new HourClientDailyReport();
		clientDailyReport.setSnid(gamesVO.getEntity().getSnid());
		clientDailyReport.setGameid(gamesVO.getEntity().getGameid());
		
		HourClientDailyReportVO clientDailyReportVO = new HourClientDailyReportVO(clientDailyReport);
//		Map<String,Object> timeZoneMap = getTimeZone(gamesVO.getEntity());
//		clientDailyReportVO.setBeginDate((String) timeZoneMap.get("date"));
//		clientDailyReportVO.setEndDate(clientDailyReportVO.getBeginDate());
//		clientDailyReportVO.setBeginHour(String.valueOf(timeZoneMap.get("hour")));
//		clientDailyReportVO.setEndHour(clientDailyReportVO.getEndHour());
		
		List<String> gameClients = hourClientDailyReportService.selectGameClients(clientDailyReportVO);
		
		ret.put("gameClients", gameClients);
		return ret;
	}
	
	private Map<String, Object> getTimeZone(Games game, int num) {
		Map<String, Object> timeZoneMap = new HashMap<String, Object>();

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int minute = calendar.get(Calendar.MINUTE);
		int hour = 0;
		if(minute < num){
			hour = calendar.get(Calendar.HOUR_OF_DAY)-1;
			calendar.set(Calendar.MINUTE, -num);
			minute += calendar.get(Calendar.MINUTE);
			minute = minute>=60 ? minute-60 : minute;
		}else {
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minute = minute - num;
		}
		if(hour<0){
			hour += 24;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		timeZoneMap.put("hour", hour);
		timeZoneMap.put("date", sdf.format(calendar.getTime()));
		timeZoneMap.put("minute", minute);
		
		return timeZoneMap;
	}
	
	class RunEtlStatus{
		private String snid;
		private String gameid;
		private String status;
		private Date runBeginTime;
		private int hour;
		
		public RunEtlStatus(String snid,String gameid,int hour){
			this.snid = snid;
			this.gameid = gameid;
			this.hour = hour;
			this.status="running";
			runBeginTime = new Date();
		}
		
		public long spendMinuts(){
			return ((new Date().getTime()-runBeginTime.getTime())/(60*1000));
		}

		public String getSnid() {
			return snid;
		}

		public void setSnid(String snid) {
			this.snid = snid;
		}

		public String getGameid() {
			return gameid;
		}

		public void setGameid(String gameid) {
			this.gameid = gameid;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Date getRunBeginTime() {
			return runBeginTime;
		}

		public void setRunBeginTime(Date runBeginTime) {
			this.runBeginTime = runBeginTime;
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
			this.hour = hour;
		}
	}
}

