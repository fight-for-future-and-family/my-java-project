package com.hoolai.loader.main;

import java.io.IOException;

import com.hoolai.loader.util.CountLoadUtil;

/**
 * 仅仅是 测试   配置文件中  指定的  jar 包是否已经运行
 *
 */
public class Test {
	
	public static void main(String[] args) throws IOException {
		
		CountLoadUtil.check();
		
	}
}
