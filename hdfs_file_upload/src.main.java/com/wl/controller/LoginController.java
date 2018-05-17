package com.wl.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {


	LoginService  service=new LoginService();
	
	@Resource
	HttpServletRequest   request;
	
	@RequestMapping(value="/login",method=RequestMethod.GET) 
	public ModelAndView toLoginPage(){
		return new ModelAndView("login");
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST) 
     public ModelAndView doLogin(){
    	 String loginPageUrl="login";
    	 String succcessPageUrl="success";
    	 String uname=request.getParameter("uname");
    	 String upasswd=request.getParameter("upasswd");
    	 return service.doLogin(loginPageUrl,succcessPageUrl,uname,upasswd);
     }
	
}
