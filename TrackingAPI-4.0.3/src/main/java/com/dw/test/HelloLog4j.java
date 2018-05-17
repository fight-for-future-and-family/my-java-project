package com.dw.test;

import org.apache.log4j.Logger;

import com.dw.services.Utils;

public class HelloLog4j {
	static{
		System.out.println("sdfwefowiefuwef--we-e-4--34r-3-4");
	}
	static{
		System.out.println("26666666666666666666666");
	}
	private static final long MEGABYTE = 1024L * 1024;
	
	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}
	
	public HelloLog4j(){
		System.out.println("this is the construct method");
		System.out.println(super.toString());
	}
	
	  private static Logger logger = Logger.getLogger(Utils.class);  
      public static void main(String[] args) {
    	  new String("1234567890");
    	  HelloLog4j wl=new HelloLog4j();
       //  记录 debug 级别的信息   
        logger.debug("This is debug message.");  
        //  记录 info 级别的信息   
        logger.info("This is info message.");  
        //  记录 error 级别的信息   
        logger.error("This is error message.");  
        
        
        Runtime runtime = Runtime.getRuntime();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
        System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
        System.out.println(2147483647);
        System.out.println('a');
        byte b1=3,b2=(byte)400,b;
         float f1= 12.345f;
         float f2=(float)12.3434;
        
        System.out.println("5+5"+5+5+"   "+f1+f2+b2); 
    }  
}
