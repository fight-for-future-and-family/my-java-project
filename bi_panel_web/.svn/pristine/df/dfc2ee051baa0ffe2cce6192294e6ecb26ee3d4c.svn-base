package com.hoolai.panel.web.processor;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.hoolai.manage.model.CustomReportModel;

public class CustomReportTaskProcessor {
	
	public enum AddTaskRetStatus{
		SUCCESS,PARM_NULL,TASK_EXIST,NO_TIME,ERROR
	}

	private CustomReportModel customReportModel;
	private String[] nameArr;
	private String[] valueArr;
	
	public CustomReportTaskProcessor(CustomReportModel customReportModel,
			String[] nameArr,String[] valueArr){
		this.customReportModel = customReportModel;
		this.nameArr = nameArr;
		this.valueArr = valueArr;
	}
	
	
	public Map<String, String> parseMap(String[] nameArr, String[] valueArr) {
		Map<String,String> parm = new HashMap<String, String>();
		int len = nameArr.length;
		for(int i= 0;i<len;i++){
			parm.put(nameArr[i].split(":")[1], valueArr[i]);
		}
		return parm;
	}

	public String getTaskCode(){
		int len = nameArr.length;
		StringBuffer buff = new StringBuffer();
		for(int i= 0;i<len;i++){
			buff.append(nameArr[i].split(":")[1]);
			buff.append("=");
			buff.append(valueArr[i]);
			buff.append(",");
		}
		return buff.toString();
	}
	
	public String getTaskName() {
		int len = nameArr.length;
		StringBuffer buff = new StringBuffer();
		for(int i= 0;i<len;i++){
			buff.append(nameArr[i].split(":")[0]);
			buff.append("=");
			buff.append(valueArr[i]);
			buff.append(",");
		}
		return buff.toString();
	}
	
	public String sql() throws Exception{
		Map<String,String> envParams = parseMap(nameArr, valueArr);
		return sql(envParams,customReportModel.getTemplateSql());
	}
	
	public Integer isPresto() {
		return customReportModel.getIsPresto();
	}
	
	public static String sql(Map<String,String> envParams,String templateSql) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException{
		//替换参数值
		StringWriter sw = new StringWriter();
		Context cnxt = new VelocityContext();
		
		for(Entry<String, String> entry: envParams.entrySet()){
			cnxt.put(entry.getKey(), entry.getValue());
		}
		Velocity.evaluate(cnxt, sw, "velocity", templateSql);
		return sw.toString();
	}
	
	public static void main(String[] args) {
		try{ 
	         String content="select * from daily_report where snid=${snid} and gameid=${gameid} and day >= ${beginDate} and day <=${endDate}";
	         String ps1="\\${[^}]+\\}"; 
	         PatternCompiler orocom=new Perl5Compiler(); 
	         Pattern pattern1=orocom.compile(ps1); 
	         PatternMatcher matcher=new Perl5Matcher(); 
	         
	         PatternMatcherInput input=new PatternMatcherInput(content);
            while (matcher.contains(input,pattern1)){ 
                  MatchResult result= matcher.getMatch(); 
                  System.out.println(result.group(0).replace("${", "").replace("}", "")); 
            } 
	       } 
	  catch(Exception e) { 
	             System.out.println(e); 
	}
	}

}
