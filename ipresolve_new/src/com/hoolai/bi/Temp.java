package com.hoolai.bi;



	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.URI;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.StringTokenizer;
	import org.apache.hadoop.conf.Configuration;
	import org.apache.hadoop.fs.FSDataInputStream;
	import org.apache.hadoop.fs.FileSystem;
	import org.apache.hadoop.fs.Path;
	import org.apache.hadoop.hive.ql.exec.UDF;
	import org.apache.hadoop.io.IOUtils;
	import org.apache.hadoop.io.Text;

	public class Temp extends UDF {
	{
	  public static List<String> map = new ArrayList();
	  public static long[] start_from_index;
	  public static long[] end_to_index;
	  public static Map<Long, String> idcCache = new HashMap();
	  public static Map<Long, String> regionCache = new HashMap();
	  public static Map<Long, String> cityCache = new HashMap();

	  private void LoadIPLocation()
	  {
	    Configuration conf = new Configuration();
	    String namenode = conf.get("fs.default.name");
	    String uri = namenode + "/user/hadoop/IP.csv";
	    FileSystem fs = null;
	    FSDataInputStream in = null;
	    BufferedReader d = null;
	    try
	    {
	      fs = FileSystem.get(URI.create(uri), conf);
	      in = fs.open(new Path(uri));
	      d = new BufferedReader(new InputStreamReader(in));
	      String s = null;
	      while (true)
	      {
	        s = d.readLine();
	        if (s == null) {
	          break;
	        }
	        map.add(s);
	      }
	    }
	    catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      IOUtils.closeStream(in);
	    }
	  }

	  public static int binarySearch(long[] start, long[] end, long ip)
	  {
	    int low = 0;
	    int high = start.length - 1;
	    while (low <= high) {
	      int middle = (low + high) / 2;
	      if ((ip >= start[middle]) && (ip <= end[middle]))
	        return middle;
	      if (ip < start[middle])
	        high = middle - 1;
	      else {
	        low = middle + 1;
	      }
	    }
	    return -1;
	  }

	  public static long ip2long(String ip)
	  {
	    if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
	      String[] ips = ip.split("[.]");
	      long ipNum = 0L;
	      if (ips == null) {
	        return 0L;
	      }
	      for (int i = 0; i < ips.length; i++) {
	        ipNum = ipNum << 8 | Long.parseLong(ips[i]);
	      }

	      return ipNum;
	    }
	    return 0L;
	  }

	  public String evaluate(Text ip, Text which) {
	    long ipLong = ip2long(ip.toString());
	    String whichString = which.toString();

	    if ((!whichString.equals("IDC")) && (!whichString.equals("REGION")) && (!whichString.equals("CITY")))
	    {
	      return "Unknown Args!use(IDC or REGION or CITY)";
	    }

	    if (map.size() == 0) {
	      LoadIPLocation();
	      start_from_index = new long[map.size()];
	      end_to_index = new long[map.size()];
	      for (int i = 0; i < map.size(); i++) {
	        StringTokenizer token = new StringTokenizer((String)map.get(i), ",");
	        token.nextToken();
	        start_from_index[i] = Long.parseLong(token.nextToken());
	        end_to_index[i] = Long.parseLong(token.nextToken());
	      }

	    }

	    int ipindex = 0;
	    if (((whichString.equals("IDC")) && (!idcCache.containsKey(Long.valueOf(ipLong)))) || ((whichString.equals("REGION")) && (!regionCache.containsKey(Long.valueOf(ipLong)))) || ((whichString.equals("CITY")) && (!cityCache.containsKey(Long.valueOf(ipLong)))))
	    {
	      ipindex = binarySearch(start_from_index, end_to_index, ipLong);
	    }
	    if (ipindex == 0) {
	      if (whichString.equals("IDC"))
	        return (String)idcCache.get(Long.valueOf(ipLong));
	      if (whichString.equals("REGION"))
	        return (String)regionCache.get(Long.valueOf(ipLong));
	      if (whichString.equals("CITY")) {
	        return (String)cityCache.get(Long.valueOf(ipLong));
	      }
	      return "Error";
	    }
	    if (ipindex == -1) {
	      return "Other IDC";
	    }

	    String[] location = ((String)map.get(ipindex)).split(",");
	    if (whichString.equals("IDC")) {
	      idcCache.put(Long.valueOf(ipLong), location[5]);
	      return location[5];
	    }if (whichString.equals("REGION")) {
	      regionCache.put(Long.valueOf(ipLong), location[3]);
	      return location[3];
	    }if (whichString.equals("CITY")) {
	      cityCache.put(Long.valueOf(ipLong), location[4]);
	      return location[4];
	    }
	    return "Error";
	  }

	  public static void main(String[] args)
	  {
	    long startTime = System.currentTimeMillis();
	    System.out.println("now:" + startTime);
	    UDFGetIPLocationNew getIPLocation = new UDFGetIPLocationNew();
	    Text ip = new Text("112.122.64.0");

	    System.out.printf("ip = %s, %s, %s, %s\n", new Object[] { ip, getIPLocation.evaluate(ip, new Text("IDC")), getIPLocation.evaluate(ip, new Text("REGION")), getIPLocation.evaluate(ip, new Text("CITY")) });

	    long endTime = System.currentTimeMillis();
	    System.out.println("over:" + endTime);
	    System.out.println("count:" + (endTime - startTime) * 1.0D / 1000.0D);
	  }
	}
}
