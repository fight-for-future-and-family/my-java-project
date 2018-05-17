package com.hoolai.bi;

import in.ankushs.dbip.api.DbIpClient;
import in.ankushs.dbip.api.GeoEntity;
import in.ankushs.dbip.model.GeoAttributes;
import in.ankushs.dbip.model.GeoAttributesImpl;
import in.ankushs.dbip.parser.CsvParser;
import in.ankushs.dbip.parser.CsvParserImpl;
import in.ankushs.dbip.repository.DbIpRepository;
import in.ankushs.dbip.repository.JavaMapDbIpRepositoryImpl;
import in.ankushs.dbip.utils.CountryResolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaStringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.common.net.InetAddresses;

public class IpResolve_v1 extends UDF {

	private static String ipPattern = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
	private static Pattern r = Pattern.compile(ipPattern);
	private static Matcher m;
	private static FSDataInputStream hdfsInStream;
	private static File gzip = null;
	private static DbIpClient client;
	private static GeoEntity geoEntity;
	private static FileInputStream fs;
	private static InputStream fis;
	private static String dsf;
	private static final DbIpRepository repository = JavaMapDbIpRepositoryImpl.getInstance();
//	private static IpResolve ipr;

	public IpResolve_v1() {
		try {
			System.out.println("create a new instance"+new Date().getDate());
			dsf = "/usr/lib/hive/udfs/dbipcity.csv.gz";
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://hlnn01:9000/");
			FileSystem fs = FileSystem.get(URI.create(dsf), conf);
			hdfsInStream = fs.open(new Path(dsf));
			fis = (InputStream) hdfsInStream;
			this.dbIpInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {

//		ipr.dbIpInit();
//		System.out.println(ipr.lookup(args[0]));
		// if (null != args && !"".equals(args[0])) {
		// IpResolve ipr = new IpResolve();
		// ipr.dbIpInit();
		// ipr.evaluate("test");
		// // m = r.matcher(args[0]);
		// // if (m.find()) {
		// // System.out.println(ipr.evaluate(new Text(args[0])).toString());
		// // } else {
		// // System.out.println("The IP format is wrong");
		// // }
		// } else {
		// System.out.println("No arguments");
		// }
	}

	public static String evaluate(String str) {
		try {
			System.out.println("string method"+new Date());
//			if(null==ipr){
//			ipr = new IpResolve();}
			// ipr.dbIpInit();
			JavaStringObjectInspector stringInspector = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
			String ip = stringInspector.getPrimitiveJavaObject(str);
			GeoEntity temp = IpResolve_v1.lookup(ip);
			String country = temp.getCountry();
			String province = temp.getProvince();
			String city = temp.getCity();
			return country + "-" + province + "-" + city;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("the string method has error");
		}
		return "string method wl";
	}

	public static Text evaluate(Text str) {
		try {
			System.out.println("text method"+new Date());
//			if(null==ipr){
//			ipr = new IpResolve();}
			JavaStringObjectInspector stringInspector = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
			String ip = stringInspector.getPrimitiveJavaObject(str);
			GeoEntity temp = IpResolve_v1.lookup(ip);
			String country = temp.getCountry();
			String province = temp.getProvince();
			String city = temp.getCity();
			return new Text(country + "-" + province + "-" + city);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("the text method has error");
		}
		return new Text("text method wl");
	}

	public static GeoEntity lookup(final String ip) {
		InetAddress inetAddress = InetAddresses.forString(ip);
		GeoEntity geoEntity = repository.get(inetAddress);
		return geoEntity;
	}

	public static void dbIpInit() {
		Logger logger = LoggerFactory.getLogger(IpResolve_v1.class);
		CsvParser csvParser = CsvParserImpl.getInstance();
		Interner<String> interner = Interners.newWeakInterner();
//		repository = JavaMapDbIpRepositoryImpl.getInstance();

		// try {
		// PreConditions.checkExpression(!GzipUtils.isGzipped(file),"Not a  gzip file");
		// } catch (final IOException ex) {
		// logger.error("", ex);
		// }

		// System.out.println("Staring read the db-ip file");
		int i = 0;
		try {
			// final InputStream fis = new FileInputStream(file);
			final InputStream gis = new GZIPInputStream(fis);
			final Reader decorator = new InputStreamReader(gis,
					StandardCharsets.UTF_8);
			final BufferedReader reader = new BufferedReader(decorator);
			logger.debug("Reading dbip data from {}" + dsf);
			String line = null;
			while ((line = reader.readLine()) != null) {
				i++;
				final String[] array = csvParser.parseRecord(line);
				final GeoAttributes geoAttributes = new GeoAttributesImpl.Builder()
						.withStartInetAddress(InetAddresses.forString(array[0]))
						.withEndInetAddress(InetAddresses.forString(array[1]))
						.withCountryCode(array[2])
						.withCountry(
								CountryResolver.resolveToFullName(array[2]))
						.withProvince(interner.intern(array[3]))
						.withCity(interner.intern(array[4])).build();
				repository.save(geoAttributes);
				if (i % 100000 == 0) {
					logger.debug("Loaded {} entries", i);
				}
			}
			// System.out.println("read some records" + i);
		}

		catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
