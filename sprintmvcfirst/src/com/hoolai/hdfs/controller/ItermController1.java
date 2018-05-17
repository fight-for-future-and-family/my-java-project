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
		items_1.setName("����ʼǱ�");
		items_1.setPrice(6000f);
		items_1.setDetail("ThinkPad T430 ����ʼǱ�!");
		
		Items items_2 = new Items();
		items_2.setName("ƻ���ֻ�");
		items_2.setPrice(5000f);
		items_2.setDetail("iphone6 ƻ���ֻ�!");
		
		
		itemsList.add(items_1);
		itemsList.add(items_2);
		
		//����ģ������
	    arg0.setAttribute("itemsList", itemsList);
		
	    // ����ת������ͼ
	    arg0.getRequestDispatcher("/WEB-INF/jsp/items/itemsList.jsp").forward(arg0, arg1);
	    
	    // ʹ�ô˷�������ͨ���޸�  response��������Ӧ�����ݸ�ʽ������ json
	}

	/*@Override
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
	}*/

	
	
	
	
	
	
	
	
}
