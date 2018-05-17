package com.hoolai.panel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.runtime.parser.node.PutExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.Types.GameAnalysisChannel;
import com.hoolai.bi.report.model.Types.GamePayerLevelPage;
import com.hoolai.bi.report.model.entity.ClientLevelNewPayment;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.LevelDauReport;
import com.hoolai.bi.report.model.entity.LevelInstallDauReport;
import com.hoolai.bi.report.model.entity.LevelInstallPayerReport;
import com.hoolai.bi.report.model.entity.LevelNewPayment;
import com.hoolai.bi.report.service.ClientLevelNewPaymentService;
import com.hoolai.bi.report.service.LevelDauReportService;
import com.hoolai.bi.report.service.LevelInstallPayerReportService;
import com.hoolai.bi.report.service.LevelNewPaymentService;
import com.hoolai.bi.report.vo.ClientLevelNewPaymentVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.LevelDauReportVO;
import com.hoolai.bi.report.vo.LevelInstallPayerReportVO;
import com.hoolai.bi.report.vo.LevelNewPaymentVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadGameLevelProcessor;

@Controller
@RequestMapping("/panel_bi/payerLevel")
public class GamePayerLevelController extends AbstractPanelController{
	
	@Autowired
	private LevelDauReportService levelDauReportService;
	
	@Autowired
	private LevelInstallPayerReportService levelInstallPayerReportService;
	
	@Autowired
	private LevelNewPaymentService levelNewPaymentService;
	
	@Autowired
	private ClientLevelNewPaymentService clientLevelNewPaymentService;
	
	@RequestMapping(value = {"/toGamePayerLevel.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGamePayerLevel(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		Games games = this.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		String view = request.getParameter("view");
		GamePayerLevelPage page = GamePayerLevelPage.convertToPage(view);
		
		switch(page){
		case INSTALL_LEVEL:
			return "gameLevel/installLevel.jsp";
		case INSTALL_PAYMENT_LEVEL:
			return "gameLevel/installPaymentLevel.jsp";
		case NEW_PAYMENT_LEVEL:
			return "gameLevel/newPayLevel.jsp";
		case KPI_PREDICT:
			return "gameLevel/kpiPredict.jsp";
		case DAU_LEVEL:
		default:
			return "gameLevel/dauLevel.jsp";
		}
	}
	
	@RequestMapping(value = {"/downloadData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("ec_beginTime");
		String endDate = request.getParameter("ec_endTime");
		String view = request.getParameter("view");
		
		
		DownLoadGameLevelProcessor processor = new DownLoadGameLevelProcessor(games, response);		
		GamePayerLevelPage page = GamePayerLevelPage.convertToPage(view);
		
		switch(page){
		case INSTALL_LEVEL:
			LevelInstallPayerReport installPayerReport = new LevelInstallPayerReport(games.getSnid(),games.getGameid());
			LevelInstallPayerReportVO installPayerReportVO = new LevelInstallPayerReportVO(installPayerReport);
			installPayerReportVO.setDate(beginDate, endDate);
			
			List<LevelInstallDauReport> installReports = levelInstallPayerReportService.getLevelDauReportList(installPayerReportVO,null);
			
			processor.setReports(installReports);
			processor.writeInstallLevelReport(beginDate);
			break;
		case INSTALL_PAYMENT_LEVEL:
			LevelInstallPayerReport payerReport = new LevelInstallPayerReport(games.getSnid(),games.getGameid());
			LevelInstallPayerReportVO payerReportVO = new LevelInstallPayerReportVO(payerReport);
			payerReportVO.setDate(beginDate, endDate);
			
			List<LevelInstallDauReport> paymemtReports = levelInstallPayerReportService.getLevelDauReportList(payerReportVO,"install_payment");
			List<LevelInstallDauReport> payersReports = levelInstallPayerReportService.getLevelDauReportList(payerReportVO,"install_payers");
			
			processor.setReports(paymemtReports,payersReports);
			processor.writeInstallPaymentLevelReport(beginDate);
			break;
		case NEW_PAYMENT_LEVEL:
			String indicators = request.getParameter("indicators");
			String clientid = request.getParameter("s_c");
			GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(indicators);
			switch(channel){
			case ALL:
				LevelNewPayment levelNewPayment = new LevelNewPayment(games.getSnid(),games.getGameid());
				LevelNewPaymentVO levelNewPaymentVO = new LevelNewPaymentVO(levelNewPayment);
				levelNewPaymentVO.setDate(beginDate, endDate);
				
				List<LevelInstallDauReport> newPaymemtReports = levelNewPaymentService.getLevelNewPayReportList(levelNewPaymentVO, "install_payment");
				List<LevelInstallDauReport> newPayPayersReports = levelNewPaymentService.getLevelNewPayReportList(levelNewPaymentVO, "install_payers");
				
				processor.setReports(newPaymemtReports,newPayPayersReports);
				processor.writeNewPaymentLevelReport(beginDate, endDate, 0);
				break;
			case CLIENT:
				ClientLevelNewPayment clientLevelNewPayment = new ClientLevelNewPayment(games.getSnid(),games.getGameid(),Integer.valueOf(clientid));
				ClientLevelNewPaymentVO clientLevelNewPaymentVO = new ClientLevelNewPaymentVO(clientLevelNewPayment);
				clientLevelNewPaymentVO.setDate(beginDate, endDate);
				
				List<LevelInstallDauReport> clientNewPayReports = clientLevelNewPaymentService.getLevelNewPayReportList(clientLevelNewPaymentVO, "install_payment");
				List<LevelInstallDauReport> clientNewPayPayersReports = clientLevelNewPaymentService.getLevelNewPayReportList(clientLevelNewPaymentVO, "install_payers");
				
				processor.setReports(clientNewPayReports,clientNewPayPayersReports);
				processor.writeNewPaymentLevelReport(beginDate, endDate, clientLevelNewPayment.getClientid());
				break;
			}
			break;
		case KPI_PREDICT:
			break;
		case DAU_LEVEL:
			default:
				LevelDauReport levelDauReport = new LevelDauReport(games.getSnid(),games.getGameid());
				LevelDauReportVO levelDauReportVO = new LevelDauReportVO(levelDauReport);
				levelDauReportVO.setDate(beginDate, endDate);
				List<LevelInstallDauReport> reports = levelDauReportService.getLevelDauReportList(levelDauReportVO);
				
				processor.setReports(reports);
				processor.writeDauLevelReport(beginDate, endDate);
				break;
		}
		return null;
	}
	
	@RequestMapping(value = {"/getGameLevelData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameLevelData(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String indicators = request.getParameter("indicators");
		String view = request.getParameter("view");
//		String groupType = request.getParameter("groupType");
		String queryType = request.getParameter("queryType");
		String clientid = request.getParameter("clientid");
		
		GamePayerLevelPage page = GamePayerLevelPage.convertToPage(view);
		GameAnalysisChannel channel = GameAnalysisChannel.convertToChannel(indicators);
		//Map<String,Object> pageInfo = super.findPageInfo(request);
		
		switch(page){
		case INSTALL_LEVEL:
		case INSTALL_PAYMENT_LEVEL:
			LevelInstallPayerReport installPayerReport = new LevelInstallPayerReport(games.getSnid(),games.getGameid());
			LevelInstallPayerReportVO installPayerReportVO = new LevelInstallPayerReportVO(installPayerReport);
			installPayerReportVO.setDate(beginDate, endDate);
			
			List<LevelInstallDauReport> installReports = levelInstallPayerReportService.getLevelDauReportList(installPayerReportVO,queryType);
			
			ret.put("reports", installReports);
			break;
		case NEW_PAYMENT_LEVEL:
			switch(channel){
			case ALL:
				LevelNewPayment levelNewPayment = new LevelNewPayment(games.getSnid(),games.getGameid());
				LevelNewPaymentVO levelNewPaymentVO = new LevelNewPaymentVO(levelNewPayment);
				levelNewPaymentVO.setDate(beginDate, endDate);
				
				List<LevelInstallDauReport> newPayReports = levelNewPaymentService.getLevelNewPayReportList(levelNewPaymentVO, queryType);
				
				ret.put("reports", newPayReports);
				break;
			case CLIENT:
				ClientLevelNewPayment clientLevelNewPayment = new ClientLevelNewPayment(games.getSnid(),games.getGameid(),Integer.valueOf(clientid));
				ClientLevelNewPaymentVO clientLevelNewPaymentVO = new ClientLevelNewPaymentVO(clientLevelNewPayment);
				clientLevelNewPaymentVO.setDate(beginDate, endDate);
				
				List<LevelInstallDauReport> clientNewPayReports = clientLevelNewPaymentService.getLevelNewPayReportList(clientLevelNewPaymentVO, queryType);
				
				ret.put("reports", clientNewPayReports);
				break;
			}
			break;
		case KPI_PREDICT:
			break;
		case DAU_LEVEL:
			default:
				LevelDauReport levelDauReport = new LevelDauReport(games.getSnid(),games.getGameid());
				LevelDauReportVO levelDauReportVO = new LevelDauReportVO(levelDauReport);
				levelDauReportVO.setDate(beginDate, endDate);
				List<LevelInstallDauReport> reports = levelDauReportService.getLevelDauReportList(levelDauReportVO);
				
				ret.put("reports", reports);
				break;
		}
		
		setSessionWeekDate(request, beginDate, endDate);
		ret.put("game", games);
		session.setAttribute("game", games);
		return ret;
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
	
	@RequestMapping(value = {"/getNewPayClient.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getNewPayClient(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		
		ClientLevelNewPayment clientLevelNewPayment = new ClientLevelNewPayment(games.getSnid(),games.getGameid(),0);
		ClientLevelNewPaymentVO clientLevelNewPaymentVO = new ClientLevelNewPaymentVO(clientLevelNewPayment);
		clientLevelNewPaymentVO.setDate(beginDate, endDate);
		
		List<Integer> gameClients = clientLevelNewPaymentService.selectClientids(clientLevelNewPaymentVO);
		
		ret.put("gameClients", gameClients);
		return ret;
	}
}

