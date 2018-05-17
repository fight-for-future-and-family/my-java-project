<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<title>胡莱游戏报表系统</title>
<link rel="stylesheet" href="/css/bootstrap.min.css"/>
<link rel="stylesheet" href="/css/laydate.css"/>
<link rel="stylesheet" href="/css/skin_laydate.css"/>
<link rel="stylesheet" href="/css/page.css?v=${version}"/>
<link rel="stylesheet" href="/css/main/main.css?v=${version}"  />
<style type="text/css">
	.kong{
	    width:100%;
		height:50px;
		float:left;
	}
	
	.game_con{
		margin:0 auto; 
		width:100%; 
		height:100%; 
		position: relative;
		text-align:center;
	}
	
	.game{
	    float:left;
		position:relative;
		width:165px;
		height:130px;
		margin-left:20px;
		margin-bottom:20px; 
		border: 2px solid #ebebeb;
		border-radius:15px;
		cursor:pointer;
	}
	
	.games{
	    float:left;
		position:relative;
		width:165px;
		height:130px;
		margin-left:20px;
		margin-bottom:20px; 
		border: 2px solid #ebebeb;
		border-radius:15px;
		cursor:pointer;
	}
	
	.gameTypeCls{
	    float:left;
		position:relative;
		width:165px;
		height:130px;
		margin-left:20px;
		margin-bottom:15px; 
		border: 2px solid #ebebeb;
		border-radius:15px;
		cursor:pointer;
	}
	
	.name{
      height: 80px;
	  font-size: 14px;
	  display: block;
	  line-height: 20px;
	}
	
	.name i{
	    display: inline-block;
        height: 100%;
        vertical-align: middle;
	}
	.name span{
	display: inline-block;
	vertical-align: middle;
	}
	.name div{
	position: absolute;
	left: 0;
	width: 100%;
	height: 80px;
	padding: 20px 10px;
	}
	.install{
	  text-align: left;
	  padding-left: 12%;
	  font-size: 12px;
	  font-weight:bold;
	  padding-bottom: 5px;
	  color: rgba(51, 51, 51, 0.75);
	  display: block;
	}
	
	.payment{
	  text-align: left;
	  padding-left: 12%;
	  font-size: 12px;
	  font-weight:bold;
	  color: rgba(51, 51, 51, 0.75);
	  display: block;
	}
</style>
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/js/echarts/dist/echarts.js" ></script>
<script type="text/javascript" src="/js/echarts/theme/macarons.js" ></script>
<script type="text/javascript" src="/js/main_games.js?v=${version}"></script>

</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header_main.jsp" />

	  <div class="data-container-main">
	    <div class="kong"></div>
			<c:forEach items="${gameTypes }" var="gameType">
			   <div class="gameTypeCls" gvalue="${gameType.entity.id }" gname="${gameType.entity.name }">
			      <ul>
			        <li class="name">
			        <div>
				        <span>${gameType.entity.name } 系列</span>
				        <i></i>
				    </div>
			        </li>
			        <li class="install">昨日收入：${gameType.ytdAmount }</li>
    	            <li class="payment">月收入：${gameType.monthAmount }</li>
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
         <form:form id="submitForm" name="submitForm" method="post" modelAttribute="gamesVO" action="/panel_bi/gameRealTime/toGameRealTime.ac" enctype="multipart/form-data">
  	       <form:input name="" path="entity.id" type="hidden" id="gameId"/>
  	       <input name="gameTypeId" type="hidden" id="gameTypeId"/>
  	       <input name="isMainRequest" type="hidden" value="true"/>
  	       <input name="isOutSideUser" type="hidden" value="${isOutSideUser}" id="isOutSideUser"/>
  	       <input name="isAdmin" type="hidden" value="${isAdmin}" id="isAdmin"/>
  	       <input name="isLeader" type="hidden" value="${isLeader}" id="isLeader"/>
  	       <input name="isPM" type="hidden" value="${isPM}" id="isPM"/>
	       <div class="data-container-main-fuceng">
	          
	       </div>
         </form:form>
     </div>
</div>
<div class="theme-popover-mask"></div>

<div class="gameTemp" hidden="hidden">
<div class="game" tips="" gvalue="" >
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