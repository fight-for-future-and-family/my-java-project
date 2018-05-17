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
		//��ȡ�����ļ�����ȡ�ϴ��ļ���·��
		Properties upload =new Properties();
	    upload.load(UserController.class.getClassLoader().getResourceAsStream("upload.properties"));
		realPath=upload.getProperty("filepath");
		//��֤
		String user_name=upload.getProperty("identify");
		try {
			JSONObject identify= new JSONObject(user_name);
			if( ! biuser.getPassword().equals(identify.getString(biuser.getUsername()))){
				System.out.println("�û������password �� json �ļ���û���ҵ�");
				return "/error_fileupload";  
			}
		} catch (JSONException e) {
			System.out.println("�ȶ��û�������ʱ������δ֪����.");
			return "/error_fileupload";  
		}
		
		if (biuser.getPath()==null || biuser.getPath().equals("")){
			System.out.println("û������·����");
			return "/error_fileupload";
		}
		
		hdfsPath=biuser.getPath();
        //���ֻ���ϴ�һ���ļ�����ֻ��ҪMultipartFile���ͽ����ļ����ɣ�����������ʽָ��@RequestParamע��  
        //������ϴ�����ļ�����ô�����Ҫ��MultipartFile[]�����������ļ������һ�Ҫָ��@RequestParamע��  
        //�����ϴ�����ļ�ʱ��ǰ̨���е�����<input type="file"/>��name��Ӧ����myfiles������������myfiles�޷���ȡ�������ϴ����ļ�  
        for(MultipartFile myfile : myfiles){  
            if(myfile.isEmpty()){  
                System.out.println("�ļ�δ�ϴ�");  
    			return "/error_fileupload";  
            }else{  
                System.out.println("�ļ�����: " + myfile.getSize());  
                System.out.println("�ļ�����: " + myfile.getContentType());  
                System.out.println("�ļ�����: " + myfile.getName());  
                System.out.println("�ļ�ԭ��: " + myfile.getOriginalFilename()); 
                fileName=myfile.getOriginalFilename();
                System.out.println("========================================");  
                //����õ���Tomcat�����������ļ����ϴ���\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\�ļ�����  
               // String realPath = request.getSession().getServletContext().getRealPath("/wl_spring_mvc/WEB-INF/upload");  
               // String realPath = "\\WEB-INF\\upload-new";  
                //���ﲻ�ش���IO���رյ����⣬��ΪFileUtils.copyInputStreamToFile()�����ڲ����Զ����õ���IO���ص������ǿ�����Դ���֪����  
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
                System.out.println(String.format("%s �ļ��ɹ��ϴ�����ʱ·����_______at %s",myfile.getOriginalFilename(),new Date().toString()));
                System.out.println("�ϴ��ļ�������·����:"+realPath+"/"+myfile.getOriginalFilename());
                
                
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
