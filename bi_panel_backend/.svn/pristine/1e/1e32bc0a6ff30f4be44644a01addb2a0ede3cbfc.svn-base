package com.hoolai.bi.report.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * 
 */
public class FileUtil {


	/**
	 * 文件路径
	 * 
	 * @param recommendImgPath
	 * @param recommendDir
	 * @param filename
	 * @return
	 */
	public static String generalRecommendImgPath(String recommendImgPath,
			String recommendDir, String filename) {
		return recommendImgPath + File.separator + recommendDir
				+ File.separator + filename;
	}

	public static long writeToDisk(InputStream inputstream, File outputfile)
			throws IOException {
		long writtenBytesCount = 0;

		if (inputstream == null) {
			return writtenBytesCount;
		}

		// create local directory
		outputfile.getParentFile().mkdirs();

		FileOutputStream fileoutputstream = new FileOutputStream(outputfile);

		byte abyte0[] = new byte[1024];
		int j;
		while ((j = inputstream.read(abyte0)) >= 0) {
			fileoutputstream.write(abyte0, 0, j);
			writtenBytesCount += j;
		}

		fileoutputstream.close();
		inputstream.close();

		return writtenBytesCount;
	}

	public static long writeToDisk(InputStream inputstream, String outputfile)
			throws IOException {
		File opf = new File(outputfile);
		return writeToDisk(inputstream, opf);
	}

	public static void writeToDisk(byte[] data, File outputfile)
			throws IOException {
		// create local directory
		outputfile.getParentFile().mkdirs();

		FileOutputStream fileoutputstream = new FileOutputStream(outputfile);

		IOUtils.write(data, fileoutputstream);

		fileoutputstream.close();

		return;
	}

	public static void writeToDisk(byte[] data, String outputfile)
			throws IOException {
		File opf = new File(outputfile);
		writeToDisk(data, opf);
	}

	public static void writeToDisk(StringBuffer sb, File outputfile)
			throws IOException {
		// create local directory
		outputfile.getParentFile().mkdirs();

		FileOutputStream fileoutputstream = new FileOutputStream(outputfile);

		IOUtils.write(sb, fileoutputstream, "UTF-8");
	}
}
