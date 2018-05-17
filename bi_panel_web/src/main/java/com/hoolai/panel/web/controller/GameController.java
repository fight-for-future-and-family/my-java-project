package com.hoolai.panel.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.entity.ClientDailyReport;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.SourceDailyReport;
import com.hoolai.bi.report.service.ClientDailyReportService;
import com.hoolai.bi.report.service.SourceDailyReportService;
import com.hoolai.bi.report.vo.ClientDailyReportVO;
import com.hoolai.bi.report.vo.SourceDailyReportVO;
import com.hoolai.manage.exception.ApplyException;

@Controller
@RequestMapping("/panel_bi/game")
public class GameController extends AbstractPanelController{
	
	@Autowired
	private SourceDailyReportService sourceDailyReportService;
	@Autowired
	private ClientDailyReportService clientDailyReportService;
	
	@RequestMapping(value = {"/getGameSource.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameSource(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = findGames(request);
		
		SourceDailyReport sourceDailyReport = new SourceDailyReport();
		sourceDailyReport.setSnid(games.getSnid());
		sourceDailyReport.setGameid(games.getGameid());
		
		SourceDailyReportVO sourceDailyReportVO = new SourceDailyReportVO(sourceDailyReport);
		List<String> gameSources = sourceDailyReportService.selectGameSources(sourceDailyReportVO);
		
		ret.put("gameSources", gameSources);
		
		return ret;
	}

	private Games findGames(HttpServletRequest request) throws ApplyException {
		String snid = request.getParameter("snid");
		String gameid = request.getParameter("gameId");
		String gamesId = request.getParameter("gamesId");
		Games games = null;
		
		if(StringUtils.isEmpty(snid) || StringUtils.isEmpty(gameid)){
			if(StringUtils.isEmpty(gamesId)|| (games = this.getSessionGames(request, Long.valueOf(gamesId))) == null){
				throw new ApplyException("auth is error!");
			}
		}else if((games = this.getSessionGames(request, snid, gameid)) == null){
			throw new ApplyException("auth is error!");
		}
		return games;
	}
	
	@RequestMapping(value = {"/getGameClient.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameClient(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = findGames(request);
		
		ClientDailyReport clientDailyReport = new ClientDailyReport();
		clientDailyReport.setSnid(games.getSnid());
		clientDailyReport.setGameid(games.getGameid());
		
		ClientDailyReportVO clientDailyReportVO = new ClientDailyReportVO(clientDailyReport);
		List<String> gameClients = clientDailyReportService.selectGameClients(clientDailyReportVO);
		
		ret.put("gameClients", gameClients);
		
		return ret;
	}
}

