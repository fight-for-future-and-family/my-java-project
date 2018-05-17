package com.hoolai.panel.web.controller.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hoolai.bi.report.core.Constant;
import com.hoolai.manage.vo.UsersVO;
import com.hoolai.panel.web.controller.AbstractPanelController;

@Controller
public class IndexController extends AbstractPanelController{

	@RequestMapping(value = {"/"}, method = { RequestMethod.GET,RequestMethod.POST })
	public String index(HttpServletRequest request,HttpSession session,@ModelAttribute("usersVO") UsersVO usersVO,BindingResult result,Model model)throws Exception {
		super.setSession(request, "isMaintain", Constant.IS_MAINTAIN);
		return "index.jsp";
	}
	
}
