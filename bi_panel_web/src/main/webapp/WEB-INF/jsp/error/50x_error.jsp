<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"  import="java.io.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--  
<meta http-equiv="refresh" content="1;url=/panel_manage/indexLogin.ac">
-->
<title>系统异常</title>
</head>
<body>
  <div id="ReportWrapper" class="content clearfix">
    <div align="center" style="padding-top: 100px">
    	出错了！<br>
发生了以下的错误：
<br><hr><font color=red><hr>
getMessage():<br>
<%=exception.getMessage()%><br><hr>
getLocalizedMessage():<br>
<%=exception.getLocalizedMessage()%><br><hr>
PrintStatckTrace():<br>
<%
StringWriter sw=new StringWriter();
PrintWriter pw=new PrintWriter(sw);
exception.printStackTrace(pw);
out.println(sw);
%><br>
</font>
    </div>
</div>
</body>
</html>
