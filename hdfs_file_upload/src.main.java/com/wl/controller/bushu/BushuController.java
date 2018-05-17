package com.wl.controller.bushu;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BushuController {

	Map<String,BushuService> result = new HashMap<String,BushuService>();
	
	@RequestMapping(value="/test",method=RequestMethod.GET) 
	public ModelAndView toindexPage(){
		return new ModelAndView("test");
	}
	
	@RequestMapping(value="/bushu",method=RequestMethod.GET) 
	public ModelAndView toBushuPage(){
		return new ModelAndView("bushu");
	}
	@RequestMapping(value="/bushu",method=RequestMethod.POST)
	public ModelAndView toBushuPageManager(BushuService temp,Model mo){
		int wl;String callback;
		if (null==temp.table || "".equals(temp.table)){
			mo.addAttribute("error","����д_����");
			return new ModelAndView("/bushu");
		}else if (null==temp.snid || "".equals(temp.snid)){
			mo.addAttribute("table",temp.table);
			mo.addAttribute("error","����д_snid");
			return new ModelAndView("/bushu");
		}else if (null==temp.gameid || "".equals(temp.gameid)){
			mo.addAttribute("table",temp.table);
			mo.addAttribute("snid",temp.snid);
			mo.addAttribute("error","����д_gameid");
			return new ModelAndView("/bushu");
		}else if (null==temp.ds || "".equals(temp.ds)){
			mo.addAttribute("table",temp.table);
			mo.addAttribute("snid",temp.snid);
			mo.addAttribute("gameid",temp.gameid);
			mo.addAttribute("error","��ѡ���Ӧ������");
			return new ModelAndView("/bushu");
		}else if (null==temp.target || "".equals(temp.target)){
			mo.addAttribute("table",temp.table);
			mo.addAttribute("snid",temp.snid);
			mo.addAttribute("gameid",temp.gameid);
			mo.addAttribute("ds",temp.ds);
			mo.addAttribute("error","���������ݣ�����ɾ������  ?");
			return new ModelAndView("/bushu");
		}
		System.out.println("����ȫ������д�ɹ�");
		wl=temp.dataManager();
		if(wl==0){
			callback="�����ɹ���ɣ�";
		}else if (wl==88){
			callback="���������쳣������ϵ��ؼ�����Ա!";
		}else if (wl==66){
			callback="δִ���κβ���������ϵ��ؼ�����Ա!";
		}else {
			callback="�������ʹ�������ϵ��ؼ�����Ա!";
		}
		return new ModelAndView("bushu","error",callback);
		
	}
}
