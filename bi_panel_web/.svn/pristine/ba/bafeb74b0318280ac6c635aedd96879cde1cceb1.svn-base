<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-CPA&CPS</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/cpa/channelCps.js?v=${version}"></script>
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
		   <jsp:param value="gameli_CPA" name="game_page"/>
		</jsp:include>
		<div class="data-container" style="min-height:700px">
			<div class="detail">
				<ol class="lp-breadcrumb">
					<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
					<li>></li>
					<li>${game.name }</li>
					<li>></li>
			    	<li>CPS</li>
			    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
				     
			    <ul class="detail-nav">
			       <!--  <li id="cpa"><a ></a>CPA</li>-->
			       <li id="cps" class="de-first active"><a></a>CPS</li>
			       <c:if test="${!isOutSideUser }">
				       <li id="dimension"><a></a>维表管理</li>
				       <li id="authManage"><a></a>权限管理</li>
			       </c:if>
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
					<input id="view" name="view" type="hidden" value="cps" />
					<div class="detail-info">
				    	<ul class="detail-ul">
				    		<li class="detail-li">
				    		   <span style="padding-left:20px;">
				    		      <select id="indicators" name="indicators" style="width:100px;">
				    		         <option value="source" selected="selected">按渠道</option>
				    		         <option value="day">按天</option>
				    		         <option value="month">按月</option>
				    		      </select>
				    			</span>
				    		   <span style="padding-left:20px;">
				    		      <select id="channel" name="channel" style="max-width:200px">
			    	                <!--  <option value="0" label="请选择..."/>   -->   
			    	                <option value="-1" label="全部"/>    
			    	                <c:forEach items="${dimensionList}" var="dimension">
			    	                   <option value="${dimension.sourceCode }">${dimension.sourceName }</option>
			    	                </c:forEach>
			    	              </select>
				    			</span>
				    			<span class="detail-left" id="day_span">
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${ec_beginTime }" id="ec_beginTime" name="ec_beginTime"/>
					    			<span>至</span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${ec_endTime }" id="ec_endTime" name="ec_endTime" />
				    			</span>
				    			<span id="month_span"  class="detail-left" style="display:none">
			    			   <span id="m_y_begin">
			    		          <select class="dateSelect" id="year_begin" style="width:62px">
			    		            <c:forEach var="y" begin="<%=year-5 %>" end="<%=year %>" step="1"> 
                                        <option value="${y }"><c:out value="${y }" /></option>
			    		            </c:forEach>
			    		         </select>
			    		       </span>
			    			   <span id="m_m_begin">
			    			      <select class="dateSelect" id="month_begin" style="width:62px">
			    		            <c:forEach var="m" begin="1" end="12" step="1"> 
                                        <option value="${m-1 }"><c:out value="${m }月" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span>至</span>
			    			   <span id="m_y_end">
			    			      <select class="dateSelect" id="year_end" style="width:62px">
			    		            <c:forEach var="y" begin="<%=year-5 %>" end="<%=year %>" step="1"> 
                                        <option value="${y }"><c:out value="${y }" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span id="m_m_end">
			    			      <select class="dateSelect" id="month_end" style="width:62px">
			    		            <c:forEach var="m" begin="1" end="12" step="1"> 
                                        <option value="${m-1 }"><c:out value="${m }月" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span id="w_m_w_span_end"></span>
			    			   <span id="datetextSpan" style="display: none">
			    		         <input type="text" class="noneInput" id="m_beginTime" readonly="readonly"/> ~ 
			                     <input type="text" class="noneInput" id="m_endTime" readonly="readonly"/>
			    		       </span>
			    			</span>
				    			<span class="detail-right">
				    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;">查询</button>
				    			</span>
				    		</li>
				    	</ul>
				    </div>
				    
			   		 <div id="data" class="detail-table">
			   		   <div id="caption">
		   					<button type="button" class="btn btn-default btn-sm" style="width:80px;margin-left: 10px;margin-top: 5px;">下载数据</button>
                       </div>
                       
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
		      <td>付费人数</td>
		      <td>付费次数</td>
		      <td>付费额(${game.currency })</td>
		       <c:if test="${!isOutSideUser }">
			      <td>真实付费人数</td>
			      <td>真实付费次数</td>
			      <td>真实付费额(${game.currency })</td>
		      </c:if>
	      </tr>
	      </thead>
          <tbody></tbody>
	   </table>
	</div>
</body>
</html>