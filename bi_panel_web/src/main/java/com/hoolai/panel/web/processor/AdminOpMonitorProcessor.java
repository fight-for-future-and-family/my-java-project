package com.hoolai.panel.web.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.hoolai.bi.report.model.entity.AdminOpMonitor;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.util.DateUtils;

public class AdminOpMonitorProcessor {
	
	public enum AdminOpMonitorRequestStatus{
		SUCCESS,FAIL
	}

	private HttpServletRequest request;
	private GamesService gamesService;
	private Map<String,Object> ret = new HashMap<String, Object>();
	
	public AdminOpMonitorProcessor(HttpServletRequest request,GamesService gamesService){
		this.request = request;
		this.gamesService = gamesService;
	}
	
	public AdminOpMonitor processAdminOpMoninor() {
		String clientid = request.getParameter("clientid");
		String opCode = request.getParameter("opCode");
		String snid = request.getParameter("snid");
		String gameid = request.getParameter("gameid");
		String opUserName = request.getParameter("opUserName");
		String itemId = request.getParameter("itemId");
		String itemName = request.getParameter("itemName");
		String itemNum = request.getParameter("itemNum");
		String beOperatedUserName = request.getParameter("beOperatedUserName");
		String beOperatedUserRole = request.getParameter("beOperatedUserRole");
		String opDate = request.getParameter("opDate");
		String opTime = request.getParameter("opTime");
		
		if(StringUtils.isEmpty(clientid)
			|| StringUtils.isEmpty(opCode)
			|| StringUtils.isEmpty(snid)
			|| StringUtils.isEmpty(gameid)
			|| StringUtils.isEmpty(opUserName)
			|| StringUtils.isEmpty(itemId)
			|| StringUtils.isEmpty(itemName)
			|| StringUtils.isEmpty(itemNum)
			|| StringUtils.isEmpty(beOperatedUserName)
			|| StringUtils.isEmpty(beOperatedUserRole)
			|| StringUtils.isEmpty(opDate)
			|| StringUtils.isEmpty(opTime)
			|| !DateUtils.isCorrectFormat(opDate,"yyyy-MM-dd")
			|| !DateUtils.isCorrectFormat(opTime,"HH:mm:ss")){
			ret.put("status", AdminOpMonitorRequestStatus.FAIL.ordinal());
			ret.put("cause", "Some essential fields are empty,or dateate format is not correct.");
			ret.put("requestParam", request.getParameterMap());
			return null;
		}else{
			AdminOpMonitor adminOpMonitor = new AdminOpMonitor();
			
			Games games = gamesService.getGames(snid, gameid);
			if(games != null){
				adminOpMonitor.setGameName(games.getName());
			}
			
			adminOpMonitor.setClientid(Integer.valueOf(clientid));
			adminOpMonitor.setOpCode(opCode);
			adminOpMonitor.setSnid(snid);
			adminOpMonitor.setGameid(gameid);
			adminOpMonitor.setOpUserName(opUserName);
			adminOpMonitor.setItemId(Long.valueOf(itemId));
			adminOpMonitor.setItemName(itemName);
			adminOpMonitor.setItemNum(Integer.valueOf(itemNum));
			adminOpMonitor.setBeOperatedUserName(beOperatedUserName);
			adminOpMonitor.setBeOperatedUserRole(beOperatedUserRole);
			adminOpMonitor.setOpDate(opDate);
			adminOpMonitor.setOpTime(opTime);
			adminOpMonitor.setCreateTime(new Date());
			
			return adminOpMonitor;
		}
	}
	
	public Map<String,Object> getFailRet(){
		return ret;
	}
}
