package com.hoolai.panel.web.controller.manage;

import java.util.HashMap;
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

import com.hoolai.bi.report.core.Constant;
import com.hoolai.bi.report.vo.GamesVO;
import com.hoolai.manage.model.Users;
import com.hoolai.manage.service.GroupUsersService;

@Controller
@RequestMapping("/panel_manage/auth")
public class AuthController extends AbstractManageController{

	@Autowired
	private GroupUsersService groupUserService;
	
	@RequestMapping(value = {"/toSystem.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String toSystem(HttpServletRequest request,HttpSession session,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		model.addAttribute("isMaintain",Constant.IS_MAINTAIN);
		return "manage/sysSetting/changeToMaintain.jsp";
	}
	
	@RequestMapping(value = {"/changeToMaintain.ac"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String index(HttpServletRequest request,HttpSession session,Model model)throws Exception {
		Users users = getSessionUsers(request);
		if(!(groupUserService.isAdmin(users.getId()))){
			throw new Exception(users.getId()+","+users.getLoginName()+"权限越界！");
		}
		String isMaintain = request.getParameter("isMaintain");
		if(StringUtils.isEmpty(isMaintain)){
			model.addAttribute("msg", "parameter is null");
		}else if((Boolean)session.getAttribute("isAdmin")){
			Constant.IS_MAINTAIN = Boolean.valueOf(isMaintain);
			session.setAttribute("isMaintain", Constant.IS_MAINTAIN);
			model.addAttribute("msg", "Switch success!");
		}else{
			model.addAttribute("msg", "No authority!");
		}
		
		return "manage/sysSetting/changeToMaintain.jsp";
	}
	
}
