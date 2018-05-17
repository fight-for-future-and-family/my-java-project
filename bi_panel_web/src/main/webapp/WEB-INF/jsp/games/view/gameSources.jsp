<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-分渠道总览</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>

<style type="text/css">
	.kong{
	    width:100%;
		height:50px;
	}
	.zhibiao{
		width:100%;
		height:200px;
		float: left;
		margin-top: 50px;
	}
	.zhibiao_pie{
	    width:100%;
		height:380px;
		float: left;
		margin-top: 20px;
		margin-bottom: 20px;
	}
	.zhibiao_bar{
	    width:100%;
		height:380px;
		float: left;
		margin-top: 20px;
		margin-bottom: 20px;
	}
	.zhibiao_pie_tab{
	    width:32%;
		height:380px;
		float: left;
		margin-top: 20px;
		margin-bottom: 20px;
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

<script type="text/javascript" src="/js/games/gameSources.js?v=${version}"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_all" name="game_page"/>
		</jsp:include>
		<div class="data-container">
		<form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/gameView/downloadData.ac" enctype="multipart/form-data">
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
			      <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="gameRate" type="hidden" value="${game.rate }" />
			      <input id="terminalType" type="hidden" value="${game.terminalType }" />
			      <input id="systemType" type="hidden" value="${game.systemType }" />
			      <input id="view" name="view" type="hidden" value="sources" />
			    <ul class="detail-nav">
			       <li  id="overview"><a href="#"></a>游戏</li>
			       <li class="active" id="sources"><a></a>渠道</li>
			    </ul>
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		   <span style="padding-left:20px;">
			    		      <select id="indicators" name="indicators" style="width:60px;">
			    		         <option value="day" selected="selected">日</option>
			    		      </select>
			    			</span>
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
			    
			    <div id="sc_pie_chart" class="clear-fix" style="width: 49%;float: left;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="sc_pie" class="zhibiao_pie"></div>
			       
			       <div id="sc_pay_data" class="zhibiao_pie_tab" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: left;"> </div>
			       
			       <div id="sc_install_data" class="zhibiao_pie_tab" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: left;margin-left: 2%;margin-right: 2%"> </div>
			      
			       <div id="sc_dau_data" class="zhibiao_pie_tab" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: right;"> </div>
			    </div>
			    
			    <div id="ltv_bar_chart" class="clear-fix" style="width: 50%;float: right;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="ltv_bar" class="zhibiao_bar"></div>
			       <div id="ltv_bar_data" class="zhibiao_bar" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;float: right;"> </div>
			    </div>
			    
			    <div id="ajax_data" class="detail-table"></div>
			    
			</div>
		</form:form>
		</div>
	</div>
	
	<div hidden="hidden" class="template_cache">
   <table class="view_table">
   <caption>
          <button type="button" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
     </caption>
      <thead>
        <tr>
          <td>日期</td>
          <td>渠道</td>
          <td>新注册</td>
          <td>创角数</td>
          <td>活跃</td>
          <td>收入(${game.currency })</td>
          <td>付费用户数</td>
        </tr>
        <tr>
          <td>日期</td>
          <td>渠道</td>
          <td>新注册</td>
          <td>创角数</td>
          <td>活跃</td>
          <td>收入(${game.currency })</td>
          <td>付费用户数</td>
          <td>广告点击总数</td>
		  <td>广告点击总数（去重）</td>
		  <td>ip总数（去重）</td>
        </tr>
      </thead>
      <tbody>
        <tr>
        </tr>
      </tbody>
   </table>
   <table class="sc_pay_table">
      <thead>
        <tr>
          <td>渠道</td>
          <td>付费(累计)(${game.currency })</td>
          <td>占比</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="source">渠道</td>
          <td class="payMent">收入(${game.currency })</td>
          <td class="rate">-%</td>
        </tr>
      </tbody>
   </table>
   <table class="sc_install_table">
      <thead>
        <tr>
          <td>渠道</td>
          <td>新注册(平均)</td>
          <td>占比</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="source">渠道</td>
          <td class="install">0</td>
          <td class="rate">-%</td>
        </tr>
      </tbody>
   </table>
   <table class="sc_dau_table">
      <thead>
        <tr>
          <td>渠道</td>
          <td>活跃(平均)</td>
          <td>占比</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="source">渠道</td>
          <td class="dau">0</td>
          <td class="rate">-%</td>
        </tr>
      </tbody>
   </table>
   <table class="ltv_table">
      <thead>
        <tr>
          <td>渠道</td>
          <td>D1(${game.currency })</td>
          <td>D3(${game.currency })</td>
          <td>D7(${game.currency })</td>
          <td>D30(${game.currency })</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="source">渠道</td>
          <td class="D1">0</td>
          <td class="D3">0</td>
          <td class="D7">0</td>
          <td class="D30">0</td>
        </tr>
      </tbody>
   </table>
</div>
</body>
</html>