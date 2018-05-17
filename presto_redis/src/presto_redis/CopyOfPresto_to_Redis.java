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

public class CopyOfPresto_to_Redis {
	
	String snid;
	String[] gameid;
	String ds;
	String start_time;
	String end_time;
	String redis_server;
	String redis_port;
	String alldata;
	
	
	public static void main(String[] args) {
		
	
	
	
	
	}

}