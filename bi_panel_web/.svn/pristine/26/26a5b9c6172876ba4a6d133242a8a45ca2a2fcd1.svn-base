package com.hoolai.panel.web.controller;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.Types.GameDataPage;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.HourClientDailyReport;
import com.hoolai.bi.report.model.entity.HourSourceDailyReport;
import com.hoolai.bi.report.model.entity.RealTimeGame;
import com.hoolai.bi.report.model.entity.RealTimeGameClient;
import com.hoolai.bi.report.model.entity.RealTimeGameSource;
import com.hoolai.bi.report.model.entity.RealTimeNoClientResult;
import com.hoolai.bi.report.model.entity.RealTimeResult;
import com.hoolai.bi.report.model.entity.RealtimeSourceReport;
import com.hoolai.bi.report.model.entity.ReportingEachTimeNC;
import com.hoolai.bi.report.model.entity.SeriesAll;
import com.hoolai.bi.report.model.entity.SeriesGame;
import com.hoolai.bi.report.model.entity.TestReportSbwd;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.RealTimeGameClientidService;
import com.hoolai.bi.report.service.RealTimeGameSourceService;
import com.hoolai.bi.report.service.RealTimeNoClientResultService;
import com.hoolai.bi.report.service.RealTimeResultService;
import com.hoolai.bi.report.service.RealTimeGamesService;
import com.hoolai.bi.report.service.RealtimeSourceReportService;
import com.hoolai.bi.report.service.ReportingEachTimeNCService;
import com.hoolai.bi.report.service.SeriesAllService;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.HourClientDailyReportVO;
import com.hoolai.bi.report.vo.HourSourceDailyReportVO;
import com.hoolai.bi.report.vo.RealTimeGameClientVo;
import com.hoolai.bi.report.vo.RealTimeGameSourceVo;
import com.hoolai.bi.report.vo.RealTimeGameVo;
import com.hoolai.bi.report.vo.RealTimeNoClientResultVO;
import com.hoolai.bi.report.vo.RealTimeResultVO;
import com.hoolai.bi.report.vo.RealtimeSourceReportVO;
import com.hoolai.bi.report.vo.ReportingEachTimeNCVO;
import com.hoolai.manage.model.Platforms;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.PlatformsService;
import com.hoolai.manage.vo.PlatformsVO;
import com.hoolai.manage.vo.SysConfigVO;
import com.hoolai.panel.web.controller.GameHourReportSourceRetentionController.RunEtlStatus;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;
import com.hoolai.panel.web.job.RealTimeGamesJob;
import com.hoolai.panel.web.job.SeriesGamesJob;
import com.hoolai.panel.web.processor.PrestoJdbcTemplate;
import com.hoolai.panel.web.testReport.PrestoTestReport;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/panel_bi/gameRealTime")
public class GameRealTimeController extends AbstractPanelController{
	
	public  static final long INTERVAL_TIME = 2l * 30l * 24l * 60l * 60l * 1000l;
	@Autowired
	private RealTimeNoClientResultService realTimeNoClientResultService;
	@Autowired
	private RealTimeResultService realTimeResultService;
	@Autowired
	private ReportingEachTimeNCService reportingEachTimeNCService;
	@Autowired
	private RealtimeSourceReportService realtimeSourceReportService;
	@Autowired
	private PlatformsService platformsService;
	@Autowired
	private SeriesAllService seriesAllService;
	
	@Autowired
	@Qualifier("sourceCpaConfiguration")
	private Configuration seriesAllConfiguration;
	
	@Autowired
	private SeriesGamesJob seriesGamesJob;
	
	@Autowired
	private RealTimeGamesService realtTimeGamesService;
//	@Autowired
//	private PrestoTestReport prestoTestReport;
//	
//	@Autowired
//	private RealTimeGamesService realTimeGamesService;
//	
	@Autowired
	private RealTimeGameClientidService realTimeGameClientidService;
	
	@Autowired
	private RealTimeGameSourceService realTimeGameSourceService;
//	
//	@Autowired
//	private GamesService gamesService;
//	
//	@Autowired
//	@Qualifier("sourceCpaConfiguration")
//	private Configuration sourceCpaConfiguration;
//	
//	private Map<String,RunEtlStatus> runEtlSet = new HashMap<String, GameRealTimeController.RunEtlStatus>();
	
	@RequestMapping(value = {"/toGameRealTime.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameRealTime(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		String isMainRequest = request.getParameter("isMainRequest");
		if(!StringUtils.isEmpty(isMainRequest) && Boolean.valueOf(isMainRequest)){
			removeSession(request, "game");
		}
		Games games = this.getSessionGames(request);
		// 在session中存放game实例
		super.setSession(request, "game", games);
		
		Date calendar = new Date();
//		if(games.getOnlineDate() != null 
//			&& (calendar.getTime() - games.getOnlineDate().getTime()) <= INTERVAL_TIME){
			model.addAttribute("isDisplayHourReport", true);
//		}else{
//			model.addAttribute("isDisplayHourReport", false);
//		}
		
		String view = request.getParameter("view");
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
	
	@RequestMapping(value = {"/toGameSeries.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameSeries(HttpServletRequest request,HttpSession session,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		super.removeSession(request, "gameTypeId");
		super.removeSession(request, "gamesTypeNames");
		
		String gameTypeId = request.getParameter("gameTypeId");
		List<SysConfigVO> gameTypes = (List<SysConfigVO>) super.getSession(request, "gameTypes");
		String gamesTypeName = "";
		for (SysConfigVO scv : gameTypes) {
			if(gameTypeId.equals(String.valueOf(scv.getEntity().getId()))){
				gamesTypeName = scv.getEntity().getName();
				break;
			}
		}
		
		Calendar cal = Calendar.getInstance();//获取当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String beginDate = sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -2);
		String endDate = sdf.format(cal.getTime());
		
		super.setSessionDate(request, endDate, beginDate);
		super.setSession(request, "gameTypeId", gameTypeId);
		super.setSession(request, "gamesTypeName", gamesTypeName);
		
		return "games/games.jsp";
	}
	
	@RequestMapping(value = {"/getAllGameSeries.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getAllGameSeries(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String seriesid = request.getParameter("gameTypeId");
		String seriesName = request.getParameter("gamesTypeName");
		String dataType = request.getParameter("dataType");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dataType = daysOfTwo(sdf.parse(beginDate), sdf.parse(endDate));
		
		SeriesAll seriesAll = new SeriesAll();
		if(!"".equals(dataType)&&"".equals(beginDate)){
			beginDate = seriesGamesJob.getBeginDate(endDate, dataType);
		}
		seriesAll.setBeginDate(beginDate);
		seriesAll.setEndDate(endDate);
		seriesAll.setDs(endDate);
		seriesAll.setSeriesid(seriesid);
		seriesAll.setDataType(dataType);
		
//		int num = daysOfTwo(sdf.parse(beginDate), sdf.parse(endDate));
//		if(num>29) {
//			ret.put("error", "开始日期和结束日期相差不能大于30天");
//			return ret;
//		}else if(num<0){
//			ret.put("error", "开始日期不能大于结束日期");
//			return ret;
//		}
//		seriesAll.setIsAll("0");
		List<SeriesAll> seriesAllList = seriesAllService.selectSeriesAll(seriesAll);
//		seriesAllList = new ArrayList<SeriesAll>();
		String status = "";
		if(seriesAllList.size()==0) {
			List<Games> gameList = (List<Games>) super.getSession(request, "games");
			if("".equals(dataType)){
//				seriesGamesJob.runSeriesGamesData(beginDate, endDate, seriesName, seriesid, dataType, gameList);
			}else{
				status = seriesGamesJob.runSeriesGamesData(beginDate, endDate, seriesName, seriesid, gameList);
//				seriesGamesJob.runSeriesGamesData("", endDate, seriesName, seriesid, "3", gameList);
//				seriesGamesJob.runSeriesGamesData("", endDate, seriesName, seriesid, "7", gameList);
			}
			seriesAllList = seriesAllService.selectSeriesAll(seriesAll);
		}
		if(status.equals(0)){
			ret.put("msg", "0");
			return ret;
		}
		
		
		ret.put("report", seriesAllList);
		seriesAll.setIsAll("1");
		seriesAllList = seriesAllService.selectSeriesAll(seriesAll);
		ret.put("reportAll", seriesAllList.get(0));
		return ret;
	}
	
	public static String daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return String.valueOf(day2 - day1 + 1);
	}
	
	@RequestMapping(value = {"/getGameSeries.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameSeries(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String seriesName = request.getParameter("gamesTypeName");
		String seriesid = request.getParameter("gameTypeId");
		String dataType = request.getParameter("dataType");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dataType = daysOfTwo(sdf.parse(beginDate), sdf.parse(endDate));
		
		SeriesGame seriesGame = new SeriesGame();
		seriesGame.setBeginDate(beginDate);
		seriesGame.setEndDate(endDate);
		seriesGame.setSeriesid(seriesid);
		seriesGame.setDataType(dataType);
		
		List<SeriesGame> seriesGameList = seriesAllService.selectSeriesGame(seriesGame);
		ret.put("recordsTotal", seriesGameList.size());
		ret.put("recordsFiltered", seriesGameList.size());
		ret.put("report", seriesGameList);
		seriesGame.setIsAll("1");
		seriesGameList = seriesAllService.selectSeriesGame(seriesGame);
		ret.put("reportAll", seriesGameList);
		
		return ret;
	}
	
	
	
	@RequestMapping(value = {"/downloadSeriesGameData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downloadSeriesGameData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String seriesName = request.getParameter("gamesTypeName");
		String seriesid = request.getParameter("gameTypeId");
		String dataType = request.getParameter("dataType");
		
		return null;
	}
	
	@RequestMapping(value = {"/downloadData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		String view = request.getParameter("view");
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String downType = request.getParameter("downType");
		
		GameDataPage page = GameDataPage.convertToPage(view);
		
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
		switch(page){
		case REALTIME:
			if("source".equals(downType)){
				RealtimeSourceReport sourceReport = new RealtimeSourceReport(games.getSnid(),games.getGameid());
				RealtimeSourceReportVO sourceReportVO = new RealtimeSourceReportVO(sourceReport);
				sourceReportVO.setDate(beginDate,endDate);
				
				Long allCount = realtimeSourceReportService.selecCount(sourceReportVO);
				
				sourceReportVO.setOffset(0);
				sourceReportVO.setRows(allCount);
				
				processor.initRealTimeSourceList(realtimeSourceReportService.selectList(sourceReportVO),platformsService.getMatch(new PlatformsVO()));
				processor.writeRealTimeSource();
			}else if("client".equals(downType)){
				RealTimeResult realTimeResult = new RealTimeResult(games.getSnid(),games.getGameid());
				RealTimeResultVO realTimeResultVO = new RealTimeResultVO(realTimeResult);
				realTimeResultVO.setBeginDate(beginDate);
				realTimeResultVO.setEndDate(endDate);
				
				Long allCount = realTimeResultService.selecCount(realTimeResultVO);
				
				realTimeResultVO.setOffset(0);
				realTimeResultVO.setRows(allCount);
				processor.initRealTimeList(realTimeResultService.selectList(realTimeResultVO));
				processor.writeClientRealTime();
			}else if("no_client".equals(downType)){
				RealTimeNoClientResult realTimeNoClientResult = new RealTimeNoClientResult(games.getSnid(),games.getGameid());
				RealTimeNoClientResultVO realTimeNoClientResultVO = new RealTimeNoClientResultVO(realTimeNoClientResult);
				realTimeNoClientResultVO.setBeginDate(beginDate);
				realTimeNoClientResultVO.setEndDate(endDate);
				
				processor.writeRealTime(realTimeNoClientResultService.getMatch(realTimeNoClientResultVO));
			}else if("hourRealTime".equals(downType)){
				ReportingEachTimeNC reportingEachTimeNC = new ReportingEachTimeNC(games.getSnid(),games.getGameid());
				ReportingEachTimeNCVO reportingEachTimeNCVO = new ReportingEachTimeNCVO(reportingEachTimeNC);
				
				processor.writeHourRealTime(reportingEachTimeNCService.getMatch(reportingEachTimeNCVO));
			}
			break;
			default:
				throw new RuntimeException();
		}
		return null;
		}
	
	@RequestMapping(value = {"/getGameRealTimeClientView.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameRealTimeClientView(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String view = request.getParameter("view");
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		
		GameDataPage page = GameDataPage.convertToPage(view);
		
		Map<String,Object> pageInfo = findPageInfo(request);
		switch(page){
		case REALTIME:
			RealTimeResult realTimeResult = new RealTimeResult(games.getSnid(),games.getGameid());
			RealTimeResultVO realTimeResultVO = new RealTimeResultVO(realTimeResult);
			realTimeResultVO.setBeginDate(beginDate);
			realTimeResultVO.setEndDate(endDate);
			
			Long allCount = realTimeResultService.selecCount(realTimeResultVO);
			realTimeResultVO.setSearchValue((String) pageInfo.get("searchValue"));
			long recordsFiltered = realTimeResultService.selecCount(realTimeResultVO);
			
			realTimeResultVO.setOffset((Long) pageInfo.get("start"));
			realTimeResultVO.setRows((Long) pageInfo.get("length"));
			realTimeResultVO.setOrderCol(getOrderCol((String)pageInfo.get("orderCol")));
			realTimeResultVO.setOrderType((String) pageInfo.get("orderType"));
			
			ret.put("recordsTotal", allCount);
			ret.put("recordsFiltered", recordsFiltered);
			
			ret.put("data", realTimeResultService.selectList(realTimeResultVO));
			break;
		}
		
		return ret;
	}
	
	@RequestMapping(value = {"/getGameRealTimeSourceView.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameRealTimeSourceView(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String view = request.getParameter("view");
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		
		GameDataPage page = GameDataPage.convertToPage(view);
		
		Map<String,Object> pageInfo = findPageInfo(request);
		switch(page){
		case REALTIME:
			RealtimeSourceReport sourceReport = new RealtimeSourceReport(games.getSnid(),games.getGameid());
			RealtimeSourceReportVO sourceReportVO = new RealtimeSourceReportVO(sourceReport);
			sourceReportVO.setDate(beginDate,endDate);
			
			Long allCount = realtimeSourceReportService.selecCount(sourceReportVO);
			sourceReportVO.setSearchValue((String) pageInfo.get("searchValue"));
			long recordsFiltered = realtimeSourceReportService.selecCount(sourceReportVO);
			
			sourceReportVO.setOffset((Long) pageInfo.get("start"));
			sourceReportVO.setRows((Long) pageInfo.get("length"));
			sourceReportVO.setOrderCol(getSourceOrderCol((String)pageInfo.get("orderCol")));
			sourceReportVO.setOrderType((String) pageInfo.get("orderType"));
			
			ret.put("recordsTotal", allCount);
			ret.put("recordsFiltered", recordsFiltered);
			
			List<RealtimeSourceReport> sourceReports = realtimeSourceReportService.selectList(sourceReportVO);
			processRealtimeSourceData(sourceReports, platformsService.getMatch(new PlatformsVO()));
			ret.put("data", sourceReports);
			break;
		}
		
		return ret;
	}
	
	private void processRealtimeSourceData(List<RealtimeSourceReport> sourceReports,List<Platforms> platforms){
		for(RealtimeSourceReport sourceReport:sourceReports){
			String ads[] = sourceReport.getCreateive().split("_");
			if(ads.length >= 2){
				sourceReport.setAdPlace(sourceReport.getCreateive().replace(ads[0]+"_", ""));//广告位中也可能有_线
				sourceReport.setCreateive(DownLoadCsvProcessor.findPlatformName(ads[0], platforms));
			}
		}
	}
	
	private String getOrderCol(String orderCol){
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "ds";
			case 1:
				return "clientid+0";
			case 2:
				return "install";
			case 3:
				return "dau";
			case 4:
				return "amount";
			case 5:
				return "amount/dau";
			case 6:
				return "amount/pay_users";
			}
		}
		return null;
	}
	//按小时  分服
	private String getHourOrderCol(String orderCol){
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "finish_time";
			case 1:
				return "clientid";
			case 2:
				return "dnu";
			case 3:
				return "yesterday_dnu";
			case 4:
				return "dau";
			case 5:
				return "yesterday_dau";
			case 6:
				return "total_amount";
			case 7:
				return "yesterday_total_amount";
			case 8:
				return "payer";
			case 9:
				return "yesterday_payer";
			case 10:
				return "total_amount/dau";
			case 11:
				return "yesterday_total_amount/yesterday_dau";
			case 12:
				return "total_amount/payer";
			case 13:
				return "yesterday_total_amount/yesterday_payer";
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
				return "ds";
			case 3:
				return "idfa";
			case 4:
				return "distinct_idfa";
			case 5:
				return "distinct_ip";
			case 6:
				return "install";
			case 7:
				return "new_equip";
			case 8:
				return "install_payer";
			case 9:
				return "install_payment";
			}
		}
		return null;
	}
	private String getSourceHourOrderCol(String orderCol){
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "finish_time";
			case 1:
				return "install_creative";
			case 2:
				return "dnu";
			case 3:
				return "yesterday_dnu";
			case 4:
				return "equips";
			case 5:
				return "yesterday_equips";
			case 6:
				return "dau";
			case 7:
				return "yesterday_dau";
			case 8:
				return "total_amount";
			case 9:
				return "yesterday_total_amount";
			case 10:
				return "payer";
			case 11:
				return "yesterday_payer";
			case 12:
				return "total_amount/dau";
			case 13:
				return "yesterday_total_amount/yesterday_dau";
			case 14:
				return "total_amount/payer";
			case 15:
				return "yesterday_total_amount/yesterday_payer";
			}
		}
		return null;
	}
	//实时
	@RequestMapping(value = {"/getGameRealTime.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	@ResponseBody
	public Map<String,Object> getGameRealTime(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		
		RealTimeNoClientResult realTimeNoClientResult = new RealTimeNoClientResult(games.getSnid(),games.getGameid());
		RealTimeNoClientResultVO realTimeNoClientResultVO = new RealTimeNoClientResultVO(realTimeNoClientResult);
		realTimeNoClientResultVO.setBeginDate(beginDate);
		realTimeNoClientResultVO.setEndDate(endDate);
		
		ret.put("noClientResultList",realTimeNoClientResultService.getMatch(realTimeNoClientResultVO));
		return ret;
	}
	
	//按小时
	/*@RequestMapping(value = {"/getReportEachTime1.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	@ResponseBody
	public Map<String,Object> getReportEachTime1(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		ReportingEachTimeNC reportingEachTimeNC = new ReportingEachTimeNC(games.getSnid(),games.getGameid());
		ReportingEachTimeNCVO reportingEachTimeNCVO = new ReportingEachTimeNCVO(reportingEachTimeNC);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		RealTimeGameVo realTimeGameVo = new RealTimeGameVo(games.getSnid(), games.getGameid());
		realTimeGameVo.setStart_time("00:00:00");
		realTimeGameVo.setFinish_time(String.valueOf(new Date().getHours())+":00:00");
		realTimeGameVo.setDay(sdf.format(cal.getTime()));
//		realTimeGameVo.setDay("2017-06-08");
		cal.add(Calendar.DAY_OF_MONTH, -1);
		realTimeGameVo.setYesterday(sdf.format(cal.getTime()));
//		realTimeGameVo.setYesterday("2017-06-07");
		List<RealTimeGame> realTimeDataList = realtTimeGamesService.getSelectGamesList2(realTimeGameVo);
		
		List<ReportingEachTimeNC> list = reportingEachTimeNCService.getMatch(reportingEachTimeNCVO);
//		ret.put("reports", reportingEachTimeNCService.getMatch(reportingEachTimeNCVO));
		ret.put("reports", realTimeDataList);
		ret.put("sum", realTimeDataList);
		ret.put("game", games);
		return ret;
	}*/
	
	@RequestMapping(value = {"/getReportEachTime.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	@ResponseBody
	public Map<String,Object> getReportEachTime(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		ReportingEachTimeNC reportingEachTimeNC = new ReportingEachTimeNC(games.getSnid(),games.getGameid());
		ReportingEachTimeNCVO reportingEachTimeNCVO = new ReportingEachTimeNCVO(reportingEachTimeNC);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		RealTimeGameVo realTimeGameVo = new RealTimeGameVo(games.getSnid(), games.getGameid());
		realTimeGameVo.setStart_time("00:00:00");
		realTimeGameVo.setFinish_time(String.valueOf(new Date().getHours())+":00:00");
		realTimeGameVo.setDay(sdf.format(cal.getTime()));
//		realTimeGameVo.setDay("2017-06-08");
		cal.add(Calendar.DAY_OF_MONTH, -1);
		realTimeGameVo.setYesterday(sdf.format(cal.getTime()));
//		realTimeGameVo.setYesterday("2017-06-07");
		List<RealTimeGame> realTimeDataList = realtTimeGamesService.getSelectGamesList2(realTimeGameVo);
		
		List<ReportingEachTimeNC> list = reportingEachTimeNCService.getMatch(reportingEachTimeNCVO);
//		ret.put("reports", reportingEachTimeNCService.getMatch(reportingEachTimeNCVO));
		ret.put("reports", realTimeDataList);
		//ret.put("sum", realTimeDataList);
		ret.put("game", games);
		return ret;
	}
	
	//按小时
	@RequestMapping(value = {"/getGameHourRealTime.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	@ResponseBody
	public Map<String,Object> getGameHourRealTime(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		

		RealTimeGameVo realTimeGameVo = new RealTimeGameVo();
		realTimeGameVo.setSnid(games.getSnid());
		realTimeGameVo.setGameid(games.getGameid());
		realTimeGameVo.setDay(beginDate);
		
	
		List<RealTimeGameVo>  reports = realtTimeGamesService.getSelectGamesHourList(realTimeGameVo);
//		for(int i =0 ; i<reports.size();i++){
//			
//			if(reports.get(i).getStart_time().equals("23:00:00")){
//				
//				reports.get(i).setFinish_time("24:00:00");
//				
//			}
//		}
		
		
		ret.put("game", games);
		ret.put("reports", reports);
		return ret;
	}
	
	
	//总览汇总
		@RequestMapping(value = {"/getGameTotalRealTime.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
		@ResponseBody
		public Map<String,Object> getGameTotalRealTime(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
			
			Map<String,Object> ret=new HashMap<String, Object>();
			Games games = this.getSessionGames(request);
			
			String beginDate = request.getParameter("beginTime");
			

			RealTimeGameVo realTimeGameVo = new RealTimeGameVo();
			realTimeGameVo.setSnid(games.getSnid());
			realTimeGameVo.setGameid(games.getGameid());
			realTimeGameVo.setDay(beginDate);
			
			List<RealTimeGameVo>  reports = realtTimeGamesService.getSelectGamesTotalList(realTimeGameVo);
//			for(int i =0 ; i<reports.size();i++){
//				if(reports.get(i).getStart_time()=="23:00:00"){
//					reports.get(i).setFinish_time("24:00:00");
//				}
//			}
			
			
			reports = addData(reports);

			ret.put("reports", reports);
			return ret;
		}
		

		public List<RealTimeGameVo> addData(List<RealTimeGameVo> realTimeGameList) {
			
			double dnu;
			double dau;
			double equips;
			double dau_total;
			double payer;
			double total_amount;
			String yesterday_dnu = "";
			String yesterday_dau = "";
			String yesterday_equips = "";
			String yesterday_total_amount = "";
			String yesterday_payer = "";
			String yesterday_dau_total = "";
			
//			for (RealTimeGameVo realTimeGame : realTimeGameList) {
			for(int i = 0; i< realTimeGameList.size(); i++) {
				if(i==0){
					dnu = realTimeGameList.get(i).getDnu();
					dau = realTimeGameList.get(i).getDau();
					equips = realTimeGameList.get(i).getEquips();
					dau_total = realTimeGameList.get(i).getDau_total();
					payer = realTimeGameList.get(i).getPayer();
					total_amount = realTimeGameList.get(i).getTotal_amount();
					yesterday_dnu = (realTimeGameList.get(i).getYesterday_dnu()==null?"0":realTimeGameList.get(i).getYesterday_dnu());
					yesterday_dau = (realTimeGameList.get(i).getYesterday_dau()==null?"0":realTimeGameList.get(i).getYesterday_dau());
					yesterday_equips = (realTimeGameList.get(i).getYesterday_equips()==null?"0":realTimeGameList.get(i).getYesterday_equips());
					yesterday_total_amount = (realTimeGameList.get(i).getYesterday_total_amount()==null?"0":realTimeGameList.get(i).getYesterday_total_amount());
					yesterday_payer = (realTimeGameList.get(i).getYesterday_payer()==null?"0":realTimeGameList.get(i).getYesterday_payer());
					yesterday_dau_total = (realTimeGameList.get(i).getYesterday_dau_total()==null?"0":realTimeGameList.get(i).getYesterday_dau_total());
				}else{
					if(realTimeGameList.get(i).getYesterday_dnu()==null){
						realTimeGameList.get(i).setYesterday_dnu(yesterday_dnu);
					}else{
						yesterday_dnu = realTimeGameList.get(i).getYesterday_dnu();
					}
					
					if(realTimeGameList.get(i).getYesterday_dau()==null){
						realTimeGameList.get(i).setYesterday_dau(yesterday_dau);
					}else{
						yesterday_dau = realTimeGameList.get(i).getYesterday_dau();
					}
					
					if(realTimeGameList.get(i).getYesterday_equips()==null){
						realTimeGameList.get(i).setYesterday_equips(yesterday_equips);
					}else{
						yesterday_equips = realTimeGameList.get(i).getYesterday_equips();
					}
					
					if(realTimeGameList.get(i).getYesterday_total_amount()==null){
						realTimeGameList.get(i).setYesterday_total_amount(yesterday_total_amount);
					}else{
						yesterday_total_amount = realTimeGameList.get(i).getYesterday_total_amount();
					}
					
					if(realTimeGameList.get(i).getYesterday_payer()==null){
						realTimeGameList.get(i).setYesterday_payer(yesterday_payer);
					}else{
						yesterday_payer = realTimeGameList.get(i).getYesterday_payer();
					}
					
					if(realTimeGameList.get(i).getYesterday_dau_total()==null){
						realTimeGameList.get(i).setYesterday_dau_total(yesterday_dau_total);
					}else{
						yesterday_dau_total = realTimeGameList.get(i).getYesterday_dau_total();
					}
//					String yesterday_total_amount = "";
//					String yesterday_payer = "";
//					String yesterday_dau_total = "";
				}
			}
			
			return realTimeGameList;
		}
		/**
		 * 按小时分渠道
		 * @param request
		 * @param session
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = {"/getGameRealTimeHourSourceView.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
		@ResponseBody
		public Map<String,Object> getGameRealTimeHourSourceView(HttpServletRequest request,HttpSession session)throws Exception {
			Users users = getSessionUsers(request);
			if(isOutSideUser(request)){
				throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
			}
			Map<String,Object> ret=new HashMap<String, Object>();
			Games games = this.getSessionGames(request);
			
			String view = request.getParameter("view");
			String beginDate = request.getParameter("beginTime");
			String endDate = request.getParameter("endTime");
			String source = request.getParameter("source");
			GameDataPage page = GameDataPage.convertToPage(view);
			
			Map<String,Object> pageInfo = findPageInfo(request);
			switch(page){
			case REALTIME:
				
				RealTimeGameSourceVo realTimeGameSourceVo = new RealTimeGameSourceVo();
				realTimeGameSourceVo.setSnid(games.getSnid());
				realTimeGameSourceVo.setGameid(games.getGameid());
				realTimeGameSourceVo.setDay(beginDate);
//				long count =  realTimeGameSourceService.getSelectCountSourcedata(realTimeGameSourceVo);
//				if(count>0){
//					realTimeGameSourceService.deleteRealTimeSourceData(realTimeGameSourceVo);
//				}
				
				
				/*//获取所有游戏snid, gameid
				RealTimeGamesJob job =new  RealTimeGamesJob();
				List<Games> gamesList = gamesService.selectAllGames2();
				RealTimeGameSourceVo realTimeGameSourceVos = new RealTimeGameSourceVo();
				//实时分渠道 Android
				System.out.println("开始实时分渠道Android -- Ios"+new Date());
				
				if(games.getSystemType()==1){
					realTimeGameSourceVos.setSnid(games.getSnid());
					realTimeGameSourceVos.setGameid(games.getGameid());
					realTimeGameSourceVos.setDs(job.getDayDate());
					realTimeGameSourceVos.setFinish_time(job.getClient_finishTime());
					List<RealTimeGameSource> sourceAndroidlist = this.realTimeSource(realTimeGameSourceVos,"realTimeSourceAndroid");
				}else if(games.getSystemType()==0){
					realTimeGameSourceVos.setSnid(games.getSnid());
					realTimeGameSourceVos.setGameid(games.getGameid());
					realTimeGameSourceVos.setDs(job.getDayDate());
					realTimeGameSourceVos.setFinish_time(job.getfinishTime());
					List<RealTimeGameSource> sourceIoslist = this.realTimeSource(realTimeGameSourceVos,"realTimeSourceIos");
				}
				System.out.println("结束实时分渠道Android -- Ios"+new Date());*/
				Long allCount = realTimeGameSourceService.getSelectGamesSourceCount(realTimeGameSourceVo);
				if((String) pageInfo.get("searchValue") == null && source!=null && !source.equals("-1")){
					
					realTimeGameSourceVo.setInstall_creative(source);
				}else{
					realTimeGameSourceVo.setInstall_creative((String) pageInfo.get("searchValue"));
				}
			
				
				long recordsFiltered = realTimeGameSourceService.getSelectGamesSourceCount(realTimeGameSourceVo);
				
				realTimeGameSourceVo.setOffset((Long) pageInfo.get("start"));
				realTimeGameSourceVo.setRows((Long) pageInfo.get("length"));
				realTimeGameSourceVo.setOrderCol(getSourceHourOrderCol((String)pageInfo.get("orderCol")));
				realTimeGameSourceVo.setOrderType((String) pageInfo.get("orderType"));

				
				List<RealTimeGameSourceVo> data = realTimeGameSourceService.getSelectGamesSourceList(realTimeGameSourceVo);
				ret.put("recordsTotal", allCount);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", data);
				
				break;
			}
			
			return ret;
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
			RealTimeGameSourceVo realTimeGameSourceVo = new RealTimeGameSourceVo();
			realTimeGameSourceVo.setSnid(gamesVO.getEntity().getSnid());
			realTimeGameSourceVo.setGameid(gamesVO.getEntity().getGameid());
			List<String> gameSources =realTimeGameSourceService.selectGameSources(realTimeGameSourceVo);
			
			ret.put("gameSources", gameSources);
			return ret;
		}
		
		//2017-06-27  更改后  分服
		@RequestMapping(value = {"/getGameRealTimeHourClientView.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
		@ResponseBody
		public Map<String,Object> getGameRealTimeHourClientView(HttpServletRequest request,HttpSession session)throws Exception {
			Users users = getSessionUsers(request);
			if(isOutSideUser(request)){
				throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
			}
			Map<String,Object> ret=new HashMap<String, Object>();
			Games games = this.getSessionGames(request);
			
			String view = request.getParameter("view");
			String beginDate = request.getParameter("beginTime");
			String endDate = request.getParameter("endTime");
			String clientid = request.getParameter("clientid");
			GameDataPage page = GameDataPage.convertToPage(view);
			
			Map<String,Object> pageInfo = findPageInfo(request);
			boolean isPresto = false;
			switch(page){
			case REALTIME:
		
				RealTimeGameClientVo realTimeGameClientidVo = new RealTimeGameClientVo();
				realTimeGameClientidVo.setSnid(games.getSnid());
				realTimeGameClientidVo.setGameid(games.getGameid());
				realTimeGameClientidVo.setDay(beginDate);
				
				/*long count = realTimeGameClientidService.getSelectCountdata(realTimeGameClientidVo);
				//先清空表中的数据
				if(count>0){
					realTimeGameClientidService.deleteRealTimeClientData(realTimeGameClientidVo);
				}
				//获取所有游戏snid, gameid
				RealTimeGamesJob job =new  RealTimeGamesJob();
				List<Games> gamesList = gamesService.selectAllGames2();
				RealTimeGameClientVo realTimeGameClientidVos = new RealTimeGameClientVo();
				System.out.println("开始实时分服"+new Date());
				realTimeGameClientidVos.setSnid(games.getSnid());
				realTimeGameClientidVos.setGameid(games.getGameid());
				realTimeGameClientidVos.setDs(job.getDayDate());
				realTimeGameClientidVos.setFinish_time(job.getClient_finishTime());
				List<RealTimeGameClient> clientidlist = this.realTime(realTimeGameClientidVos,"realTimeClientid");
				System.out.println("结束实时分服"+new Date());*/
				
				
				long allCount = realTimeGameClientidService.getSelectGamesClientidCount(realTimeGameClientidVo);
				
				if((String) pageInfo.get("searchValue") == null && clientid!=null && !clientid.equals("-1")){
					
					realTimeGameClientidVo.setClientid(clientid);
				}else{
					realTimeGameClientidVo.setClientid((String) pageInfo.get("searchValue"));
				}
				
				
				
				long recordsFiltered = realTimeGameClientidService.getSelectGamesClientidCount(realTimeGameClientidVo);
				
				realTimeGameClientidVo.setOffset((Long) pageInfo.get("start"));
				realTimeGameClientidVo.setRows((Long) pageInfo.get("length"));
				realTimeGameClientidVo.setOrderCol(getHourOrderCol((String)pageInfo.get("orderCol")));
				realTimeGameClientidVo.setOrderType((String) pageInfo.get("orderType"));

				List<RealTimeGameClientVo>  data = realTimeGameClientidService.getSelectGamesClientidList(realTimeGameClientidVo);
				ret.put("recordsTotal", allCount);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", data);
				break;
			}
			
			return ret;
		}
		@RequestMapping(value = {"/getGameClient.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
		@ResponseBody
		public Map<String,Object> getGameClient(HttpServletRequest request,HttpSession session)throws Exception {
			Map<String,Object> ret=new HashMap<String, Object>();
			GamesVO gamesVO = new GamesVO(super.getSessionGames(request));
			
			RealTimeGameClientVo realTimeGameClientidVo = new RealTimeGameClientVo();
			realTimeGameClientidVo.setSnid(gamesVO.getEntity().getSnid());
			realTimeGameClientidVo.setGameid(gamesVO.getEntity().getGameid());
			
			List<String> gameClients = realTimeGameClientidService.selectGameClients(realTimeGameClientidVo);
			
			ret.put("gameClients", gameClients);
			return ret;
		}
		
		@RequestMapping(value = {"/getGameHourSourceRealTime.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
		@ResponseBody
		public Map<String,Object> getGameHourSourceRealTime(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
			
			Map<String,Object> ret=new HashMap<String, Object>();
			Games games = this.getSessionGames(request);
			
			String beginDate = request.getParameter("beginTime");
			String indicators = request.getParameter("source_one");

			RealTimeGameSourceVo realTimeGameSourceVo = new RealTimeGameSourceVo();
			realTimeGameSourceVo.setSnid(games.getSnid());
			realTimeGameSourceVo.setGameid(games.getGameid());

			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Calendar cal = Calendar.getInstance();//获取当前日期
			  beginDate = sdf.format(cal.getTime());
			  
			realTimeGameSourceVo.setDay(beginDate);

			String maxDau = null ;
			if(!indicators.isEmpty()){
				realTimeGameSourceVo.setInstall_creative(indicators);
			}else{
				 maxDau = realTimeGameSourceService.getSelectMaxDau(realTimeGameSourceVo);
				realTimeGameSourceVo.setInstall_creative(maxDau.toUpperCase());
				ret.put("maxDau", maxDau.toUpperCase());
			}
			
			
			List<RealTimeGameSourceVo> reports = realTimeGameSourceService.getSelectGamesSourceIconList(realTimeGameSourceVo);
			
			ret.put("game", games);
			ret.put("reports", reports);
			
			
			return ret;
		}
}

