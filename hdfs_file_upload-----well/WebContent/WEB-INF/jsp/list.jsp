<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>upload file to hdfs</title>
<style type="text/css">
body {
	background-image: url('http://crunchify.com/bg.png');
}
</style>
</head>
<body>

    <c:if test="${result==6}"> <h4><font color="#5544">未做任何操作！</font></h4></c:if> <br/>
    <c:if test="${result==0}"> <h4><font color="green">OK！ 文件正常上传。</font></h4></c:if> <br/>
    <c:if test="${result==1}"> <h4><font  color="red">上传发生异常。</font></h4></c:if> <br/>
	<c:forEach items="${users}" var="user">  
    所上传文件名为：            ${fileName}<br/>
    上传文件路径为：            ${realPath}
		<br />
	</c:forEach>
	<br />
	<a href="<%=request.getContextPath()%>/add">继续上传文件</a>

</body>
</html>

