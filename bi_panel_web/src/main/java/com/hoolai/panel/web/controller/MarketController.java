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

import com.hoolai.bi.report.model.entity.AnalysisGDT;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.SummaryGDT;
import com.hoolai.bi.report.service.AnalysisGDTService;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.SummaryGDTService;
import com.hoolai.bi.report.vo.AnalysisGDTVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.SummaryGDTVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.UploadGDTProcessor;

@Controller
@RequestMapping("/panel_bi/market")
public class MarketController extends AbstractPanelController{
	
	@Autowired
	private GamesService gamesService;
	@Autowired
	private SummaryGDTService summaryGDTService;
	@Autowired
	private AnalysisGDTService analysisGDTService;
	
	@RequestMapping(value = {"/toMarket.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		String view = request.getParameter("view");
		
		if("upload".equals(view)){
			return "marketManager/upLoad.jsp";
		}else if("dataquery".equals(view)){
			return "marketManager/gdtList.jsp";
		}
		
		return "marketManager/gdtList.jsp";
	}
	
	@RequestMapping(value = {"/getMarketDats.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getMarketDats(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		
		String templeteType = request.getParameter("templeteType");
		
		if("gdt".equals(templeteType)){
			SummaryGDT summaryGDT = new SummaryGDT(games.getSnid(), games.getGameid());
			SummaryGDTVO summaryGDTVO = new SummaryGDTVO(summaryGDT);
			summaryGDTVO.setDate(beginDate, endDate);
			List<SummaryGDT> summaryGDTs = summaryGDTService.getMatch(summaryGDTVO);
			
			AnalysisGDT analysisGDT = new AnalysisGDT(games.getSnid(), games.getGameid());
			AnalysisGDTVO analysisGDTVO = new AnalysisGDTVO(analysisGDT);
			analysisGDTVO.setDate(beginDate, endDate);
			List<AnalysisGDT> analysisGDTs = analysisGDTService.getMatch(analysisGDTVO);
			
			ret.put("summaryGDTs", summaryGDTs);
			ret.put("analysisGDTs", analysisGDTs);
		}else if("scgl".equals(templeteType)){
			ret.put("marketDatas", null);
		}
		
		super.setSessionDate(request, beginDate, endDate);
		
		return ret;
	}
	
	@RequestMapping(value = "/download_data_templete.ac", method = {RequestMethod.POST })
	public String downloadDataTemplete(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)throws Exception{
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		String templeteType = request.getParameter("templeteType");
		String fileName = "gdt_template.xlsx";
		
		if("gdt".equals(templeteType)){
			fileName = "gdt_template.xlsx";
		}else if("scgl".equals(templeteType)){
			fileName = "gdt_template.xlsx";
		}
		
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
		
		UploadGDTProcessor processor = new UploadGDTProcessor(filePath,uploadFile);
		processor.upload();
		
		List<SummaryGDT> summaryGDTs = processor.getSummaryGDTs();
		List<AnalysisGDT> analysisGDTs = processor.getAnalysisGDTs();
		
		for(SummaryGDT summaryGDT:summaryGDTs){
			summaryGDT.setSnid(games.getSnid());
			summaryGDT.setGameid(games.getGameid());
			
			SummaryGDT temp = summaryGDTService.getByGameId(summaryGDT);
			if(temp != null){
				summaryGDTService.removeById(temp.getId());
			}
			summaryGDTService.saveEntity(summaryGDT);
		}
		
		for(AnalysisGDT analysisGDT:analysisGDTs){
			analysisGDT.setSnid(games.getSnid());
			analysisGDT.setGameid(games.getGameid());
			
			AnalysisGDT temp = analysisGDTService.getByGameId(analysisGDT);
			if(temp != null){
				analysisGDTService.removeById(temp.getId());
			}
			analysisGDTService.saveEntity(analysisGDT);
		}
		
		session.setAttribute("isUpLoadEnd", "1");
		
		return null;
	}
	
	
	@RequestMapping(value = {"/getUpLoadEndMark.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getEndMark(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		Map<String,Object> ret = new HashMap<String, Object>();
		
		if(session.getAttribute("isUpLoadEnd") != null
				&& session.getAttribute("isUpLoadEnd").equals("1")){
			ret.put("isUpLoadEnd", "1");
			session.removeAttribute("isUpLoadEnd");
		}else{
			ret.put("isUpLoadEnd", "0");
		}
		return ret;
	}
}

