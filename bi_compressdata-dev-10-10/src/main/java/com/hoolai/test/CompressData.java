package com.hoolai.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

public class CompressData implements Runnable {
	
	private static final Configuration conf = new Configuration();
	private static final ArrayList<Path> files = new ArrayList();
	private static String type = null;
	private Path file = null;

	public CompressData() {
	}

	public CompressData(String file) {
		this.file = new Path(file);
	}

	public CompressData(Path p) {
		this.file = p;
	}

	public static String getSystemDate() {
		Date today = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(today);
	}

	public static boolean needCompress(FileStatus fileStatus) {
		if (fileStatus.isDir()) {
			return false;
		}

		CompressionCodecFactory factory = new CompressionCodecFactory(conf);
		CompressionCodec codec = factory.getCodec(fileStatus.getPath());

		return (codec == null);
	}

	public static void getFiles(Path rootPath, String day) throws IOException {
		FileStatus[] arrayOfFileStatus1;
		FileSystem fs = FileSystem.get(conf);
		FileStatus[] fss = fs.listStatus(rootPath);
		int j = (arrayOfFileStatus1 = fss).length;
		for (int i = 0; i < j; ++i) {
			FileStatus fileStatus = arrayOfFileStatus1[i];
			if (fileStatus.isDir()) {
				getFiles(fileStatus.getPath(), day);
			} else if (needCompress(fileStatus)) {
				Path p = fileStatus.getPath();
				String today = getSystemDate();
				if (!(p.toString().contains(today))){
					if ( ((day != null) && (p.toString().contains(day)) && (!(p.toString().contains("_SUCCESS"))))
							|| ((day == null) && (!(p.toString().contains("_SUCCESS")))) ){
						files.add(fileStatus.getPath());
					} else {
						System.out.println(String.format("file path [%s] contains datestamp of system today, it is not safe to compress this file, so skipped!",
								new Object[] { p.toString() }));
					}
				}
			}
		}
	}
	
	public static void compress(Path p, String codecClassname) throws ClassNotFoundException, IOException {
		FileSystem fs = FileSystem.get(conf);
		if (!(fs.getFileStatus(p).isDir())) {
			Class codecClass = Class.forName(codecClassname);
			CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, conf);
			Path output = p.suffix(codec.getDefaultExtension());
			BufferedInputStream is = new BufferedInputStream(fs.open(p));
			FSDataOutputStream os = fs.create(output);
			CompressionOutputStream out = codec.createOutputStream(os);
			IOUtils.copyBytes(is, out, 4096, true);
			fs.delete(p, false);
			System.out.println(String.format("[%s] compressed using [%s]", new Object[] { p, codecClassname }));
		}
	}

	public void startWithThreads(String dir, int threads, String day) throws InterruptedException, IOException {
		ExecutorService exec = Executors.newFixedThreadPool(threads);
		getFiles(new Path(dir), day);
		for (Iterator localIterator = files.iterator(); localIterator.hasNext();) {
			Path p = (Path) localIterator.next();
			exec.execute(new CompressData(p));
		}
		exec.shutdown();
		exec.awaitTermination(12L, TimeUnit.HOURS);
	}

	public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException, InterruptedException {
		Options options = new Options();
		options.addOption("dir", true,
				"Specify the dir contains files need to be compressed");
		options.addOption("threads", true, "threads num");
		options.addOption("day", true, "which day is today");
		options.addOption("type", true, "type");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);
//		String dir = cmd.getOptionValue("dir", "/user/hive/warehouse/rawdata/");
		String dir = cmd.getOptionValue("dir", null);
		if(dir==null){
			return ;
		}
		int threads = Integer.parseInt(cmd.getOptionValue("threads", "10"));
		String day = cmd.getOptionValue("day", null);
		type = cmd.getOptionValue("type", "lzo");

		CompressData compressData = new CompressData();
		compressData.startWithThreads(dir, threads, day);
	}

	public void run() {
		try {
			String codecClassname = "";
			if("lzo".equals(type)) {
				codecClassname = "com.hadoop.compression.lzo.LzopCodec";
			} else if ("snappy".equals(type)) {
				codecClassname = "org.apache.hadoop.io.compress.SnappyCodec";
			}
			System.out.println(codecClassname+"   start");
			compress(this.file, codecClassname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class IncludePathFilter implements PathFilter {
		private final String regex;

		public IncludePathFilter(String regex, String paramString) {
			this.regex = regex;
		}

		@Override
		public boolean accept(Path path) {
			return path.toString().matches(this.regex);
		}

	}

}
