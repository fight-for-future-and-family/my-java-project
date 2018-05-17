<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>指标趋势</title>
<style type="text/css">
.chart {
	width: 50%;
	height: 300px;
	margin-top: 20px;
	float: left;
}

.detail-info {
	border: 1px solid #ebebeb;
	border-radius: 10px;
	width: 100%;
	min-height: 150px;
	height:auto !important;
}

.detail-div-ul {
	width: 65%;
	float: left;
}

.detail-div {
	width: 34%;
	float: left;
	margin-top: 5px;
	height: 130px;
	text-align:center;
}

.detail-div-but {
	width: 100%;
	border: 1px solid #ebebeb;
	border-radius: 10px;
	margin-top: 5px;
	height: 80%;
	margin:0 auto;
	text-align:center;
}
.data-container-chart{
    width: 100%;
	min-height: 480px;
	height:auto !important;
	margin-bottom: 25px;
}
.detail-li{
 text-align: left;
}

.detail-li label {
	padding-left: 0px;
	padding-top: 8px;
	width: 80px;
	text-align: right;
}

.detail-li span {
	padding-left: 2px;
	width: 80px;
}
.div-but{
    width: 33%;
	background-color: #99f21b;
	border: 1px solid #ebebeb;
	margin-top: 8px;
}
.noneInput-right{
    outline:medium;
    border:0;
    background:transparent;
    color: gray;
    min-width:50px;
}

.noneInput-left{
    outline:medium;
    border:0;
    background:transparent;
    color: gray;
    min-width:50px;
    text-align: right;
}
.caption{
  text-align: left;font-size: 14px;margin-left: -65%;color:#008acd;
}
.dateSelect{
	display: inline-block;
	height: 30px;
	text-align: left;
	margin-top: 5px;
	border: 1px solid #ebebeb;
	border-radius: 5px;
}
</style>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/games/tool/gameTrend.js?v=${version}"></script>
</head>
<%
   Calendar calendar = Calendar.getInstance();
   int year = calendar.get(Calendar.YEAR);
%>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_tool" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>分析工具</li>
			    </ol>
			    <input id="view" type="hidden" value="trend" />
		    	<input name="snid" type="hidden" value="${snid }" />
				<input name="gameId" type="hidden" value="${gameId }" />
			    <ul class="detail-nav">
			       <li id="trend" class="de-first active"><a ></a>趋势对比</li>
			       <li id="multi_indicators"><a></a>多指标对比</li>
			       <li id="prediction"><a></a>DAU&KPI预测</li>
			    </ul>
			    
			    <div class="detail-info">
			    <div class="detail-div-ul">
			    	<ul class="detail-ul">
			    	     <li class="detail-li">
			    	        <label for="channel">时间维度</label>
			    		   <span>
			    		      <select id="dateDimension" name="dateDimension" style="width:26%;">
			    		         <option value="day" selected="selected">日</option>
			    		         <option value="week">周</option>
			    		         <option value="month">月</option>
			    		      </select>
			    			</span>
			    	     </li>
			    	    <li class="detail-li">
			    	       <label for="channel">channel</label>
			    		   <span>
			    		      <select id="channel" name="channel" style="width:26%;">
			    		         <option value="all" selected="selected">总览</option>
			    		         <option id="ch-s" value="source">分渠道</option>
			    		         <option id="ch-c" value="client">分服</option>
			    		      </select>
			    			</span>
			    			<span id="s_c_span" class="b-radius5" style="padding-left:20px;"></span>
			    	    </li>
			    		<li class="detail-li">
			    			<label for="gameA">游戏</label>
			    			<span>
			    		      <select id="gameA" name="gameA" style="width:32%;">
			    		        <c:forEach items="${gameList }" var="game">
			    		           <option rate="${game.rate }" currency="${game.currency }" value="${game.id }">${game.name }</option>
			    		        </c:forEach>
			    		      </select>
			    			</span>
			    			
			    			<label id="gameB_label" for="gameB">对比游戏</label>
			    			<span>
			    		      <select id="gameB" name="gameB" style="width:32%;">
			    		        <c:forEach items="${gameList }" var="game">
			    		           <option rate="${game.rate }" currency="${game.currency }" value="${game.id }">${game.name }</option>
			    		        </c:forEach>
			    		      </select>
			    			</span>
			    		</li>
			    		<li class="detail-li">
			    		   <label for="dateA">日期</label>
			    			<span id="day_span_A">
			    			   <span id="dateA">
				    			   <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${beginTimeA }" id="beginTimeA" name="beginTimeA"/>
				    			   <span>至</span>
				    			   <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${endTimeA }" id="endTimeA" name="endTimeA" />
			    			</span>
			    			</span>
			    			<span id="month_week_span_A" style="display:none">
			    		       <span id="w_m_y_span_a_begin">
			    		          <select class="dateSelect" id="year_begin_A" style="width:62px">
			    		            <c:forEach var="y" begin="<%=year-5 %>" end="<%=year %>" step="1"> 
                                        <option value="${y }"><c:out value="${y }" /></option>
			    		            </c:forEach>
			    		         </select>
			    		       </span>
			    			   <span id="w_m_m_span_a_begin">
			    			      <select class="dateSelect" id="month_begin_A" style="width:62px">
			    		            <c:forEach var="m" begin="1" end="12" step="1"> 
                                        <option value="${m-1 }"><c:out value="${m }月" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span id="w_m_w_span_a_begin"></span>
			    			   <span>至</span>
			    			   <span id="w_m_y_span_a_end">
			    			      <select class="dateSelect" id="year_end_A" style="width:62px">
			    		            <c:forEach var="y" begin="<%=year-5 %>" end="<%=year %>" step="1"> 
                                        <option value="${y }"><c:out value="${y }" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span id="w_m_m_span_a_end">
			    			      <select class="dateSelect" id="month_end_A" style="width:62px">
			    		            <c:forEach var="m" begin="1" end="12" step="1"> 
                                        <option value="${m-1 }"><c:out value="${m }月" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span id="w_m_w_span_a_end"></span>
			    		    </span>
			    		      <span id="brspan" style="display: none">
			    		       <input type="text" class="noneInput-left" id="m_beginTimeA" readonly="readonly"/> ~ 
			                   <input type="text" class="noneInput-right" id="m_endTimeA" readonly="readonly"/>
			    		       <br>
			    		      </span>
			    		      
			    			  <label id="dateB_label" for="dateB"  style="padding-left:20px;">对比日期</label>
			    			  <span id="day_span_B">
			    			      <span id="dateB">
				    			      <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${beginTimeB }" id="beginTimeB" name="beginTimeB"/>
				    			      <span>至</span>
				    			      <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${endTimeB }" id="endTimeB" name="endTimeB" />
			    			      </span>
			    		      </span>
			    		 <span id="month_week_span_B"  style="display:none">
			    		       <span id="w_m_y_span_b_begin">
			    		         <select class="dateSelect" id="year_begin_B" style="width:62px">
			    		            <c:forEach var="y" begin="<%=year-5 %>" end="<%=year %>" step="1"> 
                                        <option value="${y }"><c:out value="${y }" /></option>
			    		            </c:forEach>
			    		         </select>
			    		       </span>
			    			   <span id="w_m_m_span_b_begin">
			    			      <select class="dateSelect" id="month_begin_B" style="width:62px">
			    		            <c:forEach var="m" begin="1" end="12" step="1"> 
                                        <option value="${m-1 }"><c:out value="${m }月" /></option>
			    		            </c:forEach>
			    		          </select>
			    			   </span>
			    			   <span id="w_m_w_span_b_begin"></span>
			    			   <span>至</span>
			    			   <span id="w_m_y_span_b_end">
			    			      <select class="dateSelect" id="year_end_B" style="width:62px">
			    		            <c:forEach var="y" begin="<%=year-5 %>" end="<%=year %>" step="1"> 
                                        <option value="${y }"><c:out value="${y }" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span id="w_m_m_span_b_end">
			    			      <select class="dateSelect" id="month_end_B" style="width:62px">
			    		            <c:forEach var="m" begin="1" end="12" step="1"> 
                                        <option value="${m-1 }"><c:out value="${m }月" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span id="w_m_w_span_b_end"></span>
			    		    </span>
			    		    <span id="datetextSpan" style="display: none">
			    		      <input type="text" class="noneInput-left" id="m_beginTimeB" readonly="readonly"/> ~ 
			                  <input type="text" class="noneInput-right" id="m_endTimeB" readonly="readonly"/>
			    		    </span>
			    		    
			    		</li>
			    	</ul>
			    	</div>
					<div class="detail-div">
						<label for="indicators">指标</label> 
						<span>
                           <select id="indicators" name="indicators" style="width: 150px;">
								<option value="-1">请选择</option>
								<c:forEach items="${authorities }" var="auth">
									<option value="${auth.id }">${auth.authorityName }</option>
								</c:forEach>
						   </select>
						</span>
						<div class="detail-div-but"></div>
					</div>
				</div>
			    
			    <div id="chart" class="data-container-main">
			    
			    
			    </div>
			    
			    <div id="data" class="detail-table">
			    
			    </div>
			    <nav id="page_nav"></nav>
			</div>
		</div>
	</div>
</body>
</html>