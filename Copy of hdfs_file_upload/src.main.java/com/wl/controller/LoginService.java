package com.wl.controller;

import org.springframework.web.servlet.ModelAndView;

public class LoginService {

	public  ModelAndView doLogin(String loginPageUrl, String succcessPageUrl,String uname, String upasswd) {
          if(null==uname || "".equals(uname)){
        	  return new ModelAndView(loginPageUrl,"error","用户名不能为空");
          }
          if(null==upasswd  || "".equals(upasswd)){
        	  return new ModelAndView(loginPageUrl,"error","用户密码不能为空");
          }
        //uname=admin,upasswd=123
          if(uname.equals("admin")  || upasswd.equals("123")){
        	  return new ModelAndView(succcessPageUrl,"error","用户密码不能为空");
          }
          
		return new ModelAndView(loginPageUrl,"error","用户名或者密码错误");
	}

}
