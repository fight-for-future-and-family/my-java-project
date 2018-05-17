<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="robots" content="noodp, noydir" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="viewport" content="width=device-width,height=device-height,maximum-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link href="/manage/css/login.css?v=1.0" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<title>常见问题</title>
<style type="text/css">
.warn-btn{
	height: 35px;
	text-align: left;
	border-bottom: 1px solid #ebebeb;
}
.ml30{
	margin-left: 30px !important;
}
.pt10{
	padding-top: 10px;
}
.quesShow{
  margin-top:20px;
  margin-left:5%;
  margin-bottom:50px;
  width:80%;
  border: 1px solid #deb887;
  border-radius: 10px;
  padding: 20px 30px;
}
.grid {
	width: 95%;
	margin: 0 auto;
}
img.shadow {
   padding: 5px 10px 10px 5px;
   height: expression(this.width > 750 ? this.height = this.height * 750 / this.width : "auto");
   width: expression(this.width > 680 ? "680px" : "auto");
   max-width:680px;
}
</style>
<script type="text/javascript">
$(function(){
	$(".questions .question").each(function(i,n){
		$(n).click(function(){
			var v = $(n).attr("val");
			$("#show").show();
			$(".question").css('color','black');
			$(n).css('color','red');
			
			$("#show div").hide();
			$("."+v+"").show();
		});
	});
});
</script>
</head>
<body id="content">
<div id="header" class="header-img">
   <span style="display:inline-block;float: right;padding-top: 5%;padding-right:15px">
      <a href="/panel_manage/indexLogin.ac">返回登录</a>
   </span>
 </div>
	
  <div id="content-container" class="grid clr" style="overflow: hidden;">
      <p class="warn-btn">
		 <span class="pt10 ml30" style="display:inline-block;"><span>报表系统在线帮助</span></span>
		 <span class="pt10" style="display:inline-block;float: right;"><span>问题反映邮箱：BI@hoolai.com</span></span>
	  </p>
      <div class="questions" style="line-height:200%;margin-top: 20px;margin-left: 40px;">
	    <ul>
	        <li class="question" val="openUserName"><a>1.如何开通账户？</a></li>
			<li class="question" val="findPass"><a>2.如何找回密码？</a></li>
	        <li class="question" val="updatePass"><a>3.如何修改用户名和密码？ </a></li>
	        <li class="question" val="dataAndChart"><a>4.图表版和纯数据版的报表系统有何区别？</a></li>
	        <li class="question" val="returnLogin"><a>5.为什么有时候点击页面时会回到登录页面？</a></li>
	        <li class="question" val="noAuth"><a>6.为什么登录后看不到我负责的游戏或者能看到不是我负责的游戏？</a></li>
	        <li class="question" val="csvError"><a>7.下载数据后，.csv文件用excel打开中文乱码？</a></li>
	        <li class="question" val="source"><a>8.为什么系统中搜寻不到我想看的渠道数据或者服务器数据？</a></li>
	        <li class="question" val="other"><a>9.看完以上问题仍不能解决我的问题怎么办？</a></li>
	    </ul>
    </div>
</div>
<div id="show" hidden="hidden" class="quesShow">
       <div class="openUserName" hidden="hidden">
                                  发送申请账号邮件至BI@hoolai.com并抄送给你的直接领导，邮件内容如下例：<br><br>
          BI账号：zhangsan,真实名称：张三，手机号码：158********,邮箱：zhangsan@hoolai.com<br>
                                 申请项目：游戏名称，项目角色：运营专员<br><br>
          <font color="red">系统声明：提供个人信息中，手机号码方便接收游戏短信报警，邮箱方便密码找回。</font><br><br>
                                 账号申请成功后，BI的相关工作人员会将登陆系统原始密码以邮件或者工作QQ告知，登陆系统后尽快在账号管理中修改密码！如何修改，参见Q3。
       </div>
       <div class="findPass" hidden="hidden">
          1. 在登录页面(http://report.hoolai.com)中点击忘记密码，如下图：<br>
          <img class="shadow" src="/images/qa/findPass1.jpg"><br><br>
          2. 在(http://report.hoolai.com/panel_manage/toInstructions.ac)填写你的账号、与账号绑定的邮箱，如下图： <br>
          <img class="shadow" src="/images/qa/findPass2.jpg"><br><br>
          3.点击找回密码，成功后显示以下信息：<br>
          <img class="shadow" src="/images/qa/findPass3.jpg"><br><br>
          4.登录邮箱查看密码，登陆系统尽快在账号管理中修改密码，以确保vip.hoolai.com也能够正常使用！如何修改，参见Q3。
       </div>
       <div class="updatePass" hidden="hidden">
          1. 登录到系统主界面后，在页面右上方点击账号管理，如下图：<br>
          <img class="shadow" src="/images/qa/updatePass.jpg"><br><br>
          2. 如果只修改基本信息，修改后点击保存即可。如果箱修改密码，点击修改密码按钮，如下图： <br>
          <img class="shadow" src="/images/qa/updatePass1.jpg"><br><br>
          3.填写原始密码以及确认新密码，如下图：<br>
          <img class="shadow" src="/images/qa/updatePass2.jpg"><br><br>
       </div>
       <div class="dataAndChart" hidden="hidden">
          1.纯数据版单纯以表格形式展现数据。<br>
            <span style="padding-left:10px"> 优点：</span><br>
               <span style="padding-left:25px">可展示的指标多，精度高。页面简洁，产生的流量少，速度快，特别适用于手机访问。</span><br>
               <span style="padding-left:25px"> 纯数据版不限查询时间，也适用于在电脑上查看更多数据以及下载。</span><br>
            <span style="padding-left:10px"> 不足：功能相对少，欠直观。</span><br><br>
          2.图表版功能完全，分别以文字、表格、图表三种方式呈现数据。<br>
                <span style="padding-left:15px">2.1.图表形式的数据非常直观，易于发现异常情况，易于看出趋势，易于根据现有的数据推测近期甚至未来的数据。</span><br>
                <span style="padding-left:45px">柱形图和条形图表现较多关系类型数据之间的比较;</span><br>
                <span style="padding-left:45px"> 折线图表现数据的趋势、起伏;</span><br>
                <span style="padding-left:45px"> 饼图表现各部分数据占整体的百分比。</span><br>
                <span style="padding-left:15px">2.2.一些累计平均的图形数据可能存在些许误差，图表版也提供了表格数据以便精确分析。</span>                      
       </div>
       <div class="returnLogin" hidden="hidden">
          1.session过期，重新登录便能恢复正常。<br>
          2.如果重新登录后仍然出现返回登录页面的情况，请将出现的问题以及出现问题的操作流程邮件给BI@hoolai.com,BI相关工作人员会尽快解决问题。<br>
       </div>
       <div class="noAuth" hidden="hidden">
          1.先确认自己的申请账号邮件提供的项目以及角色是否正确，如正确则是系统分配权限错误。<br>
          2.然后邮件给BI@hoolai.com，说明情况, 修正权限。
       </div>
       <div class="csvError" hidden="hidden">
                                  在简体中文环境下，EXCEL打开的CSV文件默认是ANSI编码，如果CSV文件的编码方式为utf-8、Unicode等编码可能就会出现文件乱码的情况。<br>
          1.使用记事本打开CSV文件.如下图：<br>
          <img class="shadow" src="/images/qa/csvError1.jpg"><br><br>
          2.点击菜单：文件-另存为，编码方式选择ANSI，如下图：<br>
          <img class="shadow" src="/images/qa/csvError2.jpg"><br><br>
          3.保存完毕后，再用EXCEL打开这个文件就不会出现乱码的情况。<br>
          <img class="shadow" src="/images/qa/csvError3.jpg"><br><br>
       </div>
       <div class="source" hidden="hidden">
          1.报表系统中渠道名称是各游戏项目的开发人员报送过来的渠道编码。如果运营人员不清楚编码规则，让其项目的开发人员整理一份渠道编码名称对应表，以方便查看。<br>
          2.在报表系统中搜寻不到某些渠道的原因有：在选择的日期段内，这个渠道没有接入数据;渠道数据报送异常;渠道数据分析异常。依次排除、核对情况。<br><br>
                                 服务器同理。
       </div>
       <div class="other" hidden="hidden">
                                     邮件给BI@hoolai.com，说明情况,BI相关工作人员会尽快解决问题。
       </div>
    </div>
</body>
</html>