package com.hoolai.hdfs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.hoolai.hdfs.po.Items;
		
public class ItermController  implements  Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		
		List<Items>   itemsList=new ArrayList<Items>();
		Items items_1 = new Items();
		items_1.setName("����ʼǱ�");
		items_1.setPrice(6000f);
		items_1.setDetail("ThinkPad T430 ����ʼǱ�!");
		
		Items items_2 = new Items();
		items_2.setName("ƻ���ֻ�");
		items_2.setPrice(5000f);
		items_2.setDetail("iphone6 ƻ���ֻ�!");
		
		
		itemsList.add(items_1);
		itemsList.add(items_2);
		
		//  ���� modelandviewer
		ModelAndView  modelAndVidew = new ModelAndView();
		modelAndVidew.addObject("itemsList",itemsList);
		
		
		// ָ����ͼ
		modelAndVidew.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		return modelAndVidew;
	}

}
