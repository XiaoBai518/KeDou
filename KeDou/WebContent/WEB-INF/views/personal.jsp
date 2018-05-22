<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <c:set var="userImgPath" value="/file/personal" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>personal</title>
    <link rel="stylesheet" type="text/css" href="${ctx }/css/personal.css"/>
 
	<link rel="stylesheet" type="text/css" href="${ctx }/css/banner.css">
       <script src="${ctx }/js/jquery-2.2.0.min.js"></script>
		<script src="${ctx }/js/jquery.zui.js"></script>
		<script src="${ctx }/js/zui.min.js"></script>
		<script src="${ctx }/js/personal.js"></script>
		<script src="${ctx }/js/common.js"></script>
		   <link rel="stylesheet" type="text/css" href="${ctx }/css/zui.min.css">
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
					<form>
						<div class="input-group">
							<div class="input-control search-box search-box-circle has-icon-left has-icon-right search-example" id="searchboxExample">
								<input id="inputSearchExample3" type="search" class="form-control search-input" placeholder="搜索"/>
								<label for="inputSearchExample3" class="input-control-icon-left search-icon">
									<i class="icon icon-search" style="line-height: 2;"></i>
								</label>
							</div>
							<span class="input-group-btn">
								<button class="btn btn-primary" type="submit">搜索</button>
							</span>
						</div>
					</form>					
				</div>
				<!--定位-->
				<div id="locate">
					<i class="icon icon-map-marker icon-2x" ></i>
					<p id="address">[<a href="#" style="color:#3280fc; text-decoration: none;">切换地址</a>]</p>
				</div>
				<!--个人头像-->
				<div id="userPad">
					<div id="myPhoto">
						<img src="${ctx }/img/login.png"/>
					</div>
					<div class="popover bottom" id="myPopover">
						<div class="arrow"></div>
							<h3 class="popover-title"><a href="#"><i class="icon icon-home"></i>&nbsp&nbsp个人中心</a></h3>
							<div class="popover-content">
								<ul>
									<li class="menu-li"><a href="#"><i class="icon icon-envelope"></i>&nbsp&nbsp我的消息</a></li>
									<li class="menu-li"><a href="#"><i class="icon icon-history"></i>&nbsp&nbsp我的足迹</a></li>
									<li class="menu-li"><a href="#"><i class="icon icon-check-board"></i>&nbsp&nbsp我的预约</a></li>
									<li class="menu-li"><a href="#"><i class="icon icon-star"></i>&nbsp&nbsp我的收藏</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				
				<!--导航条-->
				<div id="navigation">
					<ul class="nav nav-secondary">
						<li><a href="#" class="nav-a">首页</a></li>
						<li><a href="#" class="nav-a">动态 <span class="label label-badge label-success">4</span></a></li>
						<li><a href="#" class="nav-a">项目 </a></li>
						<li><a href="#" class="nav-a">哈哈</a></li>
					</ul>
				</div>
			</div> 
		</div>
		<script>
			 $(document).ready(function(){
		         $("#myPhoto").hover(function(){
		            $("#myPopover").toggle();
		         });
		      });
		      $(document).ready(function(){
		         $("#myPopover").hover(function(){
		            $("#myPopover").show();
		         });
		         $("#myPopover").mouseleave(function(){
		            $("#myPopover").hide();
		         });
		      });
			
		</script>

		
		<div class="main_left" style="float:left;">
			<div class="image"  id="image"><img src="${userImgPath}/${sessionScope.loginUser.userIcon}" width="100%" height="100%"></div>
			<div class="image_change" id="image_change" onclick="check('chanageImg','${userImgPath}/${sessionScope.loginUser.userIcon}')">
				<img src="${ctx }/img/timg.jpg"width="100%" height="100%">
					<p class="image_text" id="image_text">更换头像</p>	
			</div>
			<div class="username">${sessionScope.loginUser.userName }</div>
			<div class="username_mananger">账户管理</div>
				<div class="usermessage"></div>
				<a style="text-decoration: none;" href="${ctx }/user/person_message?address=message" target="preson_megs"><div class="usermessage111" id="usermessage1" name="yes">个人信息</div></a>
				<a style="text-decoration: none;" href="${ctx }/user/person_message?address=history" target="preson_megs"><div class="usermessage2" id="usermessage2">访问历史</div></a>
				<a style="text-decoration: none;" href="${ctx }/user/person_message?address=yuyue" target="preson_megs"><div class="usermessage3" id="usermessage3">预约课程</div></a>
				<a style="text-decoration: none;" href="${ctx }/user/person_message?address=collect" target="preson_megs"><div class="usermessage4" id="usermessage4">收藏课程</div></a>
		</div>
		
		<div class="main_right"  style="float:left;">
			<div id="buton1" class="buton1" onclick="check('personal')"><label style="line-height :20px;vertical-align:middle;">编辑</label></div>
			<div class="main_message">
				<iframe src="${ctx}/user/person_message?address=message"  frameborder="0" width="100%" scrolling="no" height="100%" name="preson_megs"></iframe>
			</div>
		</div>

	</body>
</html>