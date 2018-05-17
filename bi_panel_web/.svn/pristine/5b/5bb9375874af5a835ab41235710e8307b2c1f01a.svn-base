<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>下载</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
#data {
  min-height: 560px;
}
#data li{
 text-align: left;
 font-size: 14px;
 margin-top: 10px;
}
.ajax_loading{
    background:#fff;
    height:60px;
    width:100%;
    text-align:center;
    line-height:60px;
    font-size:16px;
    display:none;
    bottom:0px
  }
</style>
<script type="text/javascript" src="/js/download/dailyReportDownLoad.js?v=${version}"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_download" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>日报下载</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			    <ul class="detail-nav">
			       <li class="de-first active" id="dailyDown"><a href="#"></a>日报下载</li>
			    </ul>
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/tool/download.ac" enctype="multipart/form-data">
			      <input name="id" type="hidden" value="${game.id }" />
			      <input name="gamesId" type="hidden" value="${game.id }" />
			      <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
			      <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" type="hidden" value="overview" />
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    			<span class="detail-left" style="width:70%">
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:105px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			<span>至</span>
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:105px;" value="${endTime }" id="endTime" name="endTime" />
			    			</span>
			    			<span class="detail-right" style="width:20%">
			    				<button id="query" class="btn btn-default btn-sm" style="width:80px">下载</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			    </form:form>
			</div>
			
			<div id="data">
		       <ul>
		         <li>下载注意事项：</li>
		         <li style="padding-left: 25px;">  1.下载文件默认为office07版Excel。</li>
		         <li style="padding-left: 25px;">  2.请不要频繁执行下载操作。</li>
		         <li style="padding-left: 25px;">  3.下载报表，时间跨度一个月为宜。</li>
		       </ul>
		       <div class='ajax_loading'>报表下载中...<img src="/images/loading.gif"></img></div>
			</div>
			
		</div>
	</div>
</body>
</html>