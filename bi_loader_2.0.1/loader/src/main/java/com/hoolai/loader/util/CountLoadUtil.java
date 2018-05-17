package com.hoolai.loader.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CountLoadUtil {

	public static boolean check(){
		boolean result = false;
		
		try {
			String[] cmds = new String[]{"/bin/bash", "-c", Constant.checkNumCmd};
			
			Process ps = Runtime.getRuntime().exec(cmds);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			
			String line;
			int num = -1;
			if ((line = br.readLine()) != null) {
				num = Integer.parseInt(line);
			}
			
			if(num == 1){
				result = true;
			}
			
		} catch (Exception e){
			
		}
		
		return result;
	}
}
