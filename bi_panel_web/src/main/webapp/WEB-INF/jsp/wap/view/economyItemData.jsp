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
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<title>${game.name }-经济分析</title>
<link rel="stylesheet" href="/css/wap/wap.css?v=${version}"/>
<script type="text/javascript" src="/wap/js/dataTables.js?v=${version}"></script>
<script type="text/javascript" src="/wap/js/economyItemData.js?v=${version}"></script>
<style type="text/css">

.dataTables_wrapper .dataTables_filter {
	  margin-left:10px;
      z-index:1;
      top: -35px;
      position:absolute;
     }

     #caption {
	font-style: normal;
	text-align: left;
	top: 5px;
	margin-right: 20px;
	float: right;
}

#caption span.ui-selected {
	background: #56C887;
	color: white;
}

#caption span.last {
	border-right: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}

#caption span {
	float: left;
	margin: 5px auto;
	width: 75px;
	text-align: center;
	font-size: 14px;
	line-height: 26px;
	border-left: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}
     #caption1 {
        font-style: normal;
        text-align: left;
        top: 5px;
         margin-right: 8px;
        float: right;
	}
</style>
</head>
<body>
	<div class="lp-container">
	<jsp:include page="/WEB-INF/jsp/wap/common/header.jsp" />
		<div class="data-container-main">
			<div class="detail">
			    <ul class="detail-nav">
			       <li class="de-first active" id="realTime"><a></a>实时</li>
			       <li id="daily" ><a ></a>基本运营数据</li>
			       <li id="equipDau"><a></a>设备数据</li>
		       		<li id="taskList" ><a ></a>自定义报表</li>
		       	
			    </ul>
			    
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/economy/downloadData.ac" enctype="multipart/form-data">
					<input name="gamesId" type="hidden" value="${game.id }" />
					<%-- <input name="snid" type="hidden" value="${game.snid }" />
				      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
				      <input name="snid" type="hidden" value="${snid }" />
					  <input name="gameId" type="hidden" value="${gameId }" />
					<input id="gameRate" type="hidden" value="${game.rate }" />
					<input id="gameCurrency" type="hidden" value="${game.currency }" />
					<input id="timeZone" type="hidden" value="${game.timeZone }" />
					<input id="view" name="view" type="hidden" value="economyItem" />
					<input id="queryType" name="queryType" type="hidden" value="amount" />
					<div class="detail-info">
				    	<ul class="detail-ul">
				    		<li class="detail-li" style="overflow: hidden;">
				    		   <div style="padding-left:5px;padding-top:10px;float: left;">
				    		      <select id="indicators" name="indicators" style="width:100px;">
				    		         <option value="all" selected="selected">总览</option>
				    		         <option value="client">分服</option>
				    		      </select>
				    			</div>
				    		   <div style="padding-left:5px;padding-top:10px;float: left;">
				    		      <select id="groupType" name="groupType" style="width:100px;">
				    		         <option value="first" selected="selected">一级分类</option>
				    		         <option value="second">二级分类</option>
				    		         <option value="third">三级分类</option>
				    		         <option value="fourth">四级分类</option>
				    		      </select>
				    			</div>
				    			<div id="s_c_span" style="padding-left:5px;padding-top:10px;float: left;"></div>
				    		</li>
				    		<li class="detail-li">
				    			<span class="detail-left" style="width:80%">
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${ec_beginTime }" id="ec_beginTime" name="ec_beginTime"/>
					    			<span>至</span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${ec_endTime }" id="ec_endTime" name="ec_endTime" />
				    			</span>
				    			<span class="detail-right">
				    				<button id="query" type="button" class="btn btn-default btn-sm">查询</button>
				    			</span>
				    		</li>
				    	</ul>
				    </div>
				    
				    <div id="caption">
						    <span id="t_amount" class="ui-selected" val="amount" >消费元宝</span>
						    <span id="t_users" val="users">消费人次</span>
						    <span id="t_times" val="times">消费次数</span>
						    <span id="t_itemNum" val="item_num" class="last">消费道具数</span>
                       </div>
			   		 <div id="data" class="detail-table">
			   		    <div id="caption1">
		   					<button type="button"  class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
                       </div>
                       
			   		 </div>
			    </form:form>
			</div>
		</div>
	</div>
	<div id="mao"></div>
	<div hidden="hidden" class="template_cache">
		 <table class="view_table" style="overflow: hidden;">
	      <thead></thead>
          <tbody></tbody>
	   </table>
	</div>
</body>
</html>