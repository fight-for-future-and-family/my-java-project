package com.hoolai.panel.web.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.hoolai.bi.report.model.Types.GameEquipmentPage;
import com.hoolai.bi.report.model.Types.GameEquipmentType;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.equip.EquipDay;
import com.hoolai.bi.report.model.entity.equip.EquipLtv;
import com.hoolai.bi.report.model.entity.equip.EquipModelDay;
import com.hoolai.bi.report.model.entity.equip.EquipModelRetentionLtv;
import com.hoolai.bi.report.model.entity.equip.EquipRetentionLtv;
import com.hoolai.bi.report.model.entity.equip.EquipSourceDay;
import com.hoolai.bi.report.model.entity.equip.EquipSourceRetentionLtv;
import com.hoolai.bi.report.model.entity.equip.EquipVersionDau;
import com.hoolai.bi.report.service.EquipDayService;
import com.hoolai.bi.report.service.EquipModelDayService;
import com.hoolai.bi.report.service.EquipModelRetentionLtvService;
import com.hoolai.bi.report.service.EquipRetentionLtvService;
import com.hoolai.bi.report.service.EquipSourceDayService;
import com.hoolai.bi.report.service.EquipSourceRetentionLtvService;
import com.hoolai.bi.report.service.EquipVersionDauService;
import com.hoolai.bi.report.vo.EquipDayVO;
import com.hoolai.bi.report.vo.EquipModelDayVO;
import com.hoolai.bi.report.vo.EquipModelRetentionLtvVO;
import com.hoolai.bi.report.vo.EquipRetentionLtvVO;
import com.hoolai.bi.report.vo.EquipSourceDayVO;
import com.hoolai.bi.report.vo.EquipSourceRetentionLtvVO;
import com.hoolai.bi.report.vo.EquipVersionDauVO;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;

@Controller
@RequestMapping("/panel_bi/equip")
public class EquipmentController extends AbstractPanelController{
	
	@Autowired
	private EquipDayService equipDayService;
	
	@Autowired
	private EquipSourceDayService equipSourceDayService;
	
	@Autowired
	private EquipModelDayService equipModelDayService;
	
	@Autowired
	private EquipRetentionLtvService equipRetentionLtvService;
	
	@Autowired
	private EquipSourceRetentionLtvService equipSourceRetentionLtvService;
	
	@Autowired
	private EquipModelRetentionLtvService equipModelRetentionLtvService;
	
	@Autowired
	private EquipVersionDauService equipVersionDauService;
	
	@RequestMapping(value = {"/toEuipment.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toEuipment(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		
		String view = request.getParameter("view");
		GameEquipmentPage page = GameEquipmentPage.convertToPage(view);
		
		switch(page){
		case VERSION_DAU:
			return "equip/equipVersionDau.jsp";
		case INSTALL_RETENTION:
			return "equip/newEquipRetention.jsp";
		case INSTALL_STEP:
			return "equip/installStep.jsp";
		case ALL_STEP:
			return "equip/allStep.jsp";
		case EQUIP_DAU:
			default:
				return "equip/equipDau.jsp";
		}
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/getEquipmentData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getEquipmentData(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		Map<String,Object> ret=new HashMap<String, Object>();
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String indicators = request.getParameter("indicators");
		String source = request.getParameter("source");
		String model = request.getParameter("model");
		String view = request.getParameter("view");
		GameEquipmentPage page = GameEquipmentPage.convertToPage(view);
		GameEquipmentType type = GameEquipmentType.convertToType(indicators);
		
		Map<String,Object> pageInfo = super.findPageInfo(request);
		switch(page){
		case VERSION_DAU:
			EquipVersionDau versionDau = new EquipVersionDau(games.getSnid(),games.getGameid());
			EquipVersionDauVO versionDauVO = new EquipVersionDauVO(versionDau);
			versionDauVO.setDate(beginDate, endDate);
			return versionDau(versionDauVO,request, pageInfo);
		case INSTALL_RETENTION:
			switch(type){
			case SOURCE:
				EquipSourceRetentionLtv sourceRetention = new EquipSourceRetentionLtv(games.getSnid(),games.getGameid());
				EquipSourceRetentionLtvVO sourceRetentionVO = new EquipSourceRetentionLtvVO(sourceRetention);
				sourceRetentionVO.setDate(beginDate, endDate);
				if("-99".equals(source)){
					sourceRetention.setSource(null);
				}else{
					sourceRetention.setSource(source);
				}
				
				return sourceRetention(pageInfo, sourceRetentionVO);
			case MODEL:
				EquipModelRetentionLtv modelRetention = new EquipModelRetentionLtv(games.getSnid(),games.getGameid(),null);
				EquipModelRetentionLtvVO modelRetentionVO = new EquipModelRetentionLtvVO(modelRetention);
				modelRetentionVO.setDate(beginDate, endDate);
				if("-99".equals(model)){
					modelRetention.setModel(null);
				}else{
					modelRetention.setModel(model);
				}
				
				return modelRetention(pageInfo, modelRetentionVO);
			case ALL:
				default:
					EquipRetentionLtv retention = new EquipRetentionLtv(games.getSnid(),games.getGameid());
					EquipRetentionLtvVO retentionVO = new EquipRetentionLtvVO(retention);
					retentionVO.setDate(beginDate, endDate);
					
					String isPageRequst = request.getParameter("isPageRequest");
					if(StringUtils.isEmpty(isPageRequst)){
						retentionVO.setQueryType("all");
						ret.putAll(dauRetention(pageInfo, retentionVO));
						ret.put("ltvChart", equipRetentionLtvService.selectAvgRetentionDataList(retentionVO));
						return ret;
					}else{
						return dauRetention(pageInfo, retentionVO);
					}
			}
		case INSTALL_STEP:
			break;
		case ALL_STEP:
			break;
		case EQUIP_DAU:
		default:
			
			switch(type){
			case SOURCE:
				EquipSourceDay equipSourceDay = new EquipSourceDay(games.getSnid(),games.getGameid(),null);
				EquipSourceDayVO equipSourceDayVO = new EquipSourceDayVO(equipSourceDay);
				equipSourceDayVO.setDate(beginDate, endDate);
				String isPageRequest = request.getParameter("isPageRequest");
				if(StringUtils.isEmpty(isPageRequest)){
					ret.put("sourceInstallData", equipSourceDayService.selectInsatll4Pie(equipSourceDayVO));
					ret.put("sourceDauData", equipSourceDayService.selectDau4Pie(equipSourceDayVO));
					ret.put("sourceNewEquipData", equipSourceDayService.selectNewEquip4Pie(equipSourceDayVO));
					
					ret.put("sourceDauBarData", equipSourceDayService.selectDau4Bar(equipSourceDayVO));
					ret.put("sourceNewEquipLineData", equipSourceDayService.selectNewEquip4Line(equipSourceDayVO));
					ret.put("sourceNewEquipRateLineData", equipSourceDayService.selectNewEquipRate4Line(equipSourceDayVO));
					return ret;
				}
				
				if("-99".equals(source)){
					equipSourceDay.setSource(null);
				}else{
					equipSourceDay.setSource(source);
				}
				return sourceDau(pageInfo, equipSourceDayVO);
			case MODEL:
				EquipModelDay equipModelDay = new EquipModelDay(games.getSnid(),games.getGameid(),null);
				EquipModelDayVO equipModelDayVO = new EquipModelDayVO(equipModelDay);
				equipModelDayVO.setDate(beginDate, endDate);
				
				String isPageRequest1 = request.getParameter("isPageRequest");
				if(StringUtils.isEmpty(isPageRequest1)){
					ret.put("modelInstallData", equipModelDayService.selectInsatll4Pie(equipModelDayVO));
					ret.put("modelDauData", equipModelDayService.selectDau4Pie(equipModelDayVO));
					ret.put("modelNewEquipData", equipModelDayService.selectNewEquip4Pie(equipModelDayVO));
					
					ret.put("modelDauBarData", equipModelDayService.selectDau4Bar(equipModelDayVO));
					ret.put("modelNewEquipLineData", equipModelDayService.selectNewEquip4Line(equipModelDayVO));
					ret.put("modelNewEquipRateLineData", equipModelDayService.selectNewEquipRate4Line(equipModelDayVO));
					return ret;
				}
				
				if("-99".equals(model)){
					equipModelDay.setModel(null);
				}else{
					equipModelDay.setModel(model);
				}
				
				return modelDau(pageInfo, equipModelDayVO);
			case ALL:
				default:
					EquipDay equipDay = new EquipDay(games.getSnid(),games.getGameid());
					EquipDayVO equipDayVO = new EquipDayVO(equipDay);
					equipDayVO.setDate(beginDate, endDate);
					
					if(!StringUtils.isEmpty(request.getParameter("isPageRequest"))){
						//总记录数
						long recordsTotal = equipDayService.selectCount(equipDayVO);
						// 过滤记录数
						equipDayVO.setSearchValue((String) pageInfo.get("searchValue"));
						long recordsFiltered = equipDayService.selectCount(equipDayVO);
						
						// 分页数据
						equipDayVO.setOffset((Long) pageInfo.get("start"));
						equipDayVO.setRows((Long) pageInfo.get("length"));
						
						equipDayVO.setOrderType((String)pageInfo.get("orderType"));// 由于排序的时候会用到orderType，所有在此之前赋值
						equipDayVO.setOrderCol((String)pageInfo.get("orderCol"));
						
						ret.put("recordsTotal", recordsTotal);
						ret.put("recordsFiltered", recordsFiltered);
						ret.put("data", equipDayService.selectList(equipDayVO));
						return ret;
					}
					
				return allDau(pageInfo, equipDayVO);
			}
		}
		
		return Collections.EMPTY_MAP;
	}


	private Map<String,Object> allDau(Map<String, Object> pageInfo, EquipDayVO equipDayVO) {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		equipDayVO.setOrderType("asc");
		equipDayVO.setQueryType("all");
		
		EquipSourceDay equipSourceDay = new EquipSourceDay(equipDayVO.getEntity().getSnid(),equipDayVO.getEntity().getGameid());
		EquipSourceDayVO equipSourceDayVO = new EquipSourceDayVO(equipSourceDay);
		equipSourceDayVO.setDate(equipDayVO.getBeginDate(), equipDayVO.getEndDate());
		
		EquipModelDay equipModelDay = new EquipModelDay(equipDayVO.getEntity().getSnid(),equipDayVO.getEntity().getGameid(),null);
		EquipModelDayVO equipModelDayVO = new EquipModelDayVO(equipModelDay);
		equipModelDayVO.setDate(equipDayVO.getBeginDate(), equipDayVO.getEndDate());
		
		ret.put("equipData", equipDayService.selectList(equipDayVO));
		
		ret.put("sourceInstallData", equipSourceDayService.selectInsatll4Pie(equipSourceDayVO));
		ret.put("sourceDauData", equipSourceDayService.selectDau4Pie(equipSourceDayVO));
		ret.put("sourceNewEquipData", equipSourceDayService.selectNewEquip4Pie(equipSourceDayVO));
		
		ret.put("modelInstallData", equipModelDayService.selectInsatll4Pie(equipModelDayVO));
		ret.put("modelDauData", equipModelDayService.selectDau4Pie(equipModelDayVO));
		ret.put("modelNewEquipData", equipModelDayService.selectNewEquip4Pie(equipModelDayVO));
		return ret;
	}


	private Map<String,Object> modelDau(Map<String, Object> pageInfo,
			EquipModelDayVO equipModelDayVO) {
		Map<String,Object> ret=new HashMap<String, Object>();
		if("limit".equals(equipModelDayVO.getQueryType())){
			//总记录数
			long recordsTotal_model = equipModelDayService.selectCount(equipModelDayVO);
			// 过滤记录数
			equipModelDayVO.setSearchValue((String) pageInfo.get("searchValue"));
			long recordsFiltered_model = equipModelDayService.selectCount(equipModelDayVO);
			
			// 分页数据
			equipModelDayVO.setOffset((Long) pageInfo.get("start"));
			equipModelDayVO.setRows((Long) pageInfo.get("length"));
			
			equipModelDayVO.setOrderType((String)pageInfo.get("orderType"));// 由于排序的时候会用到orderType，所有在此之前赋值
			equipModelDayVO.setOrderCol((String)pageInfo.get("orderCol"));
			
			ret.put("recordsTotal", recordsTotal_model);
			ret.put("recordsFiltered", recordsFiltered_model);
		}
		
		ret.put("data", equipModelDayService.selectList(equipModelDayVO));
		return ret;
	}


	private Map<String,Object> sourceDau(Map<String, Object> pageInfo,
			EquipSourceDayVO equipSourceDayVO) {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		if("limit".equals(equipSourceDayVO.getQueryType())){
			//总记录数
			long recordsTotal_source = equipSourceDayService.selectCount(equipSourceDayVO);
			// 过滤记录数
			equipSourceDayVO.setSearchValue((String) pageInfo.get("searchValue"));
			long recordsFiltered_source = equipSourceDayService.selectCount(equipSourceDayVO);
			
			// 分页数据
			equipSourceDayVO.setOffset((Long) pageInfo.get("start"));
			equipSourceDayVO.setRows((Long) pageInfo.get("length"));
			
			equipSourceDayVO.setOrderType((String)pageInfo.get("orderType"));// 由于排序的时候会用到orderType，所有在此之前赋值
			equipSourceDayVO.setOrderCol((String)pageInfo.get("orderCol"));
			
			ret.put("recordsTotal", recordsTotal_source);
			ret.put("recordsFiltered", recordsFiltered_source);
		}
		
		ret.put("data", equipSourceDayService.selectList(equipSourceDayVO));
		return ret;
	}


	private Map<String,Object> dauRetention(Map<String, Object> pageInfo,
			EquipRetentionLtvVO retentionVO) {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		if("limit".equals(retentionVO.getQueryType())){
			//总记录数
			long recordsTotal = equipRetentionLtvService.selectCount(retentionVO);
			// 过滤记录数
			retentionVO.setSearchValue((String) pageInfo.get("searchValue"));
			long recordsFiltered = equipRetentionLtvService.selectCount(retentionVO);
			
			// 分页数据
			retentionVO.setOffset((Long) pageInfo.get("start"));
			retentionVO.setRows((Long) pageInfo.get("length"));
			
			retentionVO.setOrderType((String)pageInfo.get("orderType"));// 由于排序的时候会用到orderType，所有在此之前赋值
			retentionVO.setOrderCol((String)pageInfo.get("orderCol"));
			
			ret.put("recordsTotal", recordsTotal);
			ret.put("recordsFiltered", recordsFiltered);
		}
		ret.put("data", equipRetentionLtvService.selectList(retentionVO));
		return ret;
	}


	private Map<String,Object> modelRetention(Map<String, Object> pageInfo,
			EquipModelRetentionLtvVO modelRetentionVO) {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		if("limit".equals(modelRetentionVO.getQueryType())){
		//总记录数
			long recordsTotal_model = equipModelRetentionLtvService.selectCount(modelRetentionVO);
			// 过滤记录数
			modelRetentionVO.setSearchValue((String) pageInfo.get("searchValue"));
			long recordsFiltered_model = equipModelRetentionLtvService.selectCount(modelRetentionVO);
			
			// 分页数据
			modelRetentionVO.setOffset((Long) pageInfo.get("start"));
			modelRetentionVO.setRows((Long) pageInfo.get("length"));
			
			modelRetentionVO.setOrderType((String)pageInfo.get("orderType"));// 由于排序的时候会用到orderType，所有在此之前赋值
			modelRetentionVO.setOrderCol((String)pageInfo.get("orderCol"));
			
			ret.put("recordsTotal", recordsTotal_model);
			ret.put("recordsFiltered", recordsFiltered_model);
		}
		ret.put("data", equipModelRetentionLtvService.selectList(modelRetentionVO));
		return ret;
	}


	private Map<String,Object> sourceRetention(Map<String, Object> pageInfo,
			EquipSourceRetentionLtvVO sourceRetentionVO) {
		
		Map<String,Object> ret=new HashMap<String, Object>();
		if("limit".equals(sourceRetentionVO.getQueryType())){
			//总记录数
			long recordsTotal_source = equipSourceRetentionLtvService.selectCount(sourceRetentionVO);
			// 过滤记录数
			sourceRetentionVO.setSearchValue((String) pageInfo.get("searchValue"));
			long recordsFiltered_source = equipSourceRetentionLtvService.selectCount(sourceRetentionVO);
			
			// 分页数据
			sourceRetentionVO.setOffset((Long) pageInfo.get("start"));
			sourceRetentionVO.setRows((Long) pageInfo.get("length"));
			
			sourceRetentionVO.setOrderType((String)pageInfo.get("orderType"));// 由于排序的时候会用到orderType，所有在此之前赋值
			sourceRetentionVO.setOrderCol((String)pageInfo.get("orderCol"));
			
			ret.put("recordsTotal", recordsTotal_source);
			ret.put("recordsFiltered", recordsFiltered_source);
		}
		ret.put("data", equipSourceRetentionLtvService.selectList(sourceRetentionVO));
		return ret;
	}


	private Map<String, Object> versionDau(EquipVersionDauVO versionDauVO,HttpServletRequest request ,Map<String, Object> pageInfo) {
		Map<String,Object> ret=new HashMap<String, Object>();
		if(!StringUtils.isEmpty(request.getParameter("nameRequest"))){
			ret.put("names", equipVersionDauService.selectTop5Version(versionDauVO));
			return ret;
		}else if(!StringUtils.isEmpty(request.getParameter("isPageRequest"))){
			//总记录数
			long recordsTotal_source = equipVersionDauService.selectCount(versionDauVO);
			// 过滤记录数
			//versionDauVO.setSearchValue((String) pageInfo.get("searchValue"));
			//long recordsFiltered_source = equipVersionDauService.selectCount(versionDauVO);
			
			// 分页数据
			versionDauVO.setOffset((Long) pageInfo.get("start"));
			versionDauVO.setRows((Long) pageInfo.get("length"));
			
			versionDauVO.setOrderType((String)pageInfo.get("orderType"));// 由于排序的时候会用到orderType，所有在此之前赋值
			versionDauVO.setOrderCol((String)pageInfo.get("orderCol"));
			
			ret.put("recordsTotal", recordsTotal_source);
			ret.put("recordsFiltered", recordsTotal_source);
			ret.put("data", equipVersionDauService.selectList(versionDauVO));
			return ret;
		}else{
			versionDauVO.setQueryType("all");
			ret.put("names", equipVersionDauService.selectTop5Version(versionDauVO));
			ret.put("equipVersionDauList", equipVersionDauService.selectList(versionDauVO));
			ret.put("nameList", versionDauVO.getVersionNames());
			return ret;
		}
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/downloadData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		String indicators = request.getParameter("indicators");
		String source = request.getParameter("source");
		String model = request.getParameter("model");
		String view = request.getParameter("view");
		GameEquipmentPage page = GameEquipmentPage.convertToPage(view);
		GameEquipmentType type = GameEquipmentType.convertToType(indicators);
		
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
		switch(page){
		case VERSION_DAU:
			EquipVersionDau versionDau = new EquipVersionDau(games.getSnid(),games.getGameid());
			EquipVersionDauVO versionDauVO = new EquipVersionDauVO(versionDau);
			versionDauVO.setDate(beginDate, endDate);
			versionDauVO.setQueryType("all");
			List<EquipVersionDau> list = equipVersionDauService.getMatch(versionDauVO);
			
			processor.writeEquipVersionDau(list);
			break;
		case INSTALL_RETENTION:
			switch(type){
			case SOURCE:
				EquipSourceRetentionLtv sourceRetention = new EquipSourceRetentionLtv(games.getSnid(),games.getGameid());
				EquipSourceRetentionLtvVO sourceRetentionVO = new EquipSourceRetentionLtvVO(sourceRetention);
				sourceRetentionVO.setDate(beginDate, endDate);
				sourceRetentionVO.setQueryType("all");
				if("-99".equals(source)){
					sourceRetention.setSource(null);
				}else{
					sourceRetention.setSource(source);
				}
				
				processor.writeRetention("source",((List<EquipLtv>)sourceRetention(null, sourceRetentionVO).get("data")));
				break;
			case MODEL:
				EquipModelRetentionLtv modelRetention = new EquipModelRetentionLtv(games.getSnid(),games.getGameid(),null);
				EquipModelRetentionLtvVO modelRetentionVO = new EquipModelRetentionLtvVO(modelRetention);
				modelRetentionVO.setDate(beginDate, endDate);
				modelRetentionVO.setQueryType("all");
				if("-99".equals(model)){
					modelRetention.setModel(null);
				}else{
					modelRetention.setModel(model);
				}
				
				processor.writeRetention("model",((List<EquipLtv>)modelRetention(null, modelRetentionVO).get("data")));
				break;
			case ALL:
				default:
					EquipRetentionLtv retention = new EquipRetentionLtv(games.getSnid(),games.getGameid());
					EquipRetentionLtvVO retentionVO = new EquipRetentionLtvVO(retention);
					retentionVO.setDate(beginDate, endDate);
					retentionVO.setQueryType("all");
					
					processor.writeRetention("all",((List<EquipLtv>)dauRetention(null, retentionVO).get("data")));
					break;
			}
			break;
		case INSTALL_STEP:
			break;
		case ALL_STEP:
			break;
		case EQUIP_DAU:
		default:
			
			switch(type){
			case SOURCE:
				EquipSourceDay equipSourceDay = new EquipSourceDay(games.getSnid(),games.getGameid(),null);
				EquipSourceDayVO equipSourceDayVO = new EquipSourceDayVO(equipSourceDay);
				equipSourceDayVO.setQueryType("all");
				equipSourceDayVO.setDate(beginDate, endDate);
				if("-99".equals(source)){
					equipSourceDay.setSource(null);
				}else{
					equipSourceDay.setSource(source);
				}
				
				processor.writeEquipSourceDau(((List<EquipSourceDay>)sourceDau(null, equipSourceDayVO).get("data")));
				break;
			case MODEL:
				EquipModelDay equipModelDay = new EquipModelDay(games.getSnid(),games.getGameid(),null);
				EquipModelDayVO equipModelDayVO = new EquipModelDayVO(equipModelDay);
				equipModelDayVO.setQueryType("all");
				equipModelDayVO.setDate(beginDate, endDate);
				if("-99".equals(model)){
					equipModelDay.setModel(null);
				}else{
					equipModelDay.setModel(model);
				}
				
				processor.writeEquipModelDau(((List<EquipModelDay>)modelDau(null, equipModelDayVO).get("data")));
				break;
			case ALL:
				default:
					EquipDay equipDay = new EquipDay(games.getSnid(),games.getGameid());
					EquipDayVO equipDayVO = new EquipDayVO(equipDay);
					equipDayVO.setQueryType("all");
					equipDayVO.setDate(beginDate, endDate);
					
				processor.writeEquipDau((List<EquipDay>)allDau(null, equipDayVO).get("data"));
				break;
			}
		}
		
		return null;
	}
	
	@RequestMapping(value = {"/getGameSource.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getGameSource(HttpServletRequest request,HttpSession session)throws Exception {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		Games games = getSessionGames(request);
		
		EquipSourceDay equipSourceDay = new EquipSourceDay(games.getSnid(), games.getGameid());
		EquipSourceDayVO equipSourceDayVO = new EquipSourceDayVO(equipSourceDay);
		List<String> gameSources = equipSourceDayService.selectEquipSources(equipSourceDayVO);
		
		ret.put("gameSources", gameSources);
		
		return ret;
	}
}

