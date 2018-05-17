package etl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.hoolai.bi.report.core.Constant;
import com.hoolai.bi.report.etl.ETLEngineGameInfo.Type;
import com.hoolai.bi.report.job.etl.DailyTriggerAllGamesETLEngineJob;
import com.hoolai.bi.report.model.entity.Games;

public class TestTriggerEtl{
	

	@Test
	public void testOneDay() throws Exception {
		String statDay="2015-08-28";
		List<Games> filterGameList=new ArrayList<Games>();
		Games game=new Games();
		game.setSnid("15");
		game.setGameid("145");
		game.setTerminalType(1L);
		game.setSystemType(0L);
		filterGameList.add(game);
		List<Map<String,String>> list = DailyTriggerAllGamesETLEngineJob.triggerGameEtlEngine(Type.ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/runEtls",filterGameList, statDay,statDay,0,null);
		for(Map<String,String> map:list){
			Set<String> keySet = map.keySet();
			for(String key:keySet){
				System.out.println(key+": "+map.get(key));
			}
		}
	}
	
	@Test
	public void testOneDay2() throws Exception {
		String statDay="2015-06-03";
		List<Games> filterGameList=new ArrayList<Games>();
		Games game=new Games();
		game.setSnid("1");
		game.setGameid("71");
		filterGameList.add(game);
		List<Map<String,String>> list = DailyTriggerAllGamesETLEngineJob.triggerGameEtlEngine(Type.QUASI_ETL_ENGINE_RUN,Constant.ELT_ENGINE_TRIGGER_URL+"/runEtls",filterGameList, statDay,statDay,1,null);
		for(Map<String,String> map:list){
			Set<String> keySet = map.keySet();
			for(String key:keySet){
				System.out.println(key+": "+map.get(key));
			}
		}
	}
}
