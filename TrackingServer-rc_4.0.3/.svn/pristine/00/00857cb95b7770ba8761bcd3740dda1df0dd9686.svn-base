package com.hoolai.bi.tracking.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hoolai.bi.tracking.log.StatsManager;
import com.hoolai.util.JSONUtils;

public class TrackingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER=Logger.getLogger("tracking");
	
	private static final Logger LOGGER_ERROR=Logger.getLogger("trackingError");

	public TrackingServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
		// Put your code here
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doPost(request, response);
		
	}

	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String metric = request.getParameter("metric");
		String queryString=this.getParamsUrl(request);

		Map<String,Object> jsonData=new HashMap<String,Object>();
		jsonData.put("metric", metric);
		jsonData.put("queryString", queryString);

		try {
			// 要记录的Metrics是什么，需要验证
			int status = StatsManager.log(metric, request);
			jsonData.put("status", status);
			response.getWriter().print(status);

			if(status<3){
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug(JSONUtils.toJSON(jsonData));
				}
			}else{
				LOGGER_ERROR.error(JSONUtils.toJSON(jsonData));
			}
		} catch (Exception e) {
			jsonData.put("errMsg", e.getMessage());
			LOGGER_ERROR.error(JSONUtils.toJSON(jsonData));
			e.printStackTrace();
		}
	}
	
	private String getParamsUrl(HttpServletRequest request){
		StringBuilder sb=new StringBuilder();
		try {
			Enumeration<String> names=request.getParameterNames();
			while(names.hasMoreElements()){
				String name=names.nextElement();
				String[] values=request.getParameterValues(name);
				StringBuilder value=new StringBuilder();
				if(values!=null){
					if(values.length>1){
						value.append("[");
						for (String v : values) {
							value.append(v);
							value.append(",");
						}
						value.append("]");
					}else{
						value.append(values[0]);
					}
					
				}
				sb.append(name).append("=").append(value.toString()).append("&");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public void init() throws ServletException {
		// Put your code here
	}
	

	@Deprecated
	public void doGetDel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuilder sb = new StringBuilder("{");
		try {
			String metric = request.getParameter("metric");
			String userid = request.getParameter("userid");
			String timekey = request.getParameter("timekey");
			
			sb.append("metric:").append(metric).append(",userid:").append(userid);
	
			if (timekey != null
					&& StatsManager.KEY.equals(Integer.valueOf(timekey))) {
				sb.append(",date:").append(request.getParameter("date"));
			} else if (timekey != null && StatsManager.KEY2.equals(timekey)) {
				sb.append(",timestamp:").append(request.getParameter("time"));
			}
		
			// 要记录的Metrics是什么，需要验证
			int status = StatsManager.log(metric, request);
			sb.append(",status:").append(status).append("}");
			response.getWriter().print(status);

			LOGGER.debug(sb.toString());
		} catch (Exception e) {
			sb.append("}");
			LOGGER_ERROR.error(sb.toString(), e);
		}
		
	}

}
