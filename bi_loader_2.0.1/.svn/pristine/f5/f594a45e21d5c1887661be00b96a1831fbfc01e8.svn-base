package com.hoolai.loader.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.hoolai.loader.util.StringUtil;

/**
 * 
 * @author ruijie
 * @date 2013-11-4
 * @version V1.0
 */
public class PartitionDao {
	
	private static Logger log = Logger.getLogger(PartitionDao.class);
	
	private static final String HIVEURL = "jdbc:hive2://hlnn01:10001/default";
	
	private static PartitionDao dao = new PartitionDao();
	
	private PartitionDao() { }

	public static PartitionDao newInstance() {
		return dao;
	}
	
	public void addPartition(String path) throws Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		

		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
			
			boolean isRevision = StringUtil.isRevision(path);
			
			String param[] = StringUtil.getParams(path, isRevision);
			
			long begin=System.currentTimeMillis(); 
			

			String metric=param[0];
			String snid=param[1];
			String gameid=param[2];
			String clientid=null;
			String ds=null;
			
			if(isRevision){// 改版后
				
				ds=param[3];
				
				log.info(" add partition " + metric + " : [ snid = " + snid + ", gameid = " + gameid + ", ds = " + ds + " ] begin..");
				
				//String sql="ALTER TABLE nt_" + param[0] + " ADD IF NOT EXISTS PARTITION(snid=?,gameid=?,ds=?) LOCATION 'hdfs://pnn:9000/user/hive/warehouse/nt_" + param[0] +"/snid=" + param[1] + "/gameid=" + param[2] + "/ds=" + param[3] +"'";
				String sql="ALTER TABLE nt_" + param[0] + " ADD IF NOT EXISTS PARTITION(snid="+snid+",gameid="+gameid+",ds='"+ds+"') LOCATION 'hdfs://namenode-cluster/user/hive/warehouse/nt_" + metric +"/snid=" + snid + "/gameid=" + gameid + "/ds=" + ds +"'";
				log.info("exec sql ["+sql+"]");
				
				connection = DriverManager.getConnection(HIVEURL, "", "");
				pstm = connection.prepareStatement(sql);
				//pstm.setInt(1, Integer.parseInt(param[1]));
				//pstm.setInt(2, Integer.parseInt(param[2]));
				//pstm.setString(3, param[3]);
				pstm.execute();
				
//				pstm.close();
//				connection.close();
				
				log.info("partition " + metric + " : [ snid = " + snid + ", gameid = " + gameid + ", ds = " + ds + " ] begin..");
				
			} else {
				
				clientid=param[3];
				ds=param[4];
				
				log.info(" add partition " + metric + " : [ snid = " + snid + ", gameid = " + gameid + ", ds = " + ds + " ] begin..");
				
				//String sql="ALTER TABLE ht_" + param[0] + " ADD IF NOT EXISTS PARTITION(snid=?,clientid=?,gameid=?,ds=?) LOCATION 'hdfs://pnn:9000/user/hive/warehouse/rawdata/" + param[0] +"/" + path +"'";
				String sql="ALTER TABLE ht_" + param[0] + " ADD IF NOT EXISTS PARTITION(snid="+snid+",clientid="+clientid+",gameid="+gameid+",ds='"+ds+"') LOCATION 'hdfs://namenode-cluster/user/hive/warehouse/rawdata/" + metric +"/" + path +"'";
				log.info("exec sql ["+sql+"]");
				
				connection = DriverManager.getConnection(HIVEURL, "", "");
				pstm = connection.prepareStatement(sql);
//				pstm.setInt(1, Integer.parseInt(param[1]));
//				pstm.setInt(2, Integer.parseInt(param[2]));
//				pstm.setInt(3, Integer.parseInt(param[3]));
//				pstm.setString(4, param[4]);
				pstm.execute();
				
//				pstm.close();
//				connection.close();
//				
				log.info("partition " +  metric + " : [ snid = " + snid + ", gameid = " + gameid + ", ds = " + ds + " ] begin..");
			}
			long end=System.currentTimeMillis(); 
			log.info(" add partition " +  metric + " : [ snid = " + snid + ", gameid = " + gameid + ", ds = " + ds + " ] " + " ] end.. spendMills:"+(end-begin));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(pstm!=null){
				pstm.close();
			}
			if(connection!=null){
				connection.close();
			}
		}
	}
}