package com.hoolai.panel.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.entity.AnalysisGDT;
import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.model.entity.SummaryGDT;
import com.hoolai.bi.report.model.entity.UserRetentionLtv;
import com.hoolai.bi.report.service.AnalysisGDTService;
import com.hoolai.bi.report.service.ClientDailyReportService;
import com.hoolai.bi.report.service.DailyReportService;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.service.SummaryGDTService;
import com.hoolai.bi.report.service.UserRetentionLtvService;
import com.hoolai.bi.report.vo.AnalysisGDTVO;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.hoolai.bi.report.vo.DailyReportVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.bi.report.vo.SummaryGDTVO;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadProcessor;
import com.hoolai.panel.web.download.DownLoadProcessor.WorkbookType;

@Controller
@RequestMapping("/panel_bi/tool")
public class GameDownLoadController extends AbstractPanelController{
	
	private static final Logger logger=Logger.getLogger("exception");
	
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private SourceDailyReportService sourceDailyReportService;
	@Autowired
	private ClientDailyReportService clientDailyReportService;
	@Autowired
	private UserRetentionLtvService userRetentionLtvService;
	@Autowired
	private SummaryGDTService summaryGDTService;
	@Autowired
	private AnalysisGDTService analysisGDTService;
	
	@RequestMapping(value = {"/toGameTool.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		model.addAttribute("game", games);
		
		if("testServer".equals(request.getParameter("view"))){
			return "download/testServer.jsp";
		}else{
			return "download/dailyReportDownLoad.jsp";
		}
	}

	
	@RequestMapping(value = {"/download.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String getGameInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		Games games = this.getSessionGames(request);
		if(games==null) {
			return null;
		}
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String workbookType = "xlsx";
		
		/* 准备总览的数据 */
		DailyReport dailyReport = new DailyReport(games.getSnid(), games.getGameid());
		DailyReportVO dailyReportVO = new DailyReportVO(dailyReport);
		dailyReportVO.setDate(beginDate, endDate);
		List<DailyReport> dailyReports = dailyReportService.selectDailyDownloadAllList(dailyReportVO);
		
		/* 准备分渠道的数据 */
		SourceDailyReport sourceDailyReport = new SourceDailyReport(games.getSnid(), games.getGameid());
		SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setDate(beginDate, endDate);
		List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.getMatch(sourceDailyReportVO);
		
		/* 准备分服的数据 */
		ClientDailyReport clientDailyReport = new ClientDailyReport(games.getSnid(), games.getGameid());
		ClientDailyReportVO clientDailyReportVO = new ClientDailyReportVO(clientDailyReport);
		clientDailyReportVO.setDate(beginDate, endDate);
		List<ClientDailyReport> clientDailyReports = clientDailyReportService.getMatch(clientDailyReportVO);
//		clientDailyReportVO.setBeginDate(null);
		List<ClientDailyReportVO> clientAllInstalls = clientDailyReportService.selectInstallForDownLoad(clientDailyReportVO);
		
		/* 准备LTV和留存的数据 */
		UserRetentionLtv userRetention = new UserRetentionLtv(games.getSnid(), games.getGameid());
		UserRetentionLtvVO userRetentionLtvVO = new UserRetentionLtvVO(userRetention);
		userRetentionLtvVO.setDate(beginDate, endDate);
		List<UserRetentionLtvVO> userRetentions_install = userRetentionLtvService.selectHorizontal4InstallRetentionRateList(userRetentionLtvVO);
		List<UserRetentionLtvVO> userLtvs_install = userRetentionLtvService.selectHorizontal4InstallPayList(userRetentionLtvVO);
		List<UserRetentionLtvVO> userRetentions_role = userRetentionLtvService.selectHorizontal4RoleRetentionRateList(userRetentionLtvVO);
		List<UserRetentionLtvVO> userLtvs_role = userRetentionLtvService.selectHorizontal4RolePayList(userRetentionLtvVO);
		List<UserRetentionLtvVO> userRetentionsLtvInstall = userRetentionLtvService.selectClientUserRetentionLtv(userRetentionLtvVO);
		List<UserRetentionLtvVO> userRetentionsLtvInstallUserCount = userRetentionLtvService.selectClientUserRetentionLtvInstallUserCount(userRetentionLtvVO);
		
		
		/* 准备广点通的数据 */
		SummaryGDT summaryGDT = new SummaryGDT(games.getSnid(), games.getGameid());
		SummaryGDTVO summaryGDTVO = new SummaryGDTVO(summaryGDT);
		summaryGDTVO.setDate(beginDate, endDate);
		List<SummaryGDT> summaryGDTs = summaryGDTService.getMatch(summaryGDTVO);
		
		AnalysisGDT analysisGDT = new AnalysisGDT(games.getSnid(), games.getGameid());
		AnalysisGDTVO analysisGDTVO = new AnalysisGDTVO(analysisGDT);
		analysisGDTVO.setDate(beginDate, endDate);
		List<AnalysisGDT> analysisGDTs = analysisGDTService.getMatch(analysisGDTVO);
		
		try{
			/* 读取模板生成Excel推送至浏览器 */
			DownLoadProcessor processor = new DownLoadProcessor(WorkbookType.convertToWorkbookType(workbookType));
			processor.initData(games, dailyReports, sourceDailyReports, 
					clientDailyReports,clientAllInstalls,userRetentions_install, 
					userLtvs_install,userRetentions_role, 
					userLtvs_role,userRetentionsLtvInstall,userRetentionsLtvInstallUserCount,summaryGDTs,analysisGDTs);
			processor.readyDownload();
			processor.writeBook(response);
		}catch(Exception e){
			logger.info("下载出错信息： snid="+games.getSnid() +", gameid="+ games.getGameid()+", gameName=" + games.getName()
					+", beginDate="+ beginDate +", endDate="+ endDate);
			e.printStackTrace();
			throw new Exception(e);
		}
		
		session.setAttribute("isLoadEnd", "1");
		
		return null;
	}
	
	@RequestMapping(value = {"/getEndMark.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getEndMark(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		Map<String,Object> ret = new HashMap<String, Object>();
		
		if(session.getAttribute("isLoadEnd") != null
				&& session.getAttribute("isLoadEnd").equals("1")){
			ret.put("isLoadEnd", "1");
			session.removeAttribute("isLoadEnd");
		}else{
			ret.put("isLoadEnd", "0");
		}
		return ret;
	}
	
	
	
	@RequestMapping(value = {"/getTestData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> testTable (HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		@SuppressWarnings("unused")
		class Person{
			
			public Person(String name,int age,String email,String tel){
				this.age = age;
				this.name = name;
				this.email = email;
				this.telepone = tel;
			}
			private String name;
			private int age;
			private String email;
			private String telepone;
			
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public int getAge() {
				return age;
			}
			public void setAge(int age) {
				this.age = age;
			}
			public String getEmail() {
				return email;
			}
			public void setEmail(String email) {
				this.email = email;
			}
			public String getTelepone() {
				return telepone;
			}
			public void setTelepone(String telepone) {
				this.telepone = telepone;
			}
			
		}
		
		//数据起始位置
	    String start = request.getParameter("start");
	    //数据长度
	    String length = request.getParameter("length");
	 
	    int temp = Integer.valueOf(start) % Integer.valueOf(length);
	    int pageTemp = Integer.valueOf(start) / Integer.valueOf(length);
		int page = temp == 0?pageTemp+1:pageTemp+2;
		
		Map<String,Object> ret = new HashMap<String, Object>();
		List<Person> data = new ArrayList<Person>();
		switch(page){
		case 1:
			data.add(new Person("张三", 25, "zhangsan@email.com", "15845124758"));
			data.add(new Person("李四", 24, "lisi@email.com", "18623145214"));
			data.add(new Person("王兰", 30, "wanglan@email.com", "13812345879"));
			data.add(new Person("曾晗", 52, "zenghan@email.com", "13562147851"));
			data.add(new Person("张锡祥", 42, "xiyang@email.com", "13562147852"));
			data.add(new Person("王东秋", 52, "dongqiu@email.com", "13812579635"));
			data.add(new Person("程琳",32, "chenglin@email.com", "13712584123"));
			data.add(new Person("张海英", 32, "haiying@email.com", "13325412369"));
			data.add(new Person("李媛", 24, "yuan@email.com", "13352144521"));
			data.add(new Person("李开平", 29, "kaip@email.com", "13814521247"));
			break;
		case 2:
			data.add(new Person("刘璃", 12, "liuli@email.com", "13523125455"));
			data.add(new Person("李雨姗", 2, "anan@email.com", "13521242563"));
			data.add(new Person("李子园", 22, "ziyuan@email.com", "13823212587"));
			data.add(new Person("莫琼", 32, "qiong@email.com", "13707732568"));
			data.add(new Person("李比希", 27, "bibi@email.com", "13702310002"));
			data.add(new Person("杨柳", 26, "liuliu@email.com", "13820010241"));
			data.add(new Person("卢国强", 19, "luguoqiang@email.com", "13820366656"));
			data.add(new Person("春暖", 18, "chuannuan@email.com", "13852523645"));
			data.add(new Person("陈辉", 23, "chenhui@email.com", "13845213658"));
			data.add(new Person("邓侃泰", 21, "taier@email.com", "13823563256"));
			break;
		case 3:
			data.add(new Person("沈竞康", 33, "jingkang@email.com", "18623545652"));
			data.add(new Person("马凡尔", 35, "faner@email.com", "18852145214"));
			data.add(new Person("廖永杰", 24, "yongjie@email.com", "18852366254"));
			data.add(new Person("林佳敏", 22, "min@email.com", "13522001478"));
			data.add(new Person("熊黛林", 28, "dailin@email.com", "13522001201"));
			data.add(new Person("冯亚楠", 20, "yanan@email.com", "1358882020"));
			data.add(new Person("李芸", 23, "yunyun@email.com", "13852102566"));
			data.add(new Person("黄斌新", 29, "binxin@email.com", "13522462654"));
			data.add(new Person("文宁", 30, "wening@email.com", "13522485212"));
			data.add(new Person("谭昊林", 72, "hao@email.com", "13810466656"));
			break;
		}
		
		ret.put("recordsTotal", 30);
		ret.put("recordsFiltered", 30);
		ret.put("data", data);
		
		return ret;
	}
}

