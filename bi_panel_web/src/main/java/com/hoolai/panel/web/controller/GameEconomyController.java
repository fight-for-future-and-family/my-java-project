package com.hoolai.panel.web.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
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

import com.hoolai.bi.report.model.Types.GameEconomyPage;
import com.hoolai.bi.report.model.Types.GameRoleAnalysisChannel;
import com.hoolai.bi.report.model.entity.ClientEconomyNewReport;
import com.hoolai.bi.report.model.entity.ConsumeDimension;
import com.hoolai.bi.report.model.entity.EconomyItemReport;
import com.hoolai.bi.report.model.entity.EconomyNewReport;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.ClientEconomyNewReportService;
import com.hoolai.bi.report.service.ConsumeDimensionService;
import com.hoolai.bi.report.service.EconomyNewReportService;
import com.hoolai.bi.report.vo.ClientEconomyNewReportVO;
import com.hoolai.bi.report.vo.ConsumeDimensionVO;
import com.hoolai.bi.report.vo.EconomyNewReportVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadEconomyProcessor;
import com.hoolai.panel.web.download.UploadEconomyProcessor;

@Controller
@RequestMapping("/panel_bi/economy")
public class GameEconomyController extends AbstractPanelController{
	
	@Autowired
	private EconomyNewReportService economyNewReportService;
	
	@Autowired
	private ClientEconomyNewReportService clientEconomyNewReportService;
	
	@Autowired
	private ConsumeDimensionService consumeDimensionService;
	
	@RequestMapping(value = {"/toGameEconomy.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameEconomy(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		
		Users users = getSessionUsers(request);
		Games games = this.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		String view = request.getParameter("view");
		GameEconomyPage page = GameEconomyPage.convertToPage(view);
		
		switch(page){
		case ECONOMY_DIMENSION:
			return "economy/economyDimensionManage.jsp";
		case ECONOMY_ITEM_DATA:
		default:
			return "economy/economyItemData.jsp";
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
		String indicators = request.getParameter("indicators");
		String beginClientid = request.getParameter("b_s_c");
		String endClientid = request.getParameter("e_s_c");
		String groupType = request.getParameter("groupType");
		
		GameRoleAnalysisChannel channel = GameRoleAnalysisChannel.convertToChannel(indicators);
		
		DownLoadEconomyProcessor processor = new DownLoadEconomyProcessor(games, response);
		
		switch(channel){
		case CLIENT:
			ClientEconomyNewReport clientEconomyReport = new ClientEconomyNewReport(games.getSnid(),games.getGameid());
			ClientEconomyNewReportVO clientEconomyReportVO = new ClientEconomyNewReportVO(clientEconomyReport);
			clientEconomyReportVO.setDate(beginDate, endDate);
			clientEconomyReportVO.setClientid(Integer.valueOf(beginClientid), Integer.valueOf(endClientid));
			
			/* 因为下载比较慢改成一级一级下载 */
			clientEconomyReportVO.setGroupType(groupType);
			List<ClientEconomyNewReport> clientEconomyReports1 = clientEconomyNewReportService.selectItemList4Down(clientEconomyReportVO);
			
//			clientEconomyReportVO.setGroupType("first");
//			List<ClientEconomyNewReport> clientEconomyReports1 = clientEconomyNewReportService.selectItemList4Down(clientEconomyReportVO);
//			
//			clientEconomyReportVO.setGroupType("second");
//			List<ClientEconomyNewReport> clientEconomyReports2 = clientEconomyNewReportService.selectItemList4Down(clientEconomyReportVO);
//			
//			clientEconomyReportVO.setGroupType("third");
//			List<ClientEconomyNewReport> clientEconomyReports3 = clientEconomyNewReportService.selectItemList4Down(clientEconomyReportVO);
//			
//			clientEconomyReportVO.setGroupType("fourth");
//			List<ClientEconomyNewReport> clientEconomyReports4 = clientEconomyNewReportService.selectItemList4Down(clientEconomyReportVO);
				
			processor.initClientEconomyNewReportList(clientEconomyReports1,null,null,null);
		    processor.writeClientEconomyNewReport(beginClientid,endClientid,groupType);
			
		    break;
			
		case ALL:
			default:
				EconomyNewReport economyReport = new EconomyNewReport(games.getSnid(),games.getGameid());
				EconomyNewReportVO economyReportVO = new EconomyNewReportVO(economyReport);
				economyReportVO.setDate(beginDate, endDate);
				
				economyReportVO.setGroupType(groupType);
				List<EconomyNewReport> economyReports1 = economyNewReportService.selectItemList4Down(economyReportVO);
				
//				economyReportVO.setGroupType("first");
//				List<EconomyNewReport> economyReports1 = economyNewReportService.selectItemList4Down(economyReportVO);
//				economyReportVO.setGroupType("second");
//				List<EconomyNewReport> economyReports2 = economyNewReportService.selectItemList4Down(economyReportVO);
//				economyReportVO.setGroupType("third");
//				List<EconomyNewReport> economyReports3 = economyNewReportService.selectItemList4Down(economyReportVO);
//				economyReportVO.setGroupType("fourth");
//				List<EconomyNewReport> economyReports4 = economyNewReportService.selectItemList4Down(economyReportVO);
				
				processor.initEconomyNewReportList(economyReports1,null,null,null);
			    processor.writeEconomyNewReport(groupType);
				
		}
		return null;
	}
	
	@RequestMapping(value = {"/getGameEconomy.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameEconomy(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String indicators = request.getParameter("indicators");
		String beginClientid = request.getParameter("beginClientid");
		String endClientid = request.getParameter("endClientid");
		String groupType = request.getParameter("groupType");
		String queryType = request.getParameter("queryType");
		String isCountRequest = request.getParameter("isCountRequest");
		
		GameRoleAnalysisChannel channel = GameRoleAnalysisChannel.convertToChannel(indicators);
		Map<String,Object> pageInfo = super.findPageInfo(request);
		
		switch(channel){
		case CLIENT:
			ClientEconomyNewReport clientEconomyReport = new ClientEconomyNewReport(games.getSnid(),games.getGameid());
			ClientEconomyNewReportVO clientEconomyReportVO = new ClientEconomyNewReportVO(clientEconomyReport);
			clientEconomyReportVO.setDate(beginDate, endDate);
			clientEconomyReportVO.setClientid(Integer.valueOf(beginClientid), Integer.valueOf(endClientid));
			clientEconomyReportVO.setGroupType(groupType);
			
			if(StringUtils.isEmpty(isCountRequest)){
				//总记录数
				long recordsTotal = clientEconomyNewReportService.selectItemCount(clientEconomyReportVO);
				// 过滤记录数
				clientEconomyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
				long recordsFiltered = clientEconomyNewReportService.selectItemCount(clientEconomyReportVO);
				
				// 分页数据
				clientEconomyReportVO.setOffset((Long) pageInfo.get("start"));
				clientEconomyReportVO.setRows((Long) pageInfo.get("length"));
				String orderCol = (String)pageInfo.get("orderCol");
				clientEconomyReportVO.setOrderDate(StringUtils.isEmpty(orderCol)?null:"day"+orderCol);
				clientEconomyReportVO.setOrderType((String)pageInfo.get("orderType"));
				clientEconomyReportVO.setGroupType(request.getParameter("groupType"));
				List<EconomyItemReport> itemReports = clientEconomyNewReportService.selectItemList(queryType,clientEconomyReportVO);
				
				ret.put("recordsTotal", recordsTotal);
				ret.put("recordsFiltered", recordsFiltered);
				ret.put("data", itemReports);
			}else{
				clientEconomyReportVO.setOrderDate("amount");
				ret.put("chartData", clientEconomyNewReportService.selectItemList(queryType, clientEconomyReportVO));
				ret.put("dataCount", clientEconomyNewReportService.selectItemCountList(queryType, clientEconomyReportVO));
			}
			break;
		case ALL:
			default:
				EconomyNewReport economyReport = new EconomyNewReport(games.getSnid(),games.getGameid());
				EconomyNewReportVO economyReportVO = new EconomyNewReportVO(economyReport);
				economyReportVO.setDate(beginDate, endDate);
				economyReportVO.setGroupType(groupType);
				
				if(StringUtils.isEmpty(isCountRequest)){
					//总记录数
					long recordsTotal_all = economyNewReportService.selectItemCount(economyReportVO);
					
					// 过滤记录数
					economyReportVO.setSearchValue((String) pageInfo.get("searchValue"));
					long recordsFiltered_all = economyNewReportService.selectItemCount(economyReportVO);
					
					// 分页数据
					economyReportVO.setOffset((Long) pageInfo.get("start"));
					economyReportVO.setRows((Long) pageInfo.get("length"));
					
					String orderCol_all = (String)pageInfo.get("orderCol");
					economyReportVO.setOrderDate(StringUtils.isEmpty(orderCol_all)?null:"day"+orderCol_all);
					economyReportVO.setOrderType((String)pageInfo.get("orderType"));
					economyReportVO.setGroupType(request.getParameter("groupType"));
					List<EconomyItemReport> itemReports_all = economyNewReportService.selectItemList(queryType,economyReportVO);
					
					
					ret.put("recordsTotal", recordsTotal_all);
					ret.put("recordsFiltered", recordsFiltered_all);
					ret.put("data", itemReports_all);
				}else {
					economyReportVO.setOrderDate("amount");
					ret.put("chartData", economyNewReportService.selectItemList(queryType, economyReportVO));
					ret.put("dataCount", economyNewReportService.selectItemCountList(queryType, economyReportVO));
				}
				break;
		}
		
		setSessionWeekDate(request, beginDate, endDate);
		ret.put("game", games);
		session.setAttribute("game", games);
		return ret;
	}

	@RequestMapping(value = {"/getGameClient.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameClient(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = getSessionGames(request);
		
		ClientEconomyNewReport clientDailyReport = new ClientEconomyNewReport(games.getSnid(), games.getGameid());
		ClientEconomyNewReportVO clientEconomyReportVO = new ClientEconomyNewReportVO(clientDailyReport);
		
		List<Integer> gameClients = clientEconomyNewReportService.selectGameClients(clientEconomyReportVO);
		
		ret.put("gameClients", gameClients);
		
		return ret;
	}
	
	@RequestMapping(value = "/download_data_templete.ac", method = {RequestMethod.POST })
	public String downloadDataTemplete(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)throws Exception{
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = getSessionGames(request);
		
		String fileName = "consume_point_template.xlsx";
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream strem = null;
		strem = loader.getResourceAsStream("templates/"+fileName);
		Workbook wb = new XSSFWorkbook(strem);
		Sheet sheet = wb.getSheetAt(0);
		
		fileName = "consume_template.xlsx";
		
		ConsumeDimension consumePointDimension = new ConsumeDimension(games.getSnid(), games.getGameid());
		ConsumeDimensionVO consumePointDimensionVO = new ConsumeDimensionVO(consumePointDimension);
		List<ConsumeDimension> consumePointDimensions = consumeDimensionService.getMatch(consumePointDimensionVO);
		
		int index = 1;
		for(ConsumeDimension pointDimension:consumePointDimensions){
			Row row = sheet.createRow(index++);
			Cell cell = row.createCell(0);
			cell.setCellValue(pointDimension.getConsumeCode());
			
			cell = row.createCell(1);
			cell.setCellValue(pointDimension.getConsumeName());
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
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		String filePath = request.getParameter("filePath");
		
		UploadEconomyProcessor processor = new UploadEconomyProcessor(filePath,uploadFile,games);
		
		processor.readBook();
		
		List<ConsumeDimension> consumeDimensions = processor.getConsumeDimensions();
		
		for(ConsumeDimension consumePointDimension:consumeDimensions){
			ConsumeDimension temp = consumeDimensionService.getByGameId(consumePointDimension);
			if(temp != null){
				consumeDimensionService.removeById(temp.getId());
			}
			consumePointDimension.setUpdateUserId(getSessionUsers(request).getId());
			consumeDimensionService.saveEntity(consumePointDimension);
		}
		
		session.setAttribute("isEconomyUpLoadEnd", "1");
		
		return null;
	}
	
	
	@RequestMapping(value = {"/getUpLoadEndMark.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getUpLoadEndMark(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		Map<String,Object> ret = new HashMap<String, Object>();
		
		if(session.getAttribute("isEconomyUpLoadEnd") != null
				&& session.getAttribute("isEconomyUpLoadEnd").equals("1")){
			ret.put("isEconomyUpLoadEnd", "1");
			session.removeAttribute("isEconomyUpLoadEnd");
		}else{
			ret.put("isEconomyUpLoadEnd", "0");
		}
		return ret;
	}
	
	@RequestMapping(value = {"/queryDimensionData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> queryDimensionData(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = getSessionGames(request);
		
		Map<String,Object> pageInfo = this.findPageInfo(request);
		
		ConsumeDimension consumeDimension = new ConsumeDimension(games.getSnid(), games.getGameid());
		ConsumeDimensionVO consumeDimensionVO = new ConsumeDimensionVO(consumeDimension);
		
		//总记录数
		long recordsTotal = consumeDimensionService.selectCount(consumeDimensionVO);
		// 过滤记录数
		consumeDimensionVO.setSearchValue((String) pageInfo.get("searchValue"));
		long recordsFiltered = consumeDimensionService.selectCount(consumeDimensionVO);
		
		// 分页数据
		consumeDimensionVO.setOffset((Long) pageInfo.get("start"));
		consumeDimensionVO.setRows((Long) pageInfo.get("length"));
		
		consumeDimensionVO.setOrderCol((String)pageInfo.get("orderCol"));
		consumeDimensionVO.setOrderType((String)pageInfo.get("orderType"));
		
		ret.put("recordsTotal", recordsTotal);
		ret.put("recordsFiltered", recordsFiltered);
		ret.put("data", consumeDimensionService.selectList(consumeDimensionVO));
		
		return ret;
	}
	
	@RequestMapping(value = {"/saveDimensionData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> saveDimensionData(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String code = request.getParameter("consumeCode");
		String name = request.getParameter("consumeName");
		
		if(StringUtils.isEmpty(code) || StringUtils.isEmpty(name)){
			ret.put("msg", "1");//内容不能为空，请重新输入！
			return ret;
		}
		
		ConsumeDimension consumeDimension = new ConsumeDimension(games.getSnid(), games.getGameid());
		consumeDimension.setConsumeCode(code);
		consumeDimension.setConsumeName(name);
		consumeDimension.setUpdateUserId(getSessionUsers(request).getId());
		
		ConsumeDimension temp = consumeDimensionService.getByGameId(consumeDimension);
		if(temp == null){
			consumeDimensionService.saveEntity(consumeDimension);
		}else{
			consumeDimension.setId(temp.getId());
			consumeDimensionService.modifyEntity(consumeDimension);
		}
		
		ret.put("msg", "2");// 修改成功
		return ret;
	}
	
	@RequestMapping(value = {"/delDimensionData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delDimensionData(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String code = request.getParameter("consumeCode");
		String name = request.getParameter("consumeName");
		
		if(StringUtils.isEmpty(code) || StringUtils.isEmpty(name)){
			ret.put("msg", "1");//不存在
			return ret;
		}
		
		ConsumeDimension consumeDimension = new ConsumeDimension(games.getSnid(), games.getGameid());
		consumeDimension.setConsumeCode(code);
		consumeDimension.setConsumeName(name);
		
		ConsumeDimension temp = consumeDimensionService.getByGameId(consumeDimension);
		if(temp == null){
			ret.put("msg", "1");//不存在
			return ret;
		}else{
			consumeDimensionService.removeById(temp.getId());
		}
		
		ret.put("msg", "2");//删除成功
		return ret;
	}
}

