<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
      <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta charset="utf-8">
		<title>商家管理系统</title>
   		<link rel="icon" href="${ctx }/img/favicon.ico" type="image/x-icon" />
   		<link rel="shortcut icon" href="${ctx }/img/favicon.ico">
   		<link rel="Bookmark" href="${ctx }/img/favicon.ico">
   		<link rel="SHORTCUT ICON" href="${ctx }/img/favicon.ico"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/busadmin.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/zui.tabs.min.css">
		<link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
		<link href="${ctx }/css/datetimepicker.min.css" rel="stylesheet">
   		<script src="${ctx }/js/jquery.zui.js"></script>
		<script type="text/javascript" src="${ctx }/js/busadmin.js"></script>
		<script src="${ctx }/js/zui.min.js"></script>
		<script src="${ctx }/js/zui.tabs.min.js"></script>
			<script type="text/javascript">
	// 定义欢迎标签页
	 tabs = [{
		title: '实时数据',
		url: '${ctx}/business/toRealData?busid=${busid}',
		type: 'iframe'
	}];
	</script>
	</head>
	<body>
		<div class="container-fluid" style="background-color: #F9F8F7;">
			<div id="header">
				<!--logo-->
				<div id="logo">
					<img src="${ctx }/img/logo2.png"/>
				</div>
				<!--搜索框-->
				<div id="find">
				</div>
				<!--定位-->
				<div id="locate">
					<i class="icon icon-map-marker icon-2x" ></i>
					<p id="address">${sessionScope.userAddress.address }[<a href="${ctx }/useraddress/toaddress" style="color:#3280fc; text-decoration: none;">切换地址</a>]</p>
				</div>
				<!--个人头像-->
				<div id="userPad">
						 <c:if test="${ not empty sessionScope.loginBusiness}">
					 	<div id="myPhoto">
						<img src="${ctx }/img/label-5.jpg" onerror="this.src='${ctx }/img/logo.jpg'"/>
						</div>
						<div class="popover bottom" id="myPopover">
							<div class="arrow"></div>
								<h3 class="popover-title"><a href="${ctx }/business/tobusadmin"><i class="icon icon-home"></i>&nbsp&nbsp个人中心</a></h3>
								<div class="popover-content">
									<ul>
										<li class="menu-li"><a href="javascript:;" onclick="realData(${busid})"><i class="icon icon-envelope"></i>&nbsp&nbsp店铺数据</a></li>
										<li class="menu-li"><a href="javascript:;" onclick="basicInfo(${busid})"><i class="icon icon-history"></i>&nbsp&nbsp我的信息</a></li>
									</ul>
								</div>
						</div>
					 </c:if>
					 <c:if test="${ empty sessionScope.loginBusiness}">
					 	<div id="loginregiste">
							<a href="${ctx }/user/toiframe"><font size="4">登陆 </font></a><font size="5" color="blue"> / </font><a href="${ctx }/user/toiframe?option=regist"><font size="4"> 注册</font></a>
						</div>
					 </c:if>
				</div>
			</div>	
				<!--导航条-->
				<div id="navigation">
					<ul class="nav nav-secondary">
						<li><a href="${ctx }/common/toindex" class="nav-a">首页</a></li>
						<li><a href="${ctx }/calendar/list" class="nav-a">考试日历</a></li>
						<li><a href="${ctx }/forum/showforum" class="nav-a">论坛 </a></li>
					</ul>
				</div>
				
		</div>
		<div class="container-fluid">
			<!--左侧导航-->
			<div id="left-nav">
				<ul class="nav nav-secondary nav-stacked">
					<li class="nav-heading">我的店铺</li>
					<li class="nav-left-item"><a href="#collapseExample0" data-toggle="collapse" data-parent="#left-nav"><i class="icon icon-home">&nbsp;&nbsp;</i>店铺实时数据</a></li>
					<li>
						<ul class="collapse" id="collapseExample0">
							<li class="collapse-item"><a href="javascript:;" onclick="realData(${busid})"><i class="icon icon-line-chart">&nbsp;&nbsp;</i>实时数据</a></li>
							<li class="collapse-item"><a href="javascript:;" onclick="basicInfo(${busid})"><i class="icon icon-list-alt">&nbsp;&nbsp;</i>基本信息</a></li>
						</ul>
  					</li>
  					<li class="nav-left-item"><a href="#collapseExample1" data-toggle="collapse" data-parent="#left-nav"><i class="icon icon-book">&nbsp;&nbsp;</i>课程管理</a></li>
  					<li>
  						<ul class="collapse" id="collapseExample1">
							<li class="collapse-item"><a href="javascript:;" onclick="addCourse()"><i class="icon icon-plus"></i>&nbsp&nbsp添加课程</a></li>
							<li class="collapse-item"><a href="javascript:;" onclick="allCourse(1)"><i class="icon icon-list"></i>&nbsp&nbsp所有课程</a></li>
						</ul>
  					</li>
					<li class="nav-left-item"><a href="#collapseExample2" data-toggle="collapse" data-parent="#left-nav"><i class="icon icon-newspaper-o">&nbsp;&nbsp;</i>公告管理</a></li>
  					<li>
  						<ul class="collapse" id="collapseExample2">
							<li class="collapse-item"><a href="javascript:;" onclick="addAnnounce()"><i class="icon icon-plus"></i>&nbsp&nbsp添加公告</a></li>
							<li class="collapse-item"><a href="javascript:;" onclick="allAnnounce()"><i class="icon icon-list"></i>&nbsp&nbsp所有公告</a></li>
						</ul>
  					</li>
  					<li class="nav-left-item"><a href="#collapseExample3" data-toggle="collapse" data-parent="#left-nav"><i class="icon icon-time">&nbsp;&nbsp;</i>预约管理&nbsp<span class="label label-badge label-success">${untreatedCount }</span></a></li>
  					<li>
  						<ul class="collapse" id="collapseExample3">
							<li class="collapse-item"><a href="javascript:;" onclick="untreated()"><i class="icon icon-plus"></i>&nbsp&nbsp未处理预约&nbsp<span class="label label-badge label-success">${untreatedCount }</span></a></li>
							<li class="collapse-item"><a href="javascript:;" onclick="allOrders()"><i class="icon icon-list"></i>&nbsp&nbsp已处理预约</a></li>
						</ul>
  					</li>
  					<li class="nav-left-item"><a href="#collapseExample4" data-toggle="collapse" data-parent="#left-nav"><i class="icon icon-file-text-o">&nbsp;&nbsp;</i>经验贴</a></li>
  					<li>
  						<ul class="collapse" id="collapseExample4">
							<li class="collapse-item"><a href="javascript:;" onclick="addExperience()"><i class="icon icon-plus"></i>&nbsp&nbsp新增经验贴</a></li>
							<li class="collapse-item"><a href="javascript:;" onclick="allExperience()"><i class="icon icon-list"></i>&nbsp&nbsp所有经验贴</a></li>
						</ul>
  					</li>
				</ul>
			</div>
			<!--右侧标签页-->
			<div class="tabs" id="tabs"></div>
		</div>

	</body>
</html>