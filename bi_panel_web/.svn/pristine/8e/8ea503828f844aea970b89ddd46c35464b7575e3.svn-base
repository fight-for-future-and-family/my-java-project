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
<title>常见问题</title>
<style type="text/css">
.warn-btn{
	height: 35px;
	text-align: left;
	border-bottom: 1px solid #ebebeb;
}
.ml30{
	margin-left: 30px !important;
}
.pt10{
	padding-top: 10px;
}
.quesShow{
  top:75%;
left:50%;
margin-left: -47%;
  width:85%;
  min-height:80px;
  border-radius: 10px;
  padding: 20px 30px;
  position:absolute;
}

.container{
line-height:200%;
margin-top: 20px;
float: left;
border: 1px solid #deb887;
padding: 10px 10px;
height:200px;
position:absolute;
width: 80%;
top:35%;
left:50%;
margin-left: -42%;
text-align: center;
}

.container li{
margin-top: 20px;
}
.container_input{
  height:25px;
  width:200px;
  border: 1px solid #ebebeb;
  border-radius: 5px;
  padding: 0 5px;
}
.findPass-submit{
  margin-top: 20px;
  height:55px;
  margin-left: 10%;
}
.fm-button {
  background: repeat scroll 0 0 #e66e1e;
  border: 0 none;
  border-radius: 0;
  color: #fff;
  cursor: pointer;
  font-size: 12px;
  font-weight: 400;
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
  text-align: center;
  text-decoration: none;
  text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.2);
  vertical-align: middle;
  width:150px;
  left:50%;
margin-left: -70px;
}
</style>
<script type="text/javascript">
$(function(){
	
	var header_w = $("#header").width();
	$("#headerRepeat").css('width',header_w-330+'px');
	
	$("#submit-btn").click(function(){
		var loginName = $("input[name='entity.loginName']").val();
		var email = $("input[name='entity.email']").val();
		if(loginName == undefined || loginName == ''){
			$("#show").show();
			$("#show").text('用户名不能为空！');
		}else if(email == undefined || email == ''){
			$("#show").show();
			$("#show").text('邮箱不能为空！');
		}else{
			var submitForm=$("#submitForm");
			if(submitForm.length>0){
				submitForm.submit();
			} 
		}
	});
	
	$(".fanhui").click(function(){
			var loginForm=$("#loginForm");
			if(loginForm.length>0){
				loginForm.submit();
			} 
	});
});
</script>
</head>
<body id="content">
<div id="header">
		<div class="header-container header-img" style="width:330px">
			<h1 id="logo">
			</h1>
			<div id="navigation" class="clr">
			</div>
		</div>
		<div id="headerRepeat" class="header-container header-img-repeat">
             <span style="display:inline-block;float: right;padding-top: 5%;padding-right:15px">
                 <a class="fanhui" href="/panel_manage/indexLogin.ac">返回登录</a>
             </span>
		
		</div>
	</div>
  <div id="content-container" class="grid990 clr" style="overflow: hidden;text-align: center">
      <p class="warn-btn">
		 <span class="pt10 ml30" style="display:inline-block;"><span>找回密码</span></span>
		 <span class="pt10" style="display:inline-block;float: right;"><span>问题反映邮箱：BI@hoolai.com</span></span>
	  </p>
           
			<div class="container">
				<form:form id="submitForm" name="submitForm" method="post" action="/panel_manage/findPass.ac" modelAttribute="usersVO">
				   <ul>
			        <li style="margin-top: 20px;">
			           <label for="entity.loginName">登录账号：</label>
			           <form:input class="container_input" path="entity.loginName"/>*
			        </li>
			        <li>
			           <label for="entity.email">绑定邮箱：</label>
			           <form:input class="container_input" path="entity.email"/>*
			        </li>
			        <li>
			           <label for="veriCode">验证码：</label>
						<input name="veriCode" type="text" id="veriCode" class="container_input" style="width:100px" placeholder="请输入验证码" />*
                         <img style="cursor: pointer; width: 8%; height: 30px; vertical-align: middle;font-size:12px" alt="图片看不清？点击重新获取"
                                id="yzm" src="/panel_manage/random/codeGenerate.ac"/>
			        </li>
			      </ul>
					
					<div class="findPass-submit" >
					   <input type="button" name="submit-btn" 
					   class="fm-button fm-submit" value="找回密码" id="submit-btn"/>
					</div>
				</form:form>
			</div>
    <div id="show" class="quesShow" style="float: left">
                          注意：登录账号必须和申请账号时提供的邮箱一致才能找回密码！<br><br><br>
        <font color="red">
           <c:if test="${(mess != null && mess != '')}">
              ${mess }   >>   
                 <a href="/panel_manage/indexLogin.ac">去登录</a>
              
           </c:if>
        </font>
        
    </div>
</div>
</body>
</html>