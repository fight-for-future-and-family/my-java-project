package com.hoolai.panel.web.controller.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoolai.bi.hive2mysql.sync.SyncAllReportDatas;
import com.hoolai.bi.hive2mysql.sync.SyncConditions.GameBiId;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.vo.SyncDatasVO;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.panel.web.controller.AbstractPanelController;

@Controller
public class SyncController extends AbstractPanelController{
	
	@Autowired
	private SyncAllReportDatas syncAllReportDatas;
	@Autowired
	private GroupUsersService groupUserService;
	
	@RequestMapping(value = {"/panel_manage/syncDatas.ac"}, method = { RequestMethod.GET })
	public String syncDatasGet(HttpServletRequest request,HttpSession session,
			@ModelAttribute("syncDatasVO") SyncDatasVO syncDatasVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		return "manage/sync/syncDatas.jsp";
	}
	
	@RequestMapping(value = {"/panel_manage/syncDatas.ac"}, method = {RequestMethod.POST })
	public String syncDatasPost(HttpServletRequest request,HttpSession session,
			@ModelAttribute("syncDatasVO") SyncDatasVO syncDatasVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		Date beginDate=DateUtils.parseDate(syncDatasVO.getBeginDate(), "yyyy-MM-dd");
		Date endDate=DateUtils.parseDate(syncDatasVO.getEndDate(), "yyyy-MM-dd");
		
		List<GameBiId> gameBiIds=new ArrayList<GameBiId>();
		
		Map<Long,Games> gamesMap=(Map<Long,Games>)session.getAttribute("gamesMap");
		
		String[] gameids=syncDatasVO.getGameids();
		for (String gameid : gameids) {
			if(!StringUtils.isNumeric(gameid)){
				continue;
			}
			Games games=gamesMap.get(Long.parseLong(gameid));
			if(games==null){
				continue;
			}
			gameBiIds.add(new GameBiId(games.getSnid(), games.getGameid()));
		}	
		
		this.syncAllReportDatas.sync(gameBiIds,beginDate, endDate);
		model.addAttribute("status", "succ");
		return "manage/sync/syncDatas.jsp";
	}

	
}

