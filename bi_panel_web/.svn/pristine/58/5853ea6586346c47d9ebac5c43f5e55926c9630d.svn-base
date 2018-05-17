package com.hoolai.panel.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.ConvertUtils;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.Types.GameCPAPage;
import com.hoolai.bi.report.model.entity.CpaSourceDimension;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.SourceCpaDailyReport;
import com.hoolai.bi.report.model.entity.UserCpaCpsSource;
import com.hoolai.bi.report.service.CpaSourceDimensionService;
import com.hoolai.bi.report.service.SourceCpaDailyReportService;
import com.hoolai.bi.report.service.UserCpaCpsSourceService;
import com.hoolai.bi.report.vo.CpaSourceDimensionVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.SourceCpaDailyReportVO;
import com.hoolai.bi.report.vo.UserCpaCpsSourceVO;
import com.hoolai.manage.model.Groups;
import com.hoolai.manage.model.Platforms;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.PlatformsService;
import com.hoolai.manage.service.UsersService;
import com.hoolai.manage.vo.PlatformsVO;
import com.hoolai.manage.vo.UsersVO;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;
import com.hoolai.panel.web.processor.PrestoJdbcTemplate;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/panel_bi/cpaCreative")
public class GameCPACreativeController extends AbstractPanelController{
	
	@Autowired
	private UserCpaCpsSourceService userCpaCpsSourceService;
	
	@Autowired
	private CpaSourceDimensionService cpaSourceDimensionService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private SourceCpaDailyReportService sourceCpaDailyReportService;
	
	@Autowired
	private PlatformsService platformsService;
	
	@Autowired
	@Qualifier("sourceCpaConfiguration")
	private Configuration sourceCpaConfiguration;
	
	@RequestMapping(value = {"/toCPACreative.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toCPA(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {

		String view = request.getParameter("view");
		GameCPAPage page = GameCPAPage.convertToPage(view);
		boolean isOutSideUser = super.isOutSideUser(request);
		Users users = super.getSessionUsers(request);
		Games games = this.getSessionGames(request);

		switch (page) {
		case DIMENSION:
			if(isOutSideUser){
				System.out.println("用户："+users.getId()+"（"+users.getLoginName()+"）访问了不是权限内的网页");
				throw new Exception("权限越界");
			}else{
				return "cpaCreative/dimensionCreative.jsp";
			}
		case AUTH_MANAGE:
			if (isOutSideUser) {
				System.out.println("用户：" + users.getId() + "（"
						+ users.getLoginName() + "）访问了不是权限内的网页");
				throw new Exception("权限越界");
			} else {
				return "cpaCreative/authManageCreative.jsp";
			}
		case CPA:
		default:
//			Games games = getSessionGames(request);
			if (isOutSideUser) {
				UserCpaCpsSource userSource = new UserCpaCpsSource();
				userSource.setUserId(users.getId());

				CpaSourceDimension dimension = new CpaSourceDimension(
						games.getSnid(), games.getGameid());
				CpaSourceDimensionVO dimensionVO = new CpaSourceDimensionVO(
						dimension);
				dimensionVO.setUserSource(userSource);
				model.addAttribute("dimensionList",cpaSourceDimensionService.getUserSource(dimensionVO));
				super.setSession(request, "game", games);
			} else {
				SourceCpaDailyReport sourceDailyReport = new SourceCpaDailyReport();
				sourceDailyReport.setSnid(games.getSnid());
				sourceDailyReport.setGameid(games.getGameid());

				SourceCpaDailyReportVO sourceDailyReportVO = new SourceCpaDailyReportVO(
						sourceDailyReport);
				List<String> gameSources = sourceCpaDailyReportService.selectGameSources(sourceDailyReportVO);

				CpaSourceDimension temp = new CpaSourceDimension(games.getSnid(), games.getGameid());
				CpaSourceDimensionVO dimensionVO = new CpaSourceDimensionVO(temp);
				List<CpaSourceDimension> dimensions = cpaSourceDimensionService.getMatch(dimensionVO);

				List<CpaSourceDimension> gameSourceNames = new ArrayList<CpaSourceDimension>();
				for (String source : gameSources) {
					CpaSourceDimension dimension = DownLoadCsvProcessor.getCpaDimension(dimensions, source);
					if (dimension == null) {
						dimension = new CpaSourceDimension();
						dimension.setSourceCode(source);
						dimension.setSourceName(source);
						gameSourceNames.add(dimension);
					} else {
						gameSourceNames.add(dimension);
					}
				}

				model.addAttribute("dimensionList", gameSourceNames);
			}
			if (isOutSideUser) {
				return "cpaCreative/cpaCreative1.jsp";
			} else {
				return "cpaCreative/cpaCreative.jsp";
			}
		}
	}
	
	@RequestMapping(value = {"/downloadData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String channel = request.getParameter("channel");
		String view = request.getParameter("view");
		
		if(StringUtils.isEmpty(beginDate)
			|| StringUtils.isEmpty(endDate)
			|| StringUtils.isEmpty(channel)
			|| StringUtils.isEmpty(view)
			){
			return null;
		}
		
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
		
		SourceCpaDailyReport sourceDailyReport = new SourceCpaDailyReport();
		sourceDailyReport.setSnid(games.getSnid());
		sourceDailyReport.setGameid(games.getGameid());
		
		SourceCpaDailyReportVO sourceDailyReportVO = new SourceCpaDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setEndDate(endDate);
		sourceDailyReportVO.setQueryType("all");
		
		CpaSourceDimension temp = new CpaSourceDimension(games.getSnid(), games.getGameid());
		List<CpaSourceDimension> dimensions = new ArrayList<CpaSourceDimension>();
		
		boolean isOutSideUser = isOutSideUser(request);
		if("0".equals(channel) || "-1".equals(channel)){
			CpaSourceDimensionVO dimensionVO = new CpaSourceDimensionVO(temp);
			if(isOutSideUser){
				UserCpaCpsSource userSource = new UserCpaCpsSource();
				userSource.setUserId(getSessionUsers(request).getId());
				dimensionVO.setUserSource(userSource);
				dimensions = cpaSourceDimensionService.getUserSource(dimensionVO);
				if(dimensions.size() < 1){
					return null;
				}else{
					String sourceStr = "";
					for(CpaSourceDimension dimension:dimensions){
						sourceStr += "'"+dimension.getSourceCode()+"',";
					}
					sourceDailyReport.setSource(sourceStr.substring(0, sourceStr.length()-1));
					sourceDailyReportVO.setMultiSource("multi");
				}
			}else{
				dimensions = cpaSourceDimensionService.getMatch(dimensionVO);
				sourceDailyReport.setSource(null);
			}
		}else{
			sourceDailyReport.setSource(channel);
			
			temp.setSourceCode(channel);
			CpaSourceDimension dimension = cpaSourceDimensionService.getByCode(temp);
			if(dimension!=null){
				dimensions.add(dimension);
			}
		}
		
		List<SourceCpaDailyReport> channelCpss = sourceCpaDailyReportService.selectListBySource(sourceDailyReportVO);
		processor.writeChannelCpa(channelCpss,dimensions,isOutSideUser,"cpa_channel");
		
		return null;
	}
	
	
	
	@RequestMapping(value = {"/delChannelCreativeDataList.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delChannelCreativeDataList(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String endDate = request.getParameter("endTime");
		Games games = this.getSessionGames(request);
		SourceCpaDailyReport sourceCpaDailyReport = new SourceCpaDailyReport();
		sourceCpaDailyReport.setSnid(games.getSnid());
		sourceCpaDailyReport.setGameid(games.getGameid());
		sourceCpaDailyReport.setDay(endDate);
//		Calendar cl = Calendar.getInstance();
//		cl.add(Calendar.DAY_OF_MONTH, -1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		sourceCpaDailyReport.setCreateTime(sdf.format(cl.getTime()));
		SourceCpaDailyReportVO sourceDailyReportVO = new SourceCpaDailyReportVO(sourceCpaDailyReport);
		sourceDailyReportVO.setEndDate(endDate);
		sourceCpaDailyReportService.delSourceDailyReportByDay(sourceDailyReportVO);
		
		return ret;
	}
	
	@RequestMapping(value = {"/getChannelCreativeDataList.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getChannelCreativeDataList(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret = new HashMap<String, Object>();
		
		Games games = this.getSessionGames(request);
		
		String endDate = request.getParameter("endTime");
		String channel = request.getParameter("channel");
		String view = request.getParameter("view");
		
		if(StringUtils.isEmpty(endDate)
			|| StringUtils.isEmpty(channel)
			|| StringUtils.isEmpty(view)
			){
			return null;
		}
		String systemType = "";
		if("0".equals(games.getSystemType().toString())){
			systemType = "ios";
		}else if("1".equals(games.getSystemType().toString())){
			systemType = "android";
		}
		SourceCpaDailyReport sourceDailyReport = new SourceCpaDailyReport();
		sourceDailyReport.setSnid(games.getSnid());
		sourceDailyReport.setGameid(games.getGameid());
		sourceDailyReport.setDay(endDate);
		SourceCpaDailyReportVO sourceDailyReportVO = new SourceCpaDailyReportVO(sourceDailyReport);
		sourceDailyReportVO.setEndDate(endDate);
		sourceDailyIsNull(sourceDailyReportVO, systemType);
		sourceDailyReportVO.setQueryType("limit");
		
		CpaSourceDimension temp = new CpaSourceDimension(games.getSnid(), games.getGameid());
		List<CpaSourceDimension> dimensions = new ArrayList<CpaSourceDimension>();
		
		boolean isOutSideUser = isOutSideUser(request);
		if("0".equals(channel) || "-1".equals(channel)){
			CpaSourceDimensionVO dimensionVO = new CpaSourceDimensionVO(temp);
			if(isOutSideUser){
				UserCpaCpsSource userSource = new UserCpaCpsSource();
				userSource.setUserId(getSessionUsers(request).getId());
				dimensionVO.setUserSource(userSource);
				dimensions = cpaSourceDimensionService.getUserSource(dimensionVO);
				if(dimensions.size() < 1){
					ret.put("recordsTotal", 0);
					ret.put("recordsFiltered", 0);
					ret.put("data", "");
					return ret;
				}else{
					String sourceStr = "";
					for(CpaSourceDimension dimension:dimensions){
						sourceStr += "'"+dimension.getSourceCode()+"',";
					}
					sourceDailyReport.setSource(sourceStr.substring(0, sourceStr.length()-1));
					sourceDailyReportVO.setMultiSource("multi");
				}
			}else{
				dimensions = cpaSourceDimensionService.getMatch(dimensionVO);
				sourceDailyReport.setSource(null);
			}
		}else{
			sourceDailyReport.setSource(channel);
			
			temp.setSourceCode(channel);
			CpaSourceDimension dimension = cpaSourceDimensionService.getByCode(temp);
			if(dimension!=null){
				dimensions.add(dimension);
			}
		}
		
		Map<String,Object> pageInfo = super.findPageInfo(request);
		//总记录数
		long recordsTotal_cps_source = sourceCpaDailyReportService.selectBySourceCount(sourceDailyReportVO);
		// 过滤记录数
		sourceDailyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
		long recordsFiltered_cps_source = sourceCpaDailyReportService.selectBySourceCount(sourceDailyReportVO);
		
		// 分页数据
		sourceDailyReportVO.setOffset((Long) pageInfo.get("start"));
		sourceDailyReportVO.setRows((Long) pageInfo.get("length"));
		
		sourceDailyReportVO.setOrderCol(getOrderCol((String)pageInfo.get("orderCol"),"source"));
		sourceDailyReportVO.setOrderType((String)pageInfo.get("orderType"));
		
		ret.put("recordsTotal", recordsTotal_cps_source);
		ret.put("recordsFiltered", recordsFiltered_cps_source);
		
		List<SourceCpaDailyReport> sourceDailyReports = sourceCpaDailyReportService.selectListBySource(sourceDailyReportVO);
		ret.put("data", exChange(dimensions, sourceDailyReports,isOutSideUser));
		
		setSessionWeekDate(request, null, endDate);
		session.setAttribute("game", games);
		return ret;
	}
	
	private void sourceDailyIsNull(SourceCpaDailyReportVO sourceCpaDailyReportVO, String systemType) throws IOException{
		long sum = sourceCpaDailyReportService.selectBySourceCount(sourceCpaDailyReportVO);
		if(sum==0){
			String sql = getSourceDailySql(sourceCpaDailyReportVO.getEntity(), systemType);
			PrestoJdbcTemplate prestoJdbcTemplate = new PrestoJdbcTemplate();
			List<Object> list = new ArrayList<Object>();
			List<SourceCpaDailyReport> resultList = new ArrayList<SourceCpaDailyReport>();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			try {
				ResultSet rs = prestoJdbcTemplate.exec(sql);
				resultList = columnToField(rs, SourceCpaDailyReport.class);
		        prestoJdbcTemplate.destory();
		        int num = sourceCpaDailyReportService.saveCpaSourceDailysReport(resultList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				prestoJdbcTemplate.destory();
			}
		}
	}
	
	private String getSourceDailySql(SourceCpaDailyReport sourceCpaDailyReport,String systemType){
		Configuration configuration = new Configuration();
		Template template= null;
		StringWriter sw = new StringWriter();
		try {
			template = sourceCpaConfiguration.getTemplate(systemType+".ftl");
			template.process(sourceCpaDailyReport, sw);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("get sql template error!"+e.getMessage()+" path:"+"templates/ios.ftl");
		}
		return sw.toString();
	}
	
	public static List<SourceCpaDailyReport> columnToField(ResultSet rs,Class clazz) throws Exception{
        Object obj=clazz.newInstance();
        Field[] fields=clazz.getDeclaredFields();
        SourceCpaDailyReport sourceCpaDailyReport = null;
        List<SourceCpaDailyReport> list=new ArrayList<SourceCpaDailyReport>();
        while(rs.next()){
//            for(Field f:fields){
//                String name=f.getName();
//                Class type=f.getType();
//                Method method=null;
//                try {
//                    method=clazz.getMethod("set"+name.replaceFirst(name.substring(0, 1),
//                            name.substring(0, 1).toUpperCase()), type);
//                    Object qqq=ConvertUtils.convert(rs.getString(name),type);
//                    System.out.println(qqq);
//                    method.invoke(obj,ConvertUtils.convert(rs.getString(name),type));
//                } catch (Exception e) {
////                    e.printStackTrace();
//                }
//            }
            sourceCpaDailyReport =  new SourceCpaDailyReport();
            sourceCpaDailyReport.setSnid(rs.getObject("snid").toString());
            sourceCpaDailyReport.setGameid(rs.getObject("gameid").toString());
            sourceCpaDailyReport.setSource(rs.getObject("source").toString());
            sourceCpaDailyReport.setDay(rs.getObject("day").toString());
            sourceCpaDailyReport.setNewEquip(Integer.valueOf(rs.getObject("newEquip").toString()));
            sourceCpaDailyReport.setDnu(Integer.valueOf(rs.getObject("dnu").toString()));
            list.add(sourceCpaDailyReport);
        }
        return list;
    }
	
	private List<SourceCpaDailyReportVO> exChange(List<CpaSourceDimension> dimensions,
			List<SourceCpaDailyReport> sourceDailyReports,boolean isOutSideUser) {
		List<SourceCpaDailyReportVO> sourceDailyReportVOs = new ArrayList<SourceCpaDailyReportVO>();
		for(SourceCpaDailyReport dailyReport:sourceDailyReports){
			SourceCpaDailyReportVO dailyReportVO = null;
			if(isOutSideUser){//如果是外部人员将真实数据隐藏
				dailyReportVO = new SourceCpaDailyReportVO(new SourceCpaDailyReport(dailyReport.getSnid(), dailyReport.getGameid()));
				dailyReportVO.getEntity().setSource(dailyReport.getSource());
				dailyReportVO.getEntity().setDay(dailyReport.getDay());
				dailyReportVO.getEntity().setDnu(dailyReport.getDnu());
				dailyReportVO.getEntity().setNewEquip(dailyReport.getNewEquip());
			}else{
				dailyReportVO = new SourceCpaDailyReportVO(dailyReport);
			}
			CpaSourceDimension dimension = DownLoadCsvProcessor.getCpaDimension(dimensions, dailyReport.getSource());
			if(dimension != null){
				dailyReportVO.setSourceName(dimension.getSourceName());
//				dailyReportVO.setPu(Long.valueOf(Math.round(dailyReport.getPu()*dimension.getPayUserRate())).intValue());
//				dailyReportVO.setPaymentCnt(Long.valueOf(Math.round(dailyReport.getPaymentCnt()*dimension.getPayTimesRate())).intValue());
//				dailyReportVO.setPaymentAmount(dailyReport.getPaymentAmount()*dimension.getPayRate());
			}else{
//				dailyReportVO.setPu(dailyReport.getPu());
//				dailyReportVO.setPaymentCnt(dailyReport.getPaymentCnt());
//				dailyReportVO.setPaymentAmount(dailyReport.getPaymentAmount());
			}
			sourceDailyReportVOs.add(dailyReportVO);
		}
		return sourceDailyReportVOs;
	}

	private String getOrderCol(String orderCol,String queryType) {
		if("source".equals(queryType)){
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return null;
			case 1:
				return null;
			case 2:
				return "dnu";
			case 3:
				return "new_equip";
			default:
				return "dnu";
			}
		}else{
			return null;
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
		
		CpaSourceDimension dimension = new CpaSourceDimension(games.getSnid(), games.getGameid());
		CpaSourceDimensionVO dimensionVO = new CpaSourceDimensionVO(dimension);
		List<CpaSourceDimension> dimensions = cpaSourceDimensionService.getMatch(dimensionVO);
		
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
		
		for(CpaSourceDimension dim:dimensions){
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
	
	@RequestMapping(value = {"/queryDimensionCreativeData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> queryDimensionCreativeData(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = getSessionGames(request);
		
		Map<String,Object> pageInfo = this.findPageInfo(request);
		
		CpaSourceDimension dimension = new CpaSourceDimension(games.getSnid(), games.getGameid());
		CpaSourceDimensionVO dimensionVO = new CpaSourceDimensionVO(dimension);
		
		//总记录数
		long recordsTotal = cpaSourceDimensionService.selectCount(dimensionVO);
		// 过滤记录数
		dimensionVO.setSearchValue((String) pageInfo.get("searchValue"));
		long recordsFiltered = cpaSourceDimensionService.selectCount(dimensionVO);
		
		// 分页数据
		dimensionVO.setOffset((Long) pageInfo.get("start"));
		dimensionVO.setRows((Long) pageInfo.get("length"));
		
		dimensionVO.setOrderCol((String)pageInfo.get("orderCol"));
		dimensionVO.setOrderType((String)pageInfo.get("orderType"));
		
		ret.put("recordsTotal", recordsTotal);
		ret.put("recordsFiltered", recordsFiltered);
		ret.put("data", cpaSourceDimensionService.selectList(dimensionVO));
		
		return ret;
	}
	
	@RequestMapping(value = {"/saveDimensionCreativeData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> saveDimensionCreativeData(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String code = request.getParameter("sourceCode");
		String name = request.getParameter("sourceName");
//		String payRate = request.getParameter("payRate");
//		String payUserRate = request.getParameter("payUserRate");
//		String payTimesRate = request.getParameter("payTimesRate");
		
		if(StringUtils.isEmpty(code) 
			|| StringUtils.isEmpty(name)
//			|| StringUtils.isEmpty(payRate)
//			|| StringUtils.isEmpty(payUserRate)
//			|| StringUtils.isEmpty(payTimesRate)
			){
			ret.put("msg", "1");//内容不能为空，请重新输入！
			return ret;
		}
		
		CpaSourceDimension dimension = new CpaSourceDimension(games.getSnid(), games.getGameid());
		dimension.setSourceCode(code);
		dimension.setSourceName(name);
//		dimension.setPayRate(Double.valueOf(payRate));
//		dimension.setPayUserRate(Double.valueOf(payUserRate));
//		dimension.setPayTimesRate(Double.valueOf(payTimesRate));
		dimension.setUpdateUserId(getSessionUsers(request).getId());
		
		CpaSourceDimension temp = cpaSourceDimensionService.getByCode(dimension);
		if(temp == null){
			cpaSourceDimensionService.saveEntity(dimension);
		}else{
			dimension.setId(temp.getId());
			cpaSourceDimensionService.modifyEntity(dimension);
		}
		
		ret.put("msg", "2");// 修改成功
		return ret;
	}
	
	@RequestMapping(value = {"/delDimensionCreativeData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delDimensionCreativeData(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		long remove = cpaSourceDimensionService.removeById(Long.valueOf(request.getParameter("id")));
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
		
//		CostPerSourceDimension dimension = new CostPerSourceDimension(games.getSnid(), games.getGameid());
		CpaSourceDimension dimension = new CpaSourceDimension(games.getSnid(), games.getGameid());
//		CostPerSourceDimensionVO dimensionVO = new CostPerSourceDimensionVO(dimension);
		CpaSourceDimensionVO dimensionVO = new CpaSourceDimensionVO(dimension);
		dimensionVO.setUserSource(userSource);
		
//		List<CostPerSourceDimension> hadDimensions = cpaSourceDimensionService.getUserSource(dimensionVO);
		List<CpaSourceDimension> hadDimensions = cpaSourceDimensionService.getUserSource(dimensionVO);
		dimensionVO.setIsHad(1);
		List<CpaSourceDimension> notHadDimensions = cpaSourceDimensionService.getUserSource(dimensionVO);
		
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
				UserCpaCpsSource entity = userCpaCpsSourceService.selectByCpaSource(userSourceVO);
				if(entity == null){
					userSource.setAllotUserId(users.getId());
					userSource.setAllotUserName(users.getLoginName());
					userCpaCpsSourceService.saveCpaEntity(userSource);
				}
			}else{
				userSourceVO.getEntity().setSourceId(sourceId);
				userCpaCpsSourceService.removeCpaAuth(userSourceVO);
			}
		}
		ret.put("msg", 2);
		return ret;
	}
	
}

