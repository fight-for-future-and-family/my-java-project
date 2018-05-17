<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-DAU&KPI预测</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
#selectDate span.ui-selected {
	background: #56C887;
	color: white;
}

#selectDate span.last {
	border-right: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}

#selectDate span {
	float: left;
	width: 85px;
	margin: -3px auto;
	text-align: center;
	font-size: 14px;
	line-height: 27px;
	border-left: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}

.p-ul {
height: 170px;
width:100%;
border-top: 1px solid #c0c0c0;
background-color: rgba(247, 247, 247, 0.24);
}

.p-ul li{
 padding-bottom: 2px;
 padding-top: 6px;
 width:100%;
 overflow: hidden;
}

.head-table{
   width:100%;
   margin-top: 8px;
}

.head-table tr{
  height: 30px;
}

.p-ul li label {
    width: 130px;
    font-size: 12px;
}

.p-ul li input {
    padding-left: 5px;
}

.detail-table td{
	min-width: 90px; 
}

.rowspan input{
  height: 80px;
}

.tishi{
 text-align: left;
 padding: 5px;
}

.tishi span{
  font-size: 12px;
  padding-left: 15px;
}

.tishi_head{
  font-size: 14px !important;
  padding-left: 0px !important;
}

.enClick{
  background-color: rgba(153, 242, 27, 0.29);
  border-right: 1px solid #fff;
  border-radius:4px;
  cursor:pointer;
}

.cankao_tr{
   background-color: rgba(137, 140, 138, 0.17) !important;
}

</style>
<script type="text/javascript" src="/js/predict/prediction.js?v=${version}"></script>
</head>
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
			    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
				     
			    <ul class="detail-nav">
			       <li id="trend"><a ></a>趋势对比</li>
			       <li id="multi_indicators"><a></a>多指标对比</li>
			       <li id="prediction" class="active"><a></a>DAU&KPI预测</li>
			    </ul>
			    
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/prediction/downloadData.ac" enctype="multipart/form-data">
					<input name="gamesId" type="hidden" value="${game.id }" />
					<%-- <input name="snid" type="hidden" value="${game.snid }" />
					<input name="gameId" type="hidden" value="${game.gameid }" /> --%>
					<input name="snid" type="hidden" value="${snid }" />
					<input name="gameId" type="hidden" value="${gameId }" />
					<input id="gameRate" type="hidden" value="${game.rate }" />
					<input id="gameCurrency" type="hidden" value="${game.currency }" />
					<input id="timeZone" type="hidden" value="${game.timeZone }" />
					<input id="currDate" type="hidden" value="${currDate }" />
					<input id="view" name="view" type="hidden" value="dauLevel" />
					<input id="queryType" name="queryType" type="hidden" value="dau" />
					<div class="detail-info">
				    	<ul class="detail-ul" style="margin-bottom: 10px">
				    		<li class="detail-li">
				    			<span style="overflow: hidden;display: inline-block;width: 100%;height: 40px;text-align: left;padding-top: 10px;padding-left: 20px;">
					    		    <span id="selectDate" style="padding-left:20px;overflow: hidden;margin-top: 8px;">
		                                <span css="dateSelect" id="currMonth" val="0" class="ui-selected">当月</span>
		                                <span css="dateSelect" id="nextMonth" val="1" class="last">下月</span>
					    			</span>
					    			  <span style="padding-right:20px;float: right">
					    				<button id="calculate" type="button" class="btn btn-default btn-sm" style="width:80px;">计算</button>
					    				<button id="reset" type="button" class="btn btn-default btn-sm" style="width:80px;">重置</button>
					    				<button id="download" type="button" class="btn btn-default btn-sm" style="width:80px;">下载</button>
					    			</span>
			    				</span>
				    		</li>
				    	</ul>
				    	<ul class="p-ul">
				    	<li>
				    	  <table class="head-table">
				    	    <tr>
				    	       <td><label for="predictAmount">预测收入(${game.currency })</label></td>
				    	       <td style="font-weight: bold;color: #56C887;width: 100px;">
				    	       		<span id="predictAmount"></span>
				    	       </td>
				    	       <td><label for="retention">次日留存(%)</label></td>
				    	       <td><input class="b-radius5 p-input" id="retention" colnum="7" required="required" /></td>
				    	       <td><label for="install">注册(个)</label></td>
				    	       <td><input class="b-radius5 p-input" id="install" colnum="6" required="required"  /></td>
				    	       <td rowspan="5" class="tishi">
				    	       	<span class="tishi_head">提示：</span><br>
				    	       	<span>1.左方灰色区域为运营总体计划数据。</span><br>
				    	       	<span>2.若有特殊情况，表头带颜色的字段可双击设定指定值。</span><br>
				    	       	<span>3.数据计划完毕后，点击计算得出预测结果。点击下载，可下载此计划数据。</span><br>
				    	       	<span>4.重置恢复初始值。</span><br>
				    	       </td>
				    	    </tr>
				    	    <tr>
				    	    	<td><label for="arppu_before">ARPPU从目前(${game.currency })</label></td>
				    	    	<td><input class="b-radius5 p-input" id="arppu_before" value="" required="required"  /></td>
				    	    	<td><label for="arppu_increase">到(${game.currency })</label></td>
				    	    	<td><input class="b-radius5 p-input" id="arppu_increase" value="" required="required"  /></td>
				    	    	<td><label for="arppu_use_time">用时(天)</label></td>
				    	    	<td rowspan="3" class="rowspan"><input class="b-radius5 p-input" id="arppu_use_time" value="1" required="required"  /></td>
				    	    </tr>
				    	    <tr>
				    	    	<td><label for="arpu_before">ARPU从目前(${game.currency })</label></td>
				    	    	<td><span id="arpu_before"></span></td>
				    	    	<td><label for="arpu_increase">到(${game.currency })</label></td>
				    	    	<td><span id="arpu_increase"></span></td>
				    	    	<td><label for="arppu_use_time">用时(天)</label></td>
				    	    </tr>
				    	    <tr>
				    	    	<td><label for="payRate_before">付费率从目前(%)</label></td>
				    	    	<td><input class="b-radius5 p-input" id="payRate_before" value="" required="required"  /></td>
				    	    	<td><label for="payRate_increase">到(%)</label></td>
				    	    	<td><input class="b-radius5 p-input" id="payRate_increase" value="" required="required"  /></td>
				    	    	<td><label for="arppu_use_time">用时(天)</label></td>
				    	    </tr>
				    	    <tr>
				    	    	<td><label for="oldUser_before">老用户流失率从目前(%)</label></td>
				    	    	<td><input class="b-radius5 p-input" id="oldUser_before" required="required"  /></td>
				    	    	<td><label for="oldUser_increase">到(%)</label></td>
				    	    	<td><input class="b-radius5 p-input" id="oldUser_increase" required="required"  /></td>
				    	    	<td><label for="oldUser_use_time">用时(天)</label></td>
				    	    	<td><input class="b-radius5 p-input" id="oldUser_use_time" value="0" required="required"  /></td>
				    	    </tr>
				    	  </table>
				    	</li>
				    	</ul>
				    	
				    </div>
				    <textarea id="data_table_value" name="dataTableValue" hidden="hidden"></textarea>
				    </form:form>
			   		 <div id="data" class="detail-table">
			   		  <!--   <div id="caption">
		   					<button type="button"  style="width:80px;margin-left: 10px;margin-top: 5px;">下载数据</button>
                       </div>-->
                       <table id="data_table" class="table table-striped">
                          <thead>
                             <tr>
                                <td></td>
                                <td>DAU</td>
                                <td>老用户</td>
                                <td>收入(${game.currency })</td>
                                <td>ARPU(${game.currency })</td>
                                <td>老玩家流失率</td>
                                <td id="installTh" val="6" class="enClick">注册</td>
                                <td id="d1Th" val="7" class="enClick">次日留存</td>
                                <td id="oldUserTh" val="8" class="enClick">调整老玩家流失率</td>
                                <td id="arppuTh" val="9" class="enClick">调整ARPPU(${game.currency })</td>
                                <td id="payRateTh" val="10" class="enClick">调整付费率</td>
                             </tr>
                          </thead>
                          <tbody>
                            <tr>
                            	<td class="day">参考值</td>
                                <td class="dau">0</td>
                                <td class="oldUser">0</td>
                                <td class="payment">0</td>
                                <td class="arpu">0</td>
                                <td class="oldUserLossRate">0%</td>
                                <td class="install">0</td>
                                <td class="d1">0%</td>
                                <td class="oldUser_update"></td>
                                <td class="arppu_update"></td>
                                <td class="payRate_update"></td>
                            </tr>
                          </tbody>
                       </table>
			   		 </div>
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