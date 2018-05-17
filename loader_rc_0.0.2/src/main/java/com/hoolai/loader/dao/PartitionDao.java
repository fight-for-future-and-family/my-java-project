package com.hoolai.loader.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.hoolai.loader.util.StringUtil;

/**
 * 连接 固定的  hive  jdbc:hive://monet00:12222/default
 * 按照传入的字符串 进行分区的添加
 * 
 */
public class PartitionDao {
	
	private static Logger log = Logger.getLogger(PartitionDao.class);
	
	private static final String HIVEURL = "jdbc:hive://monet00:12222/default";
	
	private static PartitionDao dao = new PartitionDao();
	
	private PartitionDao() { }

	public static PartitionDao newInstance() {
		return dao;
	}
	public void addPartition(String path) throws Exception {
	    Connection connection = null;
	    PreparedStatement pstm = null;
	    
	    Class.forName("org.apache.hadoop.hive.jdbc.HiveDriver");
	    
	    connection = DriverManager.getConnection("jdbc:hive://monet00:12222/default", "", "");
	    
	    boolean isRevision = StringUtil.isRevision(path);
	    
	    String[] param = StringUtil.getParams(path, isRevision);
	    
	    if (isRevision)
	    {
	      pstm = connection.prepareStatement("ALTER TABLE nt_" + param[0] + " ADD IF NOT EXISTS PARTITION(snid=?,gameid=?,ds=?) LOCATION 'hdfs://pnn:9000/user/hive/warehouse/nt_" + param[0] + "/snid=" + param[1] + "/gameid=" + param[2] + "/ds=" + param[3] + "'");
	      pstm.setInt(1, Integer.parseInt(param[1]));
	      pstm.setInt(2, Integer.parseInt(param[2]));
	      pstm.setString(3, param[3]);
	      pstm.execute();
	      
	      pstm.close();
	      connection.close();
	      
	      log.info("partition " + param[0] + " : [ snid = " + param[1] + ", gameid = " + param[2] + ", ds = " + param[3] + " ]");
	    }
	    else
	    {
	      pstm = connection.prepareStatement("ALTER TABLE ht_" + param[0] + " ADD IF NOT EXISTS PARTITION(snid=?,clientid=?,gameid=?,ds=?) LOCATION 'hdfs://pnn:9000/user/hive/warehouse/rawdata/" + param[0] + "/" + path + "'");
	      pstm.setInt(1, Integer.parseInt(param[1]));
	      pstm.setInt(2, Integer.parseInt(param[2]));
	      pstm.setInt(3, Integer.parseInt(param[3]));
	      pstm.setString(4, param[4]);
	      pstm.execute();
	      
	      pstm.close();
	      connection.close();
	      
	      log.info("partition " + param[0] + " : [ snid = " + param[1] + ", clientid = " + param[2] + ", gameid = " + param[3] + ", ds = " + param[4] + " ]");
	    }
	  }
	
	
}