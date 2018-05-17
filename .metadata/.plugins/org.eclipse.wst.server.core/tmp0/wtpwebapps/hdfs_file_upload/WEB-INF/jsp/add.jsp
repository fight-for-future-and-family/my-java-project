
<%@ page language="java" pageEncoding="UTF-8"%>


<html>
<head>
<title>Spring MVC Tutorial by Crunchify - Hello World Spring MVC
	Example</title>
<style type="text/css">
body {
	background-image: url('wl.png');
}
</style>
</head>
<body>

	<div
		style="font-family: verdana; padding: 100px; border-radius: 100px; font-size: 30px; text-align: left;">

		<form action="<%=request.getContextPath()%>/add" method="POST"
			enctype="multipart/form-data">
			username: <input type="text" name="username" /><br />
		    password: <input type="password" name="password" /><br />
		    path: <input type="text" name="path" /><br />
		         选择要上传的文件: <input type="file" name="myfiles" /><br />
			<br> <input type="submit" value="确认上传文件" />
		</form>

	</div>


</body>
</html>
