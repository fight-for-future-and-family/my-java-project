<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-道具分析</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/economy/economyItemData.js?v=${version}"></script>
<style type="text/css">
.dataTables_wrapper .dataTables_filter {
	margin-left: 10px;
	z-index: 1;
	top: -40px;
	position: absolute;
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
	width: 85px;
	text-align: center;
	font-size: 14px;
	line-height: 27px;
	border-left: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}

.zhibiao {
	width: 100%;
	height: 350px;
	margin-top: 20px;
	margin-bottom: 20px;
	float: left;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_economy" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
					<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
					<li>></li>
					<li>${game.name }</li>
					<li>></li>
			    	<li>经济系统</li>
			    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
				     
			    <ul class="detail-nav">
			       <li id="economyItem" class="de-first active"><a ></a>消费分析</li>
			       <li id="economyDimension"><a></a>维表管理</li>
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
				    		<li class="detail-li">
				    		<span class="detail-left" style="width: 80%">
				    		   <span>
				    		      <select id="indicators" name="indicators" style="width:100px;">
				    		         <option value="all" selected="selected">总览</option>
				    		         <option value="client">分服</option>
				    		      </select>
				    			</span>
				    		   <span style="padding-left:20px;">
				    		      <select id="groupType" name="groupType" style="width:100px;">
				    		         <option value="first" selected="selected">一级分类</option>
				    		         <option value="second">二级分类</option>
				    		         <option value="third">三级分类</option>
				    		         <option value="fourth">四级分类</option>
				    		      </select>
				    			</span>
				    			<span id="s_c_span" style="padding-left:20px;"></span>
				    			<span style="padding-left:20px;">
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${ec_beginTime }" id="ec_beginTime" name="ec_beginTime"/>
					    			<span>至</span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${ec_endTime }" id="ec_endTime" name="ec_endTime" />
				    			</span>
				    			</span>
				    			<span class="detail-right" style="width: 15%">
				    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;">查询</button>
				    			</span>
				    		</li>
				    	</ul>
				    </div>
				    
				    <div id="economy_chart" class="zhibiao"></div>
			   
			   		 <div id="data" class="detail-table">
			   		   <div id="caption">
						    <span id="t_amount" class="ui-selected" val="amount" >消费元宝</span>
						    <span id="t_users" val="users">消费人次</span>
						    <span id="t_times" val="times">消费次数</span>
						    <span id="t_itemNum" val="item_num" class="last">消费道具数</span>
		   					<button type="button"  class="btn btn-default btn-sm" style="width:80px;margin-left: 10px;margin-top: 5px;">下载数据</button>
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