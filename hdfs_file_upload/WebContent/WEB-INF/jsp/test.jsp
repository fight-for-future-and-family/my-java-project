<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" type="text/css" href="picture_css/wl.css" />
<title>HL_BI 集群管理工具</title>
</head>
<body>

        <!-- 定义顶部块，并为这个块设定一个名称为head的id值 -->
        <div id="head">
            <h1>欢迎使用集群后台管理工具</h1>
        </div>
        <!-- 定义主体容器块，并为这个块设定一个名称为container的id值 -->
        <div id="container">
            <!-- 在contarner块中定义两个子块，然后分别为这两个块设定名称为main、siderbar的class类 -->
            <div class="main"><h1>上传文件至系统</h1><br>
                      <a href="add"><font></font><h2>请点击这里</h2></a>   
            </div>
            <div class="sidebar"><h1>游戏数据修补操作</h1>
                      <a href="bushu"><h2>请点击这里</h2></a>   
            </div>
        </div>
        <!-- 定义底部块，并为这个块设定一个名称为bottom的id值 -->
        <div id="bottom"><h1>这里是底部</h1></div>

</body>
</html>