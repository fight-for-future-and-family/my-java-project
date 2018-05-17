package com.hoolai.bi.report.util;

import java.util.Random;

/**
 * 随机数正则工具
 * 
 * @author zhizhong
 * 
 */
public class RandomUtil {

	/**
	 * 取范围内的随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		if (min == max)
			return min;
		if (min > max) {
			int t = min;
			min = max;
			max = t;
		}
		Random rnd = new Random();
		return rnd.nextInt(max - min + 1) + min;
	}
	
	public  static void main(String args[]){
		for(int i =0;i < 100;i++){
			System.out.println(random(0,10));
		}
	}

}