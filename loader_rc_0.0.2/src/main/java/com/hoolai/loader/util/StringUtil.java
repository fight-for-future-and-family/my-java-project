package com.hoolai.loader.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	/**
	 * 首字母大写
	 * @param s
	 * @return
	 */
	public static String toUpperCaseInitial(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}
	
	/**
	 * 通过数据文件路径判断是否改版
	 * 当不存在 message_click时，用  _ 进行分割，如果存在的元素个数小于5 则为 真
	 * 如果存在 message_click_ 时，那么把这个 字符串去掉， 再用  _ 进行分割， 如果存在元素个数小于4个 则为真
	 * 否则返回 false
	 */
	public static boolean isRevision(String path){
		
		boolean result = false;
		
		if(path.indexOf("message_click") < 0){
			result = StringUtils.split(path, "_").length < 5;
		} else {
			path = StringUtils.replace(path, "message_click_", "");
			
			result = StringUtils.split(path, "_").length < 4;
		}
		
		return result;
	}
	
	/**
	 * 通过目录名获取metric,snid、gameid、ds   组成的数组
	 */
	public static String[] getParams(String path, boolean isRevision){
		String result[] = new String [isRevision ? 4 : 5];
		
		if(path.indexOf("message_click") < 0){
			result = StringUtils.split(path, "_");
		} else {
			result[0] = "message_click";
			path = StringUtils.replace(path, "message_click_", "");
			
			String param[] = StringUtils.split(path, "_");
			
			System.arraycopy(param, 0, result, 1, isRevision ? 3 : 4);
		}
		
		return result;
	}
	
	/**
	 * 获取不带扩展名的文件名
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length()))) {   
                return filename.substring(0, dot);   
            }   
        }   
        return filename;   
    }
}
