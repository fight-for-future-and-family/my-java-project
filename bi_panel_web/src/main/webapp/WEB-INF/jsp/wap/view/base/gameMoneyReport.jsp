<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>

<title>${game.name }-Money</title>
<link rel="stylesheet" href="/css/wap/wap.css?v=${version}"/>
<link rel="stylesheet" href="/css/wap/realTime.css?v=${version}"/>
  <link rel="stylesheet" href="/css/wap/realtimeHour.css?v=${version}"/>
<script type="text/javascript" src="/wap/js/dataTables.js?v=${version}"></script>
<script type="text/javascript" src="/wap/js/gameMoneyReport.js?v=${version}"></script>
<style type="text/css">
.caption {
	font-style: normal;
	text-align: left;
	top: 5px;
	margin-right: 20px;
}

.caption span.ui-selected {
	background: #56C887;
	color: white;
}

.caption span.last {
	border-right: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
	
}

.caption span {
	float: left;
	margin: 5px auto;
	width: 76px;
	text-align: center;
	font-size: 14px;
	line-height: 27px;
	border-left: 1px solid #ebebeb;
	border-bottom: 1px solid #ebebeb;
	border-top: 1px solid #ebebeb;
}
#ajax_data .dataTables_wrapper .dataTables_filter {
	margin-left: 10px;
	z-index: 1;
	position: absolute;
	margin-top: -30px;
}

</style>
</head>
<body>
	<div class="lp-container">
	<jsp:include page="/WEB-INF/jsp/wap/common/header.jsp" />
		<div class="data-container-main">
			<div class="detail"  style="width:100%;">
			     <ul class="detail-nav">
			       <li id="realTime"><a></a>实时</li>
			       <li class="active" id="daily" ><a ></a>基本运营数据</li>
		       		<c:if test="${game != null && game.terminalType == 1}">
			       		<li id="equipDau"><a></a>设备数据</li>
			       </c:if>
		       		<li id="taskList" ><a ></a>自定义报表</li>
		       		
			    </ul>
			    <div class="area-sub">
					<div id="layout-t" class="tab-product tab-sub-3 ui-style-gradient">
						<h2 class="tab-hd">
							<span class="tab-hd-con" view="daily"><a></a>日报</span> 
							<span class="tab-hd-con" view="retention"><a></a>留存</span> 
							<span class="tab-hd-con" view="life"><a></a>注收比</span>
							<span class="tab-hd-con current" view="money"><a></a>金额</span>
						</h2>
					<div class="tab-bd dom-display">
					<div class="tab-bd-con current">
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/wap/gameView/downloadData.ac" enctype="multipart/form-data">
			    <input name="gamesId" type="hidden" value="${game.id }" />
			     <%-- <input name="snid" type="hidden" value="${game.snid }" />
			      <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			      <input name="snid" type="hidden" value="${snid }" />
				  <input name="gameId" type="hidden" value="${gameId }" />
			      <input id="view" name="view" type="hidden" value="money" />
			       <input id="gameRate" type="hidden" value="${game.rate }" />
			    <div class="detail-info" style="margin-top: 10px;min-height:70px;overflow: hidden;">
			    	<ul class="detail-ul">
			    		<li class="detail-li" style="overflow: hidden;">
			    		   <div style="padding-left:5px;padding-top:8px;float: left;">
			    		      <select id="channel" name="channel" style="padding-left:10px;width:137px;">
			    		         <option value="install_all" selected="selected">按注册-总览</option>
			    		         <option value="role_all" >按角色-总览</option>
			    		         <option value="install_source">按注册-分渠道</option>
			    		         <option value="role_source">按角色-分渠道</option>
			    		         <option value="client">分服</option>
			    		      </select>
			    			</div>
			    			<div id="s_c_span" style="padding-left:5px;float: left;width:200px;margin-top: -1px;" class="detail-left-append"></div>
			    		</li>
			    		<li class="detail-li">
			    			<span class="detail-left" style="width:75%">
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:105px;"  value="${beginTime }" id="beginTime" name="beginTime"/>
				    			<span>至</span>
				    			<input type="text" class="b-radius5 p-input" style="padding-left:10px;width:105px;" value="${endTime }" id="endTime" name="endTime" />
			    			</span>
			    			<span class="detail-right" style="width:25%;margin-right: 0px">
			    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;padding-top: 3px;margin-right: 5px;">查询</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
		<div id="data" class="detail-table" style="text-align: left;">
		   <span id="warnSpan" style="font-size: 10px;margin-left: 5px;" >提示：手机端点击后可能需要耐心等待6秒左右...</span>
		   <div id="data_caption" class="caption">
			   <span class="ui-selected" val="1">D0~D30</span>
			   <span val="2">D31~D60</span>
			   <span class="last" val="3">D61~D90</span>
            </div>
		</div>
		<div id="ajax_data" class="detail-table" style="position: relative;text-align: left;" hidden="hidden">
		   <span id="warnSpan" style="font-size: 10px;margin-left: 5px;" >提示：手机端点击后可能需要耐心等待6秒左右...</span>
		   <div id="ajax_data_caption" class="caption" style="float: right;margin-bottom: 30px;">
			   <span class="ui-selected" val="1">D0~D30</span>
			   <span val="2">D31~D60</span>
			   <span class="last" val="3">D61~D90</span>
			   <button id="clientAllData" type="button" class="btn btn-default btn-sm"  style="margin-left: 10px;margin-top: 5px;width:80px;">下载数据</button>
            </div>
		
		</div>
		</form:form>
		</div>
		</div>
		</div>
		</div>
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
				    <td class="D31">D31</td>
					<td class="D32">D32</td>
					<td class="D33">D33</td>
					<td class="D34">D34</td>
					<td class="D35">D35</td>
					<td class="D36">D36</td>
					<td class="D37">D37</td>
					<td class="D38">D38</td>
					<td class="D39">D39</td>
					<td class="D40">D40</td>
					<td class="D41">D41</td>
					<td class="D42">D42</td>
					<td class="D43">D43</td>
					<td class="D44">D44</td>
					<td class="D45">D45</td>
					<td class="D46">D46</td>
					<td class="D47">D47</td>
					<td class="D48">D48</td>
					<td class="D49">D49</td>
					<td class="D50">D50</td>
					<td class="D51">D51</td>
					<td class="D52">D52</td>
					<td class="D53">D53</td>
					<td class="D54">D54</td>
					<td class="D55">D55</td>
					<td class="D56">D56</td>
					<td class="D57">D57</td>
					<td class="D58">D58</td>
					<td class="D59">D59</td>
					<td class="D60">D60</td>
					<td class="D61">D61</td>
					<td class="D62">D62</td>
					<td class="D63">D63</td>
					<td class="D64">D64</td>
					<td class="D65">D65</td>
					<td class="D66">D66</td>
					<td class="D67">D67</td>
					<td class="D68">D68</td>
					<td class="D69">D69</td>
					<td class="D70">D70</td>
					<td class="D71">D71</td>
					<td class="D72">D72</td>
					<td class="D73">D73</td>
					<td class="D74">D74</td>
					<td class="D75">D75</td>
					<td class="D76">D76</td>
					<td class="D77">D77</td>
					<td class="D78">D78</td>
					<td class="D79">D79</td>
					<td class="D80">D80</td>
					<td class="D81">D81</td>
					<td class="D82">D82</td>
					<td class="D83">D83</td>
					<td class="D84">D84</td>
					<td class="D85">D85</td>
					<td class="D86">D86</td>
					<td class="D87">D87</td>
					<td class="D88">D88</td>
					<td class="D89">D89</td>
			        <td class="D90">D90</td>
				</tr>
			</tbody>
			
			<tfoot>
			</tfoot>
		</table>
		<table class="ajax_table">
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
	</div>
	</div>
</body>
</html>