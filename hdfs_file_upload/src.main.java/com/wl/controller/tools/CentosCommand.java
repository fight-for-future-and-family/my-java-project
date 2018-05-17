package com.wl.controller.tools;

import java.util.Date;

public class CentosCommand {

	int exitVal ;
	public int executeCommand(String command){
	    Runtime rt = Runtime.getRuntime();
	    try {
	    	Process pr = rt.exec(command);
	    	exitVal = pr.waitFor();
	    	if(exitVal==0){
	    		System.out.println(String.format("The command __%s__  is   successfull executed  at  %s",command,new Date().toString()));
	    		return exitVal;
	    	}else if(exitVal!=0) {
	    		System.out.println(String.format("The command >>>>>>__%s__<<<<<  is   failedly executed  at  %s",command,new Date().toString()));
	    		return exitVal;
	    	}
	    } catch (Exception e) {
	    	System.out.println(String.format("Some unknown exception happened   at  %s",new Date().toString()));
	    }
	    
	    System.out.println(String.format("没有执行任何命令  at   %s",new Date().toString()));
        return 88;
			
		 
	}
	
	
}
