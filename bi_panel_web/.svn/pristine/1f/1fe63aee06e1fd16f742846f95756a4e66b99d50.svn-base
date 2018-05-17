package com.hoolai.panel.web.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.Types.GameAnalysisChannel;
import com.hoolai.bi.report.model.Types.GameplayerAnalysisPage;
import com.hoolai.bi.report.model.Types.GameplayerType;
import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.GameLtv;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.model.entity.UserRetentionClientLtv;
import com.hoolai.bi.report.model.entity.UserRetentionLtv;
import com.hoolai.bi.report.model.entity.UserRetentionSourceLtv;
import com.hoolai.bi.report.service.ClientDailyReportService;
import com.hoolai.bi.report.service.DailyReportService;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.service.UserRetentionClientLtvService;
import com.hoolai.bi.report.service.UserRetentionLtvService;
import com.hoolai.bi.report.service.UserRetentionSourceLtvService;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.bi.report.vo.UserRetentionClientLtvVO;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;

@Controller
@RequestMapping("/panel_bi/gamePlayer")
public class GamePlayerController extends AbstractPanelController{
	
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private SourceDailyReportService sourceDailyReportService;
	@Autowired
	private ClientDailyReportService clientDailyReportService;
	@Autowired
	private UserRetentionClientLtvService userRetentionClientLtvService;
	@Autowired
	private UserRetentionSourceLtvService userRetentionSourceLtvService;
	@Autowired
	private UserRetentionLtvService userRetentionLtvService;
	
	@RequestMapping(value = {"/toGameplayerAnalysis.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String toGameplayerAnalysis(HttpServletRequest request,HttpSession session,
			@ModelAttribute("gamesVO") GamesVO gamesVO,@ModelAttribute("dailyReportVO") DailyReportVO dailyReportVO,
			BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		Games games = this.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		String view = request.getParameter("view");
		GameplayerAnalysisPage page = GameplayerAnalysisPage.convertToPage(view);
		switch(page){
		case DAU:
			return "games/player/gameDau.jsp";
		case RETENTION:
			return "games/player/gameRetention.jsp";
		case LIFE:
			return "games/player/gameLife.jsp";
		case INSTALL:
			return "games/player/gameInstall.jsp";
		default:
			return "games/player/gameInstall.jsp";
		}
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
		String indicators = request.getParameter("indicators");
		String searchValue = request.getParameter("search");
        String queryTypeStr = request.getParameter("queryType");
        
        //状态    1是注收比    2是金额
        String stats = request.getParameter("stats");
        //分渠道
        String source = request.getParameter("source2");
       //fenfu
        String clientid = request.getParameter("source2");
        
		String snid = games.getSnid();
		String gameid = games.getGameid();
		UserRetentionSourceLtv userRetentionSourceLtv = new UserRetentionSourceLtv(snid, gameid, source);
		UserRetentionSourceLtvVO userRetentionSourceLtvVO = new UserRetentionSourceLtvVO(userRetentionSourceLtv);
		userRetentionSourceLtvVO.setDate(beginDate, endDate);
		
		UserRetentionClientLtv userRetentionClientLtv = new UserRetentionClientLtv(snid, gameid, clientid);
		UserRetentionClientLtvVO userRetentionClientLtvVO = new UserRetentionClientLtvVO(userRetentionClientLtv);
		userRetentionClientLtvVO.setDate(beginDate, endDate);
		
		GameplayerAnalysisPage page = GameplayerAnalysisPage.convertToPage(view);
		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(indicators);
		GameplayerType queryType = GameplayerType.convertToType(queryTypeStr);
		
		SourceDailyReport sourceDailyReport = new SourceDailyReport(snid, gameid);
		SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setDate(beginDate, endDate);
		
		ClientDailyReport clientDailyReport = new ClientDailyReport(snid,gameid);
		ClientDailyReportVO clientDailyReportVO = new ClientDailyReportVO(clientDailyReport);
		clientDailyReportVO.setDate(beginDate, endDate);
		
		UserRetentionSourceLtv sourceLtv = new UserRetentionSourceLtv(snid, gameid, source);
		UserRetentionSourceLtvVO sourceLtvVO = new UserRetentionSourceLtvVO(sourceLtv);
		sourceLtvVO.setDate(beginDate, endDate);
		
		UserRetentionClientLtv clientLtv = new UserRetentionClientLtv(snid, gameid,clientid);
		UserRetentionClientLtvVO clientLtvVO = new UserRetentionClientLtvVO(clientLtv);
		clientLtvVO.setDate(beginDate, endDate);
		
		UserRetentionLtv ltv = new UserRetentionLtv(snid, gameid);
		UserRetentionLtvVO ltvVO = new UserRetentionLtvVO(ltv);
		ltvVO.setDate(beginDate, endDate);
		
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
		switch(channel){
		case SOURCE:
			if(page == GameplayerAnalysisPage.INSTALL || page == GameplayerAnalysisPage.DAU){
				sourceDailyReportVO.setSearchValue(StringUtils.isEmpty(searchValue) ? null : searchValue);
				sourceDailyReportVO.setOffset(0l);
				sourceDailyReportVO.setRows(sourceDailyReportService.selectCount(sourceDailyReportVO));
				List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectList(sourceDailyReportVO);
				processor.initSourceList(sourceDailyReports);
			}else{
				//注收比  按注册-分渠道-单个渠道
				sourceLtvVO.setSearchValue(StringUtils.isEmpty(searchValue) ? null : searchValue);
				sourceLtvVO.setOffset(0l);
				sourceLtvVO.setRows(userRetentionSourceLtvService.selectCount(sourceLtvVO));
			}
			
			switch(page){
			case INSTALL:
				processor.writeSourceInstall();
				break;
			case DAU:
				processor.writeSourceDau();
				break;
			case RETENTION:
				List<UserRetentionSourceLtvVO> sourceLtvs = null;
				switch(queryType){
				case ROLE_CHOICE:
					sourceLtvs = userRetentionSourceLtvService.selectAllRoleRetentionList(sourceLtvVO);
					break;
				case INSATLL:
					sourceLtvs = userRetentionSourceLtvService.selectAllInstallRetentionList(sourceLtvVO);
					break;
				}
				processor.initSourceLtvList(sourceLtvs);
				processor.writeSourceRetention(queryType);
				break;
			case LIFE:
				List<UserRetentionSourceLtvVO> lifeLtvs = null;
				switch(queryType){
				case ROLE_CHOICE:
					if(stats.equals("1")){
						//注收比  按创角-分渠道-单个渠道
						lifeLtvs = userRetentionSourceLtvService.selectAllRolePayList(sourceLtvVO);
						
						//注收比  按创角-分渠道-导出所有渠道合计
						List<UserRetentionSourceLtvVO> lifeLtvs_Role_Choice_totals = userRetentionSourceLtvService.selectAllRolePayList_export(sourceLtvVO);
						if(lifeLtvs.size()>0){
								lifeLtvs_Role_Choice_totals.get(0).getGameLtv().setInstallDay("合计");
						}
						//等于“”或者null 就导出所有渠道数据
						if(!source.equals("")&&!source.equals(null)){
							lifeLtvs.addAll(lifeLtvs_Role_Choice_totals);
						}
						
						processor.initSourceLtvList(lifeLtvs);
						processor.writeSourcePayLtv(queryType);
					}else{
						//金额   按创角-分渠道-单个渠道
						lifeLtvs = userRetentionSourceLtvService.selectAllRolePayList(sourceLtvVO);
						//金额  按创角-分渠道-导出所有渠道合计
						List<UserRetentionSourceLtvVO> lifeLtvs_Role_Choice_totals = userRetentionSourceLtvService.selectAllRolePayList_export(sourceLtvVO);
						if(lifeLtvs.size()>0){
								lifeLtvs_Role_Choice_totals.get(0).getGameLtv().setInstallDay("合计");
						}
						
						//等于“”或者null 就导出所有渠道数据
						if(!source.equals("")&&!source.equals(null)){
							lifeLtvs.addAll(lifeLtvs_Role_Choice_totals);
						}
						
						lifeLtvs.addAll(lifeLtvs_Role_Choice_totals);
						processor.initSourceLtvList(lifeLtvs);
						processor.writeSourcePayLtv_money(queryType);
					}
					
					break;
				case INSATLL:
					
					if(stats.equals("1")){
						//注收比  按注册-分渠道-单个渠道
						lifeLtvs = userRetentionSourceLtvService.selectAllInstallPayList(sourceLtvVO);
						//各个渠道的合计
						List<UserRetentionSourceLtvVO> lifeLtvs_totals = userRetentionSourceLtvService.selectHorizontal4TotalInstallPayList_export(sourceLtvVO);
						if(lifeLtvs.size()>0){
							lifeLtvs_totals.get(0).getGameLtv().setInstallDay("合计");
						}
						//等于“”或者null 就导出所有渠道数据
						if(!source.equals("")&&!source.equals(null)){
							lifeLtvs.addAll(lifeLtvs_totals);
						}
						
						processor.initSourceLtvList(lifeLtvs);
						processor.writeSourcePayLtv(queryType);
					}else{
						//金额导出
						lifeLtvs = userRetentionSourceLtvService.selectAllInstallPayList(sourceLtvVO);
						//各个渠道的合计
						List<UserRetentionSourceLtvVO> lifeLtvs_totals_money = userRetentionSourceLtvService.selectHorizontal4TotalInstallPayList_export(sourceLtvVO);
						if(lifeLtvs.size()>0){
								lifeLtvs_totals_money.get(0).getGameLtv().setInstallDay("合计");
						}
						//等于“”或者null 就导出所有渠道数据
						if(!source.equals("")&&!source.equals(null)){
							lifeLtvs.addAll(lifeLtvs_totals_money);
						}
						
						processor.initSourceLtvList(lifeLtvs);
						processor.writeSourcePayLtv_money(queryType);
					}
					
					break;
				}
				
				break;
			}
			break;
		case CLIENT:
			if(page == GameplayerAnalysisPage.INSTALL || page == GameplayerAnalysisPage.DAU){
				clientDailyReportVO.setSearchValue(StringUtils.isEmpty(searchValue) ? null : searchValue);
				clientDailyReportVO.setOffset(0l);
				clientDailyReportVO.setRows(clientDailyReportService.selectCount(clientDailyReportVO));
				List<ClientDailyReport> clientDailyReports = clientDailyReportService.selectList(clientDailyReportVO);
				processor.initClientList(clientDailyReports);
			}else{
				//注收比   按注册-分服
				clientLtvVO.setSearchValue(StringUtils.isEmpty(searchValue) ? null : searchValue);
				clientLtvVO.setOffset(0l);
				clientLtvVO.setRows(userRetentionClientLtvService.selectCount(clientLtvVO));
			}
			
			switch(page){
			case INSTALL:
				processor.writeClientInstall();
				break;
			case DAU:
				processor.writeClientDau();
				break;
			case RETENTION:
				List<UserRetentionClientLtvVO> lts = userRetentionClientLtvService.selectAllInstallRetentionList(clientLtvVO);
				processor.initClientLtvList(lts);
				processor.writeClientRetention();
				break;
			case LIFE:
				//导出
				if(stats.equals("1")){
					//注收比   按注册-分服
					List<UserRetentionClientLtvVO> lifelts = userRetentionClientLtvService.selectAllInstallPayList(clientLtvVO);
					List<UserRetentionClientLtvVO> lifelts_totals = userRetentionClientLtvService.selectAllInstallPayList_exporp(clientLtvVO);
					
					if(lifelts.size()>0){
								lifelts_totals.get(0).getGameLtv().setInstallDay("合计");
					}
					if(!clientid.equals("")&&!clientid.equals(null)){
						lifelts.addAll(lifelts_totals);
					}
					
					processor.initClientLtvList(lifelts);
					processor.writeClientPayLtv();
				}else{
					//金额   按注册-分服
					List<UserRetentionClientLtvVO> lifelts = userRetentionClientLtvService.selectAllInstallPayList(clientLtvVO);
					List<UserRetentionClientLtvVO> lifelts_totals = userRetentionClientLtvService.selectAllInstallPayList_exporp(clientLtvVO);
					if(lifelts.size()>0){
								lifelts_totals.get(0).getGameLtv().setInstallDay("合计");
					}
					if(!clientid.equals("")&&!clientid.equals(null)){
						lifelts.addAll(lifelts_totals);
					}
					
					processor.initClientLtvList(lifelts);
					processor.writeClientPayLtv_money();
				}
				
			}
			break;
			default:
				//总览
				if(stats.equals("1")){
					switch(queryType){
					case ROLE_CHOICE:  //注收比       按创角-总览
						//主收比导出
						//UserRetentionLtvVO userRetentionLtvVO = userRetentionLtvService.selectHorizontal4TotalPayList(ltvVO);
						UserRetentionLtvVO userRetentionLtvVO = userRetentionLtvService.selectHorizontal4TotalRolePayList2(ltvVO);
						List<UserRetentionLtvVO> ltvs = userRetentionLtvService.selectHorizontal4RolePayList(ltvVO);
						if(userRetentionLtvService.selectHorizontal4InstallPayList(ltvVO).size()>0){
							userRetentionLtvVO.getGameLtv().setInstallDay("合计");
							
						}
						ltvs.add(userRetentionLtvVO);
						processor.initClientRegList(ltvs);
						processor.writeClientPayLtvs();
						
						break;
					case INSATLL://注收比   按注册-总览
						 userRetentionLtvVO = userRetentionLtvService.selectHorizontal4TotalPayList2(ltvVO);
					     ltvs = userRetentionLtvService.selectHorizontal4InstallPayList(ltvVO);
						if(userRetentionLtvService.selectHorizontal4InstallPayList(ltvVO).size()>0){
							userRetentionLtvVO.getGameLtv().setInstallDay("合计");
							
						}
						ltvs.add(userRetentionLtvVO);
						processor.initClientRegList(ltvs);
						processor.writeClientPayLtvs();
						default:
					}
					
					
					
				}else{
					//金额导出
					UserRetentionLtvVO userRetentionLtvVO_money =  userRetentionLtvService.selectHorizontal4MoneyTotalPayList(ltvVO);
					List<UserRetentionLtvVO>  money = userRetentionLtvService.selectHorizontal4MoneyPayList(ltvVO);
					if(money.size()>0){
						userRetentionLtvVO_money.getGameLtv().setInstallDay("合计");
					}
					money.add(userRetentionLtvVO_money);
					processor.initClientMoneyList(money);
					processor.writeClientPayMoney();
				}
				
				//ret.put("money_totals",userRetentionLtvVO_money);
				//throw new RuntimeException();
		}
		return null;
	}
	
	@RequestMapping(value = {"/getGameplayerAnalysis.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameplayerAnalysis(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = this.getSessionGames(request);
		
		String view = request.getParameter("view");
		GameplayerAnalysisPage page = GameplayerAnalysisPage.convertToPage(view);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String snid = games.getSnid();
		String gameid = games.getGameid();
		String clientid = request.getParameter("clientid");
		
		super.setSessionDate(request, beginDate, endDate);
		
		DailyReport dailyReport = new DailyReport(snid,gameid);
		DailyReportVO dailyReportVO = new DailyReportVO(dailyReport);
		dailyReportVO.setDate(beginDate,endDate);
		
		SourceDailyReport sourceDailyReport = new SourceDailyReport(snid, gameid);
		SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setDate(beginDate, endDate);
		
		ClientDailyReport clientDailyReport = new ClientDailyReport(snid,gameid,clientid);
		ClientDailyReportVO clientDailyReportVO = new ClientDailyReportVO(clientDailyReport);
		clientDailyReportVO.setDate(beginDate, endDate);
		
		String indicators = request.getParameter("indicators");
		String source = request.getParameter("source");
		
		Map<String,Object> pageInfo = findPageInfo(request);
		
		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(indicators);
		switch(channel){
		case SOURCE:
			switch(page){
			case LIFE:
			case RETENTION:
				return getGameplayerLtvAnalysis(request, session);
			case INSTALL:
			case DAU:
			default:
				if("-99".equals(source)){
					sourceDailyReport.setSource(null);
					//总记录数
					long recordsTotal = sourceDailyReportService.selectCount(sourceDailyReportVO);
					// 过滤记录数
					sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
					long recordsFiltered = sourceDailyReportService.selectCount(sourceDailyReportVO);
					
					// 分页数据
					sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
					sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
					
					String orderCol = (String)pageInfo.get("orderCol");
					sourceDailyReportVO.setOrderCol(getOrderCol(channel,page,orderCol));
					sourceDailyReportVO.setOrderType((String)pageInfo.get("orderType"));
					
					List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectList(sourceDailyReportVO);
					
					ret.put("recordsTotal", recordsTotal);
					ret.put("recordsFiltered", recordsFiltered);
					ret.put("data", sourceDailyReports);
					return ret;
				}else{
					sourceDailyReport.setSource(source);
					List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.getMatch(sourceDailyReportVO);
					ret.put("scReports", sourceDailyReports);
					return ret;
				}
			}
		case CLIENT:
			switch(page){
			case LIFE:
			case RETENTION:
				return getGameplayerLtvAnalysis(request, session);
			case INSTALL:
			case DAU:
			default:
               if("-99".equals(clientid)){
            	   clientDailyReport.setClientid(null);
            	   clientDailyReportVO.setEntity(clientDailyReport);
					
					//总记录数
					long recordsTotal = clientDailyReportService.selectCount(clientDailyReportVO);
					// 过滤记录数
					clientDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
					long recordsFiltered = clientDailyReportService.selectCount(clientDailyReportVO);
					
					// 分页数据
					clientDailyReportVO.setOffset((Long) pageInfo.get("start"));
					clientDailyReportVO.setRows((Long) pageInfo.get("length"));
					
					String orderCol = (String)pageInfo.get("orderCol");
					clientDailyReportVO.setOrderCol(getOrderCol(channel,page,orderCol));
					clientDailyReportVO.setOrderType((String)pageInfo.get("orderType"));
					
					List<ClientDailyReport> clientDailyReports = clientDailyReportService.selectList(clientDailyReportVO);
					
					ret.put("recordsTotal", recordsTotal);
					ret.put("recordsFiltered", recordsFiltered);
					ret.put("data", clientDailyReports);
					return ret;
				}else{
					List<ClientDailyReport> clientDailyReports = clientDailyReportService.getMatch(clientDailyReportVO);
					ret.put("scReports", clientDailyReports);
					return ret;
				}
				
			}
		case ALL:
		default:
			switch(page){
			case DAU:
				ret.put("sdreports", sourceDailyReportService.selectDauForPieList(sourceDailyReportVO));
				ret.put("sdreportsCount", sourceDailyReportService.selectDauForPieListCount(sourceDailyReportVO));
				ret.put("reports", dailyReportService.getMatch(dailyReportVO));
				return ret;
			case RETENTION:
			case LIFE:
				return getGameplayerLtvAnalysis(request, session);
			case INSTALL:
			default:
				List<SourceDailyReportVO> sreports = sourceDailyReportService.selectInstallForPieList(sourceDailyReportVO);
				List<ClientDailyReportVO> creports = clientDailyReportService.selectInstallForPieList(clientDailyReportVO);
				
				ret.put("sreports", sreports);
				ret.put("sreportsCount", sourceDailyReportService.selectInstallForPieListCount(sourceDailyReportVO));
				ret.put("creports", creports);
				ret.put("creportsCount", clientDailyReportService.selectInstallForPieListCount(clientDailyReportVO));
				ret.put("reports", dailyReportService.getMatch(dailyReportVO));
				return ret;
			}
		}
	}

	private String getOrderCol(GameAnalysisChannel channel,GameplayerAnalysisPage page,String orderCol) {
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "day";
			case 1:
				return channel.value() == "source" ? null : "clientid+0";
			case 2:
				switch(page){
				case INSTALL:
					return "dnu";
				case DAU:
					return "dau";
				}
				return "dnu";
			case 3:
				switch(page){
				case INSTALL:
					return channel.value() == "source" ? "role_choice" : "day";
				case DAU:
					return "dnu";
				}
			case 4:
				switch(page){
				case INSTALL:
					return "install_pay_amount";
				case DAU:
					return "dau-dnu";
				}
			case 5:
				switch(page){
				case INSTALL:
					return "install_pay_amount/install_pu";
				case DAU:
					return "pcu";
				}
			case 6:
				switch(page){
				case INSTALL:
					return "install_pu/dnu";
				case DAU:
					return "acu";
				}
			case 7:
				switch(page){
				case INSTALL:
					return "install_pay_amount/dnu";
				case DAU:
					return "avg_user_time";
				}
			default:
				return null;
			}
		}
	}
	
	private Map<String,Object> getGameplayerLtvAnalysis(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = this.getSessionGames(request);
		
		String view = request.getParameter("view");
		GameplayerAnalysisPage page = GameplayerAnalysisPage.convertToPage(view);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String snid = games.getSnid();
		String gameid = games.getGameid();
		String source = request.getParameter("source");
		String clientid = request.getParameter("clientid");
		
		super.setSessionDate(request, beginDate, endDate);
		
		UserRetentionSourceLtv userRetentionSourceLtv = new UserRetentionSourceLtv(snid, gameid, source);
		UserRetentionSourceLtvVO userRetentionSourceLtvVO = new UserRetentionSourceLtvVO(userRetentionSourceLtv);
		userRetentionSourceLtvVO.setDate(beginDate, endDate);
		
		UserRetentionClientLtv userRetentionClientLtv = new UserRetentionClientLtv(snid, gameid, clientid);
		UserRetentionClientLtvVO userRetentionClientLtvVO = new UserRetentionClientLtvVO(userRetentionClientLtv);
		userRetentionClientLtvVO.setDate(beginDate, endDate);
		
		UserRetentionLtv ltv = new UserRetentionLtv(snid, gameid);
		UserRetentionLtvVO ltvVO = new UserRetentionLtvVO(ltv);
		ltvVO.setDate(beginDate, endDate);
		
		String indicators = request.getParameter("indicators");
		String queryTypeStr = request.getParameter("queryType");
		
		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(indicators);
		GameplayerType queryType = GameplayerType.convertToType(queryTypeStr);
		
		Map<String,Object> pageInfo = this.findPageInfo(request);
		long recordsTotal = 0;
		long recordsFiltered = 0;
		switch(channel){
		case SOURCE:
			if("-99".equals(source)){
				userRetentionSourceLtvVO.getEntity().setSource(null);
				//总记录数
				recordsTotal = userRetentionSourceLtvService.selectCount(userRetentionSourceLtvVO);
				// 过滤记录数
				userRetentionSourceLtvVO.setSearchValue((String) pageInfo.get("searchValue"));
				recordsFiltered = userRetentionSourceLtvService.selectCount(userRetentionSourceLtvVO);
				
				// 分页数据
				userRetentionSourceLtvVO.setOffset((Long) pageInfo.get("start"));
				userRetentionSourceLtvVO.setRows((Long) pageInfo.get("length"));
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
			}
			switch(page){
			case RETENTION:
				switch(queryType){
				case ROLE_CHOICE:
					if("-99".equals(source)){
						List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllRoleRetentionList(userRetentionSourceLtvVO);
						for(UserRetentionSourceLtvVO ltvo:ltvs){
							GameLtv gameLtv = ltvo.getGameLtv();
							resultToArray(gameLtv);
						}
						ret.put("data", ltvs);
						return ret;
					}else{
						ret.put("ltvsCharts",userRetentionSourceLtvService.selectAvgRoleRetentionDataList(userRetentionSourceLtvVO));
						ret.put("ltvs", userRetentionSourceLtvService.selectHorizontal4RoleRetentionRateList(userRetentionSourceLtvVO));
						return ret;
					}
				case INSATLL:
					default:
						if("-99".equals(source)){
							List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllInstallRetentionList(userRetentionSourceLtvVO);
							for(UserRetentionSourceLtvVO ltvo:ltvs){
								GameLtv gameLtv = ltvo.getGameLtv();
								resultToArray(gameLtv);
							}
							ret.put("data", ltvs);
							return ret;
						}else{
							ret.put("ltvsCharts",userRetentionSourceLtvService.selectAvgInstallRetentionDataList(userRetentionSourceLtvVO));
							ret.put("ltvs", userRetentionSourceLtvService.selectHorizontal4InstallRetentionRateList(userRetentionSourceLtvVO));
							return ret;
						}
				}
			case LIFE:
				switch(queryType){
				case ROLE_CHOICE:
					if("-99".equals(source)){
						List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllRolePayList(userRetentionSourceLtvVO);
						for(UserRetentionSourceLtvVO ltvo:ltvs){
							GameLtv gameLtv = ltvo.getGameLtv();
							resultToArray(gameLtv);
						}
						ret.put("data", ltvs);
						return ret;
					}else{
						//注收比  按创角-分渠道-单个渠道
						UserRetentionSourceLtvVO userRetentionSourceLtvVO_2 = userRetentionSourceLtvService.selectHorizontal4TotalRolePayList2(userRetentionSourceLtvVO);
						//金额  按创角-分渠道-单个渠道
						UserRetentionSourceLtvVO userRetentionSourceLtvVO_1 = userRetentionSourceLtvService.selectHorizontal4TotalRolePayList(userRetentionSourceLtvVO);
						List<UserRetentionSourceLtvVO>  userRetentionSourceLtvVOs = userRetentionSourceLtvService.selectHorizontal4RolePayList(userRetentionSourceLtvVO);
																												  
						if(userRetentionSourceLtvVOs.size()>0){
							userRetentionSourceLtvVO_1.setDay("合计");
							userRetentionSourceLtvVO_2.setDay("合计");
						}
						ret.put("ltvsCharts",userRetentionSourceLtvService.selectAvgRoleChioceLTVDataList(userRetentionSourceLtvVO));
						ret.put("ltvs",  userRetentionSourceLtvService.selectHorizontal4RolePayList(userRetentionSourceLtvVO));
						ret.put("totals", userRetentionSourceLtvVO_2);
						ret.put("money",  userRetentionSourceLtvService.selectHorizontal4RolePayList(userRetentionSourceLtvVO));
						ret.put("money_totals", userRetentionSourceLtvVO_1);
						return ret;
					}
				case INSATLL:
					default:
						if("-99".equals(source)){
							List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectAllInstallPayList(userRetentionSourceLtvVO);
							for(UserRetentionSourceLtvVO ltvo:ltvs){
								GameLtv gameLtv = ltvo.getGameLtv();
								resultToArray(gameLtv);
							}
							ret.put("data", ltvs);
							return ret;
						}else{
							//金额     按注册-分渠道-单个渠道
							UserRetentionSourceLtvVO userRetentionSourceLtvVO_1 = userRetentionSourceLtvService.selectHorizontal4TotalInstallPayList2(userRetentionSourceLtvVO);
							//注收比       按注册-分渠道-单个渠道
							UserRetentionSourceLtvVO userRetentionSourceLtvVO_2 = userRetentionSourceLtvService.selectHorizontal4TotalInstallPayList2(userRetentionSourceLtvVO);
							List<UserRetentionSourceLtvVO> userRetentionSourceLtvVOs = userRetentionSourceLtvService.selectHorizontal4InstallPayList(userRetentionSourceLtvVO);
							if(userRetentionSourceLtvVOs.size()>0){
								userRetentionSourceLtvVO_1.setDay("合计");
								userRetentionSourceLtvVO_2.setDay("合计");
							}
							ret.put("ltvsCharts",userRetentionSourceLtvService.selectAvgInstallLTVDataList(userRetentionSourceLtvVO));
							ret.put("ltvs", userRetentionSourceLtvVOs );
							ret.put("totals", userRetentionSourceLtvVO_2);
							ret.put("money", userRetentionSourceLtvVOs );
							ret.put("money_totals", userRetentionSourceLtvVO_1);
							return ret;
						}
				}
			default:
				return ret;
			}
			
		case CLIENT:
			
			if("-99".equals(clientid)){
				userRetentionClientLtvVO.getEntity().setClientid(null);
				//总记录数
				recordsTotal = userRetentionClientLtvService.selectCount(userRetentionClientLtvVO);
				// 过滤记录数
				userRetentionClientLtvVO.setSearchValue((String) pageInfo.get("searchValue"));
				recordsFiltered = userRetentionClientLtvService.selectCount(userRetentionClientLtvVO);
				
				// 分页数据
				userRetentionClientLtvVO.setOffset((Long) pageInfo.get("start"));
				userRetentionClientLtvVO.setRows((Long) pageInfo.get("length"));
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
			}
			switch(page){
			case RETENTION:
				if("-99".equals(clientid)){
					List<UserRetentionClientLtvVO> ltvs = userRetentionClientLtvService.selectAllInstallRetentionList(userRetentionClientLtvVO);
					for(UserRetentionClientLtvVO ltvo:ltvs){
						GameLtv gameLtv = ltvo.getGameLtv();
						resultToArray(gameLtv);
					}
					ret.put("data", ltvs);
					return ret;
				}else{
					ret.put("ltvsCharts",userRetentionClientLtvService.selectAvgDataList(userRetentionClientLtvVO));
					ret.put("ltvs", userRetentionClientLtvService.selectHorizontal4RateList(userRetentionClientLtvVO));
				}
				
				return ret;
			case LIFE:
				if("-99".equals(clientid)){
					List<UserRetentionClientLtvVO> ltvs = userRetentionClientLtvService.selectAllInstallPayList(userRetentionClientLtvVO);
					for(UserRetentionClientLtvVO ltvo:ltvs){
						GameLtv gameLtv = ltvo.getGameLtv();
						resultToArray(gameLtv);
					}
					ret.put("data", ltvs);
					return ret;
				}else{
					//注收比  金额   按注册-分服-单个服务器
					UserRetentionClientLtvVO userRetentionClientLtvVO_1 = userRetentionClientLtvService.selectHorizontalTotalList(userRetentionClientLtvVO);
					List<UserRetentionClientLtvVO> userRetentionClientLtvVOs = userRetentionClientLtvService.selectHorizontalList(userRetentionClientLtvVO);
					if(userRetentionClientLtvVOs.size()>0){
						userRetentionClientLtvVO_1.setDay("合计");
					}
					
					ret.put("ltvsCharts",userRetentionClientLtvService.selectAvgDataList(userRetentionClientLtvVO));
					ret.put("ltvs",  userRetentionClientLtvVOs);
					ret.put("totals", userRetentionClientLtvVO_1);
					ret.put("money",  userRetentionClientLtvVOs);
					ret.put("money_totals", userRetentionClientLtvVO_1);
					return ret;
				}
				
			default:
				return ret;
			}
		case ALL:
		default:
			switch(page){
			case RETENTION:
				switch(queryType){
				case ROLE_CHOICE:
					ret.put("ltvsCharts",userRetentionLtvService.selectAvgRoleRetentionDataList(ltvVO));
					ret.put("ltvs", userRetentionLtvService.selectHorizontal4RoleRetentionRateList(ltvVO));
					return ret;
				case INSATLL:
					default:
						ret.put("ltvsCharts",userRetentionLtvService.selectAvgInstallRetentionDataList(ltvVO));
						ret.put("ltvs", userRetentionLtvService.selectHorizontal4InstallRetentionRateList(ltvVO));
						return ret;
				}
			case LIFE:
				switch(queryType){
				case ROLE_CHOICE:
					//注收比    金额      按创角-总览
					UserRetentionLtvVO userRetentionLtvVO = userRetentionLtvService.selectHorizontal4TotalRolePayList(ltvVO);
					//注收比       按创角-总览
					UserRetentionLtvVO userRetentionLtvVO_2 = userRetentionLtvService.selectHorizontal4TotalRolePayList2(ltvVO);
					
					List<UserRetentionLtvVO> userRetentionLtvVOs = userRetentionLtvService.selectHorizontal4RolePayList(ltvVO);
					if(userRetentionLtvVOs.size()>0){
						userRetentionLtvVO.setDay("合计");
						userRetentionLtvVO_2.setDay("合计");
					}
					
					ret.put("ltvsCharts",userRetentionLtvService.selectAvgRoleChioceLTVDataList(ltvVO));
					ret.put("ltvs", userRetentionLtvService.selectHorizontal4RolePayList(ltvVO));
					ret.put("totals",userRetentionLtvVO_2);
					ret.put("money", userRetentionLtvService.selectHorizontal4RolePayList(ltvVO));
					ret.put("money_totals",userRetentionLtvVO);
					return ret;
				case INSATLL:
					default:
						//注收比  金额   按注册-总览
						 userRetentionLtvVO = userRetentionLtvService.selectHorizontal4TotalPayList(ltvVO);
						 //注收比   按注册-总览
						UserRetentionLtvVO	 userRetentionLtvVO2 = userRetentionLtvService.selectHorizontal4TotalPayList2(ltvVO);
						UserRetentionLtvVO userRetentionLtvVO_money =  userRetentionLtvService.selectHorizontal4MoneyTotalPayList(ltvVO);
						if(userRetentionLtvService.selectHorizontal4InstallPayList(ltvVO).size()>0 ){
							userRetentionLtvVO.setDay("合计");
							userRetentionLtvVO2.setDay("合计");
							userRetentionLtvVO_money.setDay("合计");
						}
						
						ret.put("ltvsCharts",userRetentionLtvService.selectAvgInstallLTVDataList(ltvVO));
						ret.put("ltvs", userRetentionLtvService.selectHorizontal4InstallPayList(ltvVO));
						ret.put("totals", userRetentionLtvVO2);
						ret.put("money",userRetentionLtvService.selectHorizontal4MoneyPayList(ltvVO));
						ret.put("money_totals",userRetentionLtvVO_money);
						return ret;
				}
			default:
				return ret;
			}
		}
	}

	private void resultToArray(GameLtv gameLtv) {
		double[] gameLtvs = {
				gameLtv.getD0() == null ? 0d : gameLtv.getD0(), 
				gameLtv.getD1() == null ? 0d : gameLtv.getD1(), 
				gameLtv.getD2() == null ? 0d : gameLtv.getD2(), 
				gameLtv.getD3() == null ? 0d : gameLtv.getD3(), 
				gameLtv.getD4() == null ? 0d : gameLtv.getD4(), 
				gameLtv.getD5() == null ? 0d : gameLtv.getD5(), 
				gameLtv.getD6() == null ? 0d : gameLtv.getD6(), 
				gameLtv.getD7() == null ? 0d : gameLtv.getD7(), 
				gameLtv.getD8() == null ? 0d : gameLtv.getD8(), 
				gameLtv.getD9() == null ? 0d : gameLtv.getD9(), 
				gameLtv.getD10() == null ? 0d : gameLtv.getD10(),
				gameLtv.getD11() == null ? 0d : gameLtv.getD11(),
				gameLtv.getD12() == null ? 0d : gameLtv.getD12(),
				gameLtv.getD13() == null ? 0d : gameLtv.getD13(),
				gameLtv.getD14() == null ? 0d : gameLtv.getD14(),
				gameLtv.getD15() == null ? 0d : gameLtv.getD15(),
				gameLtv.getD16() == null ? 0d : gameLtv.getD16(),
				gameLtv.getD17() == null ? 0d : gameLtv.getD17(),
				gameLtv.getD18() == null ? 0d : gameLtv.getD18(),
				gameLtv.getD19() == null ? 0d : gameLtv.getD19(),
				gameLtv.getD20() == null ? 0d : gameLtv.getD20(),
				gameLtv.getD21() == null ? 0d : gameLtv.getD21(),
				gameLtv.getD22() == null ? 0d : gameLtv.getD22(),
				gameLtv.getD23() == null ? 0d : gameLtv.getD23(),
				gameLtv.getD24() == null ? 0d : gameLtv.getD24(),
				gameLtv.getD25() == null ? 0d : gameLtv.getD25(),
				gameLtv.getD26() == null ? 0d : gameLtv.getD26(),
				gameLtv.getD27() == null ? 0d : gameLtv.getD27(),
				gameLtv.getD28() == null ? 0d : gameLtv.getD28(),
				gameLtv.getD29() == null ? 0d : gameLtv.getD29(),
				gameLtv.getD30() == null ? 0d : gameLtv.getD30(),
				gameLtv.getD31() == null ? 0d : gameLtv.getD31(),
				gameLtv.getD32() == null ? 0d : gameLtv.getD32(),
				gameLtv.getD33() == null ? 0d : gameLtv.getD33(),
				gameLtv.getD34() == null ? 0d : gameLtv.getD34(),
				gameLtv.getD35() == null ? 0d : gameLtv.getD35(),
				gameLtv.getD36() == null ? 0d : gameLtv.getD36(),
				gameLtv.getD37() == null ? 0d : gameLtv.getD37(),
				gameLtv.getD38() == null ? 0d : gameLtv.getD38(),
				gameLtv.getD39() == null ? 0d : gameLtv.getD39(),
				gameLtv.getD40() == null ? 0d : gameLtv.getD40(),
				gameLtv.getD41() == null ? 0d : gameLtv.getD41(),
				gameLtv.getD42() == null ? 0d : gameLtv.getD42(),
				gameLtv.getD43() == null ? 0d : gameLtv.getD43(),
				gameLtv.getD44() == null ? 0d : gameLtv.getD44(),
				gameLtv.getD45() == null ? 0d : gameLtv.getD45(),
				gameLtv.getD46() == null ? 0d : gameLtv.getD46(),
				gameLtv.getD47() == null ? 0d : gameLtv.getD47(),
				gameLtv.getD48() == null ? 0d : gameLtv.getD48(),
				gameLtv.getD49() == null ? 0d : gameLtv.getD49(),
				gameLtv.getD50() == null ? 0d : gameLtv.getD50(),
				gameLtv.getD51() == null ? 0d : gameLtv.getD51(),
				gameLtv.getD52() == null ? 0d : gameLtv.getD52(),
				gameLtv.getD53() == null ? 0d : gameLtv.getD53(),
				gameLtv.getD54() == null ? 0d : gameLtv.getD54(),
				gameLtv.getD55() == null ? 0d : gameLtv.getD55(),
				gameLtv.getD56() == null ? 0d : gameLtv.getD56(),
				gameLtv.getD57() == null ? 0d : gameLtv.getD57(),
				gameLtv.getD58() == null ? 0d : gameLtv.getD58(),
				gameLtv.getD59() == null ? 0d : gameLtv.getD59(),
				gameLtv.getD60() == null ? 0d : gameLtv.getD60(),
				gameLtv.getD61() == null ? 0d : gameLtv.getD61(),
				gameLtv.getD62() == null ? 0d : gameLtv.getD62(),
				gameLtv.getD63() == null ? 0d : gameLtv.getD63(),
				gameLtv.getD64() == null ? 0d : gameLtv.getD64(),
				gameLtv.getD65() == null ? 0d : gameLtv.getD65(),
				gameLtv.getD66() == null ? 0d : gameLtv.getD66(),
				gameLtv.getD67() == null ? 0d : gameLtv.getD67(),
				gameLtv.getD68() == null ? 0d : gameLtv.getD68(),
				gameLtv.getD69() == null ? 0d : gameLtv.getD69(),
				gameLtv.getD70() == null ? 0d : gameLtv.getD70(),
				gameLtv.getD71() == null ? 0d : gameLtv.getD71(),
				gameLtv.getD72() == null ? 0d : gameLtv.getD72(),
				gameLtv.getD73() == null ? 0d : gameLtv.getD73(),
				gameLtv.getD74() == null ? 0d : gameLtv.getD74(),
				gameLtv.getD75() == null ? 0d : gameLtv.getD75(),
				gameLtv.getD76() == null ? 0d : gameLtv.getD76(),
				gameLtv.getD77() == null ? 0d : gameLtv.getD77(),
				gameLtv.getD78() == null ? 0d : gameLtv.getD78(),
				gameLtv.getD79() == null ? 0d : gameLtv.getD79(),
				gameLtv.getD80() == null ? 0d : gameLtv.getD80(),
				gameLtv.getD81() == null ? 0d : gameLtv.getD81(),
				gameLtv.getD82() == null ? 0d : gameLtv.getD82(),
				gameLtv.getD83() == null ? 0d : gameLtv.getD83(),
				gameLtv.getD84() == null ? 0d : gameLtv.getD84(),
				gameLtv.getD85() == null ? 0d : gameLtv.getD85(),
				gameLtv.getD86() == null ? 0d : gameLtv.getD86(),
				gameLtv.getD87() == null ? 0d : gameLtv.getD87(),
				gameLtv.getD88() == null ? 0d : gameLtv.getD88(),
				gameLtv.getD89() == null ? 0d : gameLtv.getD89(),
				gameLtv.getD90() == null ? 0d : gameLtv.getD90(),
		};
		gameLtv.setGameltvs(gameLtvs);
	}
}

