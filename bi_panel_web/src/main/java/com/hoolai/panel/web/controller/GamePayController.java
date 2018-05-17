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
import com.hoolai.bi.report.model.Types.GamePaymentAnalysisPage;
import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.FirstPaymentLevel;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.PaylevelAmountCnt;
import com.hoolai.bi.report.model.entity.PaylevelAmountCntClient;
import com.hoolai.bi.report.model.entity.PaylevelAmountCntSource;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.model.entity.TotalPaymentLevel;
import com.hoolai.bi.report.service.ClientDailyReportService;
import com.hoolai.bi.report.service.DailyReportService;
import com.hoolai.bi.report.service.FirstPaymentLevelService;
import com.hoolai.bi.report.service.PaylevelAmountCntClientService;
import com.hoolai.bi.report.service.PaylevelAmountCntService;
import com.hoolai.bi.report.service.PaylevelAmountCntSourceService;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.service.TotalPaymentLevelService;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.FirstPaymentLevelVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.PaylevelAmountCntClientVO;
import com.hoolai.bi.report.vo.PaylevelAmountCntSourceVO;
import com.hoolai.bi.report.vo.PaylevelAmountCntVO;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.bi.report.vo.TotalPaymentLevelVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;

@Controller
@RequestMapping("/panel_bi/gamePay")
public class GamePayController extends AbstractPanelController{
	
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private SourceDailyReportService sourceDailyReportService;
	@Autowired
	private ClientDailyReportService clientDailyReportService;
	@Autowired
	private TotalPaymentLevelService totalPaymentLevelService;
	@Autowired
	private FirstPaymentLevelService firstPaymentLevelService;
	@Autowired
	private PaylevelAmountCntService paylevelAmountCntService;
	@Autowired
	private PaylevelAmountCntClientService paylevelAmountCntClientService;
	@Autowired
	private PaylevelAmountCntSourceService paylevelAmountCntSourceService;
	
	@RequestMapping(value = {"/toGamePaymentAnalysis.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String toGamePaymentAnalysis(HttpServletRequest request,HttpSession session,
			@ModelAttribute("gamesVO") GamesVO gamesVO,@ModelAttribute("dailyReportVO") DailyReportVO dailyReportVO,
			BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		Games games = this.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		String view = request.getParameter("view");
		GamePaymentAnalysisPage page = GamePaymentAnalysisPage.convertToPage(view);
		
		switch(page){
		case NEW_PAY:
			return "games/payment/gameNewPay.jsp";
		case PAY_BEHAVIOR:
			return "games/payment/gamePayBehavior.jsp";
		case All_PAY:
			default:
				return "games/payment/gamePaymentView.jsp";
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
		
		String snid = games.getSnid();
		String gameid = games.getGameid();
		GamePaymentAnalysisPage page = GamePaymentAnalysisPage.convertToPage(view);
		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(indicators);
		
		SourceDailyReport sourceDailyReport = new SourceDailyReport(snid, gameid);
		SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setDate(beginDate, endDate);
		
		ClientDailyReport clientDailyReport = new ClientDailyReport(snid,gameid);
		ClientDailyReportVO clientDailyReportVO = new ClientDailyReportVO(clientDailyReport);
		clientDailyReportVO.setDate(beginDate, endDate);
		
		PaylevelAmountCntSource paylevelAmountCntSource = new PaylevelAmountCntSource(snid, gameid,null);
		PaylevelAmountCntSourceVO sourceVO = new PaylevelAmountCntSourceVO(paylevelAmountCntSource);
		sourceVO.setDate(beginDate, endDate);
		
		PaylevelAmountCntClient paylevelAmountCntClient = new PaylevelAmountCntClient(snid, gameid, null);
		PaylevelAmountCntClientVO clientVO = new PaylevelAmountCntClientVO(paylevelAmountCntClient);
		clientVO.setDate(beginDate, endDate);
		
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
		switch(channel){
		case SOURCE:
			if(page == GamePaymentAnalysisPage.PAY_BEHAVIOR){
				sourceVO.setSearchValue(StringUtils.isEmpty(searchValue) ? null : searchValue);
				sourceVO.setOffset(0l);
				sourceVO.setRows(paylevelAmountCntSourceService.selectCount(sourceVO));
			}else{
				sourceDailyReportVO.setSearchValue(StringUtils.isEmpty(searchValue) ? null : searchValue);
				sourceDailyReportVO.setOffset(0l);
				sourceDailyReportVO.setRows(sourceDailyReportService.selectCount(sourceDailyReportVO));
				List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectList(sourceDailyReportVO);
				processor.initSourceList(sourceDailyReports);
			}
			
			switch(page){
			case All_PAY:
				processor.writeSourceAllPay();
				break;
			case NEW_PAY:
				processor.writeSourceNewPay();
				break;
			case PAY_BEHAVIOR:
				processor.initSourcePayBehaviorList(paylevelAmountCntSourceService.selectPayList(sourceVO),paylevelAmountCntSourceService.selectUserList(sourceVO));
				processor.writeSourcePayBehavior();
				break;
			}
			break;
		case CLIENT:
			if(page == GamePaymentAnalysisPage.PAY_BEHAVIOR){
				clientVO.setSearchValue(StringUtils.isEmpty(searchValue) ? null : searchValue);
				clientVO.setOffset(0l);
				clientVO.setRows(paylevelAmountCntClientService.selectCount(clientVO));
			}else{
				clientDailyReportVO.setSearchValue(StringUtils.isEmpty(searchValue) ? null : searchValue);
				clientDailyReportVO.setOffset(0l);
				clientDailyReportVO.setRows(clientDailyReportService.selectCount(clientDailyReportVO));
				List<ClientDailyReport> clientDailyReports = clientDailyReportService.selectList(clientDailyReportVO);
				processor.initClientList(clientDailyReports);
			}
			
			switch(page){
			case All_PAY:
				processor.writeClientAllPay();
				break;
			case NEW_PAY:
				processor.writeClientNewPay();
				break;
			case PAY_BEHAVIOR:
				processor.initClientPayBehaviorList(paylevelAmountCntClientService.selectPayList(clientVO),paylevelAmountCntClientService.selectUserList(clientVO));
				processor.writeClientPayBehavior();
				break;
			}
			break;
			default:
				throw new RuntimeException();
		}
		return null;
	}
	
	
	@RequestMapping(value = {"/getGamePaymentAnalysis.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGamePaymentAnalysis(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = this.getSessionGames(request);
		
		String view = request.getParameter("view");
		String indicators = request.getParameter("indicators");
		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(indicators);
		GamePaymentAnalysisPage page = GamePaymentAnalysisPage.convertToPage(view);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String snid = games.getSnid();
		String gameid = games.getGameid();
		String source = request.getParameter("source");
		String clientid = request.getParameter("clientid");
       
		super.setSessionDate(request, beginDate, endDate);
		
		Map<String,Object> pageInfo = findPageInfo(request);
		
		switch(page){
		case NEW_PAY:
			return getPayPageNewPayData(ret, channel, beginDate, endDate, snid,
					gameid, source, clientid,pageInfo);
		case PAY_BEHAVIOR:
			String initTableType = request.getParameter("initTableType");
			pageInfo.put("initTableType", initTableType);
			return getPayPagePayBehaviorData(ret, channel, beginDate, endDate,
					snid, gameid, source, clientid,pageInfo);
		case All_PAY:
			default:
			return getPayPageAllPayData(ret, channel, beginDate, endDate, snid,
					gameid, source, clientid,pageInfo);
				
		}
	}

	private Map<String, Object> getPayPageAllPayData(Map<String, Object> ret,
			GameAnalysisChannel channel, String beginDate, String endDate,
			String snid, String gameid, String source, String clientid,Map<String,Object> pageInfo)
			throws Exception {
        
		switch(channel){
		case SOURCE:
			SourceDailyReport sourceDailyReport = new SourceDailyReport(snid, gameid, source);
			SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
			sourceDailyReportVO.setDate(beginDate, endDate);
			
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
				sourceDailyReportVO.setOrderCol(getOrderCol(channel, GamePaymentAnalysisPage.All_PAY, (String)pageInfo.get("orderCol")));
				sourceDailyReportVO.setOrderType((String) pageInfo.get("orderType"));
				List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectList(sourceDailyReportVO);
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", sourceDailyReports);
				return ret;
			}else{
				ret.put("scReports", sourceDailyReportService.getMatch(sourceDailyReportVO));
				return ret;
			}
		case CLIENT:
			ClientDailyReport clientDailyReport = new ClientDailyReport(snid,gameid,clientid);
			ClientDailyReportVO clientDailyReportVO = new ClientDailyReportVO(clientDailyReport);
			clientDailyReportVO.setDate(beginDate, endDate);
			
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
					clientDailyReportVO.setOrderCol(getOrderCol(channel, GamePaymentAnalysisPage.All_PAY, (String)pageInfo.get("orderCol")));
					clientDailyReportVO.setOrderType((String) pageInfo.get("orderType"));
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
		case ALL:
			default:
				TotalPaymentLevel totalPaymentLevel = new TotalPaymentLevel(snid,gameid);
				TotalPaymentLevelVO totalPaymentLevelVO = new TotalPaymentLevelVO(totalPaymentLevel);
				totalPaymentLevelVO.setDate(beginDate, endDate);
				
				DailyReport dailyReport = new DailyReport(snid,gameid);
				DailyReportVO dailyReportVO = new DailyReportVO(dailyReport);
				dailyReportVO.setDate(beginDate,endDate);
				
				ret.put("pc", totalPaymentLevelService.selectPaymentForPieListCount(totalPaymentLevelVO));
				ret.put("payList", totalPaymentLevelService.selectPaymentForPieList(totalPaymentLevelVO));
				ret.put("puc", totalPaymentLevelService.selectPayUserForPieListCount(totalPaymentLevelVO));
				ret.put("payUserList", totalPaymentLevelService.selectPayUserForPieList(totalPaymentLevelVO));
				ret.put("reports", dailyReportService.getMatch(dailyReportVO));
				
				return ret;
		}
	}

	private Map<String, Object> getPayPagePayBehaviorData(
			Map<String, Object> ret, GameAnalysisChannel channel,
			String beginDate, String endDate, String snid, String gameid,
			String source, String clientid,Map<String,Object> pageInfo) throws Exception {
		switch(channel){
		case SOURCE:
			PaylevelAmountCntSource paylevelAmountCntSource = new PaylevelAmountCntSource(snid, gameid, source);
			PaylevelAmountCntSourceVO sourceVO = new PaylevelAmountCntSourceVO(paylevelAmountCntSource);
			sourceVO.setDate(beginDate, endDate);
			
			if("-99".equals(source)){
				paylevelAmountCntSource.setSource(null);
				sourceVO.setEntity(paylevelAmountCntSource);
					
					//总记录数
					long recordsTotal = paylevelAmountCntSourceService.selectCount(sourceVO);
					// 过滤记录数
					sourceVO.setSearchValue((String) pageInfo.get("searchValue"));
					long recordsFiltered = paylevelAmountCntSourceService.selectCount(sourceVO);
					
					// 分页数据
					sourceVO.setOffset((Long) pageInfo.get("start"));
					sourceVO.setRows((Long) pageInfo.get("length"));
					sourceVO.setOrderCol(getOrderCol(channel, GamePaymentAnalysisPage.PAY_BEHAVIOR, (String)pageInfo.get("orderCol")));
					sourceVO.setOrderType((String) pageInfo.get("orderType"));
					
					List<PaylevelAmountCntSourceVO> datas = null;
					if("user".equals(pageInfo.get("initTableType"))){
						datas = paylevelAmountCntSourceService.selectUserList(sourceVO);
					}else{
						datas = paylevelAmountCntSourceService.selectPayList(sourceVO);
					}
					
					ret.put("recordsTotal", recordsTotal);
					ret.put("recordsFiltered", recordsFiltered);
					ret.put("data", datas);
					return ret;
			}else{
				ret.put("levPayUserReports", paylevelAmountCntSourceService.selectHorizontalUserList(sourceVO));
				ret.put("levPayPayReports", paylevelAmountCntSourceService.selectHorizontalPayList(sourceVO));
				ret.put("levPayView", paylevelAmountCntSourceService.selectHorizontalPayView(sourceVO));
				ret.put("levUserView", paylevelAmountCntSourceService.selectHorizontalUserView(sourceVO));
				ret.put("reports", paylevelAmountCntSourceService.getMatch(sourceVO));
				return ret;
			}
		case CLIENT:
			PaylevelAmountCntClient paylevelAmountCntClient = new PaylevelAmountCntClient(snid, gameid, clientid);
			PaylevelAmountCntClientVO clientVO = new PaylevelAmountCntClientVO(paylevelAmountCntClient);
			clientVO.setDate(beginDate, endDate);
			
			if("-99".equals(clientid)){
				paylevelAmountCntClient.setClientid(null);
				clientVO.setEntity(paylevelAmountCntClient);
					
					//总记录数
					long recordsTotal = paylevelAmountCntClientService.selectCount(clientVO);
					// 过滤记录数
					clientVO.setSearchValue((String) pageInfo.get("searchValue"));
					long recordsFiltered = paylevelAmountCntClientService.selectCount(clientVO);
					
					// 分页数据
					clientVO.setOffset((Long) pageInfo.get("start"));
					clientVO.setRows((Long) pageInfo.get("length"));
					clientVO.setOrderCol(getOrderCol(channel, GamePaymentAnalysisPage.PAY_BEHAVIOR, (String)pageInfo.get("orderCol")));
					clientVO.setOrderType((String) pageInfo.get("orderType"));
					
					List<PaylevelAmountCntClientVO> datas = null;
					if("user".equals(pageInfo.get("initTableType"))){
						datas = paylevelAmountCntClientService.selectUserList(clientVO);
					}else{
						datas = paylevelAmountCntClientService.selectPayList(clientVO);
					}
					
					ret.put("recordsTotal", recordsTotal);
					ret.put("recordsFiltered", recordsFiltered);
					ret.put("data", datas);
					return ret;
			}else{
				ret.put("levPayUserReports", paylevelAmountCntClientService.selectHorizontalUserList(clientVO));
				ret.put("levPayPayReports", paylevelAmountCntClientService.selectHorizontalPayList(clientVO));
				ret.put("levPayView", paylevelAmountCntClientService.selectHorizontalPayView(clientVO));
				ret.put("levUserView", paylevelAmountCntClientService.selectHorizontalUserView(clientVO));
				ret.put("reports", paylevelAmountCntClientService.getMatch(clientVO));
				return ret;
			}
		case ALL:
			default:
				PaylevelAmountCnt paylevelAmountCnt = new PaylevelAmountCnt(snid, gameid);
				PaylevelAmountCntVO paylevelAmountCntVO = new PaylevelAmountCntVO(paylevelAmountCnt);
				paylevelAmountCntVO.setDate(beginDate, endDate);
				
				ret.put("levPayUserReports", paylevelAmountCntService.selectHorizontalUserList(paylevelAmountCntVO));
				ret.put("levPayPayReports", paylevelAmountCntService.selectHorizontalPayList(paylevelAmountCntVO));
				ret.put("levPayView", paylevelAmountCntService.selectHorizontalPayView(paylevelAmountCntVO));
				ret.put("levUserView", paylevelAmountCntService.selectHorizontalUserView(paylevelAmountCntVO));
				ret.put("reports", paylevelAmountCntService.getMatch(paylevelAmountCntVO));
				return ret;
		}
	}

	private Map<String, Object> getPayPageNewPayData(Map<String, Object> ret,
			GameAnalysisChannel channel, String beginDate, String endDate,
			String snid, String gameid, String source, String clientid,Map<String,Object> pageInfo)
			throws Exception {
		switch(channel){
		case SOURCE:
			SourceDailyReport sourceDailyReport = new SourceDailyReport(snid, gameid, source);
			SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
			sourceDailyReportVO.setDate(beginDate, endDate);
			
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
				sourceDailyReportVO.setOrderCol(getOrderCol(channel, GamePaymentAnalysisPage.NEW_PAY, (String)pageInfo.get("orderCol")));
				sourceDailyReportVO.setOrderType((String) pageInfo.get("orderType"));
				List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectList(sourceDailyReportVO);
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", sourceDailyReports);
				return ret;
			}else{
				ret.put("scReports", sourceDailyReportService.getMatch(sourceDailyReportVO));
				return ret;
			}
		case CLIENT:
			ClientDailyReport clientDailyReport = new ClientDailyReport(snid,gameid);
			ClientDailyReportVO clientDailyReportVO = new ClientDailyReportVO(clientDailyReport);
			clientDailyReportVO.setDate(beginDate, endDate);
			
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
					clientDailyReportVO.setOrderCol(getOrderCol(channel, GamePaymentAnalysisPage.NEW_PAY, (String)pageInfo.get("orderCol")));
					clientDailyReportVO.setOrderType((String) pageInfo.get("orderType"));
					List<ClientDailyReport> clientDailyReports = clientDailyReportService.selectList(clientDailyReportVO);
					
					ret.put("recordsTotal", recordsTotal);
					ret.put("recordsFiltered", recordsFiltered);
					ret.put("data", clientDailyReports);
					return ret;
				}else{
					clientDailyReport.setClientid(clientid);
					List<ClientDailyReport> clientDailyReports = clientDailyReportService.getMatch(clientDailyReportVO);
					ret.put("scReports", clientDailyReports);
					return ret;
				}
		case ALL:
			default:
				FirstPaymentLevel firstPaymentLevel = new FirstPaymentLevel(snid,gameid);
				FirstPaymentLevelVO firstPaymentLevelVO = new FirstPaymentLevelVO(firstPaymentLevel);
				firstPaymentLevelVO.setDate(beginDate, endDate);
				
				DailyReport dailyReport = new DailyReport(snid,gameid);
				DailyReportVO dailyReportVO = new DailyReportVO(dailyReport);
				dailyReportVO.setDate(beginDate,endDate);
				
				ret.put("pc", firstPaymentLevelService.selectPaymentForPieListCount(firstPaymentLevelVO));
				ret.put("payList", firstPaymentLevelService.selectPaymentForPieList(firstPaymentLevelVO));
				ret.put("puc", firstPaymentLevelService.selectPayUserForPieListCount(firstPaymentLevelVO));
				ret.put("payUserList", firstPaymentLevelService.selectPayUserForPieList(firstPaymentLevelVO));
				ret.put("reports", dailyReportService.getMatch(dailyReportVO));
				
				return ret;
		}
	}
	
	private String getOrderCol(GameAnalysisChannel channel,GamePaymentAnalysisPage page,String orderCol) {
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				switch(page){
				case PAY_BEHAVIOR:
					return "stat_day";
					default:
						return "day";
				}
			case 1:
				return channel.value() == "source" ? null : "clientid+0";
			case 2:
				switch(page){
				case All_PAY:
					return "payment_amount";
				case NEW_PAY:
					return "new_pu";
				case PAY_BEHAVIOR:
					return "LP_0_10";
				}
			case 3:
				switch(page){
				case All_PAY:
					return "pu";
				case NEW_PAY:
					return "new_pay_amount";
				case PAY_BEHAVIOR:
					return "LP_10_50";
				}
			case 4:
				switch(page){
				case All_PAY:
					return "arpu";
				case NEW_PAY:
					return "new_pay_amount / new_pu";
				case PAY_BEHAVIOR:
					return "LP_50_100";
				}
			case 5:
				switch(page){
				case All_PAY:
					return "arppu";
				case NEW_PAY:
					return "install_pu";
				case PAY_BEHAVIOR:
					return "LP_100_200";
				}
			case 6:
				switch(page){
				case All_PAY:
					return "pay_rate";
				case NEW_PAY:
					return "install_pay_amount";
				case PAY_BEHAVIOR:
					return "LP_200_500";
				}
			case 7:
				switch(page){
				case All_PAY:
					return null;
				case NEW_PAY:
					return "install_pay_amount / install_pu";
				case PAY_BEHAVIOR:
					return "LP_500_1000";
				}
			case 8:
				switch(page){
				case All_PAY:
					return null;
				case NEW_PAY:
					return null;
				case PAY_BEHAVIOR:
					return "LP_1000_2000";
				}
			case 9:
				switch(page){
				case All_PAY:
					return null;
				case NEW_PAY:
					return null;
				case PAY_BEHAVIOR:
					return "LP_2000_up";
				}
			default:
				return null;
			}
		}
	}
}

