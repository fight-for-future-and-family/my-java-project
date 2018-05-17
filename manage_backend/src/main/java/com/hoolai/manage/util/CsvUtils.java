package com.hoolai.manage.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class CsvUtils {
	
	private static Logger log = Logger.getLogger(CsvUtils.class);
	
	private static List<File> srcfile = new ArrayList<File>();
	
   /** 
	* @Title: createCSVFile 
	* @Description: 生成CSV文件 
	* @param exportData		源数据List
	* @param map	CSV文件的列表头map
	* @param outPutPath 	文件路径
	* @param fileName 	文件名称
	* @return    File
	*/
	public static File createCSVFile(List exportData, LinkedHashMap map, String outPutPath, String fileName) {
		File csvFile = null;
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			List<String> listStr = new ArrayList<String>();
			// 定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
			// UTF-8使正确读取分隔符","
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "gbk"), 1024);
			// 写入文件头部
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
				bufferedWriter.write("" + (String) propertyEntry.getValue() != null ? (String) propertyEntry
								.getValue() : "" + "");
				if (propertyIterator.hasNext()) {
					bufferedWriter.write(",");
				}
				listStr.add(propertyEntry.getKey().toString());
			}
			bufferedWriter.newLine();
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Map row = (Map) iterator.next();
				for (Iterator iter = listStr.iterator(); iter.hasNext();) {
					String values = iter.next().toString();
					bufferedWriter.write(row.get(values).toString());
					if (iter.hasNext()) {
						bufferedWriter.write(",");
					}
				}
				if (iterator.hasNext()) {
					bufferedWriter.newLine();
				}
			}
			bufferedWriter.flush();

			srcfile.add(csvFile);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}
		  
	/**
	 * 导出CSV文件
	 * @param response
	 * @param fileInServer fileInServer 服务器端的临时文件路径+临时文件名称
	 * @param exportedfileInClient 客户端导出的文件名称
	 * @throws IOException
	 */
	public static void exportCsv(HttpServletResponse response,
			String fileInServer, String exportedfileInClient) throws IOException {
		// 文件名称转换编码格式
		exportedfileInClient = new String(exportedfileInClient.getBytes("gbk"), "ISO8859-1");
		response.setContentType("application/csv;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ exportedfileInClient);

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(fileInServer);
			int len = 0;
			byte[] buffer = new byte[1024];
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			log.debug("create CSV exception" + e);
		} finally {
			if (in != null) {
				try {
					in.close();
					out.close();
				} catch (Exception e) {
//					throw new RuntimeException(e);
				}
			}
		}
		// 将相应的文件删除
		File excelFile = new File(fileInServer);
		if (excelFile.exists()) {
			if(excelFile.listFiles() != null){//删除文件夹及文件
				File[] files = excelFile.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						files[i].delete();//删除文件
					}
				}
				excelFile.delete();//删除文件夹
			}else{
				excelFile.delete();//删除文件
			}
			
		}
		srcfile.clear();
	}
	
	public static List<String> importCsv(MultipartFile file) throws IOException{
        List<String> dataList=new ArrayList<String>();
        
        InputStream in= file.getInputStream();
        byte[] b = new byte[3];
        in.read(b);
        in.close();
        
        BufferedReader br=null;
        String line = "";
    	if (b[0] == -17 && b[1] == -69 && b[2] == -65)  
    		br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
    	else  
        	br = new BufferedReader(new InputStreamReader(file.getInputStream(),"GB2312"));
    	try {
    		int num = 0;
            while ((line = br.readLine()) != null) {
            	if(num>0){
            		dataList.add(line);
            	}
            	num++;
            }
		} catch (Exception e) {
		} finally{
			if(br!=null){
				try {
					br.close();
					br=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
        return dataList;
    }
	
}
