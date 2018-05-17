package com.hoolai.bi.report.job;

import java.util.Map;

import org.apache.log4j.Logger;

public abstract class AbstractExecuteJob {
	
	protected static final  Logger logger=Logger.getLogger("executeJob");

	public void execute(){
//		if(!Config.IS_EXECUTEJOB_MACHINE){
//			return ;
//		}
		this.debug("-["+this.getClass().getSimpleName()+"]"+" execute begin...");
		long start=System.currentTimeMillis();
		try {
			this.executeJob();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		long end=System.currentTimeMillis();
		this.debug("-["+this.getClass().getSimpleName()+"]"+"execute end! spend time:"+(end-start));
	}
	
	public abstract Map<String,Object> executeJob() throws Exception;
	
	public void info(String msgs){
		logger.info("-["+this.getClass().getSimpleName()+"] ["+msgs+"]");
	}
	
	public void debug(String msgs){
		logger.debug("-["+this.getClass().getSimpleName()+"] ["+msgs+"]");
	}
	
	public void error(String msgs,Throwable throwable){
		logger.error("-["+this.getClass().getSimpleName()+"] ["+msgs+"]",throwable);
	}
}
