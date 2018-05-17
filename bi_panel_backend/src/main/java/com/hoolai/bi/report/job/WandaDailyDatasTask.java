package com.hoolai.bi.report.job;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoolai.bi.report.model.Types.CurrencyConvertedToRMBRate;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.model.entity.GamesCreative;
import com.hoolai.bi.report.model.entity.WandaDailyReport;
import com.hoolai.bi.report.service.GamesCreativeService;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.report.service.WandaDailyReportService;

@Component("wandaDailyDatasTask")
public class WandaDailyDatasTask {
	
	@Autowired
	private GamesService gamesService;
	
	@Autowired
	private GamesCreativeService gamesCreativeService;
	
	@Autowired
	private WandaDailyReportService wandaDailyReportService;
	
	public void job1() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		Calendar cal = Calendar.getInstance();//获取当前日期
		cal.setTime(sdf.parse(dateStr));
		cal.add(Calendar.DAY_OF_MONTH, -2);
		dateStr = sdf.format(cal.getTime());
		Map<String, String> dateMap = new HashMap<String, String>();
		dateMap.put("beginDate", dateStr);
		dateMap.put("endDate", dateStr);
		List resultLists = wandaDailyReportService.selectFirstTen(dateMap);
		if(resultLists.size()==0){
			List<WandaDailyReport> resultList = select(dateStr);
			if(resultList.size()>0){
				wandaDailyReportService.saveWandaDailysReport(resultList);
			}
		}
	}
	
	public List<WandaDailyReport> select(String dateStr){
		List<WandaDailyReport> resultList = new ArrayList<WandaDailyReport>();
		List<GamesCreative> gameNameList = gamesCreativeService.selectGameNameList();
		for (GamesCreative gamesName : gameNameList) {
			List<GamesCreative> gamesList = gamesCreativeService.selectGames(gamesName);
			for (GamesCreative gamesCreative : gamesList) {
				gamesCreative.setDs(dateStr);
				List<WandaDailyReport> wndaDailyList = new ArrayList<WandaDailyReport>();
				if(!"官网".equals(gamesCreative.getCreative())){
					wndaDailyList = wandaDailyReportService.selectGameCreativeDailyBysource(gamesCreative);
				}else{
					wndaDailyList = wandaDailyReportService.selectGwCreativeDailyBysource(gamesCreative);
				}	
				if(wndaDailyList.size()>0){
					Games game = gamesService.getGames(gamesCreative.getSnid(), gamesCreative.getGameid());
					String currency = game.getCurrency();
					if(!"元".equals(currency)){
						System.out.println();
					}
					Double toRMBRate = CurrencyConvertedToRMBRate.convert(currency).getValue();
					resultList = mergeDaily(resultList, wndaDailyList, toRMBRate);
				}
			}
		}
		return resultList;
	}
	
	public List<WandaDailyReport> mergeDaily(List<WandaDailyReport> gameList, 
			List<WandaDailyReport> wndaDailyList,
			Double toRMBRate){
		for (WandaDailyReport wandaDailyReport : wndaDailyList) {
			BigDecimal amount = wandaDailyReport.getPaymentAmount();
			amount = new BigDecimal(amount.doubleValue()*toRMBRate);
			wandaDailyReport.setPaymentAmount(amount);
			gameList.add(wandaDailyReport);
		}
		return gameList;
	}
	
}
