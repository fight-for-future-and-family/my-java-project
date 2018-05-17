package com.wl.controller.bushu;

import java.util.Date;

import com.wl.controller.tools.CentosCommand;

public class BushuService {

	public String table;
	public String snid;
	public String gameid;
	public String ds;
	public String target;
	
	
	
	
	public int dataManager() {
		int wl;
		if(this.target.equals("1")){
			String file=String.format("/user/hive/warehouse/%s/snid=%s/gameid=%s/ds=%s", this.table,this.snid,this.gameid,this.ds);
			String hive=String.format("sudo -u hive hive -e \'alter table %s add if not exists partition  (snid=%s,gameid=%s,ds=\"%s\") location \"%s\"; \'", 
                                       this.table,this.snid,this.gameid,this.ds,file);
			System.out.println(String.format("这里是增加数据的操作_____%s",hive));
			wl=new CentosCommand().executeCommand(hive);
			return wl;
			
		}else if (this.target.equals("2")){
			String delFile=String.format("sudo -u hdfs  hadoop fs -rm -r /user/hive/warehouse/%s/snid=%s/gameid=%s/ds=%s", 
					                  this.table,this.snid,this.gameid,this.ds);
			System.out.println(String.format("这里是删除数据的操作_____%s",delFile));
			wl=new CentosCommand().executeCommand(delFile);
			return wl;
		}
		System.out.println(String.format("没有调用系统命令执行工具类  at   %s",new Date().toString()));
		return 66;
	}
	
	
	
	
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
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
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
