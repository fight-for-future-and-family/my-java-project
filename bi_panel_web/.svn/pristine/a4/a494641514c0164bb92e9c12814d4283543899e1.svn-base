package com.hoolai.panel.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.manage.model.Groups;
import com.hoolai.manage.model.Users;
import com.jian.tools.util.HttpClientUtils;
import com.jian.tools.util.ServletUtils;


public abstract class AbstractPanelController {

	protected static final String PROCESS_STATUS_KEY="p_s";
	
	protected static final int PROCESS_STATUS_SUCCESS=0;
	
	protected static final int PROCESS_STATUS_FAIL=-1;
	/**
	 * panel_session_user_key
	 */
	public static final String SESSION_USER_KEY="p_s_u_k";
	
	public static final String SESSION_USER_LAST_URL_KEY="jianUserLastUrl";
	
	public static final int PAGE_SIZE = 10;
	
	public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	// 默认月
	public static final String BEGIN_DATE = "beginTime";
	public static final String END_DATE = "endTime";
	
	// 默认周
	public static final String EC_BEGIN_DATE = "ec_beginTime";
	public static final String EC_END_DATE = "ec_endTime";
	
	@Autowired
	private GamesService gamesService;
	
	public String forward(String url){
		return "forward:"+url;
	}
	
	public String redirect(String url){
		return "redirect:"+url;
	}
	
	public String redirect404(Map<String,String> params){
		if(params!=null&&!params.isEmpty()){
			try {
				String url=HttpClientUtils.concatUrlSerialParams("/404.ac", params, true);
				return redirect(url);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return redirect("/404.ac");
	}
	
	public String redirect404(){
		return redirect404(null);
	}
	
	protected void setSessionDate(HttpServletRequest request,String beginDate,String endDate){
		HttpSession session=ServletUtils.getSession(request);
		session.setAttribute(BEGIN_DATE, beginDate);
		session.setAttribute(END_DATE, endDate);
	}
	
	protected void setSessionWeekDate(HttpServletRequest request,String beginDate,String endDate){
		HttpSession session=ServletUtils.getSession(request);
		session.setAttribute(EC_BEGIN_DATE, beginDate);
		session.setAttribute(EC_END_DATE, endDate);
	}
	
	protected void setSessionUsers(HttpServletRequest request,Users users) {
		if(users==null){
			throw new RuntimeException("set session users is null!");
		}
		HttpSession session=ServletUtils.getSession(request);
		session.setAttribute(SESSION_USER_KEY, users);
	}
	
	protected void removeSessionUsers(HttpServletRequest request) {
		HttpSession session=ServletUtils.getSession(request);
		session.invalidate();
	}
	
	protected Users getSessionUsers(HttpServletRequest request){
		Users users=null;
		HttpSession session=ServletUtils.getSession(request);
		users=(Users)session.getAttribute(SESSION_USER_KEY);
		return users;
	}
	
	protected void setSession(HttpServletRequest request,String key,Object obj) {
		if(obj==null){
			throw new RuntimeException("set session users is null!");
		}
		HttpSession session=ServletUtils.getSession(request);
		session.setAttribute(key, obj);
	}
	
	protected void removeSession(HttpServletRequest request,String key) {
		HttpSession session=ServletUtils.getSession(request);
		session.removeAttribute(key);
	}
	
	protected Object getSession(HttpServletRequest request,String key){
		HttpSession session=ServletUtils.getSession(request);
		return session.getAttribute(key);
	}
	
	protected boolean isAdmin(HttpServletRequest request) {
		HttpSession session=ServletUtils.getSession(request);
		List<Groups> groups=(List<Groups>)session.getAttribute("groups");
		if(groups==null||groups.isEmpty()){
			return false;
		}
		for(Groups group:groups){
			if(group.getId().equals(Groups.ADMIN_GROUP)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean isOutSideUser(HttpServletRequest request) {
		HttpSession session=ServletUtils.getSession(request);
		List<Groups> groups=(List<Groups>)session.getAttribute("groups");
		if(groups==null||groups.isEmpty()){
			return false;
		}
		for(Groups group:groups){
			if(group.getId().equals(Groups.OUTSIDERS_GROUP)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean isWanDa(HttpServletRequest request) {
		HttpSession session=ServletUtils.getSession(request);
		List<Groups> groups=(List<Groups>)session.getAttribute("groups");
		if(groups==null||groups.isEmpty()){
			return false;
		}
		for(Groups group:groups){
			if(group.getId().equals(Groups.WANDA_GROUP)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean isProduct(HttpServletRequest request) {
		HttpSession session=ServletUtils.getSession(request);
		List<Groups> groups=(List<Groups>)session.getAttribute("groups");
		if(groups==null||groups.isEmpty()){
			return false;
		}
		for(Groups group:groups){
			if(group.getId().equals(Groups.PRODUCT_GROUP)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean isPM(HttpServletRequest request) {
		HttpSession session=ServletUtils.getSession(request);
		List<Groups> groups=(List<Groups>)session.getAttribute("groups");
		if(groups==null||groups.isEmpty()){
			return false;
		}
		for(Groups group:groups){
			if(group.getId().equals(Groups.PM_GROUP)){
				return true;
			}
		}
		return false;
	}
	
	protected boolean isLeader(HttpServletRequest request) {
		HttpSession session=ServletUtils.getSession(request);
		List<Groups> groups=(List<Groups>)session.getAttribute("groups");
		if(groups==null||groups.isEmpty()){
			return false;
		}
		for(Groups group:groups){
			if(group.getId().equals(Groups.BOSS_GROUP)){
				return true;
			}
		}
		return false;
	}
	
//	protected Games getSessionGames(HttpServletRequest request) {
//		HttpSession session=ServletUtils.getSession(request);
//		Games games=(Games)session.getAttribute("game");
//		if(games == null){
//			if(StringUtils.isEmpty(request.getParameter("id"))
//					&& StringUtils.isEmpty(request.getParameter("entity.id"))){
//			   return null;
//			}else if(!StringUtils.isEmpty(request.getParameter("id"))){
//				long id = Long.valueOf(request.getParameter("id"));
//				games = this.getSessionGames(request, id);
//			}else if(!StringUtils.isEmpty(request.getParameter("entity.id"))){
//				long id = Long.valueOf(request.getParameter("entity.id"));
//				games = this.getSessionGames(request, id);
//			}
//		}
//		return games;
//	}
	
	protected Games getSessionGames(HttpServletRequest request) {
		HttpSession session=ServletUtils.getSession(request);
//		Games games=(Games)session.getAttribute("game");
		Games games = null;
		if(games == null){
			if(!StringUtils.isEmpty(request.getParameter("snid"))
					&& !StringUtils.isEmpty(request.getParameter("gameId"))){
				games = gamesService.getGames(request.getParameter("snid"), request.getParameter("gameId"));
				if(games==null && !StringUtils.isEmpty(request.getParameter("gameId")) 
						&& !StringUtils.isEmpty(request.getParameter("gameid"))){
					games = gamesService.getGames(request.getParameter("snid"), request.getParameter("gameid"));
				}
			}else if(StringUtils.isEmpty(request.getParameter("id"))
					&& StringUtils.isEmpty(request.getParameter("entity.id"))){
			   return null;
			}else if(!StringUtils.isEmpty(request.getParameter("id"))){
				long id = Long.valueOf(request.getParameter("id"));
				games = this.getSessionGames(request, id);
			}else if(!StringUtils.isEmpty(request.getParameter("entity.id"))){
				long id = Long.valueOf(request.getParameter("entity.id"));
				games = this.getSessionGames(request, id);
			}
		}
		removeSession(request, "game");
		removeSession(request, "snid");
		removeSession(request, "gameId");
//		games = this.getSessionGames(request);
		setSession(request, "game", games);
		setSession(request, "snid", games.getSnid());
		setSession(request, "gameId", games.getGameid());
		return games;
	}
	
	protected Games getSessionGames(HttpServletRequest request,long id) {
		HttpSession session=ServletUtils.getSession(request);
		List<Games> gameList=(List<Games>) session.getAttribute("games");
		for(Games game:gameList){
			if(game.getId() == id){
				return game;
			}
		}
		return null;
	}
	
	protected Games getSessionGames(HttpServletRequest request,String snid,String gameid) {
		HttpSession session=ServletUtils.getSession(request);
		HashMap<String,Games> gamesMap= (HashMap<String, Games>) session.getAttribute("biGamesMap");
		return gamesMap.get(snid+"_"+gameid);
	}
	
	protected Map<String,Object> findPageInfo(HttpServletRequest request){
		
		Map<String,Object> pageInfo = new HashMap<String, Object>();
		
		//数据起始位置
	     String start = request.getParameter("start");
	    //数据长度
	    String length = request.getParameter("length");
	    //获取用户过滤框里的字符
	    String searchValue = request.getParameter("search[value]");
	    //获取排序的字段
	    String orderCol = request.getParameter("order[0][column]");
	    String orderType = request.getParameter("order[0][dir]");
	    
	    pageInfo.put("start", StringUtils.isEmpty(start)? 0 : Long.valueOf(start));
	    pageInfo.put("length", StringUtils.isEmpty(length)? 0 : Long.valueOf(length));
	    pageInfo.put("searchValue", StringUtils.isEmpty(searchValue)? null : searchValue);
	    pageInfo.put("orderCol", orderCol);
	    pageInfo.put("orderType", orderType);
	    
	    return pageInfo;
	}
	
	protected String getVerificationCode(HttpServletRequest request){
		HttpSession session=ServletUtils.getSession(request);
		String code = (String) session.getAttribute("sessRandomCode");
		return code;
	}
	
	protected void removeVerificationCode(HttpServletRequest request){
		HttpSession session=ServletUtils.getSession(request);
		session.removeAttribute("sessRandomCode");
	}
}
