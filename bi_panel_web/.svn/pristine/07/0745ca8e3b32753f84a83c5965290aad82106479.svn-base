<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
	<div class="navbar" >
	    <div id="img" class="header-img" style="float: left;">
	    
	    </div>
	    <div id="img-repeat" class="header-img-repeat" style="padding:20px 0px;float: left;">
		    <div class="header-inner">
				<span class="nologin">
				
					<a href="/panel_manage/logout.ac">注销</a>
					<span class="separator"></span>
					
					<span style="color: #fff;">
					    <c:if test="${p_s_u_k!=null }">${p_s_u_k.loginName}</c:if>
					</span>
					
					<span class="separator"></span>
					
					<a href="/panel_manage/user/modifyPass.ac">账号管理</a>
					<c:if test="${isAdmin or isProduct}">
						<span class="separator"></span>
						<a href="/panel_manage/user/toUserList.ac">权限管理</a>
					</c:if>
					<c:if test="${isWanDa }">
						<span class="separator"></span>
						<a href="/panel_manage/toMain.ac">首页</a>
					</c:if>
					<c:if test="${!isWanDa }">
						<c:if test="${!isOutSideUser }">
							<span class="separator"></span>
							<a href="/panel_manage/toMain.ac">图表版首页</a>
							<span class="separator"></span>
							<a href="/panel_manage/toMain.ac?type=1">数据版首页</a>
						</c:if>
					</c:if>
				</span>
					
					
			</div>
	    </div>
	</div>

<script type="text/javascript">
jQuery(function($) {
	$(document).ready(function() {
		var header_w = $(".navbar").width();
		$("#img-repeat").css('width',(header_w-410)+'px');
	});
	 
});
</script>
