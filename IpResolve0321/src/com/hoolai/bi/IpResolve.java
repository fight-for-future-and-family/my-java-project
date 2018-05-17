package com.hoolai.bi;

import in.ankushs.dbip.api.DbIpClient;
import in.ankushs.dbip.api.GeoEntity;

import java.io.File;

public class IpResolve {

	public static void main(String[] args) {
//		private static File gzip = new File("/usr/lib/hive/udfs/dbip-city.csv.gz");
		File gzip = new File("D:\\工作相关\\IP地址转换\\dbip-city-2018-03.csv.gz");
		DbIpClient client = new DbIpClient(gzip);
		GeoEntity geoEntity = client.lookup("31.45.127.255");
		String city = geoEntity.getCity();
		String country = geoEntity.getCountry();
		String province = geoEntity.getProvince();
		String countryCode = geoEntity.getCountryCode();

		System.out.println("city : " + city);
		System.out.println("province : " + province);
		System.out.println("country : " + country);
		System.out.println("country code : " + countryCode);
	}
	
}
