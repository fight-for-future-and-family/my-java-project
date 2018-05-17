package com.hoolai.panel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.Types.GameAnalysisChannel;
import com.hoolai.bi.report.model.Types.GameAnalysisDimension;
import com.hoolai.bi.report.model.Types.GameToolPage;
import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.MonthReport;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.model.entity.UserRetentionClientLtv;
import com.hoolai.bi.report.model.entity.UserRetentionLtv;
import com.hoolai.bi.report.model.entity.UserRetentionSourceLtv;
import com.hoolai.bi.report.model.entity.WeekReport;
import com.hoolai.bi.report.service.ClientDailyReportService;
import com.hoolai.bi.report.service.DailyReportService;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.MonthReportService;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.service.UserRetentionClientLtvService;
import com.hoolai.bi.report.service.UserRetentionLtvService;
import com.hoolai.bi.report.service.UserRetentionSourceLtvService;
import com.hoolai.bi.report.service.WeekReportService;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.MonthReportVO;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.bi.report.vo.UserRetentionClientLtvVO;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.hoolai.bi.report.vo.WeekReportVO;
import com.hoolai.manage.exception.ApplyException;
import com.hoolai.manage.model.Authorities;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.AuthoritiesService;

@Controller
@RequestMapping("/panel_bi/analysisTool")
public class GameToolsController extends AbstractPanelController{
	
	@Autowired
	private GamesService gamesService; 
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private SourceDailyReportService sourceDailyReportService;
	@Autowired
	private ClientDailyReportService clientDailyReportService;
	@Autowired
	private MonthReportService monthReportService;
	@Autowired
	private WeekReportService weekReportService;
	@Autowired
	private UserRetentionLtvService userRetentionLtvService;
	@Autowired
	private UserRetentionSourceLtvService userRetentionSourceLtvService;
	@Autowired
	private UserRetentionClientLtvService userRetentionClientLtvService;
	
	@RequestMapping(value = {"/toGameAnalysisTool.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,Model model)throws Exception {

		Users users = getSessionUsers(request);
		if(users == null){
			return "index.jsp";
		}
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		String view = request.getParameter("view");
		GameToolPage page = GameToolPage.convertToPage(view);
		if(page.ordinal()<2){
			List<Games> games = gamesService.selectGamesByUserId(users.getId());
			List<Authorities> authorities = authoritiesService.getAllAuthorities();//authoritiesService.getAuthoritiesByUserId(users.getId());
			
			model.addAttribute("gameList", games);
			model.addAttribute("authorities", authorities);
		}
		
		switch(page){
		case PREDICTION:
			return "kpiPredict/kpiPredict.jsp";
		case MULTI_INDICATORS:
			return "games/tool/gameMultiIndicatorsContrast.jsp";
		case TREND:
			default:
				return "games/tool/gameTrendContrast.jsp";
		}
	}
	
	@RequestMapping(value = {"/getGameContrastData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameContrastData(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String gameAId = request.getParameter("gameAId");
		String gameBId = request.getParameter("gameBId");
		String dateDimension = request.getParameter("dateDimension");
		String channelStr = request.getParameter("channel");
		String view = request.getParameter("view");
		String beginDateA = request.getParameter("beginTimeA");
		String endDateA = request.getParameter("endTimeA");
		String beginDateB = request.getParameter("beginTimeB");
		String endDateB = request.getParameter("endTimeB");
		String m_beginDateA = request.getParameter("m_beginTimeA");
		String m_endDateA = request.getParameter("m_endTimeA");
		String m_beginDateB = request.getParameter("m_beginTimeB");
		String m_endDateB = request.getParameter("m_endTimeB");
		String source = request.getParameter("source");
		String clientid = request.getParameter("clientid");
		
		GameToolPage page = GameToolPage.convertToPage(view);
		GameAnalysisDimension dimension = GameAnalysisDimension.convertToDimension(dateDimension);
		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(channelStr);
		
		Games gameA = null;
		Games gameB = null;
		
		if(StringUtils.isEmpty(gameAId) || (!StringUtils.isEmpty(gameAId) && (gameA = this.getSessionGames(request, Long.valueOf(gameAId)))  == null)){
			throw new ApplyException("auth is error!");
		}
		if(!channel.value().equals(GameAnalysisChannel.ALL.value())){
			gameB = gameA;
		}else if(!StringUtils.isEmpty(gameBId) &&  (gameB = this.getSessionGames(request, Long.valueOf(gameBId))) == null){
			throw new ApplyException("auth is error!");
		}
		
		switch(page){
		case MULTI_INDICATORS:
			break;
		case TREND:
			default:
				switch(dimension){
				case WEEK:
					if(gameA != null){
						WeekReport weekReportA = new WeekReport(gameA.getSnid(), gameA.getGameid());
						WeekReportVO weekReportVOA = new WeekReportVO(weekReportA);
						weekReportVOA.setDate(m_beginDateB, m_endDateB);
						
						ret.put("gameAReports", weekReportService.getMatch(weekReportVOA));
					}
					
					if(gameB != null){
						WeekReport weekReportB = new WeekReport(gameB.getSnid(), gameB.getGameid());
						WeekReportVO weekReportVOB = new WeekReportVO(weekReportB);
						weekReportVOB.setDate(m_beginDateB, m_endDateB);
						
						ret.put("gameBReports", weekReportService.getMatch(weekReportVOB));
					}
					break;
				case MONTH:
					if(gameA != null){
						MonthReport monthReportA = new MonthReport(gameA.getSnid(), gameA.getGameid());
						MonthReportVO monthReportVOA = new MonthReportVO(monthReportA);
						monthReportVOA.setDate(m_beginDateA, m_endDateA);
						
						ret.put("gameAReports", monthReportService.getMatch(monthReportVOA));
					}
					
					if(gameB != null){
						MonthReport monthReportB = new MonthReport(gameB.getSnid(), gameB.getGameid());
						MonthReportVO monthReportVOB = new MonthReportVO(monthReportB);
						monthReportVOB.setDate(m_beginDateB, m_endDateB);
						
						ret.put("gameBReports", monthReportService.getMatch(monthReportVOB));
					}
					break;
				case DAY:
					default:
					switch(channel){
					case SOURCE:
						if(gameA != null){
							SourceDailyReport sourceDailyReportA = new SourceDailyReport(gameA.getSnid(), gameA.getGameid(),source);
							SourceDailyReportVO sourceDailyReportVOA = new SourceDailyReportVO(sourceDailyReportA);
							sourceDailyReportVOA.setDate(beginDateA, endDateA);
							
							UserRetentionSourceLtv sourceLtvA = new UserRetentionSourceLtv(gameA.getSnid(), gameA.getGameid(),source);
							UserRetentionSourceLtvVO sourceLtvVOA = new UserRetentionSourceLtvVO(sourceLtvA);
							sourceLtvVOA.setDate(beginDateA, endDateA);
							
							ret.put("gameAReports", sourceDailyReportService.getMatch(sourceDailyReportVOA));
							ret.put("ltvsA",userRetentionSourceLtvService.selectHorizontal4InstallRetentionRateList(sourceLtvVOA));
						}
						
						if(gameB != null){
							SourceDailyReport sourceDailyReportB = new SourceDailyReport(gameB.getSnid(), gameB.getGameid(),source);
							SourceDailyReportVO sourceDailyReportVOB = new SourceDailyReportVO(sourceDailyReportB);
							sourceDailyReportVOB.setDate(beginDateB, endDateB);
							
							UserRetentionSourceLtv ltvB = new UserRetentionSourceLtv(gameB.getSnid(), gameB.getGameid(),source);
							UserRetentionSourceLtvVO ltvVOB = new UserRetentionSourceLtvVO(ltvB);
							ltvVOB.setDate(beginDateB, endDateB);
							
							ret.put("gameBReports", sourceDailyReportService.getMatch(sourceDailyReportVOB));
							ret.put("ltvsB",userRetentionSourceLtvService.selectHorizontal4InstallRetentionRateList(ltvVOB));
						}
						break;
					case CLIENT:
						if(gameA != null){
							ClientDailyReport dailyReportA = new ClientDailyReport(gameA.getSnid(), gameA.getGameid(),clientid);
							ClientDailyReportVO dailyReportVOA = new ClientDailyReportVO(dailyReportA);
							dailyReportVOA.setDate(beginDateA, endDateA);
							
							UserRetentionClientLtv ltvA = new UserRetentionClientLtv(gameA.getSnid(), gameA.getGameid(),clientid);
							UserRetentionClientLtvVO ltvVOA = new UserRetentionClientLtvVO(ltvA);
							ltvVOA.setDate(beginDateB, endDateB);
							
							ret.put("gameAReports", clientDailyReportService.getMatch(dailyReportVOA));
							ret.put("ltvsA",userRetentionClientLtvService.selectHorizontal4RateList(ltvVOA));
						}
						
						if(gameB != null){
							ClientDailyReport dailyReportB = new ClientDailyReport(gameB.getSnid(), gameB.getGameid(),clientid);
							ClientDailyReportVO dailyReportVOB = new ClientDailyReportVO(dailyReportB);
							dailyReportVOB.setDate(beginDateB, endDateB);
							
							UserRetentionClientLtv ltvB = new UserRetentionClientLtv(gameB.getSnid(), gameB.getGameid(),clientid);
							UserRetentionClientLtvVO ltvVOB = new UserRetentionClientLtvVO(ltvB);
							ltvVOB.setDate(beginDateB, endDateB);
							
							ret.put("gameBReports", clientDailyReportService.getMatch(dailyReportVOB));
							ret.put("ltvsB",userRetentionClientLtvService.selectHorizontal4RateList(ltvVOB));
						}
						break;
					case ALL:
						default:
							if(gameA != null){
								DailyReport dailyReportA = new DailyReport(gameA.getSnid(), gameA.getGameid());
								DailyReportVO dailyReportVOA = new DailyReportVO(dailyReportA);
								dailyReportVOA.setDate(beginDateA, endDateA);
								
								UserRetentionLtv ltvA = new UserRetentionLtv(gameA.getSnid(), gameA.getGameid());
								UserRetentionLtvVO ltvVOA = new UserRetentionLtvVO(ltvA);
								ltvVOA.setDate(beginDateA, endDateA);
								
								ret.put("gameAReports", dailyReportService.getMatch(dailyReportVOA));
								ret.put("ltvsA",userRetentionLtvService.selectHorizontal4InstallRetentionRateList(ltvVOA));
							}
							
							if(gameB != null){
								DailyReport dailyReportB = new DailyReport(gameB.getSnid(), gameB.getGameid());
								DailyReportVO dailyReportVOB = new DailyReportVO(dailyReportB);
								dailyReportVOB.setDate(beginDateB, endDateB);
								
								UserRetentionLtv ltvB = new UserRetentionLtv(gameB.getSnid(), gameB.getGameid());
								UserRetentionLtvVO ltvVOB = new UserRetentionLtvVO(ltvB);
								ltvVOB.setDate(beginDateB, endDateB);
								
								ret.put("gameBReports", dailyReportService.getMatch(dailyReportVOB));
								ret.put("ltvsB",userRetentionLtvService.selectHorizontal4InstallRetentionRateList(ltvVOB));
							}
					}
				}
		}
		
		return ret;
	}
	
}

