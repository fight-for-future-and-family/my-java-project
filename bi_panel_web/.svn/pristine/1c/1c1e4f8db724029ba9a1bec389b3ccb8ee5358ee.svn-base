<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
	<div class="navbar">
	<div id="img" class="header-img" style="float: left;">
	    
	</div>
	<div id="img-repeat" class="header-img-repeat" style="float: left;">  
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
				<c:if test="${!isOutSideUser}">
				 <span class="separator"></span>
				 <a href="/panel_manage/toMain.ac">图表版首页</a>
				 <span class="separator"></span>
				 <a href="/panel_manage/toMain.ac?type=1">数据版首页</a>
				 </c:if>
				</c:if>
			</span>
		</div>
		<c:if test="${!isWanDa }">
		<div style="float: right; width: 100%;line-height: 30px;padding-right: 20px;">
		   <div style="float: right; width: 27%;text-align: left;font-size: 12px;color: #581D25;">
		   	<div class="notice" id="notice" onmouseover="clearInterval(timer)" onmouseout="timer=setInterval(mar,30)">
   				<div class="scroll" id="scroll">
     				<div class="noticeValue" id="noticeValue_1">
     				<c:if test="${!isOutSideUser}">
        				<ul>
          					<li>
          					   <span>
          					       <img src="/images/notice.png" style="width:2%;height:2%">
              	      			新功能上线通知：可直接刷新执行任务获取新数据啦，<a href="/panel_bi/customTask/toTask.ac">刷新任务 </a>
          					   </span>
          					   <span>
          					       <img src="/images/notice.png" style="width:2%;height:2%">
              	      			新功能上线通知：<a href="/panel_bi/equip/toEuipment.ac">设备分析 </a>
          					   </span>
              	      		</li>
        				</ul>
        				</c:if>
        				<c:if test="${isOutSideUser}">
        				<ul>
          					<li>
          					   <span>
								   	 
          					   </span>
              	      		</li>
        				</ul>
        				</c:if>
 					</div>
  					<div id="noticeValue_2" class="noticeValue"></div>
			 	</div>
			</div>
		   </div>
		</div>
		</c:if>
		</div>
	</div>
	
<script type="text/javascript">
$(function(){
	$(document).ready(function() {
		var header_w = $(".navbar").width();
		$("#img-repeat").css('width',(header_w-410)+'px');
	});
	 
});
</script>
<script type="text/javascript" src="/manage/js/header.js?v=${version}"></script>




	
	