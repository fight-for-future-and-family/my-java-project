package com.hoolai.bi.tracking.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hoolai.bi.tracking.log.StatsManager;

public class AdminServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String on = request.getParameter("tracking_status");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>Tracking Status</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("set tracking_status to 0, indicate turn off the tracking, set 1 to turn on<br/>");
		if(on != null && on.equals("0")) {
			StatsManager.TRACKINGON = "0";
			out.println("Tracking status: <font color='ff0000'>off</font>");			
		}else if(on != null && on.equals("1")) {
			StatsManager.TRACKINGON = "1";
			out.println("Tracking status: <font color='00ff00'>on</font>");
		}
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}