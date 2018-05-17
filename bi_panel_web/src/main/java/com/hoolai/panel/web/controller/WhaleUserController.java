package com.hoolai.panel.web.controller;

import java.util.HashMap;
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

import com.hoolai.bi.report.model.Types.GameWhaleUserPage;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.whaleUser.PayUserRetentionLtv;
import com.hoolai.bi.report.model.entity.whaleUser.WhaleUser;
import com.hoolai.bi.report.service.PayUserRetentionLtvService;
import com.hoolai.bi.report.service.WhaleUserService;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.bi.report.vo.PayUserRetentionLtvVO;
import com.hoolai.bi.report.vo.WhaleUserVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadCsvProcessor;

@Controller
@RequestMapping("/panel_bi/whaleUser")
public class WhaleUserController extends AbstractPanelController{
	
	@Autowired
	private WhaleUserService whaleUserService;
	
	@Autowired
	private PayUserRetentionLtvService payUserRetentionLtvService;
	
	@RequestMapping(value = {"/toWhaleUser.ac"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public String toGameInfo(HttpServletRequest request,HttpSession session,@ModelAttribute("gamesVO") GamesVO gamesVO,BindingResult result,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		GameWhaleUserPage page = GameWhaleUserPage.convertToPage(request.getParameter("view"));
		switch(page){
		case PAY_USER_LTV:
			return "whaleUser/payUserLtv.jsp";
		case SMALL_USER_LTV:
			return "whaleUser/smallUser.jsp";
		case WHALE_USER:
			default:
				return "whaleUser/whaleUser.jsp";
		}
	}

	
	@RequestMapping(value = {"/getWhaleUser.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> getWhaleUser(HttpServletRequest request,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Map<String,Object> ret=new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		GameWhaleUserPage page = GameWhaleUserPage.convertToPage(request.getParameter("view"));
		Map<String,Object> pageInfo = super.findPageInfo(request);
		
		switch(page){
		case PAY_USER_LTV:
			PayUserRetentionLtv retention = new PayUserRetentionLtv(games.getSnid(),games.getGameid());
			PayUserRetentionLtvVO retentionVO = new PayUserRetentionLtvVO(retention);
			retentionVO.setDate(beginDate, endDate);
			
			String isPageRequst = request.getParameter("isPageRequest");
			if(StringUtils.isEmpty(isPageRequst)){
				retentionVO.setQueryType("all");
				ret.putAll(payUserRetention(pageInfo, retentionVO));
				ret.put("ltvChart", payUserRetentionLtvService.selectAvgRetentionDataList(retentionVO));
				return ret;
			}else{
				return payUserRetention(pageInfo, retentionVO);
			}
		case SMALL_USER_LTV:
			return ret;
		case WHALE_USER:
			default:
				String beginDauDate = request.getParameter("beginDauTime");
				String endDauDate = request.getParameter("endDauTime");
				String beginPay = request.getParameter("beginPay");
				String endPay = request.getParameter("endPay");
				String indicators = request.getParameter("indicators");
				
				WhaleUser whaleUser = new WhaleUser(games.getSnid(),games.getGameid());
				WhaleUserVO whaleUserVO = new WhaleUserVO(whaleUser);
				whaleUserVO.getEntity().setDs(whaleUserService.selectMaxDs(whaleUserVO).getDs());
				whaleUserVO.setDate(beginDate,endDate);
				whaleUserVO.setPayRange(beginPay,endPay);
				whaleUserVO.setDauDate(beginDauDate,endDauDate);
				whaleUserVO.setIndicators(indicators);
				
				//总记录数
				long recordsTotal_cps_source = whaleUserService.selectCount(whaleUserVO);
				// 过滤记录数
				whaleUserVO.setSearchValue((String) pageInfo.get("searchValue"));
				long recordsFiltered_cps_source = whaleUserService.selectCount(whaleUserVO);
				
				// 分页数据
				whaleUserVO.setOffset((Long) pageInfo.get("start"));
				whaleUserVO.setRows((Long) pageInfo.get("length"));
				
				whaleUserVO.setOrderType((String)pageInfo.get("orderType"));// 由于排序的时候会用到orderType，所有在此之前赋值
				whaleUserVO.setOrderCol(getOrderCol((String)pageInfo.get("orderCol"),whaleUserVO));
				
				ret.put("recordsTotal", recordsTotal_cps_source);
				ret.put("recordsFiltered", recordsFiltered_cps_source);
				
				ret.put("data", whaleUserService.selectList(whaleUserVO));
				return ret;
		}
	}
	
	private Map<String,Object> payUserRetention(Map<String, Object> pageInfo,
			PayUserRetentionLtvVO retentionVO) {
		Map<String,Object> ret=new HashMap<String, Object>();
		
		if("limit".equals(retentionVO.getQueryType())){
			//总记录数
			long recordsTotal = payUserRetentionLtvService.selectCount(retentionVO);
			// 过滤记录数
			retentionVO.setSearchValue((String) pageInfo.get("searchValue"));
			long recordsFiltered = payUserRetentionLtvService.selectCount(retentionVO);
			
			// 分页数据
			retentionVO.setOffset((Long) pageInfo.get("start"));
			retentionVO.setRows((Long) pageInfo.get("length"));
			
			retentionVO.setOrderType((String)pageInfo.get("orderType"));// 由于排序的时候会用到orderType，所有在此之前赋值
			retentionVO.setOrderCol((String)pageInfo.get("orderCol"));
			
			ret.put("recordsTotal", recordsTotal);
			ret.put("recordsFiltered", recordsFiltered);
		}
		ret.put("data", payUserRetentionLtvService.selectList(retentionVO));
		return ret;
	}


	private String getOrderCol(String orderCol,WhaleUserVO whaleUserVO) {
		if(StringUtils.isEmpty(orderCol)){
			return null;
		}else {
			int col = Integer.parseInt(orderCol);
			switch(col){
			case 0:
				return null;
			case 1:
				return "install_date";
			case 2:
				return "first_pay_time";
			case 3:
				return "first_pay_amount";
			case 4:
				return "total_pay_amount";
			case 5:
				return "last_pay_time";
			case 6:
				if("asc".equals(whaleUserVO.getOrderType())){
					whaleUserVO.setOrderType("desc");
				}else{
					whaleUserVO.setOrderType("asc");
				}
				return "last_pay_time";
			case 7:
				return "last_dau_time";
			case 8:
				if("asc".equals(whaleUserVO.getOrderType())){
					whaleUserVO.setOrderType("desc");
				}else{
					whaleUserVO.setOrderType("asc");
				}
				return "last_dau_time";
			case 9:
				return "level";
			case 10:
			case 11:
				return null;
			case 12:
				return "clientid";
			case 13:
			case 14:
				return null;
			case 15:
				return "vip_level";
			default:
				return "total_pay_amount";
			}
		}
	}
	
	@RequestMapping(value = {"/downloadData.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String downData(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception {
		Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
		Games games = this.getSessionGames(request);
		
		GameWhaleUserPage page = GameWhaleUserPage.convertToPage(request.getParameter("view"));
		
		String beginDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime");
		DownLoadCsvProcessor processor = new DownLoadCsvProcessor(games, response);
		
		switch(page){
		case PAY_USER_LTV:
			PayUserRetentionLtv retention = new PayUserRetentionLtv(games.getSnid(),games.getGameid());
			PayUserRetentionLtvVO retentionVO = new PayUserRetentionLtvVO(retention);
			retentionVO.setDate(beginDate, endDate);
			retentionVO.setQueryType("all");
			processor.writeRetention(payUserRetentionLtvService.selectList(retentionVO));
			break;
		case WHALE_USER:
			default:
				String beginDauDate = request.getParameter("beginDauTime");
				String endDauDate = request.getParameter("endDauTime");
				String beginPay = request.getParameter("beginPay");
				String endPay = request.getParameter("endPay");
				String indicators = request.getParameter("indicators");
				WhaleUser whaleUser = new WhaleUser(games.getSnid(),games.getGameid());
				WhaleUserVO whaleUserVO = new WhaleUserVO(whaleUser);
				whaleUserVO.getEntity().setDs(whaleUserService.selectMaxDs(whaleUserVO).getDs());
				whaleUserVO.setDate(beginDate,endDate);
				whaleUserVO.setPayRange(beginPay,endPay);
				whaleUserVO.setDauDate(beginDauDate,endDauDate);
				whaleUserVO.setIndicators(indicators);
				whaleUserVO.setQueryType("all");
				
				processor.writeWhaleUser(whaleUserService.selectList(whaleUserVO));
				break;
		}
		
		return null;
	}
}

