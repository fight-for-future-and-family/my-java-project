package com.hoolai.bi.tracking.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.tracking.log.TrackStandardAccess;
import com.hoolai.bi.tracking.log.TrackingProcessor;
import com.hoolai.bi.tracking.log.TrackingStatus;
import com.hoolai.bi.util.IPUtils;
import com.hoolai.util.JSONUtils;

@Controller
public class TrackingController {
	
    private static final Logger LOGGER=Logger.getLogger("tracking");
	
	private static final Logger LOGGER_ERROR=Logger.getLogger("trackingError");
	
	@RequestMapping(value = {"/"}, method = { RequestMethod.GET,RequestMethod.POST })	
	@ResponseBody
	public String base(HttpServletRequest request,HttpSession session,Model model)throws Exception {
		return "{status:"+TrackingStatus.OTHER+",msg:request error}";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = {"/tracking/"}, method = { RequestMethod.GET,RequestMethod.POST })	
	public void toAdTracking(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model)throws Exception {
		
		String snid = request.getParameter("snid");
		String gameid = request.getParameter("gameid");
		String clientid = request.getParameter("clientid");
		if(clientid==null||clientid.isEmpty()){
			clientid = "0";
		}
		String ds = request.getParameter("ds");
		if(ds==null||ds.isEmpty()){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ds = sdf.format(date);
		}
		String metric = request.getParameter("metric");
		String jsonString = request.getParameter("jsonString");
		String ip = IPUtils.getIpAddr(request);
		TrackStandardAccess access = new TrackStandardAccess(snid,gameid,clientid,ds,metric,jsonString,ip);
		TrackingProcessor processor = new TrackingProcessor(access);
		processor.log();
		Map<String,Object> ret = processor.getRet();
		
		try {
			// 要记录的Metrics是什么，需要验证
			TrackingStatus status = (TrackingStatus)(ret.get("status"));
			response.getWriter().print("status:"+status+",cause:"+ret.get("cause"));

			if(status.ordinal() == TrackingStatus.SECCESS.ordinal()){
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug(JSONUtils.toJSON(ret));
				}
			}else{
				LOGGER_ERROR.error(JSONUtils.toJSON(ret));
			}
		} catch (Exception e) {
			ret.put("cause", e.getMessage());
			LOGGER_ERROR.error(JSONUtils.toJSON(ret));
			e.printStackTrace();
		}
	}
}
