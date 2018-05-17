package com.hoolai.manage.model;

/** 
 * <b>类说明：</b>
 * <blockquote>
 *   
 * </blockquote> 
 * @author jiangqm E-mail: jiangqiming@jiangqiming.cn
 * @version 创建时间：2015年11月17日 下午6:46:06  
 */

public class EtlEngineCleanup {
	 private String snid;

	 private String gameid;
	 
	 private String gameName;
	 
	 private String ds;
	 
	 private String errMessage;

	 
	 public EtlEngineCleanup(){
		 super();
	 }
	 public EtlEngineCleanup(EtlEngineCleanup engineCleanup){
		 this.snid = engineCleanup.getSnid();
		 this.gameid = engineCleanup.getGameid();
		 this.gameName = engineCleanup.getGameName();
		 this.ds = engineCleanup.getDs();
		 this.errMessage = engineCleanup.getErrMessage();
	 }
	 
	 
	public String getSnid() {
		return snid;
	}

	public void setSnid(String snid) {
		this.snid = snid;
	}

	public String getGameid() {
		return gameid;
	}

	public void setGameid(String gameid) {
		this.gameid = gameid;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	 
	 
	 
}
