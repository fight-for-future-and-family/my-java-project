<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="viewport" content="width=device-width,height=device-height,maximum-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link href="/manage/css/login.css?v=1.0" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/manage/js/login.js?v=${version}"></script>
<title>登录</title>
</head>
<body id="content">
<div id="header">
		<div class="header-container header-img" style="width:330px">
			<h1 id="logo">
				<!--  <a href="${serverUrl}/index.jsp" title="${siteName}">
					<img src="${resourceUrl}/images/channel/lepao_logo.png"></img>
				</a>
				<span class="header-title" style="display: block;width:200px;">胡莱报表系统-帐号登录</span>
				-->
			</h1>
			<div id="navigation" class="clr">
				<!--  <a href="${serverUrl}" target="_blank">${siteName}首页</a>
				
				<span class="split">|</span>
				-->
			</div>
		</div>
		<div id="headerRepeat" class="header-container header-img-repeat">
		</div>
	</div>
	
  <div id="content-container" class="grid990 clr">
           
			<div id="login-module">
			    <div class="login-field" style="overflow: hidden;">
						<span style="float: right;font-size:12px;margin-left: 5px"><a href="/panel_manage/toInstructions.ac">常见问题</a></span>
						<span style="float: right;font-size:12px;"><a href="/panel_manage/findPass.ac">忘记密码</a></span>
						<span style="float: left;font-size:12px;" id="mes" ><font color="red">${msg }</font></span> 
						<span style="float: left;font-size:12px;" id="sysmes" >
						   <c:if test="${isMaintain }">
						      <font color="red">抱歉，系统正在维护，请稍后再试！</font>
						   </c:if>
						 </span> 
				</div>
				<form:form id="submitForm" name="submitForm" method="post" action="/panel_manage/login.ac" modelAttribute="usersVO">
				   <div class="login-field">
					    <div class="login-field-name">登录账号：</div>
						<input type="text" class="login-field-value" name="entity.loginName" autocomplete="off"/>
				    </div>
					<div class="login-field">
						<div class="login-field-name">密码：</div>
							<input type="password" class="login-field-value" name="entity.password" autocomplete="off"/>
					</div>
					<c:if test="${loginErrorTimes >= 2}">
					<div class="login-field">
						<div class="login-field-name">验证码：</div>
						<input name="veriCode" type="text" id="veriCode" style="border: solid 1px #C8C8C8;
                                width: 45%; height: 36px; line-height: 36px; text-indent: 5px;" placeholder="请输入验证码" />
                         <img style="cursor: pointer; width: 52%; height: 36px; vertical-align: middle;font-size:12px" alt="图片看不清？点击重新获取"
                                id="yzm" src="/panel_manage/random/codeGenerate.ac"/>
					</div>
					</c:if>
					<div class="login-submit">
						<input type="button" name="submit-btn" tabindex="4" class="fm-button fm-submit" value="图表版登录" id="fm-login-submit"/>
						<input type="button" name="submit-btn" tabindex="4" class="fm-button fm-submit" value="纯数据版登录" id="wfm-login-submit"/>
					    <input type="hidden" id="loginType" name="loginType" value="chart">
					</div>
				</form:form>
			</div>
			
 </div>
		
	    <div class="aysw-footer">
		  <div class="aysw-inner">
		  </div>
	    </div>
</body>
</html>