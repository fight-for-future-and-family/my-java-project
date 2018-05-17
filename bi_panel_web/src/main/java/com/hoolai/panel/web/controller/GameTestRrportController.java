package com.hoolai.panel.web.controller;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.hoolai.bi.report.model.Types.GameTestReport;
import com.hoolai.bi.report.model.Types.GameViewPage;
import com.hoolai.bi.report.model.Types.GameViewTestReport;
import com.hoolai.bi.report.model.Types.GameViewTestReportPage;
import com.hoolai.bi.report.model.entity.DailyReport;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.MonthReport;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.model.entity.TestReport;
import com.hoolai.bi.report.model.entity.TestReportConversion;
import com.hoolai.bi.report.model.entity.TestReportGoldObtain;
import com.hoolai.bi.report.model.entity.TestReportLeaveSave;
import com.hoolai.bi.report.model.entity.TestReportLevel;
import com.hoolai.bi.report.model.entity.TestReportSbwd;
import com.hoolai.bi.report.model.entity.TestReportSource;
import com.hoolai.bi.report.model.entity.TestReportZtZsb;
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
import com.hoolai.bi.report.vo.RealTimeGameVo;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.bi.report.vo.TestReportLeaveSaveVo;
import com.hoolai.bi.report.vo.TestReportSbwdVo;
import com.hoolai.bi.report.vo.TestReportVo;
import com.hoolai.bi.report.vo.TestReportZtZsbVo;
import com.hoolai.bi.report.vo.UserRetentionLtvVO;
import com.hoolai.bi.report.vo.UserRetentionSourceLtvVO;
import com.hoolai.bi.report.vo.WeekReportVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.controller.AbstractPanelController;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;
import com.hoolai.panel.web.job.SeriesGamesJob;
import com.hoolai.panel.web.testReport.PrestoTestReport;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/panel_bi/gameTestReport")
public class GameTestRrportController extends AbstractPanelController {
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
	
	@Autowired
	private SeriesGamesJob seriesGamesJob;
	@Autowired
	private PrestoTestReport prestoTestReport;
	
	@Autowired
	@Qualifier("sourceCpaConfiguration")
	private Configuration sourceCpaConfiguration;
	
	@RequestMapping(value = {"/toGameTestReport.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		String view = request.getParameter("view");
		GameViewTestReport page = GameViewTestReport.convertToPage(view);
		String jsp = "";
		switch(page){
			//用户维度
			case YHWD:
				jsp = "games/testReport/gameUserDimensions2.jsp";
				break;
			//设备维度
			case SBWD:
				jsp = "games/testReport/gameEquipmentDimension.jsp";
				break;
			
			default:
				return "games/testReport/gameUserDimensions.jsp";
		}
		return jsp;
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
		
		
		
		List<Games> gameList = (List<Games>) super.getSession(request, "games");
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String indicators = request.getParameter("indicators");
		String view = request.getParameter("view");
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String source = request.getParameter("source");
		String isPageRequest = request.getParameter("isPageRequest");
		
		TestReportVo testReportVo = new TestReportVo(games.getSnid(),games.getGameid(),beginDate,endDate);
		
		TestReportLeaveSaveVo testReportLeaveSaveVo = new TestReportLeaveSaveVo(games.getSnid(),games.getGameid(),beginDate,endDate);
		
		TestReportZtZsbVo testReportZtZsbVo = new TestReportZtZsbVo(games.getSnid(),games.getGameid(),beginDate,endDate);
		
		TestReportSbwdVo testReportSbwdVo = new TestReportSbwdVo(games.getSnid(),games.getGameid(),beginDate,endDate);
		
		GameViewTestReportPage page = GameViewTestReportPage.convertToPage(view);
		GameTestReport dimension = GameTestReport.convertToDimension(indicators);
		List<TestReport> overallResult = null;
		List<TestReportLeaveSave> saveAllList = null;
		 List<TestReportSbwd> sbwdList =  null;
		 List<TestReportZtZsb> ztZsbList =  null;
		switch(page){
		case YHWDVIEW:
			
			break;
		case SBWDVIEW:
			switch(dimension){
			
			default:
				sbwdList = getSbwdList(testReportSbwdVo,"testReportSbwd");
				ret.put("reports", sbwdList);
			}
			break;
		case ALLVIEW:
			default:
				//super.setSessionDate(request, beginDate, endDate);
				switch(dimension){
				case ZSB:
					ztZsbList = getZtZsbList(testReportZtZsbVo,"TestReportZtZsb");
					ret.put("reports4", ztZsbList);
					break;
				case LEAVE:
					saveAllList = getSaveAllList(testReportLeaveSaveVo,"testReportLeaveSave");
					ret.put("reports2", saveAllList);
					break;
				case ALL:
					default:
						overallResult = getOverallList(testReportVo,"testReportAll");
						
						ret.put("reports", overallResult);
					
				}
		}
		
		
		//session.setAttribute("game", games);
		return ret;
	}
	
	//总览
	private String getTestReportAllSql(TestReportVo testReportVo,String systemType){
		Configuration configuration = new Configuration();
		Template template= null;
		StringWriter sw = new StringWriter();
		try {
			template = sourceCpaConfiguration.getTemplate(systemType+".ftl");
			template.process(testReportVo, sw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("get sql template error!"+e.getMessage()+" path:"+"templates/ios.ftl");
		}
		return sw.toString();
	}
	//总体(主要为留存数据)
	private String getTestReportLeaveSaveSql(TestReportLeaveSaveVo testReportLeaveSaveVo,String systemType){
		Configuration configuration = new Configuration();
		Template template= null;
		StringWriter sw = new StringWriter();
		try {
			template = sourceCpaConfiguration.getTemplate(systemType+".ftl");
			template.process(testReportLeaveSaveVo, sw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("get sql template error!"+e.getMessage()+" path:"+"templates/ios.ftl");
		}
		return sw.toString();
	}
	
	//总体(主收比数据)
		private String getTestReportZtZsbSql(TestReportZtZsbVo testReportZtZsbVo,String systemType){
			Configuration configuration = new Configuration();
			Template template= null;
			StringWriter sw = new StringWriter();
			try {
				template = sourceCpaConfiguration.getTemplate(systemType+".ftl");
				template.process(testReportZtZsbVo, sw);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("get sql template error!"+e.getMessage()+" path:"+"templates/ios.ftl");
			}
			return sw.toString();
		}
		
		//设备维度
		private String getTestReportSbwdSql(TestReportSbwdVo testReportSbwdVo,String systemType){
			Configuration configuration = new Configuration();
			Template template= null;
			StringWriter sw = new StringWriter();
			try {
				template = sourceCpaConfiguration.getTemplate(systemType+".ftl");
				template.process(testReportSbwdVo, sw);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("get sql template error!"+e.getMessage()+" path:"+"templates/ios.ftl");
			}
			return sw.toString();
		}
	/**
	 * 总体(主要为基础数据)
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private List<TestReport> getOverallList(TestReportVo testReportVo,String systemType){
		/*
		String sql=" select t1.ds,t1.dau,t1.dnu,t1.install,t1.role_choice, "+
					"	cast(t1.payment_amount as double)/cast(t3.rate as double) as payment_amount,payer, "+
					"	concat(cast(round(pay_rate*100,2) as varchar),'%') as pay_rate, "+
					"	round(cast(t1.arpu as double)/cast(t3.rate as double),2) as arpu, "+
					"	round(cast(t1.arppu as double)/cast(t3.rate as double),2) as arppu, "+
					"	t2.new_equip,t2.install as install2, "+
					"	concat(cast(round(cast(t2.install as double)/cast(t2.new_equip as double)*100,2) as varchar),'%') as jihuo_xinzeng_rate,  "+
					"	concat(cast(round(cast(t1.role_choice as double)/cast(t1.dnu as double)*100,2) as varchar),'%') as install_role_rate  "+
					"	from "+
					"	(select ds,dau,dnu,install,role_choice,payment_amount,payer,pay_rate,arpu,arppu,snid,gameid "+
					"	from facts.f_game_day where 1=1 ";
						sql+=this.getSql(snid, gameid, startdate, enddate);
					sql += " )t1 left outer join  "+
					"	(select ds,new_equip,install "+
					"	from facts.f_equip_day where 1=1 ";
						sql+=this.getSql(snid, gameid, startdate, enddate);
					sql += "	)t2 on t1.ds=t2.ds "+
					"	left outer join  "+
					"	(select rate,snid,gameid "+
					"	from dimension.all_game_name)t3 "+
					"	on t1.snid=t3.snid and t1.gameid=t3.gameid "+
					"	order by t1.ds ";
                */
		
		 String sql =  this.getTestReportAllSql(testReportVo,systemType);
         List<TestReport> OverallResultList = prestoTestReport.TestReportGamesData(sql);
		return OverallResultList;
	}
	
	
	
	/**
	 * 总体(主要为留存数据)
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private List<TestReportLeaveSave> getSaveAllList(TestReportLeaveSaveVo testReportLeaveSaveVo,String systemType){
		/*
		String sql ="select install_date,install, "+               
					" cast(round(sum(case when retention_day=1 then retention_rate end)*100,2) as varchar) as d1, "+
					" cast(round(sum(case when retention_day=2 then retention_rate end)*100,2) as varchar) as d2, "+
					" cast(round(sum(case when retention_day=3 then retention_rate end)*100,2) as varchar) as d3, "+
					" cast(round(sum(case when retention_day=4 then retention_rate end)*100,2) as varchar) as d4, "+
					" cast(round(sum(case when retention_day=5 then retention_rate end)*100,2) as varchar) as d5, "+
					" cast(round(sum(case when retention_day=6 then retention_rate end)*100,2) as varchar) as d6, "+
					" cast(round(sum(case when retention_day=7 then retention_rate end)*100,2) as varchar) as d7 "+
					" from  "+
					" (select install_date,install,date_diff('day',cast(install_date as date),cast(ds as date)) as retention_day, "+
					" cast(retention_uu as double)/cast(install as double) as retention_rate "+
					" from etl.a_user_retention_ltv where  1=1 ";
					sql+= this.getSnidGameid(snid, gameid);
					if(startdate!=null&&!startdate.isEmpty()){
						sql +=" and install_date>='"+startdate+"' and ds>='"+startdate+"'";
					}
					 if(enddate!=null&&!enddate.isEmpty()){
						 sql +=" and date(ds)<=date_add('day',7,date'"+enddate+"')";
					 }
					sql +=" )t group by install_date,install "+
							" order by install_date";
		*/
		
		String sql =  this.getTestReportLeaveSaveSql(testReportLeaveSaveVo, systemType);
		List<TestReportLeaveSave> SaveAllList = prestoTestReport.TestReportGamesLeaveSaveData(sql);
		
		return SaveAllList;
	}
	/**
	 * 设备维度
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private List<TestReportSbwd> getSbwdList( TestReportSbwdVo testReportSbwdVo,String systemType){
		/*
		String sql="select install_date, "+
					" sum(cast(case when retention_day=0 then new_equip end as bigint)) as new_equip,   "+                  
					" cast(round(sum(case when retention_day=1 then retention_rate end)*100,2) as varchar) as d1, "+
					" cast(round(sum(case when retention_day=2 then retention_rate end)*100,2) as varchar) as d2, "+
					" cast(round(sum(case when retention_day=3 then retention_rate end)*100,2) as varchar) as d3, "+
					" cast(round(sum(case when retention_day=4 then retention_rate end)*100,2) as varchar) as d4, "+
					" cast(round(sum(case when retention_day=5 then retention_rate end)*100,2) as varchar) as d5, "+
					" cast(round(sum(case when retention_day=6 then retention_rate end)*100,2) as varchar) as d6, "+
					" cast(round(sum(case when retention_day=7 then retention_rate end)*100,2) as varchar) as d7 "+
					" from  "+
					" (select install_date,new_equip,date_diff('day',cast(install_date as date),cast(ds as date)) as retention_day, "+
					" cast(retention_equip as double)/cast(new_equip as double) as retention_rate "+
					" from facts.equip_retention_ltv where 1=1  ";
					sql+=this.getSnidGameid(snid, gameid);
					if(startdate!=null&&!startdate.isEmpty()){
						sql+=" and install_date>='"+startdate+"' and ds>='"+startdate+"' ";
					}
					 if(enddate!=null&&!enddate.isEmpty()){
						 sql+=" and date(ds)<=date_add('day',7,date'"+enddate+"')";
					 }
					  
					sql+=" )t group by install_date order by install_date"; 
					*/
				String sql =  this.getTestReportSbwdSql(testReportSbwdVo, systemType);
				List<TestReportSbwd> sbwdList = prestoTestReport.TestReportGamesSbwdData(sql);
				return sbwdList;
	}
	
	/**
	 * 总体(主要为注收比)
	       指标：账号维度d0到d7的注收比
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private List<TestReportZtZsb> getZtZsbList(TestReportZtZsbVo testReportZtZsbVo,String systemType){
		/*
		String sql="select install_date, "+
				" sum(cast(case when retention_day=0 then install end as bigint))as install,   "+
				" round(sum(case when retention_day=0 then cast(pay as double)/cast(rate as double) end),2) as d0,    "+             
				" round(sum(case when retention_day=1 then cast(pay as double)/cast(rate as double) end),2) as d1, "+
				" round(sum(case when retention_day=2 then cast(pay as double)/cast(rate as double) end),2) as d2, "+
				" round(sum(case when retention_day=3 then cast(pay as double)/cast(rate as double) end),2) as d3, "+
				" round(sum(case when retention_day=4 then cast(pay as double)/cast(rate as double) end),2) as d4, "+
				" round(sum(case when retention_day=5 then cast(pay as double)/cast(rate as double) end),2) as d5, "+
				" round(sum(case when retention_day=6 then cast(pay as double)/cast(rate as double) end),2) as d6, "+
				" round(sum(case when retention_day=7 then cast(pay as double)/cast(rate as double) end),2) as d7 "+
				" from                                            "+
				" (select install_date,install,date_diff('day',cast(install_date as date),cast(ds as date)) as retention_day, "+
				" cast(total_payment as double)/cast(install as double)as pay,snid,gameid "+
				" from etl.a_user_retention_ltv where 1=1 ";
				sql+=this.getSnidGameid(snid, gameid);
				if(startdate!=null&&!startdate.isEmpty()){
					sql+=" and install_date>='"+startdate+"' and ds>='"+startdate+"' ";
				}
				 if(enddate!=null&&!enddate.isEmpty()){
					 sql+=" and date(ds)<=date_add('day',7,date'"+enddate+"')";
				 }
				 sql += " 		)t1 "+
				" left outer join  "+
				" (select snid,gameid,rate "+
				" from dimension.all_game_name where 1=1  ";
				 sql+=this.getSnidGameid(snid, gameid);
				sql+= " ) t2 "+
				" on t1.snid=t2.snid and t1.gameid=t2.gameid "+
				" group by install_date "+
				" order by install_date";
		*/
		
				String sql =  this.getTestReportZtZsbSql(testReportZtZsbVo, systemType);
				List<TestReportZtZsb> ztZsbLsit = prestoTestReport.TestReportGamesZtZsbData(sql);
				return ztZsbLsit;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * ---------注收比
		--总体
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param install_date
	 * @return
	 */
	private List<TestReport> getZsbSaveAllList(String snid,String gameid,String startdate,String install_date){
		
		String sql = " select install_date,install,datediff(ds,install_date) as retention_day,round(total_payment/install,4) avg_payment "+
					" from etl.a_user_retention_ltv  where 1=1 ";
					sql+=this.getSql2(snid, gameid, startdate, install_date);
		List<TestReport> ZsbSaveAllList =prestoTestReport.TestReportGamesData(sql);
		return ZsbSaveAllList;
		
	}
	
	/*
	 * ------等级分布(总体)
		---新增等级分布(一段时间的新增等级分布)
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private List<TestReportLevel> getAddLevelList(String snid,String gameid,String startdate,String enddate){
		String sql = "select k1.ds,user_level,num as dau,total_dau "+
					" from "+
					" (select ds,user_level,count(distinct userid)as num "+
					" from  "+
					" ( select userid,(case when user_level is null then 1 else user_level end) as user_level "+
					" from  "+
					" (select a.ds, a.userid,(case when (a.user_level+0)>(b.user_level+0) then a.user_level else b.user_level end) as user_level "+
					" from  "+
					" (select ds,userid ,level as user_level "+
					" from aggr.a_user_new "+
					" where  1=1 ";
					sql += this.getSql(snid, gameid, startdate, enddate);
					sql += " and to_date(first_date)=ds)a "+
					" left outer join "+
					" (select ds,userid,user_level "+
					" from etl.accumulate_milestone_levelup where 1=1 ";
					sql += this.getSql(snid, gameid, startdate, enddate);
					sql += ")b "+
					" on a.userid=b.userid and a.ds=b.ds)T)ok "+
					" group by ds, user_level)k1 "+
					" left outer join "+
					" (select ds,count(distinct userid) as total_dau "+
					" from aggr.a_user_new where 1=1 ";
					sql += this.getSql(snid, gameid, startdate, enddate);
					sql += " and to_date(last_date)=ds "+
					" group by ds)k2 "+
					" on k1.ds=k2.ds";
		List<TestReportLevel> AddLevel_AllList = prestoTestReport.TestReportGamesLevelData(sql);
		return AddLevel_AllList;
			
	}
	/**
	 * 活跃等级分布(一段时间的活跃等级分布)
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private List<TestReportLevel> getActiveLevelList(String snid,String gameid,String startdate,String enddate){
		
		String sql ="select k1.ds,user_level,num as dau,total_dau "+
					" from "+
					" (select ds,user_level,count(distinct userid)as num "+
					" from  "+
					" ( select userid,(case when user_level is null then 1 else user_level end) as user_level "+
					"  from  "+
					" (select a.ds, a.userid,(case when (a.user_level+0)>(b.user_level+0) then a.user_level else b.user_level end) as user_level "+
					" from  "+
					" (select ds,userid ,level as user_level "+
					" from aggr.a_user_new "+
					" where  1=1 ";
					sql += this.getSql(snid, gameid, startdate, enddate);
					sql += " and to_date(last_date)=ds)a "+
					" left outer join  "+
					" (select ds,userid,user_level  "+
					" from etl.accumulate_milestone_levelup "+
					" where 1=1 ";
					sql +=this.getSql(snid, gameid, startdate, enddate);
					sql += ")b "+
					" on a.userid=b.userid and a.ds=b.ds)T)ok  "+
					" group by ds, user_level)k1 "+
					" left outer join "+
					" (select ds,count(distinct userid) as total_dau "+
					" from aggr.a_user_new where 1=1 ";
					sql +=this.getSql(snid, gameid, startdate, enddate);
					sql += " and to_date(last_date)=ds "+
					" group by ds)k2  "+
					" on k1.ds=k2.ds";
					
			List<TestReportLevel> ActiveLevelList = prestoTestReport.TestReportGamesLevelData(sql);
			return ActiveLevelList;
	}
	
	/**
	 * 流失等级分析
	 * @param snid
	 * @param gameid
	 * @param enddate
	 * @param LossDay
	 * @param install_creative
	 * @return
	 */
	private List<TestReportLevel> getLossLevelList(String snid,String gameid,String enddate,String LossDay,String install_creative){
		String sql ="select install_creative,user_level,cnt "+
					" from  "+
					" (select install_creative,case when b.user_level is null then 1 else b.user_level end as user_level,count(distinct a.userid) cnt "+
					" from  "+
					" (select install_creative,userid "+
					" from aggr.a_user_new "+
					" where 1=1" ;
					if(!snid.isEmpty()){
			         	sql +=" and snid='"+snid+"'";
			         }
					 if(!gameid.isEmpty()){
				         	sql +=" and gameid='"+gameid+"'";
				      }
					 if(!enddate.isEmpty() && !LossDay.isEmpty()){
				         	sql +=" and  ds='"+enddate+"' and datediff(date('"+enddate+"'), date(split(last_date,' ')[0]))>='"+LossDay+"' ";
				      }
					 if(!install_creative.isEmpty()){
						 sql += " and if( '0'='"+install_creative+"' , 1=1,install_creative='"+install_creative+"') ";
					 }
					sql += ")a "+
					" left outer join "+
					" (select userid,max(user_level) user_level "+
					" from etl.accumulate_milestone_levelup "+
					" where snid='${snid}' and gameid='${gameid}' and  ds='${end_date}' "+
					" group by userid)b "+
					" on a.userid=b.userid "+
					" group by install_creative,case when b.user_level is null then 1 else b.user_level end "+
					" order by user_level)t";
			List<TestReportLevel> LossLevelList = prestoTestReport.TestReportGamesLevelData(sql);
			return LossLevelList;
	}
	/**
	 * 前期加载转化
	 * @param snid
	 * @param gameid
	 * @param ds
	 * @param loading
	 * @return
	 */
	private List<TestReportConversion> getLoad_ConversionList(String snid,String gameid,String ds,String loading){
		String sql="select val1,family,cnt,use_time "+
					" from "+
					" (select val1,family,count(distinct userid) as cnt,percentile(use_time,0.5) as use_time "+
					" from "+
					" (select a.userid,val1,family,unix_timestamp(str2)-unix_timestamp(str) as use_time "+
					" from "+
					" (select userid,val+0 as val1,family,min(concat(ds,' ',gameinfo_time)) as str  "+
					" from default.nt_gameinfo where 1=1 ";
					this.getSql3(snid, gameid, ds, loading);
					sql+=" group by userid,val+0,family)a "+
					" left outer join "+
					" (select userid,val-1 as val2,min(concat(ds,' ',gameinfo_time)) as str2  "+
					" from default.nt_gameinfo "+
					" where 1=1  ";
					this.getSql3(snid, gameid, ds, loading);
					sql +=" group by userid,val-1)b  "+
					" on a.userid=b.userid and a.val1=b.val2 "+
					" left outer join "+
					" (select userid "+
					" from aggr.a_user_new where 1=1 ";
					if(!snid.isEmpty()){
			         	sql +=" and snid='"+snid+"'";
			         }
					 if(!gameid.isEmpty()){
				         	sql +=" and gameid='"+gameid+"'";
				      }
					 if(!ds.isEmpty()){
						 sql +=" and ds=date_sub('${date}',1)";
					 }
					sql +=" )c "+
					 " on a.userid=c.userid "+
					 " where c.userid is null "+
					 " )a "+
					 " group by val1,family)ok";
					List<TestReportConversion>  Load_ConversionList = prestoTestReport.TestReportGamesConversionData(sql);
					return Load_ConversionList;
		
	}
	
	/**
	 * 新手引导转化
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param enddate
	 * @param guide
	 * @return
	 */
	private List<TestReportConversion> getNewConversionList(String snid,String gameid,String startdate,String enddate,String guide){
		String sql="select ds,kingdom,family,count(distinct userid) as num "+
					" from  "+
					"  (select a.gameid,a.userid,a.family,a.kingdom,a.ds "+
					"  from "+
					"  (select userid,gameid,family,kingdom,ds "+
					"  from nt_counter where 1=1 "+
					 this.getSql(snid, gameid, startdate, enddate);
					 if(!guide.isEmpty()){
						 sql+= " and counter='"+guide+"' ";
					 }
					  
					sql+= " group by userid,family,kingdom,gameid,ds)a  "+
					 " right  outer join  "+
					 " (select userid,gameid,to_date(first_date) as ds  "+
					 " from aggr.a_user_new where 1=1  "+
					this.getSql4(snid, gameid, startdate, enddate);
					sql += "  )b on a.userid=b.userid and a.gameid=b.gameid and a.ds=b.ds "+
					 " where a.userid is not null)X "+
					 " group by kingdom,family,ds "+
					 " order by ds,kingdom,family";
				List<TestReportConversion> NewConversionList = prestoTestReport.TestReportGamesConversionData(sql);
				return NewConversionList;
	}
	
	/**
	 * 金币消耗&获取
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private List<TestReportGoldObtain> getGoldObtain(String snid,String gameid,String startdate,String enddate){
		String sql="select ds,currency,kingdom,phylum,classfield,genus, amount,val, num, cnt "+
					" from  "+
					" (select ds,currency,kingdom,phylum,classfield,genus,sum(amount+0) as amount,sum(val+0) as val, "+
					" count(distinct userid)as num,count(userid) as cnt "+
					" from nt_economy  "+
					" where 1=1  ";
					this.getSql(snid, gameid, startdate, enddate);
					//snid='${snid}' and gameid='${gameid}' and ds>='${date}'and ds<='${date2}'
					sql +=" group by ds,phylum,kingdom,classfield,genus,currency)ok ";
					
			List<TestReportGoldObtain>  goldBbtainList = prestoTestReport.TestReportGamesGoldObtainData(sql);
			return goldBbtainList;
	}
	/**
	 * 用户首次付费等级分布
	 * @param snid
	 * @param gameid
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private List<TestReportLevel> getPayLevelList(String snid,String gameid,String startdate,String enddate){
		String sql ="select first_payment_level,users "+
					" from  "+
					" (select first_payment_level,count(distinct userid)as users "+
					" from "+
					" (select userid,max(val) as first_payment_level "+
					" from "+
					" (select userid,first_payment_timestamp,val,time_diff "+
					" from "+
					" (select a.userid,first_payment_timestamp,val,milestone_timestamp, "+
					" date_diff('minute',cast(milestone_timestamp  as timestamp),cast(first_payment_timestamp as timestamp)) as time_diff "+
					" from "+
					" (select userid,first_payment_timestamp "+
					" from aggr.a_user_new where 1=1 ";
					
					this.getSnidGameid(snid, gameid);
					
					if(!enddate.isEmpty()){
						sql +=" and date(ds)=date_add('day',-1,date'"+enddate+"')  ";
					}
					if(!startdate.isEmpty()){
						sql +=" and split(first_date,' ')[1]>='"+startdate+"' ";
					}
					sql +=" and frist_amount>0)a "+
					" left outer join "+
					" (select userid,cast(val as bigint) as val,concat(milestone_date,' ',milestone_time)as milestone_timestamp "+
					" from nt_milestone where 1=1  ";
					this.getSnidGameid(snid, gameid); 
					if(!enddate.isEmpty()){
						sql +=" and ds<='"+enddate+"'";
					}
					 //snid='${snid}' and gameid='${gameid}' and ds<='${end_date}'
					sql +=" )b on a.userid=b.userid)a "+
					" where time_diff>=0)ok "+
					" group by userid)OK "+
					" group by first_payment_level "+
					" order by users desc)ok";
		List<TestReportLevel> payLevelList = prestoTestReport.TestReportGamesLevelData(sql);
		return payLevelList;
		
	}
	
	
	private String getSnidGameid(String snid,String gameid){
		String sql="";
		if(snid!=null&&!snid.isEmpty()){
         	sql +=" and snid='"+snid+"'";
         }
		 if(gameid!=null&&!gameid.isEmpty()){
	         	sql +=" and gameid='"+gameid+"'";
	      }
		 return sql;
		
	}
	private String getSql4(String snid,String gameid,String startdate,String enddate){
		
		String sql="";
		if(snid!=null&&!snid.isEmpty()){
         	sql +=" and snid='"+snid+"'";
         }
		 if(gameid!=null&&!gameid.isEmpty()){
	         	sql +=" and gameid='"+gameid+"'";
	      }
		 if(enddate!=null&&!enddate.isEmpty()){
			 sql +=" and ds ='"+enddate+"'";
		 }
		 if(startdate!=null&&!startdate.isEmpty()){
			 sql += " and to_date(first_date)>='"+startdate+"'";
		 }
		 return sql;
	}
	private String getSql3(String snid,String gameid,String ds,String loading){
		
		
		String sql="";
		if(snid!=null&&!snid.isEmpty()){
         	sql +=" and snid='"+snid+"'";
         }
		 if(gameid!=null&&!gameid.isEmpty()){
	         	sql +=" and gameid='"+gameid+"'";
	      }
		 if(ds!=null&&!ds.isEmpty()){
			 sql +=" and ds='"+ds+"'";
		 }
		if(loading!=null&&!loading.isEmpty()){
			sql +=" and gameinfo='"+loading+"'";
		}
		return sql;
		
	}
	private String getSql2(String snid,String gameid,String startdate,String install_date){
		String sql = "";
		if(snid!=null&&!snid.isEmpty()){
         	sql +=" and snid='"+snid+"'";
         }
		 if(gameid!=null&&!gameid.isEmpty()){
	         	sql +=" and gameid='"+gameid+"'";
	      }
         if(startdate!=null&&!startdate.isEmpty()){
         	sql +=" and ds='"+startdate+"'";
         }
         if(install_date!=null&&!install_date.isEmpty()){
         	sql +=" and  install_date<='"+install_date+"'";
         }
		return sql;
	};
	
	private String getSql(String snid,String gameid,String startdate,String enddate){
		String sql ="";
		if(snid!=null&&!snid.isEmpty()){
         	sql +=" and snid='"+snid+"'";
         }
		 if(gameid!=null&&!gameid.isEmpty()){
	         	sql +=" and gameid='"+gameid+"'";
	      }
         if(startdate!=null&&!startdate.isEmpty()){
         	sql +=" and ds>='"+startdate+"'";
         }
         if(enddate!=null&&!enddate.isEmpty()){
         	sql +=" and  ds<='"+enddate+"'";
         }
         return sql;
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
