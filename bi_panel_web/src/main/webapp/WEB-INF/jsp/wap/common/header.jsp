<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
	<div class="navbar">
		<div style="margin-top: 5px;">
			<span id="header_game_name" style="font-weight: bold; margin-left: 10px; font-size: 16px">
				<c:if test="${game!=null }">${game.name}</c:if>
			</span>
			<input id="timeZone" type="hidden" value="${game.timeZone }" />
			<span id="timeZoneName" style="font-weight: bold; margin-left: 10px; font-size: 12px;float:right;">
				<c:choose>
				 <c:when test="${game == null || game.timeZone == null || game.timeZone == ''}">
				    (运营时区：UTC+8)
				 </c:when>
				 <c:otherwise>
				     (运营时区：${game.timeZone})
				 </c:otherwise>
				</c:choose>
				
			</span>
		</div>

	    <div class="header-inner">
		   
			<span class="nologin">
			
				<a href="/panel_manage/logout.ac">注销</a>
				<span class="separator"></span>
				<span class="light-green">
				    <c:if test="${p_s_u_k!=null }">${p_s_u_k.loginName}</c:if>
				</span>
				
				
				<span class="separator"></span>
				<a href="/panel_manage/toMain.ac?type=1">数据版首页</a>
				
				<span class="separator"></span>
				<a href="/panel_manage/toMain.ac">图表版首页</a>
				
			</span>
		</div>
	</div>
<script type="text/javascript">
$(function(){
	
	$(document).ready(function() {
	   // $.timeZone.showTimeZone();
	});
});

</script>