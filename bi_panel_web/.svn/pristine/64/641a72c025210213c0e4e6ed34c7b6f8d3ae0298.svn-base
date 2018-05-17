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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.Types.GameDataPage;
import com.hoolai.bi.report.model.Types.GameRoleAnalysisChannel;
import com.hoolai.bi.report.model.Types.GameplayerType;
import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.model.entity.CustomReportTask;
import com.hoolai.bi.report.model.entity.CustomReportTask.CustomReportTaskExecuteTimes;
import com.hoolai.bi.report.model.entity.CustomReportTask.CustomReportTaskStatus;
import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.RealTimeGame;
import com.hoolai.bi.report.model.entity.RealTimeNoClientResult;
import com.hoolai.bi.report.model.entity.RealTimeResult;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.model.entity.UserRetentionClientLtv;
import com.hoolai.bi.report.model.entity.UserRetentionLtv;
import com.hoolai.bi.report.model.entity.UserRetentionSourceLtv;
import com.hoolai.bi.report.service.ClientDailyReportService;
import com.hoolai.bi.report.service.CustomReportTaskService;
import com.hoolai.bi.report.service.DailyReportService;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.RealTimeGamesService;
import com.hoolai.bi.report.service.RealTimeNoClientResultService;
import com.hoolai.bi.report.service.RealTimeResultService;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.service.UserRetentionClientLtvService;
import com.hoolai.bi.report.service.UserRetentionLtvService;
import com.hoolai.bi.report.service.UserRetentionSourceLtvService;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.hoolai.bi.report.vo.CustomReportTaskVO;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.PanelPagination;
import com.hoolai.bi.report.vo.RealTimeGameVo;
import com.hoolai.bi.report.vo.RealTimeNoClientResultVO;
import com.hoolai.bi.report.vo.RealTimeResultVO;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.bi.report.vo.UserRetentionClientLtvVO;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.hoolai.manage.model.CustomReportModel;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.CustomReportModelService;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;
import com.hoolai.panel.web.processor.CustomReportTaskProcessor;
import com.hoolai.panel.web.processor.CustomReportTaskProcessor.AddTaskRetStatus;
import com.hoolai.panel.web.processor.CustomReportThreadPool;
import com.hoolai.panel.web.processor.CustomTaskJob;
import com.hoolai.panel.web.processor.CustomTaskThread;
import com.jian.service.pagination.PaginationResult;

@Controller
@RequestMapping("/panel_bi/wap/gameView")
public class WapGameViewController extends AbstractPanelController{
	
	private  static final long INTERVAL_TIME = 2l * 30l * 24l * 60l * 60l * 1000l;
	private static final CustomReportThreadPool CUSTOM_REPORT_THREAD_POOL = new CustomReportThreadPool(10, 100);
	
	@Autowired
	private GamesService gamesService;
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private SourceDailyReportService sourceDailyReportService;
	@Autowired
	private ClientDailyReportService clientDailyReportService;
	@Autowired
	private UserRetentionSourceLtvService userRetentionSourceLtvService;
	@Autowired
	private UserRetentionClientLtvService userRetentionClientLtvService;
	@Autowired
	private UserRetentionLtvService userRetentionLtvService;
	@Autowired
	private RealTimeNoClientResultService realTimeNoClientResultService;
	@Autowired
	private RealTimeResultService realTimeResultService;
	@Autowired
	private CustomReportTaskService customReportTaskService;
	@Autowired
	private CustomReportModelService customReportModelService;
	@Autowired
	private CustomTaskJob customTaskJob;
	@Autowired
	private RealTimeGamesService realtTimeGamesService;
	
	@RequestMapping(value = {"/toWapGameDatasView.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toWapGameDatasView(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		
		String view = request.getParameter("view");
		GameDataPage page = GameDataPage.convertToPage(view);
		String jsp = "";
		switch(page){
		case RETENTION:
			jsp = "wap/view/base/gameRetentionReport.jsp";
			break;
		case LIFE:
			jsp = "wap/view/base/gameLTVReport.jsp";
			break;
		case DAILY:
			jsp = "wap/view/base/gameDailyReport.jsp";
			break;
		case MONEY:
			jsp = "wap/view/base/gameMoneyReport.jsp";
			break;
		case HOUR_REPORT:
			jsp = "wap/view/realtime/hourReport.jsp";
			break;
		case HOUR_REPORT_SOURCE_LTV:
			jsp = "wap/view/realtime/hourLtvReport.jsp";
			break;
		case HOUR_REPORT_SOURCE_RETENTION:
			jsp = "wap/view/realtime/hourReportSourceRetention.jsp";
			break;
		case ECONOMY:
			jsp = "wap/view/economyItemData.jsp";
			break;
		case FORECAST_HOUR_REPORT:
			jsp = "wap/view/realtime/reportEachTime.jsp";
			break;
		case WHALE_USER:
			jsp = "wap/view/whaleUser.jsp";
			break;
		case EQUIP_DAU:
			jsp="wap/view/equip/equipDau.jsp";
			break;
		case VERSION_DAU:
			jsp="wap/view/equip/equipVersionDau.jsp";
			break;
		case INSTALL_RETENTION:
			jsp="wap/view/equip/newEquipRetention.jsp";
			break;
		case TASK_LIST:
			jsp="wap/view/task/taskList.jsp";
			break;
		case TESTREPORT:
			jsp = "wap/view/testReport/gameTestReport.jsp";
			break;
		case SBWDVIEW:
			jsp = "wap/view/testReport/gameSbwd.jsp";
			break;
		case REALTIME:
		   default:
				jsp = "wap/view/realtime/gameRealTime.jsp";
		}
		
		Date calendar = new Date();
//		if(games.getOnlineDate() != null 
//			&& (calendar.getTime() - games.getOnlineDate().getTime()) <= INTERVAL_TIME){
			model.addAttribute("isDisplayHourReport", true);
//		}else{
//			model.addAttribute("isDisplayHourReport", false);
//		}
		
		return jsp;
	}
	
	@RequestMapping(value = {"/getTaskList.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getTaskList(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		CustomReportTask task = new CustomReportTask(games.getSnid(),games.getGameid());
		CustomReportTaskVO taskVO = new CustomReportTaskVO(task);
		
		Map<String, Object> pageInfo = super.findPageInfo(request);
		
		long count = customReportTaskService.selectCount(taskVO);
		ret.put("recordsTotal", count);
		
		String valueArrStr = request.getParameter("valueArr");
		String nameArrStr = request.getParameter("nameArr");
		String[] nameArr = null;
		String[] valueArr = null;
		List<CustomReportTask> reportTasks = null;
		Long reportModel = Long.parseLong(request.getParameter("reportModel"));
		if(!StringUtils.isEmpty(valueArrStr)&&!StringUtils.isEmpty(nameArrStr)){
			nameArr = nameArrStr.split(",");
			valueArr = valueArrStr.split(",");
			
			CustomReportModel customReportModel = customReportModelService.getById(Long.valueOf(reportModel));
			CustomReportTaskProcessor processor = new CustomReportTaskProcessor(customReportModel,nameArr,valueArr);
			String taskCode = processor.getTaskCode();
			
			CustomReportTask taskOb = new CustomReportTask(games.getSnid(),games.getGameid(),customReportModel.getCode()+"_"+taskCode);
			CustomReportTaskVO taskVO2 = new CustomReportTaskVO(taskOb);
			reportTasks = customReportTaskService.selectOneByTaskCode(taskVO2);
		}
		
		ret.put("data", reportTasks);
		ret.put("recordsFiltered", reportTasks==null?0:reportTasks.size());
		ret.put("game", games);
		return ret;
	}
	
	@RequestMapping(value = {"/addTask.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> addTask(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String valueArrStr = request.getParameter("valueArr");
		String nameArrStr = request.getParameter("nameArr");
		String reportModel = request.getParameter("reportModel");
		
		if(StringUtils.isEmpty(valueArrStr)
			|| StringUtils.isEmpty(nameArrStr)
			|| StringUtils.isEmpty(reportModel)){
			ret.put("msg", AddTaskRetStatus.PARM_NULL.ordinal());
			return ret;
		}
		
		String[] nameArr = nameArrStr.split(",");
		String[] valueArr = valueArrStr.split(",");
		int status = checkValue(nameArr, valueArr);
		if(status != 0){
			ret.put("msg", status);
			return ret;
		}else{
			CustomReportModel customReportModel = customReportModelService.getById(Long.valueOf(reportModel));
			if(customReportModel == null){
				ret.put("msg", AddTaskRetStatus.PARM_NULL.ordinal());
				return ret;
			}
			CustomReportTaskProcessor processor = new CustomReportTaskProcessor(customReportModel,nameArr,valueArr);
			
			// 查询此任务是否存在 此模板是否还有未完成的任务在执行
            String taskCode = processor.getTaskCode();
            String taskName = processor.getTaskName();
            CustomReportTask task = new CustomReportTask(games.getSnid(),games.getGameid(),customReportModel.getCode()+"_"+taskCode);
    		CustomReportTaskVO taskVO = new CustomReportTaskVO(task);
    		List<CustomReportTask> reportTasks = customReportTaskService.selectOneByTaskCode(taskVO);
    		if(reportTasks.size()!=0){
    			return null;
    		}else{
    			// 新建任务
    			task.setTaskName(customReportModel.getName()+"("+taskName+")");
    			task.setCreateTime(new Date());
    			task.setExecuteTime(new Date());
    			task.setExecuteUserId(users.getId());
    			task.setExecuteUserName(users.getLoginName());
    			task.setIntervalTime(customReportModel.getIntervalTime());
    			task.setStatus(CustomReportTaskStatus.RUNNING.ordinal());
    			task.setTemplateId(customReportModel.getId());
    			customReportTaskService.saveEntity(task);
    			
    			// 执行任务、 分析接口返回数据 、入库，展示数据
    			CUSTOM_REPORT_THREAD_POOL.execute(new CustomTaskThread(task.getId(), processor.sql(), processor.isPresto(),
    					CustomReportTaskExecuteTimes.FIRST,customTaskJob,customReportTaskService));
    			
                ret.put("msg", status);
    			return ret;
    		}
		}
		
	}
	
	/**
	 * 判断传入的参数是否完整
	 * @param nameArr
	 * @param valueArr
	 * @return
	 */
	private int checkValue(String[] nameArr,String[] valueArr) {
		if(nameArr.length < 1 || valueArr.length < 1){
			return AddTaskRetStatus.PARM_NULL.ordinal();
		}
		if(nameArr.length != valueArr.length){
			return AddTaskRetStatus.ERROR.ordinal();
		}
		for(String name:nameArr){
			if(StringUtils.isEmpty(name)){
				return AddTaskRetStatus.PARM_NULL.ordinal();
			}
		}
		for(String value:valueArr){
			if(StringUtils.isEmpty(value)){
				return AddTaskRetStatus.PARM_NULL.ordinal();
			}
		}
		return AddTaskRetStatus.SUCCESS.ordinal();
	}
	
	//总览
	@RequestMapping(value = {"/getGameTotalRealTime.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	@ResponseBody
	public Map<String,Object> getGameRealTime(HttpServletRequest request,HttpSession session)throws Exception {
		
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		
		
		
		RealTimeGameVo realTimeGameVo = new RealTimeGameVo();
		realTimeGameVo.setSnid(games.getSnid());
		realTimeGameVo.setGameid(games.getGameid());
		realTimeGameVo.setDay(beginDate);
		
		List<RealTimeGame> newDate = realtTimeGamesService.getSelectNewDate(realTimeGameVo);
	
		if(newDate.size()>0){
			
			realTimeGameVo.setFinish_time(newDate.get(0).getFinish_time());
			
		}else{

			Date date = new Date();
//			Calendar calendar = Calendar.getInstance();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			calendar.add(Calendar.DAY_OF_MONTH, -1);
			int startHour = date.getHours();
			String finish_time = "";
			finish_time = (startHour>9?startHour:"0"+startHour)+":00:00";
			realTimeGameVo.setFinish_time(finish_time);
		}
		
		
		//realTimeGameVo.setFinish_time(String.valueOf(new Date().getHours())+":00:00");
		List<RealTimeGame> realTimeGameList = realtTimeGamesService.getSelectGamesList(realTimeGameVo);
		
		ret.put("noClientResultList", realTimeGameList);
		return ret;
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
		String channelStr = request.getParameter("channel");
		String source = request.getParameter("source");
		String clientid = request.getParameter("clientid");
		
		GameDataPage page = GameDataPage.convertToPage(view);
		GameRoleAnalysisChannel channel = GameRoleAnalysisChannel.convertToChannel(channelStr);
		
		UserRetentionSourceLtv ltv = new UserRetentionSourceLtv(games.getSnid(),games.getGameid(),null);
		UserRetentionSourceLtvVO sourceLtvVO = new UserRetentionSourceLtvVO(ltv);
		sourceLtvVO.setDate(beginDate, endDate);
		
		UserRetentionClientLtv clientltv = new UserRetentionClientLtv(games.getSnid(),games.getGameid(),null);
		UserRetentionClientLtvVO clientltvVO = new UserRetentionClientLtvVO(clientltv);
		clientltvVO.setDate(beginDate, endDate);
		
		SourceDailyReport sourceDailyReport = new SourceDailyReport(games.getSnid(),games.getGameid());
		SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setDate(beginDate, endDate);
		
		ClientDailyReport clientDailyReport = new ClientDailyReport(games.getSnid(),games.getGameid());
		ClientDailyReportVO clientDailyReportVO = new ClientDailyReportVO(clientDailyReport);
		clientDailyReportVO.setDate(beginDate, endDate);
		
		Map<String,Object> pageInfo = this.findPageInfo(request);
		
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
		
		switch(page){
		case DAILY:
			switch(channel){
			case SOURCE:
				sourceDailyReportVO.setOffset(0l);
				sourceDailyReportVO.setRows(sourceDailyReportService.selectCount(sourceDailyReportVO));
				List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectListJoin(sourceDailyReportVO);
				processor.initSourceList(sourceDailyReports);
				processor.writewapSourceDaily();
				break;
			case CLIENT:
				clientDailyReportVO.setOffset(0l);
				clientDailyReportVO.setRows(clientDailyReportService.selectCount(clientDailyReportVO));
				List<ClientDailyReport> clientDailyReports = clientDailyReportService.selectList(clientDailyReportVO);
				processor.initClientList(clientDailyReports);
				processor.writewapClientDaily();
				break;
			}
			break;
		case RETENTION:
			List<UserRetentionSourceLtvVO> sourceLtvs = null;
			switch (channel) {
			case INSTALL_SOURCE:
				sourceLtvVO.setOffset(0l);
				sourceLtvVO.setRows(userRetentionSourceLtvService.selectCount(sourceLtvVO));
				sourceLtvs = userRetentionSourceLtvService.selectAllInstallRetentionList(sourceLtvVO);
				processor.initSourceLtvList(sourceLtvs);
				processor.writeSourceRetention(GameplayerType.INSATLL);
				break;
			case ROLE_SOURCE:
				sourceLtvVO.setOffset(0l);
				sourceLtvVO.setRows(userRetentionSourceLtvService.selectCount(sourceLtvVO));
				sourceLtvs = userRetentionSourceLtvService.selectAllRoleRetentionList(sourceLtvVO);
				processor.initSourceLtvList(sourceLtvs);
				processor.writeSourceRetention(GameplayerType.ROLE_CHOICE);
				break;
			case CLIENT:
				clientltvVO.setOffset(0l);
				clientltvVO.setRows(userRetentionClientLtvService.selectCount(clientltvVO));
				processor.initClientLtvList(userRetentionClientLtvService.selectAllInstallRetentionList(clientltvVO));
				processor.writeClientRetention();
				break;
			default:
				break;
			}
			break;
		case LIFE:
			List<UserRetentionSourceLtvVO> sourcePayLtvs = null;
			List<UserRetentionSourceLtvVO> lifeLtvs_totals = null;
			switch (channel) {
			case INSTALL_SOURCE:
				sourceLtvVO.setOffset(0l);
				sourceLtvVO.setRows(userRetentionSourceLtvService.selectCount(sourceLtvVO));
				sourcePayLtvs = userRetentionSourceLtvService.selectAllInstallPayList(sourceLtvVO);
				//按注册    	各个渠道的合计
				 lifeLtvs_totals = userRetentionSourceLtvService.selectHorizontal4TotalInstallPayList_export(sourceLtvVO);
				if(sourcePayLtvs.size()>0){
					for(int i=0;i<lifeLtvs_totals.size();i++){
						lifeLtvs_totals.get(i).getGameLtv().setInstallDay("合计");
					}
				}
				sourcePayLtvs.addAll(lifeLtvs_totals);
				processor.initSourceLtvList(sourcePayLtvs);
				processor.writeSourcePayLtv(GameplayerType.INSATLL);
				break;
			case ROLE_SOURCE:
				sourceLtvVO.setOffset(0l);
				sourceLtvVO.setRows(userRetentionSourceLtvService.selectCount(sourceLtvVO));
				sourcePayLtvs = userRetentionSourceLtvService.selectAllRolePayList(sourceLtvVO);
				//注收比  按创角-分渠道-导出所有渠道合计
				lifeLtvs_totals = userRetentionSourceLtvService.selectAllRolePayList_export(sourceLtvVO);
				if(sourcePayLtvs.size()>0){
					for(int i=0;i<lifeLtvs_totals.size();i++){
						lifeLtvs_totals.get(i).getGameLtv().setInstallDay("合计");
					}
				}
				sourcePayLtvs.addAll(lifeLtvs_totals);
				processor.initSourceLtvList(sourcePayLtvs);
				processor.writeSourcePayLtv(GameplayerType.ROLE_CHOICE);
				break;
			case CLIENT:
				clientltvVO.setOffset(0l);
				clientltvVO.setRows(userRetentionClientLtvService.selectCount(clientltvVO));
				List<UserRetentionClientLtvVO>  clientLtvs = userRetentionClientLtvService.selectAllInstallPayList(clientltvVO);
				//注收比  各个分服的   合计
				List<UserRetentionClientLtvVO> lifelts_totals = userRetentionClientLtvService.selectAllInstallPayList_exporp(clientltvVO);
				if(clientLtvs.size()>0){
						for(int i=0;i<lifelts_totals.size();i++){
							lifelts_totals.get(i).getGameLtv().setInstallDay("合计");
						}
				}
				clientLtvs.addAll(lifelts_totals);
				processor.initClientLtvList(clientLtvs);
				processor.writeClientPayLtv();
				break;
			default:
				break;
			}
			break;
		case MONEY:
			//List<UserRetentionSourceLtvVO> sourceMoneyLtvs = null;
			//List<UserRetentionSourceLtvVO> lifeLtvs_totals = null;
			switch (channel) {
			case INSTALL_SOURCE:
				sourceLtvVO.setOffset(0l);
				sourceLtvVO.setRows(userRetentionSourceLtvService.selectCount(sourceLtvVO));
				sourcePayLtvs = userRetentionSourceLtvService.selectAllInstallPayList(sourceLtvVO);
				//    	各个渠道的合计
				 lifeLtvs_totals = userRetentionSourceLtvService.selectHorizontal4TotalInstallPayList_export(sourceLtvVO);
				if(sourcePayLtvs.size()>0){
					for(int i=0;i<lifeLtvs_totals.size();i++){
						lifeLtvs_totals.get(i).getGameLtv().setInstallDay("合计");
					}
				}
				sourcePayLtvs.addAll(lifeLtvs_totals);
				processor.initSourceLtvList(sourcePayLtvs);
				processor.writeSourcePayLtv_Datamoney(GameplayerType.INSATLL);
				break;
			case ROLE_SOURCE:
				sourceLtvVO.setOffset(0l);
				sourceLtvVO.setRows(userRetentionSourceLtvService.selectCount(sourceLtvVO));
				sourcePayLtvs = userRetentionSourceLtvService.selectAllRolePayList(sourceLtvVO);
				//  按角色-分渠道-导出所有渠道合计
				lifeLtvs_totals = userRetentionSourceLtvService.selectAllRolePayList_export(sourceLtvVO);
				if(sourcePayLtvs.size()>0){
					for(int i=0;i<lifeLtvs_totals.size();i++){
						lifeLtvs_totals.get(i).getGameLtv().setInstallDay("合计");
					}
				}
				sourcePayLtvs.addAll(lifeLtvs_totals);
				processor.initSourceLtvList(sourcePayLtvs);
				processor.writeSourcePayLtv_Datamoney(GameplayerType.ROLE_CHOICE);
				break;
			case CLIENT:
				clientltvVO.setOffset(0l);
				clientltvVO.setRows(userRetentionClientLtvService.selectCount(clientltvVO));
				List<UserRetentionClientLtvVO>  clientLtvs = userRetentionClientLtvService.selectAllInstallPayList(clientltvVO);
				//金额  各个分服的   合计
				List<UserRetentionClientLtvVO> lifelts_totals = userRetentionClientLtvService.selectAllInstallPayList_exporp(clientltvVO);
				if(clientLtvs.size()>0){
						for(int i=0;i<lifelts_totals.size();i++){
							lifelts_totals.get(i).getGameLtv().setInstallDay("合计");
						}
				}
				clientLtvs.addAll(lifelts_totals);
				processor.initClientLtvList(clientLtvs);
				processor.writeClientPayLtv_Datamoney();
				break;
			default:
				break;
			}
			break;
		}
		return null;
	}
	
	@RequestMapping(value = {"/getWapGameRealTimeClientView_{pageNo:\\d+}.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getWapGameRealTimeClientView(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session)throws Exception {
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
		
		switch(page){
		case REALTIME:
			RealTimeResult realTimeResult = new RealTimeResult(games.getSnid(),games.getGameid());
			RealTimeResultVO realTimeResultVO = new RealTimeResultVO(realTimeResult);
			realTimeResultVO.setBeginDate(beginDate);
			realTimeResultVO.setEndDate(endDate);
			
			int queryCount = Long.valueOf(realTimeResultService.selecCount(realTimeResultVO)).intValue();
			queryCount = queryCount < 1 ? 1 : queryCount;
			PaginationResult<RealTimeResult> paginationResult = realTimeResultService.getPaginationResult(realTimeResultVO, pageNo, queryCount);
			List<RealTimeResult> displayResultList = new ArrayList<RealTimeResult>();
			for(RealTimeResult rtr:paginationResult.getResults()){
				displayResultList.add(rtr);
			}
			
			int count = new Long(paginationResult.getPagination().getRecordCount()).intValue();
			PanelPagination displayPagination = new PanelPagination(pageNo, PAGE_SIZE, count);
			
			ret.put("resultList",displayResultList);
			ret.put("dataCount",count);
			ret.put("pageCount", displayPagination.getTotalPage());
			ret.put("game", games);
			break;
		}
		
		return ret;
	}
	
	@RequestMapping(value = {"/getWapGameDatasView.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getWapGameDatasView(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String view = request.getParameter("view");
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String channelStr = request.getParameter("channel");
		String source = request.getParameter("source");
		String clientid = request.getParameter("clientid");
		
		GameDataPage page = GameDataPage.convertToPage(view);
		GameRoleAnalysisChannel channel = GameRoleAnalysisChannel.convertToChannel(channelStr);
		
		if(page != GameDataPage.REALTIME){
			super.setSessionDate(request, beginDate, endDate);
		}
		
		UserRetentionSourceLtv ltv = new UserRetentionSourceLtv(games.getSnid(),games.getGameid(),source);
		UserRetentionSourceLtvVO sourceLtvVO = new UserRetentionSourceLtvVO(ltv);
		sourceLtvVO.setDate(beginDate, endDate);
		
		UserRetentionLtv ltv_daily = new UserRetentionLtv(games.getSnid(),games.getGameid());
		UserRetentionLtvVO ltvVO_daily = new UserRetentionLtvVO(ltv_daily);
		ltvVO_daily.setDate(beginDate, endDate);
		
		Map<String,Object> pageInfo = this.findPageInfo(request);
		long recordsTotal = 0;
		long recordsFiltered = 0;
		
		switch(page){
		case REALTIME:
			RealTimeNoClientResult realTimeNoClientResult = new RealTimeNoClientResult(games.getSnid(),games.getGameid());
			RealTimeNoClientResultVO realTimeNoClientResultVO = new RealTimeNoClientResultVO(realTimeNoClientResult);
			realTimeNoClientResultVO.setBeginDate(beginDate);
			realTimeNoClientResultVO.setEndDate(endDate);
			
			ret.put("noClientResultList",realTimeNoClientResultService.getMatch(realTimeNoClientResultVO));
			
			break;
		case RETENTION://留存
			//留存  按注册-分渠道     所有渠道
			//留存  按角色-分渠道     所有渠道
			//分服    所有服务器
			if("-99".equals(source)){  
				sourceLtvVO.getEntity().setSource(null);
				//总记录数
				recordsTotal = userRetentionSourceLtvService.selectCount(sourceLtvVO);
				// 过滤记录数
				sourceLtvVO.setSearchValue((String) pageInfo.get("searchValue"));
				recordsFiltered = userRetentionSourceLtvService.selectCount(sourceLtvVO);
				
				// 分页数据
				sourceLtvVO.setOffset((Long) pageInfo.get("start"));
				sourceLtvVO.setRows((Long) pageInfo.get("length"));
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
			}
			switch(channel){
			case INSTALL_SOURCE:
				//留存   按注册-分渠道   所有渠道
				if("-99".equals(source)){
					List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllInstallRetentionList(sourceLtvVO);
					ret.put("data", ltvs);
					return ret;
				}else{
					//留存  按注册-分渠道    HOOLAIYSDK_680          一个渠道
					ret.put("installRetentions", userRetentionSourceLtvService.selectHorizontal4InstallRetentionRateList(sourceLtvVO));
				}
				break;
			case ROLE_SOURCE:
				//按角色-分渠道   所有渠道
				if("-99".equals(source)){
					List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllRoleRetentionList(sourceLtvVO);
					ret.put("data", ltvs);
					return ret;
				}else{
					//按角色-分渠道   HOOLAIYSDK_680    一个渠道
					ret.put("installRetentions", userRetentionSourceLtvService.selectHorizontal4RoleRetentionRateList(sourceLtvVO));
				}
				break;
			case CLIENT:
				//分服   所有服务区
				UserRetentionClientLtv clientltv = new UserRetentionClientLtv(games.getSnid(),games.getGameid(),clientid);
				UserRetentionClientLtvVO clientltvVO = new UserRetentionClientLtvVO(clientltv);
				clientltvVO.setDate(beginDate, endDate);
				
				if("-99".equals(clientid)){
					clientltvVO.getEntity().setClientid(null);
					//总记录数
					recordsTotal = userRetentionClientLtvService.selectCount(clientltvVO);
					// 过滤记录数
					clientltvVO.setSearchValue((String) pageInfo.get("searchValue"));
					recordsFiltered = userRetentionClientLtvService.selectCount(clientltvVO);
					
					// 分页数据
					clientltvVO.setOffset((Long) pageInfo.get("start"));
					clientltvVO.setRows((Long) pageInfo.get("length"));
					
					ret.put("recordsTotal", recordsTotal);
					ret.put("recordsFiltered", recordsFiltered);
					
					List<UserRetentionClientLtvVO> ltvs = userRetentionClientLtvService.selectAllInstallRetentionList(clientltvVO);
					ret.put("data", ltvs);
					return ret;
				}else{
					//留存   分服    309
					ret.put("installRetentions", userRetentionClientLtvService.selectHorizontal4RateList(clientltvVO));
				}
				
				break;
			case ROLE_ALL:
				//留存    按角色--总览
				ret.put("installRetentions", userRetentionLtvService.selectHorizontal4RoleRetentionRateList(ltvVO_daily));
			    break;
			case INSTALL_ALL:
				default:
					//留存   按注册--总览 
					ret.put("installRetentions", userRetentionLtvService.selectHorizontal4InstallRetentionRateList(ltvVO_daily));
					break;
			}
			break;
		case LIFE: //注收比
			//注收比  按注册-分渠道   所有渠道
			//注收比  按角色-分渠道    所有渠道 
			//注收比  分服    所有服务器
			if("-99".equals(source)){
				sourceLtvVO.getEntity().setSource(null);
				//总记录数
				recordsTotal = userRetentionSourceLtvService.selectCount(sourceLtvVO);
				// 过滤记录数
				sourceLtvVO.setSearchValue((String) pageInfo.get("searchValue"));
				recordsFiltered = userRetentionSourceLtvService.selectCount(sourceLtvVO);
				
				// 分页数据
				sourceLtvVO.setOffset((Long) pageInfo.get("start"));
				sourceLtvVO.setRows((Long) pageInfo.get("length"));
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
			}
			
			switch(channel){
					case INSTALL_SOURCE:
						//注收比   按注册-分渠道   所有渠道
						if("-99".equals(source)){
							List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllInstallPayList(sourceLtvVO);
							ret.put("data", ltvs);
							return ret;
						}else{
							//注收比    按注册-分渠道    一个渠道
							UserRetentionSourceLtvVO userRetentionSourceLtvVO = userRetentionSourceLtvService.selectHorizontal4TotalInstallPayList2(sourceLtvVO);
							List<UserRetentionSourceLtvVO> userRetentionSourceLtvVOs = userRetentionSourceLtvService.selectHorizontal4InstallPayList(sourceLtvVO);
							if(userRetentionSourceLtvVOs.size()>0){
								userRetentionSourceLtvVO.setDay("合计");
							}
							ret.put("ltvs", userRetentionSourceLtvVOs);
							ret.put("totals", userRetentionSourceLtvVO);
						}
						
						break;
					case ROLE_SOURCE:
						//注收比   按角色-分渠道  所有渠道 
						if("-99".equals(source)){
							List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllRolePayList(sourceLtvVO);
							ret.put("data", ltvs);
							return ret;
						}else{
							//注收比   按角色--分渠道
							UserRetentionSourceLtvVO userRetentionSourceLtvVO = userRetentionSourceLtvService.selectHorizontal4TotalRolePayList2(sourceLtvVO);
																													 
							List<UserRetentionSourceLtvVO>  userRetentionSourceLtvVOs = userRetentionSourceLtvService.selectHorizontal4RolePayList(sourceLtvVO);
																													  
							if(userRetentionSourceLtvVOs.size()>0){
								userRetentionSourceLtvVO.setDay("合计");
							}
							ret.put("ltvs",userRetentionSourceLtvVOs);
							ret.put("totals", userRetentionSourceLtvVO);
						}
						break;
					case CLIENT:
						//注收比   分服   所有服务器
						UserRetentionClientLtv clientltv = new UserRetentionClientLtv(games.getSnid(),games.getGameid(),clientid);
						UserRetentionClientLtvVO clientltvVO = new UserRetentionClientLtvVO(clientltv);
						clientltvVO.setDate(beginDate, endDate);
						
						if("-99".equals(clientid)){
							clientltvVO.getEntity().setClientid(null);
							//总记录数
							recordsTotal = userRetentionClientLtvService.selectCount(clientltvVO);
							// 过滤记录数
							clientltvVO.setSearchValue((String) pageInfo.get("searchValue"));
							recordsFiltered = userRetentionClientLtvService.selectCount(clientltvVO);
							
							// 分页数据
							clientltvVO.setOffset((Long) pageInfo.get("start"));
							clientltvVO.setRows((Long) pageInfo.get("length"));
							
							ret.put("recordsTotal", recordsTotal);
							ret.put("recordsFiltered", recordsFiltered);
							
							List<UserRetentionClientLtvVO> ltvs = userRetentionClientLtvService.selectAllInstallPayList(clientltvVO);
							ret.put("data", ltvs);
							return ret;
						}else{
							// 注收比  分服   一个服务器
							UserRetentionClientLtvVO userRetentionClientLtvVO = userRetentionClientLtvService.selectHorizontalTotalList(clientltvVO);
							List<UserRetentionClientLtvVO> userRetentionClientLtvVOs = userRetentionClientLtvService.selectHorizontalList(clientltvVO);
							if(userRetentionClientLtvVOs.size()>0){
								userRetentionClientLtvVO.setDay("合计");
							}
							ret.put("ltvs", userRetentionClientLtvVOs);
							ret.put("totals", userRetentionClientLtvVO);
						}
						
						break;
					case ROLE_ALL:
						//注收比     按角色-总览  2017-4-17  更改
						UserRetentionLtvVO userRetentionLtvVO = userRetentionLtvService.selectHorizontal4TotalRolePayList2(ltvVO_daily);
						List<UserRetentionLtvVO> userRetentionLtvVOs = userRetentionLtvService.selectHorizontal4RolePayList(ltvVO_daily);
						if(userRetentionLtvVOs.size()>0){
							userRetentionLtvVO.setDay("合计");
						}
						ret.put("ltvs", userRetentionLtvVOs);
						ret.put("totals",userRetentionLtvVO);
						
						break;
					case INSTALL_ALL:
						default:
							//注收比   按注册--总览
							 userRetentionLtvVO = userRetentionLtvService.selectHorizontal4TotalPayList2(ltvVO_daily);
							if(userRetentionLtvService.selectHorizontal4InstallPayList(ltvVO_daily).size()>0){
								userRetentionLtvVO.setDay("合计");
							}
							ret.put("ltvs", userRetentionLtvService.selectHorizontal4InstallPayList(ltvVO_daily));
							ret.put("totals", userRetentionLtvVO);
							break;
					}
			break;
		case MONEY://金额      金额 注收比   调用的方法都一样，区别：在js中处理的，金额需要 除(/) 新注册
			//金额  按注册-分渠道   所有渠道
			//金额  按角色-分渠道    所有渠道 
			//金额  分服    所有服务器
			if("-99".equals(source)){
				sourceLtvVO.getEntity().setSource(null);
				//总记录数
				recordsTotal = userRetentionSourceLtvService.selectCount(sourceLtvVO);
				// 过滤记录数
				sourceLtvVO.setSearchValue((String) pageInfo.get("searchValue"));
				recordsFiltered = userRetentionSourceLtvService.selectCount(sourceLtvVO);
				
				// 分页数据
				sourceLtvVO.setOffset((Long) pageInfo.get("start"));
				sourceLtvVO.setRows((Long) pageInfo.get("length"));
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
			}
			
			switch(channel){
					case INSTALL_SOURCE:
						//金额   按注册-分渠道   所有渠道
						if("-99".equals(source)){
							List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllInstallPayList(sourceLtvVO);
							//List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectHorizontal4InstallPayList(sourceLtvVO);
							ret.put("data", ltvs);
							return ret;
						}else{
							//金额    按注册-分渠道    一个渠道
							UserRetentionSourceLtvVO userRetentionSourceLtvVO = userRetentionSourceLtvService.selectHorizontal4TotalInstallPayList(sourceLtvVO);
							List<UserRetentionSourceLtvVO> userRetentionSourceLtvVOs = userRetentionSourceLtvService.selectHorizontal4InstallPayList(sourceLtvVO);
							if(userRetentionSourceLtvVOs.size()>0){
								userRetentionSourceLtvVO.setDay("合计");
							}
							ret.put("money", userRetentionSourceLtvVOs);
							ret.put("money_totals", userRetentionSourceLtvVO);
						}
						
						break;
					case ROLE_SOURCE:
						//金额   按角色-分渠道  所有渠道 
						if("-99".equals(source)){
							List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllRolePayList(sourceLtvVO);
							ret.put("data", ltvs);
							return ret;
						}else{
							//金额   按角色--分渠道
							UserRetentionSourceLtvVO userRetentionSourceLtvVO = userRetentionSourceLtvService.selectHorizontal4TotalRolePayList(sourceLtvVO);
							List<UserRetentionSourceLtvVO>  userRetentionSourceLtvVOs = userRetentionSourceLtvService.selectHorizontal4RolePayList(sourceLtvVO);
																													  
							if(userRetentionSourceLtvVOs.size()>0){
								userRetentionSourceLtvVO.setDay("合计");
							}
							ret.put("money",userRetentionSourceLtvVOs);
							ret.put("money_totals", userRetentionSourceLtvVO);
						}
						break;
					case CLIENT:
						//金额   分服   所有服务器
						UserRetentionClientLtv clientltv = new UserRetentionClientLtv(games.getSnid(),games.getGameid(),clientid);
						UserRetentionClientLtvVO clientltvVO = new UserRetentionClientLtvVO(clientltv);
						clientltvVO.setDate(beginDate, endDate);
						
						if("-99".equals(clientid)){
							clientltvVO.getEntity().setClientid(null);
							//总记录数
							recordsTotal = userRetentionClientLtvService.selectCount(clientltvVO);
							// 过滤记录数
							clientltvVO.setSearchValue((String) pageInfo.get("searchValue"));
							recordsFiltered = userRetentionClientLtvService.selectCount(clientltvVO);
							
							// 分页数据
							clientltvVO.setOffset((Long) pageInfo.get("start"));
							clientltvVO.setRows((Long) pageInfo.get("length"));
							
							ret.put("recordsTotal", recordsTotal);
							ret.put("recordsFiltered", recordsFiltered);
							
							List<UserRetentionClientLtvVO> ltvs = userRetentionClientLtvService.selectAllInstallPayList(clientltvVO);
							ret.put("data", ltvs);
							return ret;
						}else{
							// 金额  分服   一个服务器
							UserRetentionClientLtvVO userRetentionClientLtvVO = userRetentionClientLtvService.selectHorizontalTotalList(clientltvVO);
							List<UserRetentionClientLtvVO> userRetentionClientLtvVOs = userRetentionClientLtvService.selectHorizontalList(clientltvVO);
							if(userRetentionClientLtvVOs.size()>0){
								userRetentionClientLtvVO.setDay("合计");
							}
							ret.put("money", userRetentionClientLtvVOs);
							ret.put("money_totals", userRetentionClientLtvVO);
						}
						
						break;
					case ROLE_ALL:
						//金额     按角色-总览
						UserRetentionLtvVO userRetentionLtvVO = userRetentionLtvService.selectHorizontal4TotalRolePayList(ltvVO_daily);
						List<UserRetentionLtvVO> userRetentionLtvVOs = userRetentionLtvService.selectHorizontal4RolePayList(ltvVO_daily);
						if(userRetentionLtvVOs.size()>0){
							userRetentionLtvVO.setDay("合计");
						}
						ret.put("money", userRetentionLtvVOs);
						ret.put("money_totals",userRetentionLtvVO);
						
						break;
					case INSTALL_ALL:
						default:
							//金额   按注册--总览
							UserRetentionLtvVO userRetentionLtvVO_money =  userRetentionLtvService.selectHorizontal4MoneyTotalPayList(ltvVO_daily);
							if(userRetentionLtvService.selectHorizontal4MoneyPayList(ltvVO_daily).size()>0){
								userRetentionLtvVO_money.setDay("合计");
							}
							ret.put("money",userRetentionLtvService.selectHorizontal4MoneyPayList(ltvVO_daily));
							ret.put("money_totals",userRetentionLtvVO_money);
							break;
					}
			break;
			
		case DAILY://日报
			default:
				switch(channel){
						case SOURCE://  日报   分渠道   所有渠道
							SourceDailyReport sourceDailyReport = new SourceDailyReport(games.getSnid(),games.getGameid(),source);
							SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
							sourceDailyReportVO.setDate(beginDate, endDate);
							
							if("-99".equals(source)){
								sourceDailyReportVO.getEntity().setSource(null);
								//总记录数
								recordsTotal = sourceDailyReportService.selectCount(sourceDailyReportVO);
								// 过滤记录数
								sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
								recordsFiltered = sourceDailyReportService.selectCount(sourceDailyReportVO);
								
								// 分页数据
								sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
								sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
								
								String orderCol = (String)pageInfo.get("orderCol");
								sourceDailyReportVO.setOrderCol(getOrderCol(orderCol,channel.name()));
								sourceDailyReportVO.setOrderType((String)pageInfo.get("orderType"));
								List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectListJoin(sourceDailyReportVO);
								
								ret.put("recordsTotal", recordsTotal);
								ret.put("recordsFiltered", recordsFiltered);
								ret.put("data", sourceDailyReports);
								return ret;
							}else{
								//日报  分渠道  一个渠道   HOOLAIYSDK_680
								List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.getMatchJoin(sourceDailyReportVO);
								SourceDailyReport sourceDailyReportTemp = sourceDailyReportService.getSumMatchSourceJoin(sourceDailyReportVO);
								if(sourceDailyReports.size()>0){
									sourceDailyReportTemp.setDay("合计");
								}
								ret.put("scReports", sourceDailyReports);
								ret.put("scReport", sourceDailyReportTemp);
							}
							break;
					case CLIENT://  日报  分服   所有服务器
						ClientDailyReport clientDailyReport = new ClientDailyReport(games.getSnid(), games.getGameid(), clientid);
						ClientDailyReportVO clientDailyReportVO = new ClientDailyReportVO(clientDailyReport);
						clientDailyReportVO.setDate(beginDate, endDate);
		
						if ("-99".equals(clientid)) {
							clientDailyReport.setClientid(null);
							clientDailyReportVO.setEntity(clientDailyReport);
		
							// 总记录数
							recordsTotal = clientDailyReportService.selectCount(clientDailyReportVO);
							// 过滤记录数
							clientDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
							recordsFiltered = clientDailyReportService.selectCount(clientDailyReportVO);
		
							// 分页数据
							clientDailyReportVO.setOffset((Long) pageInfo.get("start"));
							clientDailyReportVO.setRows((Long) pageInfo.get("length"));
							String orderCol = (String)pageInfo.get("orderCol");
							clientDailyReportVO.setOrderCol(getOrderCol_ff(orderCol,channel.name()));
							clientDailyReportVO.setOrderType((String)pageInfo.get("orderType"));
							List<ClientDailyReport> clientDailyReports = clientDailyReportService.selectList(clientDailyReportVO);
		
							ret.put("recordsTotal", recordsTotal);
							ret.put("recordsFiltered", recordsFiltered);
							ret.put("data", clientDailyReports);
							return ret;
						} else {
							//日报  分服   一个服务器
							List<ClientDailyReport> clientDailyReports = clientDailyReportService.getMatch(clientDailyReportVO);
							ClientDailyReport clientDailyReportTemp = clientDailyReportService.getClientMatch(clientDailyReportVO);
							if(clientDailyReports.size()>0){
								clientDailyReportTemp.setDay("合计");
							}
							ret.put("scReports", clientDailyReports);
							ret.put("scReport", clientDailyReportTemp);
						}
						break;
						case ALL://日报  总览
							default:
								DailyReport dailyReport = new DailyReport(games.getSnid(),games.getGameid());
								DailyReportVO dailyReportVO = new DailyReportVO(dailyReport);
								dailyReportVO.setDate(beginDate, endDate);
								
								List<DailyReport> dailyReports = dailyReportService.getMatchJoin(dailyReportVO);
								dailyReport = dailyReportService.getSumMatchJoin(dailyReportVO);
								if(dailyReports.size()>0){
									dailyReport.setDay("合计");
								}
								
		
								ret.put("report", dailyReport);
								ret.put("reports", dailyReports);
								break;
				}
		}
		
		ret.put("game", games);
		session.setAttribute("game", games);
		return ret;
	}
	
	private String getOrderCol(String orderCol, String type){
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "day";
			case 1:
				return type == "source" ? "source" : "clientid";
			case 2:
				//return "dnu";
				return "new_equip";
			case 3:
				//return "new_equip";
				return "dnu";
			case 4:
				return "role_choice";
			case 5:
				return "dau";
			case 8:
				return "pu";
			case 11:
				return "payment_cnt";
			case 12:
				return "pay_rate";
			case 14:
				return "new_pu";
			case 16:
				return "install_pu";
			case 18:
				return "roll_pay_users";
			case 19:
				return "roll_users";
			case 20:
				return "pcu";
			case 21:
				return "acu";
			case 22:
				return "avg_user_time";
			default:
				return null;
			}
		}
	}
	
	private String getOrderCol_ff(String orderCol, String type){
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "day";
			case 1:
				return type == "source" ? "source" : "clientid";
			case 2:
				//return "dnu";
				return "dnu";
			case 3:
				//return "new_equip";
				return "dnu";
			case 4:
				return "dnu";
			case 5:
				return "dau";
			case 8:
				return "pu";
			case 11:
				return "payment_cnt";
			case 12:
				return "pay_rate";
			case 14:
				return "new_pu";
			case 16:
				return "install_pu";
			case 18:
				return "roll_pay_users";
			case 19:
				return "roll_users";
			case 20:
				return "pcu";
			case 21:
				return "acu";
			case 22:
				return "avg_user_time";
			default:
				return null;
			}
		}
	}
}

