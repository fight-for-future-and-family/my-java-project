package com.wl.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UserController {

	private final static Map<String,User> users=new HashMap<String, User>();
	int result=6;
	String realPath;
	String fileName;
	String hdfsPath;
	
	
	@RequestMapping("/list")
	public String list(Model model){
		System.out.println("list-------------------------------------------------------listd");
		model.addAttribute("users",users);
		model.addAttribute("result",result);
		model.addAttribute("realPath",hdfsPath);
		model.addAttribute("fileName",fileName);
		
		Iterator<Entry<String, User>> use=users.entrySet().iterator();
		while (use.hasNext()){
			Map.Entry<String, User> tmp=(Map.Entry<String, User>)use.next();
			System.out.println("the key is_____"+tmp.getKey()+"; and the value is +++"+tmp.getValue());
		}
		return "list";
	}
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String addUser(){
		return "/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	//	public String addUser( User biuser,@RequestParam MultipartFile myfile,HttpServletRequest request) throws IOException{
	public String addUser( User biuser,@RequestParam MultipartFile[] myfiles,HttpServletRequest request) throws IOException{
		//读取配置文件，获取上传文件的路径
		Properties upload =new Properties();
	    upload.load(UserController.class.getClassLoader().getResourceAsStream("upload.properties"));
		realPath=upload.getProperty("filepath");
		//认证
		String user_name=upload.getProperty("identify");
		try {
			JSONObject identify= new JSONObject(user_name);
			if( ! biuser.getPassword().equals(identify.getString(biuser.getUsername()))){
				System.out.println("用户输入的password 在 json 文件中没有找到");
				return "/error_fileupload";  
			}
		} catch (JSONException e) {
			System.out.println("比对用户名密码时，发生未知错误.");
			return "/error_fileupload";  
		}
		
		if (biuser.getPath()==null || biuser.getPath().equals("")){
			System.out.println("没有输入路径！");
			return "/error_fileupload";
		}
		
		hdfsPath=biuser.getPath();
        //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件  
        for(MultipartFile myfile : myfiles){  
            if(myfile.isEmpty()){  
                System.out.println("文件未上传");  
    			return "/error_fileupload";  
            }else{  
                System.out.println("文件长度: " + myfile.getSize());  
                System.out.println("文件类型: " + myfile.getContentType());  
                System.out.println("文件名称: " + myfile.getName());  
                System.out.println("文件原名: " + myfile.getOriginalFilename()); 
                fileName=myfile.getOriginalFilename();
                System.out.println("========================================");  
                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
               // String realPath = request.getSession().getServletContext().getRealPath("/wl_spring_mvc/WEB-INF/upload");  
               // String realPath = "\\WEB-INF\\upload-new";  
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
                System.out.println(String.format("%s 文件成功上传至临时路径中_______at %s",myfile.getOriginalFilename(),new Date().toString()));
                System.out.println("上传文件的完整路径是:"+realPath+"/"+myfile.getOriginalFilename());
                
                
               Hdfs_File_Manager comeOn=new Hdfs_File_Manager();
               result=comeOn.loadFile(realPath+"/"+myfile.getOriginalFilename(), biuser.getPath());
               
               
            
            }  
        }  //for
        
        System.out.println(biuser.getPath()+"++++++++++++++++++++++++++++++++++++++++++");
        users.clear();
        users.put(biuser.getUsername(), biuser);  
        return "redirect:/list";  
	}
	
	
	
}
