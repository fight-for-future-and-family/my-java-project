package com.hoolai.panel.web.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.core.Constant;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.adtracking.AdShortUrl;
import com.hoolai.bi.report.model.entity.adtracking.AdtrackingResult;
import com.hoolai.bi.report.service.AdShortUrlService;
import com.hoolai.bi.report.vo.AdShortUrlVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.manage.model.Platforms;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.PlatformsService;
import com.hoolai.manage.vo.PlatformsVO;
import com.jian.tools.util.HttpClientUtils;
import com.jian.tools.util.JSONUtils;

@Controller
@RequestMapping("/panel_bi/adTracking")
public class AdTrackingController extends AbstractPanelController{
	
	@Autowired
	private PlatformsService platformsService;
	@Autowired
	private AdShortUrlService adShortUrlService;

	@RequestMapping(value = {"/toAdTracking.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toAdTracking(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		Games games = this.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		model.addAttribute("platforms",platformsService.selectPlatforms(new PlatformsVO(new Platforms())));
		return "adTracking/shortUrl.jsp";
	}
	
	@RequestMapping(value = {"/toAdShortAdd.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toAdShortAdd(HttpServletRequest request,HttpSession session, Model model)throws Exception {
		Users users = getSessionUsers(request);
		Games games = this.getSessionGames(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		model.addAttribute("platforms",platformsService.selectPlatforms(new PlatformsVO(new Platforms())));
		
		return "adTracking/addShortUrl.jsp";
	}
	
	@RequestMapping(value = {"/getShortUrlByPlatform.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getShortUrlByPlatform(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		AdShortUrl adShortUrl = new AdShortUrl(games.getId(),games.getSnid(),games.getGameid());
		String platformCode = request.getParameter("platformName").split("_", -1)[1];
		adShortUrl.setPlatformCode(platformCode);
		
		adShortUrl = adShortUrlService.getShortUrlByPlatform(adShortUrl);
		
		ret.put("data", adShortUrl);
		return ret;
	}
	
	@RequestMapping(value = {"/getShortUrl.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getShortUrl(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		AdShortUrl adShortUrl = new AdShortUrl(games.getId(),games.getSnid(),games.getGameid());
		AdShortUrlVO adShortUrlVO = new AdShortUrlVO(adShortUrl);
		
		Map<String, Object> pageInfo = super.findPageInfo(request);
		
		long count = adShortUrlService.selectCount(adShortUrlVO);
		ret.put("recordsTotal", count);
		
		adShortUrlVO.setSearchValue((String)pageInfo.get("searchValue"));
		long scount = adShortUrlService.selectCount(adShortUrlVO);
		ret.put("recordsFiltered", scount);
		
		adShortUrlVO.setOffset((Long) pageInfo.get("start"));
		adShortUrlVO.setRows((Long) pageInfo.get("length"));
		ret.put("data", adShortUrlService.getMatch(adShortUrlVO));
		ret.put("game", games);
		return ret;
	}
	
	@RequestMapping(value = {"/batchSaveShortUrl.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> batchSaveShortUrl(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String platformNamestr = request.getParameter("platformName");
		String appUrl = request.getParameter("appUrl");
		String status = request.getParameter("status");
		String redirect = request.getParameter("redirect");
		String adPlace = request.getParameter("adPlace");
		String id = request.getParameter("id");
		String sign = request.getParameter("sign");
		String matchRule = request.getParameter("matchRule");
		String terminalType = request.getParameter("terminalType");
		String systemType = request.getParameter("systemType");
		String encryptKey = request.getParameter("encryptKey");
		String size = request.getParameter("size");
		
		if(StringUtils.isEmpty(platformNamestr)
			|| StringUtils.isEmpty(appUrl)
			|| StringUtils.isEmpty(sign)
			|| StringUtils.isEmpty(redirect)
			|| StringUtils.isEmpty(adPlace)
			|| StringUtils.isEmpty(status)
			|| StringUtils.isEmpty(matchRule)
			|| StringUtils.isEmpty(encryptKey)
			|| StringUtils.isEmpty(systemType)
			|| StringUtils.isEmpty(terminalType)
			|| StringUtils.isEmpty(size)){
			ret.put("msg", "1");//内容不能为空，请重新输入！
			return ret;
		}else{
			if("0".equals(terminalType)){
				systemType = "0";
			}// PC 默认系统为0
		}
		
		String msgStr = "";
		AdShortUrl adShortUrl = new AdShortUrl(games.getId(), games.getSnid(), games.getGameid());
		AdShortUrl temp = null;
		int num = Integer.valueOf(size);
		for (int i = 1; i <= num; i++) {
			adShortUrl = new AdShortUrl(games.getId(), games.getSnid(), games.getGameid());
			String[] platformNames = platformNamestr.split("_", -1);
			String platformId = platformNames[0];
			String platformCode = platformNames[1];
			String platformName = platformNames[2];
			adShortUrl.setAppUrl(appUrl);
			adShortUrl.setPlatformCode(platformCode);
			adShortUrl.setPlatformId(Long.valueOf(platformId));
			adShortUrl.setPlatformName(platformName);
			adShortUrl.setStatus(Integer.valueOf(status));
			adShortUrl.setAdPlace(adPlace + String.valueOf(i));
			adShortUrl.setRedirect(Integer.valueOf(redirect));
			adShortUrl.setSign(sign);
			adShortUrl.setMatchRule(Integer.valueOf(matchRule));
			adShortUrl.setSystemType(Integer.valueOf(systemType));
			adShortUrl.setTerminalType(Integer.valueOf(terminalType));
			adShortUrl.setEncryptKey(encryptKey);
			temp = adShortUrlService.getByGameId(new AdShortUrlVO(adShortUrl));
			
			if(temp == null){
				adShortUrl.setIsCallback(1);
				adShortUrlService.saveEntity(adShortUrl);
				
				String jsonStr = JSONUtils.toJSON(adShortUrl);
				Map<String,String> jsonMap = new HashMap<String, String>();
				jsonMap.put("bodyDatas", jsonStr);
				HttpClientUtils.executePostRequest(Constant.PLATFORM_SYN_URL+"insert/", jsonMap, "UTF-8");
			}else{
				msgStr = msgStr + (adShortUrl.getAdPlace()) + "、";
				continue;
			}
		}
		
		ret.put("msg", "2");
		if(msgStr.length()>0){
			ret.put("msgStr", msgStr);
		}
		return ret;
	}
	
	@RequestMapping(value = {"/saveShortUrl.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> saveShortUrl(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String platformNamestr = request.getParameter("platformName");
		String appUrl = request.getParameter("appUrl");
		String status = request.getParameter("status");
		String redirect = request.getParameter("redirect");
		String adPlace = request.getParameter("adPlace");
		String id = request.getParameter("id");
		String sign = request.getParameter("sign");
		String matchRule = request.getParameter("matchRule");
		String terminalType = request.getParameter("terminalType");
		String systemType = request.getParameter("systemType");
		String encryptKey = request.getParameter("encryptKey");
		
		if(StringUtils.isEmpty(platformNamestr)
			|| StringUtils.isEmpty(appUrl)
			|| StringUtils.isEmpty(sign)
			|| StringUtils.isEmpty(redirect)
			|| StringUtils.isEmpty(adPlace)
			|| StringUtils.isEmpty(status)
			|| StringUtils.isEmpty(matchRule)
			|| StringUtils.isEmpty(encryptKey)
			|| StringUtils.isEmpty(systemType)
			|| StringUtils.isEmpty(terminalType)){
			ret.put("msg", "1");//内容不能为空，请重新输入！
			return ret;
		}else{
			if("0".equals(terminalType)){
				systemType = "0";
			}// PC 默认系统为0
		}
		
		AdShortUrl adShortUrl = new AdShortUrl(games.getId(), games.getSnid(), games.getGameid());
		AdShortUrl temp = null;
		if(StringUtils.isEmpty(id)){
			String[] platformNames = platformNamestr.split("_", -1);
			
			String platformId = platformNames[0];
			String platformCode = platformNames[1];
			String platformName = platformNames[2];
			
			adShortUrl.setAppUrl(appUrl);
			adShortUrl.setPlatformCode(platformCode);
			adShortUrl.setPlatformId(Long.valueOf(platformId));
			adShortUrl.setPlatformName(platformName);
			adShortUrl.setStatus(Integer.valueOf(status));
			adShortUrl.setAdPlace(adPlace);
			adShortUrl.setRedirect(Integer.valueOf(redirect));
			adShortUrl.setSign(sign);
			adShortUrl.setMatchRule(Integer.valueOf(matchRule));
			adShortUrl.setSystemType(Integer.valueOf(systemType));
			adShortUrl.setTerminalType(Integer.valueOf(terminalType));
			adShortUrl.setEncryptKey(encryptKey);
			
			temp = adShortUrlService.getByGameId(new AdShortUrlVO(adShortUrl));
		}else{
			
			temp = adShortUrlService.getById(Long.valueOf(id));
			
			adShortUrl.setId(Long.valueOf(id));
			adShortUrl.setAppUrl(appUrl);
			adShortUrl.setStatus(Integer.valueOf(status));
			adShortUrl.setSign(sign);
			adShortUrl.setAdPlace(adPlace);
			adShortUrl.setRedirect(Integer.valueOf(redirect));
			adShortUrl.setMatchRule(Integer.valueOf(matchRule));
			adShortUrl.setSystemType(Integer.valueOf(systemType));
			adShortUrl.setTerminalType(Integer.valueOf(terminalType));
			adShortUrl.setEncryptKey(encryptKey);
		}
		
		if(temp == null){
			adShortUrl.setIsCallback(1);
			adShortUrlService.saveEntity(adShortUrl);
			
			
			String jsonStr = JSONUtils.toJSON(adShortUrl);
			Map<String,String> jsonMap = new HashMap<String, String>();
			jsonMap.put("bodyDatas", jsonStr);
			HttpClientUtils.executePostRequest(Constant.PLATFORM_SYN_URL+"insert/", jsonMap, "UTF-8");
		}else{
			adShortUrl.setId(temp.getId());
			adShortUrlService.modifyEntitySelective(adShortUrl);
			
			String jsonStr = JSONUtils.toJSON(adShortUrl);
			Map<String,String> jsonMap = new HashMap<String, String>();
			jsonMap.put("bodyDatas", jsonStr);
			HttpClientUtils.executePostRequest(Constant.PLATFORM_SYN_URL+"update/", jsonMap, "UTF-8");
		}
		
		ret.put("msg", "2");// 修改成功
		return ret;
	}
	
	@RequestMapping(value = {"/delShortUrl.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delShortUrl(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String id = request.getParameter("id");
		
		AdShortUrl adShortUrl = new AdShortUrl(games.getId(), games.getSnid(), games.getGameid());
		if(StringUtils.isEmpty(id)){
			ret.put("msg", "1");//不存在
			return ret;
		}else{
			adShortUrl.setId(Long.valueOf(id));
			//adShortUrl.setStatus(status.equals("有效") ? 1 : status.equals("无效") ? 0 : Integer.valueOf(status));
		}
		
		AdShortUrl temp = adShortUrlService.getByGameId(new AdShortUrlVO(adShortUrl));
		if(temp == null){
			ret.put("msg", "1");//不存在
			return ret;
		}else{
			adShortUrlService.updateById(temp.getId());
			
//			String jsonStr = JSONUtils.toJSON(temp);
//			Map<String,String> jsonMap = new HashMap<String, String>();
//			jsonMap.put("bodyDatas", jsonStr);
//			HttpClientUtils.executePostRequest(Constant.PLATFORM_SYN_URL+"del/", jsonMap, "UTF-8");
		}
		
		ret.put("msg", "2");//删除成功
		return ret;
	}
	
	@RequestMapping(value = {"/queryInstalls.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> queryInstalls(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String suid = request.getParameter("suid");
		String idfa = request.getParameter("idfa");
		if(StringUtils.isEmpty(idfa) || StringUtils.isEmpty(suid)){
			ret.put("msg", "1");//null
			return ret;
		}
		
		Map<String,String> jsonMap = new HashMap<String, String>();
		jsonMap.put("idfa", idfa);
		String retStr = HttpClientUtils.executePostRequest(Constant.TRACKING_URL+"queryInstalls/"+suid+"/", jsonMap, "UTF-8");
		
		QueryInstallResult map = JSONUtils.fromJSON(retStr, QueryInstallResult.class);
		if("0".equals(map.getStatus())){
			ret.put("msg", "2");
			ret.put("installs", map.getData());
			return ret;
		}else{
			ret.put("msg", map.getStatus());//null
			return ret;
		}
	}
	
	static class QueryInstallResult implements Serializable{
		
		private static final long serialVersionUID = 2539263746577623501L;
		private String status;
		private String msg;
		private List<AdtrackingResult> data;
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public List<AdtrackingResult> getData() {
			return data;
		}
		public void setData(List<AdtrackingResult> data) {
			this.data = data;
		}
	}
}
