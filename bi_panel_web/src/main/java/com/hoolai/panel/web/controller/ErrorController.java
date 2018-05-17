package com.hoolai.panel.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController extends AbstractPanelController{
	
	private static final Logger logger=Logger.getLogger("exception");

	@RequestMapping(value = "/404.ac", method = { RequestMethod.GET,RequestMethod.POST })
	public String to404(HttpServletRequest request,Model model)throws Exception {
		try{
			System.out.println("404 referUrl:"+request.getHeader("refer"));
		}catch(Exception e){
			logger.error("404 :",e);
		}
		return "error/404.jsp";
	}
	
	@RequestMapping(value = "/50x.ac", method = { RequestMethod.GET,RequestMethod.POST })
	public String to50x(Model model)throws Exception {
		try{
			System.out.println("50x...");
		}catch(Exception e){
			logger.error("50x :",e);
		}
		return "error/50x.jsp";
	}
	
	
}