package com.wl.controller;

import org.springframework.web.servlet.ModelAndView;

public class LoginService {

	public  ModelAndView doLogin(String loginPageUrl, String succcessPageUrl,String uname, String upasswd) {
          if(null==uname || "".equals(uname)){
        	  return new ModelAndView(loginPageUrl,"error","�û�������Ϊ��");
          }
          if(null==upasswd  || "".equals(upasswd)){
        	  return new ModelAndView(loginPageUrl,"error","�û����벻��Ϊ��");
          }
        //uname=admin,upasswd=123
          if(uname.equals("admin")  || upasswd.equals("123")){
        	  return new ModelAndView(succcessPageUrl,"error","�û����벻��Ϊ��");
          }
          
		return new ModelAndView(loginPageUrl,"error","�û��������������");
	}

}
