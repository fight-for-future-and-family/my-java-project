package com.hoolai.hdfs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

import com.hoolai.hdfs.po.Items;
		
public class ItermController1  implements  HttpRequestHandler {

	 
	public void handleRequest(HttpServletRequest arg0, HttpServletResponse arg1)   throws ServletException, IOException {
		 
		List<Items>   itemsList=new ArrayList<Items>();
		Items items_1 = new Items();
		items_1.setName("联想笔记本");
		items_1.setPrice(6000f);
		items_1.setDetail("ThinkPad T430 联想笔记本!");
		
		Items items_2 = new Items();
		items_2.setName("苹果手机");
		items_2.setPrice(5000f);
		items_2.setDetail("iphone6 苹果手机!");
		
		
		itemsList.add(items_1);
		itemsList.add(items_2);
		
		//设置模型数据
	    arg0.setAttribute("itemsList", itemsList);
		
	    // 设置转发的视图
	    arg0.getRequestDispatcher("/WEB-INF/jsp/items/itemsList.jsp").forward(arg0, arg1);
	    
	    // 使用此方法可以通过修改  response，设置响应的数据格式，比如 json
	}

	/*@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		
		List<Items>   itemsList=new ArrayList<Items>();
		Items items_1 = new Items();
		items_1.setName("联想笔记本");
		items_1.setPrice(6000f);
		items_1.setDetail("ThinkPad T430 联想笔记本!");
		
		Items items_2 = new Items();
		items_2.setName("苹果手机");
		items_2.setPrice(5000f);
		items_2.setDetail("iphone6 苹果手机!");
		
		
		itemsList.add(items_1);
		itemsList.add(items_2);
		
		//  返回 modelandviewer
		ModelAndView  modelAndVidew = new ModelAndView();
		modelAndVidew.addObject("itemsList",itemsList);
		
		
		// 指定视图
		modelAndVidew.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		return modelAndVidew;
	}*/

	
	
	
	
	
	
	
	
}
