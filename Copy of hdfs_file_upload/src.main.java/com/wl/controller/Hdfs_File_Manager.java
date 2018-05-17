package com.wl.controller;

import java.util.Date;


public class Hdfs_File_Manager {

	
	 public int loadFile(String fileName,String filePath) {
		 
		 int exitVal=0;
         try {
             Runtime rt = Runtime.getRuntime();
             String fuck=String.format("hadoop fs -put -f %s  %s", fileName,filePath);
             System.out.println("888888888888888"+fuck);
             Process pr = rt.exec(fuck);

            /*
             * 输出命令执行结果
             *  BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

             String line;
             while((line=input.readLine()) != null) {
                 System.out.println(line);
             }*/

               exitVal = pr.waitFor();
             if(exitVal==0){
            	 System.out.println(String.format("The file __%s__ has been uploaded to the directory ___%s__  on  the cluster successfullly on %s !",fileName,filePath,new Date().toString()));
             }else {
            	 System.out.println(String.format("The file .....%s..... has been uploaded to the directory .....%s.....  on  the cluster failed on %s !",fileName,filePath,new Date().toString()));
			}
             System.out.println("Exited with error code "+exitVal);
             
         } catch(Exception e) {
             System.out.println(e.toString());
             System.out.println("遇到了一些未知的错误");
             e.printStackTrace();
         }
         return exitVal;
     }
}
