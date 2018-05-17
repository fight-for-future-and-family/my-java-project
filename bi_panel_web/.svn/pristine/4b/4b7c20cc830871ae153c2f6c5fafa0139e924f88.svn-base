<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>平台管理-添加平台</title>
<%@include file="/WEB-INF/jsp/common/headConfig.jsp"%>
<style type="text/css">
.p-input{
 width: 200px;
 height: 25px;
 border: none;
 border: 1px solid #ebebeb;
}

.p-ul {
height: 50px;
width:100%
}

.p-ul li{
 padding-bottom: 20px;
 width:100%;
 overflow: hidden;
}

.p-ul span{
 width:32%;
 text-align: left;
 float: left;
}

.errorMsg{
  color: red;
  display: none;
}

.notic{
  color: #999;
  font-size: 12px;
}
</style>
<script type="text/javascript" src="/js/timezone.js"></script>
<script type="text/javascript" src="/manage/js/adTracking/updatePlatforms.js?v=${version}"></script>

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
	<div class="lp-container">
		<jsp:include page="/WEB-INF/jsp/common/left_auth.jsp" >
		  <jsp:param value="authli_plat" name="auth_page"/>
		</jsp:include>
		<div class="data-container">
			<div class="detail">
				<ol class="lp-breadcrumb">
				<li><a href="/panel_manage/toMain.ac">主页</a></li>
    			<li>></li>
    			<li><a href="/panel_manage/adManage/toadManage.ac">平台管理</a></li>
				<li>></li>
		    	<li class="active green">编辑平台</li>
		    	
			    </ol>
			    <div class="person-data">
			      <form:form id="submitForm" name="submitForm" method="post" modelAttribute="platformsVO" action="/panel_manage/adManage/updatePlatforms.ac" enctype="multipart/form-data">
					<input id="view" type="hidden" value="addPlatforms" />
					<span id="paramsList" hidden="hidden">${platformsParams }</span>
					<form:input id="paramValues" path="paramValues" value="" hidden="hidden" />
					<p class="warn-btn">
						<span class="pt10 ml30" style="display:inline-block;">平台基本信息  
							<c:if test="${msg!=null && msg!='' }">
								<span style="color: red;font-size: 12px;font-weight: bold;">(${msg })</span>
							</c:if>
						</span>
						<button id="add" type="button" class="btn btn-sm warn-set" style="margin-left:400px;">保存</button>
				    </p>
					
					<ul id="baseUl" class="p-ul mt20">
						<li>
							<span>
							    <form:input path="entity.id" hidden="hidden" />
							    <label for="entity.name">平台名称</label>
								<form:input path="entity.name" 
								cssClass="b-radius5 p-input" required="required" />
								<font color="red">*</font>
						    </span>
						      <span>
							    <label for="entity.code">平台CODE</label>
								<form:input path="entity.code" 
								cssClass="b-radius5 p-input" required="required" placeholder="注意：code不能使用下划线_" />
								<font color="red">*</font>
						      </span>
						</li>
					</ul>
					
					<p class="warn-btn">
						<span class="pt10 ml30" style="display:inline-block;">平台参数信息
						(<span style="color: #49C37C;font-size: 12px;font-weight: bold;">参数填写规则：统一左侧为本地代码，右侧为对接代码。删除置空文本框即可。</span>)
						</span>
						
				    </p>
				    <ul id="paramUl" class="p-ul mt20" style="min-height:120px;height:0px;margin-top: 20px;">
				    	<li>
				    	  <span>
				    	    <label for="mac">mac</label>
				    	    <input name="mac" class="b-radius5 p-input"   />
				    	  </span>
				    	  <span>
				    	    <label for="macMd5">macMd5</label>
				    	    <input name="macMd5" class="b-radius5 p-input"   />
				    	  </span>
				    	  <span>
				    	    <label for="ifa">ifa</label>
				    	    <input name="ifa" class="b-radius5 p-input"   />
				    	  </span>
				    	</li>
				    	<li>
				    	  <span>
				    	    <label for="ifaMd5">ifaMd5</label>
				    	    <input name="ifaMd5" class="b-radius5 p-input"   />
				    	  </span>
				    	  <span>
				    	    <label for="userAgent">userAgent</label>
				    	    <input name="userAgent" class="b-radius5 p-input"   />
				    	  </span>
				    	  <span>
				    	    <label for="ip">ip</label>
				    	    <input name="ip" class="b-radius5 p-input"   />
				    	  </span>
				    	</li>
				    	<li>
				    	  <span style="width:99%">
				    	    <label for="extra">extra</label>
				    	    <input name="extra" class="b-radius5 p-input" style="width:82%;"
				    	    placeholder="扩展字段，格式：param1:value1,param2:value2" />
				    	  </span>
				    	</li>
				    </ul>
					<p class="warn-btn">
						<span class="pt10 ml30" style="display:inline-block;">平台回调字段信息
						(<span style="color: #49C37C;font-size: 12px;font-weight: bold;">回调参数填写规则：统一左侧为本地代码，右侧为对接代码。删除置空文本框即可。</span>)
						</span>
				    </p>
				    <ul id="columnUl" class="p-ul mt20" style="min-height:600px;">
				    <li>
				    
				    	<span>
				    		<label for="cb.id">id</label>
				    		<input name="cb.id" class="b-radius5 p-input"   />
				    	</span>
				    
				    	  <span>
				    	    <label for="cb.mac">mac</label>
				    	    <input name="cb.mac" class="b-radius5 p-input"   />
				    	  </span>
				    	  <span>
				    	    <label for="cb.macMd5">macMd5</label>
				    	    <input name="cb.macMd5" class="b-radius5 p-input"   />
				    	  </span>
				    	  
				    	</li>
				    	<li>
				    	
				    	  <span>
				    	    <label for="cb.ifa">ifa</label>
				    	    <input name="cb.ifa" class="b-radius5 p-input"   />
				    	  </span>
				    	
				    	  <span>
				    	    <label for="cb.ifaMd5">ifaMd5</label>
				    	    <input name="cb.ifaMd5" class="b-radius5 p-input"   />
				    	  </span>
				    	  <span>
				    	    <label for="cb.userAgent">userAgent</label>
				    	    <input name="cb.userAgent" class="b-radius5 p-input"   />
				    	  </span>
				    	 
				    	</li>
				    	<li>
				    	
				    	 <span>
				    	    <label for="cb.ip">ip</label>
				    	    <input name="cb.ip" class="b-radius5 p-input"   />
				    	  </span>
				    	
				    	  <span style="width:60%">
				    	    <label for="cb.extra">extra</label>
				    	    <input name="cb.extra" class="b-radius5 p-input" style="width:40%;"
				    	    placeholder="扩展字段，格式：param1:value1,param2:value2" />
				    	  </span>
				    	</li>
				    </ul>
			    	</form:form>	
			    </div>
			</div>
		</div>
		
		<div hidden="hidden" class="template">
			<ul class="templateUl">
				<li class="extraLi">
		    		<span>
					    <label for="cb.extra.name">本地代码</label>
					    <input name="cb.extra.name" class="b-radius5 p-input"  />
				     </span>
		    		<span>
					    <label for="cb.extra.code">对接代码</label>
					    <input name="cb.extra.code" class="b-radius5 p-input"  />
				     </span>
		    		<span>
					    <label for="cb.extra.type">是否扩展</label>
					    <select name="cb.extra.type">
					       <option value="2">是</option>
					       <option value="1">否</option>
					    </select>
					    <button type="button" class="cb-del btn btn-default btn-sm" style="margin-left: 20%" >删除</button>
				     </span>
				</li>
			</ul>
		</div>
		
		
	</div>
	
	
</body>
</html>