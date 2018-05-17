<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" type="text/css" href="picture_css/wl.css" />
<title>HL_BI 集群监控导航页面</title>
</head>
<body>

        <!-- 定义顶部块，并为这个块设定一个名称为head的id值 -->
        <div id="head">
            <h1>HL_BI 集群监控导航页面</h1>
        </div>
            <!-- 在contarner块中定义两个子块，然后分别为这两个块设定名称为main、siderbar的class类 -->
            <div class="zabbix1"><h1>zabbix监控页面</h1><br>
                      <a href="http://119.254.145.145:82/hlzabbix/zabbix.php?action=dashboard.view"><font></font><h2>请点击这里</h2></a>   
            </div>
            <div class="zabbix2"><h1>gnaglia监控页面</h1>
                      <a href="http://119.254.145.145:81/glmonitor/"><h2>请点击这里</h2></a>   
            </div>
            <div class="hadoop1"><h1>hadoop监控页面</h1>
                      <a href="http://192.168.90.110:50070"><h2>请点击这里</h2></a>   
            </div>
            <div class="hadoop2"><h1>hadoop监控页面</h1>
                      <a href="http://192.168.90.110:8088/cluster"><h2>请点击这里</h2></a>   
            </div>

</body>
</html>