package com.hoolai.panel.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoolai.bi.monitor.service.GameAlarmUserService;
import com.hoolai.bi.monitor.vo.GameAlarmUserVO;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.UserGamesService;
import com.hoolai.bi.report.vo.UserGamesVO;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;

@Controller
@RequestMapping("/panel_bi/alarm_user")
public class GameAlarmUserController extends AbstractPanelController{
	
	@Autowired
	private GamesService gamesService;
	
	@Autowired
	private GroupUsersService groupUserService;
	
	@Autowired
	private GameAlarmUserService gameAlarmUserService;
	
	@Autowired
	private UserGamesService userGamesService;
	

	@RequestMapping(value = {"/toList.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String toList(HttpServletRequest request,HttpSession session,@ModelAttribute("gameAlarmUserVO") GameAlarmUserVO gameAlarmUserVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		return list(1, request, session, gameAlarmUserVO, result, model);
		
	}
	
	@RequestMapping(value = {"/list_{pageNo:\\d+}.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String list(@PathVariable Integer pageNo,HttpServletRequest request,HttpSession session,@ModelAttribute("gameAlarmUserVO") GameAlarmUserVO gameAlarmUserVO,BindingResult result,Model model)throws Exception {
		
		//super.fillGames(gameAlarmUserVO.getGameId(), model);
		
		Games game=super.getSessionGames(request);
		
		List<GameAlarmUserVO> gameAlarmUserVOList=this.gameAlarmUserService.getGameAlarmUserList(game.getId());
		
		model.addAttribute("gameAlarmUserVOList", gameAlarmUserVOList);
		model.addAttribute("gameId", gameAlarmUserVO.getGameId());
		
		return "games/alarmuser/list.jsp";
	}
	
	@RequestMapping(value = {"/toAdd.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String toAdd(HttpServletRequest request,HttpSession session,@ModelAttribute("gameAlarmUserVO") GameAlarmUserVO gameAlarmUserVO,BindingResult result,Model model)throws Exception {
		
		//super.fillGames(gameAlarmUserVO.getGameId(), model);
		
		Games game=super.getSessionGames(request);
		
		List<GameAlarmUserVO> gameAlarmUserVOList=this.gameAlarmUserService.getGameAlarmUserList(game.getId());
		List<Long> userIds=new ArrayList<Long>();
		for (GameAlarmUserVO gameAlarmUserVO2 : gameAlarmUserVOList) {
			userIds.add(gameAlarmUserVO2.getEntity().getUserId());
		}
		gameAlarmUserVO.setUserIds(userIds);
		
//		gamesService.getGames(game.getSnid(), game.getGameid());
		
		List<UserGamesVO> userGamesVOList=this.userGamesService.getGameUserList(gamesService.getGames(game.getSnid(), game.getGameid()).getId());
		model.addAttribute("userGamesVOList", userGamesVOList);
		
		return "games/alarmuser/add.jsp";
	}
	
	@RequestMapping(value = {"/add.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String add(HttpServletRequest request,HttpSession session,@ModelAttribute("gameAlarmUserVO") GameAlarmUserVO gameAlarmUserVO,BindingResult result,Model model)throws Exception {
		
		Games game=super.getSessionGames(request);
		
		List<Long> userIds=gameAlarmUserVO.getUserIds();
		if(userIds!=null&&!userIds.isEmpty()){
			this.gameAlarmUserService.addGameAlarmUser(game.getId(), userIds);
		}
		
		return super.redirect("/panel_bi/alarm_user/toList.ac?gameId="+game.getGameid()+"&snid="+game.getSnid());
	}
	
	@RequestMapping(value = {"/del.ac"}, method = {RequestMethod.GET,RequestMethod.POST })
	public String del(HttpServletRequest request,HttpSession session,@ModelAttribute("gameAlarmUserVO") GameAlarmUserVO gameAlarmUserVO,BindingResult result,Model model)throws Exception {
		Games game=super.getSessionGames(request);
		this.gameAlarmUserService.removeById(gameAlarmUserVO.getEntity().getId());
		
		return super.redirect("/panel_bi/alarm_user/toList.ac?gameId="+game.getGameid()+"&snid="+game.getSnid());
		
	}

	
	
}

