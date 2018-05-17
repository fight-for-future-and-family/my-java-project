package com.hoolai.bi;

import in.ankushs.dbip.api.DbIpClient;
import in.ankushs.dbip.api.GeoEntity;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class IpResolve extends UDF {

	private static String ipPattern = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
	private static Pattern r = Pattern.compile(ipPattern);
	private static Matcher m;

	private static IpResolve ipr = new IpResolve();

	private static File gzip = new File("/usr/lib/hive/udfs/dbip-city.csv.gz");
	private static DbIpClient client = new DbIpClient(gzip);
	private static GeoEntity geoEntity;

	public static void amain(String[] args) {
		if (null != args && !"".equals(args[0])) {
			m = r.matcher(args[0]);
			if (m.find()) {
				System.out.println(ipr.evaluate(new Text(args[0])).toString());
			} else {
				System.out.println("The IP format is wrong");
			}
		} else {
			System.out.println("No arguments");
		}
	}

	public Text evaluate(Text str) {
		geoEntity = client.lookup(str.toString());
		return new Text(geoEntity.getCountry() + "-" + geoEntity.getProvince()
				+ "-" + geoEntity.getCity());

	}

}
