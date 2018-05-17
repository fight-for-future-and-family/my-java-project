<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-游戏总览</title>
<link rel="stylesheet" href="/css/game/gameView.css?v=${version}"/>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/games/gameView.js?v=${version}"></script>
<style type="text/css">
.noneInput{
    outline:medium;
    border:0;
    background:transparent;
    color: gray;
    min-width:50px;
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
		   <jsp:param value="gameli_all" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
				<li>总览</li>
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
			      <input id="view" type="hidden" value="overview" />
			    <ul class="detail-nav">
			       <li class="de-first active" id="overview"><a href="#"></a>游戏</li>
			       <li id="sources"><a></a>渠道</li>
			    </ul>
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		   <span style="padding-left:20px;">
			    		      <select id="dateDimension" name="dateDimension" style="width:60px;height:24px;">
			    		         <option value="day" selected="selected">日</option>
			    		         <option value="week">周</option>
			    		         <option value="month">月</option>
			    		      </select>
			    			</span>
			    			<span id="day_span">
			    			   <span class="detail-left">
				    			   <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			   <span>至</span>
				    			   <input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${endTime }" id="endTime" name="endTime" />
			    			   </span>
			    			</span>
			    			<span id="month_week_span" style="display:none">
			    			<span id="w_m_y_span_begin">
			    		          <select class="dateSelect" id="year_begin" style="width:62px">
			    		            <c:forEach var="y" begin="<%=year-5 %>" end="<%=year %>" step="1"> 
                                        <option value="${y }"><c:out value="${y }" /></option>
			    		            </c:forEach>
			    		         </select>
			    		       </span>
			    			   <span id="w_m_m_span_begin">
			    			      <select class="dateSelect" id="month_begin" style="width:62px">
			    		            <c:forEach var="m" begin="1" end="12" step="1"> 
                                        <option value="${m-1 }"><c:out value="${m }月" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span id="w_m_w_span_begin"></span>
			    			   <span>至</span>
			    			   <span id="w_m_y_span_end">
			    			      <select class="dateSelect" id="year_end" style="width:62px">
			    		            <c:forEach var="y" begin="<%=year-5 %>" end="<%=year %>" step="1"> 
                                        <option value="${y }"><c:out value="${y }" /></option>
			    		            </c:forEach>
			    		         </select>
			    			   </span>
			    			   <span id="w_m_m_span_end">
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
			    				<button id="query" class="btn btn-default btn-sm" style="width:100px;">查询</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			    
			    <div class="data_table">
			       <div id="install_count" class="zhibiao_left">
			          <ul class="data_ul">
			            <li class="div_count_normal_li">新注册用户数</li>
			            <li class="div_count_bold_li count"></li>
			            <li class="div_count_normal_li min"></li>
			            <li class="div_count_normal_li max"></li>
			            <li class="div_count_normal_li avg"></li>
			         </ul>
			         <div id="installCount_tu" class="tu" tuname="新注册用户数"></div>
			       </div>
			       <div id="pay_count" class="zhibiao">
			          <ul class="data_ul">
			            <li class="div_count_normal_li">累计付费</li>
			            <li class="div_count_bold_li count"></li>
			            <li class="div_count_normal_li min"></li>
			            <li class="div_count_normal_li max"></li>
			            <li class="div_count_normal_li avg"></li>
			         </ul>
			         <div id="payCount_tu" class="tu" tuname="累计付费"></div>
			       </div>
			       <div id="dau_mau_count" class="zhibiao">
			          <ul class="data_ul">
			            <li class="div_count_normal_li">活跃人次</li>
			            <li class="div_count_bold_li count"></li>
			            <li class="div_count_normal_li min"></li>
			            <li class="div_count_normal_li max"></li>
			            <li class="div_count_normal_li avg"></li>
			         </ul>
			         <div id="dauCount_tu" class="tu" tuname="活跃人次"></div>
			       </div>
			       <div id="pay_user_count" class="zhibiao_right">
			          <ul class="data_ul">
			            <li class="div_count_normal_li">累计付费人次</li>
			            <li class="div_count_bold_li count"></li>
			            <li class="div_count_normal_li min"></li>
			            <li class="div_count_normal_li max"></li>
			            <li class="div_count_normal_li avg"></li>
			         </ul>
			         <div id="payUserCount_tu" class="tu" tuname="累计付费人次"></div>
			       </div>
			    </div>
			    
			      <div id="ir" class="ir_biao_table" style="display: none;"></div>
			      <div id="ir_text" class="ir_biao_text" style="display: none;"></div>
			      <div id="pay_text" class="pay_text_zhong">
			         <ul class="data_ul" id="pay_ul">
			            <li class="div_text_normal_li">合计收入</li>
			            <li class="div_text_bold_li"><span class="count"></span> ${game.currency }</li>
			            <li class="div_text_normal_li">
			               <span class="payCnt" style="color:blue"></span>笔收入,单笔平均<span class="avg_pay" style="color:blue"></span>${game.currency }
			            </li>
			            <li class="div_text_normal_li">
			           		<span>付费率</span>
			            	<span style="padding-left: 30px;">ARPU</span>
			            	<span style="padding-left: 30px;">ARPPU</span>
			            </li>
			            <li class="div_text_normal_li">
			           		<span class="payRate" style="color:blue"></span>%
			            	<span class="arpu" style="padding-left: 30px;color:blue"></span>${game.currency }
			            	<span class="arppu" style="padding-left: 30px;color:blue"></span>${game.currency }
			            </li>
			         </ul>
			      </div>
			      <div id="roll_text" class="pay_text_zhong">
			        <ul class="data_ul" id="roll_ul">
			          <li class="div_text_normal_li">滚服收入</li>
			          <li class="div_text_bold_li"><span class="count"></span> ${game.currency }</li>
			          <li class="div_text_normal_li">
			                                           平均每日用户数 <span class="avg_user" style="color:blue"></span> 人
			          </li>
			          <li class="div_text_normal_li">
			                                           平均每日付费用户数 <span class="avg_pay_user" style="color:blue"></span> 人
			          </li>
			        </ul>
			      </div>
			      <div id="ctl_text" class="pay_text_hou"></div>
			   
			    <div id="data" class="detail-table" style="margin-top: 20px"></div>
			</div>
		</div>
	</div>



<div hidden="hidden" class="template_cache">
   <table class="view_table">
      <thead>
        <tr>
          <td>日期</td>
          <td>新注册</td>
          <td>创角数</td>
          <td>活跃</td>
          <td>收入(${game.currency })</td>
          <td>付费用户数</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="day"></td>
          <td class="dnu">新注册</td>
          <td class="roleChoice">-</td>
          <td class="dau">活跃</td>
          <td class="payMent">收入(${game.currency })</td>
          <td class="pu">付费用户数</td>
        </tr>
      </tbody>
   </table>
</div>

  
</body>
</html>