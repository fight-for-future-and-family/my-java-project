	<%@ page language="java" pageEncoding="UTF-8"%>
	<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
	<div class="sidebar">
		<ul>
		   <c:if test="${isAdmin}">
		   <li><a href="/panel_manage/toMain.ac">主页</a></li>
		   <li id="authli_user" class="active"><a href="/panel_manage/user/toUserList.ac">用户管理</a></li>
		   <li id="authli_auth"><a href="/panel_manage/gameManager/toGameList.ac">权限管理</a></li>
		   <li id="authli_game"><a href="/panel_manage/gameManager/toGameList.ac">游戏管理</a></li>
		   <li id="authli_plat"><a href="/panel_manage/adManage/toadManage.ac">平台管理</a></li>
		   <li id="authli_data"><a href="/panel_manage/syncDatas.ac">数据管理</a></li>
		   <li id="authli_etlengine_trigger"><a href="/panel_manage/etlengine_trigger_query.ac">ETL引擎</a></li>
		   <li id="authli_sys"><a href="/panel_manage/auth/toSystem.ac">系统管理</a></li>
		   <li id="authli_cus"><a href="/panel_manage/customReportModel/toCustomReportModel.ac">自定义报表</a></li>
		   <li id="authli_cusManager"><a href="/panel_manage/customReportModel/toCustomReportManager.ac">自定义报表任务管理</a></li>
		   <li id="authli_etlengineManage"><a href="/panel_manage/EtlengineManage/toEtlengineManage.ac">ETL模板管理</a></li>
		   <li id="authli_etlengineCleanup"><a href="/panel_manage/EtlengineCleanup/toEtlengineCleanupQuery.ac">ETL数据清理</a></li>
		   </c:if>
		   <li id="wanda_save"><a href="/panel_bi/wanda/toCreativeDailySave.ac">游戏流水录入</a></li>
		   <li id="wandaRate_save"><a href="/panel_bi/wanda/toCreativeRateSave.ac">游戏分成比例录入</a></li>
		   <li id="wandaDailt_qyery"><a href="/panel_bi/wanda/toWandaDailyQuery.ac">游戏数据查询</a></li>
		</ul>
		<input type="hidden" id="auth_page" name="auth_page" value="${param.auth_page }">
	</div>

<script type="text/javascript" src="/manage/js/left_auth.js?v=${version}"></script>
