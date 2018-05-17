<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-注收比</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>

<style type="text/css">
	.zhibiao{
		height:350px;
		margin-bottom: 30px;
	}
	.zhibiao_bar{
		width:100%;
		height:350px;
		float: left;
		margin-top: 10px;
		margin-bottom: 20px;
	}
	.dataTables_wrapper .dataTables_filter {
	  margin-left:10px;
      z-index:1;
      position:absolute;
     }
     caption {
        font-style: normal;
        text-align: left;
        margin-left: 26%;
	}
	
		.detail-nav-g {
			height: 44px;
			margin-top: 10px;
			background-color: #F7F7F7;
			border-radius: 10px;
		}
		
		.detail-nav-g li {
			float: left;
			height: 44px;
			*height: 24px;
			padding: 10px 10px;
			border-right: 1px solid #ebebeb;
			cursor: pointer;
		}
		.download_money {
			float: right;
		}
		.download_reg {
			float: right;
		}
</style>
<script type="text/javascript" src="/js/games/gameLife.js?v=${version}"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_nav.jsp" >
		   <jsp:param value="gameli_user" name="game_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a class="green" href="/panel_manage/toMain.ac">主页</a></li>
				<li>></li>
				<li>${game.name }</li>
				<li>></li>
		    	<li>玩家分析</li>
		    	<li id="timeZoneName" style="float:right;font-size:12px;"></li>
			    </ol>
			     
			    <ul class="detail-nav">
			       <li id="install"><a ></a>新增</li>
			       <li id="dau"><a href="#"></a>活跃</li>
			       <li id="retention"><a href="#"></a>留存</li>
			       <li id="life" class="active"><a href="#"></a>注收比</li>
			    </ul>
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/gamePlayer/downloadData.ac" enctype="multipart/form-data">
			    <input name="gamesId" type="hidden" value="${game.id }" />
			     <%-- <input name="snid" type="hidden" value="${game.snid }" />
			     <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			     <input name="snid" type="hidden" value="${snid }" />
			     <input name="gameId" type="hidden" value="${gameId }" />
			     <input id="gameRate" type="hidden" value="${game.rate }" />
			     <input id="gameCurrency" type="hidden" value="${game.currency }" />
			     <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" name="view" type="hidden" value="life" />
			      
			       <input id="stats" name="stats" type="hidden" value="1" />
			       <input id="source2" name="source2" type="hidden" value=""/>
			    <div class="detail-info">
			    	<ul class="detail-ul">
			    		<li class="detail-li">
			    		   <span style="padding-left:20px;">
			    		      <select id="queryType" name="queryType" style="width:100px;">
			    		         <option value="install" selected="selected">按注册</option>
			    		         <option value="roleChoice">按创角</option>
			    		      </select>
			    		     <select id="indicators" name="indicators" style="padding-left:10px;width:100px;">
			    		         <option value="all" selected="selected">总览</option>
			    		         <option value="source">分渠道</option>
			    		         <option value="client">分服</option>
			    		      </select>
			    			</span>
			    			<span id="s_c_span" style="padding-left:20px;"></span>
			    			<span class="detail-left" style="width:40%">
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
			    
			    <div id="life_funnel_chart" class="clear-fix" style="width: 49%;float: left;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="life_funnel" class="zhibiao"></div>
			       <div id="life_funnel_data" class="zhibiao" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			    </div>
			    
			    <div id="life_line_chart" class="clear-fix" style="width: 50%;float: right;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="life_line" class="zhibiao"></div>
			       <div id="life_line_data" class="zhibiao" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			    </div>
			    
			    <div id="life_bar_chart" class="clear-fix" style="width: 100%;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="life_bar" class="zhibiao_bar"></div>
			       <div id="life_bar_data" class="zhibiao_bar" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			    </div>
			    <div>
				    <ul class="detail-nav-g">
				       <li id="registered">注收比</li>
				       <li id="money">金额</li>
				    </ul>
			    </div>
			   
              	 <div id="Download_reg">
		   			 	<button type="button"  class="download_reg" style="width:80px;">下载数据</button>
              	</div>
			    <div id="data" class="detail-table"></div>
			     <div id="Download_money">
		   			 	<button type="button"  class="download_money" style="width:80px;">下载数据</button>
              	</div>
              	 
			     <div id="data_money" class="detail-table"></div>
			    <div id="ajax_data" class="detail-table" style="position: relative;" hidden="hidden">
			    	 
			    
			    </div>
			   
			    
			    </form:form>
			</div>
		</div>
	</div >
	
	
	<div class="template_cache_money" hidden="hidden">
		<table class="dataTable2">
			<thead>
				<tr>
					<td>日期</td>
					<td class="head_install">新注册</td>
					<td>D0(${game.currency })</td>
					<td>D1(${game.currency })</td>
					<td>D2(${game.currency })</td>
					<td>D3(${game.currency })</td>
					<td>D4(${game.currency })</td>
					<td>D5(${game.currency })</td>
					<td>D6(${game.currency })</td>
					<td>D7(${game.currency })</td>
					<td>D8(${game.currency })</td>
					<td>D9(${game.currency })</td>
					<td>D10(${game.currency })</td>
					<td>D11(${game.currency })</td>
					<td>D12(${game.currency })</td>
					<td>D13(${game.currency })</td>
					<td>D14(${game.currency })</td>
					<td>D15(${game.currency })</td>
					<td>D16(${game.currency })</td>
					<td>D17(${game.currency })</td>
					<td>D18(${game.currency })</td>
					<td>D19(${game.currency })</td>
					<td>D20(${game.currency })</td>
					<td>D21(${game.currency })</td>
					<td>D22(${game.currency })</td>
					<td>D23(${game.currency })</td>
					<td>D24(${game.currency })</td>
					<td>D25(${game.currency })</td>
					<td>D26(${game.currency })</td>
					<td>D27(${game.currency })</td>
					<td>D28(${game.currency })</td>
					<td>D29(${game.currency })</td>
					<td>D30(${game.currency })</td>
			        <td>D31(${game.currency })</td>
					<td>D32(${game.currency })</td>
					<td>D33(${game.currency })</td>
					<td>D34(${game.currency })</td>
					<td>D35(${game.currency })</td>
					<td>D36(${game.currency })</td>
					<td>D37(${game.currency })</td>
					<td>D38(${game.currency })</td>
					<td>D39(${game.currency })</td>
					<td>D40(${game.currency })</td>
					<td>D41(${game.currency })</td>
					<td>D42(${game.currency })</td>
					<td>D43(${game.currency })</td>
					<td>D44(${game.currency })</td>
					<td>D45(${game.currency })</td>
					<td>D46(${game.currency })</td>
					<td>D47(${game.currency })</td>
					<td>D48(${game.currency })</td>
					<td>D49(${game.currency })</td>
					<td>D50(${game.currency })</td>
					<td>D51(${game.currency })</td>
					<td>D52(${game.currency })</td>
					<td>D53(${game.currency })</td>
					<td>D54(${game.currency })</td>
					<td>D55(${game.currency })</td>
					<td>D56(${game.currency })</td>
					<td>D57(${game.currency })</td>
					<td>D58(${game.currency })</td>
					<td>D59(${game.currency })</td>
					<td>D60(${game.currency })</td>
					<td>D61(${game.currency })</td>
					<td>D62(${game.currency })</td>
					<td>D63(${game.currency })</td>
					<td>D64(${game.currency })</td>
					<td>D65(${game.currency })</td>
					<td>D66(${game.currency })</td>
					<td>D67(${game.currency })</td>
					<td>D68(${game.currency })</td>
					<td>D69(${game.currency })</td>
					<td>D70(${game.currency })</td>
					<td>D71(${game.currency })</td>
					<td>D72(${game.currency })</td>
					<td>D73(${game.currency })</td>
					<td>D74(${game.currency })</td>
					<td>D75(${game.currency })</td>
					<td>D76(${game.currency })</td>
					<td>D77(${game.currency })</td>
					<td>D78(${game.currency })</td>
					<td>D79(${game.currency })</td>
					<td>D80(${game.currency })</td>
					<td>D81(${game.currency })</td>
					<td>D82(${game.currency })</td>
					<td>D83(${game.currency })</td>
					<td>D84(${game.currency })</td>
					<td>D85(${game.currency })</td>
					<td>D86(${game.currency })</td>
					<td>D87(${game.currency })</td>
					<td>D88(${game.currency })</td>
					<td>D89(${game.currency })</td>
					<td>D90(${game.currency })</td>
				</tr>
				
			</thead>
				
			<tbody>
				
				<tr>
					<td class="installDay"></td>
					<td class="install"></td>
					<td class="D0"></td>
					<td class="D1"></td>
					<td class="D2"></td>
					<td class="D3"></td>
					<td class="D4"></td>
					<td class="D5"></td>
					<td class="D6"></td>
					<td class="D7"></td>
					<td class="D8"></td>
					<td class="D9"></td>
					<td class="D10"></td>
					<td class="D11"></td>
					<td class="D12"></td>
					<td class="D13"></td>
					<td class="D14"></td>
					<td class="D15"></td>
					<td class="D16"></td>
					<td class="D17"></td>
					<td class="D18"></td>
					<td class="D19"></td>
					<td class="D20"></td>
					<td class="D21"></td>
					<td class="D22"></td>
					<td class="D23"></td>
					<td class="D24"></td>
					<td class="D25"></td>
					<td class="D26"></td>
					<td class="D27"></td>
					<td class="D28"></td>
					<td class="D29"></td>
					<td class="D30"></td>
				    <td class="D31"></td>
					<td class="D32"></td>
					<td class="D33"></td>
					<td class="D34"></td>
					<td class="D35"></td>
					<td class="D36"></td>
					<td class="D37"></td>
					<td class="D38"></td>
					<td class="D39"></td>
					<td class="D40"></td>
					<td class="D41"></td>
					<td class="D42"></td>
					<td class="D43"></td>
					<td class="D44"></td>
					<td class="D45"></td>
					<td class="D46"></td>
					<td class="D47"></td>
					<td class="D48"></td>
					<td class="D49"></td>
					<td class="D50"></td>
					<td class="D51"></td>
					<td class="D52"></td>
					<td class="D53"></td>
					<td class="D54"></td>
					<td class="D55"></td>
					<td class="D56"></td>
					<td class="D57"></td>
					<td class="D58"></td>
					<td class="D59"></td>
					<td class="D60"></td>
					<td class="D61"></td>
					<td class="D62"></td>
					<td class="D63"></td>
					<td class="D64"></td>
					<td class="D65"></td>
					<td class="D66"></td>
					<td class="D67"></td>
					<td class="D68"></td>
					<td class="D69"></td>
					<td class="D70"></td>
					<td class="D71"></td>
					<td class="D72"></td>
					<td class="D73"></td>
					<td class="D74"></td>
					<td class="D75"></td>
					<td class="D76"></td>
					<td class="D77"></td>
					<td class="D78"></td>
					<td class="D79"></td>
					<td class="D80"></td>
					<td class="D81"></td>
					<td class="D82"></td>
					<td class="D83"></td>
					<td class="D84"></td>
					<td class="D85"></td>
					<td class="D86"></td>
					<td class="D87"></td>
					<td class="D88"></td>
					<td class="D89"></td>
			        <td class="D90"></td>
				</tr>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		
	</div>
	
	
    <div class="template_cache" hidden="hidden">
		<table class="dataTable">
			<thead>
				<tr>
					<td>日期</td>
					<td class="head_install">新注册</td>
					<td>D0(${game.currency })</td>
					<td>D1(${game.currency })</td>
					<td>D2(${game.currency })</td>
					<td>D3(${game.currency })</td>
					<td>D4(${game.currency })</td>
					<td>D5(${game.currency })</td>
					<td>D6(${game.currency })</td>
					<td>D7(${game.currency })</td>
					<td>D8(${game.currency })</td>
					<td>D9(${game.currency })</td>
					<td>D10(${game.currency })</td>
					<td>D11(${game.currency })</td>
					<td>D12(${game.currency })</td>
					<td>D13(${game.currency })</td>
					<td>D14(${game.currency })</td>
					<td>D15(${game.currency })</td>
					<td>D16(${game.currency })</td>
					<td>D17(${game.currency })</td>
					<td>D18(${game.currency })</td>
					<td>D19(${game.currency })</td>
					<td>D20(${game.currency })</td>
					<td>D21(${game.currency })</td>
					<td>D22(${game.currency })</td>
					<td>D23(${game.currency })</td>
					<td>D24(${game.currency })</td>
					<td>D25(${game.currency })</td>
					<td>D26(${game.currency })</td>
					<td>D27(${game.currency })</td>
					<td>D28(${game.currency })</td>
					<td>D29(${game.currency })</td>
					<td>D30(${game.currency })</td>
			        <td>D31(${game.currency })</td>
					<td>D32(${game.currency })</td>
					<td>D33(${game.currency })</td>
					<td>D34(${game.currency })</td>
					<td>D35(${game.currency })</td>
					<td>D36(${game.currency })</td>
					<td>D37(${game.currency })</td>
					<td>D38(${game.currency })</td>
					<td>D39(${game.currency })</td>
					<td>D40(${game.currency })</td>
					<td>D41(${game.currency })</td>
					<td>D42(${game.currency })</td>
					<td>D43(${game.currency })</td>
					<td>D44(${game.currency })</td>
					<td>D45(${game.currency })</td>
					<td>D46(${game.currency })</td>
					<td>D47(${game.currency })</td>
					<td>D48(${game.currency })</td>
					<td>D49(${game.currency })</td>
					<td>D50(${game.currency })</td>
					<td>D51(${game.currency })</td>
					<td>D52(${game.currency })</td>
					<td>D53(${game.currency })</td>
					<td>D54(${game.currency })</td>
					<td>D55(${game.currency })</td>
					<td>D56(${game.currency })</td>
					<td>D57(${game.currency })</td>
					<td>D58(${game.currency })</td>
					<td>D59(${game.currency })</td>
					<td>D60(${game.currency })</td>
					<td>D61(${game.currency })</td>
					<td>D62(${game.currency })</td>
					<td>D63(${game.currency })</td>
					<td>D64(${game.currency })</td>
					<td>D65(${game.currency })</td>
					<td>D66(${game.currency })</td>
					<td>D67(${game.currency })</td>
					<td>D68(${game.currency })</td>
					<td>D69(${game.currency })</td>
					<td>D70(${game.currency })</td>
					<td>D71(${game.currency })</td>
					<td>D72(${game.currency })</td>
					<td>D73(${game.currency })</td>
					<td>D74(${game.currency })</td>
					<td>D75(${game.currency })</td>
					<td>D76(${game.currency })</td>
					<td>D77(${game.currency })</td>
					<td>D78(${game.currency })</td>
					<td>D79(${game.currency })</td>
					<td>D80(${game.currency })</td>
					<td>D81(${game.currency })</td>
					<td>D82(${game.currency })</td>
					<td>D83(${game.currency })</td>
					<td>D84(${game.currency })</td>
					<td>D85(${game.currency })</td>
					<td>D86(${game.currency })</td>
					<td>D87(${game.currency })</td>
					<td>D88(${game.currency })</td>
					<td>D89(${game.currency })</td>
					<td>D90(${game.currency })</td>
				</tr>
				
			</thead>
				
			<tbody>
				
				<tr>
					<td class="installDay"></td>
					<td class="install"></td>
					<td class="D0"></td>
					<td class="D1"></td>
					<td class="D2"></td>
					<td class="D3"></td>
					<td class="D4"></td>
					<td class="D5"></td>
					<td class="D6"></td>
					<td class="D7"></td>
					<td class="D8"></td>
					<td class="D9"></td>
					<td class="D10"></td>
					<td class="D11"></td>
					<td class="D12"></td>
					<td class="D13"></td>
					<td class="D14"></td>
					<td class="D15"></td>
					<td class="D16"></td>
					<td class="D17"></td>
					<td class="D18"></td>
					<td class="D19"></td>
					<td class="D20"></td>
					<td class="D21"></td>
					<td class="D22"></td>
					<td class="D23"></td>
					<td class="D24"></td>
					<td class="D25"></td>
					<td class="D26"></td>
					<td class="D27"></td>
					<td class="D28"></td>
					<td class="D29"></td>
					<td class="D30"></td>
				    <td class="D31"></td>
					<td class="D32"></td>
					<td class="D33"></td>
					<td class="D34"></td>
					<td class="D35"></td>
					<td class="D36"></td>
					<td class="D37"></td>
					<td class="D38"></td>
					<td class="D39"></td>
					<td class="D40"></td>
					<td class="D41"></td>
					<td class="D42"></td>
					<td class="D43"></td>
					<td class="D44"></td>
					<td class="D45"></td>
					<td class="D46"></td>
					<td class="D47"></td>
					<td class="D48"></td>
					<td class="D49"></td>
					<td class="D50"></td>
					<td class="D51"></td>
					<td class="D52"></td>
					<td class="D53"></td>
					<td class="D54"></td>
					<td class="D55"></td>
					<td class="D56"></td>
					<td class="D57"></td>
					<td class="D58"></td>
					<td class="D59"></td>
					<td class="D60"></td>
					<td class="D61"></td>
					<td class="D62"></td>
					<td class="D63"></td>
					<td class="D64"></td>
					<td class="D65"></td>
					<td class="D66"></td>
					<td class="D67"></td>
					<td class="D68"></td>
					<td class="D69"></td>
					<td class="D70"></td>
					<td class="D71"></td>
					<td class="D72"></td>
					<td class="D73"></td>
					<td class="D74"></td>
					<td class="D75"></td>
					<td class="D76"></td>
					<td class="D77"></td>
					<td class="D78"></td>
					<td class="D79"></td>
					<td class="D80"></td>
					<td class="D81"></td>
					<td class="D82"></td>
					<td class="D83"></td>
					<td class="D84"></td>
					<td class="D85"></td>
					<td class="D86"></td>
					<td class="D87"></td>
					<td class="D88"></td>
					<td class="D89"></td>
			        <td class="D90"></td>
				</tr>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
		
		
		
		<table class="life_funnel_table">
			<thead>
				<tr>
					<td class="head_install">按注册-注收比</td>
					<td>占比</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="install">0</td>
					<td class="rate">0%</td>
				</tr>
			</tbody>
		</table>
		<table class="life_line_table">
			<thead>
				<tr>
				    <td class="head_install">日期</td>
					<td>D0(${game.currency })</td>
					<td>D1(${game.currency })</td>
					<td>D3(${game.currency })</td>
					<td>D7(${game.currency })</td>
					<td>D30(${game.currency })</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="installDay"></td>
					<td class="D0"></td>
					<td class="D1"></td>
					<td class="D3"></td>
					<td class="D7"></td>
					<td class="D30"></td>
				</tr>
			</tbody>
		</table>
		<table class="life_bar_table">
			<thead>
				<tr>
				    <td>日期</td>
				    <td class="head_install">新注册</td>
					<td>D0(${game.currency })</td>
					<td>D1(${game.currency })</td>
					<td>D3(${game.currency })</td>
					<td>D7(${game.currency })</td>
					<td>D30(${game.currency })</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="installDay"></td>
					<td class="install"></td>
					<td class="D0"></td>
					<td class="D1"></td>
					<td class="D3"></td>
					<td class="D7"></td>
					<td class="D30"></td>
				</tr>
			</tbody>
		</table>
		<table class="ajax_table">
		<caption style="font-style: normal;text-align: left;margin-left: 10.1%;">
           <button type="button"  id="clientAllData" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
      </caption>
			<thead>
				<tr>
					<td>日期</td>
					<td class="head_type">渠道</td>
                    <td class="head_install">新注册</td>
					<td>D0(${game.currency })</td>
					<td>D1(${game.currency })</td>
					<td>D2(${game.currency })</td>
					<td>D3(${game.currency })</td>
					<td>D4(${game.currency })</td>
					<td>D5(${game.currency })</td>
					<td>D6(${game.currency })</td>
					<td>D7(${game.currency })</td>
					<td>D8(${game.currency })</td>
					<td>D9(${game.currency })</td>
					<td>D10(${game.currency })</td>
					<td>D11(${game.currency })</td>
					<td>D12(${game.currency })</td>
					<td>D13(${game.currency })</td>
					<td>D14(${game.currency })</td>
					<td>D15(${game.currency })</td>
					<td>D16(${game.currency })</td>
					<td>D17(${game.currency })</td>
					<td>D18(${game.currency })</td>
					<td>D19(${game.currency })</td>
					<td>D20(${game.currency })</td>
					<td>D21(${game.currency })</td>
					<td>D22(${game.currency })</td>
					<td>D23(${game.currency })</td>
					<td>D24(${game.currency })</td>
					<td>D25(${game.currency })</td>
					<td>D26(${game.currency })</td>
					<td>D27(${game.currency })</td>
					<td>D28(${game.currency })</td>
					<td>D29(${game.currency })</td>
					<td>D30(${game.currency })</td>
			        <td>D31(${game.currency })</td>
					<td>D32(${game.currency })</td>
					<td>D33(${game.currency })</td>
					<td>D34(${game.currency })</td>
					<td>D35(${game.currency })</td>
					<td>D36(${game.currency })</td>
					<td>D37(${game.currency })</td>
					<td>D38(${game.currency })</td>
					<td>D39(${game.currency })</td>
					<td>D40(${game.currency })</td>
					<td>D41(${game.currency })</td>
					<td>D42(${game.currency })</td>
					<td>D43(${game.currency })</td>
					<td>D44(${game.currency })</td>
					<td>D45(${game.currency })</td>
					<td>D46(${game.currency })</td>
					<td>D47(${game.currency })</td>
					<td>D48(${game.currency })</td>
					<td>D49(${game.currency })</td>
					<td>D50(${game.currency })</td>
					<td>D51(${game.currency })</td>
					<td>D52(${game.currency })</td>
					<td>D53(${game.currency })</td>
					<td>D54(${game.currency })</td>
					<td>D55(${game.currency })</td>
					<td>D56(${game.currency })</td>
					<td>D57(${game.currency })</td>
					<td>D58(${game.currency })</td>
					<td>D59(${game.currency })</td>
					<td>D60(${game.currency })</td>
					<td>D61(${game.currency })</td>
					<td>D62(${game.currency })</td>
					<td>D63(${game.currency })</td>
					<td>D64(${game.currency })</td>
					<td>D65(${game.currency })</td>
					<td>D66(${game.currency })</td>
					<td>D67(${game.currency })</td>
					<td>D68(${game.currency })</td>
					<td>D69(${game.currency })</td>
					<td>D70(${game.currency })</td>
					<td>D71(${game.currency })</td>
					<td>D72(${game.currency })</td>
					<td>D73(${game.currency })</td>
					<td>D74(${game.currency })</td>
					<td>D75(${game.currency })</td>
					<td>D76(${game.currency })</td>
					<td>D77(${game.currency })</td>
					<td>D78(${game.currency })</td>
					<td>D79(${game.currency })</td>
					<td>D80(${game.currency })</td>
					<td>D81(${game.currency })</td>
					<td>D82(${game.currency })</td>
					<td>D83(${game.currency })</td>
					<td>D84(${game.currency })</td>
					<td>D85(${game.currency })</td>
					<td>D86(${game.currency })</td>
					<td>D87(${game.currency })</td>
					<td>D88(${game.currency })</td>
					<td>D89(${game.currency })</td>
					<td>D90(${game.currency })</td>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
  
</body>
</html>