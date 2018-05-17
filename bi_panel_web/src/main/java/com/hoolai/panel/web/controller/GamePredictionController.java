package com.hoolai.panel.web.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoolai.bi.report.model.entity.GamePrediction;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.DailyReportService;
import com.hoolai.bi.report.util.DateUtils;
import com.hoolai.bi.report.vo.GamePredictionVO;
import com.hoolai.manage.model.Users;
import com.hoolai.panel.web.download.DownLoadPredictionProcessor;

@Controller
@RequestMapping("/panel_bi/prediction")
public class GamePredictionController extends AbstractPanelController {

    @Autowired
    private DailyReportService dailyReportService;

    @RequestMapping(value = { "/downloadData.ac" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String downData(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    	Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
    	Games games = this.getSessionGames(request);

        String dataTableValue = request.getParameter("dataTableValue");
        if(!StringUtils.isEmpty(dataTableValue)){
        	DownLoadPredictionProcessor processor = new DownLoadPredictionProcessor(games,dataTableValue,response);
        	processor.write();
        }

        return null;
    }

    @RequestMapping(value = { "/getPredictionData.ac" }, method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getPredictionData(HttpServletRequest request, HttpSession session) throws Exception {
    	Users users = getSessionUsers(request);
		if(isOutSideUser(request)){
			throw new Exception("用户："+users.getId()+"（"+users.getLoginName()+"）权限越界");
		}
    	Map<String, Object> ret = new HashMap<String, Object>();
		Games games = this.getSessionGames(request);
		
		String isCurrMonth = request.getParameter("isCurrMonth");
		
		GamePrediction gamePrediction = new GamePrediction(games.getSnid(),games.getGameid());
		GamePredictionVO gamePredictionVO = new GamePredictionVO(gamePrediction);
		
		if("1".equals(isCurrMonth)){
			// 暂时不需要做什么操作
		}else{
			String monthBeginDate = DateUtils.getCurrMonthBeginDate();
			String monthendDate = DateUtils.getCurrMonthEndDate();

			Calendar calendar = Calendar.getInstance();
			String currDate = DateUtils.getDateStr(calendar);

			calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) - 1);
			String avgEndDate = DateUtils.getDateStr(calendar);
			calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) - 6);
			String avgBeginDate = DateUtils.getDateStr(calendar);

			gamePredictionVO.setAvgDate(avgBeginDate, avgEndDate);
			GamePrediction avgGamePrediction = dailyReportService.avg4Predict(gamePredictionVO);
			GamePrediction avgRetentionGamePrediction = dailyReportService.avgOldUserLossRate4Predict(gamePredictionVO);

			gamePredictionVO.setSumDate(monthBeginDate, monthendDate);
			GamePrediction sumGamePrediction = dailyReportService.sumPayAmount4Predict(gamePredictionVO);

			gamePrediction.setup(avgGamePrediction, avgRetentionGamePrediction,sumGamePrediction);
			gamePredictionVO.setCurrDate(currDate);
		}

		ret.put("gamePrediction", gamePredictionVO);
		ret.put("game", games);
		session.setAttribute("game", games);
		return ret;
    }

    
}
