<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-付费行为</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
	.zhibiao_left{
		width:100%;
		height:350px;
		margin-top: 20px;
		margin-bottom:20px;
	}
	.zhibiao_right{
		width:100%;
		height:350px;
		margin-top: 20px;
		margin-bottom: 30px;
	}
	.title{
	    font-size: 16px;
	    font-style: normal;
	    color: blue;
	    text-align: center;
	    margin-top: -35px;
	}
     
     .dataTables_wrapper .dataTables_filter {
	  margin-left:10px;
      z-index:1;
      position:absolute;
     }
     
     #buttonDiv {
        font-style: normal;
        text-align: right;
        margin-right: 30px;
        margin-top: 10px;
	}
	
	.ajax_title {
        font-style: normal;
        text-align: center;
        font-size: 16px;
	    font-style: normal;
	    color: blue;
	}
</style>
<script type="text/javascript" src="/js/games/gamePayBehavior.js?v=${version}"></script>
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
			       <li id="newPay" ><a href="#"></a>新付费</li>
			      <li id="payBehavior" class="active"><a href="#"></a>付费行为</li>
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
			      <input id="view" name="view" type="hidden" value="payBehavior" />
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
			    
			    <div id="pay_level_chart" class="clear-fix" style="width: 100%;float: left;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:32px;right:18px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="pay_level" class="zhibiao_left"></div>
			       <div id="pay_level_data" class="zhibiao_left" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: right;"> </div>
			    </div>
			    
			    <div id="pay_user_chart" class="clear-fix" style="width: 100%;float: left;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:32px;right:18px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="pay_user" class="zhibiao_right"></div>
			       <div id="pay_user_data" class="zhibiao_right" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: right;"> </div>
			    </div>
			    
			    <div id="data_pay" class="detail-table"></div>
			    <div id="data_user" class="detail-table"></div>
			     <div id="buttonDiv" style="position: relative;" hidden="hidden"><button class="btn btn-default btn-sm" style="width:80px;margin-right: -8px;">下载数据</button></div>
			     <div id="ajax_data_pay" class="detail-table" style="position: relative;" hidden="hidden"></div>
			     <div id="ajax_data_user" class="detail-table" style="position: relative;margin-top: 20px;" hidden="hidden"></div>
			    </form:form>
			</div>
		</div>
	</div>
	
	
				
	<div hidden="hidden" class="template_cache">
   <table class="pay_level_table">
       <caption align="top" class="title">付费额(${game.currency })</caption>
      <thead>
        <tr>
           	<td>日期</td>
           	<td>[0,10]</td>
			<td>(10,50]</td>
			<td>(50,100]</td>
			<td>(100,200]</td>
			<td>(200,500]</td>
			<td>(500,1000]</td>
			<td>(1000,2000]</td>
			<td>2000以上</td>
		</tr>
      </thead>
      <tbody>
        <tr>
          <td class="date">-</td>
          <td class="lp10">-</td>
          <td class="lp50">-</td>
          <td class="lp100">-</td>
          <td class="lp200">-</td>
          <td class="lp500">-</td>
          <td class="lp1000">-</td>
          <td class="lp2000">-</td>
          <td class="lp2000up">-</td>
        </tr>
      </tbody>
   </table>
   <table class="pay_user_table">
      <caption align="top" class="title">付费人数</caption>
      <thead>
        <tr>
           	<td>日期</td>
           	<td>[0,10]</td>
			<td>(10,50]</td>
			<td>(50,100]</td>
			<td>(100,200]</td>
			<td>(200,500]</td>
			<td>(500,1000]</td>
			<td>(1000,2000]</td>
			<td>2000以上</td>
		</tr>
      </thead>
      <tbody>
        <tr>
          <td class="date">-</td>
          <td class="lp10">-</td>
          <td class="lp50">-</td>
          <td class="lp100">-</td>
          <td class="lp200">-</td>
          <td class="lp500">-</td>
          <td class="lp1000">-</td>
          <td class="lp2000">-</td>
          <td class="lp2000up">-</td>
        </tr>
      </tbody>
   </table>
   <table class="pay_cnt_table">
      <thead>
        <tr>
           	<td>日期</td>
           	<td>人均付费次数</td>
		</tr>
      </thead>
      <tbody>
        <tr>
          <td class="date">-</td>
          <td class="avg_pay_cnt">-</td>
        </tr>
      </tbody>
   </table>
   <table class="pay_level_user_table">
      <thead>
        <tr>
           	<td>阶段</td>
           	<td>付费</td>
           	<td>付费人数</td>
		</tr>
      </thead>
      <tbody>
        <tr>
          <td class="step">-</td>
          <td class="payAmount">-</td>
          <td class="payUser">-</td>
        </tr>
      </tbody>
   </table>
   <table class="ajax_pay_table">
       <caption align="top" class="ajax_title">付费额(${game.currency })</caption>
      <thead>
        <tr>
           	<td>日期</td>
           	<td class="head_type">服务器</td>
           	<td>[0,10]</td>
			<td>(10,50]</td>
			<td>(50,100]</td>
			<td>(100,200]</td>
			<td>(200,500]</td>
			<td>(500,1000]</td>
			<td>(1000,2000]</td>
			<td>2000以上</td>
		</tr>
      </thead>
      <tbody>
        <tr>
        </tr>
      </tbody>
   </table>
   <table class="ajax_user_table">
      <caption align="top" class="ajax_title">付费人数</caption>
      <thead>
        <tr>
           	<td>日期</td>
           	<td class="head_type">渠道</td>
           	<td>[0,10]</td>
			<td>(10,50]</td>
			<td>(50,100]</td>
			<td>(100,200]</td>
			<td>(200,500]</td>
			<td>(500,1000]</td>
			<td>(1000,2000]</td>
			<td>2000以上</td>
		</tr>
      </thead>
      <tbody>
        <tr>
        </tr>
      </tbody>
   </table>
</div>
</body>
</html>