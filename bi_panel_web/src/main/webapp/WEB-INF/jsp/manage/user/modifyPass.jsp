<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>账号管理-子账户</title>
<meta name="viewport" content="width=90%">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/laydate.css">
<link rel="stylesheet" href="/css/skin_laydate.css">
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/laydate.dev.js"></script>

<link rel="stylesheet" href="/css/page.css?v=${version }">
<script type="text/javascript" src="/manage/js/user/userUtil.js?v=${version }"></script>
<script type="text/javascript" src="/manage/js/modifyPass.js"></script>
<style type="text/css">
.p-ul{
 min-height:80px;
 height:100px;
 overflow: hidden;
}

.p-ul li{
 padding-bottom: 20px;
 width:100%;
 overflow: hidden;
}

.p-ul span{
 width:32%;
 text-align: left;
 float: left;
}

.p-ul input{
 width:200px;
 text-align: left;
}

</style>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<div class="data-container" style="width:90%;margin-left: 5%">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
		    	<li class="active green">账号管理</li>
			    </ol>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="usersVO" action="/panel_manage/user//modifyPass.ac" enctype="multipart/form-data">
					<p class="warn-btn">
					  <span class="pt10 ml30" style="display:inline-block;">
					  <span>基本信息</span>
					  <span id="loginNameMes" hidden="hidden" style="margin-left:25px;color:red;">${basemsg }</span>
					  </span>
					  <button id="submitButton" class="btn btn-sm warn-set">保存</button>
					  <button id="modifyPasswordBtn" class="btn btn-sm warn-set" passval="0" >修改密码</button>
				    </p>
				    
				    <form:input path="entity.id" type="hidden" value="${p_s_u_k.id }"/>
				    <input id="nameSuc" type="hidden" value="2"/>
					<ul class="p-ul mt20">
						<li>
						    <span>
							<label for="entity.email">邮箱</label>
							<input class="b-radius5 p-input" style="border:0;color: gray" name="entity.email" 
							readonly="readonly" value="	${p_s_u_k.email }" />
							</span>
							
						    <span>
							<label for="entity.loginName">登录账号</label>
							<form:input path="entity.loginName" cssClass="b-radius5 p-input" value="${p_s_u_k.loginName }"/> <font color="red">*</font>
							</span>
							
							<span>
						    <label for="entity.telepone">真实姓名</label>
							<form:input cssClass="b-radius5 p-input" path="entity.realName" value="${p_s_u_k.realName }"/> <font color="red">*</font>
							</span>
						</li>
						<li>
						   <span>
						    <label for="entity.telepone">手机</label>
							<form:input cssClass="b-radius5 p-input" path="entity.telepone" value="${p_s_u_k.telepone }"/> <font color="red">*</font>
							</span>
						</li>
					</ul>
					
					<p id="password_p" class="warn-btn" hidden="hidden">
					  <span class="pt10 ml30" style="display:inline-block;">
					  <span>修改密码</span>
					  <span style="margin-left:25px;color:blue;">密码必须包含大小写字母+数字,长度要求8-20位！</span>
					  <span id="passwordMes" hidden="hidden" style="margin-left:25px;color:red;">${passmsg }</span>
					  </span>
				    </p>
				    <ul id="password_ul" class="p-ul mt20" hidden="hidden">
				     <li style="overflow: hidden;">
				        <span>
							<label for="entity.password">原始密码</label>
							<form:password cssClass="b-radius5 p-input" path="entity.password" /> <font color="red">*</font>
						</span>
				        <span>
							<label for="newPassword">新密码</label>
							<form:password cssClass="b-radius5 p-input" path="newPassword" /> <font color="red">*</font>
						</span>
				        <span>
							<label for="repassword">确认密码密码</label>
							<input type="password" class="b-radius5 p-input" name="repassword" /> <font color="red">*</font>
						</span>
				      </li>
				    </ul>
				    <input type="hidden" id="op" name="op" value="0" />
			    	</form:form>	
			    </div>
			</div>
		</div>
	</div>
</body>
</html>