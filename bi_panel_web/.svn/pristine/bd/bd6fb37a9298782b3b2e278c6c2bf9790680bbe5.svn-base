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

import com.hoolai.bi.report.model.Types.GameAnalysisDimension;
import com.hoolai.bi.report.model.Types.GameViewPage;
import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.MonthReport;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.model.entity.UserRetentionLtv;
import com.hoolai.bi.report.model.entity.UserRetentionSourceLtv;
import com.hoolai.bi.report.model.entity.WeekReport;
import com.hoolai.bi.report.service.DailyReportService;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.MonthReportService;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.service.UserRetentionLtvService;
import com.hoolai.bi.report.service.UserRetentionSourceLtvService;
import com.hoolai.bi.report.service.WeekReportService;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.MonthReportVO;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.hoolai.bi.report.vo.WeekReportVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;

@Controller
@RequestMapping("/panel_bi/gameView")
public class GameViewController extends AbstractPanelController{
	
	@Autowired
	private GamesService gamesService;
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private MonthReportService monthReportService;
	@Autowired
	private WeekReportService weekReportService;
	@Autowired
	private SourceDailyReportService sourceDailyReportService;
	@Autowired
	private UserRetentionSourceLtvService userRetentionSourceLtvService;
	@Autowired
	private UserRetentionLtvService userRetentionLtvService;
	
	@RequestMapping(value = {"/toGameOverview.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		Games games = this.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		String view = request.getParameter("view");
		GameViewPage page = GameViewPage.convertToPage(view);
		switch(page){
		case SOURCES:
			return "games/view/gameSources.jsp";
		case OVERVIEW:
			default:
				return "games/view/gameView.jsp";
		}
	}
	
	@RequestMapping(value = {"/downloadData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		
		SourceDailyReport sourceDailyReport = new SourceDailyReport(games.getSnid(),games.getGameid());
		SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setDate(beginDate, endDate);
		
		//Map<String, Object> pageInfo = findPageInfo(request);
		
		List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.getMatch(sourceDailyReportVO);
		
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
		processor.initSourceList(sourceDailyReports);
		processor.writeViewSource();
		
		return null;
	}
	
	@RequestMapping(value = {"/getGameOverview.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameInfo(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String indicators = request.getParameter("dateDimension");
		String view = request.getParameter("view");
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String m_beginDate = request.getParameter("m_beginTime");
		String m_endDate = request.getParameter("m_endTime");
		String source = request.getParameter("source");
		String isPageRequest = request.getParameter("isPageRequest");
		
		GameViewPage page = GameViewPage.convertToPage(view);
		GameAnalysisDimension dimension = GameAnalysisDimension.convertToDimension(indicators);
		
		switch(page){
		case SOURCES:
			SourceDailyReport sourceDailyReport = new SourceDailyReport(games.getSnid(),games.getGameid());
			SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
			sourceDailyReportVO.setDate(beginDate, endDate);
			
			if("yes".equals(isPageRequest)){
				Map<String, Object> pageInfo = findPageInfo(request);
				Long allCount = sourceDailyReportService.selectCount(sourceDailyReportVO);
				sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
				long recordsFiltered = sourceDailyReportService.selectCount(sourceDailyReportVO);
				
				sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
				sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
				String orderCol = (String)pageInfo.get("orderCol");
				sourceDailyReportVO.setOrderCol(getOrderCol(orderCol));
				sourceDailyReportVO.setOrderType((String)pageInfo.get("orderType"));
				List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectList(sourceDailyReportVO);
				
				ret.put("recordsTotal", allCount);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", sourceDailyReports);
			}else{
				sourceDailyReportVO.getEntity().setSource(source);
				ret.putAll(getSourceData(sourceDailyReportVO));
			}
			break;
		case OVERVIEW:
			default:
				super.setSessionDate(request, beginDate, endDate);
				
				switch(dimension){
				case WEEK:
					ret.putAll(getViewWeekData(games, m_beginDate, m_endDate));
					break;
				case MONTH:
					ret.putAll(getViewMonthData(games, m_beginDate, m_endDate));
					break;
				case DAY:
					default:
						ret.putAll(getViewDayData(games, beginDate, endDate));
				}
		}
		
		ret.put("game", games);
		session.setAttribute("game", games);
		return ret;
	}
	
	private String getOrderCol(String orderCol) {
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "day";
			case 1:
				return null;
			case 2:
				return "dnu";
			case 3:
				return "role_choice";
			case 4:
				return "dau";
			case 5:
				return "payment_amount";
			case 6:
				return "pu";
			default:
				return null;
			}
		}
	}

	private Map<String,Object> getViewWeekData(Games games, String m_beginDate, String m_endDate) throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		WeekReport weekReport = new WeekReport(games.getSnid(),games.getGameid());
		
		WeekReportVO weekReportVO = new WeekReportVO(weekReport);
		weekReportVO.setDate(m_beginDate,m_endDate);
		
		List<WeekReport> weekReports = weekReportService.getMatch(weekReportVO);
		
		ret.put("reports", weekReports);
		
		return ret;
	}

	private Map<String,Object> getViewDayData(Games games,String beginDate, String endDate) throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		DailyReport dailyReport = new DailyReport(games.getSnid(),games.getGameid());
		
		DailyReportVO dailyReportVO = new DailyReportVO(dailyReport);
		dailyReportVO.setDate(beginDate, endDate);
		
		UserRetentionLtv ltv_daily = new UserRetentionLtv(games.getSnid(),games.getGameid());
		UserRetentionLtvVO ltvVO_daily = new UserRetentionLtvVO(ltv_daily);
		ltvVO_daily.setDate(beginDate, endDate);
		
		List<DailyReport> dailyReports = dailyReportService.getMatch(dailyReportVO);
		
		ret.put("reports", dailyReports);
		ret.put("installRetentions", userRetentionLtvService.selectAvgInstallRetentionDataList(ltvVO_daily));
		ret.put("installLtv", userRetentionLtvService.selectAvgInstallLTVDataList(ltvVO_daily));
		return ret;
	}

	private Map<String,Object> getViewMonthData(Games games,String beginDate, String endDate) throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		MonthReport monthReport = new MonthReport(games.getSnid(),games.getGameid());
		
		MonthReportVO monthReportVO = new MonthReportVO(monthReport);
		monthReportVO.setDate(beginDate,endDate);
		
		List<MonthReport> monthReports = monthReportService.getMatch(monthReportVO);
		
		ret.put("reports", monthReports);
		
		return ret;
	}

	private Map<String,Object> getSourceData(SourceDailyReportVO sourceDailyReportVO) throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		
		
		List<SourceDailyReportVO> siDailyReports = sourceDailyReportService.selectInstallForPieList(sourceDailyReportVO);
		List<SourceDailyReportVO> sdDailyReports = sourceDailyReportService.selectDauForPieList(sourceDailyReportVO);
		List<SourceDailyReportVO> spDailyReports = sourceDailyReportService.selectPaymentForPieList(sourceDailyReportVO);
		
		UserRetentionSourceLtv ltv = new UserRetentionSourceLtv(sourceDailyReportVO.getEntity().getSnid(),
				sourceDailyReportVO.getEntity().getGameid(),sourceDailyReportVO.getEntity().getSource());
		UserRetentionSourceLtvVO ltvVO = new UserRetentionSourceLtvVO(ltv);
		ltvVO.setDate(sourceDailyReportVO.getBeginDate(), sourceDailyReportVO.getEndDate());
		List<UserRetentionSourceLtvVO> ltvs = userRetentionSourceLtvService.selectPaymentForBarList(ltvVO);
		
		ret.put("ltvs", ltvs);
		ret.put("sireports", siDailyReports);
		ret.put("sireportsCount", sourceDailyReportService.selectInstallForPieListCount(sourceDailyReportVO));
		ret.put("sdreports", sdDailyReports);
		ret.put("sdreportsCount", sourceDailyReportService.selectDauForPieListCount(sourceDailyReportVO));
		ret.put("spReports", spDailyReports);
		ret.put("spreportsCount", sourceDailyReportService.selectPaymentForPieListCount(sourceDailyReportVO));
		
		return ret;
	}
}

