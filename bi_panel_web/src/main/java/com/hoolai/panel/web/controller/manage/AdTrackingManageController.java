package com.hoolai.panel.web.controller.manage;

import java.util.ArrayList;
import java.util.Date;
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
import com.hoolai.bi.report.model.entity.adtracking.AdShortUrl;
import com.hoolai.bi.report.service.AdShortUrlService;
import com.hoolai.bi.report.vo.AdShortUrlVO;
import com.hoolai.manage.model.Platforms;
import com.hoolai.manage.model.PlatformsParams;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;
import com.hoolai.manage.service.PlatformsParamsService;
import com.hoolai.manage.service.PlatformsService;
import com.hoolai.manage.vo.PlatformsParamsVO;
import com.hoolai.manage.vo.PlatformsVO;
import com.hoolai.manage.vo.PlatformsVO.InteractiveDatas;
import com.hoolai.manage.vo.PlatformsVO.PlatformsParam;
import com.hoolai.manage.vo.PlatformsVO.PlatformsCallback;
import com.hoolai.panel.web.controller.AbstractPanelController;
import com.jian.tools.util.HttpClientUtils;
import com.jian.tools.util.JSONUtils;

@Controller
@RequestMapping("/panel_manage/adManage")
public class AdTrackingManageController extends AbstractPanelController{
	
	@Autowired
	private PlatformsService platformsService;
	@Autowired
	private AdShortUrlService adShortUrlService;
	@Autowired
	private GroupUsersService groupUserService;
	@Autowired
	private PlatformsParamsService paramsService;

	@RequestMapping(value = {"/toadManage.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toAdTracking(HttpServletRequest request,HttpSession session,@ModelAttribute("platformsVO") PlatformsVO platformsVO,BindingResult result,Model model)throws Exception {
		
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		String view = request.getParameter("view");
		view = view == null ? (String)request.getAttribute("view") : view;
		
		if("platforms".equals(view)){
			return "manage/adTracking/platforms.jsp";
		}else if("addPlatforms".equals(view)){
			return "manage/adTracking/addPlatforms.jsp";
		}else if("updatePlatforms".equals(view)){
			String id = request.getParameter("platformsId");
			id = id == null ? (String)request.getAttribute("platformsId") : id;
			
			PlatformsParams params = new PlatformsParams();
			params.setPlatformsId(Long.valueOf(id));
			PlatformsParamsVO paramsVO = new PlatformsParamsVO(params);
			List<PlatformsParams>  platformsParams = paramsService.selectList(paramsVO);
			model.addAttribute("platformsParams", JSONUtils.toJSON(platformsParams));
			Platforms platforms = platformsService.getById(Long.valueOf(id));
			platformsVO.setEntity(platforms);
			return "manage/adTracking/updatePlatforms.jsp";
		}
		return "manage/adTracking/platforms.jsp";
	}
	
	@RequestMapping(value = {"/getPlatforms.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getPlatforms(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Platforms platforms = new Platforms();
		PlatformsVO platformsVO = new PlatformsVO(platforms);
		
       Map<String, Object> pageInfo = super.findPageInfo(request);
		
		long count = platformsService.selectCount(platformsVO);
		ret.put("recordsTotal", count);
		
		platformsVO.setSearchValue((String)pageInfo.get("searchValue"));
		long scount = platformsService.selectCount(platformsVO);
		ret.put("recordsFiltered", scount);
		
		platformsVO.setOffset((Long) pageInfo.get("start"));
		platformsVO.setRows((Long) pageInfo.get("length"));
		  
		ret.put("data", platformsService.getMatch(platformsVO));
		return ret;
	}
	
	@RequestMapping(value = {"/addPlatforms.ac"}, method = { RequestMethod.POST })
	@SuppressWarnings("unchecked")
	public String addPlatforms(HttpServletRequest request,HttpSession session,
			@ModelAttribute("platformsVO") PlatformsVO platformsVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		String name = platformsVO.getEntity().getName();
		String code = platformsVO.getEntity().getCode();
		if(StringUtils.isEmpty(name)
			|| StringUtils.isEmpty(code)){
			request.setAttribute("view", "addPlatforms");
			model.addAttribute("msg", "带*的字段不能为空~");
			return this.toAdTracking(request, session, platformsVO, result, model);
		}
		
		platformsVO.getEntity().setStatus(1);
		platformsService.saveEntity(platformsVO.getEntity());
		List<PlatformsParamsVO> params = param(platformsVO);
		for(PlatformsParamsVO platformsParams:params){
			paramsService.saveEntity(platformsParams.getEntity());
		}
		try{
			String jsonStr = JSONUtils.toJSON(params);
			InteractiveDatas interactiveDatas = new InteractiveDatas("PlatformsParams", "add", jsonStr);
			Map<String,String> jsonMap = new HashMap<String, String>();
			jsonMap.put("bodyDatas", JSONUtils.toJSON(interactiveDatas));
			String retStr = HttpClientUtils.executePostRequest(Constant.PLATFORM_PARAM_SYN_URL, jsonMap, "UTF-8");
			Map<String,Object> map = JSONUtils.fromJSON(retStr, HashMap.class);
			if("-1".equals(map.get("status"))){
				throw new RuntimeException();
			}
		}catch (Exception e) {
			e.printStackTrace();
			paramsService.removeByPlatformId(platformsVO.getEntity().getId());
			platformsService.removeById(platformsVO.getEntity().getId());
			request.setAttribute("view", "addPlatforms");
			model.addAttribute("msg", "未同步成功~");
			return this.toAdTracking(request, session, platformsVO, result, model);
		}
		
		return "manage/adTracking/platforms.jsp";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/updatePlatforms.ac"}, method = { RequestMethod.POST })
	public String updatePlatforms(HttpServletRequest request,HttpSession session,
			@ModelAttribute("platformsVO") PlatformsVO platformsVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		Long platformId = platformsVO.getEntity().getId();
		String name = platformsVO.getEntity().getName();
		String code = platformsVO.getEntity().getCode();
		if(platformId == null || platformId == 0
				|| StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(code)){
				request.setAttribute("view", "updatePlatforms");
				model.addAttribute("msg", "带*的字段不能为空~");
				return this.toAdTracking(request, session, platformsVO, result, model);
			}
		
		Platforms temp = platformsService.getById(platformsVO.getEntity().getId());
		AdShortUrl adShortUrl = new AdShortUrl();
		boolean isChange = false;
		if(!platformsVO.getEntity().getName().equals(temp.getName())
				|| !platformsVO.getEntity().getCode().equals(temp.getCode())){
			
			//同步修改广告投放中的平台CODE
			adShortUrl.setPlatformId(temp.getId());
			adShortUrl.setStatus(platformsVO.getEntity().getStatus());
			adShortUrl.setPlatformCode(platformsVO.getEntity().getCode());
			adShortUrl.setPlatformName(platformsVO.getEntity().getName());
			
			// 同步
			AdShortUrlVO adShortUrlVO = new AdShortUrlVO(adShortUrl);
			List<AdShortUrl> updateList = adShortUrlService.getMatch(adShortUrlVO);
			for(AdShortUrl adUrl:updateList){
				String jsonStr = JSONUtils.toJSON(adUrl);
				Map<String,String> jsonMap = new HashMap<String, String>();
				jsonMap.put("bodyDatas", jsonStr);
				HttpClientUtils.executePostRequest(Constant.PLATFORM_SYN_URL+"update/", jsonMap, "UTF-8");
			}
			isChange = true;
		}
		
		List<PlatformsParamsVO> params = param(platformsVO);
		
		//为未同步成功做个备份
		PlatformsParams paramsTemp = new PlatformsParams();
		paramsTemp.setPlatformsId(Long.valueOf(platformId));
		PlatformsParamsVO paramsVO = new PlatformsParamsVO(paramsTemp);
		List<PlatformsParams>  platformsParamsTemp = paramsService.selectList(paramsVO);

		// 先删后插入
		paramsService.removeByPlatformId(Long.valueOf(platformId));
		for(PlatformsParamsVO platformsParams:params){
			paramsService.saveEntity(platformsParams.getEntity());
		}
		
		try{
			String jsonStr = JSONUtils.toJSON(params);
			InteractiveDatas interactiveDatas = new InteractiveDatas("PlatformsParams", "update", jsonStr);
			Map<String,String> jsonMap = new HashMap<String, String>();
			jsonMap.put("bodyDatas", JSONUtils.toJSON(interactiveDatas));
			String retStr = HttpClientUtils.executePostRequest(Constant.PLATFORM_PARAM_SYN_URL, jsonMap, "UTF-8");
			Map<String,Object> map = JSONUtils.fromJSON(retStr, HashMap.class);
			if("-1".equals(map.get("status"))){
				throw new RuntimeException();
			}
		}catch (Exception e) {
			e.printStackTrace();
			paramsService.removeByPlatformId(platformsVO.getEntity().getId());
			for(PlatformsParams temp1:platformsParamsTemp){
				paramsService.saveEntity(temp1);
			}
			request.setAttribute("view", "updatePlatforms");
			request.setAttribute("platformsId", String.valueOf(platformsVO.getEntity().getId()));
			model.addAttribute("msg", "未同步成功~");
			return this.toAdTracking(request, session, platformsVO, result, model);
		}
		
		// 最后存储本地
		if(isChange){
			adShortUrlService.modifyByPlatforms(adShortUrl);
			platformsService.modifyEntitySelective(platformsVO.getEntity());
		}
		return "manage/adTracking/platforms.jsp";
	}
	
	private List<PlatformsParamsVO> param(PlatformsVO platformsVO){
		String paramValues = platformsVO.getParamValues();
		PlatformsVO.PlatformsParamsTemp temp = JSONUtils.fromJSON(paramValues, PlatformsVO.PlatformsParamsTemp.class);
		
		List<PlatformsParamsVO> params = new ArrayList<PlatformsParamsVO>();
		
		// 请求
		PlatformsParam param = temp.getParamObj();
		addParams(platformsVO, params, param,1);
		
		// 回调
		PlatformsCallback callback = temp.getCallbackObj();
		addParams(platformsVO, params, callback,2);
		
		return params;
	}

	private void addParams(PlatformsVO platformsVO,
			List<PlatformsParamsVO> params, PlatformsParam param,int mapperType) {
		
		
		if(!StringUtils.isEmpty(param.getMac())){
			params.add(getPlatformsParams(platformsVO, param.getMac(),"mac",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getMacMd5())){
			params.add(getPlatformsParams(platformsVO, param.getMacMd5(),"macMd5",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getIfa())){
			params.add(getPlatformsParams(platformsVO, param.getIfa(),"ifa",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getIfaMd5())){
			params.add(getPlatformsParams(platformsVO, param.getIfaMd5(),"ifaMd5",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getIp())){
			params.add(getPlatformsParams(platformsVO, param.getIp(),"ip",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getUserAgent())){
			params.add(getPlatformsParams(platformsVO, param.getUserAgent(),"userAgent",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getExtra())){
			
			String extras[]= param.getExtra().split(",");
			for(String str:extras){
				String extra[] = str.split(":");
				params.add(getPlatformsParams(platformsVO, extra[1],extra[0],mapperType,1));
			}
		}
	}
	
	
	/*
	 * addCallbackParams
	 */
	private void addParams(PlatformsVO platformsVO,
			List<PlatformsParamsVO> params, PlatformsCallback param,int mapperType) {
		
		if(!StringUtils.isEmpty(param.getId())){
			params.add(getPlatformsParams(platformsVO, param.getId(), "id", mapperType, 0));
		}
		
		if(!StringUtils.isEmpty(param.getMac())){
			params.add(getPlatformsParams(platformsVO, param.getMac(),"mac",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getMacMd5())){
			params.add(getPlatformsParams(platformsVO, param.getMacMd5(),"macMd5",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getIfa())){
			params.add(getPlatformsParams(platformsVO, param.getIfa(),"ifa",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getIfaMd5())){
			params.add(getPlatformsParams(platformsVO, param.getIfaMd5(),"ifaMd5",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getIp())){
			params.add(getPlatformsParams(platformsVO, param.getIp(),"ip",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getUserAgent())){
			params.add(getPlatformsParams(platformsVO, param.getUserAgent(),"userAgent",mapperType,0));
		}
		if(!StringUtils.isEmpty(param.getExtra())){
			
			String extras[]= param.getExtra().split(",");
			for(String str:extras){
				String extra[] = str.split(":");
				params.add(getPlatformsParams(platformsVO, extra[1],extra[0],mapperType,1));
			}
		}
	}
	
	
	
	
	

	private PlatformsParamsVO getPlatformsParams(PlatformsVO platformsVO,String paramsCode,String mapperColumn,
			Integer mapperType,Integer isExtra) {
		PlatformsParams params = new PlatformsParams();
		params.setIsExtraParams(isExtra);
		params.setCreateTime(new Date());
		params.setMapperColumn(mapperColumn);
		params.setParamsCode(paramsCode);
		params.setPlatformsId(platformsVO.getEntity().getId());
		params.setPlatformsName(platformsVO.getEntity().getName());
		params.setPlatformsCode(platformsVO.getEntity().getCode());
		params.setRemark("");
		params.setStatus(1);
		params.setMapperType(mapperType);
		PlatformsParamsVO vo = new PlatformsParamsVO(params);
		return vo;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/delPlatforms.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delDimensionData(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String id = request.getParameter("id");
		
		if(StringUtils.isEmpty(id)){
			ret.put("msg", "1");//不存在
			return ret;
		}
		
		Platforms temp = platformsService.getById(Long.valueOf(id));
		
		if(temp == null){
			ret.put("msg", "1");//不存在
			return ret;
		}else{
			AdShortUrl adShortUrl = new AdShortUrl();
			adShortUrl.setPlatformId(temp.getId());
			
			// 同步
			AdShortUrlVO adShortUrlVO = new AdShortUrlVO(adShortUrl);
			List<AdShortUrl> updateList = adShortUrlService.getMatch(adShortUrlVO);
			for(AdShortUrl adUrl:updateList){
				String jsonStr = JSONUtils.toJSON(adUrl);
				Map<String,String> jsonMap = new HashMap<String, String>();
				jsonMap.put("bodyDatas", jsonStr);
				HttpClientUtils.executePostRequest(Constant.PLATFORM_SYN_URL+"del/", jsonMap, "UTF-8");
			}
			
			PlatformsParams params = new PlatformsParams();
			params.setPlatformsId(temp.getId());
			List<PlatformsParamsVO> list = new ArrayList<PlatformsParamsVO>();
			PlatformsParamsVO paramsVO = new PlatformsParamsVO(params);
			list.add(paramsVO);
			
			String jsonStr = JSONUtils.toJSON(list);
			InteractiveDatas interactiveDatas = new InteractiveDatas("PlatformsParams", "delete", jsonStr);
			Map<String,String> jsonMap = new HashMap<String, String>();
			jsonMap.put("bodyDatas", JSONUtils.toJSON(interactiveDatas));
			String retStr = HttpClientUtils.executePostRequest(Constant.PLATFORM_PARAM_SYN_URL, jsonMap, "UTF-8");
			Map<String,Object> map = JSONUtils.fromJSON(retStr, HashMap.class);
			if("-1".equals(map.get("status"))){
				throw new RuntimeException();
			}
			
			paramsService.removeByPlatformId(temp.getId());
			adShortUrlService.delByPlatforms(adShortUrl);
			platformsService.removeById(temp.getId());
		}
		
		ret.put("msg", "2");//删除成功
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		Map<String,String> jsonMap = new HashMap<String, String>();
		jsonMap.put("bodyDatas", "{\"model\":\"PlatformsParams\",\"operator\":\"add\",\"jsonModelDatas\":\"[{\"searchValue\":null,\"offset\":0,\"entity\":{\"id\":null,\"status\":1,\"remark\":\"\",\"createTime\":1450168639857,\"platformsId\":59,\"platformsCode\":\"test-new-platform5\",\"platformsName\":\"测试新平台5\",\"mapperType\":1,\"mapperColumn\":\"appid\",\"paramsCode\":\"appid\",\"isExtraParams\":0},\"rows\":0,\"success\":false,\"downDateline\":null,\"upDateline\":null,\"clause\":null,\"orderClause\":null},{\"searchValue\":null,\"offset\":0,\"entity\":{\"id\":null,\"status\":1,\"remark\":\"\",\"createTime\":1450168639857,\"platformsId\":59,\"platformsCode\":\"test-new-platform5\",\"platformsName\":\"测试新平台5\",\"mapperType\":1,\"mapperColumn\":\"appkey\",\"paramsCode\":\"appkey\",\"isExtraParams\":0},\"rows\":0,\"success\":false,\"downDateline\":null,\"upDateline\":null,\"clause\":null,\"orderClause\":null},{\"searchValue\":null,\"offset\":0,\"entity\":{\"id\":null,\"status\":1,\"remark\":\"\",\"createTime\":1450168639857,\"platformsId\":59,\"platformsCode\":\"test-new-platform5\",\"platformsName\":\"测试新平台5\",\"mapperType\":1,\"mapperColumn\":\"idfa\",\"paramsCode\":\"idf\",\"isExtraParams\":0},\"rows\":0,\"success\":false,\"downDateline\":null,\"upDateline\":null,\"clause\":null,\"orderClause\":null},{\"searchValue\":null,\"offset\":0,\"entity\":{\"id\":null,\"status\":1,\"remark\":\"\",\"createTime\":1450168639857,\"platformsId\":59,\"platformsCode\":\"test-new-platform5\",\"platformsName\":\"测试新平台5\",\"mapperType\":1,\"mapperColumn\":\"uuid\",\"paramsCode\":\"uuid\",\"isExtraParams\":1},\"rows\":0,\"success\":false,\"downDateline\":null,\"upDateline\":null,\"clause\":null,\"orderClause\":null},{\"searchValue\":null,\"offset\":0,\"entity\":{\"id\":null,\"status\":1,\"remark\":\"\",\"createTime\":1450168639857,\"platformsId\":59,\"platformsCode\":\"test-new-platform5\",\"platformsName\":\"测试新平台5\",\"mapperType\":2,\"mapperColumn\":\"appid\",\"paramsCode\":\"appid\",\"isExtraParams\":0},\"rows\":0,\"success\":false,\"downDateline\":null,\"upDateline\":null,\"clause\":null,\"orderClause\":null},{\"searchValue\":null,\"offset\":0,\"entity\":{\"id\":null,\"status\":1,\"remark\":\"\",\"createTime\":1450168639857,\"platformsId\":59,\"platformsCode\":\"test-new-platform5\",\"platformsName\":\"测试新平台5\",\"mapperType\":2,\"mapperColumn\":\"appkey\",\"paramsCode\":\"appkey\",\"isExtraParams\":0},\"rows\":0,\"success\":false,\"downDateline\":null,\"upDateline\":null,\"clause\":null,\"orderClause\":null},{\"searchValue\":null,\"offset\":0,\"entity\":{\"id\":null,\"status\":1,\"remark\":\"\",\"createTime\":1450168639857,\"platformsId\":59,\"platformsCode\":\"test-new-platform5\",\"platformsName\":\"测试新平台5\",\"mapperType\":2,\"mapperColumn\":\"idfa\",\"paramsCode\":\"idf\",\"isExtraParams\":0},\"rows\":0,\"success\":false,\"downDateline\":null,\"upDateline\":null,\"clause\":null,\"orderClause\":null},{\"searchValue\":null,\"offset\":0,\"entity\":{\"id\":null,\"status\":1,\"remark\":\"\",\"createTime\":1450168639857,\"platformsId\":59,\"platformsCode\":\"test-new-platform5\",\"platformsName\":\"测试新平台5\",\"mapperType\":2,\"mapperColumn\":\"act_type\",\"paramsCode\":\"act_type\",\"isExtraParams\":2},\"rows\":0,\"success\":false,\"downDateline\":null,\"upDateline\":null,\"clause\":null,\"orderClause\":null},{\"searchValue\":null,\"offset\":0,\"entity\":{\"id\":null,\"status\":1,\"remark\":\"\",\"createTime\":1450168639857,\"platformsId\":59,\"platformsCode\":\"test-new-platform5\",\"platformsName\":\"测试新平台5\",\"mapperType\":2,\"mapperColumn\":\"act_time\",\"paramsCode\":\"act_time\",\"isExtraParams\":2},\"rows\":0,\"success\":false,\"downDateline\":null,\"upDateline\":null,\"clause\":null,\"orderClause\":null}]\"}");
		String retStr = HttpClientUtils.executePostRequest(Constant.PLATFORM_PARAM_SYN_URL, jsonMap, "UTF-8");
		Map<String,Object> map = JSONUtils.fromJSON(retStr, HashMap.class);
		if(!"-1".equals(map.get("status"))){
			System.out.println(map.get("errMsg"));
		}
	}
}
