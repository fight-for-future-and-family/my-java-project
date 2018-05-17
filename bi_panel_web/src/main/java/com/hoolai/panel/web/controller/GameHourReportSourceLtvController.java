package com.hoolai.panel.web.controller;

import java.io.StringWriter;
import java.sql.ResultSet;
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

import com.hoolai.bi.report.model.Types.GameAnalysisChannel;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.HourLtvSourceDailyReport;
import com.hoolai.bi.report.service.HourDailyReportService;
import com.hoolai.bi.report.service.HourLtvSourceDailyReportService;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.HourLtvSourceDailyReportVO;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;
import com.hoolai.panel.web.job.SeriesGamesJob;
import com.hoolai.panel.web.processor.PrestoJdbcTemplate;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/panel_bi/hourReportSourceLtv")
public class GameHourReportSourceLtvController extends AbstractPanelController{
	
	@Autowired
	private HourDailyReportService hourDailyReportService;
	@Autowired
	private GroupUsersService groupUsersService;
	@Autowired
	private HourLtvSourceDailyReportService hourSourceDailyReportService;
	
	@Autowired
	@Qualifier("etlEngineJdbcTemplate")
	private JdbcTemplate etlEngineJdbcTemplate;
	
	@Autowired
	@Qualifier("prestoJdbcTemplate")
	private JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	@Qualifier("sourceCpaConfiguration")
	private Configuration templateConfiguration;
	
	private Map<String,RunEtlStatus> runEtlSet = new HashMap<String, GameHourReportSourceLtvController.RunEtlStatus>();
	
	@RequestMapping(value = {"/toHourReportSourceLtvView.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		// 在session中存放game实例
		super.setSession(request, "game", games);
		
        String view = request.getParameter("view");
		model.addAttribute("isDisplayHourReport", true);
		
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
			HourLtvSourceDailyReport sourceDailyReport = new HourLtvSourceDailyReport(games.getSnid(),games.getGameid(),null);
			sourceDailyReport.setType("SOURCE");
			HourLtvSourceDailyReportVO sourceDailyReportVO = new HourLtvSourceDailyReportVO(sourceDailyReport);
			sourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			DownLoadCsvProcessor processorSource = new DownLoadCsvProcessor(games, response);
			processorSource.initHourLtvSourceDailyReport(hourSourceDailyReportService.getMatch(sourceDailyReportVO));
			processorSource.writeHourLtvSourceDailyReport();
			break;
				default:
					throw new RuntimeException();
		}
		
		return null;
	}
	
	@RequestMapping(value = {"/redoneLtvHourReport.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> redoneLtvHourReport(HttpServletRequest request,HttpSession session)throws Exception {
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
			HourLtvSourceDailyReport allSourceDailyReport = new HourLtvSourceDailyReport(games.getSnid(),games.getGameid());
			allSourceDailyReport.setType("ALL");
			HourLtvSourceDailyReportVO allSourceDailyReportVO = new HourLtvSourceDailyReportVO(allSourceDailyReport);
			allSourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			List<HourLtvSourceDailyReport> sourceDailyReports = hourSourceDailyReportService.getMatch(allSourceDailyReportVO);
			
			if(sourceDailyReports.size()>0){
				String tmpDate = sourceDailyReports.get(0).getDay();
				int tmpHour = sourceDailyReports.get(0).getHour();
				int tmpMinute = sourceDailyReports.get(0).getMinute()+10;
				
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
		
		if(runEtlSet.get(games.getSnid()+"_"+games.getGameid()) != null){
			ret.put("status", "4");
			ret.put("msg", "本次计算还没有结束,还不能进行重算");
			return ret;
		}
		
		String systemType = "";
		if("0".equals(games.getSystemType().toString())){
			systemType = "ios";
		}else if("1".equals(games.getSystemType().toString())){
			systemType = "android";
		}
		if(isRedone){
			runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
			
			HourLtvSourceDailyReport hourSourceDailyReport = new HourLtvSourceDailyReport();
			hourSourceDailyReport.setSnid(games.getSnid());
			hourSourceDailyReport.setGameid(games.getGameid());
			hourSourceDailyReport.setEndDay(endDate);
			hourSourceDailyReport.setHour(new Integer(endHour));
			hourSourceDailyReportService.removeHourDaily(hourSourceDailyReport);
			
		}
		return ret;
	}
	
	
	//实时日报
	@RequestMapping(value = {"/getHourReportSourceLtv.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getHourReportSourceLtv(HttpServletRequest request,HttpSession session)throws Exception {
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
		
		// 目前只开放当天上一个小时的数据
		Map<String,Object> timeZoneMap = getTimeZone(games, 10);
		beginDate = (String) timeZoneMap.get("date");
		endDate = beginDate;
		beginHour = String.valueOf(timeZoneMap.get("hour"));
		endHour = beginHour;
		minute = String.valueOf(timeZoneMap.get("minute"));

		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(channelStr);
		boolean isPresto = false;
		
		ret.put("game", games);
		ret.put("date", beginDate);
		ret.put("hour", beginHour);
		ret.put("minute", minute);
		session.setAttribute("game", games);
		
		Map<String,Object> pageInfo = findPageInfo(request);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String day = sdf.format(calendar.getTime());
		switch(channel){
		case SOURCE:
			HourLtvSourceDailyReport sourceDailyReport = new HourLtvSourceDailyReport(games.getSnid(),games.getGameid(),source);
			sourceDailyReport.setType("SOURCE");
			sourceDailyReport.setDay(day);
			HourLtvSourceDailyReportVO sourceDailyReportVO = new HourLtvSourceDailyReportVO(sourceDailyReport);
			sourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			if("-99".equals(source)){
				sourceDailyReport.setSource(null);
				
				long recordsTotal = hourSourceDailyReportService.selectCount(sourceDailyReportVO);
				sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
				sourceDailyReportVO.setOrderCol(getSourceOrderCol2((String)pageInfo.get("orderCol")));
				sourceDailyReportVO.setOrderType((String) pageInfo.get("orderType"));
				long recordsFiltered = hourSourceDailyReportService.selectCount(sourceDailyReportVO);
				
				sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
				sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
				List<HourLtvSourceDailyReport> sourceDailyReports = hourSourceDailyReportService.selectList(sourceDailyReportVO);
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", sourceDailyReports);
				
				runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
			}else{
				List<HourLtvSourceDailyReport> sourceDailyReports = hourSourceDailyReportService.getMatch(sourceDailyReportVO);
		        if(sourceDailyReports.isEmpty()){
		        	if(runEtlSet.get(games.getSnid()+"_"+games.getGameid()) != null){
		        		RunEtlStatus status = runEtlSet.get(games.getSnid()+"_"+games.getGameid());
		        		if(status.getHour() == Integer.valueOf(beginHour).intValue()
		        				&& status.spendMinuts() < 1){
		        			ret.put("status", "running");
							ret.put("spendMinuts", status.spendMinuts());
		        		}else{
		        			isPresto = true;
		        			runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
		        		}
		        	}else{
		        		isPresto = true;
		        	}
		        }else{
		        	ret.put("sourceReports", sourceDailyReports);
		        }
			}
			break;
		case ALL://实时日报
		default:
			HourLtvSourceDailyReport allSourceDailyReport = new HourLtvSourceDailyReport(games.getSnid(),games.getGameid());
			allSourceDailyReport.setType("ALL");
			allSourceDailyReport.setDay(day);
			HourLtvSourceDailyReportVO allSourceDailyReportVO = new HourLtvSourceDailyReportVO(allSourceDailyReport);
			allSourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			List<HourLtvSourceDailyReport> sourceDailyReports = hourSourceDailyReportService.getMatch(allSourceDailyReportVO);
			
			if(sourceDailyReports.isEmpty()){
				if(runEtlSet.get(games.getSnid()+"_"+games.getGameid()) != null){
	        		RunEtlStatus status = runEtlSet.get(games.getSnid()+"_"+games.getGameid());
	        		if(status.getHour() == Integer.valueOf(beginHour).intValue()
	        				&& status.spendMinuts() < 1){
	        			ret.put("status", "running");
						ret.put("spendMinuts", status.spendMinuts());
	        		}else{
	        			isPresto = true;
	        			runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
	        		}
	        	}else{
	        		isPresto = true;
	        	}
			}else{
				ret.put("reports", sourceDailyReports);
				runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
			} 
			break;
		}
		
		String systemType = "";
		if("0".equals(games.getSystemType().toString())){
			systemType = "ios";
		}else if("1".equals(games.getSystemType().toString())){
			systemType = "android";
		}

		if(isPresto){
			HourLtvSourceDailyReport hourLtvSourceDailyReport = new HourLtvSourceDailyReport(games.getSnid(), games.getGameid(), "0");
			hourLtvSourceDailyReport.setEndDay(endDate);
			hourLtvSourceDailyReport.setDay(endDate);
			hourLtvSourceDailyReport.setHour(Integer.valueOf(endHour));
			hourLtvSourceDailyReport.setMinute(Integer.valueOf(minute));
			hourLtvSourceDailyReport.setType("ALL");
			
			String allSql = getLtvDailySql(hourLtvSourceDailyReport, systemType);
			PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
			ResultSet rs = null;
			List<HourLtvSourceDailyReport> hourLtvDailyReportList = new ArrayList<HourLtvSourceDailyReport>();
			List<HourLtvSourceDailyReport> hourLtvSourceDailyReportList = new ArrayList<HourLtvSourceDailyReport>();
			runEtlSet.put(games.getSnid()+"_"+games.getGameid(), new RunEtlStatus(games.getSnid(), games.getGameid(),Integer.valueOf(endHour)));
			try{
				rs = prestoJdbcTemplate.exec(allSql);
				hourLtvDailyReportList = (List<HourLtvSourceDailyReport>)(List)SeriesGamesJob.columnToField(rs, HourLtvSourceDailyReport.class);
				hourSourceDailyReportService.saveList(hourLtvDailyReportList);
			} catch (Exception e) {
				e.printStackTrace();
				ret.put("status", "error");
				return ret;
			} finally {
				prestoJdbcTemplate.destory();
			}
			
			hourLtvSourceDailyReport.setSource("1");
			hourLtvSourceDailyReport.setType("SOURCE");
			String sourceSql = getLtvDailySql(hourLtvSourceDailyReport, systemType);
			prestoJdbcTemplate = new PrestoJdbcTemplate();
			try{
				rs = prestoJdbcTemplate.exec(sourceSql);
				hourLtvSourceDailyReportList = (List<HourLtvSourceDailyReport>)(List)SeriesGamesJob.columnToField(rs, HourLtvSourceDailyReport.class);
				hourSourceDailyReportService.saveList(hourLtvSourceDailyReportList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				prestoJdbcTemplate.destory();
			}
			ret.put("status", "running");
			ret.put("spendMinuts", runEtlSet.get(games.getSnid()+"_"+games.getGameid()).spendMinuts());
		}
		
		return ret;
	}
	
	private String getLtvDailySql(HourLtvSourceDailyReport hourLtvSourceDailyReport,String systemType){
		Configuration configuration = new Configuration();
		Template template= null;
		StringWriter sw = new StringWriter();
		try {
			template = templateConfiguration.getTemplate(systemType+"LtvSource.ftl");
			template.process(hourLtvSourceDailyReport, sw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("get sql template error!"+e.getMessage()+" path:"+"templates/ios.ftl");
		}
		return sw.toString();
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
			case 15:
				return "arpu";
			case 16:
				return "arppu";
			case 17:
				return "new_pu";
			case 18:
				return "new_payment";
			case 19:
				return "install_pu";
			case 20:
				return "install_pay_amount";
			case 21:
				return "install_pay_rate";
			}
		}
		return null;
	}
	
	private String getSourceOrderCol2(String orderCol){
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
				return "reg";
			case 4:
				return "d1";
			case 5:
				return "d2";
			case 6:
				return "d3";
			case 7:
				return "d4";
			case 8:
				return "d5";
			case 9:
				return "d6";
			case 10:
				return "d7";
			
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
		
		HourLtvSourceDailyReport sourceDailyReport = new HourLtvSourceDailyReport();
		sourceDailyReport.setSnid(gamesVO.getEntity().getSnid());
		sourceDailyReport.setGameid(gamesVO.getEntity().getGameid());
		
		HourLtvSourceDailyReportVO sourceDailyReportVO = new HourLtvSourceDailyReportVO(sourceDailyReport);
		List<String> gameSources = hourSourceDailyReportService.selectGameSources(sourceDailyReportVO);
		
		ret.put("gameSources", gameSources);
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

