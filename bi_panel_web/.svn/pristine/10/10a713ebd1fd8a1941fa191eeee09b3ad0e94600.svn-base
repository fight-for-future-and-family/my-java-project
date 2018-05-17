<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${game.name }-留存</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>

<style type="text/css">
	.kong{
	    width:100%;
		height:50px;
	}
	.zhibiao{
	    width:100%;
		height:400px;
		float: left;
		margin-top: 20px;
	}
	.zhibiao_fun{
		height:350px;
		margin-top: 10px;
		margin-bottom: 30px;
	}
	
	.dataTables_wrapper .dataTables_filter {
	  margin-left:10px;
      z-index:1;
      position:absolute;
     }
     caption {
        font-style: normal;
        text-align: right;
        margin-right: 25%;
	}
</style>
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
</style>
<script type="text/javascript" src="/js/games/gameRetention.js?v=${version}"></script>
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
			       <li id="retention" class="active"><a href="#"></a>留存</li>
			       <li id="life"><a href="#"></a>注收比</li>
			    </ul>
			    <form:form id="downLoadForm" method="post" modelAttribute="gamesVO" action="/panel_bi/gamePlayer/downloadData.ac" enctype="multipart/form-data">
			    <input name="gamesId" type="hidden" value="${game.id }" />
			     <%-- <input name="snid" type="hidden" value="${game.snid }" />
			     <input name="gameId" type="hidden" value="${game.gameid }" /> --%>
			     <input name="snid" type="hidden" value="${snid }" />
			     <input name="gameId" type="hidden" value="${gameId }" />
			     <input id="timeZone" type="hidden" value="${game.timeZone }" />
			      <input id="view" name="view" type="hidden" value="retention" />
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
			    			<span class="detail-right" style="text-align: right;margin-right: 20px;">
			    				<button id="query" type="button" class="btn btn-default btn-sm" style="width:80px;">查询</button>
			    			</span>
			    		</li>
			    	</ul>
			    </div>
			    
			    <div id="ir_funnel_chart" class="clear-fix" style="width: 49%;float: left;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="ir_funnel" class="zhibiao_fun"></div>
			       <div id="ir_funnel_data" class="zhibiao_fun" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			    </div>
			    
			    <div id="ir_x_chart" class="clear-fix" style="width: 50%;float: right;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>
			       <div id="ir_x" class="zhibiao_fun"></div>
			       <div id="ir_x_data" class="zhibiao_fun" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			     </div>
			     
			     <!--  <div id="ir_y_chart" class="clear-fix" style="width: 49%;float: left;overflow: hidden;position: relative;">
			       <div class="dataShap">
			           <ul style="top:18px;right:80px;">
						 <li class="onChoose"><em class="columnar">图</em></li>
						 <li><em class="linear">表</em></li>
					   </ul>
			       </div>-->
			       <div id="ir_y" class="zhibiao"></div>
			       <!--  <div id="ir_y_data" class="zhibiao" hidden="hidden"
			         style="border: 1px solid #ebebeb;border-radius: 10px;margin-top: 0px;padding:10px 0px;"> </div>
			     </div>-->
			     
			    <div id="data" class="detail-table">
				   
			    </div>
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
            <td class="head_install">新注册</td>
         	<td>D1</td>
			<td>D2</td>
			<td>D3</td>
			<td>D4</td>
			<td>D5</td>
			<td>D6</td>
			<td>D7</td>
			<td>D8</td>
			<td>D9</td>
			<td>D10</td>
			<td>D11</td>
			<td>D12</td>
			<td>D13</td>
			<td>D14</td>
			<td>D15</td>
			<td>D16</td>
			<td>D17</td>
			<td>D18</td>
			<td>D19</td>
			<td>D20</td>
			<td>D21</td>
			<td>D22</td>
			<td>D23</td>
			<td>D24</td>
			<td>D25</td>
			<td>D26</td>
			<td>D27</td>
			<td>D28</td>
			<td>D29</td>
			<td>D30</td>
			<td>D31</td>
			<td>D32</td>
			<td>D33</td>
			<td>D34</td>
			<td>D35</td>
			<td>D36</td>
			<td>D37</td>
			<td>D38</td>
			<td>D39</td>
			<td>D40</td>
			<td>D41</td>
			<td>D42</td>
			<td>D43</td>
			<td>D44</td>
			<td>D45</td>
			<td>D46</td>
			<td>D47</td>
			<td>D48</td>
			<td>D49</td>
			<td>D50</td>
			<td>D51</td>
			<td>D52</td>
			<td>D53</td>
			<td>D54</td>
			<td>D55</td>
			<td>D56</td>
			<td>D57</td>
			<td>D58</td>
			<td>D59</td>
			<td>D60</td>
			<td>D61</td>
			<td>D62</td>
			<td>D63</td>
			<td>D64</td>
			<td>D65</td>
			<td>D66</td>
			<td>D67</td>
			<td>D68</td>
			<td>D69</td>
			<td>D70</td>
			<td>D71</td>
			<td>D72</td>
			<td>D73</td>
			<td>D74</td>
			<td>D75</td>
			<td>D76</td>
			<td>D77</td>
			<td>D78</td>
			<td>D79</td>
			<td>D80</td>
			<td>D81</td>
			<td>D82</td>
			<td>D83</td>
			<td>D84</td>
			<td>D85</td>
			<td>D86</td>
			<td>D87</td>
			<td>D88</td>
			<td>D89</td>
			<td>D90</td>
        </tr>
      </thead>
      <tbody>
        <tr>
            <td class="date">日期</td>
            <td class="install">新注册</td>
            <td class="d1">-</td>
			<td class="d2">-</td>
			<td class="d3">-</td>
			<td class="d4">-</td>
			<td class="d5">-</td>
			<td class="d6">-</td>
			<td class="d7">-</td>
			<td class="d8">-</td>
			<td class="d9">-</td>
			<td class="d10">-</td>
			<td class="d11">-</td>
			<td class="d12">-</td>
			<td class="d13">-</td>
			<td class="d14">-</td>
			<td class="d15">-</td>
			<td class="d16">-</td>
			<td class="d17">-</td>
			<td class="d18">-</td>
			<td class="d19">-</td>
			<td class="d20">-</td>
			<td class="d21">-</td>
			<td class="d22">-</td>
			<td class="d23">-</td>
			<td class="d24">-</td>
			<td class="d25">-</td>
			<td class="d26">-</td>
			<td class="d27">-</td>
			<td class="d28">-</td>
			<td class="d29">-</td>
			<td class="d30">-</td>
			<td class="d31">-</td>
			<td class="d32">-</td>
			<td class="d33">-</td>
			<td class="d34">-</td>
			<td class="d35">-</td>
			<td class="d36">-</td>
			<td class="d37">-</td>
			<td class="d38">-</td>
			<td class="d39">-</td>
			<td class="d40">-</td>
			<td class="d41">-</td>
			<td class="d42">-</td>
			<td class="d43">-</td>
			<td class="d44">-</td>
			<td class="d45">-</td>
			<td class="d46">-</td>
			<td class="d47">-</td>
			<td class="d48">-</td>
			<td class="d49">-</td>
			<td class="d50">-</td>
			<td class="d51">-</td>
			<td class="d52">-</td>
			<td class="d53">-</td>
			<td class="d54">-</td>
			<td class="d55">-</td>
			<td class="d56">-</td>
			<td class="d57">-</td>
			<td class="d58">-</td>
			<td class="d59">-</td>
			<td class="d60">-</td>
			<td class="d61">-</td>
			<td class="d62">-</td>
			<td class="d63">-</td>
			<td class="d64">-</td>
			<td class="d65">-</td>
			<td class="d66">-</td>
			<td class="d67">-</td>
			<td class="d68">-</td>
			<td class="d69">-</td>
			<td class="d70">-</td>
			<td class="d71">-</td>
			<td class="d72">-</td>
			<td class="d73">-</td>
			<td class="d74">-</td>
			<td class="d75">-</td>
			<td class="d76">-</td>
			<td class="d77">-</td>
			<td class="d78">-</td>
			<td class="d79">-</td>
			<td class="d80">-</td>
			<td class="d81">-</td>
			<td class="d82">-</td>
			<td class="d83">-</td>
			<td class="d84">-</td>
			<td class="d85">-</td>
			<td class="d86">-</td>
			<td class="d87">-</td>
			<td class="d88">-</td>
			<td class="d89">-</td>
			<td class="d90">-</td>
        </tr>
      </tbody>
   </table>
   <table class="ir_funnel_table">
      <thead>
        <tr>
          <td class="head_install">新注册留存</td>
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
   <table class="ajax_table">
   <caption style="font-style: normal;text-align: left;margin-left: 10.21%;">
           <button type="button" class="btn btn-default btn-sm" style="width:80px;">下载数据</button>
      </caption>
      <thead>
        <tr>
          <td>日期</td>
          <td class="head_type">渠道</td>
          <td class="head_install">新注册</td>
          <td>D1</td>
			<td>D2</td>
			<td>D3</td>
			<td>D4</td>
			<td>D5</td>
			<td>D6</td>
			<td>D7</td>
			<td>D8</td>
			<td>D9</td>
			<td>D10</td>
			<td>D11</td>
			<td>D12</td>
			<td>D13</td>
			<td>D14</td>
			<td>D15</td>
			<td>D16</td>
			<td>D17</td>
			<td>D18</td>
			<td>D19</td>
			<td>D20</td>
			<td>D21</td>
			<td>D22</td>
			<td>D23</td>
			<td>D24</td>
			<td>D25</td>
			<td>D26</td>
			<td>D27</td>
			<td>D28</td>
			<td>D29</td>
			<td>D30</td>
			<td>D31</td>
			<td>D32</td>
			<td>D33</td>
			<td>D34</td>
			<td>D35</td>
			<td>D36</td>
			<td>D37</td>
			<td>D38</td>
			<td>D39</td>
			<td>D40</td>
			<td>D41</td>
			<td>D42</td>
			<td>D43</td>
			<td>D44</td>
			<td>D45</td>
			<td>D46</td>
			<td>D47</td>
			<td>D48</td>
			<td>D49</td>
			<td>D50</td>
			<td>D51</td>
			<td>D52</td>
			<td>D53</td>
			<td>D54</td>
			<td>D55</td>
			<td>D56</td>
			<td>D57</td>
			<td>D58</td>
			<td>D59</td>
			<td>D60</td>
			<td>D61</td>
			<td>D62</td>
			<td>D63</td>
			<td>D64</td>
			<td>D65</td>
			<td>D66</td>
			<td>D67</td>
			<td>D68</td>
			<td>D69</td>
			<td>D70</td>
			<td>D71</td>
			<td>D72</td>
			<td>D73</td>
			<td>D74</td>
			<td>D75</td>
			<td>D76</td>
			<td>D77</td>
			<td>D78</td>
			<td>D79</td>
			<td>D80</td>
			<td>D81</td>
			<td>D82</td>
			<td>D83</td>
			<td>D84</td>
			<td>D85</td>
			<td>D86</td>
			<td>D87</td>
			<td>D88</td>
			<td>D89</td>
			<td>D90</td>
        </tr>
      </thead>
      <tbody>
      </tbody>
   </table>
</div>
  
</body>
</html>