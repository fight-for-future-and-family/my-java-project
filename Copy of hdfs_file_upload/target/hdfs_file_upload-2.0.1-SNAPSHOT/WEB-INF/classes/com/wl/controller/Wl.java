package com.wl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Wl {

	@RequestMapping("wl")
	public ModelAndView helloWorld() {
 
		System.out.println("++++++++++++++++++++++++++++++");
		System.out.println("*******************************************");
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message ----WLWLWL------ This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
		return new ModelAndView("wl", "message", message);
	}
	

}
