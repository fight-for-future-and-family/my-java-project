package com.hoolai.panel.web.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.hoolai.bi.report.model.Types.GameCPAPage;
import com.hoolai.bi.report.model.entity.CostPerSourceDimension;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.model.entity.UserCpaCpsSource;
import com.hoolai.bi.report.service.CostPerSourceDimensionService;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.service.UserCpaCpsSourceService;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.bi.report.vo.CostPerSourceDimensionVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.bi.report.vo.UserCpaCpsSourceVO;
import com.hoolai.manage.model.Groups;
import com.hoolai.manage.model.Platforms;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.PlatformsService;
import com.hoolai.manage.service.UsersService;
import com.hoolai.manage.vo.PlatformsVO;
import com.hoolai.manage.vo.UsersVO;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;
import com.hoolai.panel.web.download.UploadCostPerSourceProcessor;

@Controller
@RequestMapping("/panel_bi/costPer")
public class GameCPAController extends AbstractPanelController{
	
	@Autowired
	private UserCpaCpsSourceService userCpaCpsSourceService;
	
	@Autowired
	private CostPerSourceDimensionService costPerSourceDimensionService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private SourceDailyReportService sourceDailyReportService;
	
	@Autowired
	private PlatformsService platformsService;
	
	@RequestMapping(value = {"/toCPA.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toCPA(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		
		String view = request.getParameter("view");
		GameCPAPage page = GameCPAPage.convertToPage(view);
		boolean isOutSideUser = super.isOutSideUser(request);
		Users users = super.getSessionUsers(request);
		
		switch(page){
		case DIMENSION:
			if(isOutSideUser){
				System.out.println("用户："+users.getId()+"（"+users.getLoginName()+"）访问了不是权限内的网页");
				throw new Exception("权限越界");
			}else{
				return "cpa/dimension.jsp";
			}
		case AUTH_MANAGE:
			if(isOutSideUser){
				System.out.println("用户："+users.getId()+"（"+users.getLoginName()+"）访问了不是权限内的网页");
				throw new Exception("权限越界");
			}else{
				return "cpa/authManage.jsp";
			}
		case CPS:
		case CPA:
		 default:
			 Games games = getSessionGames(request);
			 if(isOutSideUser){
				UserCpaCpsSource userSource = new UserCpaCpsSource();
				userSource.setUserId(users.getId());
				
				CostPerSourceDimension dimension = new CostPerSourceDimension(games.getSnid(), games.getGameid());
				CostPerSourceDimensionVO dimensionVO = new CostPerSourceDimensionVO(dimension);
				dimensionVO.setUserSource(userSource);
				model.addAttribute("dimensionList",costPerSourceDimensionService.getUserSource(dimensionVO));
				super.setSession(request, "game", games);
			 }else{
				SourceDailyReport sourceDailyReport = new SourceDailyReport();
				sourceDailyReport.setSnid(games.getSnid());
				sourceDailyReport.setGameid(games.getGameid());
				
				SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
				List<String> gameSources = sourceDailyReportService.selectGameSources(sourceDailyReportVO);
				
				CostPerSourceDimension temp = new CostPerSourceDimension(games.getSnid(), games.getGameid());
				CostPerSourceDimensionVO dimensionVO = new CostPerSourceDimensionVO(temp);
				List<CostPerSourceDimension> dimensions = costPerSourceDimensionService.getMatch(dimensionVO);
				
				List<CostPerSourceDimension> gameSourceNames =  new ArrayList<CostPerSourceDimension>();
				for(String source:gameSources){
					CostPerSourceDimension dimension = DownLoadCsvProcessor.getDimension(dimensions, source);
					if(dimension == null){
						dimension = new CostPerSourceDimension();
						dimension.setSourceCode(source);
						dimension.setSourceName(source);
						gameSourceNames.add(dimension);
					}else{
						gameSourceNames.add(dimension);
					}
				}
				
				
				model.addAttribute("dimensionList",gameSourceNames);
			 }
			if(page == GameCPAPage.CPA){
				if(isOutSideUser){
					return "cpa/cpa1.jsp";
				}else{
					return "cpa/cpa.jsp";
				}
			}else{
				if(isOutSideUser){
					return "cpa/cps1.jsp";
				}else{
					return "cpa/cps.jsp";
				}
			}
		}
	}
	
	@RequestMapping(value = {"/downloadData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String indicators = request.getParameter("indicators");
		String channel = request.getParameter("channel");
		String view = request.getParameter("view");
		
		if(StringUtils.isEmpty(beginDate)
			|| StringUtils.isEmpty(endDate)
			|| StringUtils.isEmpty(indicators)
			|| StringUtils.isEmpty(channel)
			|| StringUtils.isEmpty(view)
			){
			return null;
		}
		
		if("month".equals(indicators)){
			String[] beginArr = beginDate.split("-");
			int beginYear = Integer.valueOf(beginArr[0]);
			int beginMonth = Integer.valueOf(beginArr[1]);
			
			String[] endArr = endDate.split("-");
			int endYear = Integer.valueOf(endArr[0]);
			int endMonth = Integer.valueOf(endArr[1]);
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, beginYear);
			calendar.set(Calendar.MONTH, beginMonth);
			beginDate = DateUtils.getCurrMonthBeginDate(calendar);
			
			calendar.set(Calendar.YEAR, endYear);
			calendar.set(Calendar.MONTH, endMonth);
			endDate = DateUtils.getCurrMonthEndDate(calendar);
		}
		
		GameCPAPage page = GameCPAPage.convertToPage(view);
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
		
		SourceDailyReport sourceDailyReport = new SourceDailyReport();
		sourceDailyReport.setSnid(games.getSnid());
		sourceDailyReport.setGameid(games.getGameid());
		
		SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setDate(beginDate, endDate);
		sourceDailyReportVO.setQueryType("all");
		
		CostPerSourceDimension temp = new CostPerSourceDimension(games.getSnid(), games.getGameid());
		List<CostPerSourceDimension> dimensions = new ArrayList<CostPerSourceDimension>();
		
		boolean isOutSideUser = isOutSideUser(request);
		if("0".equals(channel) || "-1".equals(channel)){
			CostPerSourceDimensionVO dimensionVO = new CostPerSourceDimensionVO(temp);
			if(isOutSideUser){
				UserCpaCpsSource userSource = new UserCpaCpsSource();
				userSource.setUserId(getSessionUsers(request).getId());
				dimensionVO.setUserSource(userSource);
				dimensions = costPerSourceDimensionService.getUserSource(dimensionVO);
				if(dimensions.size() < 1){
					return null;
				}else{
					String sourceStr = "";
					for(CostPerSourceDimension dimension:dimensions){
						sourceStr += "'"+dimension.getSourceCode()+"',";
					}
					sourceDailyReport.setSource(sourceStr.substring(0, sourceStr.length()-1));
					sourceDailyReportVO.setMultiSource("multi");
				}
			}else{
				dimensions = costPerSourceDimensionService.getMatch(dimensionVO);
				sourceDailyReport.setSource(null);
			}
		}else{
			sourceDailyReport.setSource(channel);
			
			temp.setSourceCode(channel);
			CostPerSourceDimension dimension = costPerSourceDimensionService.getByCode(temp);
			if(dimension!=null){
				dimensions.add(dimension);
			}
		}
		
		if("source".equals(indicators)){
			switch(page){
			case CPA:
				break;
			case CPS:
				List<SourceDailyReport> channelCpss = sourceDailyReportService.selectListBySource(sourceDailyReportVO);
				processor.writeChannelCps(channelCpss,dimensions,isOutSideUser,"cps_channel");
				break;
			}
		}else if("day".equals(indicators)){
			switch(page){
			case CPA:
				break;
			case CPS:
				List<SourceDailyReport> channelCpss = sourceDailyReportService.selectList(sourceDailyReportVO);
				processor.writeChannelCps(channelCpss,dimensions,isOutSideUser,"cps_day");
				break;
			}
		}else if("month".equals(indicators)){
			switch(page){
			case CPA:
				break;
			case CPS:
				List<SourceDailyReport> channelCpss = sourceDailyReportService.selectListByMonth(sourceDailyReportVO);
				processor.writeChannelCps(channelCpss,dimensions,isOutSideUser(request),"cps_month");
				break;
			}
		}
		
		return null;
	}
	
	@RequestMapping(value = {"/getChannelDataList.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getChannelDataList(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret = new HashMap<String, Object>();
		
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String indicators = request.getParameter("indicators");
		String channel = request.getParameter("channel");
		String view = request.getParameter("view");
		
		if(StringUtils.isEmpty(beginDate)
			|| StringUtils.isEmpty(endDate)
			|| StringUtils.isEmpty(indicators)
			|| StringUtils.isEmpty(channel)
			|| StringUtils.isEmpty(view)
			){
			return null;
		}
		
		if("month".equals(indicators)){
			String[] beginArr = beginDate.split("-");
			int beginYear = Integer.valueOf(beginArr[0]);
			int beginMonth = Integer.valueOf(beginArr[1]);
			
			String[] endArr = endDate.split("-");
			int endYear = Integer.valueOf(endArr[0]);
			int endMonth = Integer.valueOf(endArr[1]);
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, beginYear);
			calendar.set(Calendar.MONTH, beginMonth);
			beginDate = DateUtils.getCurrMonthBeginDate(calendar);
			
			calendar.set(Calendar.YEAR, endYear);
			calendar.set(Calendar.MONTH, endMonth);
			endDate = DateUtils.getCurrMonthEndDate(calendar);
		}
		
		GameCPAPage page = GameCPAPage.convertToPage(view);
		
		SourceDailyReport sourceDailyReport = new SourceDailyReport();
		sourceDailyReport.setSnid(games.getSnid());
		sourceDailyReport.setGameid(games.getGameid());
		SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setDate(beginDate, endDate);
		sourceDailyReportVO.setQueryType("limit");
		
		CostPerSourceDimension temp = new CostPerSourceDimension(games.getSnid(), games.getGameid());
		List<CostPerSourceDimension> dimensions = new ArrayList<CostPerSourceDimension>();
		
		boolean isOutSideUser = isOutSideUser(request);
		if("0".equals(channel) || "-1".equals(channel)){
			CostPerSourceDimensionVO dimensionVO = new CostPerSourceDimensionVO(temp);
			if(isOutSideUser){
				UserCpaCpsSource userSource = new UserCpaCpsSource();
				userSource.setUserId(getSessionUsers(request).getId());
				dimensionVO.setUserSource(userSource);
				dimensions = costPerSourceDimensionService.getUserSource(dimensionVO);
				if(dimensions.size() < 1){
					ret.put("recordsTotal", 0);
					ret.put("recordsFiltered", 0);
					ret.put("data", "");
					return ret;
				}else{
					String sourceStr = "";
					for(CostPerSourceDimension dimension:dimensions){
						sourceStr += "'"+dimension.getSourceCode()+"',";
					}
					sourceDailyReport.setSource(sourceStr.substring(0, sourceStr.length()-1));
					sourceDailyReportVO.setMultiSource("multi");
				}
			}else{
				dimensions = costPerSourceDimensionService.getMatch(dimensionVO);
				sourceDailyReport.setSource(null);
			}
		}else{
			sourceDailyReport.setSource(channel);
			
			temp.setSourceCode(channel);
			CostPerSourceDimension dimension = costPerSourceDimensionService.getByCode(temp);
			if(dimension!=null){
				dimensions.add(dimension);
			}
		}
		
		Map<String,Object> pageInfo = super.findPageInfo(request);
		if("source".equals(indicators)){
			switch(page){
			case CPA:
				break;
			case CPS:
				//总记录数
				long recordsTotal_cps_source = sourceDailyReportService.selectBySourceCount(sourceDailyReportVO);
				// 过滤记录数
				sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
				long recordsFiltered_cps_source = sourceDailyReportService.selectBySourceCount(sourceDailyReportVO);
				
				// 分页数据
				sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
				sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
				
				sourceDailyReportVO.setOrderCol(getOrderCol((String)pageInfo.get("orderCol"),indicators));
				sourceDailyReportVO.setOrderType((String)pageInfo.get("orderType"));
				
				ret.put("recordsTotal", recordsTotal_cps_source);
				ret.put("recordsFiltered", recordsFiltered_cps_source);
				
				List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectListBySource(sourceDailyReportVO);
				ret.put("data", exChange(dimensions, sourceDailyReports,isOutSideUser));
				break;
			}
		}else if("day".equals(indicators)){
			switch(page){
			case CPA:
				break;
			case CPS:
				//总记录数
				long recordsTotal_cps_day = sourceDailyReportService.selectCount(sourceDailyReportVO);
				// 过滤记录数
				sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
				long recordsFiltered_cps_day = sourceDailyReportService.selectCount(sourceDailyReportVO);
				
				// 分页数据
				sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
				sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
				
				sourceDailyReportVO.setOrderCol(getOrderCol((String)pageInfo.get("orderCol"),indicators));
				sourceDailyReportVO.setOrderType((String)pageInfo.get("orderType"));
				
				ret.put("recordsTotal", recordsTotal_cps_day);
				ret.put("recordsFiltered", recordsFiltered_cps_day);
				List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectList(sourceDailyReportVO);
				ret.put("data", exChange(dimensions, sourceDailyReports,isOutSideUser));
				break;
			}
		}else if("month".equals(indicators)){
			switch(page){
			case CPA:
				break;
			case CPS:
				//总记录数
				long recordsTotal_cps_month = sourceDailyReportService.selectByMonthCount(sourceDailyReportVO);
				// 过滤记录数
				sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
				long recordsFiltered_cps_month = sourceDailyReportService.selectByMonthCount(sourceDailyReportVO);
				
				// 分页数据
				sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
				sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
				
				sourceDailyReportVO.setOrderCol(getOrderCol((String)pageInfo.get("orderCol"),indicators));
				sourceDailyReportVO.setOrderType((String)pageInfo.get("orderType"));
				
				ret.put("recordsTotal", recordsTotal_cps_month);
				ret.put("recordsFiltered", recordsFiltered_cps_month);
				List<SourceDailyReport> sourceDailyReports = sourceDailyReportService.selectListByMonth(sourceDailyReportVO);
				ret.put("data", exChange(dimensions, sourceDailyReports,isOutSideUser));
				break;
			}
		}
		
		if(!"month".equals(indicators)){
			setSessionWeekDate(request, beginDate, endDate);
		}
		session.setAttribute("game", games);
		return ret;
	}

	private List<SourceDailyReportVO> exChange(
			List<CostPerSourceDimension> dimensions,
			List<SourceDailyReport> sourceDailyReports,
			boolean isOutSideUser) {
		List<SourceDailyReportVO> sourceDailyReportVOs = new ArrayList<SourceDailyReportVO>();
		for(SourceDailyReport dailyReport:sourceDailyReports){
			SourceDailyReportVO dailyReportVO = null;
			if(isOutSideUser){//如果是外部人员将真实数据隐藏
				dailyReportVO = new SourceDailyReportVO(new SourceDailyReport(dailyReport.getSnid(), dailyReport.getGameid()));
				dailyReportVO.getEntity().setSource(dailyReport.getSource());
				dailyReportVO.getEntity().setDay(dailyReport.getDay());
			}else{
				dailyReportVO = new SourceDailyReportVO(dailyReport);
			}
			CostPerSourceDimension dimension = DownLoadCsvProcessor.getDimension(dimensions, dailyReport.getSource());
			if(dimension != null){
				dailyReportVO.setSourceName(dimension.getSourceName());
				dailyReportVO.setPu(Long.valueOf(Math.round(dailyReport.getPu()*dimension.getPayUserRate())).intValue());
				dailyReportVO.setPaymentCnt(Long.valueOf(Math.round(dailyReport.getPaymentCnt()*dimension.getPayTimesRate())).intValue());
				dailyReportVO.setPaymentAmount(dailyReport.getPaymentAmount()*dimension.getPayRate());
			}else{
				dailyReportVO.setPu(dailyReport.getPu());
				dailyReportVO.setPaymentCnt(dailyReport.getPaymentCnt());
				dailyReportVO.setPaymentAmount(dailyReport.getPaymentAmount());
			}
			sourceDailyReportVOs.add(dailyReportVO);
		}
		return sourceDailyReportVOs;
	}

	private String getOrderCol(String orderCol,String queryType) {
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else if("source".equals(queryType)){
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return null;
			case 1:
				return null;
			case 2:
				return "pu";
			case 3:
				return "payment_cnt";
			case 4:
				return "payment_amount";
			case 5:
				return "pu";
			case 6:
				return "payment_cnt";
			case 7:
				return "payment_amount";
			default:
				return "pu";
			}
		}else{
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return "day";
			case 1:
				return null;
			case 2:
				return null;
			case 3:
				return "pu";
			case 4:
				return "payment_cnt";
			case 5:
				return "payment_amount";
			case 6:
				return "pu";
			case 7:
				return "payment_cnt";
			case 8:
				return "payment_amount";
			default:
				return "day";
			}
		}
	}

	@RequestMapping(value = "/download_data_templete.ac", method = {RequestMethod.POST })
	public String downloadDataTemplete(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)throws Exception{
		
		Games games = getSessionGames(request);
		
		String fileName = "cost_per_sale_template.xlsx";
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream strem = null;
		strem = loader.getResourceAsStream("templates/"+fileName);
		Workbook wb = new XSSFWorkbook(strem);
		Sheet sheet = wb.getSheetAt(0);
		
		fileName = "source_template.xlsx";
		
		CostPerSourceDimension dimension = new CostPerSourceDimension(games.getSnid(), games.getGameid());
		CostPerSourceDimensionVO dimensionVO = new CostPerSourceDimensionVO(dimension);
		List<CostPerSourceDimension> dimensions = costPerSourceDimensionService.getMatch(dimensionVO);
		
		List<Platforms> platforms = platformsService.getMatch(new PlatformsVO(new Platforms()));
		
		  CellStyle cellStyleYellow = wb.createCellStyle();
		  cellStyleYellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
		  cellStyleYellow.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		  cellStyleYellow.setAlignment(HSSFCellStyle.ALIGN_CENTER);// //居中显示
		  cellStyleYellow.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		  cellStyleYellow.setBorderTop(HSSFCellStyle.BORDER_THIN);
		  cellStyleYellow.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		  cellStyleYellow.setBorderRight(HSSFCellStyle.BORDER_THIN);
		  
		  CellStyle cellStyleBlue = wb.createCellStyle();
		  cellStyleBlue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
		  cellStyleBlue.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		  cellStyleBlue.setAlignment(HSSFCellStyle.ALIGN_CENTER);// //居中显示
		  cellStyleBlue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		  cellStyleBlue.setBorderTop(HSSFCellStyle.BORDER_THIN);
		  cellStyleBlue.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		  cellStyleBlue.setBorderRight(HSSFCellStyle.BORDER_THIN);
		  
		int index = 1;
		for(Platforms plat:platforms){
			Row row = sheet.createRow(index++);
			Cell cell = row.createCell(0);
			cell.setCellStyle(cellStyleYellow);
			cell.setCellValue(plat.getCode());
			
			cell = row.createCell(1);
			cell.setCellStyle(cellStyleYellow);
			cell.setCellValue(plat.getName());
			
			cell = row.createCell(2);
			cell.setCellStyle(cellStyleYellow);
			cell.setCellValue(100);
			
			cell = row.createCell(3);
			cell.setCellStyle(cellStyleYellow);
			cell.setCellValue(100);
			
			cell = row.createCell(4);
			cell.setCellStyle(cellStyleYellow);
			cell.setCellValue(100);
		}
		
		for(CostPerSourceDimension dim:dimensions){
			Row row = sheet.createRow(index++);
			Cell cell = row.createCell(0);
			cell.setCellStyle(cellStyleBlue);
			cell.setCellValue(dim.getSourceCode());
			
			cell = row.createCell(1);
			cell.setCellStyle(cellStyleBlue);
			cell.setCellValue(dim.getSourceName());
			
			cell = row.createCell(2);
			cell.setCellStyle(cellStyleBlue);
			cell.setCellValue(dim.getPayUserRate()*100);
			
			cell = row.createCell(3);
			cell.setCellStyle(cellStyleBlue);
			cell.setCellValue(dim.getPayTimesRate()*100);
			
			cell = row.createCell(4);
			cell.setCellStyle(cellStyleBlue);
			cell.setCellValue(dim.getPayRate()*100);
		}
		
		OutputStream out;
		try {
			out = response.getOutputStream();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			wb.write(out);
			wb.close();
			out.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/upload_data.ac", method = {RequestMethod.POST })
	public Map<String, Object> uploadFile(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam MultipartFile uploadFile,Model model) throws Exception {
		
		Games games = this.getSessionGames(request);
		String filePath = request.getParameter("filePath");
		
		UploadCostPerSourceProcessor processor = new UploadCostPerSourceProcessor(filePath,uploadFile,games);
		
		processor.readBook();
		
		List<CostPerSourceDimension> dimensions = processor.getCostPerSourceDimensions();
		
		for(CostPerSourceDimension dimension:dimensions){
			CostPerSourceDimension temp = costPerSourceDimensionService.getByCode(dimension);
			if(temp != null){
				dimension.setId(temp.getId());
				costPerSourceDimensionService.modifyEntitySelective(dimension);
			}else{
				dimension.setUpdateUserId(getSessionUsers(request).getId());
				costPerSourceDimensionService.saveEntity(dimension);
			}
		}
		
		session.setAttribute("isCostPerUpLoadEnd", "1");
		
		return null;
	}
	
	
	@RequestMapping(value = {"/getUpLoadEndMark.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getUpLoadEndMark(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		Map<String,Object> ret = new HashMap<String, Object>();
		
		if(session.getAttribute("isCostPerUpLoadEnd") != null
				&& session.getAttribute("isCostPerUpLoadEnd").equals("1")){
			ret.put("isCostPerUpLoadEnd", "1");
			session.removeAttribute("isCostPerUpLoadEnd");
		}else{
			ret.put("isCostPerUpLoadEnd", "0");
		}
		return ret;
	}
	
	@RequestMapping(value = {"/queryDimensionData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> queryDimensionData(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = getSessionGames(request);
		
		Map<String,Object> pageInfo = this.findPageInfo(request);
		
		CostPerSourceDimension dimension = new CostPerSourceDimension(games.getSnid(), games.getGameid());
		CostPerSourceDimensionVO dimensionVO = new CostPerSourceDimensionVO(dimension);
		
		//总记录数
		long recordsTotal = costPerSourceDimensionService.selectCount(dimensionVO);
		// 过滤记录数
		dimensionVO.setSearchValue((String) pageInfo.get("searchValue"));
		long recordsFiltered = costPerSourceDimensionService.selectCount(dimensionVO);
		
		// 分页数据
		dimensionVO.setOffset((Long) pageInfo.get("start"));
		dimensionVO.setRows((Long) pageInfo.get("length"));
		
		dimensionVO.setOrderCol((String)pageInfo.get("orderCol"));
		dimensionVO.setOrderType((String)pageInfo.get("orderType"));
		
		ret.put("recordsTotal", recordsTotal);
		ret.put("recordsFiltered", recordsFiltered);
		ret.put("data", costPerSourceDimensionService.selectList(dimensionVO));
		
		return ret;
	}
	
	@RequestMapping(value = {"/saveDimensionData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> saveDimensionData(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String code = request.getParameter("sourceCode");
		String name = request.getParameter("sourceName");
		String payRate = request.getParameter("payRate");
		String payUserRate = request.getParameter("payUserRate");
		String payTimesRate = request.getParameter("payTimesRate");
		
		if(StringUtils.isEmpty(code) 
			|| StringUtils.isEmpty(name)
			|| StringUtils.isEmpty(payRate)
			|| StringUtils.isEmpty(payUserRate)
			|| StringUtils.isEmpty(payTimesRate)
			){
			ret.put("msg", "1");//内容不能为空，请重新输入！
			return ret;
		}
		
		CostPerSourceDimension dimension = new CostPerSourceDimension(games.getSnid(), games.getGameid());
		dimension.setSourceCode(code);
		dimension.setSourceName(name);
		dimension.setPayRate(Double.valueOf(payRate));
		dimension.setPayUserRate(Double.valueOf(payUserRate));
		dimension.setPayTimesRate(Double.valueOf(payTimesRate));
		dimension.setUpdateUserId(getSessionUsers(request).getId());
		
		CostPerSourceDimension temp = costPerSourceDimensionService.getByCode(dimension);
		if(temp == null){
			costPerSourceDimensionService.saveEntity(dimension);
		}else{
			dimension.setId(temp.getId());
			costPerSourceDimensionService.modifyEntity(dimension);
		}
		
		ret.put("msg", "2");// 修改成功
		return ret;
	}
	
	@RequestMapping(value = {"/delDimensionData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delDimensionData(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		long remove = costPerSourceDimensionService.removeById(Long.valueOf(request.getParameter("id")));
		ret.put("msg", remove > 0 ? "2" : "1");//删除成功
		return ret;
	}
	
	@RequestMapping(value = {"/queryUserList.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> queryUserList(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = getSessionGames(request);
		
		String loginName = request.getParameter("loginName");
		String email = request.getParameter("email");
		
		UsersVO usersVO = new UsersVO(new Users());
		usersVO.setGamesId(games.getId());
		Groups groups = new Groups();
		groups.setId(Groups.OUTSIDERS_GROUP);
		usersVO.setGroups(groups);
		
		if(!StringUtils.isEmpty(loginName)){
			usersVO.getEntity().setLoginName(loginName);
		}
		if(!StringUtils.isEmpty(email)){
			usersVO.getEntity().setEmail(email);
		}
		
		List<Users> userList =  usersService.selectList4Source(usersVO);
		
		ret.put("userList", userList);
		return ret;
	}
	
	@RequestMapping(value = {"/querySource.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> querySource(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String userId = request.getParameter("userid");
		if(StringUtils.isEmpty(userId)){
			ret.put("msg", 1);
			return ret;
		}
		
		Games games = getSessionGames(request);
		UserCpaCpsSource userSource = new UserCpaCpsSource();
		userSource.setUserId(Long.valueOf(userId));
		
		CostPerSourceDimension dimension = new CostPerSourceDimension(games.getSnid(), games.getGameid());
		CostPerSourceDimensionVO dimensionVO = new CostPerSourceDimensionVO(dimension);
		dimensionVO.setUserSource(userSource);
		
		List<CostPerSourceDimension> hadDimensions = costPerSourceDimensionService.getUserSource(dimensionVO);
		dimensionVO.setIsHad(1);
		List<CostPerSourceDimension> notHadDimensions = costPerSourceDimensionService.getUserSource(dimensionVO);
		
		ret.put("hadDimensions", hadDimensions);
		ret.put("notHadDimensions", notHadDimensions);
		return ret;
	}
	
	@RequestMapping(value = {"/authManager.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> authManager(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String sourceop = request.getParameter("sourceop");
		String userId = request.getParameter("userid");
		if(StringUtils.isEmpty(userId)
		|| StringUtils.isEmpty(sourceop)){
			ret.put("msg", 1);
			return ret;
		}
		
		UserCpaCpsSource userSource = new UserCpaCpsSource();
		userSource.setUserId(Long.valueOf(userId));
		UserCpaCpsSourceVO userSourceVO = new UserCpaCpsSourceVO(userSource);
		
		Map<String,String> map = new HashMap<String, String>();
		String[] sourceops = sourceop.split(",");
		for(String ops:sourceops){
			String[] temp = ops.split(":");
			map.put(temp[0], temp[1]);
		}
		
		Users users = getSessionUsers(request);
		Set<String> keys = map.keySet();
		for(String key:keys){
			Long sourceId = Long.valueOf(key);
			if("add".equals(map.get(key))){
				userSource.setSourceId(sourceId);
				UserCpaCpsSource entity = userCpaCpsSourceService.selectBySource(userSourceVO);
				if(entity == null){
					userSource.setAllotUserId(users.getId());
					userSource.setAllotUserName(users.getLoginName());
					userCpaCpsSourceService.saveEntity(userSource);
				}
			}else{
				userSourceVO.getEntity().setSourceId(sourceId);
				userCpaCpsSourceService.removeAuth(userSourceVO);
			}
		}
		ret.put("msg", 2);
		return ret;
	}
	
}

