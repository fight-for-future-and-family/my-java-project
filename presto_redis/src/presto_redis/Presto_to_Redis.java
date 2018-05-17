package presto_redis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class Presto_to_Redis {
	
	String snid;
	String[] gameid;
	String ds;
	String start_time;
	String end_time;
	String redis_server;
	String redis_port;
	String alldata;
	
	
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		String sql;
		Long total_number=0L;
		Long dbsize_original=0L;
		Long dbsize_end=0L;
		try {
			Class.forName("com.facebook.presto.jdbc.PrestoDriver");
			connection = DriverManager.getConnection("jdbc:presto://119.254.145.152:8181/hive/default", "root",null);
			Presto_to_Redis temprdis=new Presto_to_Redis();
			sql = temprdis.getSql();
			System.out.println("**********"+new SimpleDateFormat("yyyy--MM--dd HH:mm:ss").format(new Date())+"**********");
			System.out.println(sql);
			System.out.println(temprdis.redis_server+"   "+temprdis.redis_port);
			statement=connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.setFetchSize(100000);
			
			
			JedisPool jedisPool;
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(100000); 
			config.setMaxIdle(1000);
			config.setMaxWait(20000);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(false);
			jedisPool = new JedisPool(config,temprdis.redis_server, Integer.valueOf(temprdis.redis_port),1000000);


			Jedis jedis = jedisPool.getResource();  
			
			dbsize_original=jedis.dbSize();
			
			Pipeline pipeline = jedis.pipelined();
			String roleid, family,wl;
			
			while (resultSet.next()) {
			    roleid = resultSet.getString("roleid");
			    family = resultSet.getString("family");
				wl = "roleid is " + roleid  +", family is " + family;
				total_number++;
				
			    try{
			        if(null!=family&&!"".equals(family)&&null!=roleid&&!"".equals(roleid)){
			        	pipeline.set(roleid, family);
			        }else{
			        	System.out.println("____________________________________________________");
				        System.out.println(wl);
			        }
			    }catch(JedisConnectionException e){
			    	System.out.println("____________________________________________________");
			    	System.out.println("Something wrong is happened while iterating the resulset ");
			    	System.out.println("____________________________________________________");
			    	System.out.println(String.format("There is  %s rows already susscssfull checked out !",total_number));
			    	System.out.println("____________________________________________________");
			        e.printStackTrace();
			    }
				
			   
			}

			pipeline.sync();//持久化
			
			dbsize_end=jedis.dbSize();
			System.out.println(String.format("There is  %s rows already susscssfull sended !",Long.toString(dbsize_end-dbsize_original)));
			System.out.println(String.format("There is  %s rows duplicated or the roleid/family is empty !",Long.toString(total_number-(dbsize_end-dbsize_original))));
			
			
			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException sqlException) {
			System.out.println("____________________________________________________");
			System.out.println(String.format("There is  %s rows already susscssfull sended !",Long.toString(dbsize_end-dbsize_original)));
			System.out.println(String.format("There is  %s rows duplicated or the roleid/family is empty !",Long.toString(total_number-(dbsize_end-dbsize_original))));
			System.out.println("____________________________________________________");
			sqlException.printStackTrace();
			System.out.println("____________________________________________________");
			System.out.println("sql exection   counts on something wrong !");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	

	//拼写执行 sql 语句
	public  String getSql() {
		this.configGet();
		String hoolai = "'";
		for (String string : this.gameid) { hoolai=hoolai+string+"','"; }
		char[] hulai=hoolai.toCharArray();
		hulai=Arrays.copyOfRange(hulai, 0, hulai.length-2);
		
		
		String exec_sql=null;
		if(null!=this.alldata&&!"".equals(this.alldata)&&this.alldata.equals("1")){
			
//			exec_sql=String.format("select roleid,family from default.nt_counter where snid='%s' and gameid in (%s) " , this.snid,String.valueOf(hulai),this.ds);
		  exec_sql=String.format("select roleid,family from default.nt_counter where snid='%s' and gameid in (%s) and ds<='%s' order by roleid,family,counter_time asc " , this.snid,String.valueOf(hulai),this.ds);
		}else if(null!=this.alldata&&!"".equals(this.alldata)&&this.alldata.equals("0")) {
			
//		  exec_sql=String.format("select roleid,family from default.nt_counter where snid='%s' and gameid in (%s) and ds='%s' limit 10" , this.snid,String.valueOf(hulai),this.ds);
		  exec_sql=String.format("select roleid,family from default.nt_counter where snid='%s' and gameid in (%s) and ds='%s' and counter_time>='%s' and counter_time<'%s' order by roleid,family,counter_time asc " , this.snid,String.valueOf(hulai),this.ds,this.start_time,this.end_time);
		}else{
			System.out.println("The alldata is configured with wrong value");
			System.exit(1);
		}
		return exec_sql;
	}
	
	
	
	//读取 properties配置文件
	public  void configGet(){
		Properties hoolai_redis =new Properties();
	    try {
	    	 hoolai_redis.load(Presto_to_Redis.class.getClassLoader().getResourceAsStream("presto_to_redis.properties"));
			 snid=hoolai_redis.getProperty("snid").trim();
			 gameid=hoolai_redis.getProperty("gameid").trim().split(",");
			 ds=hoolai_redis.getProperty("ds").trim();
			 start_time=hoolai_redis.getProperty("start_time").trim();
			 end_time=hoolai_redis.getProperty("end_time").trim();
			 redis_server=hoolai_redis.getProperty("redis_server").trim();
			 redis_port=hoolai_redis.getProperty("redis_port").trim();
			 alldata=hoolai_redis.getProperty("alldata").trim();
		} catch (Exception e) {
			System.out.println("配置文件读取有误！");
		}
	}
	
	

}