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
			mo.addAttribute("error","请填写_表名");
			return new ModelAndView("/bushu");
		}else if (null==temp.snid || "".equals(temp.snid)){
			mo.addAttribute("table",temp.table);
			mo.addAttribute("error","请填写_snid");
			return new ModelAndView("/bushu");
		}else if (null==temp.gameid || "".equals(temp.gameid)){
			mo.addAttribute("table",temp.table);
			mo.addAttribute("snid",temp.snid);
			mo.addAttribute("error","请填写_gameid");
			return new ModelAndView("/bushu");
		}else if (null==temp.ds || "".equals(temp.ds)){
			mo.addAttribute("table",temp.table);
			mo.addAttribute("snid",temp.snid);
			mo.addAttribute("gameid",temp.gameid);
			mo.addAttribute("error","请选择对应的日期");
			return new ModelAndView("/bushu");
		}else if (null==temp.target || "".equals(temp.target)){
			mo.addAttribute("table",temp.table);
			mo.addAttribute("snid",temp.snid);
			mo.addAttribute("gameid",temp.gameid);
			mo.addAttribute("ds",temp.ds);
			mo.addAttribute("error","是增加数据，还是删除数据  ?");
			return new ModelAndView("/bushu");
		}
		System.out.println("数据全部都填写成功");
		wl=temp.dataManager();
		if(wl==0){
			callback="操作成功完成！";
		}else if (wl==88){
			callback="操作发生异常，请联系相关技术人员!";
		}else if (wl==66){
			callback="未执行任何操作，请联系相关技术人员!";
		}else {
			callback="操作发送错误，请联系相关技术人员!";
		}
		return new ModelAndView("bushu","error",callback);
		
	}
}
