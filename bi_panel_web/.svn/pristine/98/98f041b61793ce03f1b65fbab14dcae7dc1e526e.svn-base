<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-CPA</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/cpaCreative/cpaCreative1.js?v=${version}"></script>
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

.zhibiao {
	width: 100%;
	height: 350px;
	margin-top: 20px;
	margin-bottom: 20px;
	float: left;
}
</style>
<%
   Calendar calendar = Calendar.getInstance();
   int year = calendar.get(Calendar.YEAR);
%>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_CPACreative" name="game_page"/>
		</jsp:include>
		<div class="data-container" style="min-height:700px">
			<div class="detail">
				<ol class="lp-breadcrumb">
					<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
					<li>></li>
					<li>${game.name }</li>
					<li>></li>
			    	<li>CPA</li>
			    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
				     
			    <ul class="detail-nav">
			       <li id="cps" class="de-first active"><a></a>CPA</li>
			    </ul>
			    
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/costPer/downloadData.ac" enctype="multipart/form-data">
					<input name="gamesId" type="hidden" value="${game.id }" />
					<%-- <input name="snid" type="hidden" value="${game.snid }" />
					<input name="gameId" type="hidden" value="${game.gameid }" /> --%>
					<input name="snid" type="hidden" value="${snid }" />
					<input name="gameId" type="hidden" value="${gameId }" />
					<input id="gameRate" type="hidden" value="${game.rate }" />
					<input id="gameCurrency" type="hidden" value="${game.currency }" />
					<input id="timeZone" type="hidden" value="${game.timeZone }" />
					<input id="beginTime" name="beginTime" type="hidden" />
					<input id="endTime" name="endTime" type="hidden" />
					<input id="view" name="view" type="hidden" value="cpa" />
					<div class="detail-info">
				    	<ul class="detail-ul">
				    		<li class="detail-li">
				    		   <span style="padding-left:20px;">
				    		      <select id="channel" name="channel" style="max-width:200px">
			    	                <option value="-1" label="全部"/>    
			    	                <c:forEach items="${dimensionList}" var="dimension">
			    	                   <option value="${dimension.sourceCode }">${dimension.sourceName }</option>
			    	                </c:forEach>
			    	              </select>
				    			</span>
				    			<span class="detail-left" id="day_span">
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${ec_endTime }" id="ec_endTime" name="ec_endTime" />
				    			</span>
				    			<span class="detail-right">
				    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;">查询</button>
				    			</span>
				    		</li>
				    	</ul>
				    </div>
				    
			   		 <div id="data" class="detail-table">
                       
			   		 </div>
			    </form:form>
			</div>
		</div>
	</div>
	<div hidden="hidden" class="template_cache">
		 <table class="view_table" style="overflow: hidden;">
	      <thead>
	      <tr>
	      	  <td>日期</td>
		      <td>渠道代码</td>
		      <td>渠道名称</td>
		      <td>新注册</td>
		      <td>新激活设备</td>
	      </tr>
	      </thead>
          <tbody></tbody>
	   </table>
	</div>
</body>
</html>