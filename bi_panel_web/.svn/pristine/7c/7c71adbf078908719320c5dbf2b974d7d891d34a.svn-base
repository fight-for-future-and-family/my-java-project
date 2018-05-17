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

<title>${game.name }-日报</title>

 <link rel="stylesheet" href="/css/wap/wap.css?v=${version}"/>
<script type="text/javascript" src="/wap/js/dataTables.js?v=${version}"></script>
<script type="text/javascript" src="/wap/js/whaleUser.js?v=${version}"></script>
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
.detail-table td {
    min-width: 105px;
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
		       		<li id="testReport"><a></a>测试报告</li>
			    </ul>
			     <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/whaleUser/downloadData.ac" enctype="multipart/form-data">
					<input name="gamesId" type="hidden" value="${game.id }" />
					<%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
				  <input name="gameId" type="hidden" value="${gameId }" />
					<input id="gameRate" type="hidden" value="${game.rate }" />
					<input id="gameCurrency" type="hidden" value="${game.currency }" />
					<input id="timeZone" type="hidden" value="${game.timeZone }" />
					<input id="view" name="view" type="hidden" value="whaleUser" />
					<div class="detail-info">
				    	<ul class="detail-ul">
				    		<li class="detail-li">
				    		<span class="detail-left" style="width:88%;padding-left:5px;">
				    		   <span>
				    		      <span for="indicators">查询范围 </span>
				    		      <select id="indicators" name="indicators" style="width:120px;">
				    		         <option value="all" selected="selected">全部用户</option>
				    		         <option value="wastage">快流失用户</option>
				    		      </select>
				    			</span>
				    			<span class="detail-right" style="width:10%;margin-top: -7px">
				    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;">查询</button>
				    			</span>
				    			</span>
				    		</li>
				    		<li class="detail-li">
				    		<span class="detail-left" style="width:99%;padding-left:5px;text-align: left">
				    			<span>
				    			    <span>注册日期 </span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="" id="beginTime" name="beginTime"/>
					    			<span>至</span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="" id="endTime" name="endTime" />
				    			</span>
				    		</span>
				    		</li>
				    		<li class="detail-li">
				    		<span class="detail-left" style="width:99%;padding-left:5px;text-align: left">
				    			<span>
				    			    <span>登录日期 </span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="" id="beginDauTime" name="beginDauTime"/>
					    			<span>至</span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="" id="endDauTime" name="endDauTime" />
				    			</span>
				    		</span>
				    		</li>
				    		<li class="detail-li">
				    		<span class="detail-left" style="width:88%;padding-left:5px;">
				    			<span>
				    			    <span>付费范围</span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="" id="beginPay" name="beginPay"/>
					    			<span>至</span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="" id="endPay" name="endPay" />
				    			</span>
				    			</span>
				    		</li>
				    	</ul>
				    </div>
				    <div  style="margin-top: 10px;margin-bottom: 10px;text-align: left;font-size: 14px;">
				          <font style="color: rgb(82, 197, 131);font-weight: bold">提示：</font>
				          查询用户总付费>=1000${game.currency }。3天以上未登录的用户显示<font style="color: rgba(255, 0, 0, 0.9)">红色</font>。2周以上未充值的用户显示<font style="color: rgba(0, 0, 255, 0.68)">蓝色</font>。3天以上未登录且2周以上未充值的用户显示<font style="color: rgba(255, 0, 0, 0.9)">红色</font>。
				     </div>
			   		 <div id="data" class="detail-table">
                       <div id="caption">
		   					<button type="button" class="btn btn-default btn-sm" style="width:80px;margin-left: 10px;margin-top: 5px;">下载数据</button>
                       </div>
			   		 </div>
			    </form:form>
			</div>
		</div>
		
		<div hidden="hidden" class="template_cache">
		 <table class="view_table">
	      <thead>
	      <tr>
	      	<td>用户ID<td>
			<td>安装日期</td>
			<td>首次付费日期</td>
			<td>首次付费额(${game.currency })</td>
			<td>总付费(${game.currency })</td>
			<td>最近付费日期</td>
			<td>未充值天数</td>
			<td>最近登录日期</td>
			<td>未登陆天数</td>
			<td>用户等级</td>
			<td>首登服务器</td>
			<td>服务器ID</td>
			<td>角色ID</td>
			<td>角色名称</td>
			<td>vip等级</td>
			<td>更新日期</td>
	      </tr>
	      </thead>
          <tbody></tbody>
	   </table>
	</div>
	</div>
</body>
</html>