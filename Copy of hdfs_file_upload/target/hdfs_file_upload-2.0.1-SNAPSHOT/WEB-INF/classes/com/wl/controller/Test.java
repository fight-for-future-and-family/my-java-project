package com.wl.controller;

import java.util.Properties;

import org.json.JSONObject;

public class Test {

	public static void main(String[] sdf){
		Properties upload =new Properties();
	    try {
			upload.load(Test.class.getClassLoader().getResourceAsStream("upload.properties"));
			String realPath=upload.getProperty("identify");
			JSONObject identify= new JSONObject(realPath);
			System.out.println(identify.getString("zhangsan"));
			System.out.println(!(identify.getString("zhangsan")=="123"));
			System.out.println(345==567);
			System.out.println("wanglianghoolai"=="wanglianghoolai");
			System.out.println( !identify.getString("zhangsan").equals("123"));
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("fuck");
		}
	}
	
}
