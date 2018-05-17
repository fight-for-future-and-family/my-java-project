package com.hoolai.bi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class aaa {
	public static void main(String args[]){
		    try{ //以下代码通过域名建立InetAddress对象：
		      InetAddress addr = InetAddress.getByName("www.jb51.net");
		      String domainName = addr.getHostName();//获得主机名
		      String IPName = addr.getHostAddress();//获得IP地址
		      System.out.println(domainName);
		      System.out.println(IPName);
		    }catch(UnknownHostException e){
		      e.printStackTrace();
		    }
		    
		    
		    
		  String  wl="1,2,3,4,5,6----556-5-7-67-6,1-1--1,1010101";
		   System.out.println(Arrays.stream(wl.split(","))
					.map(str ->{
						return str.replace("\"", "").trim();
					})
					.toArray(String[]::new)
				   );
		  }
}
