<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<title>系统管理-维护切换</title>
<link rel="stylesheet" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" href="/css/page.css?v=${version}">

<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/page.js"></script>
<style type="text/css">
.msgcss{

}
</style>
<script type="text/javascript">
$(function(){
	
	if($("#isMaintain_sys").val() == 'true'){
		$("#isMaintain_1").attr("checked","checked");
	}else{
		$("#isMaintain_2").attr("checked","checked");
	}
	
});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="authli_sys" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
			    
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
		    	<li class="active green">系统管理</li>
			    </ol>
			    <div class="op_bar" style="overflow: hidden;">
			       <div class="msgcss">${msg }</div>
			    </div>
			    <form id="submitForm" name="submitForm" method="post" action="/panel_manage/auth/changeToMaintain.ac" enctype="multipart/form-data">
			    <input type="hidden" id="isMaintain_sys" value="${isMaintain }">
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li" style="height:45px;overflow: hidden;">
			    		    <span style="padding: 15px 15px; float: left">
			                	<input type="radio" id="isMaintain_1" name="isMaintain" value="true"> 开启
			    				<input type="radio" id="isMaintain_2" name="isMaintain" value="false"> 关闭   		    
			    		    </span>
			    			
			    			<span class="detail-right">
			    				<button id="query" class="btn btn-default btn-sm" style="width:100px;">设置</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>