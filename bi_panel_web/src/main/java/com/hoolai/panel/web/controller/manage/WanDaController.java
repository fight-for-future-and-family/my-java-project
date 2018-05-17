package com.hoolai.panel.web.controller.manage;

import java.math.BigDecimal;
import java.text.ParseException;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoolai.bi.report.model.Types.CurrencyConvertedToRMBRate;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.GamesCreative;
import com.hoolai.bi.report.model.entity.GamesCreativeRate;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.service.DailyReportService;
import com.hoolai.bi.report.service.GamesCreativeService;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.service.WandaDailyReportService;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.manage.model.SysConfig;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.SysConfigService;
import com.hoolai.manage.util.CsvUtils;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;

@Controller
@RequestMapping("/panel_bi/wanda")
public class WanDaController extends AbstractManageController {
	
	@Autowired
	private GamesService gamesService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private DailyReportService dailyReportService;
	
	@Autowired
	private SourceDailyReportService sourceDailyReportService;
	
	@Autowired
	private GamesCreativeService gamesCreativeService;
	
	@Autowired
	private WandaDailyReportService wandaDailyReportService;
	
	@RequestMapping(value = {"/getWanDaGameDatasView.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getWanDaGameDatasView(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		if("query".equals(request.getParameter("type"))){
			String seriesName = request.getParameter("seriesName");
			String dateTime = request.getParameter("dateTime");
			String creative = request.getParameter("creative");
			WandaDailyReport wandaDailyReport = new WandaDailyReport();
			List<WandaDailyReport> listReport = new ArrayList<WandaDailyReport>();
			wandaDailyReport.setSeriesName(seriesName);
			if(dateTime!=null){
				wandaDailyReport.setDs(dateTime);
			}
			if(creative!=null){
				wandaDailyReport.setCreative(creative);
			}
			listReport = wandaDailyReportService.queryDailyData(wandaDailyReport);
			if(listReport.size()>1&&listReport.get(0)==null){
				listReport = null;
			}
			ret.put("reports", listReport);
		}else if("add".equals(request.getParameter("type"))){
			String seriesName = request.getParameter("seriesName");
			String dateTime = request.getParameter("dateTime");
			String creative = request.getParameter("creative");
			BigDecimal paymentAmount = new BigDecimal(request.getParameter("paymentAmount"));
			String remark = request.getParameter("remark");
			WandaDailyReport wandaDailyReport = new WandaDailyReport();
			wandaDailyReport.setSeriesName(seriesName);
			wandaDailyReport.setDs(dateTime);
			wandaDailyReport.setCreative(creative);
			wandaDailyReport.setPaymentAmount(paymentAmount);
			wandaDailyReport.setRemark(remark);
			SysConfig sysConfig = new SysConfig();
			sysConfig.setName(seriesName);
			if(sysConfigService.selectExists(sysConfig)>0){
				wandaDailyReport.setIsExists("0");
			}else{
				wandaDailyReport.setIsExists("1");
			}
			wandaDailyReport.setIsTyping("1");
			int num = wandaDailyReportService.selectExists(wandaDailyReport);
			if(num==0){
				num = wandaDailyReportService.saveDailyReport(wandaDailyReport);
			}else{
				num++;
			}
			ret.put("num", num);
		}else if("edit".equals(request.getParameter("type"))){
			String seriesName = request.getParameter("seriesName");
			String dateTime = request.getParameter("dateTime");
			String creative = request.getParameter("creative");
			BigDecimal paymentAmount = new BigDecimal(request.getParameter("paymentAmount"));
			String remark = request.getParameter("remark");
			WandaDailyReport wandaDailyReport = new WandaDailyReport();
			wandaDailyReport.setSeriesName(seriesName);
			wandaDailyReport.setDs(dateTime);
			wandaDailyReport.setCreative(creative);
			wandaDailyReport.setPaymentAmount(paymentAmount);
			wandaDailyReport.setRemark(remark);
			SysConfig sysConfig = new SysConfig();
			sysConfig.setName(seriesName);
			if(sysConfigService.selectExists(sysConfig)>0){
				wandaDailyReport.setIsExists("0");
			}else{
				wandaDailyReport.setIsExists("1");
			}
			int num = wandaDailyReportService.updateWandaDailyReport(wandaDailyReport);
			ret.put("num", num);
		}else{
			String beginDate = request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			String day = "";
			if("".equals(beginDate)||"".equals(endDate)){
				Map<String, String> map = getLastSunday(request);
				beginDate = map.get("beginDate");
				endDate = map.get("endDate");
				day = map.get("day");
			}
			
//			String dateStr = "2017-01-18";
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Calendar cal = Calendar.getInstance();//获取当前日期
//			cal.setTime(sdf.parse(dateStr));
//			int num = 1;
//			for(;num==1;){
//				cal.add(Calendar.DAY_OF_MONTH, -1);
//				dateStr = sdf.format(cal.getTime());
//				List<WandaDailyReport> resultList = select(dateStr);
//				if(resultList.size()>0){
//					wandaDailyReportService.saveWandaDailysReport(resultList);
//				}
//				System.out.println(dateStr);
//				if("2017-01-12".equals(dateStr)){
//					num --;
//				}
//			}
			
			
			Map<String, String> dateMap = new HashMap<String, String>();
			dateMap.put("beginDate", beginDate);
			dateMap.put("endDate", endDate);
			
			List<WandaDailyReport> firstTen = wandaDailyReportService.selectFirstTen(dateMap);
			List<WandaDailyReport> resultList = new ArrayList<WandaDailyReport>();
			if(firstTen.size()==0){
				ret.put("reports", resultList);
				ret.put("beginDate", beginDate);
				ret.put("endDate", endDate);
				return ret;
			}
			WandaDailyReport sumReport = new WandaDailyReport();
			sumReport.setSeriesName("合计");
			BigDecimal sumPayAmount = new BigDecimal(0);
			BigDecimal sumHuaiAmount = new BigDecimal(0);
			int num=1;
			if(firstTen.size()==0){
				return ret;
			}
			for (WandaDailyReport wandaDaily : firstTen) {
				Map<String, String> paramsMap = dateMap;
				paramsMap.put("seriesName", wandaDaily.getSeriesName());
				List<WandaDailyReport> dailyList = wandaDailyReportService.selectSeriesList(paramsMap);
				for (WandaDailyReport wandaDailyReport : dailyList) {
					sumPayAmount = sumPayAmount.add(wandaDailyReport.getPaymentAmount());
					sumHuaiAmount = sumHuaiAmount.add(wandaDailyReport.getHulaiAmount());
					String id = String.valueOf(num);
					Map<String, String> tempMap = new HashMap<String, String>();
					tempMap.put("seriesName", wandaDaily.getSeriesName());
					tempMap.put("creative", wandaDailyReport.getCreative());
					tempMap.put("beginDate", paramsMap.get("beginDate").substring(0, 7));
					tempMap.put("endDate", paramsMap.get("endDate").substring(0, 7));
					wandaDailyReport.setId(id);
					resultList.add(wandaDailyReport);
				}
				num++;
			}
			List<WandaDailyReport> otherList = wandaDailyReportService.selectOther(dateMap);
			sumPayAmount = sumPayAmount.add(otherList.get(0).getPaymentAmount());
			sumHuaiAmount = sumHuaiAmount.add(otherList.get(0).getHulaiAmount());
			sumReport.setPaymentAmount(sumPayAmount);
			sumReport.setHulaiAmount(sumHuaiAmount);
			resultList.addAll(otherList);
			resultList.add(sumReport);
			
			ret.put("reports", resultList);
			ret.put("beginDate", beginDate);
			ret.put("endDate", endDate);
			ret.put("day", day);
		}
		return ret;
	}
	
	@RequestMapping(value = {"/getWanDaGameDatasViewAll.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getWanDaGameDatasViewAll(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String day = "";
		if("".equals(beginDate)||"".equals(endDate)){
			Map<String, String> map = getLastSunday(request);
			beginDate = map.get("beginDate");
			endDate = map.get("endDate");
			day = map.get("day");
		}
		
		Map<String, String> dateMap = new HashMap<String, String>();
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);
		dateMap.put("endNum", "200");
		
		List<WandaDailyReport> firstTen = wandaDailyReportService.selectFirstTen(dateMap);
		List<WandaDailyReport> resultList = new ArrayList<WandaDailyReport>();
		if(firstTen.size()==0){
			ret.put("reports", resultList);
			ret.put("beginDate", beginDate);
			ret.put("endDate", endDate);
			return ret;
		}
		WandaDailyReport sumReport = new WandaDailyReport();
		sumReport.setSeriesName("合计");
		BigDecimal sumPayAmount = new BigDecimal(0);
		BigDecimal sumHuaiAmount = new BigDecimal(0);
		int num=1;
		if(firstTen.size()==0){
			return ret;
		}
		for (WandaDailyReport wandaDaily : firstTen) {
			Map<String, String> paramsMap = dateMap;
			paramsMap.put("seriesName", wandaDaily.getSeriesName());
			List<WandaDailyReport> dailyList = wandaDailyReportService.selectSeriesList(paramsMap);
			for (WandaDailyReport wandaDailyReport : dailyList) {
				sumPayAmount = sumPayAmount.add(wandaDailyReport.getPaymentAmount());
				sumHuaiAmount = sumHuaiAmount.add(wandaDailyReport.getHulaiAmount());
				String id = String.valueOf(num);
				Map<String, String> tempMap = new HashMap<String, String>();
				tempMap.put("seriesName", wandaDaily.getSeriesName());
				tempMap.put("creative", wandaDailyReport.getCreative());
				tempMap.put("beginDate", paramsMap.get("beginDate").substring(0, 7));
				tempMap.put("endDate", paramsMap.get("endDate").substring(0, 7));
				wandaDailyReport.setId(id);
				resultList.add(wandaDailyReport);
			}
			num++;
		}
		sumReport.setPaymentAmount(sumPayAmount);
		sumReport.setHulaiAmount(sumHuaiAmount);
		resultList.add(sumReport);
		
		ret.put("reports", resultList);
		ret.put("beginDate", beginDate);
		ret.put("endDate", endDate);
		ret.put("day", day);
		
		return ret;
	}
	
	public Map<String,String> getLastSunday(HttpServletRequest request) throws ParseException {
		String dateStr = new String();
		Map<String, String> ret = new HashMap<String, String>();
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    if(isWanDa(request)) {
	    	calendar.add(Calendar.DAY_OF_MONTH, -7);
	    	ret.put("day", "7");
	    }else{
	    	calendar.add(Calendar.DAY_OF_MONTH, -2);
	    	ret.put("day", "2");
	    }
		dateStr = sdf.format(calendar.getTime());
		ret.put("endDate", dateStr);
//		calendar.add(Calendar.DAY_OF_MONTH, -30);
		calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		dateStr = sdf.format(calendar.getTime());
		if(!ret.get("endDate").contains(dateStr.substring(0, 7))){
			calendar.add(Calendar.DAY_OF_MONTH, -30);
			dateStr = sdf.format(calendar.getTime());
		}
		ret.put("beginDate", dateStr);
	    return ret;
	}
	
	public List<WandaDailyReport> mergeDaily(List<WandaDailyReport> gameList, 
			List<WandaDailyReport> wndaDailyList,
			Double toRMBRate){
		for (WandaDailyReport wandaDailyReport : wndaDailyList) {
			BigDecimal amount = wandaDailyReport.getPaymentAmount();
			amount = new BigDecimal(amount.doubleValue()*toRMBRate);
			wandaDailyReport.setPaymentAmount(amount);
			gameList.add(wandaDailyReport);
		}
		return gameList;
	}
	
	public List<WandaDailyReport> select(String dateStr){
		List<WandaDailyReport> resultList = new ArrayList<WandaDailyReport>();
		List<GamesCreative> gameNameList = gamesCreativeService.selectGameNameList();
		for (GamesCreative gamesName : gameNameList) {
			List<GamesCreative> gamesList = gamesCreativeService.selectGames(gamesName);
			for (GamesCreative gamesCreative : gamesList) {
				gamesCreative.setDs(dateStr);
				List<WandaDailyReport> wndaDailyList = new ArrayList<WandaDailyReport>();
				if(!"官网".equals(gamesCreative.getCreative())){
					wndaDailyList = wandaDailyReportService.selectGameCreativeDailyBysource(gamesCreative);
				}else{
					wndaDailyList = wandaDailyReportService.selectGwCreativeDailyBysource(gamesCreative);
				}	
				if(wndaDailyList.size()>0){
					Games game = gamesService.getGames(gamesCreative.getSnid(), gamesCreative.getGameid());
					String currency = game.getCurrency();
					if(!"元".equals(currency)){
						System.out.println();
					}
					Double toRMBRate = CurrencyConvertedToRMBRate.convert(currency).getValue();
					resultList = mergeDaily(resultList, wndaDailyList, toRMBRate);
				}
			}
		}
		return resultList;
	}
	
	@RequestMapping(value = {"/downloadData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String day = request.getParameter("day");
		Map<String, String> dateMap = new HashMap<String, String>();
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);
		
		boolean status = true;
		if(day!=null&&!day.isEmpty()){
			status = false;
			dateMap.put("endNum", "200");
		}
		List<WandaDailyReport> firstTen = wandaDailyReportService.selectFirstTen(dateMap);
		List<WandaDailyReport> resultList = new ArrayList<WandaDailyReport>();
		WandaDailyReport sumReport = new WandaDailyReport();
		sumReport.setSeriesName("合计");
		
		BigDecimal sumPayAmount = new BigDecimal(0);
		BigDecimal sumHuaiAmount = new BigDecimal(0);
		int num=1;
		for (WandaDailyReport wandaDaily : firstTen) {
			Map<String, String> paramsMap = dateMap;
			paramsMap.put("seriesName", wandaDaily.getSeriesName());
			List<WandaDailyReport> dailyList = wandaDailyReportService.selectSeriesList(paramsMap);
			for (WandaDailyReport wandaDailyReport : dailyList) {
				sumPayAmount = sumPayAmount.add(wandaDailyReport.getPaymentAmount());
				sumHuaiAmount = sumHuaiAmount.add(wandaDailyReport.getHulaiAmount());
				String id = String.valueOf(num);
				wandaDailyReport.setId(id);
				resultList.add(wandaDailyReport);
			}
			num++;
		}
		if(status){
			List<WandaDailyReport> otherList = wandaDailyReportService.selectOther(dateMap);
			sumPayAmount = sumPayAmount.add(otherList.get(0).getPaymentAmount());
			sumHuaiAmount = sumHuaiAmount.add(otherList.get(0).getHulaiAmount());
			resultList.addAll(otherList);
		}
		
		sumReport.setPaymentAmount(sumPayAmount);
		sumReport.setHulaiAmount(sumHuaiAmount);
		resultList.add(sumReport);
		
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(null, response);
		processor.initWandsList(resultList);
		processor.writeWandaDaily(beginDate, endDate);
		return null;
	}
	
	@RequestMapping(value = {"/addCreativeRate.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> addCreativeRate(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String seriesName = request.getParameter("seriesName");
		String creative = request.getParameter("creative");
		String rate = request.getParameter("rate");
		String ds = request.getParameter("ds");
		
		GamesCreativeRate gamesCreativeRate = new GamesCreativeRate();
		gamesCreativeRate.setSeriesName(seriesName);
		gamesCreativeRate.setCreative(creative);
		gamesCreativeRate.setRate(new Double(rate));
		gamesCreativeRate.setDs(ds);
		List<GamesCreativeRate> gamesCreativeRateList = gamesCreativeService.selectGameCreativeRates(gamesCreativeRate);
		int num = 0;
		if(!(gamesCreativeRateList.size()>0)){
			num = gamesCreativeService.saveGamesCreativeRate(gamesCreativeRate);
		}
		
		ret.put("num", num);
		return ret;
	}
	
	@RequestMapping(value = {"/editCreativeRate.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> editCreativeRate(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String seriesName = request.getParameter("seriesName");
		String creative = request.getParameter("creative");
		String rate = request.getParameter("rate");
		String ds = request.getParameter("ds");
		
		GamesCreativeRate gamesCreativeRate = new GamesCreativeRate();
		gamesCreativeRate.setSeriesName(seriesName);
		gamesCreativeRate.setCreative(creative);
		gamesCreativeRate.setRate(new Double(rate));
		gamesCreativeRate.setDs(ds);
		int num = gamesCreativeService.updateGamesCreativeRate(gamesCreativeRate);
		
		ret.put("num", num);
		return ret;
	}
	
	@RequestMapping(value = {"/getCreativeRate.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getCreativeRate(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String seriesName = request.getParameter("seriesName");
		String creative = request.getParameter("creative");
		String ds = request.getParameter("ds");
		GamesCreativeRate gamesCreativeRate = new GamesCreativeRate();
		gamesCreativeRate.setSeriesName(seriesName);
		if(creative!=null){
			gamesCreativeRate.setCreative(creative);
		}
		if(ds!=null){
			gamesCreativeRate.setDs(ds);
		}
		List<GamesCreativeRate> gamesCreativeRateList = gamesCreativeService.selectGameCreativeRates(gamesCreativeRate);
		ret.put("reports", gamesCreativeRateList);
		return ret;
	}
	
	@RequestMapping(value = {"/toCreativeDailySave.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toCreativeDailySave(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		return "index/gamesDailySave.jsp";
	}
	
	@RequestMapping(value = {"/toCreativeRateSave.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toCreativeRateSave(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		return "index/gamesCreativeRateSave.jsp";
	}
	
	@RequestMapping(value = {"/toWandaDailyQuery.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String toWandaDailyQuery(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		return "index/gamesDailyQuery.jsp";
	}
	
	@RequestMapping(value = {"/importCreativeRate.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String importCreativeRate(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret = new HashMap<String, Object>();
		
		List<String> dataList = null;
		List<WandaDailyReport> wdrList = new ArrayList<WandaDailyReport>();
		WandaDailyReport wdr = null;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int num = 1;
		try {
			dataList = CsvUtils.importCsv(file);
			if(dataList==null||dataList.size()==0){
				throw new RuntimeException("上传失败,文件内容为空");
			}
			for (String str : dataList) {
				num++;
				String createTime = sdf.format(new Date());
				wdr = new WandaDailyReport();
				String[] strArr = str.split(",");
				wdr.setSeriesName(strArr[0]);
				if(isCreative(strArr[1])){
					wdr.setCreative(strArr[1]);
				}else{
					throw new RuntimeException("上传失败,第"+num+"行渠道数据有误");
				}
				
				if(isNumeric(strArr[2])){
					wdr.setPaymentAmount(new BigDecimal(strArr[2]));
				}else{
					throw new RuntimeException("上传失败,第"+num+"行金额数据有误");
				}
				
				if(!strArr[3].contains("/")&&strArr[3].contains("-")){
					wdr.setDs(strArr[3]);
				}else{
					throw new RuntimeException("上传失败,第"+num+"行日期数据有误");
				}
				
				wdr.setIsTyping("1");
				wdr.setCreateTime(createTime);
				wdrList.add(wdr);
			}
			int i = wandaDailyReportService.saveWandaDailysReport(wdrList);
			if(i>0){
				request.getSession().setAttribute("importDailyMsg", "数据导入成功");
			}
			file = null;
		} catch (Exception e) {
			request.getSession().setAttribute("importDailyError", e.getMessage());
		}
		
		return "index/gamesDailySave.jsp";
	}
	
	public static boolean isCreative(String str){
		if("IOS".equals(str)||"安卓".equals(str)||"官网".equals(str)||"联运".equals(str)||"海外".equals(str)||
				"运营商".equals(str)||"页游".equals(str)){
			return true;
		}
		return false;
	}
	
	public static boolean isNumeric(String str) {
		BigDecimal bd = null;
		try {
			bd = new BigDecimal(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
