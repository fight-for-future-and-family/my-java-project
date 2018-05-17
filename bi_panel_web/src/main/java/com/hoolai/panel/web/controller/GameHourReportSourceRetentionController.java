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
import com.hoolai.bi.report.model.entity.HourSourceRetentionDailyReport;
import com.hoolai.bi.report.service.HourDailyReportService;
import com.hoolai.bi.report.service.HourLtvSourceDailyReportService;
import com.hoolai.bi.report.service.HourSourceRetentionDailyReportService;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.HourLtvSourceDailyReportVO;
import com.hoolai.bi.report.vo.HourSourceRetentionDailyReportVo;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.panel.web.controller.GameHourReportSourceLtvController.RunEtlStatus;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;
import com.hoolai.panel.web.job.SeriesGamesJob;
import com.hoolai.panel.web.processor.PrestoJdbcTemplate;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/panel_bi/hourReportSourceRetention")
public class GameHourReportSourceRetentionController  extends AbstractPanelController{

	@Autowired
	private HourSourceRetentionDailyReportService hourSourceRetentionDailyReportService; 
	
	@Autowired
	private GroupUsersService groupUsersService;

	@Autowired
	@Qualifier("etlEngineJdbcTemplate")
	private JdbcTemplate etlEngineJdbcTemplate;
	
	@Autowired
	@Qualifier("prestoJdbcTemplate")
	private JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	@Qualifier("sourceCpaConfiguration")
	private Configuration templateConfiguration;
	
	private Map<String,RunEtlStatus> runEtlSet = new HashMap<String, GameHourReportSourceRetentionController.RunEtlStatus>();
	
	@RequestMapping(value = {"/toHourReportSourceRetention.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toHourReportSourceRetention(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		// 在session中存放game实例
		super.setSession(request, "game", games);
		
        String view = request.getParameter("view");
        Date calendar = new Date();
		model.addAttribute("isDisplayHourReport", true);
		
        if("forecastHourReport".equals(view)){
        	//按小时
			return "games/realTime/reportEachTime.jsp";
		}else if("hourReport".equals(view)){
			return "games/hourReport/hourReport.jsp";
		}else if("hourReportSourceLtv".equals(view)){
			return "games/hourReport/hourReportSourceLtv.jsp";
		}else if("hourReportSourceRetention".equals(view)){
			//分渠道留存
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
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String day = sdf.format(calendar.getTime());
		
		switch(channel){
		case SOURCE:
			HourSourceRetentionDailyReport sourceDailyReport = new HourSourceRetentionDailyReport(games.getSnid(),games.getGameid(),null);
			sourceDailyReport.setType("SOURCE");
			sourceDailyReport.setDay(day);
			HourSourceRetentionDailyReportVo sourceDailyReportVO = new HourSourceRetentionDailyReportVo(sourceDailyReport);
			sourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			DownLoadCsvProcessor processorSource = new DownLoadCsvProcessor(games, response);
			processorSource.initHourSourceRetentionDailyReport(hourSourceRetentionDailyReportService.getMatch(sourceDailyReportVO));
			processorSource.writeHourSourceRetentionDailyReport();
			break;
				default:
					throw new RuntimeException();
		}
		
		return null;
	}
	
	@RequestMapping(value = {"/redoneRetentionHourReport.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
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
			HourSourceRetentionDailyReport allSourceDailyReport = new HourSourceRetentionDailyReport(games.getSnid(),games.getGameid());
			allSourceDailyReport.setType("ALL");
			HourSourceRetentionDailyReportVo allSourceDailyReportVO = new HourSourceRetentionDailyReportVo(allSourceDailyReport);
			allSourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			List<HourSourceRetentionDailyReport> sourceDailyReports = hourSourceRetentionDailyReportService.getMatch(allSourceDailyReportVO);
			
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
		
		String systemType = "realTimeRetention";
//		if("0".equals(games.getSystemType().toString())){
//			systemType = "ios";
//		}else if("1".equals(games.getSystemType().toString())){
//			systemType = "android";
//		}
		if(isRedone){
			runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
			
			HourSourceRetentionDailyReport hourSourceDailyReport = new HourSourceRetentionDailyReport();
			hourSourceDailyReport.setSnid(games.getSnid());
			hourSourceDailyReport.setGameid(games.getGameid());
			hourSourceDailyReport.setEndDay(endDate);
			hourSourceDailyReport.setHour(new Integer(endHour));
			hourSourceRetentionDailyReportService.removeHourDaily(hourSourceDailyReport);
			
		}
		return ret;
	}
	
	
	//分渠道留存
	@RequestMapping(value = {"/getHourReportSourceRetention.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
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
			HourSourceRetentionDailyReport sourceDailyReport = new HourSourceRetentionDailyReport(games.getSnid(),games.getGameid(),source);
			sourceDailyReport.setType("SOURCE");
			sourceDailyReport.setDay(day);
			HourSourceRetentionDailyReportVo sourceDailyReportVO = new HourSourceRetentionDailyReportVo(sourceDailyReport);
			sourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			if("-99".equals(source)){
				sourceDailyReport.setSource(null);
				
				long recordsTotal = hourSourceRetentionDailyReportService.selectCount(sourceDailyReportVO);
				sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
				sourceDailyReportVO.setOrderCol(getSourceOrderCol2((String)pageInfo.get("orderCol")));
				sourceDailyReportVO.setOrderType((String) pageInfo.get("orderType"));
				long recordsFiltered = hourSourceRetentionDailyReportService.selectCount(sourceDailyReportVO);
				
				sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
				sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
				List<HourSourceRetentionDailyReport> sourceDailyReports = hourSourceRetentionDailyReportService.selectList(sourceDailyReportVO);
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", sourceDailyReports);
				
				runEtlSet.remove(games.getSnid()+"_"+games.getGameid());
			}else{
				List<HourSourceRetentionDailyReport> sourceDailyReports = hourSourceRetentionDailyReportService.getMatch(sourceDailyReportVO);
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
			
				
			HourSourceRetentionDailyReport allSourceDailyReport = new HourSourceRetentionDailyReport(games.getSnid(),games.getGameid());
			allSourceDailyReport.setDay(day);
			allSourceDailyReport.setType("ALL");
			HourSourceRetentionDailyReportVo allSourceDailyReportVO = new HourSourceRetentionDailyReportVo(allSourceDailyReport);
			allSourceDailyReportVO.setdate(beginDate,endDate,beginHour,endHour);
			
			List<HourSourceRetentionDailyReport> sourceDailyReports = hourSourceRetentionDailyReportService.getMatch(allSourceDailyReportVO);
			System.out.println(sourceDailyReports);
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
		
		String systemType = "realTimeRetention";
//		if("0".equals(games.getSystemType().toString())){
//			systemType = "ios";
//		}else if("1".equals(games.getSystemType().toString())){
//			systemType = "android";
//		}

		if(isPresto){
			HourSourceRetentionDailyReport hourSourceRetentionDailyReport = new HourSourceRetentionDailyReport(games.getSnid(), games.getGameid(), "0");
			hourSourceRetentionDailyReport.setEndDay(endDate);
			hourSourceRetentionDailyReport.setDay(endDate);
			hourSourceRetentionDailyReport.setHour(Integer.valueOf(endHour));
			hourSourceRetentionDailyReport.setMinute(Integer.valueOf(minute));
			hourSourceRetentionDailyReport.setSource("0");
			hourSourceRetentionDailyReport.setType("ALL");
			
			String allSql = getLtvDailySql(hourSourceRetentionDailyReport, systemType);
			PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
			ResultSet rs = null;
			List<HourSourceRetentionDailyReport> hourRetentionDailyReportList = new ArrayList<HourSourceRetentionDailyReport>();
			List<HourSourceRetentionDailyReport> hourSourceRetentionDailyReportList = new ArrayList<HourSourceRetentionDailyReport>();
			runEtlSet.put(games.getSnid()+"_"+games.getGameid(), new RunEtlStatus(games.getSnid(), games.getGameid(),Integer.valueOf(endHour)));
			try{
				rs = prestoJdbcTemplate.exec(allSql);
				hourRetentionDailyReportList = (List<HourSourceRetentionDailyReport>)(List)SeriesGamesJob.columnToField(rs, HourSourceRetentionDailyReport.class);
				hourSourceRetentionDailyReportService.saveList(hourRetentionDailyReportList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				prestoJdbcTemplate.destory();
			}
			
			hourSourceRetentionDailyReport.setSource("1");
			hourSourceRetentionDailyReport.setType("SOURCE");
			String sourceSql = getLtvDailySql(hourSourceRetentionDailyReport, systemType);
			prestoJdbcTemplate = new PrestoJdbcTemplate();
			try{
				rs = prestoJdbcTemplate.exec(sourceSql);
				hourSourceRetentionDailyReportList = (List<HourSourceRetentionDailyReport>)(List)SeriesGamesJob.columnToField(rs, HourSourceRetentionDailyReport.class);
				hourSourceRetentionDailyReportService.saveList(hourSourceRetentionDailyReportList);
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
	
	private String getLtvDailySql(HourSourceRetentionDailyReport hourSourceRetentionDailyReport,String systemType){
		Configuration configuration = new Configuration();
		Template template= null;
		StringWriter sw = new StringWriter();
		try {
			template = templateConfiguration.getTemplate(systemType+".ftl");
			template.process(hourSourceRetentionDailyReport, sw);
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
//			case 1:
//				return "hour";
			case 1:
				return "source";
			case 2:
				return "reg";
			case 3:
				return "d1";
			case 4:
				return "d2";
			case 5:
				return "d3";
			case 6:
				return "d4";
			case 7:
				return "d5";
			case 8:
				return "d6";
			case 9:
				return "d7";
			
			}
		}
		return null;
	}
	//查詢所有渠道
	@RequestMapping(value = {"/getGameSource.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameSource(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		GamesVO gamesVO = new GamesVO(super.getSessionGames(request));
		
		HourSourceRetentionDailyReport sourceDailyReport = new HourSourceRetentionDailyReport();
		sourceDailyReport.setSnid(gamesVO.getEntity().getSnid());
		sourceDailyReport.setGameid(gamesVO.getEntity().getGameid());
		
		HourSourceRetentionDailyReportVo sourceDailyReportVO = new HourSourceRetentionDailyReportVo(sourceDailyReport);
		List<String> gameSources = hourSourceRetentionDailyReportService.selectGameSources(sourceDailyReportVO);
		
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
