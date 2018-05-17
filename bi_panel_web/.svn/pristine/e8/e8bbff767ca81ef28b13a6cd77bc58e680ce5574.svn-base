<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-测试报告</title>
<link rel="stylesheet" href="/css/game/gameTestReport.css?v=${version}"/>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/games/gameTestEtl.js?v=${version}"></script>
<style type="text/css">
.noneInput{
    outline:medium;
    border:0;
    background:transparent;
    color: gray;
    min-width:50px;
}
.zhibiao{
		height:350px;
		margin-top: 20px;
		float: right;
		width: 40%;
	}
.div_count_normal_li{
   text-align: left;font-size: 14px;padding-left: 30%;
}

.div_count_bold_li{
   text-align: left;
   font-size: 16px;
   font-weight:bold;
   padding-left: 30%;
   padding-bottom: 10px;
}


.div_text_normal_li{
   text-align: left;font-size: 14px;padding-left:15%;
}
.div_text_bold_li{
   text-align: left;
   font-size: 16px;
   font-weight:bold;
   padding-left: 15%;
   padding-bottom: 15px;
}

.td_left{
 text-align: left;
}

.zhibiao_pie{
		height:350px;
		margin-bottom: 30px;
		margin-top: -10px;
	}
	.zhibiao_bar{
		width:100%;
		height:350px;
		float: left;
		margin-top: 10px;
		margin-bottom: 20px;
	}
		.zhibiao_bar2{
		width:100%;
		height:350px;
		float: left;
		margin-top: 10px;
		margin-bottom: 20px;
	}
</style>
</head>
<%
   Calendar calendar = Calendar.getInstance();
   int year = calendar.get(Calendar.YEAR);
%>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_TestReport" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
				<li>测试ETL</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      
			      <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="gameRate" type="hidden" value="${game.rate }" />
			      <input id="gameCurrency" type="hidden" value="${game.currency }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="allview" />
			   
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		
			    			 <input name="snid2" type="text" value="${snid}" readonly= "true" />
			     			 <input name="gameId2" type="text" value="${gameId}" readonly= "true" />
			      
			    		<br/>选择日期：
			    			<span id="day_span">
			    			   <span class="detail-left">
				    			   <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			   <span>至</span>
				    			   <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${endTime }" id="endTime" name="endTime" />
			    			   </span>
			    			</span>
			    		
			    			<span >
			    				<button id="query" class="btn btn-default btn-sm" style="width:100px;">测试执行</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			 
		
			
			   





</body>
</html>