package com.hoolai.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LuanMa {

	public static void main(String[] args) {
		File gzip = new File("D:\\工作相关\\脚本\\hlnn01\\test");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(gzip));
			
			 StringBuffer sb=new StringBuffer();
	        String temp=br.readLine();
	         while(temp!=null) {
	             sb.append(temp+" ");
	             temp=br.readLine();
	         }
	         String wl=new String(sb.toString().getBytes(),"GBK");
	         System.out.println(wl);
		} catch (Exception io) {
			  io.printStackTrace();
		}
	}
}
