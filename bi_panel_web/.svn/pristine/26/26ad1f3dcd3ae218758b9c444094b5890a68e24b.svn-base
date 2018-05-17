package monitor;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.hoolai.bi.test.BaseTest;
import com.jian.tools.util.HttpClientUtils;

public class TestAdminMonitorOpSubmit extends BaseTest {
	
	@Test
	public void test() throws Exception {
		Map<String, String> params = new HashMap<String,String>();
		params.put("snid", "2"); //平台ID
		params.put("gameid", "20"); // 游戏ID
		params.put("clientid", "2002"); // 服务器ID
		params.put("opCode", "addEquip"); // 操作代码
		params.put("opUserName", "anna"); // 操作人名称
		params.put("itemId", "2004"); // 道具ID
		params.put("itemName", "黑铠甲"); // 道具名称
		params.put("itemNum", "2"); // 道具数量
		params.put("beOperatedUserName", "henay"); // 被操作人名称或者玩家ID
		params.put("beOperatedUserRole", "玩家"); // 被操作人角色
		params.put("opDate", "2015-12-12"); // 操作日期 格式yyyy-MM-dd
		params.put("opTime", "20:10:14"); // 操作时间 格式 HH:mm:ss
		
		String jsonStr = HttpClientUtils.executePostRequest("http://127.0.0.1:8085/panel_bi/opMonitor/adminOpMonitor.ac", params, "UTF-8");
		System.out.println(jsonStr);
	}

}
