<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-新付费</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
	.kong{
	    width:100%;
		height:50px;
	}
	.zhibiao{
		width:50%;
		height:350px;
		margin-top: 20px;
		float: left;
	}
	.zhibiao_arpu{
		width:100%;
		height:350px;
		margin-top: 20px;
		margin-bottom: 20px;
		float: left;
	}
	.zhibiao_chart{
		height:350px;
		margin-top: 20px;
	}
	.zhibiao_tab{
		height:350px;
		margin-top: 20px;
		width: 49%;
	}
	.dataTables_wrapper .dataTables_filter {
	  margin-left:10px;
      z-index:1;
      position:absolute;
     }
     caption {
        font-style: normal;
        text-align: right;
        margin-right: 30px;
	}
</style>
<script type="text/javascript" src="/js/games/gameNewPay.js?v=${version}"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_payment" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>游戏收入</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			     
			    <ul class="detail-nav">
			       <li id="allPay"><a ></a>付费总览</li>
			       <li id="newPay" class="active"><a href="#"></a>新付费</li>
			       <li id="payBehavior"><a href="#"></a>付费行为</li>
			    </ul>
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/gamePay/downloadData.ac" enctype="multipart/form-data">
			    <input name="gamesId" type="hidden" value="${game.id }" />
			     <%-- <input name="snid" type="hidden" value="${game.snid }" />
			     <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			     <input name="snid" type="hidden" value="${snid }" />
			     <input name="gameId" type="hidden" value="${gameId }" />
			     <input id="gameRate" type="hidden" value="${game.rate }" />
			      <input id="gameCurrency" type="hidden" value="${game.currency }" />
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" name="view" type="hidden" value="newPay" />
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		   <span style="padding-left:20px;">
			    		      <select id="indicators" name="indicators" style="width:100px;">
			    		         <option value="all" selected="selected">总览</option>
			    		         <option value="source">分渠道</option>
			    		         <option value="client">分服</option>
			    		      </select>
			    			</span>
			    			<span id="s_c_span" style="padding-left:20px;"></span>
			    			<span class="detail-left">
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			<span>至</span>
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${endTime }" id="endTime" name="endTime" />
			    			</span>
			    			<span class="detail-right">
			    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;">查询</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			    
			   <div id="pay_level_chart" class="clear-fix" style="width: 49%;float: left;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="pay_level" class="zhibiao_chart"></div>
			       <div id="pay_level_data" class="zhibiao_tab" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: left;"> </div>
			       <div id="avg_pay_level_data" class="zhibiao_tab" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: right;"> </div>
			    </div>
			    
			    <div id="pay_arpu_arppu" class="zhibiao"></div>
			    
			    <div id="pay_user_chart" class="clear-fix" style="width: 100%;float: left;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:32px;right:18px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="pay_user" class="zhibiao_arpu"></div>
			       <div id="pay_user_data" class="zhibiao_arpu" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: right;"> </div>
			    </div>
			    
			    
			    <div id="data" class="detail-table"> </div>
			    <div id="ajax_data" class="detail-table" style="position: relative;" hidden="hidden"></div>
			    </form:form>
			</div>
		</div>
	</div>
	
	<div hidden="hidden" class="template_cache">
   <table class="view_table">
      <thead>
        <tr>
          <td>日期</td>
          <td>新付费人数</td>
          <td>新付费额(${game.currency })</td>
          <td>新ARPPU(${game.currency })</td>
          <td>注册付费人数</td>
          <td>注册付费额(${game.currency })</td>
          <td>注册付费ARPPU(${game.currency })</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="date">-</td>
          <td class="newPayUser">-</td>
          <td class="newPayAmount">-</td>
          <td class="newARPPU">-</td>
          <td class="installPayUser">-</td>
          <td class="installPayAmount">-</td>
          <td class="installARPPU">-</td>
        </tr>
      </tbody>
   </table>
   <table class="pay_level_table">
      <thead>
        <tr>
          <td>付费用户等级</td>
          <td>累计付费</td>
          <td>占比</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="level">0</td>
          <td class="payCount">0</td>
          <td class="rate">0%</td>
        </tr>
      </tbody>
   </table>
   <table class="avg_pay_level_table">
      <thead>
        <tr>
          <td>付费用户等级</td>
          <td>平均付费</td>
          <td>占比</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="level">0</td>
          <td class="avg_payCount">0</td>
          <td class="rate">0%</td>
        </tr>
      </tbody>
   </table>
   <table class="pay_user_table">
      <thead>
        <tr>
          <td>日期</td>
          <td>新注册首日付费(${game.currency })</td>
          <td>非新注册首日付费(${game.currency })</td>
          <td>新注册首付人数</td>
          <td>非新注册首付人数</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="date">0</td>
          <td class="newPay">0</td>
          <td class="notNewPay">0</td>
          <td class="newPu">0</td>
          <td class="notNewPu">0</td>
        </tr>
      </tbody>
   </table>
   <table class="ajax_table">
   <caption>
           <button type="button"  id="clientAllData" class="btn btn-default btn-sm"style="width:80px;">下载数据</button>
      </caption>
      <thead>
        <tr>
          <td>日期</td>
          <td class="head_type">渠道</td>
          <td>新付费人数</td>
          <td>新付费额(${game.currency })</td>
          <td>新ARPPU(${game.currency })</td>
          <td>注册付费人数</td>
          <td>注册付费额(${game.currency })</td>
          <td>注册付费ARPPU(${game.currency })</td>
        </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
</div>
</body>
</html>