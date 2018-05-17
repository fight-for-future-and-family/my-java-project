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

import com.hoolai.bi.report.model.entity.AdminOpMonitor;
import com.hoolai.bi.report.model.entity.AdminOpMonitorStandard;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.AdminOpMonitorService;
import com.hoolai.bi.report.service.AdminOpMonitorStandardService;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.vo.AdminOpMonitorStandardVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;
import com.hoolai.panel.web.download.UploadAdminOpMonitorProcessor;
import com.hoolai.panel.web.processor.AdminOpMonitorProcessor;
import com.hoolai.panel.web.processor.AdminOpMonitorProcessor.AdminOpMonitorRequestStatus;

@Controller
@RequestMapping("/panel_bi/opMonitor")
public class GameOpMonitorController extends AbstractPanelController{
	
	@Autowired
	private AdminOpMonitorStandardService adminOpMonitorStandardService;
	
	@Autowired
	private AdminOpMonitorService adminOpMonitorService;
	
	@Autowired
	private GamesService gamesService;
	
	@RequestMapping(value = {"/toOpMonitor.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		// 在session中存放game实例
		super.setSession(request, "game", games);
		
        String view = request.getParameter("view");
		
        if("opMonitorUp".equals(view)){
			return "adminOpMonitor/adminOpMonitorUp.jsp";
		}
        
        return "adminOpMonitor/adminOpMonitorUp.jsp";
	}
	
	/**
	 * 下载原始模板
	 */
	@RequestMapping(value = "/download_data_templete.ac", method = {RequestMethod.POST })
	public String downloadDataTemplete(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)throws Exception{
		
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		Games games = getSessionGames(request);
		String fileName = "op_monitor_template.xlsx";
		String downType = request.getParameter("downType");
		if("data".equals(downType)){
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			
			AdminOpMonitorStandard monitorStandard = new AdminOpMonitorStandard(games.getSnid(), games.getGameid());
			monitorStandard.setMonth(year+"-"+month);
			AdminOpMonitorStandardVO monitorStandardVO = new AdminOpMonitorStandardVO(monitorStandard);
			monitorStandardVO.setQueryType("all");
			
			DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
			processor.writeGameOpMonitorStandard(adminOpMonitorStandardService.selectList(monitorStandardVO));
		}else{
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream strem = null;
			strem = loader.getResourceAsStream("templates/"+fileName);
			Workbook wb = new XSSFWorkbook(strem);
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
		}
		return null;
	}
	
	/**
	 * 上传比较标准
	 */
	@RequestMapping(value = {"/upload.ac"}, method = { RequestMethod.POST })
	public Map<String, Object> uploadFile(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam MultipartFile uploadFile,Model model) throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		Games games = this.getSessionGames(request);
		String filePath = request.getParameter("filePath");
		String date = request.getParameter("date");
		
		try {
		    UploadAdminOpMonitorProcessor processor = new UploadAdminOpMonitorProcessor(filePath,uploadFile,date,games);
			processor.readBook();
			
			List<AdminOpMonitorStandard> monitors = processor.getAdminOpMonitorStandards();
			
			for(AdminOpMonitorStandard monitor:monitors){
				AdminOpMonitorStandard temp = adminOpMonitorStandardService.getByCode(monitor);
				if(temp != null){
					monitor.setId(temp.getId());
					monitor.setUploadUserId(users.getId());
					monitor.setUploadUserName(users.getLoginName());
					adminOpMonitorStandardService.modifyEntitySelective(monitor);
				}else{
					monitor.setUploadUserId(users.getId());
					monitor.setUploadUserName(users.getLoginName());
					adminOpMonitorStandardService.saveEntity(monitor);
				}
			}
			
			session.setAttribute("isAdminOPMonitorUpLoadEnd", "1");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("isAdminOPMonitorUpLoadEnd", "2");
			
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("status", 2);
			return ret;
		}
		return null;
	}
	
	@RequestMapping(value = {"/getUpLoadEndMark.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getUpLoadEndMark(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		Map<String,Object> ret = new HashMap<String, Object>();
		
		if(session.getAttribute("isAdminOPMonitorUpLoadEnd") != null
				&& session.getAttribute("isAdminOPMonitorUpLoadEnd").equals("1")){
			ret.put("isAdminOPMonitorUpLoadEnd", "1");
			session.removeAttribute("isAdminOPMonitorUpLoadEnd");
		}else if(session.getAttribute("isAdminOPMonitorUpLoadEnd") != null
				&& session.getAttribute("isAdminOPMonitorUpLoadEnd").equals("2")){
			ret.put("isAdminOPMonitorUpLoadEnd", "2");
			session.removeAttribute("isAdminOPMonitorUpLoadEnd");
		}else{
			ret.put("isAdminOPMonitorUpLoadEnd", "0");
		}
		return ret;
	}
	
	@RequestMapping(value = {"/queryOpMonitorUpData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> queryOpMonitorUpData(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = getSessionGames(request);
		String month = request.getParameter("month");
		
		Map<String,Object> pageInfo = this.findPageInfo(request);
		
		AdminOpMonitorStandard monitorStandard = new AdminOpMonitorStandard(games.getSnid(), games.getGameid());
		monitorStandard.setMonth(month);
		AdminOpMonitorStandardVO monitorStandardVO = new AdminOpMonitorStandardVO(monitorStandard);
		
		//总记录数
		long recordsTotal = adminOpMonitorStandardService.selectCount(monitorStandardVO);
		// 过滤记录数
		monitorStandardVO.setSearchValue((String) pageInfo.get("searchValue"));
		long recordsFiltered = adminOpMonitorStandardService.selectCount(monitorStandardVO);
		
		// 分页数据
		monitorStandardVO.setOffset((Long) pageInfo.get("start"));
		monitorStandardVO.setRows((Long) pageInfo.get("length"));
		
		monitorStandardVO.setOrderCol((String)pageInfo.get("orderCol"));
		monitorStandardVO.setOrderType((String)pageInfo.get("orderType"));
		
		ret.put("recordsTotal", recordsTotal);
		ret.put("recordsFiltered", recordsFiltered);
		ret.put("data", adminOpMonitorStandardService.selectList(monitorStandardVO));
		
		return ret;
	}
	
	@RequestMapping(value = {"/adminOpMonitor.ac"}, method = {RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> adminop(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		
		AdminOpMonitorProcessor processor = new AdminOpMonitorProcessor(request,gamesService);
		AdminOpMonitor monitor = processor.processAdminOpMoninor();
		if(monitor == null){
			return processor.getFailRet();
		}else{
			adminOpMonitorService.saveEntity(monitor);
			
			Map<String,Object> ret = new HashMap<String, Object>();
			ret.put("status", AdminOpMonitorRequestStatus.SUCCESS.ordinal());
			return ret;
		}
	}

}

