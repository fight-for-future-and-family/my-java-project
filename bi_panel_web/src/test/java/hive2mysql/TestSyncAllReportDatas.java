package hive2mysql;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hoolai.bi.hive2mysql.datas.ConvertMapperDatas;
import com.hoolai.bi.hive2mysql.datas.ConvertMapperDatas.Mapper;
import com.hoolai.bi.hive2mysql.sync.SyncAllReportDatas;
import com.hoolai.bi.hive2mysql.sync.SyncConditions;
import com.hoolai.bi.hive2mysql.sync.SyncConditions.GameBiId;
import com.hoolai.bi.report.etl.ETLEngineGameInfo.Type;
import com.hoolai.bi.report.model.entity.Games;
import com.hoolai.bi.report.service.GamesService;
import com.hoolai.bi.test.BaseTest;

public class TestSyncAllReportDatas extends BaseTest {
	
	@Autowired
	private SyncAllReportDatas syncAllReportDatas;
	
	@Autowired
	private GamesService gamesService;
	
	private ConvertMapperDatas convertMapperDatas=ConvertMapperDatas.getInstance();

	@Test
	public void testOneDay() throws Exception {
		String statMonth="2015-01";
		String statDay="2015-01-21";
		String statWeek="2015-01-19_2015-01-25";
		List<GameBiId> gameBiIds=new ArrayList<GameBiId>();
		List<Games> gameList=this.gamesService.selectAllGames();
		for (Games games : gameList) {
			gameBiIds.add(new GameBiId(games.getSnid(), games.getGameid()));
		}
		List<Mapper> mapperList=convertMapperDatas.getTypeMapperList(Type.QUASI_ETL_ENGINE_RUN.getDisplayName());
		SyncConditions syncConditions=new SyncConditions(gameBiIds, statMonth, statWeek, statDay);
		syncAllReportDatas.sync(syncConditions,mapperList);
	}
	
	@Test
	public void testOneMonth() throws Exception {
		Date now=new Date();
		for(int i=365;i>40;i--){
			Date yesterday =DateUtils.addDays(now, -i);
			String statMonth=DateFormatUtils.format(yesterday, "yyyy-MM");
			String statDay=DateFormatUtils.format(yesterday, "yyyy-MM-dd");
			String statWeek=this.getWeekRange(yesterday);
			List<GameBiId> gameBiIds=new ArrayList<GameBiId>();
			List<Games> gameList=this.gamesService.selectAllGames();
			for (Games games : gameList) {
				gameBiIds.add(new GameBiId(games.getSnid(), games.getGameid()));
			}
			SyncConditions syncConditions=new SyncConditions(gameBiIds, statMonth, statWeek, statDay);
			syncConditions.setStatMonth(statMonth);
			syncConditions.setStatDay(statDay);
			syncConditions.setStatWeek(statWeek);
			List<Mapper> mapperList=convertMapperDatas.getTypeMapperList(Type.QUASI_ETL_ENGINE_RUN.getDisplayName());
			syncAllReportDatas.sync(syncConditions,mapperList);
		}
	}
	
	private String getWeekRange(Date date){
		Iterator<Calendar> it=DateUtils.iterator(date,DateUtils.RANGE_WEEK_MONDAY);
		List<String> days=new ArrayList<String>();
		while (it.hasNext()) {
			Calendar type = (Calendar) it.next();
			days.add(DateFormatUtils.format(type, "yyyy-MM-dd"));
		}
		return days.get(0)+"_"+days.get(days.size()-1);
	}

}
