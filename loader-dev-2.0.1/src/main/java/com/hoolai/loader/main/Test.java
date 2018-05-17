package com.hoolai.loader.main;

import java.io.IOException;

import com.hoolai.loader.util.CountLoadUtil;

/**
 * @author WL
 * 调用CountLoadUtil  根据配置文件中的  shell 命令判断jar包是否已经运行，没有其他操作
 */
public class Test {
	
	public static void main(String[] args) throws IOException {
		
		CountLoadUtil.check();
		
	}
}
