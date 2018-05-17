<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/WEB-INF/jsp/front/css/bootstrap.min.css" rel="stylesheet" type=”text/css” >
<link href="/WEB-INF/jsp/front/css/bootstrap-theme.min.css" rel="stylesheet" type=”text/css” >
<script type=”text/javascript”  src="/WEB-INF/jsp/front/js/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<script type=”text/javascript”  src="/WEB-INF/jsp/front/js/bootstrap.min.js"></script>

<!-- 新 Bootstrap 核心 CSS 文件   -->
<!-- link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css"> -->

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!--<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css"> -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!--<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script> -->

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!-- <script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script> -->

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>

	<form action="login" method="post">
		<p>
			用户名: <input name="uname" type="text">
		</p>
		<p>
		          密&nbsp;&nbsp;码: <input name="upasswd" type="text">
		</p>
		   <input type="submit" value="登录">
	</form>

         <font color="red">${error}</font>

  <table class="table table-hover">
      <caption>悬停表格布局</caption>
      <thead>
      <tr>
          <th>名称</th>
          <th>城市</th>
          <th>密码</th>
      </tr>
      </thead>
      <tbody>
      <tr>
          <td>Tanmay</td>
          <td>Bangalore</td>
          <td>560001</td>
      </tr>
      <tr>
          <td>Sachin</td>
          <td>Mumbai</td>
          <td>400003</td>
      </tr>
      <tr>
          <td>Uma</td>
          <td>Pune</td>
          <td>411027</td>
      </tr>
      </tbody>
  </table>
  
  <div class=”container”>
<form role=”form”>
<div class=”form-group”>
<label for=”exampleInputEmail1″>Email address</label>
<input type=”email” class=”form-control” id=”exampleInputEmail1″ placeholder=”Enter email”>
</div>
<div class=”form-group”>
<label for=”exampleInputPassword1″>Password</label>
<input type=”password” class=”form-control” id=”exampleInputPassword1″ placeholder=”Password”>
</div>
<div class=”form-group”>
<label for=”exampleInputFile”>File input</label>
<input type=”file” id=”exampleInputFile”>
<p class=”help-block”>Example block-level help text here.</p>
</div>
<div class=”checkbox”>
<label>
<input type=”checkbox”> Check me out
</label>
</div>
<button type=”submit” class=”btn btn-default”>Submit</button>
</form>
</div>
 
  
</body>
</html>