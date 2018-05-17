package com.hoolai.panel.web.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hoolai.bi.report.core.Constant;
import com.hoolai.bi.report.model.entity.UserOperationLog;
import com.hoolai.bi.report.service.UserLoginLogService;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.controller.AbstractPanelController;
import com.jian.tools.util.ServletUtils;

public class FrontConfigHandlerInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserLoginLogService userLoginLogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
		
		request.setAttribute("serverUrl", Constant.SERVER_URL);
		request.setAttribute("resourceUrl", Constant.RESOURCE_URL);
		request.setAttribute("siteName",Constant.SITE_NAME);
		request.setAttribute("isPublished",Constant.IS_PUBLISHED);
		request.setAttribute("version",Constant.VERSION);
		
		request.setAttribute("contextPath",request.getContextPath());
		
		String servletPath=request.getServletPath();
		HttpSession session = ServletUtils.getSession(request,true);
		Users users=null;
		if(session==null){
			return;
		}
		users=(Users)session.getAttribute(AbstractPanelController.SESSION_USER_KEY);
		if(!servletPath.contains("indexLogin.ac") && !servletPath.contains("404.ac") && 
				!servletPath.contains("logout.ac") && users!=null){
			String url = new String();
			
			Enumeration<String> enu=request.getParameterNames();
			while(enu.hasMoreElements()){
				String paraName=(String)enu.nextElement();
				if("entity.password".equals(paraName))
					continue;
				
				if("entity.loginName".equals(paraName)&&users==null){
					users = new Users();
					users.setLoginName(request.getParameter(paraName));
				}
				
				url += url.length()>3 ? "&"+paraName+"="+request.getParameter(paraName) : paraName+"="+request.getParameter(paraName);
			}
			UserOperationLog userOperationLog = new UserOperationLog();
			userOperationLog.setLoginName(users.getLoginName());
			userOperationLog.setUserId(String.valueOf(users.getId()));
			userOperationLog.setUrl(servletPath+(url.length()>3 ? "?"+url : url));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createTime = sdf.format(new Date());
			userOperationLog.setCreateTime(createTime);
			userLoginLogService.saveOperationLogs(userOperationLog);
		}
		
		if(modelAndView==null){
			return ;
		}
	}
}
