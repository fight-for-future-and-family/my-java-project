<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-等级分布分析</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<script type="text/javascript" src="/js/gameLevel/dauLevel.js?v=${version}"></script>
<link rel="stylesheet" href="/css/caption.css?v=${version}">
<style type="text/css">
#equipDesc {
  min-height: 20px;
  padding-top: 1px;
}
#equipDesc li{
 text-align: left;
 font-size: 12px;
 margin-top: 10px;
}
#equipDesc li div{
 width: 50%;
 float: left;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_level" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
					<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
					<li>></li>
					<li>${game.name }</li>
					<li>></li>
			    	<li>等级分布分析</li>
			    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
				     
			    <ul class="detail-nav">
			       <li id="dauLevel" class="de-first active"><a ></a>DAU</li>
			       <li id="installLevel"><a></a>日注册</li>
			       <li id="installPaymentLevel"><a></a>新注册付费</li>
			       <li id="newPaymentLevel"><a></a>新付费</li>
			       <!--  <li id="kpiPredict"><a></a>DAU&KPI预测</li>-->
			    </ul>
			    
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/payerLevel/downloadData.ac" enctype="multipart/form-data">
					<input name="gamesId" type="hidden" value="${game.id }" />
					<%-- <input name="snid" type="hidden" value="${game.snid }" />
					<input name="gameId" type="hidden" value="${game.gameid }" /> --%>
					<input name="snid" type="hidden" value="${snid }" />
					<input name="gameId" type="hidden" value="${gameId }" />
					<input id="gameRate" type="hidden" value="${game.rate }" />
					<input id="gameCurrency" type="hidden" value="${game.currency }" />
					<input id="timeZone" type="hidden" value="${game.timeZone }" />
					<input id="view" name="view" type="hidden" value="dauLevel" />
					<input id="queryType" name="queryType" type="hidden" value="dau" />
					<div class="detail-info">
				    	<ul class="detail-ul">
				    		<li class="detail-li">
				    			<span class="detail-left">
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;"  value="${ec_beginTime }" id="ec_beginTime" name="ec_beginTime"/>
					    			<span>至</span>
					    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:100px;" value="${ec_endTime }" id="ec_endTime" name="ec_endTime" />
				    			</span>
				    			<span class="detail-right">
				    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;">查询</button>
				    			</span>
				    		</li>
				    	</ul>
				    </div>
				    <div id="desc" style="width:100%;clear: both;text-align: left;padding: 10px 0px 0px 0px;">
				      <img id="descImg" alt="说明小箭头" src="/images/down.png" style="width:2%;height:2%">
				       <font style="font-size: 12px"><span id="descText">展开说明</span></font>
				    </div>
				    <div id="equipDesc" hidden="hidden">
				    <ul>
		        	 <li>
		        	 	<div>等级数据的准确性与报送密度有关，请根据自己项目的密度合理参考本数据</div>
		            </ul>
				    </div>
				    
				    <div id="dau_level_chart" class="zhibiao"></div>
			   
			   		 <div id="data" class="detail-table">
			   		 <div id="caption">
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
	      <thead>
	         <tr>
	            <td>等级</td>
	         </tr>
	      </thead>
          <tbody>
             <tr>
	            <td>等级</td>
	         </tr>
          </tbody>
	   </table>
	</div>
</body>
</html>