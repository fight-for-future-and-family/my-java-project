<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style type="text/css">
		       body {
		            text-align:center;margin:center;
		        }
		        #wl{MARGIN-RIGHT: auto; MARGIN-LEFT: auto;}
		</style>
	<title>HL_BI  GameData Manager !</title>
</head>
<body  >

	<h2>游戏数据修补操作</h2>
	<h4><font color="red">数据无价，请慎重操作</font></h4>
	<br>  


 <form action="bushu" method="post">
	 
	<div id="wl" style="border: 8px solid; width: 80%;height:260px;border-color: blue;margin:center;">
			<table border="0" width="60%" align="center">
					<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<tr>
						<td align="left">请输入_表名 </td>
						<td><input name="table" type="text" value="${table}"></td>
					</tr>
					<tr><tr/> <tr><tr/> <tr><tr/> <tr><tr/><tr>
						<td align=left>请输入_snid </td>
						<td><input name="snid" type="text" value="${snid}"></td>
					</tr>
					<tr><tr/> <tr><tr/> <tr><tr/> <tr><tr/>
					<tr>
						<td align="left">请输入_gameid </td>
						<td><input name="gameid" type="text" value="${gameid}"></td>
					</tr>
					<tr><tr/> <tr><tr/> <tr><tr/> <tr><tr/>
					<tr>
						<td align="left">请选择_日期 </td>
						<td><input name="ds" type="date" value="${ds}"></td>
					</tr>
					<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
					<tr>
					<td align="center"><label><input name="target" type="radio" value="1" />增加数据</label></td>
					<td align="center"><label><input name="target" type="radio" value="2" />删除数据 </label></td>
					<tr/>

			</table>
	</div> <br> <br> <br> <br>
					 <input type="submit" style="font-size:30px" value="提交修改" > 
					        <font color="red">${error}</font>
</form>

</body>
</html>