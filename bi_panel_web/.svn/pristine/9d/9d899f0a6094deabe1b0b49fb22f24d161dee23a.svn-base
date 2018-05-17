<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" href="/css/page.css?v=${version}"/>
<link rel="stylesheet" href="/css/main/main.css?v=${version}"  />
<link rel="stylesheet" href="/css/wap/wap.css?v=${version}"/>
<style type="text/css">
	.games{
	    float: left;
		position: relative;
		width: 139px;
		height: 110px;
		margin-left: 5px;
		margin-bottom: 8px;
		border: 2px solid #ebebeb;
		border-radius: 15px;
		cursor: pointer;
	}
</style>

<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<title>胡莱游戏报表系统</title>
<script type="text/javascript" src="/js/main_games.js?v=${version}"></script>
</head>
<body>
   <jsp:include page="/WEB-INF/jsp/wap/common/header.jsp" />
 <div class="data-container-main" style="min-height:700px;">
	    <div class="kong"></div>
			<c:forEach items="${gameTypes }" var="gameType">
			   <div class="gameTypeCls" gvalue="${gameType.entity.id }" gname="${gameType.entity.name }">
			      <ul>
			        <li class="nameCls">
			        <div>
				        <span>${gameType.entity.name } 系列</span>
				        <i></i>
				    </div>
					</li>
			        <li class="installCls">昨日收入：${gameType.ytdAmount }</li>
    	            <li class="paymentCls">月收入：${gameType.monthAmount }</li>
			      </ul>
			   </div>
			</c:forEach>
	  </div>
	  
  <div class="theme-popover">
     <div class="theme-poptit">
          <a href="javascript:;" title="关闭" class="close"><font size="5" >×</font></a>
          <h5 id="gameTypeName">登录是一种态度</h5>
     </div>
     <div class="theme-popbod dform">
         <form:form id="submitForm" name="submitForm" method="post" modelAttribute="gamesVO" action="/panel_bi/wap/gameView/toWapGameDatasView.ac" enctype="multipart/form-data">
  	       <form:input name="" path="entity.id" type="hidden" id="gameId"/>
  	       <input name="gameTypeId" type="hidden" id="gameTypeId"/>
  	       <input name="isOutSideUser" type="hidden" value="${isOutSideUser}" id="isOutSideUser"/>
  	       <input name="isAdmin" type="hidden" value="${isAdmin}" id="isAdmin"/>
  	       <input name="isLeader" type="hidden" value="${isLeader}" id="isLeader"/>
  	       <input name="isPM" type="hidden" value="${isPM}" id="isPM"/>
  	       <input name="isMainRequest" type="hidden" value="true"/>
	       <div class="data-container-main-fuceng">
	          
	       </div>
         </form:form>
     </div>
</div>
<div class="theme-popover-mask"></div>

<div class="gameTemp" hidden="hidden">
<div id="theme-popover-div" class="game" tips="" gvalue="" >
    <ul>
    	<li class="name">
    	   <div>
			 <span></span>
		     <i></i>
		   </div>
    	</li>
    	<li class="install"></li>
    	<li class="payment"></li>
   	</ul>
</div>

</div>
</body>
</html>